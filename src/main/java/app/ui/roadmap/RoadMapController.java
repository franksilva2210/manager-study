package app.ui.roadmap;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RoadMapController implements Initializable {

    @FXML
    private TextArea txtRoadMap;

    @FXML
    private VBox boxRoadMap;

    private Stage stage;

    private Object itemSelected;

    private RoadMapService roadMapService = new RoadMapService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showRoadMap();
    }

    private void showRoadMap() {
        if (itemSelected instanceof StudyDTO studySelectedDto) {
            StudyDTO studyFullDto = roadMapService.loadStudyFull(studySelectedDto.getId());
            renderStudy(studyFullDto, 0);
        } else if (itemSelected instanceof TopicDTO topicSelectedDto) {
            TopicDTO topicFullDto = roadMapService.loadTopicFull(topicSelectedDto.getId());
            renderTopic(topicFullDto, 0);
        }
    }

    private void renderStudy(StudyDTO study, int level) {
        Label studyLabel = new Label(study.getMatter());
        studyLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        boxRoadMap.getChildren().add(studyLabel);

        for (TopicDTO topic : study.getListTopicsDto()) {
            renderTopic(topic, level + 1);
        }
    }

    private void renderTopic(TopicDTO topic, int level) {
        HBox row = new HBox();

        Label label = new Label("     ".repeat(level) + "├── " + topic.getTitle());

        if (level == 0) {
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        }

        row.getChildren().add(label);
        boxRoadMap.getChildren().add(row);

        for (TopicDTO child : topic.getListTopicsDto()) {
            renderTopic(child, level + 1);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setItemSelected(Object itemSelected) {
        this.itemSelected = itemSelected;
    }
}
