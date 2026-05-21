package app.ui.main;

import app.domain.study.Study;
import app.domain.topic.Topic;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScreenMainUIHelper {

    public void updateNavigationButtons(
            Button bttNavigationLeft,
            Button bttNavigationRight,
            boolean canGoBack,
            boolean canGoForward
    ) {
        bttNavigationLeft.setDisable(!canGoBack);
        bttNavigationRight.setDisable(!canGoForward);
    }

    public void updateTxtHierarchyPath(TextField txtHierarchyPath, Object objectCurrentSelected) {
        txtHierarchyPath.setText(getHierarchyPath(objectCurrentSelected));
    }

    public String getHierarchyPath(Object current) {
        List<String> paths = new ArrayList<>();

        if (current instanceof Study study) {
            paths.add(study.getMatter());
        } else if (current instanceof Topic topic) {
            Topic currentTopic = topic;

            while (currentTopic != null) {
                paths.add(currentTopic.getTitle());

                if (currentTopic.getStudy() != null) {
                    paths.add(currentTopic.getStudy().getMatter());
                    break;
                }

                currentTopic = currentTopic.getTopicParent();
            }
        }

        Collections.reverse(paths);

        return String.join(" > ", paths);
    }

    public void updateTitleItemMain(Label lblTitleMain, Object objectCurrentSelected) {
        if (objectCurrentSelected instanceof Study study) {
            lblTitleMain.setText(study.getMatter());
        } else if (objectCurrentSelected instanceof Topic topic) {
            lblTitleMain.setText(topic.getTitle());
        }
    }

    public void updateListViewTopics(
            ObservableList<Topic> observableListTopics,
            ListView<Topic> listViewTopics,
            Object objectCurrentSelected
    ) {
        observableListTopics.clear();

        if (objectCurrentSelected instanceof Study study) {
            observableListTopics.addAll(study.getListTopics());
        } else if (objectCurrentSelected instanceof Topic topic) {
            observableListTopics.addAll(topic.getListTopics());
        }

        listViewTopics.refresh();
    }

    public void createNewTab(TabPane tabPaneStudy, Tab tabAdd) {
        HTMLEditor htmlEditor = new HTMLEditor();

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(htmlEditor);

        String title = "Texto " + tabPaneStudy.getTabs().indexOf(tabAdd);

        Tab tab = new Tab(title);
        tab.setClosable(true);
        tab.setContent(anchorPane);

        int indexAddTab = tabPaneStudy.getTabs().indexOf(tabAdd);
        tabPaneStudy.getTabs().add(indexAddTab, tab);
        tabPaneStudy.getSelectionModel().select(tab);
    }

}
