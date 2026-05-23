package app.ui.study.register;

import app.application.study.StudyDTO;
import app.util.ValidateControlFx;

public class RegisterStudyUIHelper {

    public void validateFields(RegisterStudyUI ui) throws Exception {
        ValidateControlFx validate = new ValidateControlFx();
        validate.setControl(ui.getTxtMatter());
        validate.setError(false);
        validate.validateControl();
        if (validate.getError()) {
            throw new Exception("Campo Assunto invalido.");
        }
    }

    public void extractFields(RegisterStudyUI ui, StudyDTO studyDto) {
        studyDto.setMatter(ui.getTxtMatter().getText());
    }

    public void clearScreen(RegisterStudyUI ui) {
        ui.getTxtMatter().clear();
        ui.getMsgUser().setText("");
    }

    public void showStudyScreen(RegisterStudyUI ui, StudyDTO studyDto) {
        ui.getTxtMatter().setText(studyDto.getMatter());
        ui.getMsgUser().setText("");
    }

}
