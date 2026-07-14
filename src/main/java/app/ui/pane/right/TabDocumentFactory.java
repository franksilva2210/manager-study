package app.ui.pane.right;

import app.application.document.DocumentDTO;
import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.ui.document.edit.EditorDocumentController;
import app.ui.document.edit.EditorDocumentWindow;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TabDocumentFactory {

    public Tab addTabDocument(
            Stage stage,
            TabPane tabPaneStudy,
            Tab tabAdd,
            Object itemSelected,
            DocumentDTO documentDto) {

        EditorDocumentController editorDocumentController = new EditorDocumentController();

        Label lblTitle = new Label();

        editorDocumentController.setLblTitle(lblTitle);
        editorDocumentController.setDocumentDto(documentDto);
        editorDocumentController.setStage(stage);

        if (itemSelected instanceof StudyDTO studyDto) {
            editorDocumentController.setStudyDto(studyDto);
        } else if (itemSelected instanceof TopicDTO topicDto) {
            editorDocumentController.setTopicDto(topicDto);
        }

        EditorDocumentWindow editorDocumentWindow = new EditorDocumentWindow(editorDocumentController);

        int indexTabs = tabPaneStudy.getTabs().indexOf(tabAdd);
        VBox root = editorDocumentWindow.getRoot();

        Tab newTab = createTab(indexTabs, root, lblTitle, documentDto);

        editorDocumentController.setTab(newTab);
        editorDocumentController.setTabPaneStudy(tabPaneStudy);

        newTab.setUserData(editorDocumentController);

        tabPaneStudy.getTabs().add(indexTabs, newTab);

        return newTab;
    }

    private Tab createTab(
            int indexTabs,
            VBox root,
            Label lblTitle,
            DocumentDTO documentDto) {

        String title = "";

        if (documentDto.getTitle() != null && !documentDto.getTitle().isEmpty()) {
            title = documentDto.getTitle();
        } else {
            title = "Texto " + indexTabs;
        }

        lblTitle.setText(title);

        Tab tab = new Tab();
        tab.setClosable(true);
        tab.setContent(root);
        tab.setGraphic(lblTitle);
        tab.setText("");

        return tab;
    }

    public void loadTabsDocument(
            Stage stage,
            TabPane tabPaneStudy,
            Tab tabFixed,
            Tab tabFixed2,
            Object itemSelected) {

        tabPaneStudy.getTabs().removeIf(tab -> tab != tabFixed && tab != tabFixed2);

        if (itemSelected instanceof StudyDTO studyDto) {
            for (DocumentDTO dto : studyDto.getListDocumentsDto()) {
                addTabDocument(
                        stage,
                        tabPaneStudy,
                        tabFixed2,
                        itemSelected,
                        dto);
            }
        } else if(itemSelected instanceof TopicDTO topicDto) {
            for (DocumentDTO dto : topicDto.getListDocumentsDto()) {
                addTabDocument(
                        stage,
                        tabPaneStudy,
                        tabFixed2,
                        itemSelected,
                        dto);
            }
        }
    }

    public EditorDocumentController verifyDocumentEditingOrNotSave(
            TabPane tabPaneStudy,
            Tab tabMain,
            Tab tabAdd) {

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
