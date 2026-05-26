package app.ui.document.edit;

import app.application.study.StudyDTO;
import app.application.document.DocumentDTO;
import app.application.topic.TopicDTO;
import app.ui.message.MessageConfirmController;
import app.ui.message.MessageConfirmWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagerTextController implements Initializable {

    @FXML
    private VBox boxMain;

    @FXML
    private VBox boxView;

    @FXML
    private WebView webView;

    @FXML
    private Button bttEditText;

    @FXML
    private Button bttRemove;

    private AnchorPane paneText;

    private DocumentDTO documentDto;
    private ManagerTextService managerTextService = new ManagerTextService();
    private Runnable refreshObjectCurrentSelected;
    private Runnable showData;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bttEditText.setOnAction(event -> {
            editText();
        });

        bttRemove.setOnAction(event -> {
            removeText();
        });

        previewText();
    }

    public void editText() {
        EditorDocumentController editorDocumentController =
                new EditorDocumentController();

        editorDocumentController.setPaneText(paneText);
        editorDocumentController.setTextDto(documentDto);
        editorDocumentController.setRefreshObjectCurrentSelected(refreshObjectCurrentSelected);
        editorDocumentController.setShowData(showData);
        editorDocumentController.setStage(stage);

        EditorDocumentWindow editorDocumentWindow =
                new EditorDocumentWindow(editorDocumentController);

        paneText.getChildren().setAll(editorDocumentWindow.getRoot());
    }

    private void removeText() {
        MessageConfirmController messageConfirmController = new MessageConfirmController();
        messageConfirmController.setMsgUser(
                "Deseja realmente remover este texto?"
        );

        MessageConfirmWindow messageConfirmWindow = new MessageConfirmWindow();
        messageConfirmWindow.setController(messageConfirmController);

        messageConfirmController.setMessageConfirmWindow(messageConfirmWindow);

        messageConfirmWindow.buildScreen(stage);
        messageConfirmWindow.showScreen();

        if (messageConfirmController.getConfirm()) {
            if (documentDto.getId() != null && documentDto.getId() > 0) {
                managerTextService.remove(documentDto.getId());
            }
            refreshObjectCurrentSelected.run();
            showData.run();
        }
    }

    private void previewText() {
        if (documentDto.getContent() == null || documentDto.getContent().isEmpty()) {
            return;
        }

        String markdown = documentDto.getContent();

        String htmlContent = MarkdownConverter.toHtml(markdown);

        String html = """
        <html>
            <body>
                %s
            </body>
        </html>
        """.formatted(htmlContent);

        webView.getEngine().loadContent(html);
    }

    public void editNameTab(Label label) {
        TextInputDialog dialog = new TextInputDialog(label.getText());

        dialog.setTitle("Renomear Aba");
        dialog.setHeaderText("");
        dialog.setContentText("Titulo: ");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(newName -> {
            if (!newName.isBlank()) {
                documentDto.setTitle(newName);
                documentDto = managerTextService.save(documentDto);
                label.setText(documentDto.getTitle());
            }
        });
    }

    public void setPaneText(AnchorPane paneText) {
        this.paneText = paneText;
    }

    public DocumentDTO getTextDto() {
        return documentDto;
    }

    public void setTextDto(DocumentDTO documentDto) {
        this.documentDto = documentDto;
    }

    public void setTopicDto(TopicDTO topicDto) {
        documentDto.setTopicId(topicDto.getId());
    }

    public void setStudyDto(StudyDTO studyDto) {
        documentDto.setStudyId(studyDto.getId());
    }

    public void setRefreshObjectCurrentSelected(Runnable refreshObjectCurrentSelected) {
        this.refreshObjectCurrentSelected = refreshObjectCurrentSelected;
    }

    public void setShowData(Runnable showData) {
        this.showData = showData;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
