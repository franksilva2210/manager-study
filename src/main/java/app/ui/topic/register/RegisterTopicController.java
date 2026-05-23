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
    private Button bttCancel;

    @FXML
    private Button bttOk;

    @FXML
    private TextField txtTitle;

    private Stage stage;
    private TopicDTO topicDto = new TopicDTO();
    private RegisterTopicComponentsUI componentsUI = new RegisterTopicComponentsUI();
    private RegisterTopicComponentsUIHelper componentsUIHelper = new RegisterTopicComponentsUIHelper();
    private RegisterTopicService registerTopicService = new RegisterTopicService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bttOk.setOnAction(event -> {
            saveTopic();
        });

        loadComponentsUI();
    }

    private void saveTopic() {
        try {
            componentsUIHelper.validateFields(componentsUI);
            componentsUIHelper.extractFields(topicDto, componentsUI);
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

    public void setStudy(StudyDTO studyDto) {
        topicDto.setStudyId(studyDto.getId());
    }

    public void setTopicParent(TopicDTO topicParentDto) {
        topicDto.setTopicParentId(topicParentDto.getId());
    }

    public TopicDTO getTopicDto() {
        return topicDto;
    }
}
