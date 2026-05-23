package app.ui.main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.HTMLEditor;

import java.net.URL;
import java.util.ResourceBundle;

public class TextEditorController implements Initializable {

    @FXML
    private HTMLEditor htmEditor;

    @FXML
    private Button bttCancel;

    @FXML
    private Button bttSave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bttSave.setOnAction(event -> save());
        bttCancel.setOnAction(event -> cancel());
    }

    public void save() {
        String html = htmEditor.getHtmlText();
        //save html
    }

    public void cancel() {

    }

    public void setHtml(String html) {
        htmEditor.setHtmlText(html);
    }

}
