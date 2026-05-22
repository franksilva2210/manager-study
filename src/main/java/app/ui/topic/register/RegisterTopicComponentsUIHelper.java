package app.ui.topic.register;

import app.application.topic.dto.TopicDTO;
import app.util.ValidateControlFx;

public class RegisterTopicComponentsUIHelper {

    public void validateFields(RegisterTopicComponentsUI componentsUI) throws Exception {
        ValidateControlFx validate = new ValidateControlFx();
        validate.setControl(componentsUI.getTxtTitle());
        validate.setError(false);
        validate.validateControl();
        if (validate.getError()) {
            throw new Exception("Titulo invalido.");
        }
    }

    public void extractFields(TopicDTO topicDto, RegisterTopicComponentsUI componentsUI) {
        topicDto.setTitle(componentsUI.getTxtTitle().getText());
    }

}
