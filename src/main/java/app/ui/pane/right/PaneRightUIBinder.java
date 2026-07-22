package app.ui.pane.right;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.ui.main.ScreenMainState;
import javafx.beans.binding.Bindings;

public class PaneRightUIBinder {

    private static final PaneRightUtil util = new PaneRightUtil();

    public static void bind(
            PaneRightController view,
            ScreenMainState state) {

        bindNavigationButtons(view, state);
        bindHierarchyPath(view, state);
        bindTitleMain(view, state);
    }

    public static void bindNavigationButtons(
            PaneRightController view,
            ScreenMainState state
    ) {

        view.getBttNavigationLeft().disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> {
                            boolean canGoBack = state.getBackStack().size() > 1;
                            return !canGoBack;
                        },
                        state.getBackStack()
                )
        );

        view.getBttNavigationRight().disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> {
                            boolean canGoForward = !state.getForwardStack().isEmpty();
                            return !canGoForward;
                        },
                        state.getForwardStack()
                )
        );
    }

    public static void bindHierarchyPath(
            PaneRightController view,
            ScreenMainState state
    ) {

        view.getTxtHierarchyPath().textProperty().bind(
                Bindings.createStringBinding(
                        () -> {
                            String hierarchyPath = util.buildPath(state.getBackStack());
                            return hierarchyPath;
                        },
                        state.getBackStack()
                )
        );
    }

    public static void bindTitleMain(  
            PaneRightController view,
            ScreenMainState state
    ) {

        view.getLblTitleMain().textProperty().bind(
                Bindings.createStringBinding(() -> {

                    Object value = state.getItemSelected();

                    if (value instanceof StudyDTO study) {
                        return study.getMatter();
                    }

                    if (value instanceof TopicDTO topic) {
                        return topic.getTitle();
                    }

                    return "";

                }, state.itemSelectedProperty())
        );
    }

}
