package app.ui.main;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class ScreenMainWindow {
	
	private Stage stage;
	private Scene scene;
	private Parent root;

	public ScreenMainWindow(Stage primaryStage) {
		stage = primaryStage;
		stage.setResizable(false);

		ScreenMainController screenMainController = new ScreenMainController();
		screenMainController.setStage(stage);

		FXMLLoader rootFxml = new FXMLLoader();
		rootFxml.setLocation(ScreenMainWindow.class.getResource("ScreenMainWindow.fxml"));
		rootFxml.setController(screenMainController);

		try {
			root = rootFxml.load();
		} catch (IOException e) {
			throw new RuntimeException("Erro ao carregar FXML", e);
		}

		scene = new Scene(root);
		JMetro jMetro = new JMetro();
		jMetro.setStyle(Style.LIGHT);
		jMetro.setScene(scene);

		stage.setScene(scene);
	}

	public void show() {
		stage.show();
	}
}
