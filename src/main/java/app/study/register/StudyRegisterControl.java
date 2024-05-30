package app.study.register;

import java.net.URL;
import java.util.ResourceBundle;

import app.util.ConnectionDataBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class StudyRegisterControl implements Initializable {

	@FXML
	private TextField txtMatter;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			ConnectionDataBase.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
