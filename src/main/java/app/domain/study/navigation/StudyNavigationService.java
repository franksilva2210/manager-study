package app.domain.study.navigation;

import app.domain.topic.Topic;

import java.util.ArrayDeque;
import java.util.Deque;

public class StudyNavigationService {

    private Deque<Object> history = new ArrayDeque<>();

    public Object back(Object current) {
        Object previous = null;

        if (current instanceof Topic topic) {
            if (topic.getTopicParent() != null) {
                previous = topic.getTopicParent();
            } else if (topic.getStudy() != null) {
                previous = topic.getStudy();
            }
        }

        if (previous != null) {
            history.push(current);
        }

        return previous;
    }

    public Object forward(Object current) {
        if (history.isEmpty()) {
            return current;
        }
        Object next = history.pop();
        return next;
    }

    public boolean canGoBack(Object current) {
        if (current == null || !(current instanceof Topic topic)) {
            return false;
        }
        return topic.getTopicParent() != null || topic.getStudy() != null;
    }

    public boolean canGoForward() {
        return !history.isEmpty();
    }

    public Deque<Object> getHistory() {
        return history;
    }

}
