package app.ui.message;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MessageInfoController implements Initializable {
	
	@FXML private Button bttOk;
	@FXML private Text txtMsgInfo;
	
	private String msgUser;
	private Stage stage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		txtMsgInfo.setText(msgUser);
		
		bttOk.setOnMouseClicked((MouseEvent mouse) -> {
			if (mouse.getClickCount() == 1) {
				stage.close();
			}
		});
	}

	public String getMsgUser() {
		return msgUser;
	}

	public void setMsgUser(String msgUser) {
		this.msgUser = msgUser;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}