package app.pane.study;

import app.pane.study.topic.register.Topic;
import app.study.register.Study;
import app.study.register.StudyDao;
import javafx.collections.ObservableList;

public class StudyService {

    public void showScreenStudy(Study study, StudyControlComponentsFxDto componentsFxDto) {
        if (study != null) {
            componentsFxDto.getLblTitleStudy().setText(study.getMatter());
            for(Topic topic : study.getListTopics()) {
                componentsFxDto.getObservableListTopics().add(topic.getTitle());
            }
            componentsFxDto.getListViewTopics().refresh();
            componentsFxDto.getEditorTextMatter().setHtmlText(study.getNotes());
        }
    }

    public void clearScreen(StudyControlComponentsFxDto componentsFxDto) {
        componentsFxDto.getLblTitleStudy().setText("");
        componentsFxDto.getObservableListTopics().clear();
        componentsFxDto.getListViewTopics().refresh();
    }

    public void updateObservableListTopics(Study study, ObservableList<String> listTopics) {
        listTopics.clear();
        for(Topic topic : study.getListTopics()) {
            listTopics.add(topic.getTitle());
        }
    }

    public void extractNotes(Study study, StudyControlComponentsFxDto componentsFxDto) {
        study.setNotes(componentsFxDto.getEditorTextMatter().getHtmlText());
    }

    public void saveStudy(Study study) throws Exception {
        StudyDao studyDao = new StudyDao();
        try {
            studyDao.updateFullInStudy(study);
        } catch (Exception e) {
            throw new Exception("Falha ao salvar alteracoes no estudo.");
        }
    }

    public Study consultStudyById(Long idStudy) throws Exception {
        StudyDao studyDao = new StudyDao();
        Study study = null;
        try {
            study = studyDao.consult(idStudy);
        } catch (Exception e) {
            throw new Exception("Falha na consulta do Estudo.");
        }
        return study;
    }
}
