package app.infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateUtil {

    private static EntityManagerFactory emf;

    public static void initialize() {

        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(
                    "knowledge-manager-persistence-unit"
            );

            System.out.println("Hibernate initialized.");
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

}
