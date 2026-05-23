package app.ui.topic.register;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterTopicController implements Initializable {

    @FXML
    private TextField txtTitle;

    @FXML
    private Button bttCancel;

    @FXML
    private Button bttOk;

    private Stage stage;
    private TopicDTO topicDto;
    private RegisterTopicComponentsUI componentsUI = new RegisterTopicComponentsUI();
    private RegisterTopicUIHelper uiHelper = new RegisterTopicUIHelper();
    private RegisterTopicService registerTopicService = new RegisterTopicService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txtTitle.setOnAction(event -> {
            saveTopic();
        });

        bttOk.setOnAction(event -> {
            saveTopic();
        });

        bttCancel.setOnAction(event -> {
            stage.close();
        });

        loadComponentsUI();

        showData();
    }

    private void showData() {
        if (topicDto != null && topicDto.getId() != null && topicDto.getId() > 0) {
            uiHelper.showData(componentsUI, topicDto);
        }
    }

    private void saveTopic() {
        try {
            uiHelper.validateFields(componentsUI);
            uiHelper.extractFields(topicDto, componentsUI);
            topicDto = registerTopicService.save(topicDto);
            stage.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadComponentsUI() {
        componentsUI.setTxtTitle(txtTitle);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTopicDto(TopicDTO topicDto) {
        this.topicDto = topicDto;
    }

    public TopicDTO getTopicDto() {
        return topicDto;
    }

    public void setStudy(StudyDTO studyDto) {
        topicDto.setStudyId(studyDto.getId());
    }

    public void setTopicParent(TopicDTO topicParentDto) {
        topicDto.setTopicParentId(topicParentDto.getId());
    }
}
