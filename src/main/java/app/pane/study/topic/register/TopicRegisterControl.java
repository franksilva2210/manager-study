package app.pane.study.topic.register;

import app.util.ModPersistData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class TopicRegisterControl implements Initializable {

    @FXML private TextField txtTitle;
    @FXML private Text txtMsg;
    @FXML private Button bttSave;

    private TopicRegisterWindow topicRegisterWindow;
    private Topic topic;
    private ModPersistData modPersistData = ModPersistData.INSERT;
    private TopicRegisterService topicRegisterService = new TopicRegisterService();
    private TopicRegisterComponentsFxDto componentsFxDto = new TopicRegisterComponentsFxDto();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bttSave.setOnMouseClicked((MouseEvent mouse) -> {
            if (mouse.getClickCount() == 1) {
                saveTopic();
            }
        });

        loadTopicRegisterComponentsFxDto();

        if (modPersistData.equals(ModPersistData.UPDATE)) {
            topicRegisterService.showTopicInScreen(topic, componentsFxDto);
        }
    }

    private void saveTopic() {
        try {
            topicRegisterService.validateFields(txtTitle);
            topic = topicRegisterService.extractFields(topic, componentsFxDto, modPersistData);
            topicRegisterService.clearScreen(componentsFxDto);
            topicRegisterWindow.getStage().close();
        } catch (Exception e) {
            topic = null;
            txtMsg.setText(e.getMessage());
        }
    }

    private void loadTopicRegisterComponentsFxDto() {
        componentsFxDto.setTxtTitle(txtTitle);
        componentsFxDto.setTxtMsg(txtMsg);
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = new Topic(topic);
    }

    public TopicRegisterWindow getTopicRegisterWindow() {
        return topicRegisterWindow;
    }

    public void setTopicRegisterWindow(TopicRegisterWindow topicRegisterWindow) {
        this.topicRegisterWindow = topicRegisterWindow;
    }

    public ModPersistData getModPersistData() {
        return modPersistData;
    }

    public void setModPersistData(ModPersistData modPersistData) {
        this.modPersistData = modPersistData;
    }
}
