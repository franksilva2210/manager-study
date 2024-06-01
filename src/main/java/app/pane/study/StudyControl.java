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
	@FXML private HTMLEditor editorTextMatter;
	@FXML private ListView<String> listViewTopics;
	@FXML private Button bttAddTopic;
	@FXML private Button bttEditTopic;
	@FXML private Button bttRemoveTopic;
	@FXML private Button bttSearchTopic;
	
	private static Study studySelected;
	private ObservableList<String> listTopics = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listViewTopics.setItems(listTopics);
		
		listViewTopics.setOnMouseClicked((MouseEvent mouse) -> {
			if (mouse.getClickCount() == 2) {
				selectTopic();
			}
		});

		bttAddTopic.setOnMouseClicked((MouseEvent mouse) -> {
			if (mouse.getClickCount() == 1) {
				addTopic();
			}
		});
		
		showInfoStudy();
	}
	
	private void showInfoStudy() {
		if (studySelected != null) {
			lblTitleStudy.setText(studySelected.getMatter());
			
			for(Topic topic : studySelected.getListTopics()) {
				listTopics.add(topic.getTitle());
			}
			
			listViewTopics.refresh();
		}	
	}

	private void addTopic() {
		if (studySelected != null) {
			TopicRegisterControl topicRegisterControl = new TopicRegisterControl();
			TopicRegisterWindow topicRegisterWindow = new TopicRegisterWindow();
			topicRegisterWindow.setController(topicRegisterControl);
			topicRegisterWindow.buildAndShowScreen(MainContainerWindow.getStage());
		} else {
			MessageInfoControl.setMsgUser("Não foi selecionado nenhum Estudo para\nadição de topicos. " +
					"Selecione primeiramente\num Estudo cadastrado e tente novamente.");
			MessageInfoWindow.buildAndShowScreen(MainContainerWindow.getStage());
		}
	}

	private void selectTopic() {
		String titleTopic = listViewTopics.getSelectionModel().getSelectedItem();
		if (titleTopic != null) {
			for(Topic topic : studySelected.getListTopics()) {
				if (topic.getTitle().equals(titleTopic)) {
					TopicControl topicControl = new TopicControl();
					topicControl.setTopicSelected(topic);
					TopicWindow topicWindow = new TopicWindow();
					topicWindow.buildAndShowScreen(topicControl, MainContainerWindow.getStage());
				}
			}
		}
	}

	public static Study getStudySelected() {
		return studySelected;
	}

	public static void setStudySelected(Study studySelected) {
		StudyControl.studySelected = studySelected;
	}
}
