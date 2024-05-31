package app.pane.study.topic;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class TopicWindow {
	
	private Stage stage;
	private Scene scene;
	private AnchorPane root;

	public AnchorPane getRoot() {
		return root;
	}

	public void setRoot(AnchorPane root) {
		this.root = root;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public void buildAndShowScreen(TopicControl topicControl, Stage stageOwner) {
		buildScreen(topicControl, stageOwner);
		showScreen();
	}
	
	public void showScreen() {
		stage.showAndWait();
	}
	
	public void buildScreen(TopicControl topicControl, Stage stageOwner) {
		buildVBox(topicControl);
		buildScene();
		buildStage(stageOwner);
	}

	private AnchorPane buildVBox(TopicControl topicControl) {
		FXMLLoader rootFxml = new FXMLLoader();
		rootFxml.setLocation(TopicWindow.class.getResource("TopicWindow.fxml"));
		rootFxml.setController(topicControl);
		
		try {
			root = rootFxml.load();
			return root;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void buildScene() {
		scene = new Scene(root);
		JMetro jMetro = new JMetro();
		jMetro.setStyle(Style.LIGHT);
		jMetro.setScene(scene);
	}
		
	private void buildStage(Stage stageOwner) {
		stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(stageOwner);
		stage.setTitle("Topico");
	}
}
