package app.pane.study.topic;

import java.net.URL;
import java.util.ResourceBundle;

import app.study.register.Topic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.web.HTMLEditor;

public class TopicControl implements Initializable {
	
	@FXML
    private HTMLEditor editorTextTopic;

    @FXML
    private Label lblTitleTopic;

    @FXML
    private ListView<String> listViewTopics;
    
    private static Topic topicSelected;
	private ObservableList<String> listTopics = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listViewTopics.setItems(listTopics);
		
		showInfoTopic();
	}
	
	private void showInfoTopic() {
		lblTitleTopic.setText(topicSelected.getTitle());	
		for (Topic topic : topicSelected.getListSubTopics()) {
			listTopics.add(topic.getTitle());
		}
		listViewTopics.refresh();
	}

	public static Topic getTopicSelected() {
		return topicSelected;
	}

	public static void setTopicSelected(Topic topicSelected) {
		TopicControl.topicSelected = topicSelected;
	}
	
}
