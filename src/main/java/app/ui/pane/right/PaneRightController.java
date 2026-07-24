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
    private Label lblQtdDoc;

    @FXML
    private Label lblQtdTopic;

    @FXML
    private VBox paneTopics;

    @FXML
    private Tab tabAdd;

    private final Stage stage;
    private final ScreenMainState screenMainState;
    private final ScreenMainController screenMainController;

    private final TabDocumentFactory tabDocumentFactory = new TabDocumentFactory();
    private final PaneRightUIHelper uiHelper = new PaneRightUIHelper();
    private PaneLeftController paneLeftController;

    public PaneRightController(
            Stage stage,
            ScreenMainState screenMainState,
            ScreenMainController screenMainController) {

        this.stage = stage;
        this.screenMainState = screenMainState;
        this.screenMainController = screenMainController;
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

    public Label getLblQtdDoc() {
        return lblQtdDoc;
    }

    public Label getLblQtdTopic() {
        return lblQtdTopic;
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

        uiHelper.configurateTooltip(tabAdd);

        PaneRightUIBinder.bind(this, screenMainState);
    }

    private void navigateBack() {
        if (!screenMainController.confirmChangeStudyOrTopic())
            return;

        screenMainController.navigateBack();
        loadTabsDocument();
    }

    private void navigateForward() {
        if (!screenMainController.confirmChangeStudyOrTopic())
            return;

        screenMainController.navigateForward();
        loadTabsDocument();
    }

    private void addNewDocument() {
        if (screenMainState.getItemSelected() == null) {
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
                    screenMainState,
                    documentDto
            );

            tabPaneStudy.getTabs().add(indexTabs, newTab);
            tabPaneStudy.getSelectionModel().select(newTab);
        }
    }

    private void showRoadMap() {
        RoadMapController roadMapController = new RoadMapController();
        roadMapController.setItemSelected(screenMainState.getItemSelected());
        RoadMapWindow roadMapWindow	= new RoadMapWindow(stage, roadMapController);
        roadMapWindow.showScreen();
    }

    // Helpers -----------------------

    public void loadTabsDocument() {
        tabPaneStudy.getTabs().removeIf(tab -> tab != tabMain && tab != tabAdd);

        if (screenMainState.getItemSelected() == null) {
            return;
        }

        if (screenMainState.getItemSelected() instanceof StudyDTO studyDto) {
            for (DocumentDTO dto : studyDto.getListDocumentsDto()) {
                Tab tabCreate = tabDocumentFactory.createTabDocument(stage, tabPaneStudy, screenMainState, dto);
                tabPaneStudy.getTabs().add(
                        tabPaneStudy.getTabs().indexOf(tabAdd),
                        tabCreate
                );
            }
        } else if(screenMainState.getItemSelected() instanceof TopicDTO topicDto) {
            for (DocumentDTO dto : topicDto.getListDocumentsDto()) {
                Tab tabCreate = tabDocumentFactory.createTabDocument(stage, tabPaneStudy, screenMainState, dto);
                tabPaneStudy.getTabs().add(
                        tabPaneStudy.getTabs().indexOf(tabAdd),
                        tabCreate
                );
            }
        }
    }

    public EditorDocumentController verifyDocumentEditingOrNotSave() {
        return uiHelper.verifyDocumentEditingOrNotSave(tabPaneStudy, tabMain, tabAdd);
    }

    public void loadPaneTopics() {
        PaneTopicsController controller =
                new PaneTopicsController(
                        stage,
                        screenMainState,
                        screenMainController,
                        paneLeftController,
                        this
                );

        PaneTopics pane = new PaneTopics(controller);
        pane.getRoot();
        paneTopics.getChildren().setAll(pane.getRoot());
    }

}
