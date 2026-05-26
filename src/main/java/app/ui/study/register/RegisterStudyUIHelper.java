package app.ui.study.register;

import app.application.study.StudyDTO;
import app.ui.util.ValidateControlFx;

public class RegisterStudyUIHelper {

    public void validateFields(RegisterStudyComponentsUI componentsUI) throws Exception {
        ValidateControlFx validate = new ValidateControlFx();
        validate.setControl(componentsUI.getTxtMatter());
        validate.setError(false);
        validate.validateControl();
        if (validate.getError()) {
            throw new Exception("Campo Assunto invalido.");
        }
    }

    public void extractFields(RegisterStudyComponentsUI componentsUI, StudyDTO studyDto) {
        studyDto.setMatter(componentsUI.getTxtMatter().getText());
    }

    public void clearScreen(RegisterStudyComponentsUI componentsUI) {
        componentsUI.getTxtMatter().clear();
        componentsUI.getMsgUser().setText("");
    }

    public void showStudyScreen(RegisterStudyComponentsUI componentsUI, StudyDTO studyDto) {
        componentsUI.getTxtMatter().setText(studyDto.getMatter());
        componentsUI.getMsgUser().setText("");
    }

}
