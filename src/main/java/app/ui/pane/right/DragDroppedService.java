package app.ui.pane.right;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.application.topic.TopicMapper;
import app.domain.topic.Topic;
import app.infra.topic.TopicRepository;

public class DragDroppedService {

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

        topicDragged.setTopicParentId(topicDestination.getId());

        topicRepository.updateTopicParent(topicDragged);
    }

    public void moveTopicToStudy(
            TopicDTO topicDragged,
            StudyDTO studyDestination) {

        if (topicDragged == null || studyDestination == null) {
            return;
        }

        topicDragged.setStudyId(studyDestination.getId());

        topicRepository.updateStudyParent(topicDragged);
    }

}
