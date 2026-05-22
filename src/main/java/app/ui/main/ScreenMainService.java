package app.ui.main;

import app.application.dto.StudyDTO;
import app.application.dto.TopicDTO;
import app.domain.study.Study;
import app.domain.topic.Topic;
import app.infra.study.StudyRepository;
import app.infra.text.TextRepository;
import app.infra.topic.TopicRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScreenMainService {

    private StudyRepository studyRepository = new StudyRepository();
    private TopicRepository topicRepository = new TopicRepository();
    private TextRepository textRepository = new TextRepository();

    public List<StudyDTO> consultAllStudyDto() {

        try {
            List<StudyDTO> listStudyDto = studyRepository.findAllDto();
            List<TopicDTO> listTopicDto = topicRepository.findAllRootTopicsDto();

            Map<Long, List<TopicDTO>> mapTopicsByStudy =
                    listTopicDto.stream()
                            .collect(Collectors.groupingBy(
                                    TopicDTO::getStudyId
                            ));

            for (StudyDTO study : listStudyDto) {
                study.setListTopics(
                        mapTopicsByStudy.getOrDefault(
                                study.getId(),
                                new ArrayList<>())
                );
            }

            return listStudyDto;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao buscar estudos.",
                    e
            );
        }
    }

    public List<Topic> findByStudy(Study study) {
        return topicRepository.findByStudy(study.getId());
    }

    public Topic loadTopic(Topic topic) {
        topic = topicRepository.findById(topic.getId());
        List<Topic> listTopics = topicRepository.findByTopicParent(topic.getId());
        topic.setListTopics(listTopics);
        return topic;
    }

}
