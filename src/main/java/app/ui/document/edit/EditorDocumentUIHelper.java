package app.ui.document.edit;

import app.application.document.DocumentDTO;
import javafx.scene.control.Label;
import org.fxmisc.richtext.CodeArea;

public class EditorDocumentUIHelper {

    public void extractValues(Label lblTitle, CodeArea codeArea, DocumentDTO documentDto) {
        documentDto.setTitle(lblTitle.getText());

        String markdown = codeArea.getText();
        documentDto.setContent(markdown);
    }

}
