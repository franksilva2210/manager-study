package app.pane.study;

import java.net.URL;
import java.util.ResourceBundle;

import app.message.confirm.MessageConfirmControl;
import app.message.confirm.MessageConfirmWindow;
import app.message.info.MessageInfoControl;
import app.message.info.MessageInfoWindow;
import app.pane.PaneMainWindow;
import app.pane.study.topic.TopicControl;
import app.pane.study.topic.TopicWindow;
import app.pane.study.topic.register.TopicRegisterControl;
import app.pane.study.topic.register.TopicRegisterWindow;
import app.study.register.Study;
import app.pane.study.topic.register.Topic;
import app.study.register.StudyRegisterWindow;
import app.util.ModPersistData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;

public class StudyControl implements Initializable {

	@FXML private Label lblTitleStudy;

	@FXML private TabPane tabPaneStudy;

	//Aba Topicos
	@FXML private Tab tabTopics;
	@FXML private ListView<String> listViewTopics;
	@FXML private Button bttSearchTopic;
	@FXML private Button bttAddTopic;
	@FXML private Button bttEditTopic;
	@FXML private Button bttRemoveTopic;

	//Aba Anotacoes
	@FXML private Tab tabNotes;
	@FXML private HTMLEditor editorTextMatter;

	@FXML private Button bttSave;
	
	private Study study;
	private ObservableList<String> observableListTopics = FXCollections.observableArrayList();
	private StudyService studyService = new StudyService();
	private StudyControlComponentsFxDto componentsFxDto = new StudyControlComponentsFxDto();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listViewTopics.setItems(observableListTopics);
		
		listViewTopics.setOnMouseClicked((MouseEvent mouse) -> {
			if (mouse.getClickCount() == 2) {
				openTopic();
			}
		});

		bttAddTopic.setOnMouseClicked((MouseEvent mouse) -> {
			if (mouse.getClickCount() == 1) {
				addTopic();
			}
		});

		bttEditTopic.setOnMouseClicked((MouseEvent mouse) -> {
			if (mouse.getClickCount() == 1) {
				editTopic();
			}
		});

		bttRemoveTopic.setOnMouseClicked((MouseEvent mouse) -> {
			if (mouse.getClickCount() == 1) {
				removeTopic();
			}
		});

		bttSave.setOnMouseClicked((MouseEvent mouse) -> {
			if (mouse.getClickCount() == 1) {
				saveStudy();
			}
		});

		componentsFxDto.setLblTitleStudy(lblTitleStudy);
		componentsFxDto.setListViewTopics(listViewTopics);
		componentsFxDto.setObservableListTopics(observableListTopics);
		studyService.showScreenStudy(study, componentsFxDto);
	}

	private void openTopic() {
		String titleTopic = listViewTopics.getSelectionModel().getSelectedItem();
		if (titleTopic != null) {
			for(Topic topic : study.getListTopics()) {
				if (topic.getTitle().equals(titleTopic)) {
					TopicControl topicControl = new TopicControl();
					topicControl.setTopicSelected(topic);
					TopicWindow topicWindow = new TopicWindow();
					topicWindow.buildAndShowScreen(topicControl, PaneMainWindow.getStage());
				}
			}
		}
	}

	private void addTopic() {
		if (study != null) {
			TopicRegisterWindow topicRegisterWindow = new TopicRegisterWindow();
			TopicRegisterControl topicRegisterControl = new TopicRegisterControl();
			topicRegisterControl.setTopicRegisterWindow(topicRegisterWindow);
			topicRegisterWindow.setController(topicRegisterControl);
			topicRegisterWindow.buildAndShowScreen(PaneMainWindow.getStage());
			if (topicRegisterControl.getTopic() != null) {
				Topic topic = topicRegisterControl.getTopic();
				study.getListTopics().add(topic);
				studyService.updateObservableListTopics(study, observableListTopics);
				listViewTopics.refresh();
			}
		} else {
			MessageInfoWindow messageInfoWindow = new MessageInfoWindow();
			MessageInfoControl messageInfoControl = new MessageInfoControl();
			messageInfoControl.setMsgUser("Não foi selecionado nenhum Estudo para\nadição de topicos. " +
					"Selecione primeiramente\num Estudo cadastrado e tente novamente.");
			messageInfoControl.setWindow(messageInfoWindow);
			messageInfoWindow.setController(messageInfoControl);
			messageInfoWindow.buildAndShowScreen(PaneMainWindow.getStage());
		}
	}

	private void editTopic() {
		String titleTopic = listViewTopics.getSelectionModel().getSelectedItem();
		if (titleTopic != null) {
			Topic topicSelected = study.getTopicByTitle(titleTopic);
			if (topicSelected != null) {
				TopicRegisterWindow topicRegisterWindow = new TopicRegisterWindow();
				TopicRegisterControl topicRegisterControl = new TopicRegisterControl();
				topicRegisterControl.setTopicRegisterWindow(topicRegisterWindow);
				topicRegisterControl.setTopic(topicSelected);
				topicRegisterControl.setModPersistData(ModPersistData.UPDATE);
				topicRegisterWindow.setController(topicRegisterControl);
				topicRegisterWindow.buildAndShowScreen(PaneMainWindow.getStage());
				if (topicRegisterControl.getTopic() != null) {
					Topic topicEdited = topicRegisterControl.getTopic();
					if (topicSelected.verifyUpdateInTitle(topicEdited)) {
						study.getListTopics().remove(topicSelected);
						study.getListTopics().add(topicEdited);
						studyService.updateObservableListTopics(study, observableListTopics);
						listViewTopics.refresh();
					}
				}
			}
		}
	}

	private void removeTopic() {
		String titleTopic = listViewTopics.getSelectionModel().getSelectedItem();
		if (titleTopic != null) {
			Topic topicSelected = study.getTopicByTitle(titleTopic);
			MessageConfirmWindow messageConfirmWindow = new MessageConfirmWindow();
			MessageConfirmControl messageConfirmControl = new MessageConfirmControl();
			messageConfirmControl.setConfirm(false);
			messageConfirmControl.setMsgUser("Deseja realmente remover esse topico?");
			messageConfirmControl.setMessageConfirmWindow(messageConfirmWindow);
			messageConfirmWindow.setController(messageConfirmControl);
			messageConfirmWindow.buildAndShowScreen(StudyRegisterWindow.getStage());
			if(messageConfirmControl.getConfirm()) {
				study.getListTopics().remove(topicSelected);
				studyService.updateObservableListTopics(study, observableListTopics);
				listViewTopics.refresh();
			}
		}
	}

	private void saveStudy() {
		MessageInfoWindow messageInfoWindow = new MessageInfoWindow();
		MessageInfoControl messageInfoControl = new MessageInfoControl();
		messageInfoControl.setWindow(messageInfoWindow);
		messageInfoWindow.setController(messageInfoControl);
        try {
            studyService.saveStudy(study);
			messageInfoControl.setMsgUser("Estudo atualizado com sucesso.");
			messageInfoWindow.buildAndShowScreen(PaneMainWindow.getStage());
			study = studyService.consultStudyById(study.getId());
			studyService.clearScreen(componentsFxDto);
			studyService.showScreenStudy(study, componentsFxDto);
        } catch (Exception e) {
			messageInfoControl.setMsgUser(e.getMessage());
			messageInfoWindow.buildAndShowScreen(PaneMainWindow.getStage());
        }
    }

	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}
}
