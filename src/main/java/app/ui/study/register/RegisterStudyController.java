package app.ui.study.register;

import java.net.URL;
import java.util.ResourceBundle;

import app.application.study.StudyDTO;
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
	private StudyDTO studyDto;
	private RegisterStudyComponentsUI componentsUI = new RegisterStudyComponentsUI();
	private RegisterStudyUIHelper uiHelper = new RegisterStudyUIHelper();
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
		showData();
	}

	private void showData() {
		if (studyDto != null && studyDto.getId() != null && studyDto.getId() > 0) {
			uiHelper.showStudyScreen(componentsUI, studyDto);
		}
	}

	private void saveStudy() {
        try {
			uiHelper.validateFields(componentsUI);
			uiHelper.extractFields(componentsUI, studyDto);
			studyDto = registerStudyService.saveStudy(studyDto);
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

	public StudyDTO getStudyDto() {
		return studyDto;
	}

	public void setStudyDto(StudyDTO studyDto) {
		this.studyDto = studyDto;
	}
}
