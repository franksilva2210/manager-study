package app.ui.topic.register;

import app.domain.study.Study;
import app.domain.topic.Topic;
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
    private Topic topic = new Topic();
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
            componentsUIHelper.extractFields(topic, componentsUI);
            topic = registerTopicService.save(topic);
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

    public void setStudy(Study study) {
        topic.setStudy(study);
    }

    public Topic getTopic() {
        return topic;
    }
}
