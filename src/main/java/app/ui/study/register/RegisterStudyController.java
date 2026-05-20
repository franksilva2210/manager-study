package app.ui.study.register;

import java.net.URL;
import java.util.ResourceBundle;

import app.domain.study.Study;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegisterStudyController implements Initializable {

	@FXML
	private TextField txtMatter;

	@FXML
	private Text msgUser;

	@FXML
	private Button bttSave;

	@FXML
	private Button bttCancel;

	private Stage stage;
	private Study study = new Study();
	private RegisterStudyComponentsUI componentsUI = new RegisterStudyComponentsUI();
	private RegisterStudyComponentsUIHelper componentsUIHelper = new RegisterStudyComponentsUIHelper();
	private RegisterStudyService registerStudyService = new RegisterStudyService();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		txtMatter.setOnAction(event -> {
			saveStudy();
		});

		bttSave.setOnMouseClicked((MouseEvent mouseEvent) -> {
			if (mouseEvent.getClickCount() == 1) {
				saveStudy();
			}
		});

		bttCancel.setOnAction(event -> {
			stage.close();
		});

		loadComponentsUI();
	}

	private void saveStudy() {
        try {
			componentsUIHelper.validateFields(componentsUI);
			componentsUIHelper.extractFields(componentsUI, study);
			study = registerStudyService.saveStudy(study);
			stage.close();
        } catch (Exception e) {
			msgUser.setText(e.getMessage());
        }
    }

	private void loadComponentsUI() {
		componentsUI.setTxtMatter(txtMatter);
		componentsUI.setMsgUser(msgUser);
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}
}
