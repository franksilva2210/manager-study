package app.infra;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.output.MigrateResult;

public class FlywayConfig {

    private static final String URL = SQLiteDataBaseConfig.JDBC_URL;

    public static void migrate() {

        Flyway flyway = Flyway.configure()
                .dataSource(
                        URL,
                        null,
                        null
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
