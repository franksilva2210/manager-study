package app.ui.pane.right;

import app.application.document.DocumentDTO;
import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.ui.document.edit.EditorDocumentController;
import app.ui.main.ScreenMainController;
import app.ui.main.ScreenMainState;
import app.ui.message.MessageInfoController;
import app.ui.message.MessageInfoWindow;
import app.ui.pane.left.PaneLeftController;
import app.ui.pane.right.topics.PaneTopicsController;
import app.ui.pane.right.topics.PaneTopics;
import app.ui.roadmap.RoadMapController;
import app.ui.roadmap.RoadMapWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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
    private VBox paneTopics;

    @FXML
    private Tab tabAdd;

    private final Stage stage;
    private final ScreenMainState mainState;
    private final ScreenMainController screenMainController;
    private final PaneRightNavigator navigator;

    private final PaneRightState paneRightState = new PaneRightState();
    private final TabDocumentFactory tabDocumentFactory = new TabDocumentFactory();
    private final PaneRightUIHelper uiHelper = new PaneRightUIHelper();
    private PaneLeftController paneLeftController;

    public PaneRightController(
            Stage stage,
            ScreenMainState mainState,
            ScreenMainController screenMainController,
            PaneRightNavigator navigator) {

        this.stage = stage;
        this.mainState = mainState;
        this.screenMainController = screenMainController;
        this.navigator = navigator;
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

    public TabPane getTabPaneStudy() {
        return tabPaneStudy;
    }

    public Tab getTabMain() {
        return tabMain;
    }

    public Tab getTabAdd() {
        return tabAdd;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bttNavigationLeft.setOnAction(event -> {
            navigateBack();
        });

        bttNavigationRight.setOnAction(event -> {
            navigateForward();
        });

        bttRoadMap.setOnAction(event -> {
            showRoadMap();
        });

        tabAdd.setOnSelectionChanged(event -> {
            if (tabAdd.isSelected()) {
                addNewDocument();
            }
        });

        loadPaneTopics();

        mappingStacksNavigatorState();

        PaneRightUIBinder.bind(
                this,
                mainState,
                paneRightState
        );

    }

    // Navegação ------------------------------

    private void navigateBack() {
        if (!screenMainController.confirmChangeStudyOrTopic())
            return;

        Object itemBack = navigator.back();
        mainState.setItemSelected(itemBack);
        mainState.refreshItemSelected();
        mappingStacksNavigatorState();
        loadTabsDocument();
    }

    private void navigateForward() {
        if (!screenMainController.confirmChangeStudyOrTopic())
            return;

        Object itemForward = navigator.forward();
        mainState.setItemSelected(itemForward);
        mainState.refreshItemSelected();
        mappingStacksNavigatorState();
        loadTabsDocument();
    }

    // Tabs -----------------------------

    private void addNewDocument() {
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

            int indexTabs = tabPaneStudy.getTabs().indexOf(tabAdd);

            DocumentDTO documentDto = new DocumentDTO();
            documentDto.setTitle("Texto " + indexTabs);

            Tab newTab = tabDocumentFactory.createTabDocument(
                    stage,
                    tabPaneStudy,
                    mainState.getItemSelected(),
                    documentDto
            );

            tabPaneStudy.getTabs().add(indexTabs, newTab);
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

    // Helpers -----------------------

    public void loadTabsDocument() {
        tabPaneStudy.getTabs().removeIf(tab -> tab != tabMain && tab != tabAdd);

        if (mainState.getItemSelected() == null) {
            return;
        }

        int indexTabs = tabPaneStudy.getTabs().indexOf(tabAdd);

        if (mainState.getItemSelected() instanceof StudyDTO studyDto) {
            for (DocumentDTO dto : studyDto.getListDocumentsDto()) {
                Tab tab = tabDocumentFactory.createTabDocument(stage, tabPaneStudy, mainState.getItemSelected(), dto);
                tabPaneStudy.getTabs().add(indexTabs, tab);
            }
        } else if(mainState.getItemSelected() instanceof TopicDTO topicDto) {
            for (DocumentDTO dto : topicDto.getListDocumentsDto()) {
                Tab tabCreate = tabDocumentFactory.createTabDocument(stage, tabPaneStudy, mainState.getItemSelected(), dto);
                tabPaneStudy.getTabs().add(indexTabs, tabCreate);
            }
        }
    }

    public EditorDocumentController verifyDocumentEditingOrNotSave() {
        return uiHelper.verifyDocumentEditingOrNotSave(tabPaneStudy, tabMain, tabAdd);
    }

    public void mappingStacksNavigatorState() {
        paneRightState.getBackStack().setAll(navigator.getBackStack());
        paneRightState.getForwardStack().setAll(navigator.getForwardStack());
    }

    private void loadPaneTopics() {
        PaneTopicsController controller = new PaneTopicsController(stage, mainState, screenMainController, this, navigator);
        PaneTopics pane = new PaneTopics(controller);
        pane.getRoot();
        paneTopics.getChildren().setAll(pane.getRoot());
    }

}
