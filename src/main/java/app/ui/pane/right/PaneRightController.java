package app.ui.pane.right;

import app.application.document.DocumentDTO;
import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.ui.document.edit.EditorDocumentController;
import app.ui.main.ScreenMainState;
import app.ui.message.MessageConfirmController;
import app.ui.message.MessageConfirmWindow;
import app.ui.message.MessageInfoController;
import app.ui.message.MessageInfoWindow;
import app.ui.pane.left.PaneLeftController;
import app.ui.roadmap.RoadMapController;
import app.ui.roadmap.RoadMapWindow;
import app.ui.topic.register.RegisterTopicController;
import app.ui.topic.register.RegisterTopicWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PaneRightController implements Initializable {

    @FXML
    private Button bttNavigationLeft;

    @FXML
    private Button bttNavigationRight;

    @FXML
    private TextField txtHierarchyPath;

    @FXML
    private TabPane tabPaneStudy;

    @FXML
    private Tab tabMain;

    @FXML
    private Button bttRoadMap;

    @FXML
    private Label lblTitleMain;

    @FXML
    private TextField txtSearchTopics;

    @FXML
    private Button bttAddTopic;

    @FXML
    private Button bttEditTopic;

    @FXML
    private Button bttRemoveTopic;

    @FXML
    private ListView<TopicDTO> listViewTopics;

    @FXML
    private Tab tabAdd;

    private final Stage stage;
    private final ScreenMainState mainState;
    private final PaneRightUIState paneRightUIState = new PaneRightUIState();
    private final ObservableList<TopicDTO> listTopics = FXCollections.observableArrayList();
    private final FilteredList<TopicDTO> filteredTopics = new FilteredList<>(listTopics);
    private PaneRightService paneRightService = new PaneRightService();
    private ConfigDragDroppedListView configDragDroppedListView = new ConfigDragDroppedListView();
    private TabDocumentFactory tabDocumentFactory = new TabDocumentFactory();
    private PaneRightNavigator navigator = new PaneRightNavigator();
    private PaneLeftController paneLeftController;

    public PaneRightController(Stage stage, ScreenMainState mainState) {
        this.stage = stage;
        this.mainState = mainState;
    }

    public PaneRightNavigator getNavigator() {
        return navigator;
    }

    public void setPaneLeftController(PaneLeftController paneLeftController) {
        this.paneLeftController = paneLeftController;
    }

    public Button getBttNavigationLeft() {
        return bttNavigationLeft;
    }

    public Button getBttNavigationRight() {
        return bttNavigationRight;
    }

    public TextField getTxtHierarchyPath() {
        return txtHierarchyPath;
    }

    public Label getLblTitleMain() {
        return lblTitleMain;
    }

    public ObservableList<TopicDTO> getListTopics() {
        return listTopics;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listViewTopics.setItems(filteredTopics);

        listViewTopics.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                openTopic();
            }
        });

        listViewTopics.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                openTopic();
            }
        });

        configDragDroppedListView.configureDragDropped(
                listViewTopics,
                () -> {
                    mainState.refreshItemSelected();
                }
        );

        bttNavigationLeft.setOnAction(event -> {
            navigateBack();
        });

        bttNavigationRight.setOnAction(event -> {
            navigateForward();
        });

        bttRoadMap.setOnAction(event -> {
            showRoadMap();
        });

        txtSearchTopics.setOnAction(event -> {
            searchTopic();
        });

        txtSearchTopics.textProperty().addListener((obs, oldValue, newValue) -> {
            searchTopic();
        });

        tabAdd.setOnSelectionChanged(event -> {
            if (tabAdd.isSelected()) {
                addNewDocument(new DocumentDTO());
            }
        });

        bttAddTopic.setOnAction(event -> {
            newTopic();
        });

        bttEditTopic.setOnAction(event -> {
            editTopic();
        });

        bttRemoveTopic.setOnAction(event -> {
            removeTopic();
        });

        mappingStacksNavigatorState();

        PaneRightUIBinder.bind(
                this,
                mainState,
                paneRightUIState
        );

    }

    // Navegação ------------------------------

    private void navigateBack() {
        if (!confirmChangeStudyOrTopic())
            return;

        Object itemBack = navigator.back();
        mainState.setItemSelected(itemBack);
        mainState.refreshItemSelected();
        mappingStacksNavigatorState();
        loadTabsDocument();
    }

    private void navigateForward() {
        if (!confirmChangeStudyOrTopic())
            return;

        Object itemForward = navigator.forward();
        mainState.setItemSelected(itemForward);
        mainState.refreshItemSelected();
        mappingStacksNavigatorState();
        loadTabsDocument();
    }

    // Tabs -----------------------------

    private void addNewDocument(DocumentDTO documentDto) {
        if (mainState.getItemSelected() == null) {
            MessageInfoController controller = new MessageInfoController();
            controller.setMsgUser(
                    "Selecione primeiro um estudo para adicionar\n" +
                            "um novo texto."
            );

            MessageInfoWindow window = new MessageInfoWindow(stage, controller);
            window.showScreen();

            Tab tabMain = tabPaneStudy.getTabs().get(0);
            tabPaneStudy.getSelectionModel().select(tabMain);

        } else {
            Tab newTab = tabDocumentFactory.addTabDocument(
                    stage,
                    tabPaneStudy,
                    tabAdd,
                    mainState.getItemSelected(),
                    documentDto
            );

            tabPaneStudy.getSelectionModel().select(newTab);
        }
    }

    // Tools -------------------------

    private void showRoadMap() {
        RoadMapController roadMapController = new RoadMapController();
        roadMapController.setItemSelected(mainState.getItemSelected());
        RoadMapWindow roadMapWindow	= new RoadMapWindow(stage, roadMapController);
        roadMapWindow.showScreen();
    }

    // Topics -------------------------

    private void openTopic() {
        if (!confirmChangeStudyOrTopic())
            return;

        TopicDTO topicSelected = listViewTopics.getSelectionModel().getSelectedItem();
        if (topicSelected != null) {
            mainState.setItemSelected(topicSelected);
            mainState.refreshItemSelected();
            navigator.navigate(mainState.getItemSelected());
            mappingStacksNavigatorState();
            loadTabsDocument();
        }
    }

    private void searchTopic() {
        String search = txtSearchTopics.getText().trim().toLowerCase();

        filteredTopics.setPredicate(topic ->
                search.isBlank()
                        ||
                topic.getTitle().toLowerCase().indexOf(search.toLowerCase()) != -1
        );
    }

    private void newTopic() {
        if (mainState.getItemSelected() == null) {
            return;
        }

        RegisterTopicController registerTopicController = new RegisterTopicController();
        registerTopicController.setTopicDto(new TopicDTO());

        if (mainState.getItemSelected() instanceof StudyDTO studyDto) {
            registerTopicController.setStudy(studyDto);
        } else if (mainState.getItemSelected() instanceof TopicDTO topicDto) {
            registerTopicController.setTopicParent(topicDto);
        }

        RegisterTopicWindow registerTopicWindow = new RegisterTopicWindow(stage, registerTopicController);
        registerTopicWindow.showScreen();

        if (registerTopicController.getTopicDto().getId() != null &&
                registerTopicController.getTopicDto().getId() > 0) {
            mainState.refreshItemSelected();
            loadTabsDocument();
        }
    }

    public void editTopic() {
        TopicDTO topicSelectedDto = listViewTopics.getSelectionModel().getSelectedItem();

        if (mainState.getItemSelected() == null || topicSelectedDto == null) {
            return;
        }

        RegisterTopicController registerTopicController = new RegisterTopicController();
        registerTopicController.setTopicDto(topicSelectedDto);

        RegisterTopicWindow registerTopicWindow = new RegisterTopicWindow(stage, registerTopicController);
        registerTopicWindow.showScreen();

        navigator.refreshItem(registerTopicController.getTopicDto());

        mainState.refreshItemSelected();

        loadTabsDocument();
    }

    private void removeTopic() {
        TopicDTO topicSelectedDto = listViewTopics.getSelectionModel().getSelectedItem();

        if (mainState.getItemSelected() == null || topicSelectedDto == null) {
            return;
        }

        MessageConfirmController controller = new MessageConfirmController();
        controller.setConfirm(false);
        controller.setMsgUser(
                "Deseja realmente remover o tópico selecionado?\n" +
                        "Todos os tópicos de: " + topicSelectedDto.getTitle().toUpperCase() + "\n" +
                        "também serão removidos!"
        );

        MessageConfirmWindow window = new MessageConfirmWindow(stage, controller);
        window.showScreen();

        if (controller.getConfirm()) {
            paneRightService.removeTopic(topicSelectedDto);
            navigator.removeItem(topicSelectedDto);

            mainState.refreshItemSelected();
            loadTabsDocument();
        }
    }

    // Helpers -----------------------

    public void openStudy(Object itemSelected) {
        if (!confirmChangeStudyOrTopic())
            return;

        mainState.setItemSelected(itemSelected);
        mainState.refreshItemSelected();
        navigator.navigate(mainState.getItemSelected());
        mappingStacksNavigatorState();
        loadTabsDocument();
    }

    public void loadTabsDocument() {
        tabDocumentFactory.loadTabsDocument(stage, tabPaneStudy, tabMain, tabAdd, mainState.getItemSelected());
    }

    public boolean confirmChangeStudyOrTopic() {
        EditorDocumentController editorDocumentController =
                tabDocumentFactory.verifyDocumentEditingOrNotSave(
                        tabPaneStudy,
                        tabMain,
                        tabAdd
                );

        if (editorDocumentController != null) {
            String nameItem = null;
            if (mainState.getItemSelected() instanceof StudyDTO studyDto) {
                nameItem = studyDto.getMatter();
            } else if (mainState.getItemSelected() instanceof TopicDTO topicDto) {
                nameItem = topicDto.getTitle();
            }

            MessageConfirmController controller = new MessageConfirmController();
            controller.setConfirm(false);
            controller.setMsgUser(
                    "Existem Documentos não salvos em:\n" +
                    nameItem + "\n" +
                    "Deseja continuar mesmo assim?\n\n" +
                    "Documento editando: " + editorDocumentController.getDocumentDto().getTitle()
            );

            MessageConfirmWindow window = new MessageConfirmWindow(stage, controller);
            window.showScreen();

            return controller.getConfirm();
        }

        return true;
    }

    private void mappingStacksNavigatorState() {
        paneRightUIState.getBackStack().setAll(navigator.getBackStack());
        paneRightUIState.getForwardStack().setAll(navigator.getForwardStack());
    }

}
