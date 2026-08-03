package app.ui.main;

import app.ui.pane.right.PaneRightUtil;
import javafx.beans.binding.Bindings;

public class ScreenMainUIBinder {

    private static final PaneRightUtil util = new PaneRightUtil();

    public static void bind(
            ScreenMainController view,
            ScreenMainState state) {

        bindNavigationButtons(view, state);
        bindHierarchyPath(view, state);
    }

    public static void bindNavigationButtons(
            ScreenMainController view,
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
            ScreenMainController view,
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

}
