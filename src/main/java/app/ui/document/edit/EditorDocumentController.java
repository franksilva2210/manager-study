package app.ui.document.edit;

import app.application.document.DocumentDTO;
import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.ui.message.MessageConfirmController;
import app.ui.message.MessageConfirmWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Optional;
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

        createWebView();

        showInitial();
    }

    public void editTitle() {
        TextInputDialog dialog = new TextInputDialog(lblTitle.getText());

        dialog.setTitle("Renomear Aba");
        dialog.setHeaderText("");
        dialog.setContentText("Titulo: ");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(newName -> {
            if (!newName.isBlank()) {
                lblTitle.setText(newName);

                bttSave.setDisable(false);
                bttCancel.setDisable(false);
            }
        });
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

        VBox.setVgrow(webView, Priority.ALWAYS);
        vboxMain.getChildren().setAll(webView);

        bttPreview.setDisable(true);
        bttEdit.setDisable(false);

        bttSave.setDisable(true);
        bttCancel.setDisable(true);
    }

    private void editDocument() {
        VBox.setVgrow(scrollPaneCodeArea, Priority.ALWAYS);
        vboxMain.getChildren().setAll(scrollPaneCodeArea);

        bttPreview.setDisable(false);
        bttEdit.setDisable(true);

        bttSave.setDisable(false);
        bttCancel.setDisable(false);
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
        uiHelper.extractValues(lblTitle, codeArea, documentDto);
        documentDto = editorDocumentService.save(documentDto);
        previewDocument(codeArea.getText());
    }

    private void cancel() {
        MessageConfirmController messageConfirmController
                = new MessageConfirmController();

        messageConfirmController.setConfirm(false);
        messageConfirmController.setMsgUser(
                "Deseja realmente cancelar a edição?"
        );

        MessageConfirmWindow messageConfirmWindow
                = new MessageConfirmWindow();

        messageConfirmController.setMessageConfirmWindow(messageConfirmWindow);

        messageConfirmWindow.setController(messageConfirmController);
        messageConfirmWindow.buildAndShowScreen(stage);

        if (messageConfirmController.getConfirm()) {
            if (documentDto.getId() != null && documentDto.getId() > 0) {
                lblTitle.setText(documentDto.getTitle());
            }

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
    }

    private void createWebView() {
        webView = new WebView();
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
