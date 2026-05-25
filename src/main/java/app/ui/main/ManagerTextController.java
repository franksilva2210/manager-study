package app.ui.main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.net.URL;
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

    private AnchorPane paneText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bttEditText.setOnAction(event -> {
            editText();
        });

    }

    public void editText() {
        EditorTextController editorTextController =
                new EditorTextController();

        editorTextController.setPaneText(paneText);

        EditorTextWindow editorTextWindow =
                new EditorTextWindow(editorTextController);

        paneText.getChildren().setAll(editorTextWindow.getRoot());
    }

    public void setHtml(String html) {
        html = html.replaceAll(
                "contenteditable\\s*=\\s*\"true\"",
                ""
        );

        html = html.replaceAll(
                "<script[^>]*>.*?</script>",
                ""
        );

        webView.getEngine().loadContent(html);
    }

    public void setPaneText(AnchorPane paneText) {
        this.paneText = paneText;
    }
}
