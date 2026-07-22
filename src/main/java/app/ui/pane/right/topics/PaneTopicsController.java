package app.ui.pane.right.topics;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.ui.main.Breadcrumb;
import app.ui.main.ScreenMainController;
import app.ui.main.ScreenMainState;
import app.ui.message.MessageConfirmController;
import app.ui.message.MessageConfirmWindow;
import app.ui.pane.left.PaneLeftController;
import app.ui.pane.right.*;
import app.ui.topic.card.CardTopic;
import app.ui.topic.card.CardTopicController;
import app.ui.topic.register.RegisterTopicController;
import app.ui.topic.register.RegisterTopicWindow;
import app.ui.util.ScrollPaneUtils;
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
    private Button bttRemoveTopic;

    @FXML
    private ScrollPane scrollPaneCards;

    @FXML
    private VBox paneCardsTopic;

    @FXML
    private TextField txtSearchTopics;

    private final Stage stage;
    private final ScreenMainState mainState;
    private final ScreenMainController screenMainController;
    private final PaneLeftController paneLeftController;
    private final PaneRightController paneRightController;
    private final Breadcrumb breadCrumb;

    private final ObservableList<TopicDTO> listTopics = FXCollections.observableArrayList();
    private final FilteredList<TopicDTO> listTopicsFiltered = new FilteredList<>(listTopics);
    private final PaneTopicsService paneTopicsService = new PaneTopicsService();
    private final GridPane gridTopics = new GridPane();
    private static final int MAX_COLUMNS = 3;
    private final CardSelectionModel<TopicDTO> cardSelection = new CardSelectionModel<>();

    public PaneTopicsController(
            Stage stage,
            ScreenMainState mainState,
            ScreenMainController screenMainController,
            PaneLeftController paneLeftController,
            PaneRightController paneRightController,
            Breadcrumb breadCrumb) {

        this.stage = stage;
        this.mainState = mainState;
        this.screenMainController = screenMainController;
        this.paneLeftController = paneLeftController;
        this.paneRightController = paneRightController;
        this.breadCrumb = breadCrumb;
    }

    public CardSelectionModel<TopicDTO> getCardSelection() {
        return cardSelection;
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

        bttRemoveTopic.setOnAction(event -> {
            removeTopic();
        });

        gridTopics.setOnMouseClicked(event -> {
            if (event.getTarget() == gridTopics) {
                cardSelection.clearSelection();
            }
        });

        paneCardsTopic.setOnMouseClicked(event -> {
            if (event.getTarget() == paneCardsTopic) {
                cardSelection.clearSelection();
            }
        });

        paneCardsTopic.getChildren().setAll(gridTopics);

        configScrollPane();

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

        for (TopicDTO topic : listTopicsFiltered) {

            CardTopicController controller =
                    new CardTopicController(
                            stage,
                            topic,
                            mainState,
                            screenMainController,
                            paneLeftController,
                            paneRightController,
                            this,
                            breadCrumb
                    );

            CardTopic card = new CardTopic(controller);

            controller.setRoot(card.getRoot());

            if (column == MAX_COLUMNS - 1) {
                GridPane.setMargin(card.getRoot(), new Insets(0, 0, 10, 0));
            } else {
                GridPane.setMargin(card.getRoot(), new Insets(0, 10, 10, 0));
            }

            gridTopics.add(card.getRoot(), column, row);

            column++;

            if (column == MAX_COLUMNS) {
                column = 0;
                row++;
            }
        }
    }

    private void searchTopic() {
        String search = txtSearchTopics.getText().trim().toLowerCase();

        listTopicsFiltered.setPredicate(topic ->
                search.isBlank()
                        ||
                        topic.getTitle().toLowerCase().indexOf(search.toLowerCase()) != -1
        );

        loadListTopics();
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

    private void removeTopic() {
        TopicDTO topicSelectedDto = cardSelection.getSelectedItem();

        if (mainState.getItemSelected() == null || topicSelectedDto == null) {
            return;
        }

        MessageConfirmController controller = new MessageConfirmController();
        controller.setConfirm(false);
        controller.setMsgUser(
                "Deseja realmente remover o tópico selecionado: \n" +
                topicSelectedDto.getTitle() + "?\n" +
                "todos os subtópicos pertencentes a ele\n" +
                "também serão removidos!"
        );

        MessageConfirmWindow window = new MessageConfirmWindow(stage, controller);
        window.showScreen();

        if (controller.getConfirm()) {
            paneTopicsService.removeTopic(topicSelectedDto);
            breadCrumb.removeItem(topicSelectedDto);
            mainState.refreshItemSelected();
        }
    }

    private void configScrollPane() {
        ScrollPaneUtils scrollPaneUtils = new ScrollPaneUtils();
        scrollPaneUtils.configure(scrollPaneCards);
    }

}
