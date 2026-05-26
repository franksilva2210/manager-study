package app.ui.document.edit;

import app.application.document.DocumentDTO;
import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.ui.message.MessageConfirmController;
import app.ui.message.MessageConfirmWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditorDocumentController implements Initializable {

    private Label lblTitle;

    @FXML
    private Button bttPreview;

    @FXML
    private Button bttEdit;

    @FXML
    private Button bttBlocCod;

    @FXML
    private Button bttRemove;

    @FXML
    private VBox vboxMain;

    @FXML
    private Button bttSave;

    @FXML
    private Button bttCancel;

    private WebView webView;

    private CodeArea codeArea;

    private VirtualizedScrollPane<CodeArea> scrollPaneCodeArea;

    private Stage stage;
    private DocumentDTO documentDto;
    private EditorDocumentService editorDocumentService = new EditorDocumentService();
    private Runnable refreshObjectCurrentSelected;
    private Runnable showData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblTitle.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                editTitle();
            }
        });

        bttEdit.setOnAction(event -> {
            editText();
        });

        bttPreview.setOnAction(event -> {
            previewText();
        });

        bttBlocCod.setOnAction(event -> {
            onCodeBlock();
        });

        bttRemove.setOnAction(event -> {
            removeDocument();
        });

        bttSave.setOnAction(event -> {
            save();
        });

        bttCancel.setOnAction(event -> {
            cancel();
        });

        createCodeArea();

        createWebView();

        if (documentDto.getId() != null && documentDto.getContent() != null && !documentDto.getContent().isEmpty()) {
            previewText();
        } else {
            editText();
        }
    }

    private void createCodeArea() {
        codeArea = new CodeArea();
        scrollPaneCodeArea = new VirtualizedScrollPane<>(codeArea);
    }

    private void createWebView() {
        webView = new WebView();
    }

    public void editTitle() {
        TextInputDialog dialog = new TextInputDialog(lblTitle.getText());

        dialog.setTitle("Renomear Aba");
        dialog.setHeaderText("");
        dialog.setContentText("Titulo: ");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(newName -> {
            if (!newName.isBlank()) {
                documentDto.setTitle(newName);
                lblTitle.setText(documentDto.getTitle());
            }
        });
    }

    private void editText() {
        VBox.setVgrow(scrollPaneCodeArea, Priority.ALWAYS);
        vboxMain.getChildren().setAll(scrollPaneCodeArea);

        bttEdit.setDisable(true);
        bttPreview.setDisable(false);
    }

    private void previewText() {
        String markdown = codeArea.getText();

        String htmlContent = MarkdownConverter.toHtml(markdown);

        String html = """
        <html>
            <body>
                %s
            </body>
        </html>
        """.formatted(htmlContent);

        webView.getEngine().loadContent(html);

        VBox.setVgrow(webView, Priority.ALWAYS);
        vboxMain.getChildren().setAll(webView);

        bttPreview.setDisable(true);
        bttEdit.setDisable(false);
    }

    private void onCodeBlock() {
        String selectedText = codeArea.getSelectedText();

        if (selectedText != null && !selectedText.isBlank()) {

            String blocFenced = """
            
            ```
            %s
            ```
            
            """.formatted(selectedText);

            codeArea.replaceSelection(blocFenced);
        }
    }

    private void save() {
        String markdown = codeArea.getText();
        documentDto.setTitle(lblTitle.getText());
        documentDto.setContent(markdown);
        documentDto = editorDocumentService.save(documentDto);
    }

    private void removeDocument() {
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
                editorDocumentService.remove(documentDto.getId());
            }
            refreshObjectCurrentSelected.run();
            showData.run();
        }
    }

    private void cancel() {

    }

    public void setLblTitle(Label lblTitle) {
        this.lblTitle = lblTitle;
    }

    public void setDocumentDto(DocumentDTO documentDto) {
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
