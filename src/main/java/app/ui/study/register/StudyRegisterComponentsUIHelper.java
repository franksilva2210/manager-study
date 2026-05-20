package app.ui.study.register;

import app.util.ValidateControlFx;

public class StudyRegisterComponentsUIHelper {

    public void validateFields(StudyRegisterComponentsUI componentsUI) throws Exception {
        ValidateControlFx validate = new ValidateControlFx();
        validate.setControl(componentsUI.getTxtMatter());
        validate.setError(false);
        validate.validateControl();
        if (validate.getError()) {
            throw new Exception("Campo Assunto invalido.");
        }
    }

}
