package app.ui.topic.register;

import app.application.topic.TopicDTO;
import app.application.topic.TopicMapper;
import app.domain.topic.Topic;
import app.infra.topic.TopicRepository;

public class RegisterTopicService {

    private TopicRepository topicRepository = new TopicRepository();

    public TopicDTO save(TopicDTO topicDto) {
        Topic topic =
                TopicMapper.toEntity(topicDto);

        topic = topicRepository.save(topic);

        return TopicMapper.toSimpleDTO(topic);
    }

}
