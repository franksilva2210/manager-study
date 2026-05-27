package app.infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {

    private static EntityManagerFactory emf;

    public static void initialize() {

        if (emf == null) {
            Map<String, Object> properties = new HashMap<>();

            properties.put(
                    "jakarta.persistence.jdbc.url",
                    SQLiteDataBaseConfig.JDBC_URL
            );

            emf = Persistence.createEntityManagerFactory(
                    "study-manager-persistence-unit",
                    properties
            );

            System.out.println("Hibernate initialized.");
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

}
