package app.ui.study.register;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class RegisterStudyWindow {
	
	private Stage stage;
	private Scene scene;
	private AnchorPane root;
	private RegisterStudyController controller;

	public RegisterStudyWindow(Stage stageOwner) {
		stage = new Stage();
		stage.setTitle("Novo Estudo");
		stage.setResizable(false);

		if (stageOwner != null) {
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(stageOwner);
		}

		controller = new RegisterStudyController();
		controller.setStage(stage);

		FXMLLoader rootFxml = new FXMLLoader();
		rootFxml.setLocation(RegisterStudyWindow.class.getResource("StudyRegisterWindow.fxml"));
		rootFxml.setController(controller);

		try {
			root = rootFxml.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		scene = new Scene(root);
		JMetro jMetro = new JMetro();
		jMetro.setStyle(Style.LIGHT);
		jMetro.setScene(scene);

		stage.setScene(scene);
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

	public AnchorPane getRoot() {
		return root;
	}

	public void setRoot(AnchorPane root) {
		this.root = root;
	}

	public RegisterStudyController getController() {
		return controller;
	}

	public void showScreen() {
		stage.showAndWait();
	}
}
