package app.ui.pane.right;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.Deque;

public class PaneRightUIHelper {

    public void updateNavigationButtons(
            Button bttNavigationLeft,
            Button bttNavigationRight,
            boolean canGoBack,
            boolean canGoForward
    ) {
        bttNavigationLeft.setDisable(!canGoBack);
        bttNavigationRight.setDisable(!canGoForward);
    }

    public void updateTxtHierarchyPath(TextField txtHierarchyPath, Deque<Object> backStack) {
        PaneRightUtil pathUtil = new PaneRightUtil();
        txtHierarchyPath.setText(pathUtil.buildPath(backStack));
    }

    public void updateTitleItemMain(Label lblTitleMain, Object objectCurrentSelected) {
        if (objectCurrentSelected instanceof StudyDTO studyDto) {
            lblTitleMain.setText(studyDto.getMatter());
        } else if (objectCurrentSelected instanceof TopicDTO topicDto) {
            lblTitleMain.setText(topicDto.getTitle());
        }
    }

    public void updateListViewTopics(
            ObservableList<TopicDTO> observableListTopics,
            ListView<TopicDTO> listViewTopics,
            Object objectCurrentSelected
    ) {
        observableListTopics.clear();

        if (objectCurrentSelected instanceof StudyDTO studyDto) {
            observableListTopics.addAll(studyDto.getListTopicsDto());
        } else if (objectCurrentSelected instanceof TopicDTO topicDto) {
            observableListTopics.addAll(topicDto.getListTopicsDto());
        }

        listViewTopics.refresh();
    }

}
