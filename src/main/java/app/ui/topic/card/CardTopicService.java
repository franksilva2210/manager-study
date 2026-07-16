package app.ui.topic.card;

import app.application.topic.TopicDTO;
import app.infra.topic.TopicRepository;

public class CardTopicService {

    private TopicRepository topicRepository = new TopicRepository();

    public void removeTopic(TopicDTO topicDto) {
        topicRepository.delete(topicDto.getId());
    }
}
