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
	private RegisterStudyUI ui = new RegisterStudyUI();
	private RegisterStudyUIHelper uiHelper = new RegisterStudyUIHelper();
	private RegisterStudyService registerStudyService = new RegisterStudyService();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		loadUI();

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

		showData();

	}

	private void showData() {
		if (studyDto != null) {
			uiHelper.showStudyScreen(ui, studyDto);
		}
	}

	private void saveStudy() {
        try {
			uiHelper.validateFields(ui);
			uiHelper.extractFields(ui, studyDto);
			studyDto = registerStudyService.saveStudy(studyDto);
			stage.close();
        } catch (Exception e) {
			msgUser.setText(e.getMessage());
        }
    }

	private void loadUI() {
		ui.setTxtMatter(txtMatter);
		ui.setMsgUser(msgUser);
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
