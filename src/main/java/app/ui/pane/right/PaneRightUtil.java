package app.ui.pane.right;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import javafx.collections.ObservableList;

import java.util.*;

public class PaneRightUtil {

    public String buildPath(ObservableList<Object> backStack) {
        List<String> parts = new ArrayList<>();
        ListIterator<Object> iterator = backStack.listIterator(backStack.size());

        while (iterator.hasPrevious()) {

            Object item = iterator.previous();

            if (item instanceof StudyDTO study) {
                parts.add(study.getMatter());
            } else if (item instanceof TopicDTO topic) {
                parts.add(topic.getTitle());
            }
        }

        return String.join(" > ", parts);
    }
}
