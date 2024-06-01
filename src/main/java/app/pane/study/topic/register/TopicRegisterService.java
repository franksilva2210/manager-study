package app.pane.study.topic.register;

import app.util.ModPersistData;
import app.util.ValidateControlFx;
import javafx.scene.control.TextField;

public class TopicRegisterService {

    public void validateFields(TextField txtTitle) throws Exception {
        ValidateControlFx validate = new ValidateControlFx();
        validate.setControl(txtTitle);
        validate.setError(false);
        validate.validateControl();
        if (validate.getError()) {
            throw new Exception("Campo Titulo invalido.");
        }
    }

    public Topic extractFields(Topic topic, TopicRegisterComponentsFxDto componentsFxDto,
                               ModPersistData modPersistData) throws Exception {
        Topic topicExtracted = new Topic();
        try {
            if (modPersistData.equals(ModPersistData.UPDATE)) {
                topicExtracted.setId(topic.getId());
            }
            topicExtracted.setTitle(componentsFxDto.getTxtTitle().getText());
            return topicExtracted;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Falha na extracao dos dados para objeto topic");
        }
    }

    public void clearScreen(TopicRegisterComponentsFxDto componentsFxDto) {
        componentsFxDto.getTxtTitle().clear();
        componentsFxDto.getTxtMsg().setText("");
    }

    public void showTopicInScreen(Topic topic, TopicRegisterComponentsFxDto componentsFxDto) {
        componentsFxDto.getTxtTitle().setText(topic.getTitle());
    }

}
