package app.ui.main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class PreviewTextController implements Initializable {

    @FXML
    private WebView webView;

    @FXML
    private Button bttCod;

    @FXML
    private Button bttText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bttCod.setOnAction(event -> {
            selectCode();
        });

        bttText.setOnAction(event -> {
            selectText();
        });

    }

    public void selectCode() {

    }

    public void selectText() {

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

}
