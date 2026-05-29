package app.ui.message;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MessageConfirmController implements Initializable {
	
	@FXML private Button bttCancel;
	@FXML private Button bttOk;
	@FXML private Text msgText;
	
	private boolean confirm;
	private String msgUser;
	private Stage stage;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		msgText.setText(msgUser);
		
		bttOk.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				confirm = true;
				stage.close();
			}
		});
		
		bttCancel.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				confirm = false;
				stage.close();
			}
		});
	}

	public boolean getConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
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
