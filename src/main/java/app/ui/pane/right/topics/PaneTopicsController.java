package app.ui.pane.right.topics;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.ui.main.ScreenMainController;
import app.ui.main.ScreenMainState;
import app.ui.message.MessageConfirmController;
import app.ui.message.MessageConfirmWindow;
import app.ui.pane.right.*;
import app.ui.topic.card.TopicCard;
import app.ui.topic.card.TopicCardController;
import app.ui.topic.register.RegisterTopicController;
import app.ui.topic.register.RegisterTopicWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PaneTopicsController implements Initializable {

    @FXML
    private Button bttAddTopic;

    @FXML
    private Button bttEditTopic;

    @FXML
    private Button bttRemoveTopic;

    @FXML
    private VBox paneCardsTopic;

    @FXML
    private TextField txtSearchTopics;

    private final Stage stage;
    private final ScreenMainState mainState;
    private final ScreenMainController screenMainController;
    private final PaneRightController paneRightController;
    private final PaneRightNavigator navigator;

    private final ObservableList<TopicDTO> listTopics = FXCollections.observableArrayList();
    private final FilteredList<TopicDTO> filteredTopics = new FilteredList<>(listTopics);
    private final PaneRightService paneRightService = new PaneRightService();
    private final GridPane gridTopics = new GridPane();
    private static final int MAX_COLUMNS = 2;

    public PaneTopicsController(
            Stage stage,
            ScreenMainState mainState,
            ScreenMainController screenMainController,
            PaneRightController paneRightController,
            PaneRightNavigator navigator) {

        this.stage = stage;
        this.mainState = mainState;
        this.screenMainController = screenMainController;
        this.paneRightController = paneRightController;
        this.navigator = navigator;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txtSearchTopics.setOnAction(event -> {
            searchTopic();
        });

        txtSearchTopics.textProperty().addListener((obs, oldValue, newValue) -> {
            searchTopic();
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

        paneCardsTopic.getChildren().setAll(gridTopics);

        mainState.itemSelectedProperty().addListener((obs, oldValue, newValue) -> {
            loadListTopics();
        });
    }

    private void loadListTopics() {

        listTopics.clear();

        if (mainState.getItemSelected() instanceof StudyDTO studyDto) {
            listTopics.addAll(studyDto.getListTopicsDto());
        } else if(mainState.getItemSelected() instanceof TopicDTO topicDto) {
            listTopics.addAll(topicDto.getListTopicsDto());
        }

        gridTopics.getChildren().clear();

        int row = 0;
        int column = 0;

        for (TopicDTO topic : listTopics) {

            TopicCardController controller = new TopicCardController(topic, mainState);
            TopicCard card = new TopicCard(controller);

            GridPane.setMargin(card.getRoot(), new Insets(0, 10, 10, 0));

            gridTopics.add(card.getRoot(), column, row);

            column++;

            if (column == MAX_COLUMNS) {
                column = 0;
                row++;
            }
        }
    }

    private void openTopic() {
        if (!screenMainController.confirmChangeStudyOrTopic())
            return;

        TopicDTO topicSelected = null;
        if (topicSelected != null) {
            mainState.setItemSelected(topicSelected);
            mainState.refreshItemSelected();
            navigator.navigate(mainState.getItemSelected());
            paneRightController.mappingStacksNavigatorState();
            paneRightController.loadTabsDocument();
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
        }
    }

    public void editTopic() {
        TopicDTO topicSelectedDto = null;

        if (mainState.getItemSelected() == null || topicSelectedDto == null) {
            return;
        }

        RegisterTopicController registerTopicController = new RegisterTopicController();
        registerTopicController.setTopicDto(topicSelectedDto);

        RegisterTopicWindow registerTopicWindow = new RegisterTopicWindow(stage, registerTopicController);
        registerTopicWindow.showScreen();

        navigator.refreshItem(registerTopicController.getTopicDto());
        mainState.refreshItemSelected();
    }

    private void removeTopic() {
        TopicDTO topicSelectedDto = null;

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
        }
    }

}
