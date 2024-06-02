package app;

import app.pane.PaneMainControl;
import app.pane.PaneMainWindow;
import app.pane.liststudy.ListStudyControl;
import app.pane.liststudy.ListStudyWindow;
import app.pane.study.StudyControl;
import app.pane.study.StudyWindow;

public class AppLaunch {

	public static void main(String[] args) {
		ListStudyControl listStudyControl = new ListStudyControl();
		ListStudyWindow listStudyWindow = new ListStudyWindow();
		listStudyWindow.setController(listStudyControl);

		StudyControl studyControl = new StudyControl();
		StudyWindow studyWindow = new StudyWindow();
		studyWindow.setController(studyControl);

		PaneMainControl.setListStudyWindow(listStudyWindow);
		PaneMainControl.setStudyWindow(studyWindow);

		PaneMainWindow.main(args);
	}

}