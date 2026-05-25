package app.ui.main;

import app.application.text.TextDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import java.net.URL;
import java.util.ResourceBundle;

public class EditorTextController implements Initializable {

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

    private TextDTO textDto;
    private EditorTextService editorTextService = new EditorTextService();

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
        if (textDto.getId() != null && textDto.getContent() != null && !textDto.getContent().isEmpty()) {
            codeArea.replaceText(textDto.getContent());
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
        textDto.setContent(markdown);
        textDto = editorTextService.save(textDto);

        loadManagerTextWindow();
    }

    private void cancel() {
        loadManagerTextWindow();
    }

    private void loadManagerTextWindow() {
        ManagerTextController managerTextController =
                new ManagerTextController();

        managerTextController.setPaneText(paneText);
        managerTextController.setTextDto(textDto);

        ManagerTextWindow managerTextWindow =
                new ManagerTextWindow(managerTextController);

        paneText.getChildren().setAll(managerTextWindow.getRoot());
    }

    public void setPaneText(AnchorPane paneText) {
        this.paneText = paneText;
    }

    public TextDTO getTextDto() {
        return textDto;
    }

    public void setTextDto(TextDTO textDto) {
        this.textDto = textDto;
    }
}
