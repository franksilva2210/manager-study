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
	
	private static Stage stage;
	private static Scene scene;
	private static AnchorPane root;

	public static AnchorPane getRoot() {
		return root;
	}

	public static void setRoot(AnchorPane root) {
		TopicWindow.root = root;
	}

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		TopicWindow.stage = stage;
	}

	public static Scene getScene() {
		return scene;
	}

	public static void setScene(Scene scene) {
		TopicWindow.scene = scene;
	}
	
	public static void buildAndShowScreen(Stage stageOwner) {
		buildScreen(stageOwner);
		showScreen();
	}
	
	public static void showScreen() {
		stage.showAndWait();
	}
	
	public static void buildScreen(Stage stageOwner) {
		buildVBox();
		buildScene();
		buildStage(stageOwner);
	}

	private static AnchorPane buildVBox() {
		FXMLLoader rootFxml = new FXMLLoader();
		rootFxml.setLocation(TopicWindow.class.getResource("TopicWindow.fxml"));
		rootFxml.setController(new TopicControl());
		
		try {
			root = rootFxml.load();
			return root;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static void buildScene() {
		scene = new Scene(root);
		JMetro jMetro = new JMetro();
		jMetro.setStyle(Style.LIGHT);
		jMetro.setScene(scene);
	}
		
	private static void buildStage(Stage stageOwner) {
		stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(stageOwner);
		stage.setTitle("Topico");
	}
}
