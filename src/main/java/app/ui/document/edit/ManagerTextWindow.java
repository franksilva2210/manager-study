package app.ui.document.edit;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ManagerTextWindow {

    private VBox root;

    public ManagerTextWindow(ManagerTextController controller) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerTextWindow.fxml"));
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
