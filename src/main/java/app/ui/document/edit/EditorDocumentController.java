package app.ui.document.edit;

import app.application.document.DocumentDTO;
import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.ui.message.MessageConfirmController;
import app.ui.message.MessageConfirmWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class EditorDocumentController implements Initializable {

    private TabPane tabPaneStudy;

    private Tab tab;

    private Label lblTitle;

    @FXML
    private Button bttPreview;

    @FXML
    private Button bttEdit;

    @FXML
    private Button bttRemove;

    @FXML
    private Button bttExport;

    @FXML
    private Button bttImport;

    @FXML
    private Button bttBlocCod;

    @FXML
    private Button bttBold;

    @FXML
    private Button bttItalic;

    @FXML
    private Button bttAttachImg;

    @FXML
    private StackPane pane;

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
    private EditorDocumentUIHelper uiHelper = new EditorDocumentUIHelper();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblTitle.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                editTitle();
            }
        });

        bttEdit.setOnAction(event -> {
            editDocument();
        });

        bttPreview.setOnAction(event -> {
            previewDocument(codeArea.getText());
        });

        bttRemove.setOnAction(event -> {
            removeDocument();
        });

        bttImport.setOnAction(event -> {
            importDocument();
        });

        bttExport.setOnAction(event -> {
            exportDocument();
        });
        
        bttBlocCod.setOnAction(event -> {
            onCodeBlock();
        });

        bttBold.setOnAction(event -> {
            onBold();
        });

        bttItalic.setOnAction(event -> {
            onItalic();
        });

        bttAttachImg.setOnAction(event -> {
            onAttachImage();
        });

        bttSave.setOnAction(event -> {
            save();
        });

        bttCancel.setOnAction(event -> {
            cancel();
        });

        createCodeArea();

        codeArea.richChanges()
                .subscribe(change -> {

                });

        codeArea.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.S) {
                save();
                event.consume();
            }
        });

        createWebView();

        showInitial();
    }

    public boolean isEditing() {

        String persisted = documentDto.getContent();

        if (persisted == null) {
            persisted = "";
        }

        return !codeArea.getText().equals(persisted);
    }

    public void editTitle() {
        EditTitleController controller = new EditTitleController();
        controller.setTitleCurrent(lblTitle.getText());
        EditTitleWindow window = new EditTitleWindow(stage, controller);
        window.showScreen();

        String titleCurrent = lblTitle.getText();
        String newTitle = controller.getNewTitle();

        if (newTitle != null && !newTitle.isBlank() && !newTitle.equals(titleCurrent)) {
            lblTitle.setText(newTitle);
            bttSave.setDisable(false);
            bttCancel.setDisable(false);
        }
    }

    private void previewDocument(String markdown) {
        String htmlContent = MarkdownConverter.toHtml(markdown);

        String html = """
        <html>
            <body>
                %s
            </body>
        </html>
        """.formatted(htmlContent);

        webView.getEngine().loadContent(html);

        uiHelper.showPane(webView, pane);

        bttPreview.setDisable(true);
        bttEdit.setDisable(false);

        bttSave.setDisable(true);
        bttCancel.setDisable(true);
    }

    private void editDocument() {
        uiHelper.showPane(scrollPaneCodeArea, pane);

        bttPreview.setDisable(false);
        bttEdit.setDisable(true);

        bttSave.setDisable(false);
        bttCancel.setDisable(false);
    }

    private void removeDocument() {
        MessageConfirmController messageConfirmController = new MessageConfirmController();
        messageConfirmController.setConfirm(false);
        messageConfirmController.setMsgUser(
                "Deseja realmente remover este Texto?"
        );

        MessageConfirmWindow messageConfirmWindow = new MessageConfirmWindow(stage, messageConfirmController);
        messageConfirmWindow.showScreen();

        if (messageConfirmController.getConfirm()) {
            if (documentDto.getId() != null && documentDto.getId() > 0) {
                editorDocumentService.remove(documentDto.getId());
            }
            tabPaneStudy.getTabs().remove(tab);
        }
    }

    private void exportDocument() {
        if (documentDto == null) return;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar Documento");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Markdown (*.md)", "*.md")
        );

        fileChooser.setInitialFileName(
                documentDto.getTitle() != null ? documentDto.getTitle() + ".md" : "documento.md"
        );

        File file = fileChooser.showSaveDialog(stage);

        if (file == null) return;

        try {
            String content = codeArea.getText();

            Files.writeString(file.toPath(), content);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void importDocument() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importar Documento");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Markdown (*.md)", "*.md")
        );

        File file = fileChooser.showOpenDialog(stage);

        if (file == null) return;

        try {
            String content = Files.readString(file.toPath());

            codeArea.replaceText(content);

            String fileName = file.getName();
            if (fileName.endsWith(".md")) {
                fileName = fileName.substring(0, fileName.length() - 3);
            }

            lblTitle.setText(fileName);

            bttSave.setDisable(false);
            bttCancel.setDisable(false);

            editDocument();

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void onBold() {
        String selectedText = codeArea.getSelectedText();

        if (selectedText == null || selectedText.isBlank()) {
            codeArea.replaceSelection("**texto em negrito**");
            return;
        }

        codeArea.replaceSelection("**%s**".formatted(selectedText));
    }

    private void onItalic() {
        String selectedText = codeArea.getSelectedText();

        if (selectedText != null && !selectedText.isBlank()) {

            String italicText = "*%s*".formatted(selectedText);

            codeArea.replaceSelection(italicText);
        }
    }

    private void onAttachImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            String path = file.toURI().toString();
            codeArea.replaceSelection("![](" + path + ")");
        }
    }

    private void save() {
        uiHelper.extractValues(
                lblTitle,
                codeArea,
                documentDto
        );

        documentDto = editorDocumentService.save(documentDto);

        previewDocument(codeArea.getText());
    }

    private void cancel() {
        MessageConfirmController messageConfirmController = new MessageConfirmController();
        messageConfirmController.setConfirm(false);
        messageConfirmController.setMsgUser(
                "Deseja realmente cancelar a edição?\n" +
                "O Texto voltará ao seu estado atual. "
        );

        MessageConfirmWindow messageConfirmWindow = new MessageConfirmWindow(stage, messageConfirmController);
        messageConfirmWindow.showScreen();

        if (messageConfirmController.getConfirm()) {
            lblTitle.setText(documentDto.getTitle());

            codeArea.replaceText(
                    documentDto.getContent() != null ? documentDto.getContent() : ""
            );

            previewDocument(codeArea.getText());
        }
    }

    private void showInitial() {
        if (documentDto.getId() != null) {
            codeArea.replaceText(
                    documentDto.getContent() != null ? documentDto.getContent() : ""
            );
            previewDocument(codeArea.getText());
        } else {
            editDocument();
        }
    }

    private void createCodeArea() {
        codeArea = new CodeArea();
        scrollPaneCodeArea = new VirtualizedScrollPane<>(codeArea);

        pane.getChildren().add(scrollPaneCodeArea);

        scrollPaneCodeArea.setVisible(false);
        scrollPaneCodeArea.setManaged(false);
    }

    private void createWebView() {
        webView = new WebView();

        pane.getChildren().add(webView);

        webView.setVisible(false);
        webView.setManaged(false);
    }

    public void setDocumentDto(DocumentDTO documentDto) {
        this.documentDto = documentDto;
    }

    public DocumentDTO getDocumentDto() {
        return documentDto;
    }

    public void setTopicDto(TopicDTO topicDto) {
        documentDto.setTopicId(topicDto.getId());
    }

    public void setStudyDto(StudyDTO studyDto) {
        documentDto.setStudyId(studyDto.getId());
    }

    public void setLblTitle(Label lblTitle) {
        this.lblTitle = lblTitle;
    }

    public void setTabPaneStudy(TabPane tabPaneStudy) {
        this.tabPaneStudy = tabPaneStudy;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
