package app.ui.main;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import app.domain.study.Study;
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
	private Tab tabMain;

	@FXML
	private Label lblTitleStudyOrTopic;

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
	private List<Study> listStudy = new ArrayList<>();
	private Study studySelected = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		listViewTopics.setItems(listTopics);

		menuNewStudy.setOnAction(event -> {
			newStudy();
		});

		txtSearch.setOnAction(event -> {
			searchStudy();
		});

		treeStudies.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				getStudySelected();
			}
		});

		tabAdd.setOnSelectionChanged(event -> {
			if (tabAdd.isSelected()) {
				createNewTab();
			}
		});

		bttAddTopic.setOnAction(event -> {
			newTopic();
		});

		loadStudies();
	}

	private void newStudy() {
		RegisterStudyWindow registerStudyWindow = new RegisterStudyWindow(stage);
		registerStudyWindow.showScreen();
		if (registerStudyWindow.getController().getStudy().getId() != null &&
			registerStudyWindow.getController().getStudy().getId() > 0) {
			loadStudies();
		}
	}

	private void getStudySelected() {
		TreeItem<Object> objectSelected = treeStudies.getSelectionModel().getSelectedItem();
		studySelected = (Study) objectSelected.getValue();
		showData();
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

	private void searchStudy() {
//		String search = txtSearchStudy.getText().toLowerCase().trim();
//
//		if (search.isEmpty()) {
//			loadStudies();
//			return;
//		}
//
//		List<Study> filtered = new ArrayList<>();
//		for (Study study : listStudy) {
//			if (study.getMatter().toLowerCase().contains(search)) {
//
//				filtered.add(study);
//			}
//		}

//		loadStudies(filtered);
	}

	private void showData() {
		if (studySelected != null) {
			lblTitleStudyOrTopic.setText(studySelected.getMatter());

			listTopics.clear();
			listTopics.addAll(studySelected.getListTopics());
			listViewTopics.refresh();
		}
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
		registerTopicController.setStudy(studySelected);

		RegisterTopicWindow registerTopicWindow = new RegisterTopicWindow(stage, registerTopicController);
		registerTopicWindow.showScreen();

		if (registerTopicController.getTopic().getId() != null &&
			registerTopicController.getTopic().getId() > 0) {
			screenMainService.consultListTopic(studySelected);
			showData();
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
