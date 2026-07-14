package app.ui.pane.right;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.ui.main.ScreenMainState;
import javafx.beans.binding.Bindings;

public class PaneRightUIBinder {

    private static final PaneRightUtil util = new PaneRightUtil();

    public static void bind(
            PaneRightController view,
            ScreenMainState screenMainState,
            PaneRightUIState paneRightUIState) {

        bindNavigationButtons(view, paneRightUIState);
        bindHierarchyPath(view, paneRightUIState);
        bindTitleMain(view, screenMainState);
        bindListTopics(view, screenMainState);
    }

    public static void bindNavigationButtons(
            PaneRightController view,
            PaneRightUIState paneRightUIState
    ) {

        view.getBttNavigationLeft().disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> {
                            boolean canGoBack = paneRightUIState.getBackStack().size() > 1;
                            return !canGoBack;
                        },
                        paneRightUIState.getBackStack()
                )
        );

        view.getBttNavigationRight().disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> {
                            boolean canGoForward = !paneRightUIState.getForwardStack().isEmpty();
                            return !canGoForward;
                        },
                        paneRightUIState.getForwardStack()
                )
        );
    }

    public static void bindHierarchyPath(
            PaneRightController view,
            PaneRightUIState paneRightUIState
    ) {

        view.getTxtHierarchyPath().textProperty().bind(
                Bindings.createStringBinding(
                        () -> {
                            String hierarchyPath = util.buildPath(paneRightUIState.getBackStack());
                            return hierarchyPath;
                        },
                        paneRightUIState.getBackStack()
                )
        );
    }

    public static void bindTitleMain(
            PaneRightController view,
            ScreenMainState screenMainState
    ) {

        view.getLblTitleMain().textProperty().bind(
                Bindings.createStringBinding(() -> {

                    Object value = screenMainState.getItemSelected();

                    if (value instanceof StudyDTO study) {
                        return study.getMatter();
                    }

                    if (value instanceof TopicDTO topic) {
                        return topic.getTitle();
                    }

                    return "";

                }, screenMainState.itemSelectedProperty())
        );
    }

    public static void bindListTopics(
            PaneRightController view,
            ScreenMainState screenMainState
    ) {

        screenMainState.itemSelectedProperty().addListener((obs, oldValue, newValue) -> {
            view.getListTopics().clear();

            if (newValue instanceof StudyDTO study) {
                view.getListTopics().addAll(study.getListTopicsDto());
            }

            if (newValue instanceof TopicDTO topic) {
                view.getListTopics().addAll(topic.getListTopicsDto());
            }
        });
    }

}
