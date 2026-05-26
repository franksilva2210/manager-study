package app.infra;

import java.sql.*;

public class DatabaseBootstrap {

    private static final String HOST =
            "jdbc:postgresql://localhost:5432/";

    private static final String DATABASE =
            "knowledge_manager";

    private static final String USER =
            "postgres";

    private static final String PASSWORD =
            "root";

    public static void initializeDatabase() {

        try (Connection connection = DriverManager.getConnection(HOST + "postgres", USER, PASSWORD)) {

            boolean databaseExists = databaseExists(connection);

            if (!databaseExists) {

                createDatabase(connection);

                System.out.println("Database created: " + DATABASE);

            } else {
                System.out.println("Database already exists.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error initializing database.", e);
        }
    }

    private static boolean databaseExists(Connection connection) throws Exception {
        String sql = """
                SELECT 1
                FROM pg_database
                WHERE datname = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, DATABASE);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    private static void createDatabase(Connection connection) throws Exception {
        String sql = "CREATE DATABASE " + DATABASE;

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }

    }

}
