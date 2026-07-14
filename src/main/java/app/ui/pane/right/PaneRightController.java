package app.ui.pane.right;

import app.application.document.DocumentDTO;
import app.ui.main.ScreenMainController;
import app.ui.main.ScreenMainState;
import app.ui.message.MessageInfoController;
import app.ui.message.MessageInfoWindow;
import app.ui.pane.left.PaneLeftController;
import app.ui.pane.right.topics.TopicsController;
import app.ui.pane.right.topics.TopicsPane;
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
                addNewDocument(new DocumentDTO());
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

    // Helpers -----------------------

    public void loadTabsDocument() {
        tabDocumentFactory.loadTabsDocument(stage, tabPaneStudy, tabMain, tabAdd, mainState.getItemSelected());
    }

    public void mappingStacksNavigatorState() {
        paneRightState.getBackStack().setAll(navigator.getBackStack());
        paneRightState.getForwardStack().setAll(navigator.getForwardStack());
    }

    private void loadPaneTopics() {
        TopicsController controller = new TopicsController(stage, mainState, screenMainController, this, navigator);
        TopicsPane pane = new TopicsPane(controller);
        pane.getRoot();
        paneTopics.getChildren().setAll(pane.getRoot());
    }

}
