package app.study.register;

import java.net.URL;
import java.util.ResourceBundle;

import app.message.confirm.MessageConfirmControl;
import app.message.confirm.MessageConfirmWindow;
import app.message.info.MessageInfoControl;
import app.message.info.MessageInfoWindow;
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
			removeStudy();
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

	private void removeStudy() {
		if (modPersistData.equals(ModPersistData.UPDATE)) {
			MessageConfirmWindow messageConfirmWindow = new MessageConfirmWindow();
			MessageConfirmControl messageConfirmControl = new MessageConfirmControl();
			messageConfirmControl.setConfirm(false);
			messageConfirmControl.setMsgUser("Deseja realmente remover esse estudo?");
			messageConfirmControl.setMessageConfirmWindow(messageConfirmWindow);
			messageConfirmWindow.setController(messageConfirmControl);
			messageConfirmWindow.buildAndShowScreen(StudyRegisterWindow.getStage());
			if(messageConfirmControl.getConfirm()) {
				modPersistData = ModPersistData.DELETE;
                try {
                    studyRegisterService.executePersistence(study, modPersistData);
					newStudy();
                } catch (Exception e) {
					MessageInfoControl messageInfoControl = new MessageInfoControl();
					messageInfoControl.setMsgUser(e.getMessage());
					MessageInfoWindow messageInfoWindow = new MessageInfoWindow();
					messageInfoWindow.setController(messageInfoControl);
					messageInfoWindow.buildAndShowScreen(StudyRegisterWindow.getStage());
                }
            }
		}
	}

	private void loadComponentsFxDto() {
		componentsFxDto.setTxtMatter(txtMatter);
		componentsFxDto.setMsgUser(msgUser);
	}

}
