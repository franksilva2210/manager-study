package app.pane;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class MainContainerWindow extends Application {
	
	private static Stage stage;
	private static Scene scene;
	private static AnchorPane root;

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		MainContainerWindow.stage = stage;
	}

	public static Scene getScene() {
		return scene;
	}

	public static void setScene(Scene scene) {
		MainContainerWindow.scene = scene;
	}

	public static AnchorPane getRoot() {
		return root;
	}

	public static void setRoot(AnchorPane root) {
		MainContainerWindow.root = root;
	}
	
	public static void main(String[] args) {
        launch();
    }
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		buildAndShowScreen(primaryStage);
	}
	
	public static void buildAndShowScreen(Stage primaryStage) {
		buildScreen(primaryStage);
		showScreen();
	}
	
	public static void showScreen() {
		stage.show();
	}
	
	public static void buildScreen(Stage primaryStage) {
		buildRoot();
		buildScene();
		buildStage(primaryStage);
	}

	private static void buildRoot() {
		FXMLLoader rootFxml = new FXMLLoader();
		rootFxml.setLocation(MainContainerWindow.class.getResource("MainContainerWindow.fxml"));
		rootFxml.setController(new MainContainerControl());
		
		try {
			root = rootFxml.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private static void buildScene() {
		scene = new Scene(root);
		JMetro jMetro = new JMetro();
		jMetro.setStyle(Style.LIGHT);
		jMetro.setScene(scene);
	}
		
	private static void buildStage(Stage primaryStage) {
		stage = primaryStage;
		stage.setScene(scene);
		stage.setResizable(false);
	}
}
