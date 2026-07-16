package app.ui.topic.card;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.application.topic.TopicMapper;
import app.domain.topic.Topic;
import app.infra.topic.TopicRepository;

public class CardTopicService {

    private TopicRepository topicRepository = new TopicRepository();

    public TopicDTO loadSimpleTopic(Long id) {
        Topic topic = topicRepository.findById(id);
        return TopicMapper.toSimpleDTO(topic);
    }

    public void moveTopicToTopic(
            TopicDTO topicDragged,
            TopicDTO topicDestination) {

        topicDragged.setTopicParentId(topicDestination.getId());

        topicRepository.updateTopicParent(topicDragged);
    }

    public void removeTopic(TopicDTO topicDto) {
        topicRepository.delete(topicDto.getId());
    }
}
