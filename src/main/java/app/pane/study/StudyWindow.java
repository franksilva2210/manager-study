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

	private VBox root;
	private StudyControl controller;

	public VBox getRoot() {
		return root;
	}

	public void setRoot(VBox root) {
		this.root = root;
	}

	public StudyControl getController() {
		return controller;
	}

	public void setController(StudyControl controller) {
		this.controller = controller;
	}

	public VBox buildRoot() {
		FXMLLoader rootFxml = new FXMLLoader();
		rootFxml.setLocation(StudyWindow.class.getResource("StudyWindow.fxml"));
		rootFxml.setController(this.controller);
		
		try {
			root = rootFxml.load();
			return root;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
