package app.pane.liststudy;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class ListStudyWindow {
	
	private static VBox root;

	public static VBox getRoot() {
		return root;
	}

	public static void setRoot(VBox root) {
		ListStudyWindow.root = root;
	}

	public static VBox buildVBox() {
		FXMLLoader rootFxml = new FXMLLoader();
		rootFxml.setLocation(ListStudyWindow.class.getResource("ListStudyWindow.fxml"));
		rootFxml.setController(new ListStudyControl());
		
		try {
			root = rootFxml.load();
			return root;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
