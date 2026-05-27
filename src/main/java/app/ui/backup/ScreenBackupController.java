package app.ui.backup;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class ScreenBackupController implements Initializable {

    @FXML
    private Button bttGenerateBackup;

    @FXML
    private Button bttRestoreBackup;

    @FXML
    private TextArea txtMsgBackup;

    @FXML
    private TextArea txtMsgRestore;

    private Stage stage;

    private ScreenBackupService screenBackupService = new ScreenBackupService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bttGenerateBackup.setOnAction(event -> {
            createBackup();
        });

        bttRestoreBackup.setOnAction(event -> {
            restoreBackup();
        });
    }

    private void createBackup() {
        txtMsgBackup.setText("Gerando backup...");
        bttGenerateBackup.setDisable(true);

        Task<Path> task = new Task<>() {
            @Override
            protected Path call() throws Exception {
                return screenBackupService.createBackup();
            }
        };

        task.setOnSucceeded(event -> {

            Path backupPath = task.getValue();

            txtMsgBackup.setText(
                    "Backup realizado com sucesso em: \n"
                            + backupPath.toAbsolutePath()
            );

            bttGenerateBackup.setDisable(false);
        });

        task.setOnFailed(event -> {
            txtMsgBackup.setText(
                    "Erro ao realizar backup."
            );

            bttGenerateBackup.setDisable(false);
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void restoreBackup() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Backup");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "SQLite Backup",
                        "*.db"
                )
        );

        File file = fileChooser.showOpenDialog(stage);

        if (file == null) {
            return;
        }


    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
