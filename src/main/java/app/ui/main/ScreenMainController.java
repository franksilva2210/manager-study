package app.ui.main;

import java.net.URL;
import java.util.*;

import app.application.study.StudyDTO;
import app.ui.about.AboutWindow;
import app.ui.backup.ScreenBackupController;
import app.ui.backup.ScreenBackupWindow;
import app.ui.pane.left.PaneLeftController;
import app.ui.pane.left.PaneLeftWindow;
import app.ui.pane.right.PaneRightController;
import app.ui.pane.right.PaneRightWindow;
import app.ui.roadmap.RoadMapController;
import app.ui.roadmap.RoadMapWindow;
import app.ui.study.register.RegisterStudyController;
import app.ui.study.register.RegisterStudyWindow;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScreenMainController implements Initializable {

	@FXML
	private MenuItem menuNewStudy;

	@FXML
	private MenuItem menuBackup;

	@FXML
	private Menu menuRoadMap;

	@FXML
	private MenuItem menuAbout;

	@FXML
	private MenuItem menuClose;

	@FXML
	private VBox menuLeft;

	@FXML
	private VBox paneRight;

	private Stage stage;

	private PaneLeftController paneLeftController;

	private PaneRightController paneRightController;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public VBox getMenuLeft() {
		return menuLeft;
	}

	public VBox getPaneRight() {
		return paneRight;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		menuNewStudy.setOnAction(event -> {
			newStudy();
		});

		menuRoadMap.setOnAction(event -> {
			showRoadMap();
		});

		menuAbout.setOnAction(event -> {
			showAbout();
		});

		menuBackup.setOnAction(event -> {
			openScreenBackup();
		});

		menuClose.setOnAction(event -> {
			Platform.exit();
		});

		loadMenuLeft();

		loadPaneRight();

		connectControllers();

	}

	private void newStudy() {
		RegisterStudyController controller =
				new RegisterStudyController();

		controller.setStudyDto(new StudyDTO());

		RegisterStudyWindow registerStudyWindow =
				new RegisterStudyWindow(stage, controller);

		registerStudyWindow.showScreen();
	}

	private void openScreenBackup() {
		ScreenBackupController controller = new ScreenBackupController();
		ScreenBackupWindow window = new ScreenBackupWindow(stage, controller);
		window.showScreen();
		if (controller.isSucessRestore()) {
			Platform.exit();
		}
	}

	private void showRoadMap() {
		RoadMapController roadMapController = new RoadMapController();

		RoadMapWindow roadMapWindow	= new RoadMapWindow(stage, roadMapController);
		roadMapWindow.showScreen();
	}

	private void showAbout() {
		AboutWindow window = new AboutWindow(stage);
		window.showScreen();
	}

	private void loadPaneRight() {
		paneRightController = new PaneRightController();
		PaneRightWindow window = new PaneRightWindow(paneRightController);
		paneRight.getChildren().setAll(window.getRoot());
	}

	private void loadMenuLeft() {
		paneLeftController = new PaneLeftController();
		PaneLeftWindow window = new PaneLeftWindow(paneLeftController);
		menuLeft.getChildren().setAll(window.getRoot());
	}

	private void connectControllers() {
		paneLeftController.setPaneRightController(paneRightController);
	}

}