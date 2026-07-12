package app.ui.pane.right;

import app.application.topic.TopicDTO;
import app.application.topic.TopicMapper;
import app.domain.topic.Topic;
import app.infra.topic.TopicRepository;

public class ConfigDragDroppedListViewService {

    private TopicRepository topicRepository = new TopicRepository();

    public TopicDTO loadSimpleTopic(Long id) {
        Topic topic = topicRepository.findById(id);
        return TopicMapper.toSimpleDTO(topic);
    }

    public void moveTopicToTopic(
            TopicDTO topicDragged,
            TopicDTO topicDestination) {

        if (topicDragged == null || topicDestination == null) {
            return;
        } else if (topicDragged.getId().equals(topicDestination.getId())) {
            return;
        }

        if (topicDragged.getStudyId() != null) {
            topicDragged.setStudyId(null);
        }

        topicDragged.setTopicParentId(topicDestination.getId());

        topicRepository.moveTopic(topicDragged);
    }

}
