package app.ui.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class TextEditorWindow {

    private VBox root;

    private TextEditorController controller;

    public TextEditorWindow() {
        try {
            controller = new TextEditorController();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("TextEditorWindow.fxml"));
            loader.setController(controller);

            root = loader.load();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public VBox getRoot() {
        return root;
    }

    public TextEditorController getController() {
        return controller;
    }

}
