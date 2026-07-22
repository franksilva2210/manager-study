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

	private final ScreenMainState state = new ScreenMainState();
	private final Breadcrumb breadcrumb = new Breadcrumb();

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

		stage.setOnCloseRequest(event -> {
			boolean confirm = confirmChangeStudyOrTopic();
			if (!confirm) {
				event.consume();
			}
		});

		initContextScreen();
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

	/* Helpers */

	private void initContextScreen() {
		paneLeftController = new PaneLeftController(stage, state, this, breadcrumb);
		paneRightController = new PaneRightController(stage, state, this, breadcrumb);

		paneLeftController.setPaneRightController(paneRightController);
		paneRightController.setPaneLeftController(paneLeftController);

		PaneLeftWindow paneLeftWindow = new PaneLeftWindow(paneLeftController);
		PaneRightWindow paneRightWindow = new PaneRightWindow(paneRightController);

		paneRightController.loadPaneTopics();

		menuLeft.getChildren().setAll(paneLeftWindow.getRoot());
		paneRight.getChildren().setAll(paneRightWindow.getRoot());
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
					"Documento editando: " + editorDocumentController.getState().getTitle()
			);

			MessageConfirmWindow window = new MessageConfirmWindow(stage, controller);
			window.showScreen();

			return controller.getConfirm();
		}

		return true;
	}

	public void openItemSelected(Object itemSelected) {
		breadcrumb.navigate(itemSelected);
		state.loadItemSelected(itemSelected, breadcrumb);
	}

	public void navigateForward() {
		Object itemForward = breadcrumb.forward();
		state.loadItemSelected(itemForward, breadcrumb);
	}

	public void navigateBack() {
		Object itemBack = breadcrumb.back();
		state.loadItemSelected(itemBack, breadcrumb);
	}

	public void refreshHierarchyPath(Object itemSelected, ModeUpdateItem modeUpdateItem) {
		if (modeUpdateItem == ModeUpdateItem.UPDATE) {
			breadcrumb.refreshItem(itemSelected);
		} else if (modeUpdateItem == ModeUpdateItem.REMOVE) {
			breadcrumb.removeItem(itemSelected);
		}
	}
}