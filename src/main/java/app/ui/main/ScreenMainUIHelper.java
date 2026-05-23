package app.ui.main;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;

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

    public void updateTxtHierarchyPath(TextField txtHierarchyPath, String hierarchyPath) {
        txtHierarchyPath.setText(hierarchyPath);
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
