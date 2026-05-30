package app.ui.document.edit;

import app.application.document.DocumentDTO;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.fxmisc.richtext.CodeArea;

public class EditorDocumentUIHelper {

    public void extractValues(Label lblTitle, CodeArea codeArea, DocumentDTO documentDto) {
        documentDto.setTitle(lblTitle.getText());

        String markdown = codeArea.getText();
        documentDto.setContent(markdown);
    }

    public void showPane(Node nodeToShow, StackPane pane) {
        for (Node node : pane.getChildren()) {

            boolean show = node == nodeToShow;

            node.setVisible(show);
            node.setManaged(show);
        }
    }

}
