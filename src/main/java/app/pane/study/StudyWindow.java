package app.pane.study;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class StudyWindow {
	private static VBox root;

	public static VBox getRoot() {
		return root;
	}

	public static void setRoot(VBox root) {
		StudyWindow.root = root;
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
}
