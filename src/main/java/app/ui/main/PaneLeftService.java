package app.ui.main;

import app.application.study.StudyDTO;
import app.infra.study.StudyRepository;

import java.util.List;

public class PaneLeftService {

    private StudyRepository studyRepository = new StudyRepository();

    public List<StudyDTO> consultAllStudyDto() {
        try {
            List<StudyDTO> listStudyDto = studyRepository.findAllDto();
            return listStudyDto;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar estudos.", e);
        }
    }

    public void removeStudy(StudyDTO studyDto) {
        studyRepository.delete(studyDto.getId());
    }

}
