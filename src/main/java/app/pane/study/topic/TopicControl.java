package app.pane.study.topic;

import java.net.URL;
import java.util.ResourceBundle;

import app.pane.PaneMainWindow;
import app.pane.study.topic.register.Topic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;

public class TopicControl implements Initializable {

	@FXML private Label lblTitleTopic;
	@FXML private HTMLEditor editorTextTopic;
    @FXML private ListView<String> listViewTopics;
    
    private Topic topicSelected;
	private ObservableList<String> listTopics = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listViewTopics.setItems(listTopics);

		listViewTopics.setOnMouseClicked((MouseEvent mouse) -> {
			if (mouse.getClickCount() == 2) {
				selectTopic();
			}
			listViewTopics.refresh();
		});
		
		showInfoTopic();
	}
	
	private void showInfoTopic() {
		lblTitleTopic.setText(topicSelected.getTitle());	
		for (Topic topic : topicSelected.getListTopics()) {
			listTopics.add(topic.getTitle());
		}
		listViewTopics.refresh();
	}

	private void selectTopic() {
		String titleTopic = listViewTopics.getSelectionModel().getSelectedItem();
		if (titleTopic != null) {
			for(Topic topic : topicSelected.getListTopics()) {
				if (topic.getTitle().equals(titleTopic)) {
					TopicControl topicControl = new TopicControl();
					topicControl.setTopicSelected(topic);
					TopicWindow topicWindow = new TopicWindow();
					topicWindow.buildAndShowScreen(topicControl, PaneMainWindow.getStage());
				}
			}
		}
	}

	public Topic getTopicSelected() {
		return topicSelected;
	}

	public void setTopicSelected(Topic topicSelected) {
		this.topicSelected = topicSelected;
	}
	
}
