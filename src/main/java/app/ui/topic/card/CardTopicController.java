package app.ui.topic.card;

import app.application.topic.TopicDTO;
import app.ui.main.ScreenMainController;
import app.ui.main.ScreenMainState;
import app.ui.pane.right.PaneRightController;
import app.ui.pane.right.PaneRightNavigator;
import app.ui.pane.right.topics.PaneTopicsController;

public class CardTopicController {

    private final TopicDTO topic;
    private final ScreenMainState mainState;
    private final ScreenMainController screenMainController;
    private final PaneRightController paneRightController;
    private final PaneTopicsController paneTopicsController;
    private final PaneRightNavigator navigator;

    public CardTopicController(
            TopicDTO topic,
            ScreenMainState mainState,
            ScreenMainController screenMainController,
            PaneRightController paneRightController,
            PaneTopicsController paneTopicsController,
            PaneRightNavigator navigator) {

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
}
