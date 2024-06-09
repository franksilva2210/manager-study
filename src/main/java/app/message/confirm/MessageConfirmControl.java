package app.message.confirm;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class MessageConfirmControl implements Initializable {
	
	@FXML private Button bttCancel;
	@FXML private Button bttOk;
	@FXML private Text msgText;
	
	private Boolean confirm;
	private String msgUser;
	private MessageConfirmWindow messageConfirmWindow;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		msgText.setText(msgUser);
		
		bttOk.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				confirm = true;
				messageConfirmWindow.getStage().close();
			}
		});
		
		bttCancel.setOnMouseClicked((MouseEvent mouse) -> {
			if(mouse.getClickCount() == 1) {
				confirm = false;
				messageConfirmWindow.getStage().close();
			}
		});
		
	}

	public Boolean getConfirm() {
		return confirm;
	}

	public void setConfirm(Boolean confirm) {
		this.confirm = confirm;
	}

	public String getMsgUser() {
		return msgUser;
	}

	public void setMsgUser(String msgUser) {
		this.msgUser = msgUser;
	}

	public MessageConfirmWindow getMessageConfirmWindow() {
		return messageConfirmWindow;
	}

	public void setMessageConfirmWindow(MessageConfirmWindow messageConfirmWindow) {
		this.messageConfirmWindow = messageConfirmWindow;
	}
}
