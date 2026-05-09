package app.ui.main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.web.HTMLEditor;

public class StudyControlComponentsFxDto {
    private Label lblTitleStudy;
    private ListView<String> listViewTopics;
    private ObservableList<String> observableListTopics = FXCollections.observableArrayList();
    private HTMLEditor editorTextMatter;

    public Label getLblTitleStudy() {
        return lblTitleStudy;
    }

    public void setLblTitleStudy(Label lblTitleStudy) {
        this.lblTitleStudy = lblTitleStudy;
    }

    public ListView<String> getListViewTopics() {
        return listViewTopics;
    }

    public void setListViewTopics(ListView<String> listViewTopics) {
        this.listViewTopics = listViewTopics;
    }

    public ObservableList<String> getObservableListTopics() {
        return observableListTopics;
    }

    public void setObservableListTopics(ObservableList<String> observableListTopics) {
        this.observableListTopics = observableListTopics;
    }

    public HTMLEditor getEditorTextMatter() {
        return editorTextMatter;
    }

    public void setEditorTextMatter(HTMLEditor editorTextMatter) {
        this.editorTextMatter = editorTextMatter;
    }
}
