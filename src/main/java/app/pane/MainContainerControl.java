package app.pane;

import java.net.URL;
import java.util.ResourceBundle;

import app.pane.liststudy.ListStudyWindow;
import app.pane.study.StudyWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class MainContainerControl implements Initializable {
	
	@FXML
    private VBox paneListStudy;
	
	@FXML
	private VBox paneStudy;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		paneListStudy.getChildren().add(ListStudyWindow.buildVBox());
		paneStudy.getChildren().add(StudyWindow.buildVBox());
	}
	
}
