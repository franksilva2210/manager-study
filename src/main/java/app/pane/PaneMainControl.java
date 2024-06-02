package app.pane;

import java.net.URL;
import java.util.ResourceBundle;

import app.pane.liststudy.ListStudyWindow;
import app.pane.study.StudyWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class PaneMainControl implements Initializable {
	
	@FXML
    private VBox paneListStudy;
	
	@FXML
	private VBox paneStudy;

	private static ListStudyWindow listStudyWindow;
	private static StudyWindow studyWindow;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildPaneListStudy();
		buildPaneStudy();
	}

	private void buildPaneListStudy() {
		listStudyWindow.buildRoot();
		paneListStudy.getChildren().add(listStudyWindow.getRoot());
	}

	private void buildPaneStudy() {
		studyWindow.buildRoot();
		paneStudy.getChildren().add(studyWindow.getRoot());
	}

	public static StudyWindow getStudyWindow() {
		return studyWindow;
	}

	public static void setStudyWindow(StudyWindow studyWindow) {
		PaneMainControl.studyWindow = studyWindow;
	}

	public static ListStudyWindow getListStudyWindow() {
		return listStudyWindow;
	}

	public static void setListStudyWindow(ListStudyWindow listStudyWindow) {
		PaneMainControl.listStudyWindow = listStudyWindow;
	}
}
