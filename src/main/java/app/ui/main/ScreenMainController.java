package app.ui.main;

import java.net.URL;
import java.util.*;

import app.application.study.dto.StudyDTO;
import app.application.topic.dto.TopicDTO;
import app.domain.study.Study;
import app.domain.study.navigation.StudyNavigationService;
import app.domain.topic.Topic;
import app.ui.study.register.RegisterStudyWindow;
import app.ui.topic.register.RegisterTopicController;
import app.ui.topic.register.RegisterTopicWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

		loadStudies();

		boolean canGoBack = navigationService.canGoBack(objectCurrentSelected);
		boolean canGoForward = navigationService.canGoForward();
		uiHelper.updateNavigationButtons(bttNavigationLeft, bttNavigationRight, canGoBack, canGoForward);
	}

	private void newStudy() {
		RegisterStudyWindow registerStudyWindow = new RegisterStudyWindow(stage);
		registerStudyWindow.showScreen();
		if (registerStudyWindow.getController().getStudy().getId() != null &&
			registerStudyWindow.getController().getStudy().getId() > 0) {
			loadStudies();
		}
	}

	public void loadStudies() {
		List<StudyDTO> listStudyDTO = screenMainService.consultAllStudyDto();
		uiHelper.configTreeItem(treeStudies, listStudyDTO);
	}

	private void selectItemMenuLeft() {
		TreeItem<Object> itemSelected = treeStudies.getSelectionModel().getSelectedItem();
		if (itemSelected != null) {
			selectItem(itemSelected.getValue());
			showData();
			navigationService.getHistory().clear();
		}
	}

	private void selectItemListView() {
		TopicDTO topicSelected = listViewTopics.getSelectionModel().getSelectedItem();
		if (topicSelected != null) {
			selectItem(topicSelected);
			showData();
			navigationService.getHistory().clear();
		}
	}

	private void selectItem(Object itemSelected) {
		objectCurrentSelected = itemSelected;
		if (objectCurrentSelected instanceof StudyDTO studyDto) {
			objectCurrentSelected = screenMainService.loadStudy(studyDto.getId());
		} else if (objectCurrentSelected instanceof TopicDTO topicDto) {
			objectCurrentSelected = screenMainService.loadTopic(topicDto.getId());
		}
	}

	private void showData() {
		boolean canGoBack = navigationService.canGoBack(objectCurrentSelected);
		boolean canGoForward = navigationService.canGoForward();

		uiHelper.updateNavigationButtons(bttNavigationLeft, bttNavigationRight, canGoBack, canGoForward);
//		uiHelper.updateTxtHierarchyPath(txtHierarchyPath, objectCurrentSelected);
		uiHelper.updateTitleItemMain(lblTitleMain, objectCurrentSelected);
		uiHelper.updateListViewTopics(observableListTopics, listViewTopics, objectCurrentSelected);
	}

	private void navigateBack() {
		objectCurrentSelected = navigationService.back(objectCurrentSelected);
		showData();
	}

	private void navigateForward() {
		objectCurrentSelected = navigationService.forward(objectCurrentSelected);
		showData();
	}

	private void createNewTab() {
		uiHelper.createNewTab(tabPaneStudy, tabAdd);
	}

	private void newTopic() {
		RegisterTopicController registerTopicController = new RegisterTopicController();

		if (objectCurrentSelected instanceof Study study) {
			registerTopicController.setStudy(study);
		} else if (objectCurrentSelected instanceof Topic topic) {
			registerTopicController.setTopicParent(topic);
		}

		RegisterTopicWindow registerTopicWindow = new RegisterTopicWindow(stage, registerTopicController);
		registerTopicWindow.showScreen();

		if (registerTopicController.getTopic().getId() != null &&
			registerTopicController.getTopic().getId() > 0) {
			showData();
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
