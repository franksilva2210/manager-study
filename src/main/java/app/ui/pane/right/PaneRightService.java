package app.ui.pane.right;

import app.application.topic.TopicDTO;
import app.infra.topic.TopicRepository;

public class PaneRightService {

    private TopicRepository topicRepository = new TopicRepository();

    public void removeTopic(TopicDTO topicDto) {
        topicRepository.delete(topicDto.getId());
    }
}
