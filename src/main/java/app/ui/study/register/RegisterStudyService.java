package app.ui.study.register;

import app.application.study.StudyDTO;
import app.application.study.StudyMapper;
import app.domain.study.Study;
import app.infra.study.StudyRepository;

public class RegisterStudyService {

    private StudyRepository studyRepository = new StudyRepository();

    public StudyDTO saveStudy(StudyDTO studyDto) throws Exception {
        if (studyDto.getId() != null && studyDto.getId() > 0) {
            Study study = studyRepository.update(studyDto);
            return StudyMapper.toSimpleDTO(study);
        } else {
            Study study = StudyMapper.toEntity(studyDto);
            study = studyRepository.save(study);
            return StudyMapper.toDTO(study);
        }
    }

}
