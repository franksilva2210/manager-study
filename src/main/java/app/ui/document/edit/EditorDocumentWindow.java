package app.ui.document.edit;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class EditorDocumentWindow {

    private BorderPane root;

    public EditorDocumentWindow(EditorDocumentController controller) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditorDocumentWindow.fxml"));
            loader.setController(controller);

            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BorderPane getRoot() {
        return root;
    }
}
