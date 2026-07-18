package app.ui.document.edit;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class EditorDocumentUIHelper {

    public void showPane(Node nodeToShow, StackPane pane) {
        for (Node node : pane.getChildren()) {

            boolean show = node == nodeToShow;

            node.setVisible(show);
            node.setManaged(show);
        }
    }

}
