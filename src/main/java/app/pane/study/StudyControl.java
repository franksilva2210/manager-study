package app.pane.study;

import java.net.URL;
import java.util.ResourceBundle;

import app.pane.MainContainerWindow;
import app.pane.study.topic.TopicControl;
import app.pane.study.topic.TopicWindow;
import app.study.register.Study;
import app.study.register.Topic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;

public class StudyControl implements Initializable {

	@FXML private Label lblTitleStudy;
	@FXML private HTMLEditor editorTextMatter;
	@FXML private ListView<String> listViewTopics;
	
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
