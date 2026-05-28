package app.infra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SQLiteDataBaseConfig {

    public static final Path ROOT =
            Paths.get(
                    System.getenv("LOCALAPPDATA"),
                    "StudyManager"
            );

    public static final Path DATA =
            ROOT.resolve("data");

    public static final Path BACKUP =
            ROOT.resolve("backup");

    public static final Path LOG =
            ROOT.resolve("logs");

    public static final Path DATABASE_PATH =
            DATA.resolve("study-manager.db");

    public static final Path WAL_PATH =
            DATA.resolve("study-manager.db-wal");

    public static final Path SHM_PATH =
            DATA.resolve("study-manager.db-shm");

    public static final String JDBC_URL =
            "jdbc:sqlite:" + DATABASE_PATH
                    + "?foreign_keys=on"
                    + "&journal_mode=WAL"
                    + "&synchronous=NORMAL";

    public static void initialize() {

        try {
            Files.createDirectories(DATA);
            Files.createDirectories(BACKUP);
            Files.createDirectories(LOG);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
