package app.ui.backup;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ScreenBackupController implements Initializable {

    @FXML
    private Button bttGenerateBackup;

    private Stage stage;

    private ScreenBackupService screenBackupService = new ScreenBackupService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bttGenerateBackup.setOnAction(event -> {
            screenBackupService.generateBackup();
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
