package app.ui.roadmap;

import app.application.study.StudyDTO;
import app.application.study.StudyMapper;
import app.application.topic.TopicDTO;
import app.application.topic.TopicMapper;
import app.domain.study.Study;
import app.domain.topic.Topic;
import app.infra.study.StudyRepository;
import app.infra.topic.TopicRepository;

public class RoadMapService {

    private StudyRepository studyRepository = new StudyRepository();
    private TopicRepository topicRepository = new TopicRepository();

    public StudyDTO loadStudyFull(Long studyId) {
        Study studyFull = studyRepository.findStudyFull(studyId);
        return StudyMapper.toFullDTO(studyFull);
    }

    public TopicDTO loadTopicFull(Long topicId) {
        Topic topic = topicRepository.findTopicFull(topicId);
        return TopicMapper.toFullDTO(topic);
    }

}
