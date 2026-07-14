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
            PaneRightState paneRightState) {

        bindNavigationButtons(view, paneRightState);
        bindHierarchyPath(view, paneRightState);
        bindTitleMain(view, screenMainState);
    }

    public static void bindNavigationButtons(
            PaneRightController view,
            PaneRightState paneRightState
    ) {

        view.getBttNavigationLeft().disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> {
                            boolean canGoBack = paneRightState.getBackStack().size() > 1;
                            return !canGoBack;
                        },
                        paneRightState.getBackStack()
                )
        );

        view.getBttNavigationRight().disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> {
                            boolean canGoForward = !paneRightState.getForwardStack().isEmpty();
                            return !canGoForward;
                        },
                        paneRightState.getForwardStack()
                )
        );
    }

    public static void bindHierarchyPath(
            PaneRightController view,
            PaneRightState paneRightState
    ) {

        view.getTxtHierarchyPath().textProperty().bind(
                Bindings.createStringBinding(
                        () -> {
                            String hierarchyPath = util.buildPath(paneRightState.getBackStack());
                            return hierarchyPath;
                        },
                        paneRightState.getBackStack()
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

}
