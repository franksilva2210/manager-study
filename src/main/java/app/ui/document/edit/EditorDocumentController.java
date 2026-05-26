package app.ui.document.edit;

import app.application.document.DocumentDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import java.net.URL;
import java.util.ResourceBundle;

public class EditorDocumentController implements Initializable {

    @FXML
    private Button bttPreview;

    @FXML
    private Button bttEdit;

    @FXML
    private Button bttBlocCod;

    @FXML
    private VBox vboxMain;

    @FXML
    private Button bttSave;

    @FXML
    private Button bttCancel;

    private AnchorPane paneText;

    private CodeArea codeArea;

    private WebView webView;

    private DocumentDTO documentDto;
    private EditorDocumentService editorDocumentService = new EditorDocumentService();

    private Runnable refreshObjectCurrentSelected;
    private Runnable showData;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bttEdit.setOnAction(event -> {
            editText();
        });

        bttPreview.setOnAction(event -> {
            previewText();
        });

        bttBlocCod.setOnAction(event -> {
            onCodeBlock();
        });

        bttSave.setOnAction(event -> {
            save();
        });

        bttCancel.setOnAction(event -> {
            cancel();
        });

        createCodeArea();

        createWebView();

        editText();

        settingText();
    }

    private void settingText() {
        if (documentDto.getId() != null && documentDto.getContent() != null && !documentDto.getContent().isEmpty()) {
            codeArea.replaceText(documentDto.getContent());
        }
    }

    private void createCodeArea() {
        codeArea = new CodeArea();
    }

    private void createWebView() {
        webView = new WebView();
    }

    private void editText() {
        VirtualizedScrollPane<CodeArea> scrollPane =
                new VirtualizedScrollPane<>(codeArea);

        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        vboxMain.getChildren().setAll(scrollPane);

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
        documentDto.setContent(markdown);
        documentDto = editorDocumentService.save(documentDto);

        loadManagerTextWindow();
    }

    private void cancel() {
        loadManagerTextWindow();
    }

    private void loadManagerTextWindow() {
        ManagerTextController managerTextController =
                new ManagerTextController();

        managerTextController.setPaneText(paneText);
        managerTextController.setTextDto(documentDto);
        managerTextController.setRefreshObjectCurrentSelected(refreshObjectCurrentSelected);
        managerTextController.setShowData(showData);
        managerTextController.setStage(stage);

        ManagerTextWindow managerTextWindow =
                new ManagerTextWindow(managerTextController);

        paneText.getChildren().setAll(managerTextWindow.getRoot());
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
