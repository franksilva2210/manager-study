package app.ui.topic.register;

import app.domain.topic.Topic;
import app.infra.topic.TopicRepository;

public class RegisterTopicService {

    private TopicRepository topicRepository = new TopicRepository();

    public Topic save(Topic topic) {
        return topicRepository.save(topic);
    }

}
