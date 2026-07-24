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
    private Button bttImport;
    private Button bttExport;
    private Button bttRemove;
    private Button bttBold;
    private Button bttItalic;
    private Button bttAttachImg;
    private Button bttBlocCod;
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

    public Button getBttBold() {
        return bttBold;
    }

    public void setBttBold(Button bttBold) {
        this.bttBold = bttBold;
    }

    public Button getBttItalic() {
        return bttItalic;
    }

    public void setBttItalic(Button bttItalic) {
        this.bttItalic = bttItalic;
    }

    public Button getBttAttachImg() {
        return bttAttachImg;
    }

    public void setBttAttachImg(Button bttAttachImg) {
        this.bttAttachImg = bttAttachImg;
    }

    public Button getBttBlocCod() {
        return bttBlocCod;
    }

    public void setBttBlocCod(Button bttBlocCod) {
        this.bttBlocCod = bttBlocCod;
    }

    public Button getBttImport() {
        return bttImport;
    }

    public void setBttImport(Button bttImport) {
        this.bttImport = bttImport;
    }

    public Button getBttExport() {
        return bttExport;
    }

    public void setBttExport(Button bttExport) {
        this.bttExport = bttExport;
    }

    public Button getBttRemove() {
        return bttRemove;
    }

    public void setBttRemove(Button bttRemove) {
        this.bttRemove = bttRemove;
    }
}
