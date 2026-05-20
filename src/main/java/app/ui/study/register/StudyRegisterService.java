package app.ui.study.register;

import app.domain.study.Study;
import app.infra.study.StudyRepository;

public class StudyRegisterService {

    private StudyRepository studyRepository = new StudyRepository();

    public void extractFields(StudyRegisterComponentsUI componentsUI, Study study) {
        study.setMatter(componentsUI.getTxtMatter().getText());
    }

    public Study saveStudy(Study study) throws Exception {
        return studyRepository.save(study);
    }

    public void clearScreen(StudyRegisterComponentsUI componentsFxDto) {
        componentsFxDto.getTxtMatter().clear();
        componentsFxDto.getMsgUser().setText("");
    }

    public void showStudyScreen(StudyRegisterComponentsUI componentsUI, Study study) {
        componentsUI.getTxtMatter().setText(study.getMatter());
        componentsUI.getMsgUser().setText("");
    }
}
