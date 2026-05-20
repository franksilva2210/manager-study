package app.ui.main;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class MainWindow {
	
	private Stage stage;
	private Scene scene;
	private Parent root;

	public MainWindow(Stage primaryStage) {
		FXMLLoader rootFxml = new FXMLLoader();
		rootFxml.setLocation(MainWindow.class.getResource("MainWindow.fxml"));
		rootFxml.setController(new MainController());

		try {
			root = rootFxml.load();
		} catch (IOException e) {
			throw new RuntimeException("Erro ao carregar FXML", e);
		}

		scene = new Scene(root);
		JMetro jMetro = new JMetro();
		jMetro.setStyle(Style.LIGHT);
		jMetro.setScene(scene);

		stage = primaryStage;
		stage.setScene(scene);
		stage.setResizable(false);
	}

	public void show() {
		stage.show();
	}
}
