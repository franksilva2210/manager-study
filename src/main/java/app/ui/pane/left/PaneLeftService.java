package app.ui.pane.left;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.application.topic.TopicMapper;
import app.domain.topic.Topic;
import app.infra.study.StudyRepository;
import app.infra.topic.TopicRepository;

import java.util.List;

public class PaneLeftService {

    private final StudyRepository studyRepository = new StudyRepository();
    private final TopicRepository topicRepository = new TopicRepository();

    public List<StudyDTO> consultAllStudyDto() {
        try {
            List<StudyDTO> listStudyDto = studyRepository.findAllDto();
            return listStudyDto;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar estudos.", e);
        }
    }

    public TopicDTO loadSimpleTopic(Long id) {
        Topic topic = topicRepository.findById(id);
        return TopicMapper.toSimpleDTO(topic);
    }

    public void moveTopicToStudy(TopicDTO topicDragged, StudyDTO studyDestination) {
        if (topicDragged.getStudyId().equals(studyDestination.getId())) {
            return;
        }
        topicDragged.setStudyId(studyDestination.getId());
        topicRepository.updateStudyParent(topicDragged);
    }

    public void removeStudy(StudyDTO studyDto) {
        studyRepository.delete(studyDto.getId());
    }

}
