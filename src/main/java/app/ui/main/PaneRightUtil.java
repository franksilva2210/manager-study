package app.ui.main;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class PaneRightUtil {

    public String buildPath(Deque<Object> backStack) {

        List<String> parts = new ArrayList<>();

        Iterator<Object> iterator = backStack.descendingIterator();

        while (iterator.hasNext()) {
            Object item = iterator.next();

            if (item instanceof StudyDTO study) {
                parts.add(study.getMatter());
            } else if (item instanceof TopicDTO topic) {
                parts.add(topic.getTitle());
            }
        }

        return String.join(" > ", parts);
    }
}
