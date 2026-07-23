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
        bindQtdDocuments(view, state);
        bindQtdTopic(view, state);
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

                    Object itemSelected = state.getItemSelected();

                    if (itemSelected == null) {
                        return "";
                    }

                    if (itemSelected instanceof StudyDTO study) {
                        return study.getMatter();
                    }

                    if (itemSelected instanceof TopicDTO topic) {
                        return topic.getTitle();
                    }

                    return "";

                }, state.itemSelectedProperty())
        );
    }

    public static void bindQtdDocuments(
            PaneRightController view,
            ScreenMainState state
    ) {

        view.getLblQtdDoc().textProperty().bind(
                Bindings.createStringBinding(() -> {

                    Object itemSelected = state.getItemSelected();

                    if (itemSelected == null) {
                        return "";
                    }

                    if (itemSelected instanceof StudyDTO study) {
                        return study.getListDocumentsDto().size() + " Documentos";
                    }

                    if (itemSelected instanceof TopicDTO topic) {
                        return topic.getListDocumentsDto().size() + " Documentos";
                    }

                    return "";

                }, state.itemSelectedProperty())
        );
    }

    public static void bindQtdTopic(
            PaneRightController view,
            ScreenMainState state
    ) {

        view.getLblQtdTopic().textProperty().bind(
                Bindings.createStringBinding(() -> {

                    Object itemSelected = state.getItemSelected();

                    if (itemSelected == null) {
                        return "";
                    }

                    if (itemSelected instanceof StudyDTO study) {
                        return study.getListTopicsDto().size() + " Tópicos";
                    }

                    if (itemSelected instanceof TopicDTO topic) {
                        return topic.getListTopicsDto().size() + " Tópicos";
                    }

                    return "";

                }, state.itemSelectedProperty())
        );
    }

}
