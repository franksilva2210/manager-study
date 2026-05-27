package app.ui.backup;

import java.nio.file.Path;

public class ScreenBackupService {

    public void generateBackup() {

        Thread thread = new Thread(() -> {
            try {
                BackupDatabaseService service = new BackupDatabaseService();

                Path backup = service.createBackup(
                        Path.of("backup")
                );

            } catch (Exception e) {
                throw new RuntimeException("Erro ao criar backup: ", e);
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

}
