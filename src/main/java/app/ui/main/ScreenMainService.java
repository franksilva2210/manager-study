package app.ui.main;

import app.application.study.StudyDTO;
import app.application.study.StudyMapper;
import app.application.topic.TopicDTO;
import app.application.topic.TopicMapper;
import app.domain.document.Document;
import app.domain.study.Study;
import app.domain.topic.Topic;
import app.infra.document.DocumentRepository;
import app.infra.study.StudyRepository;
import app.infra.topic.TopicRepository;

import java.util.List;
import java.util.Optional;

public class ScreenMainService {

    private StudyRepository studyRepository = new StudyRepository();
    private TopicRepository topicRepository = new TopicRepository();
    private DocumentRepository documentRepository = new DocumentRepository();

    public StudyDTO loadStudy(Long id) {
        Optional<Study> optional = studyRepository.findByIdWithTopics(id);

        if (!optional.isPresent()) return null;

        Study study = optional.get();
        List<Document> listDocuments = documentRepository.findByStudy(id);
        study.setListDocuments(listDocuments);
        return StudyMapper.toDTO(study);
    }

    public TopicDTO loadTopic(Long id) {
        Optional<Topic> optional = topicRepository.findByIdWithTopics(id);

        if (!optional.isPresent()) return null;

        Topic topic = optional.get();
        List<Document> listDocuments = documentRepository.findByTopic(id);
        topic.setListDocuments(listDocuments);
        return TopicMapper.toDTO(topic);
    }

}
