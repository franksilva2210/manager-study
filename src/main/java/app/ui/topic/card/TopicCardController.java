package app.ui.topic.card;

import app.application.topic.TopicDTO;
import app.ui.main.ScreenMainState;

public class TopicCardController {

    private final TopicDTO topic;
    private final ScreenMainState mainState;

    public TopicCardController(
            TopicDTO topic,
            ScreenMainState mainState) {

        this.topic = topic;
        this.mainState = mainState;
    }

    public TopicDTO getTopic() {
        return topic;
    }

    public void openTopic() {
        // lógica
    }
}
