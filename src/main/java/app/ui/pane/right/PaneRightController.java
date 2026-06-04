package app.ui.pane.right;

import app.application.document.DocumentDTO;
import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.ui.document.edit.EditorDocumentController;
import app.ui.message.MessageConfirmController;
import app.ui.message.MessageConfirmWindow;
import app.ui.message.MessageInfoController;
import app.ui.message.MessageInfoWindow;
import app.ui.pane.left.PaneLeftController;
import app.ui.topic.register.RegisterTopicController;
import app.ui.topic.register.RegisterTopicWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    private Stage stage;
    private ObservableList<TopicDTO> listTopicsObservable = FXCollections.observableArrayList();
    private PaneRightService paneRightService = new PaneRightService();
    private PaneRightUIHelper uiHelper = new PaneRightUIHelper();
    private TabDocumentFactory tabDocumentFactory = new TabDocumentFactory();
    private PaneRightNavigator navigator = new PaneRightNavigator();
    private Object itemSelected;
    private PaneLeftController paneLeftController;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setItemSelected(Object objectCurrentSelected) {
        this.itemSelected = objectCurrentSelected;
    }

    public Object getItemSelected() {
        return itemSelected;
    }

    public PaneRightNavigator getNavigator() {
        return navigator;
    }

    public void setPaneLeftController(PaneLeftController paneLeftController) {
        this.paneLeftController = paneLeftController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listViewTopics.setItems(listTopicsObservable);

        listViewTopics.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                selectTopicInListView();
            }
        });

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

        txtSearchTopics.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE && txtSearchTopics.getText().isBlank()) {
                uiHelper.updateListViewTopics(listTopicsObservable, listViewTopics, itemSelected);
            }
        });

        tabAdd.setOnSelectionChanged(event -> {
            if (tabAdd.isSelected()) {
                addNewTabDocument(new DocumentDTO());
            }
        });

        listViewTopics.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                selectTopicInListView();
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
    }

    // Navegação ------------------------------

    private void navigateBack() {
        if (!verifyDocumentEditingOrNotSave())
            return;

        Object itemBack = navigator.back();
        refreshItemSelected(itemBack);
        loadDataScreen();
    }

    private void navigateForward() {
        if (!verifyDocumentEditingOrNotSave())
            return;

        Object itemForward = navigator.forward();
        refreshItemSelected(itemForward);
        loadDataScreen();
    }

    // Aba Document

    private void addNewTabDocument(DocumentDTO documentDto) {
        if (itemSelected == null) {
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
                    itemSelected,
                    documentDto
            );

            tabPaneStudy.getSelectionModel().select(newTab);
        }
    }

    // Tools -------------------------

    private void showRoadMap() {

    }

    // Topics -------------------------

    private void selectTopicInListView() {
        TopicDTO topicSelected = listViewTopics.getSelectionModel().getSelectedItem();
        if (topicSelected != null) {
            openItem(topicSelected);
        }
    }

    private void searchTopic() {
        String search = txtSearchTopics.getText();
        List<TopicDTO> listPossible = new ArrayList<>();

        if (!search.equals("")) {

            for (TopicDTO topicDto : listTopicsObservable) {
                if (topicDto.getTitle().toLowerCase().indexOf(search.toLowerCase()) != -1) {
                    listPossible.add(topicDto);
                }
            }

            if (listPossible.size() > 0) {
                listTopicsObservable.clear();
                listTopicsObservable.addAll(listPossible);
                listViewTopics.refresh();
            }
        } else {
            uiHelper.updateListViewTopics(listTopicsObservable, listViewTopics, itemSelected);
        }
    }

    private void newTopic() {
        if (itemSelected == null) {
            return;
        }

        RegisterTopicController registerTopicController = new RegisterTopicController();
        registerTopicController.setTopicDto(new TopicDTO());

        if (itemSelected instanceof StudyDTO studyDto) {
            registerTopicController.setStudy(studyDto);
        } else if (itemSelected instanceof TopicDTO topicDto) {
            registerTopicController.setTopicParent(topicDto);
        }

        RegisterTopicWindow registerTopicWindow = new RegisterTopicWindow(stage, registerTopicController);
        registerTopicWindow.showScreen();

        if (registerTopicController.getTopicDto().getId() != null &&
                registerTopicController.getTopicDto().getId() > 0) {
            refreshItemSelected(this.itemSelected);
            loadDataScreen();
        }
    }

    public void editTopic() {
        TopicDTO topicSelectedDto = listViewTopics.getSelectionModel().getSelectedItem();

        if (itemSelected == null || topicSelectedDto == null) {
            return;
        }

        RegisterTopicController registerTopicController = new RegisterTopicController();
        registerTopicController.setTopicDto(topicSelectedDto);

        RegisterTopicWindow registerTopicWindow = new RegisterTopicWindow(stage, registerTopicController);
        registerTopicWindow.showScreen();

        navigator.refreshItem(registerTopicController.getTopicDto());

        refreshItemSelected(this.itemSelected);

        loadDataScreen();
    }

    private void removeTopic() {
        TopicDTO topicSelectedDto = listViewTopics.getSelectionModel().getSelectedItem();

        if (itemSelected == null || topicSelectedDto == null) {
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

            refreshItemSelected(this.itemSelected);
            loadDataScreen();
        }
    }

    // Helpers -----------------------

    public void openItem(Object itemSelected) {
        if (!verifyDocumentEditingOrNotSave())
            return;

        refreshItemSelected(itemSelected);
        navigator.navigate(this.itemSelected);
        loadDataScreen();
    }

    private void refreshItemSelected(Object itemSelected) {
        if (itemSelected instanceof StudyDTO studyDto) {
            this.itemSelected = paneRightService.loadStudy(studyDto.getId());
        } else if (itemSelected instanceof TopicDTO topicDto) {
            this.itemSelected = paneRightService.loadTopic(topicDto.getId());
        }
    }

    public void loadDataScreen() {
        boolean canGoBack = navigator.canGoBack();
        boolean canGoForward = navigator.canGoForward();

        uiHelper.updateNavigationButtons(bttNavigationLeft, bttNavigationRight, canGoBack, canGoForward);
        uiHelper.updateTxtHierarchyPath(txtHierarchyPath, navigator.getBackStack());
        uiHelper.updateTitleItemMain(lblTitleMain, itemSelected);
        tabDocumentFactory.generateTabs(stage, tabPaneStudy, tabMain, tabAdd, itemSelected);
        uiHelper.updateListViewTopics(listTopicsObservable, listViewTopics, itemSelected);
    }

    private boolean verifyDocumentEditingOrNotSave() {
        EditorDocumentController editorDocumentController =
                tabDocumentFactory.verifyDocumentEditingOrNotSave(
                        tabPaneStudy,
                        tabMain,
                        tabAdd
                );

        if (editorDocumentController != null) {
            MessageConfirmController controller = new MessageConfirmController();
            controller.setConfirm(false);
            controller.setMsgUser(
                    "Existem Documentos com alterações não salvas.\n" +
                            "Deseja continuar mesmo assim?\n\n" +
                            "Documento editando: " + editorDocumentController.getDocumentDto().getTitle()
            );

            MessageConfirmWindow window = new MessageConfirmWindow(stage, controller);
            window.showScreen();

            return controller.getConfirm();
        }

        return true;
    }

}
