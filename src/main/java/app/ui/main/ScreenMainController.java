package app.ui.main;

import java.net.URL;
import java.util.*;

import app.application.study.StudyDTO;
import app.application.document.DocumentDTO;
import app.application.topic.TopicDTO;
import app.application.study.StudyNavigationService;
import app.ui.backup.ScreenBackupController;
import app.ui.backup.ScreenBackupWindow;
import app.ui.document.edit.EditorDocumentController;
import app.ui.document.edit.EditorDocumentWindow;
import app.ui.message.MessageConfirmController;
import app.ui.message.MessageConfirmWindow;
import app.ui.message.MessageInfoController;
import app.ui.message.MessageInfoWindow;
import app.ui.roadmap.RoadMapController;
import app.ui.roadmap.RoadMapWindow;
import app.ui.study.register.RegisterStudyController;
import app.ui.study.register.RegisterStudyWindow;
import app.ui.topic.register.RegisterTopicController;
import app.ui.topic.register.RegisterTopicWindow;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScreenMainController implements Initializable {

	//Menu superior

	@FXML
	private MenuItem menuNewStudy;

	@FXML
	private MenuItem menuBackup;

	@FXML
	private MenuItem menuClose;

	//Menu Lateral

	@FXML
	private TextField txtSearch;

	@FXML
	private Button bttSearch;

	@FXML
	private TreeView<Object> treeView;

	@FXML
	private TabPane tabPaneStudy;

	//Tela central principal

	@FXML
	private Button bttNavigationLeft;

	@FXML
	private Button bttNavigationRight;

	@FXML
	private TextField txtHierarchyPath;

	@FXML
	private Tab tabMain;

	@FXML
	private Label lblTitleMain;

	@FXML
	private Button bttRoadMap;

	@FXML
	private Button bttSearchTopic;

	@FXML
	private Button bttAddTopic;

	@FXML
	private Button bttEditTopic;

	@FXML
	private Button bttRemoveTopic;

	@FXML
	private ListView<TopicDTO> listViewTopics;

	@FXML
	private Tab tabAdd;

	private Stage stage;
	private ObservableList<TopicDTO> observableListTopics = FXCollections.observableArrayList();
	private ScreenMainService screenMainService = new ScreenMainService();
	private ScreenMainUIHelper uiHelper = new ScreenMainUIHelper();
	private Object objectCurrentSelected;
	private StudyNavigationService navigationService = new StudyNavigationService();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		listViewTopics.setItems(observableListTopics);

		menuNewStudy.setOnAction(event -> {
			newStudy();
		});

		menuBackup.setOnAction(event -> {
			openScreenBackup();
		});

		menuClose.setOnAction(event -> {
			Platform.exit();
		});

		txtSearch.setOnAction(event -> {
//			searchStudy();
		});

		treeView.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				selectItemMenuLeft();
			}
		});

		bttNavigationLeft.setOnAction(event -> {
			navigateBack();
		});

		bttNavigationRight.setOnAction(event -> {
			navigateForward();
		});

		bttRoadMap.setOnAction(event -> {
			showRoadMap();
		});

		tabAdd.setOnSelectionChanged(event -> {
			if (tabAdd.isSelected()) {
				addNewTabDocument(new DocumentDTO());
			}
		});

		listViewTopics.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				selectItemListView();
			}
		});

		bttAddTopic.setOnAction(event -> {
			newTopic();
		});

		bttEditTopic.setOnAction(event -> {
			editTopic();
		});

		bttRemoveTopic.setOnAction(event -> {
			removeTopic();
		});

		initUI();

		refreshStudies();
	}

	private void openScreenBackup() {
		if (!confirmChangeStudyOrTopic())
			return;

		ScreenBackupController controller = new ScreenBackupController();
		ScreenBackupWindow window = new ScreenBackupWindow(stage, controller);
		window.showScreen();
		if (controller.isSucessRestore()) {
			Platform.exit();
		}
	}

	private void newStudy() {
		RegisterStudyController controller =
				new RegisterStudyController();

		controller.setStudyDto(new StudyDTO());

		RegisterStudyWindow registerStudyWindow =
				new RegisterStudyWindow(stage, controller);

		registerStudyWindow.showScreen();

		if (controller.getStudyDto().getId() != null &&
			controller.getStudyDto().getId() > 0) {
			refreshStudies();
		}
	}

	public void editStudy(Object objectSelected) {
		StudyDTO studyDto = (StudyDTO) objectSelected;

		RegisterStudyController controller = new RegisterStudyController();
		controller.setStudyDto(studyDto);

		RegisterStudyWindow window = new RegisterStudyWindow(stage, controller);
		window.showScreen();

		StudyDTO studyDtoUpdated = controller.getStudyDto();

		navigationService.refreshItem(studyDtoUpdated);
		refreshStudies();
		loadDataScreen();
	}

	private void removeStudy(Object objectDeletion) {
		StudyDTO studyDeletionDto = (StudyDTO) objectDeletion;

		MessageConfirmController controller = new MessageConfirmController();
		controller.setConfirm(false);
		controller.setMsgUser(
				"Deseja realmente remover o estudo selecionado?\n" +
				"Todos os tópicos de: " + studyDeletionDto.getMatter().toUpperCase() + ", também " +
				"serão removidos!"
		);

		MessageConfirmWindow window = new MessageConfirmWindow(stage, controller);
		window.showScreen();

		if (controller.getConfirm()) {
			screenMainService.removeStudy(studyDeletionDto);
			navigationService.removeStudy(studyDeletionDto);
			objectCurrentSelected = null;
			refreshStudies();
			loadDataScreen();
		}
	}

	private void newTopic() {
		if (objectCurrentSelected == null) {
			return;
		}

		RegisterTopicController registerTopicController = new RegisterTopicController();
		registerTopicController.setTopicDto(new TopicDTO());

		if (objectCurrentSelected instanceof StudyDTO studyDto) {
			registerTopicController.setStudy(studyDto);
		} else if (objectCurrentSelected instanceof TopicDTO topicDto) {
			registerTopicController.setTopicParent(topicDto);
		}

		RegisterTopicWindow registerTopicWindow = new RegisterTopicWindow(stage, registerTopicController);
		registerTopicWindow.showScreen();

		if (registerTopicController.getTopicDto().getId() != null &&
				registerTopicController.getTopicDto().getId() > 0) {
			refreshObjectCurrentSelected();
			loadDataScreen();
		}
	}

	public void editTopic() {
		TopicDTO topicSelectedDto =
				listViewTopics.getSelectionModel().getSelectedItem();

		if (objectCurrentSelected == null || topicSelectedDto == null) {
			return;
		}

		RegisterTopicController registerTopicController = new RegisterTopicController();
		registerTopicController.setTopicDto(topicSelectedDto);

		RegisterTopicWindow registerTopicWindow =
				new RegisterTopicWindow(stage, registerTopicController);

		registerTopicWindow.showScreen();

		TopicDTO topicUpdatedDto = registerTopicController.getTopicDto();

		navigationService.refreshItem(topicUpdatedDto);
		refreshObjectCurrentSelected();
		loadDataScreen();
	}

	private void removeTopic() {
		TopicDTO topicSelectedDto = listViewTopics.getSelectionModel().getSelectedItem();

		if (objectCurrentSelected == null || topicSelectedDto == null) {
			return;
		}

		MessageConfirmController controller = new MessageConfirmController();
		controller.setConfirm(false);
		controller.setMsgUser(
				"Deseja realmente remover o tópico selecionado?\n" +
				"Todos os tópicos de: " + topicSelectedDto.getTitle().toUpperCase() + ", também " +
				"serão removidos!"
		);

		MessageConfirmWindow window = new MessageConfirmWindow(stage, controller);
		window.showScreen();

		if (controller.getConfirm()) {
			screenMainService.removeTopic(topicSelectedDto);
			refreshObjectCurrentSelected();
			loadDataScreen();
		}
	}

	public void refreshStudies() {
		List<StudyDTO> listStudyDTO = screenMainService.consultAllStudyDto();
		uiHelper.generateTreeItems(treeView, listStudyDTO);
	}

	private void selectItemMenuLeft() {
		if (!confirmChangeStudyOrTopic())
			return;

		TreeItem<Object> itemSelected = treeView.getSelectionModel().getSelectedItem();
		if (itemSelected != null) {
			objectCurrentSelected = itemSelected.getValue();
			refreshObjectCurrentSelected();
			navigationService.navigateTo(objectCurrentSelected);
			loadDataScreen();
		}
	}

	private void selectItemListView() {
		if (!confirmChangeStudyOrTopic())
			return;

		TopicDTO topicSelected = listViewTopics.getSelectionModel().getSelectedItem();
		if (topicSelected != null) {
			objectCurrentSelected = topicSelected;
			refreshObjectCurrentSelected();
			navigationService.navigateTo(objectCurrentSelected);
			loadDataScreen();
		}
	}

	private void navigateBack() {
		if (!confirmChangeStudyOrTopic())
			return;

		objectCurrentSelected = navigationService.back();
		refreshObjectCurrentSelected();
		loadDataScreen();
	}

	private void navigateForward() {
		if (!confirmChangeStudyOrTopic())
			return;

		objectCurrentSelected = navigationService.forward();
		refreshObjectCurrentSelected();
		loadDataScreen();
	}

	private void refreshObjectCurrentSelected() {
		if (objectCurrentSelected instanceof StudyDTO studyDto) {
			objectCurrentSelected = screenMainService.loadStudy(studyDto.getId());
		} else if (objectCurrentSelected instanceof TopicDTO topicDto) {
			objectCurrentSelected = screenMainService.loadTopic(topicDto.getId());
		}
	}

	public void loadDataScreen() {
		boolean canGoBack = navigationService.canGoBack();
		boolean canGoForward = navigationService.canGoForward();
		String hierarchyPath = navigationService.getHierarchyPath();

		uiHelper.updateNavigationButtons(bttNavigationLeft, bttNavigationRight, canGoBack, canGoForward);
		uiHelper.updateTxtHierarchyPath(txtHierarchyPath, hierarchyPath);
		uiHelper.updateTitleItemMain(lblTitleMain, objectCurrentSelected);
		uiHelper.updateTabs(tabPaneStudy, tabMain, tabAdd, objectCurrentSelected, this::createNewTabDocument);
		uiHelper.updateListViewTopics(observableListTopics, listViewTopics, objectCurrentSelected);
	}

	private void addNewTabDocument(DocumentDTO documentDto) {
		if (objectCurrentSelected == null) {
			MessageInfoController controller = new MessageInfoController();
			controller.setMsgUser(
					"Selecione primeiro um estudo para adicionar\n" +
					"um novo texto."
			);

			MessageInfoWindow window = new MessageInfoWindow(stage, controller);
			window.showScreen();

			Tab tabMain = tabPaneStudy.getTabs().get(0);
			tabPaneStudy.getSelectionModel().select(tabMain);

		} else {
			Tab newTab = createNewTabDocument(documentDto);
			tabPaneStudy.getSelectionModel().select(newTab);
		}
	}

	private Tab createNewTabDocument(DocumentDTO documentDto) {
		EditorDocumentController editorDocumentController = new EditorDocumentController();

		Label lblTitle = new Label();

		editorDocumentController.setLblTitle(lblTitle);
		editorDocumentController.setDocumentDto(documentDto);
		editorDocumentController.setStage(stage);

		if (objectCurrentSelected instanceof StudyDTO studyDto) {
			editorDocumentController.setStudyDto(studyDto);
		} else if (objectCurrentSelected instanceof TopicDTO topicDto) {
			editorDocumentController.setTopicDto(topicDto);
		}

		EditorDocumentWindow editorDocumentWindow = new EditorDocumentWindow(editorDocumentController);

		int indexTabs = tabPaneStudy.getTabs().indexOf(tabAdd);
		VBox root = editorDocumentWindow.getRoot();

		Tab newTab = uiHelper.createNewTab(indexTabs, root, lblTitle, documentDto);

		editorDocumentController.setTab(newTab);
		editorDocumentController.setTabPaneStudy(tabPaneStudy);

		newTab.setUserData(editorDocumentController);

		tabPaneStudy.getTabs().add(indexTabs, newTab);

		return newTab;
	}

	private void showRoadMap() {
		RoadMapController roadMapController = new RoadMapController();
		roadMapController.setObjectCurrentSelected(objectCurrentSelected);
		RoadMapWindow roadMapWindow	= new RoadMapWindow(stage, roadMapController);
		roadMapWindow.showScreen();
	}

	private boolean confirmChangeStudyOrTopic() {
		boolean existDocumentEditing = false;
		EditorDocumentController editorDocumentController = null;

		for (Tab tab : tabPaneStudy.getTabs()) {
			if (tab == tabMain || tab == tabAdd) {
				continue;
			}

			editorDocumentController = (EditorDocumentController) tab.getUserData();

			if (editorDocumentController != null && editorDocumentController.isEditing()) {
				existDocumentEditing = true;
				break;
			}
		}

		if (existDocumentEditing) {
			MessageConfirmController controller = new MessageConfirmController();
			controller.setConfirm(false);
			controller.setMsgUser(
					"Existem Documentos com alterações não salvas.\n" +
					"Deseja continuar mesmo assim?\n\n" +
					"Documento editando: " + editorDocumentController.getDocumentDto().getTitle()
			);

			MessageConfirmWindow window = new MessageConfirmWindow(stage, controller);
			window.showScreen();

			return controller.getConfirm();
		}

		return true;
	}

	private void initUI() {
		uiHelper.initTreeView(treeView, this::editStudy, this::removeStudy);
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Stage getStage() {
		return stage;
	}
}