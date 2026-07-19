package app.ui.main;

import java.net.URL;
import java.util.*;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.ui.about.AboutWindow;
import app.ui.backup.ScreenBackupController;
import app.ui.backup.ScreenBackupWindow;
import app.ui.document.edit.EditorDocumentController;
import app.ui.message.MessageConfirmController;
import app.ui.message.MessageConfirmWindow;
import app.ui.pane.left.PaneLeftController;
import app.ui.pane.left.PaneLeftWindow;
import app.ui.pane.right.PaneRightController;
import app.ui.pane.right.PaneRightNavigator;
import app.ui.pane.right.PaneRightWindow;
import app.ui.pane.right.TabDocumentFactory;
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

	private final ScreenMainState state = new ScreenMainState();
	private final TabDocumentFactory tabDocumentFactory = new TabDocumentFactory();
	private final PaneRightNavigator navigator = new PaneRightNavigator();

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

		stage.setOnCloseRequest(event -> {
			boolean confirm = confirmChangeStudyOrTopic();
			if (!confirm) {
				event.consume();
			}
		});

	}

	private void newStudy() {
		RegisterStudyController controller = new RegisterStudyController();
		controller.setStudyDto(new StudyDTO());

		RegisterStudyWindow window = new RegisterStudyWindow(stage, controller);
		window.showScreen();

		StudyDTO studyCreateDto = controller.getStudyDto();
		if (studyCreateDto.getId() != null && studyCreateDto.getId() > 0) {
			paneLeftController.refreshStudies();
		}
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
		if (paneRightController == null || state.getItemSelected() == null)
			return;

		RoadMapController roadMapController = new RoadMapController();
		roadMapController.setItemSelected(state.getItemSelected());
		RoadMapWindow roadMapWindow	= new RoadMapWindow(stage, roadMapController);
		roadMapWindow.showScreen();
	}

	private void showAbout() {
		AboutWindow window = new AboutWindow(stage);
		window.showScreen();
	}

	public boolean confirmChangeStudyOrTopic() {
		EditorDocumentController editorDocumentController = paneRightController.verifyDocumentEditingOrNotSave();

		if (editorDocumentController != null) {
			String nameItem = null;
			if (state.getItemSelected() instanceof StudyDTO studyDto) {
				nameItem = studyDto.getMatter();
			} else if (state.getItemSelected() instanceof TopicDTO topicDto) {
				nameItem = topicDto.getTitle();
			}

			MessageConfirmController controller = new MessageConfirmController();
			controller.setConfirm(false);
			controller.setMsgUser(
					"Existem Documentos não salvos em:\n" +
					nameItem + "\n" +
					"Deseja continuar mesmo assim?\n" +
					"Documento editando: " + editorDocumentController.getDocumentDto().getTitle()
			);

			MessageConfirmWindow window = new MessageConfirmWindow(stage, controller);
			window.showScreen();

			return controller.getConfirm();
		}

		return true;
	}

	private void loadPaneRight() {
		paneRightController = new PaneRightController(stage, state, this, navigator);
		PaneRightWindow window = new PaneRightWindow(paneRightController);
		paneRight.getChildren().setAll(window.getRoot());
	}

	private void loadMenuLeft() {
		paneLeftController = new PaneLeftController(stage, state, this, navigator);
		PaneLeftWindow window = new PaneLeftWindow(paneLeftController);
		menuLeft.getChildren().setAll(window.getRoot());
	}

	private void connectControllers() {
		paneLeftController.setPaneRightController(paneRightController);
		paneRightController.setPaneLeftController(paneLeftController);
	}

}