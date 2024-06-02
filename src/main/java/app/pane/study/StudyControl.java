package app.pane.study;

import java.net.URL;
import java.util.ResourceBundle;

import app.message.info.MessageInfoControl;
import app.message.info.MessageInfoWindow;
import app.pane.MainContainerWindow;
import app.pane.study.topic.TopicControl;
import app.pane.study.topic.TopicWindow;
import app.pane.study.topic.register.TopicRegisterControl;
import app.pane.study.topic.register.TopicRegisterWindow;
import app.study.register.Study;
import app.pane.study.topic.register.Topic;
import app.util.ModPersistData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;

public class StudyControl implements Initializable {

	@FXML private Label lblTitleStudy;
	@FXML private Button bttSave;

	//Aba Topicos
	@FXML private ListView<String> listViewTopics;
	@FXML private Button bttSearchTopic;
	@FXML private Button bttAddTopic;
	@FXML private Button bttEditTopic;
	@FXML private Button bttRemoveTopic;

	//Aba Anotacoes
	@FXML private HTMLEditor editorTextMatter;
	
	private Study study;
	private ObservableList<String> listTopics = FXCollections.observableArrayList();
	private StudyService studyService = new StudyService();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listViewTopics.setItems(listTopics);
		
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

		bttSave.setOnMouseClicked((MouseEvent mouse) -> {
			if (mouse.getClickCount() == 1) {
				saveStudy();
			}
		});
		
		showInfoStudy();
	}
	
	private void showInfoStudy() {
		if (study != null) {
			lblTitleStudy.setText(study.getMatter());
			for(Topic topic : study.getListTopics()) {
				listTopics.add(topic.getTitle());
			}
			listViewTopics.refresh();
		}	
	}

	private void openTopic() {
		String titleTopic = listViewTopics.getSelectionModel().getSelectedItem();
		if (titleTopic != null) {
			for(Topic topic : study.getListTopics()) {
				if (topic.getTitle().equals(titleTopic)) {
					TopicControl topicControl = new TopicControl();
					topicControl.setTopicSelected(topic);
					TopicWindow topicWindow = new TopicWindow();
					topicWindow.buildAndShowScreen(topicControl, MainContainerWindow.getStage());
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
			topicRegisterWindow.buildAndShowScreen(MainContainerWindow.getStage());
			if (topicRegisterControl.getTopic() != null) {
				Topic topic = topicRegisterControl.getTopic();
				study.getListTopics().add(topic);
				studyService.updateListTopics(study, listTopics);
				listViewTopics.refresh();
			}
		} else {
			MessageInfoControl.setMsgUser("Não foi selecionado nenhum Estudo para\nadição de topicos. " +
					"Selecione primeiramente\num Estudo cadastrado e tente novamente.");
			MessageInfoWindow.buildAndShowScreen(MainContainerWindow.getStage());
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
				topicRegisterWindow.buildAndShowScreen(MainContainerWindow.getStage());
				if (topicRegisterControl.getTopic() != null) {
					Topic topicEdited = topicRegisterControl.getTopic();
					if (topicSelected.verifyUpdateInTitle(topicEdited)) {
						study.getListTopics().remove(topicSelected);
						study.getListTopics().add(topicEdited);
						studyService.updateListTopics(study, listTopics);
						listViewTopics.refresh();
					}
				}
			}
		}
	}

	private void saveStudy() {
        try {
            studyService.saveStudy(study);
        } catch (Exception e) {
			MessageInfoControl.setMsgUser(e.getMessage());
			MessageInfoWindow.buildAndShowScreen(MainContainerWindow.getStage());
        }
    }

	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}
}
