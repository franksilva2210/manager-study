package app.ui.study.register;

import app.domain.study.Study;
import app.infra.study.StudyRepository;

public class RegisterStudyService {

    private StudyRepository studyRepository = new StudyRepository();

    public Study saveStudy(Study study) throws Exception {
        return studyRepository.save(study);
    }

}
