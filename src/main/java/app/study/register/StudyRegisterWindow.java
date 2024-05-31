package app.study.register;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class StudyRegisterWindow {
	
	private static Stage stage;
	private static Scene scene;
	private static AnchorPane root;

	public static AnchorPane getRoot() {
		return root;
	}

	public static void setRoot(AnchorPane root) {
		StudyRegisterWindow.root = root;
	}

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		StudyRegisterWindow.stage = stage;
	}

	public static Scene getScene() {
		return scene;
	}

	public static void setScene(Scene scene) {
		StudyRegisterWindow.scene = scene;
	}
	
	public static void buildAndShowScreen(Stage stageOwner) {
		buildScreen(stageOwner);
		showScreen();
	}
	
	public static void showScreen() {
		stage.showAndWait();
	}
	
	public static void buildScreen(Stage stageOwner) {
		buildAnchorPane();
		buildScene();
		buildStage(stageOwner);
	}

	private static AnchorPane buildAnchorPane() {
		FXMLLoader rootFxml = new FXMLLoader();
		rootFxml.setLocation(StudyRegisterWindow.class.getResource("StudyRegisterWindow.fxml"));
		rootFxml.setController(new StudyRegisterControl());
		
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
		stage.setTitle("Cadastro de Estudo");
	}
}
