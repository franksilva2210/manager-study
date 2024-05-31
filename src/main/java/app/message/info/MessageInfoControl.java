package app.message.info;

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
	
	private static String msgUser;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		txtMsgInfo.setText(msgUser);
		
		bttOk.setOnMouseClicked((MouseEvent mouse) -> {
			if (mouse.getClickCount() == 1) {
				MessageInfoWindow.getStage().close();
			}
		});
	}

	public static String getMsgUser() {
		return msgUser;
	}

	public static void setMsgUser(String msgUser) {
		MessageInfoControl.msgUser = msgUser;
	}
	
	

}