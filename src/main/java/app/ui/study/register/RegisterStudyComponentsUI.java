package app.ui.study.register;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RegisterStudyComponentsUI {

    private TextField txtMatter;
    private Text msgUser;

    public TextField getTxtMatter() {
        return txtMatter;
    }

    public void setTxtMatter(TextField txtMatter) {
        this.txtMatter = txtMatter;
    }

    public Text getMsgUser() {
        return msgUser;
    }

    public void setMsgUser(Text msgUser) {
        this.msgUser = msgUser;
    }
}
