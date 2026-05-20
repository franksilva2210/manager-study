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

public class StudyRegisterController implements Initializable {

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
	private StudyRegisterComponentsUI componentsUI = new StudyRegisterComponentsUI();
	private StudyRegisterComponentsUIHelper componentsUIHelper = new StudyRegisterComponentsUIHelper();
	private StudyRegisterService studyRegisterService = new StudyRegisterService();

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

		loadComponentsFxDto();
	}

	private void saveStudy() {
        try {
			componentsUIHelper.validateFields(componentsUI);
			studyRegisterService.extractFields(componentsUI, study);
			study = studyRegisterService.saveStudy(study);
			stage.close();
        } catch (Exception e) {
			msgUser.setText(e.getMessage());
        }
    }

	private void loadComponentsFxDto() {
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
