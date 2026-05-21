package app.ui.main;

import app.domain.study.Study;
import app.domain.topic.Topic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScreenMainHelper {

    public String getHierarchyPath(Object current) {
        List<String> paths = new ArrayList<>();

        if (current instanceof Study study) {
            paths.add(study.getMatter());
        } else if (current instanceof Topic topic) {
            Topic currentTopic = topic;

            while (currentTopic != null) {
                paths.add(currentTopic.getTitle());

                if (currentTopic.getStudy() != null) {
                    paths.add(currentTopic.getStudy().getMatter());
                    break;
                }

                currentTopic = currentTopic.getTopicParent();
            }
        }

        Collections.reverse(paths);

        return String.join(" > ", paths);
    }

}
