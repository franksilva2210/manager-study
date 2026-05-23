package app.application.study;

import app.application.topic.TopicDTO;

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

    public void refreshItem(Object objectUpdated) {
        refreshDeque(navigationStack, objectUpdated);
        refreshDeque(forwardHistory, objectUpdated);
    }

    private void refreshDeque(
            Deque<Object> deque,
            Object objectUpdated
    ) {

        List<Object> updatedItems = new ArrayList<>();

        for (Object item : deque) {

            if (isSameItem(item, objectUpdated)) {

                updatedItems.add(objectUpdated);

            } else {

                updatedItems.add(item);
            }
        }

        deque.clear();

        for (Object item : updatedItems) {
            deque.addLast(item);
        }
    }

    private boolean isSameItem(
            Object currentItem,
            Object updatedItem
    ) {

        if (currentItem instanceof StudyDTO currentStudy &&
                updatedItem instanceof StudyDTO updatedStudy) {

            return currentStudy.getId()
                    .equals(updatedStudy.getId());
        }

        if (currentItem instanceof TopicDTO currentTopic &&
                updatedItem instanceof TopicDTO updatedTopic) {

            return currentTopic.getId()
                    .equals(updatedTopic.getId());
        }

        return false;
    }

}
