package app.infra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SQLiteDataBaseConfig {

    public static final String DATA_FOLDER =
            "data";

    public static final String DATABASE_FILE =
            "study-manager.db";

    public static final String DATABASE_PATH =
            DATA_FOLDER + "/" + DATABASE_FILE;

    public static final String JDBC_URL =
            "jdbc:sqlite:"
            + DATABASE_PATH
            + "?foreign_keys=on"
            + "&journal_mode=WAL"
            + "&synchronous=NORMAL";;

    public static void initialize() {

        try {

            Files.createDirectories(
                    Paths.get(DATA_FOLDER)
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
