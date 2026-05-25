package app.ui.main;

import app.application.study.StudyDTO;
import app.application.study.StudyMapper;
import app.application.topic.TopicDTO;
import app.application.topic.TopicMapper;
import app.domain.study.Study;
import app.domain.text.Text;
import app.domain.topic.Topic;
import app.infra.study.StudyRepository;
import app.infra.text.TextRepository;
import app.infra.topic.TopicRepository;

import java.util.List;

public class ScreenMainService {

    private StudyRepository studyRepository = new StudyRepository();
    private TopicRepository topicRepository = new TopicRepository();
    private TextRepository textRepository = new TextRepository();

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
        List<Text> listTexts = textRepository.findByStudy(id);
        study.setListText(listTexts);
        return StudyMapper.toDTO(study);
    }

    public TopicDTO loadTopic(Long id) {
        Topic topic = topicRepository.findByIdWithTopics(id);
        List<Text> listTexts = textRepository.findByTopic(id);
        topic.setListText(listTexts);
        return TopicMapper.toDTO(topic);
    }

    public void removeStudy(StudyDTO studyDto) {
        studyRepository.delete(studyDto.getId());
    }

    public void removeTopic(TopicDTO topicDto) {
        topicRepository.delete(topicDto.getId());
    }

}
