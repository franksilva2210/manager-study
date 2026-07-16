package app.ui.topic.card;

import app.application.topic.TopicDTO;
import app.ui.main.ScreenMainController;
import app.ui.main.ScreenMainState;
import app.ui.message.MessageConfirmController;
import app.ui.message.MessageConfirmWindow;
import app.ui.pane.right.PaneRightController;
import app.ui.pane.right.PaneRightNavigator;
import app.ui.pane.right.topics.PaneTopicsController;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class CardTopicController {

    private final Stage stage;
    private final TopicDTO topic;
    private final ScreenMainState mainState;
    private final ScreenMainController screenMainController;
    private final PaneRightController paneRightController;
    private final PaneTopicsController paneTopicsController;
    private final PaneRightNavigator navigator;

    private Parent root;
    private final CardTopicService service = new CardTopicService();

    public CardTopicController(
            Stage stage, TopicDTO topic,
            ScreenMainState mainState,
            ScreenMainController screenMainController,
            PaneRightController paneRightController,
            PaneTopicsController paneTopicsController,
            PaneRightNavigator navigator) {

        this.stage = stage;
        this.topic = topic;
        this.mainState = mainState;
        this.screenMainController = screenMainController;
        this.paneRightController = paneRightController;
        this.paneTopicsController = paneTopicsController;
        this.navigator = navigator;

        this.paneTopicsController.getCardSelection().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (topic.equals(newValue)) {
                root.setStyle("""
                        -fx-border-color: #3B82F6;
                        -fx-border-width: 2;
                        -fx-background-color: #EAF3FF;
                        -fx-border-radius: 3;
                        """);
            } else {
                root.setStyle("""
                        -fx-border-color: #cccccc;
                        -fx-border-width: 1;
                        -fx-background-color: transparent;
                        -fx-border-radius: 3;
                        """);
            }
        });
    }

    public TopicDTO getTopic() {
        return topic;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public ScreenMainState getMainState() {
        return mainState;
    }

    public CardTopicService getService() {
        return service;
    }

    public void select() {
        paneTopicsController.getCardSelection().select(topic);
    }

    public void openTopic() {
        if (!screenMainController.confirmChangeStudyOrTopic())
            return;

        mainState.setItemSelected(topic);
        mainState.refreshItemSelected();
        navigator.navigate(mainState.getItemSelected());
        paneRightController.mappingStacksNavigatorState();
        paneRightController.loadTabsDocument();
    }

    public void removeTopic() {
        MessageConfirmController controller = new MessageConfirmController();
        controller.setConfirm(false);
        controller.setMsgUser(
                "Deseja realmente remover o tópico selecionado: \n" +
                topic.getTitle().toUpperCase() + "?\n" +
                "todos os sub tópicos pertencentes a ele\n" +
                "também serão removidos!"
        );

        MessageConfirmWindow window = new MessageConfirmWindow(stage, controller);
        window.showScreen();

        if (controller.getConfirm()) {
            service.removeTopic(topic);
            navigator.removeItem(topic);
            mainState.refreshItemSelected();
        }
    }

    public void moveTopicToTopic(TopicDTO topicDragged, TopicDTO topicDestination) {

        if (topicDragged == null || topicDestination == null) {
            return;
        } else if (topicDragged.getId().equals(topicDestination.getId())) {
            return;
        }

        MessageConfirmController controller = new MessageConfirmController();
        controller.setConfirm(false);
        controller.setMsgUser(
                "Deseja realmente mover o tópico:\n" +
                topicDragged.getTitle().toUpperCase() + "\n" +
                "para: " + topicDestination.getTitle().toUpperCase() + "?\n" +
                "todos os sub tópicos também serão movidos"
        );

        MessageConfirmWindow window = new MessageConfirmWindow(stage, controller);
        window.showScreen();

        if (controller.getConfirm()) {
            service.moveTopicToTopic(topicDragged, topicDestination);
            mainState.refreshItemSelected();
        }
    }
}
