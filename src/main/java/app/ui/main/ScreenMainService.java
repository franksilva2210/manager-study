package app.ui.main;

import app.application.study.StudyDTO;
import app.application.study.StudyMapper;
import app.application.topic.TopicDTO;
import app.application.topic.TopicMapper;
import app.domain.study.Study;
import app.domain.document.Document;
import app.domain.topic.Topic;
import app.infra.study.StudyRepository;
import app.infra.document.DocumentRepository;
import app.infra.topic.TopicRepository;

import java.util.List;

public class ScreenMainService {

    private StudyRepository studyRepository = new StudyRepository();
    private TopicRepository topicRepository = new TopicRepository();
    private DocumentRepository documentRepository = new DocumentRepository();

    public List<StudyDTO> consultAllStudyDto() {
        try {
            List<StudyDTO> listStudyDto = studyRepository.findAllDto();
            return listStudyDto;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar estudos.", e);
        }
    }

    public StudyDTO loadStudy(Long id) {
        Study study = studyRepository.findByIdWithTopics(id);
        List<Document> listDocuments = documentRepository.findByStudy(id);
        study.setListText(listDocuments);
        return StudyMapper.toDTO(study);
    }

    public TopicDTO loadTopic(Long id) {
        Topic topic = topicRepository.findByIdWithTopics(id);
        List<Document> listDocuments = documentRepository.findByTopic(id);
        topic.setListDocuments(listDocuments);
        return TopicMapper.toDTO(topic);
    }

    public void removeStudy(StudyDTO studyDto) {
        studyRepository.delete(studyDto.getId());
    }

    public void removeTopic(TopicDTO topicDto) {
        topicRepository.delete(topicDto.getId());
    }

}
