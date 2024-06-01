package app.pane.study.topic.register;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class TopicRegisterComponentsFxDto {
    private TextField txtTitle;
    private Text txtMsg;

    public TextField getTxtTitle() {
        return txtTitle;
    }

    public void setTxtTitle(TextField txtTitle) {
        this.txtTitle = txtTitle;
    }

    public Text getTxtMsg() {
        return txtMsg;
    }

    public void setTxtMsg(Text txtMsg) {
        this.txtMsg = txtMsg;
    }
}
