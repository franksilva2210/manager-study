package app;

import app.pane.PaneMainControl;
import app.pane.PaneMainWindow;
import app.pane.liststudy.ListStudyControl;
import app.pane.liststudy.ListStudyWindow;
import app.pane.menutop.MenuTopControl;
import app.pane.menutop.MenuTopWindow;
import app.pane.study.StudyControl;
import app.pane.study.StudyWindow;

public class AppLaunch {

	public static void main(String[] args) {
		MenuTopWindow menuTopWindow = new MenuTopWindow();
		MenuTopControl menuTopControl = new MenuTopControl();
		menuTopWindow.setController(menuTopControl);

		ListStudyControl listStudyControl = new ListStudyControl();
		ListStudyWindow listStudyWindow = new ListStudyWindow();
		listStudyWindow.setController(listStudyControl);

		StudyControl studyControl = new StudyControl();
		StudyWindow studyWindow = new StudyWindow();
		studyWindow.setController(studyControl);

		PaneMainControl.setMenuTopWindow(menuTopWindow);
		PaneMainControl.setListStudyWindow(listStudyWindow);
		PaneMainControl.setStudyWindow(studyWindow);

		PaneMainWindow.main(args);
	}

}