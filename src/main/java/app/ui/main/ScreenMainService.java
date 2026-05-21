package app.ui.main;

import app.domain.study.Study;
import app.domain.text.Text;
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

    public List<Study> consultStudyAll() {
        try {
            List<Study> listStudy = studyRepository.findAll();
            List<Topic> listTopic = topicRepository.findAllRootTopics();
            List<Text> listText = textRepository.findAllStudyTexts();

            for (Topic topic : listTopic) {
                List<Topic> listSubTopics = topicRepository.findByTopicParent(topic.getId());
                topic.setListTopics(listSubTopics);
            }

            Map<Long, List<Topic>> mapTopicsByStudy = listTopic.stream()
                    .collect(Collectors.groupingBy(
                            t -> t.getStudy().getId()
                    ));

            Map<Long, List<Text>> mapTextsByStudy = listText.stream()
                    .collect(Collectors.groupingBy(
                            t -> t.getStudy().getId()
                    ));

            for (Study study : listStudy) {
                study.setListTopics(
                        mapTopicsByStudy.getOrDefault(study.getId(), new ArrayList<>())
                );

                study.setListText(
                        mapTextsByStudy.getOrDefault(study.getId(), new ArrayList<>())
                );
            }

            return listStudy;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar estudos. ", e);
        }
    }

    public void consultListTopicByStudy(Study study) {
        List<Topic> listTopic = topicRepository.findByStudy(study.getId());
        study.setListTopics(listTopic);
    }

    public void consultListTopicByTopicParent(Topic topic) {
        List<Topic> listTopic = topicRepository.findByTopicParent(topic.getId());
        topic.setListTopics(listTopic);
    }

}
