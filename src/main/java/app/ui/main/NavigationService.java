package app.ui.main;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;

import java.util.*;

public class NavigationService {

    private final Deque<Object> backStack = new ArrayDeque<>();
    private final Deque<Object> forwardStack = new ArrayDeque<>();

    public void navigate(Object object) {
        if (object == null) {
            return;
        }

        if (object instanceof StudyDTO) {
            backStack.clear();
            forwardStack.clear();

            backStack.push(object);

            return;
        }

        Object current = backStack.peek();

        if (isSame(current, object)) {
            return;
        }

        backStack.push(object);

        forwardStack.clear();
    }

    public Object back() {

        if (backStack.size() <= 1) {
            return backStack.peek();
        }

        forwardStack.push(backStack.pop());

        return backStack.peek();
    }

    public Object forward() {

        if (forwardStack.isEmpty()) {
            return backStack.peek();
        }

        Object next = forwardStack.pop();

        backStack.push(next);

        return next;
    }

    public Object current() {
        return backStack.peek();
    }

    public Deque<Object> getBackStack() {
        return backStack;
    }

    public boolean canGoBack() {
        return backStack.size() > 1;
    }

    public boolean canGoForward() {
        return !forwardStack.isEmpty();
    }

    private boolean isSame(Object obj1, Object obj2) {
        if (obj1 == null || obj2 == null) {
            return false;
        }

        if (obj1 instanceof StudyDTO currentStudy && obj2 instanceof StudyDTO nextStudy) {
            return currentStudy.getId().equals(nextStudy.getId());
        }

        if (obj1 instanceof TopicDTO currentTopic && obj2 instanceof TopicDTO nextTopic) {
            return currentTopic.getId().equals(nextTopic.getId());
        }

        return false;
    }

}
