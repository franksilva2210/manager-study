package app.pane.liststudy;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class ListStudyWindow {
	
	private VBox root;
	private ListStudyControl controller;

	public VBox getRoot() {
		return root;
	}

	public void setRoot(VBox root) {
		this.root = root;
	}

	public ListStudyControl getController() {
		return controller;
	}

	public void setController(ListStudyControl controller) {
		this.controller = controller;
	}

	public VBox buildRoot() {
		FXMLLoader rootFxml = new FXMLLoader();
		rootFxml.setLocation(ListStudyWindow.class.getResource("ListStudyWindow.fxml"));
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
