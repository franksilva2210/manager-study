package app.study.register;

import java.net.URL;
import java.util.ResourceBundle;

import app.study.search.StudySearchControl;
import app.study.search.StudySearchWindow;
import app.util.ModPersistData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class StudyRegisterControl implements Initializable {

	@FXML
	private Button bttSave;

	@FXML
	private MenuItem menuNew;

	@FXML
	private MenuItem menuRemove;

	@FXML
	private MenuItem menuSearch;

	@FXML
	private TextField txtMatter;

	@FXML
	private Text msgUser;

	private Study study = new Study();
	private StudyRegisterComponentsFxDto componentsFxDto = new StudyRegisterComponentsFxDto();
	private StudyRegisterService studyRegisterService = new StudyRegisterService();
	private ModPersistData modPersistData = ModPersistData.INSERT;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menuNew.setOnAction((ActionEvent event) -> {
			newStudy();
		});

		menuSearch.setOnAction((ActionEvent event) -> {
			searchStudy();
		});

		menuRemove.setOnAction((ActionEvent event) -> {
			//desenvolver tela de confirmacao de operacao
		});

		bttSave.setOnMouseClicked((MouseEvent mouseEvent) -> {
			if (mouseEvent.getClickCount() == 1) {
				saveStudy();
			}
		});

		loadComponentsFxDto();
	}

	private void saveStudy() {
        try {
            studyRegisterService.validateFields(componentsFxDto);
			studyRegisterService.extractFields(componentsFxDto, study);
			studyRegisterService.executePersistence(study, modPersistData);
			newStudy();
        } catch (Exception e) {
			msgUser.setText(e.getMessage());
        }
    }

	private void newStudy() {
		modPersistData = ModPersistData.INSERT;
		study = new Study();
		studyRegisterService.clearScreen(componentsFxDto);
	}

	private void searchStudy() {
		StudySearchControl.setStudySelected(null);
		StudySearchWindow.buildAndShowScreen(StudyRegisterWindow.getStage());
		if (StudySearchControl.getStudySelected() != null) {
			study = StudySearchControl.getStudySelected();
			modPersistData = ModPersistData.UPDATE;
			studyRegisterService.showStudyScreen(componentsFxDto, study);
		}
	}

	private void loadComponentsFxDto() {
		componentsFxDto.setTxtMatter(txtMatter);
		componentsFxDto.setMsgUser(msgUser);
	}

}
