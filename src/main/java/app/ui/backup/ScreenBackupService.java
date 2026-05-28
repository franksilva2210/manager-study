package app.ui.backup;

import app.infra.BackupDatabaseService;
import app.infra.SQLiteDataBaseConfig;

import java.io.File;
import java.nio.file.Path;

public class ScreenBackupService {

    private BackupDatabaseService service = new BackupDatabaseService();

    public Path createBackup() {
        Path path = SQLiteDataBaseConfig.BACKUP;

        return service.createBackup(path);
    }

    public void restoreBackup(File file) {
    	service.restoreBackup(file.toPath());
    }

}
