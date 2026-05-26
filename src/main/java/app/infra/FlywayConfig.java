package app.infra;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.output.MigrateResult;

public class FlywayConfig {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/knowledge_manager";

    private static final String USER =
            "postgres";

    private static final String PASSWORD =
            "root";

    public static void migrate() {

        Flyway flyway = Flyway.configure()
                .dataSource(
                        URL,
                        USER,
                        PASSWORD
                )
                .load();

        MigrateResult result =
                flyway.migrate();

        System.out.println(
                "Migrations executed: "
                        + result.migrationsExecuted
        );

        MigrationInfo current =
                flyway.info().current();

        if (current != null) {

            System.out.println(
                    "Current schema version: "
                            + current.getVersion()
            );

            System.out.println(
                    "Description: "
                            + current.getDescription()
            );

        } else {

            System.out.println(
                    "No schema version found."
            );

        }

        System.out.println(
                "Flyway migrations finished."
        );
    }

}
