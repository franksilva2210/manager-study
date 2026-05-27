package app.ui.backup;

import app.infra.SQLiteDataBaseConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupDatabaseService {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");

    public Path createBackup(Path backupDirectory) {

        try {

            createDirectoryIfNotExists(backupDirectory);

            Path backupFile = backupDirectory.resolve(
                    generateBackupFileName()
            );

            executeVacuumInto(backupFile);

            return backupFile;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao criar backup.",
                    e
            );
        }
    }

    private void executeVacuumInto(Path backupFile) throws Exception {

        try (Connection connection =
                     DriverManager.getConnection(SQLiteDataBaseConfig.JDBC_URL)) {

            String sql = "VACUUM INTO ?";

            try (PreparedStatement stmt =
                         connection.prepareStatement(sql)) {

                stmt.setString(
                        1,
                        backupFile.toAbsolutePath().toString()
                );

                stmt.execute();
            }
        }
    }

    private void createDirectoryIfNotExists(Path path)
            throws IOException {

        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }
    }

    private String generateBackupFileName() {

        return "backup-"
                + LocalDateTime.now().format(FORMATTER)
                + ".db";
    }

}
