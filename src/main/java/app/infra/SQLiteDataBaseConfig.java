package app.infra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SQLiteDataBaseConfig {

    public static final Path DATABASE_PATH =
            Path.of("data", "study-manager.db");

    public static final Path WAL_PATH =
            Path.of("data", "study-manager.db-wal");

    public static final Path SHM_PATH =
            Path.of("data", "study-manager.db-shm");

    public static final String JDBC_URL =
            "jdbc:sqlite:" + DATABASE_PATH
            + "?foreign_keys=on"
            + "&journal_mode=WAL"
            + "&synchronous=NORMAL";

    public static void initialize() {
        try {
            Files.createDirectories(
                    Paths.get("data")
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
