package app.ui.main;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import app.domain.study.Study;
import app.domain.topic.Topic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

public class MainController implements Initializable {

	@FXML
	private VBox paneMenuTop;

	@FXML
	private VBox paneListStudy;

	@FXML
	private VBox paneStudy;

	@FXML
	private TextField txtSearchStudy;

	@FXML
	private TreeView<String> treeStudies;

	private final MainService service = new MainService();

	private List<Study> listStudy = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtSearchStudy.setOnAction(event -> {
			searchStudy();
		});

		loadStudies();
	}

	public void loadStudies() {
		listStudy.clear();
		listStudy.addAll(service.consultStudyAll());

		TreeItem<String> treeItemRoot = new TreeItem<>("Estudos");
		treeItemRoot.setExpanded(true);

		for (Study study : listStudy) {
			TreeItem<String> treeItemStudy = new TreeItem<>(study.getMatter());

			for (Topic topic : study.getListTopics()) {
				TreeItem<String> treeItemTopic = new TreeItem<>(topic.getTitle());
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

}
