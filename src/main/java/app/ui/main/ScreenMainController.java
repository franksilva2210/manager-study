package app.ui.main;

import java.net.URL;
import java.util.*;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.application.study.StudyNavigationService;
import app.ui.study.register.RegisterStudyController;
import app.ui.study.register.RegisterStudyWindow;
import app.ui.topic.register.RegisterTopicController;
import app.ui.topic.register.RegisterTopicWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ScreenMainController implements Initializable {

	//Menu superior

	@FXML
	private MenuItem menuNewStudy;

	@FXML
	private MenuItem menuClose;

	//Menu Lateral

	@FXML
	private TextField txtSearch;

	@FXML
	private Button bttSearch;

	@FXML
	private TreeView<Object> treeStudies;

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
	private AnchorPane paneText;

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

		txtSearch.setOnAction(event -> {
//			searchStudy();
		});

		treeStudies.setOnMouseClicked(event -> {
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

		tabAdd.setOnSelectionChanged(event -> {
			if (tabAdd.isSelected()) {
				createNewTab();
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

		loadStudies();

		boolean canGoBack = navigationService.canGoBack();
		boolean canGoForward = navigationService.canGoForward();
		uiHelper.updateNavigationButtons(bttNavigationLeft, bttNavigationRight, canGoBack, canGoForward);

		loadTabText();
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
			loadStudies();
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
		loadStudies();
		showData();
	}

	private void removeStudy(Object objectDeletion) {
		StudyDTO studyDeletionDto = (StudyDTO) objectDeletion;

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirmação");
		alert.setHeaderText("Remover estudo");
		alert.setContentText(
				"Deseja realmente remover o estudo selecionado?\n" +
				"Todos os tópicos de: " + studyDeletionDto.getMatter().toUpperCase() + ", também " +
				"serão removidos!"
		);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.OK) {
			screenMainService.removeStudy(studyDeletionDto);
			navigationService.removeStudy(studyDeletionDto);
			objectCurrentSelected = null;
			loadStudies();
			showData();
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
			showData();
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
		showData();
	}

	private void removeTopic() {
		TopicDTO topicSelectedDto =
				listViewTopics.getSelectionModel().getSelectedItem();

		if (objectCurrentSelected == null || topicSelectedDto == null) {
			return;
		}

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirmação");
		alert.setHeaderText("Remover Tópico");
		alert.setContentText(
				"Deseja realmente remover o tópico selecionado?\n" +
				"Todos os tópicos de: " + topicSelectedDto.getTitle().toUpperCase() + ", também " +
				"serão removidos!"
		);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			screenMainService.removeTopic(topicSelectedDto);
			//atualizar navigation, também remover topico da navegação
			refreshObjectCurrentSelected();
			showData();
		}
	}

	public void loadStudies() {
		List<StudyDTO> listStudyDTO = screenMainService.consultAllStudyDto();
		uiHelper.generateTreeItem(treeStudies, listStudyDTO);
		uiHelper.configureContextMenu(treeStudies, this::editStudy, this::removeStudy);
	}

	private void selectItemMenuLeft() {
		TreeItem<Object> itemSelected = treeStudies.getSelectionModel().getSelectedItem();
		if (itemSelected != null) {
			objectCurrentSelected = itemSelected.getValue();
			refreshObjectCurrentSelected();
			navigationService.navigateTo(objectCurrentSelected);
			showData();
		}
	}

	private void selectItemListView() {
		TopicDTO topicSelected = listViewTopics.getSelectionModel().getSelectedItem();
		if (topicSelected != null) {
			objectCurrentSelected = topicSelected;
			refreshObjectCurrentSelected();
			navigationService.navigateTo(objectCurrentSelected);
			showData();
		}
	}

	private void navigateBack() {
		objectCurrentSelected = navigationService.back();
		refreshObjectCurrentSelected();
		showData();
	}

	private void navigateForward() {
		objectCurrentSelected = navigationService.forward();
		refreshObjectCurrentSelected();
		showData();
	}

	private void refreshObjectCurrentSelected() {
		if (objectCurrentSelected instanceof StudyDTO studyDto) {
			objectCurrentSelected = screenMainService.loadStudy(studyDto.getId());
		} else if (objectCurrentSelected instanceof TopicDTO topicDto) {
			objectCurrentSelected = screenMainService.loadTopic(topicDto.getId());
		}
	}

	private void showData() {
		boolean canGoBack = navigationService.canGoBack();
		boolean canGoForward = navigationService.canGoForward();
		String hierarchyPath = navigationService.getHierarchyPath();

		uiHelper.updateNavigationButtons(bttNavigationLeft, bttNavigationRight, canGoBack, canGoForward);
		uiHelper.updateTxtHierarchyPath(txtHierarchyPath, hierarchyPath);
		uiHelper.updateTitleItemMain(lblTitleMain, objectCurrentSelected);
		uiHelper.updateListViewTopics(observableListTopics, listViewTopics, objectCurrentSelected);
	}

	private void createNewTab() {
		uiHelper.createNewTab(tabPaneStudy, tabAdd);
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private void loadTabText() {
		ManagerTextController managerTextController =
				new ManagerTextController();

		managerTextController.setPaneText(paneText);

		ManagerTextWindow managerTextWindow =
				new ManagerTextWindow(managerTextController);

		paneText.getChildren().setAll(managerTextWindow.getRoot());
	}

}
