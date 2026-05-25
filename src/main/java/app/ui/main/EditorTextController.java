package app.ui.main;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        createCodeArea();

        createWebView();

        editText();

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

    }

    private void cancel() {
        ManagerTextController managerTextController =
                new ManagerTextController();

        managerTextController.setPaneText(paneText);

        ManagerTextWindow managerTextWindow =
                new ManagerTextWindow(managerTextController);

        paneText.getChildren().setAll(managerTextWindow.getRoot());
    }

    public void setPaneText(AnchorPane paneText) {
        this.paneText = paneText;
    }
}
