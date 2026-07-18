package app.ui.document.edit;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import org.fxmisc.richtext.CodeArea;

public class EditorDocumentFacade {

    private Tab tab;
    private Label lblTitle;
    private Button bttPreview;
    private Button bttEdit;
    private CodeArea codeArea;
    private Button bttSave;
    private Button bttCancel;

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public Label getLblTitle() {
        return lblTitle;
    }

    public void setLblTitle(Label lblTitle) {
        this.lblTitle = lblTitle;
    }

    public Button getBttPreview() {
        return bttPreview;
    }

    public void setBttPreview(Button bttPreview) {
        this.bttPreview = bttPreview;
    }

    public Button getBttEdit() {
        return bttEdit;
    }

    public void setBttEdit(Button bttEdit) {
        this.bttEdit = bttEdit;
    }

    public CodeArea getCodeArea() {
        return codeArea;
    }

    public void setCodeArea(CodeArea codeArea) {
        this.codeArea = codeArea;
    }

    public Button getBttSave() {
        return bttSave;
    }

    public void setBttSave(Button bttSave) {
        this.bttSave = bttSave;
    }

    public Button getBttCancel() {
        return bttCancel;
    }

    public void setBttCancel(Button bttCancel) {
        this.bttCancel = bttCancel;
    }
}
