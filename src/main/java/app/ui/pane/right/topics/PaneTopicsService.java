package app.ui.pane.right.topics;

import app.application.topic.TopicDTO;
import app.infra.topic.TopicRepository;

public class PaneTopicsService {

    private TopicRepository topicRepository = new TopicRepository();

    public void removeTopic(TopicDTO topicDto) {
        topicRepository.delete(topicDto.getId());
    }
}
