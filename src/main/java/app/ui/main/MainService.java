package app.ui.main;

import app.domain.study.Study;
import app.infra.study.StudyDao;

import java.util.List;

public class MainService {

    private final StudyDao studyDao = new StudyDao();

    public List<Study> consultStudyAll() {
        try {
            return studyDao.consultAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
