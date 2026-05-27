package app.ui.backup;

import app.infra.BackupDatabaseService;

import java.io.File;
import java.nio.file.Path;

public class ScreenBackupService {

    private BackupDatabaseService service = new BackupDatabaseService();

    public Path createBackup() {
        return
                service.createBackup(
                        Path.of("backup")
                );
    }

    public void restoreBackup(File file) {

    }

}
