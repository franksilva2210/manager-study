package app.ui.topic.card;

import app.application.topic.TopicDTO;
import app.ui.main.ScreenMainController;
import app.ui.main.ScreenMainState;
import app.ui.message.MessageConfirmController;
import app.ui.message.MessageConfirmWindow;
import app.ui.pane.right.PaneRightController;
import app.ui.pane.right.PaneRightNavigator;
import app.ui.pane.right.topics.PaneTopicsController;
import javafx.stage.Stage;

public class CardTopicController {

    private final Stage stage;
    private final TopicDTO topic;
    private final ScreenMainState mainState;
    private final ScreenMainController screenMainController;
    private final PaneRightController paneRightController;
    private final PaneTopicsController paneTopicsController;
    private final PaneRightNavigator navigator;

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
    }

    public TopicDTO getTopic() {
        return topic;
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
                "Deseja realmente remover o tópico: \n" +
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
}
