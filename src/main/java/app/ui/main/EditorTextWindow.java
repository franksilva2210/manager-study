package app.ui.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class EditorTextWindow {

    private VBox root;

    public EditorTextWindow(EditorTextController controller) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditorTextWindow.fxml"));
            loader.setController(controller);

            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public VBox getRoot() {
        return root;
    }
}
