package app.pane.study;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class StudyWindow {
	private static Stage stage;
	private static Scene scene;
	private static VBox root;

	public static VBox getRoot() {
		return root;
	}

	public static void setRoot(VBox root) {
		StudyWindow.root = root;
	}

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		StudyWindow.stage = stage;
	}

	public static Scene getScene() {
		return scene;
	}

	public static void setScene(Scene scene) {
		StudyWindow.scene = scene;
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

	public static VBox buildVBox() {
		FXMLLoader rootFxml = new FXMLLoader();
		rootFxml.setLocation(StudyWindow.class.getResource("StudyWindow.fxml"));
		rootFxml.setController(new StudyControl());
		
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
		stage.setTitle("Estudo");
	}
}
