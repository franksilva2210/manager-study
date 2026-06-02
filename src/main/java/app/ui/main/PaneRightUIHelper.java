package app.ui.main;

import app.application.document.DocumentDTO;
import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.ui.document.edit.EditorDocumentController;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Deque;
import java.util.function.Consumer;

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

    public void updateTabs(
            TabPane tabPaneStudy,
            Tab tabFixed,
            Tab tabFixed2,
            Object objectCurrentSelected,
            Consumer<DocumentDTO> createNewTabText) {

        tabPaneStudy.getTabs().removeIf(tab -> tab != tabFixed && tab != tabFixed2);

        if (objectCurrentSelected instanceof StudyDTO studyDto) {
            for (DocumentDTO documentDto : studyDto.getListDocumentsDto()) {
                createNewTabText.accept(documentDto);
            }
        } else if(objectCurrentSelected instanceof TopicDTO topicDto) {
            for (DocumentDTO documentDto : topicDto.getListDocumentsDto()) {
                createNewTabText.accept(documentDto);
            }
        }
    }

    public Tab createNewTab(
            int indexTabs,
            VBox root,
            Label lblTitle,
            DocumentDTO documentDto) {

        String title = "";

        if (documentDto.getTitle() != null && !documentDto.getTitle().isEmpty()) {
            title = documentDto.getTitle();
        } else {
            title = "Texto " + indexTabs;
            documentDto.setTitle(title);
        }

        lblTitle.setText(title);

        Tab tab = new Tab();
        tab.setClosable(true);
        tab.setContent(root);
        tab.setGraphic(lblTitle);
        tab.setText("");

        return tab;
    }

    public EditorDocumentController verifyDocumentEditingOrNotSave(TabPane tabPaneStudy, Tab tabMain, Tab tabAdd) {

        for (Tab tab : tabPaneStudy.getTabs()) {
            if (tab == tabMain || tab == tabAdd) {
                continue;
            }

            EditorDocumentController editorDocumentController = (EditorDocumentController) tab.getUserData();

            if (editorDocumentController.isEditing() || editorDocumentController.getDocumentDto().getId() == null) {
                return editorDocumentController;
            }
        }

        return null;
    }

}
