package app.ui.main;

import app.domain.study.Study;
import app.infra.study.StudyDao;
import app.domain.topic.Topic;
import app.util.Report;
import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class StudyService {

    public void showScreenStudy(Study study, StudyControlComponentsFxDto componentsFxDto) {
        if (study != null) {
            componentsFxDto.getLblTitleStudy().setText(study.getMatter());
            for(Topic topic : study.getListTopics()) {
                componentsFxDto.getObservableListTopics().add(topic.getTitle());
            }
            componentsFxDto.getListViewTopics().refresh();
//            componentsFxDto.getEditorTextMatter().setHtmlText(study);
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
//        study.setNotes(componentsFxDto.getEditorTextMatter().getHtmlText());
    }

    public void showMap() {
        Report report = new Report();
        //report.addParam("total", output.getValue());
        //report.addParam("listItens", new JRBeanCollectionDataSource(null));

        try {
            report.readFileReport(getClass().getResource("Map.jrxml").toURI().getPath());
            report.generateReport();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JRException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        report.showReport();
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
