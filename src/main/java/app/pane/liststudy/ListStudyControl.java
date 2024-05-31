package app.pane.liststudy;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import app.pane.MainContainerWindow;
import app.pane.study.StudyControl;
import app.pane.study.StudyWindow;
import app.study.register.Study;
import app.study.register.StudyRegisterWindow;
import app.study.register.Topic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ListStudyControl implements Initializable {

	@FXML private ListView<HBox> listViewStudyGui;
	@FXML private MenuItem menuListNovo;
	
	private ObservableList<HBox> observableListStudyGui = FXCollections.observableArrayList();
	private ListStudyService listStudyService = new ListStudyService();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		listViewStudyGui.setItems(observableListStudyGui);
		
		menuListNovo.setOnAction((event) -> {
			newRegisterStudy();
		});

		consultAllStudy();
	}
	
	private HBox generateStudyGui(Study study) {
		TitledPane titledPaneStudy = new TitledPane();
		titledPaneStudy.setText(study.getMatter());
		titledPaneStudy.setMaxWidth(195);
		titledPaneStudy.setPadding(new Insets(0, 2, 0, 0));
		titledPaneStudy.setExpanded(false);
		
		ListView<String> listViewTitleTopics = new ListView<>();
		listViewTitleTopics.setPrefHeight(300);
		listViewTitleTopics.setItems(generateListTopicsStudy(study));
		listViewTitleTopics.setOnMouseClicked((MouseEvent mouse) -> {
			if (mouse.getClickCount() == 2) {
				selectStudy(study);
			}
		});
		
		titledPaneStudy.setContent(listViewTitleTopics);
		
		Image img = new Image(getClass().getResourceAsStream("touchscreen.png"));
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(18);
        imgView.setFitHeight(16);
		
        Button button = new Button();
        button.setGraphic(imgView);
		button.setMinHeight(23);
		button.setPrefHeight(23);
		button.setMaxHeight(23);
		button.setOnMouseClicked((MouseEvent mouse) -> {
			if (mouse.getClickCount() == 1) {
				selectStudy(study);
			}
		});
		
		HBox hbox = new HBox();
		hbox.getChildren().addAll(titledPaneStudy, button);
		
		return hbox;
	}
	
	private ObservableList<String> generateListTopicsStudy(Study study) {
		ObservableList<String> listTitleTopics = FXCollections.observableArrayList();
		for (Topic topic: study.getListTopics()) {
			listTitleTopics.add(topic.getTitle());
		}
		return listTitleTopics;
	}

	private void newRegisterStudy() {
		StudyRegisterWindow.buildAndShowScreen(MainContainerWindow.getStage());
		consultAllStudy();
	}
	
	private void selectStudy(Study study) {
		StudyControl.setStudySelected(study);
		VBox paneStudy = (VBox) MainContainerWindow.getScene().lookup("#paneStudy");
		paneStudy.getChildren().clear();
		paneStudy.getChildren().add(StudyWindow.buildVBox());
	}

	private void consultAllStudy() {
		List<Study> studyList = new ArrayList<>();

		try {
			studyList = listStudyService.consultAllStudy();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Study study1 = new Study("Estudo Java");
		study1.getListTopics().add(new Topic("Livro H Schildt"));
		study1.getListTopics().add(new Topic("Java Web"));
		study1.getListTopics().add(new Topic("Persistência"));
		study1.getListTopics().add(new Topic("Maven"));

		Topic topic = new Topic("JavaFX");
		topic.getListTopics().add(new Topic("JavaFX & Spring Boot"));
		topic.getListTopics().add(new Topic("JavaFX & Swing"));
		study1.getListTopics().add(topic);

		Study study2 = new Study("Geografia");
		study2.getListTopics().add(new Topic("Geologia"));
		study2.getListTopics().add(new Topic("Relevo"));
		study2.getListTopics().add(new Topic("Urbanização"));

		studyList.add(study1);
		studyList.add(study2);

		observableListStudyGui.clear();

		for (Study study : studyList) {
			observableListStudyGui.add(generateStudyGui(study));
		}
	}
	
}
