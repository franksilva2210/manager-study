package app.ui.main;

import app.application.study.dto.StudyDTO;
import app.application.topic.dto.TopicDTO;
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

    public void configTreeItem(TreeView<Object> treeStudies, List<StudyDTO> listStudyDTO) {
        TreeItem<Object> treeItemRoot = new TreeItem<>("Estudos");
        treeItemRoot.setExpanded(true);

        for (StudyDTO studyDto : listStudyDTO) {
            TreeItem<Object> treeItemStudy = new TreeItem<>(studyDto);

            for (TopicDTO topicDto : studyDto.getListTopicsDto()) {
                TreeItem<Object> treeItemTopic = new TreeItem<>(topicDto);
                treeItemStudy.getChildren().add(treeItemTopic);
            }

            treeItemRoot.getChildren().add(treeItemStudy);
        }

        treeStudies.setRoot(treeItemRoot);
        treeStudies.setShowRoot(false);
    }

}
