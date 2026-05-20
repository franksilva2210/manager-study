package app.ui.study.register;

import app.domain.study.Study;
import app.infra.study.StudyRepository;

public class RegisterStudyService {

    private StudyRepository studyRepository = new StudyRepository();

    public void extractFields(RegisterStudyComponentsUI componentsUI, Study study) {
        study.setMatter(componentsUI.getTxtMatter().getText());
    }

    public Study saveStudy(Study study) throws Exception {
        return studyRepository.save(study);
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
