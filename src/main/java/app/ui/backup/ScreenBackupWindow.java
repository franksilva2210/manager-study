package app.ui.backup;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class ScreenBackupWindow {

    private Stage stage;
    private Scene scene;
    private AnchorPane root;

    public ScreenBackupWindow(Stage stageOwner, ScreenBackupController controller) {
        stage = new Stage();
        stage.setTitle("Backup");
        stage.setResizable(false);

        if (stageOwner != null) {
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(stageOwner);
        }

        controller.setStage(stage);

        FXMLLoader rootFxml = new FXMLLoader();
        rootFxml.setLocation(ScreenBackupWindow.class.getResource("ScreenBackupWindow.fxml"));
        rootFxml.setController(controller);

        try {
            root = rootFxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        scene = new Scene(root);
        JMetro jMetro = new JMetro();
        jMetro.setStyle(Style.LIGHT);
        jMetro.setScene(scene);

        stage.setScene(scene);
    }

    public void showScreen() {
        stage.showAndWait();
    }

}
