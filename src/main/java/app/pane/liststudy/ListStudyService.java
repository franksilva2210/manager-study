package app.pane.liststudy;

import app.study.register.Study;
import app.study.register.StudyDao;

import java.util.ArrayList;
import java.util.List;

public class ListStudyService {

    public List<Study> consultAllStudy() throws Exception {
        List<Study> studyList = new ArrayList<>();
        StudyDao studyDao = new StudyDao();
        try {
            studyList = studyDao.consultAll();
        } catch (Exception e) {
            throw new Exception("Falha na consulta dos estudos cadastrados.");
        }
        return studyList;
    }
}
