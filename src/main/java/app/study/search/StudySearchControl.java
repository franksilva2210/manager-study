package app.study.search;

import app.study.register.Study;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class StudySearchControl implements Initializable {
    @FXML
    private Button bttSelect;

    @FXML
    private TableView<Study> tableStudy;

    @FXML
    private TableColumn<Study, Long> columnCod;

    @FXML
    private TableColumn<Study, String> columnMatter;

    private static Study studySelected;
    private ObservableList<Study> listStudy = FXCollections.observableArrayList();
    private StudySearchService studySearchService = new StudySearchService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableStudy.setItems(listStudy);

        tableStudy.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                selected();
            }
        });

        columnCod.setCellValueFactory(new PropertyValueFactory<Study, Long>("id"));
        columnMatter.setCellValueFactory(new PropertyValueFactory<Study, String>("matter"));

        bttSelect.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                selected();
            }
        });

        try {
            listStudy.addAll(studySearchService.consultAllStudy());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selected() {
        Study study = tableStudy.getSelectionModel().getSelectedItem();
        if (study != null) {
            studySelected = study;
            StudySearchWindow.getStage().close();
        }
    }

    public static Study getStudySelected() {
        return studySelected;
    }

    public static void setStudySelected(Study studySelected) {
        StudySearchControl.studySelected = studySelected;
    }
}
