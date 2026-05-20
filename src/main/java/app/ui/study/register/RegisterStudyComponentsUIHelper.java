package app.ui.study.register;

import app.domain.study.Study;
import app.util.ValidateControlFx;

public class RegisterStudyComponentsUIHelper {

    public void validateFields(RegisterStudyComponentsUI componentsUI) throws Exception {
        ValidateControlFx validate = new ValidateControlFx();
        validate.setControl(componentsUI.getTxtMatter());
        validate.setError(false);
        validate.validateControl();
        if (validate.getError()) {
            throw new Exception("Campo Assunto invalido.");
        }
    }

    public void extractFields(RegisterStudyComponentsUI componentsUI, Study study) {
        study.setMatter(componentsUI.getTxtMatter().getText());
    }

    public void clearScreen(RegisterStudyComponentsUI componentsUI) {
        componentsUI.getTxtMatter().clear();
        componentsUI.getMsgUser().setText("");
    }

    public void showStudyScreen(RegisterStudyComponentsUI componentsUI, Study study) {
        componentsUI.getTxtMatter().setText(study.getMatter());
        componentsUI.getMsgUser().setText("");
    }

}
