package app.pane.study;

import app.pane.study.topic.register.Topic;
import app.study.register.Study;
import javafx.collections.ObservableList;

public class StudyService {

    public void updateListTopics(Study study, ObservableList<String> listTopics) {
        listTopics.clear();
        for(Topic topic : study.getListTopics()) {
            listTopics.add(topic.getTitle());
        }
    }
}
