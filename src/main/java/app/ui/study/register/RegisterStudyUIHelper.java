package app.ui.study.register;

import app.application.study.StudyDTO;
import app.ui.util.ValidateControlFx;
import app.ui.util.ValidateDataUIException;

public class RegisterStudyUIHelper {

    public void validateFields(RegisterStudyComponentsUI componentsUI) throws ValidateDataUIException {
        ValidateControlFx validate = new ValidateControlFx();
        validate.setControl(componentsUI.getTxtMatter());
        validate.setError(false);
        validate.validateControl();
        if (validate.getError()) {
            throw new ValidateDataUIException("Tema invalido.");
        }
    }

    public void extractFields(RegisterStudyComponentsUI componentsUI, StudyDTO studyDto) {
        studyDto.setMatter(componentsUI.getTxtMatter().getText());
    }

    public void clearScreen(RegisterStudyComponentsUI componentsUI) {
        componentsUI.getTxtMatter().clear();
    }

    public void showStudyScreen(RegisterStudyComponentsUI componentsUI, StudyDTO studyDto) {
        componentsUI.getTxtMatter().setText(studyDto.getMatter());
    }

}
