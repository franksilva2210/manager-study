package app.ui.main;

import java.net.URL;
import java.util.*;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
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
	private ListView<Topic> listViewTopics;

	@FXML
	private Tab tabAdd;

	private Stage stage;
	private ObservableList<Topic> listTopics = FXCollections.observableArrayList();
	private ScreenMainService screenMainService = new ScreenMainService();
	private ScreenMainHelper screenMainHelper = new ScreenMainHelper();
	private List<Study> listStudy = new ArrayList<>();
	private Object objectCurrentSelected;
	private StudyNavigationService navigationService = new StudyNavigationService();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		listViewTopics.setItems(listTopics);

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

		updateNavigationButtons();
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
		listStudy.clear();
		listStudy.addAll(screenMainService.consultStudyAll());

		TreeItem<Object> treeItemRoot = new TreeItem<>("Estudos");
		treeItemRoot.setExpanded(true);

		for (Study study : listStudy) {
			TreeItem<Object> treeItemStudy = new TreeItem<>(study);

			for (Topic topic : study.getListTopics()) {
				TreeItem<Object> treeItemTopic = new TreeItem<>(topic);
				treeItemStudy.getChildren().add(treeItemTopic);
			}

			treeItemRoot.getChildren().add(treeItemStudy);
		}

		treeStudies.setRoot(treeItemRoot);
		treeStudies.setShowRoot(false);
	}

	private void selectItemMenuLeft() {
		TreeItem<Object> itemSelected = treeStudies.getSelectionModel().getSelectedItem();
		if (itemSelected != null) {
			handleSelectionItem(itemSelected.getValue());
		}
	}

	private void selectItemListView() {
		Topic topicSelected = listViewTopics.getSelectionModel().getSelectedItem();
		if (topicSelected != null) {
			handleSelectionItem(topicSelected);
		}
	}

	private void handleSelectionItem(Object objectSelected) {
		if (objectSelected instanceof Topic topicSelected) {
			if (objectCurrentSelected instanceof Study study) {
				topicSelected.setStudy(study);
			} else if (objectCurrentSelected instanceof Topic topicParent) {
				topicSelected.setTopicParent(topicParent);
			}
		}
		objectCurrentSelected = objectSelected;
		navigationService.getHistory().clear();
		loadData();
	}

	private void navigateBack() {
		objectCurrentSelected = navigationService.back(objectCurrentSelected);
		loadData();
	}

	private void navigateForward() {
		objectCurrentSelected = navigationService.forward(objectCurrentSelected);
		loadData();
	}

	private void loadData() {
		txtHierarchyPath.setText(screenMainHelper.getHierarchyPath(objectCurrentSelected));

		listTopics.clear();
		if (objectCurrentSelected instanceof Study study) {
			lblTitleMain.setText(study.getMatter());
			screenMainService.consultListTopicByStudy(study);
			listTopics.addAll(study.getListTopics());
		} else if (objectCurrentSelected instanceof Topic topic) {
			lblTitleMain.setText(topic.getTitle());
			screenMainService.consultListTopicByTopicParent(topic);
			listTopics.addAll(topic.getListTopics());
		}

		listViewTopics.refresh();

		updateNavigationButtons();
	}

	private void updateNavigationButtons() {
		bttNavigationLeft.setDisable(!navigationService.canGoBack(objectCurrentSelected));
		bttNavigationRight.setDisable(!navigationService.canGoForward());
	}

	private void createNewTab() {
		HTMLEditor htmlEditor = new HTMLEditor();

		AnchorPane anchorPane = new AnchorPane();
		anchorPane.getChildren().add(htmlEditor);

		String title = "Texto " + tabPaneStudy.getTabs().indexOf(tabAdd);

		Tab tab = new Tab(title);
		tab.setClosable(true);
		tab.setContent(anchorPane);

		int indexAddTab = tabPaneStudy.getTabs().indexOf(tabAdd);

		tabPaneStudy.getTabs().add(indexAddTab, tab);
		tabPaneStudy.getSelectionModel().select(tab);
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
			loadData();
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
