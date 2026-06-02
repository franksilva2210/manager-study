package app.ui.main;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;

import java.util.*;

public class PaneRightNavigator {

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

    public Deque<Object> getForwardStack() {
        return forwardStack;
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

    public void refreshItem(Object objectUpdated) {

        if (objectUpdated == null) {
            return;
        }

        refreshStack(backStack, objectUpdated);
        refreshStack(forwardStack, objectUpdated);
    }

    private void refreshStack(Deque<Object> stack, Object objectUpdated) {

        List<Object> updatedItems = new ArrayList<>();

        for (Object item : stack) {
            if (isSame(item, objectUpdated)) {
                updatedItems.add(objectUpdated);
            } else {
                updatedItems.add(item);
            }
        }

        stack.clear();

        for (int i = updatedItems.size() - 1; i >= 0; i--) {
            stack.push(updatedItems.get(i));
        }
    }

    public void removeItem(Object objectToRemove) {

        if (objectToRemove == null) {
            return;
        }

        removeFromBackStack(objectToRemove);

        // invalida histórico futuro
        forwardStack.clear();
    }

    private void removeFromBackStack(Object objectToRemove) {

        // Study remove tudo
        if (objectToRemove instanceof StudyDTO) {
            backStack.clear();
            return;
        }

        // Topic remove ele e todos acima
        if (objectToRemove instanceof TopicDTO) {

            List<Object> items = new ArrayList<>(backStack);

            int indexToRemove = -1;

            for (int i = 0; i < items.size(); i++) {

                if (isSame(items.get(i), objectToRemove)) {
                    indexToRemove = i;
                    break;
                }
            }

            if (indexToRemove == -1) {
                return;
            }

            List<Object> remaining = items.subList(indexToRemove + 1, items.size());

            backStack.clear();

            for (int i = remaining.size() - 1; i >= 0; i--) {
                backStack.push(remaining.get(i));
            }
        }
    }

}
