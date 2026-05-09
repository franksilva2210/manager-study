package app.ui.message;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class MessageInfoControl implements Initializable {
	
	@FXML private Button bttOk;
	@FXML private Text txtMsgInfo;
	
	private String msgUser;
	private MessageInfoWindow window;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		txtMsgInfo.setText(msgUser);
		
		bttOk.setOnMouseClicked((MouseEvent mouse) -> {
			if (mouse.getClickCount() == 1) {
				window.getStage().close();
			}
		});
	}

	public String getMsgUser() {
		return msgUser;
	}

	public void setMsgUser(String msgUser) {
		this.msgUser = msgUser;
	}

	public MessageInfoWindow getWindow() {
		return window;
	}

	public void setWindow(MessageInfoWindow window) {
		this.window = window;
	}
}