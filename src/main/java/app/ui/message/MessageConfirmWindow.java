package app.ui.message;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class MessageConfirmWindow {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private MessageConfirmController controller;

	public MessageConfirmWindow(Stage stageOwner, MessageConfirmController controller) {
		stage = new Stage();
		stage.setTitle("Confirmação");
		stage.setResizable(false);

		if (stageOwner != null) {
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(stageOwner);
		}

		this.controller = controller;
		controller.setStage(stage);

		FXMLLoader rootFxml = new FXMLLoader();
		rootFxml.setLocation(MessageConfirmWindow.class.getResource("MessageConfirmWindow.fxml"));
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

	public Parent getRoot() {
		return root;
	}

	public void setRoot(Parent root) {
		this.root = root;
	}

	public MessageConfirmController getController() {
		return controller;
	}

	public void setController(MessageConfirmController controller) {
		this.controller = controller;
	}

	public void showScreen() {
		stage.showAndWait();
	}
}
