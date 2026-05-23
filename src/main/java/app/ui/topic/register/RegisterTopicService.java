package app.ui.topic.register;

import app.application.topic.TopicDTO;
import app.application.topic.TopicMapper;
import app.domain.topic.Topic;
import app.infra.topic.TopicRepository;

public class RegisterTopicService {

    private TopicRepository topicRepository = new TopicRepository();

    public TopicDTO save(TopicDTO topicDto) {

        if (topicDto.getId() != null && topicDto.getId() > 0) {
            return TopicMapper.toSimpleDTO(topicRepository.update(topicDto));
        } else {
            Topic topic = TopicMapper.toEntity(topicDto);
            return TopicMapper.toSimpleDTO(topicRepository.save(topic));
        }

    }

}
