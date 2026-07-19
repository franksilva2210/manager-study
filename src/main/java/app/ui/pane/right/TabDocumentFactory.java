package app.ui.pane.right;

import app.application.document.DocumentDTO;
import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.ui.document.edit.EditorDocumentController;
import app.ui.document.edit.EditorDocumentWindow;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TabDocumentFactory {

    public Tab createTabDocument(
            Stage stage,
            TabPane tabPaneStudy,
            Object itemSelected,
            DocumentDTO documentDto) {

        EditorDocumentController editorDocumentController = new EditorDocumentController();
        editorDocumentController.setTabPaneStudy(tabPaneStudy);
        editorDocumentController.setDocumentDto(documentDto);
        editorDocumentController.setStage(stage);

        if (itemSelected instanceof StudyDTO studyDto) {
            editorDocumentController.setStudyDto(studyDto);
        } else if (itemSelected instanceof TopicDTO topicDto) {
            editorDocumentController.setTopicDto(topicDto);
        }

        Tab tab = createTab(editorDocumentController);

        editorDocumentController.setTab(tab);

        return tab;
    }

    private Tab createTab(EditorDocumentController editorDocumentController) {
        Tab tab = new Tab();
        tab.setText("");
        tab.setClosable(true);

        ImageView imageInfo = new ImageView(new Image(getClass().getResourceAsStream("note.png")));
        imageInfo.setFitWidth(13);
        imageInfo.setFitHeight(13);

        Label lblTitle = new Label();
        lblTitle.setGraphic(imageInfo);
        lblTitle.setContentDisplay(ContentDisplay.RIGHT);
        lblTitle.setGraphicTextGap(6);
        lblTitle.setCursor(Cursor.HAND);

        tab.setGraphic(lblTitle);

        editorDocumentController.setLblTitle(lblTitle);

        tab.setUserData(editorDocumentController);

        EditorDocumentWindow editorDocumentWindow = new EditorDocumentWindow(editorDocumentController);
        VBox root = editorDocumentWindow.getRoot();

        tab.setContent(root);

        return tab;
    }
}
