package app.application.study.dto;

import app.application.topic.dto.TopicDTO;

import java.util.*;

public class StudyNavigationService {

    private final Deque<Object> navigationStack =
            new ArrayDeque<>();

    private final Deque<Object> forwardHistory =
            new ArrayDeque<>();

    public void navigateTo(Object destination) {
        if (destination == null) {
            return;
        }

        if (destination instanceof StudyDTO) {
            navigationStack.clear();
            forwardHistory.clear();
        }

        Object current = navigationStack.peek();

        if (current != null &&
                current.equals(destination)) {
            return;
        }

        navigationStack.push(destination);

        forwardHistory.clear();
    }

    public Object back() {
        if (navigationStack.size() <= 1) {
            return navigationStack.peek();
        }

        Object current = navigationStack.pop();
        forwardHistory.push(current);

        Object previous = navigationStack.peek();

        return previous;
    }

    public Object forward() {
        if (forwardHistory.isEmpty()) {
            return navigationStack.peek();
        }

        Object next = forwardHistory.pop();

        navigationStack.push(next);

        return next;
    }

    public boolean canGoBack() {
        return navigationStack.size() > 1;
    }

    public boolean canGoForward() {
        return !forwardHistory.isEmpty();
    }

    public String getHierarchyPath() {

        List<String> hierarchyPath =
                new ArrayList<>();

        Iterator<Object> iterator =
                navigationStack.descendingIterator();

        while (iterator.hasNext()) {

            Object item = iterator.next();

            if (item instanceof StudyDTO studyDto) {

                hierarchyPath.add(
                        studyDto.getMatter()
                );

            } else if (item instanceof TopicDTO topicDto) {

                hierarchyPath.add(
                        topicDto.getTitle()
                );
            }
        }

        return String.join(" > ", hierarchyPath);
    }

    public Object getCurrent() {
        return navigationStack.peek();
    }

    public void clear() {
        navigationStack.clear();
        forwardHistory.clear();
    }

}
