package app.ui.topic.register;

import app.domain.topic.Topic;
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

    public void extractFields(Topic topic, RegisterTopicComponentsUI componentsUI) {
        topic.setTitle(componentsUI.getTxtTitle().getText());
    }

}
