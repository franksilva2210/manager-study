package app.ui.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class PreviewTextWindow {

    private VBox root;

    private PreviewTextController controller;

    public PreviewTextWindow() {
        try {

            controller = new PreviewTextController();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("PreviewTextWindow.fxml"));
            loader.setController(controller);

            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public VBox getRoot() {
        return root;
    }

    public PreviewTextController getController() {
        return controller;
    }
}
