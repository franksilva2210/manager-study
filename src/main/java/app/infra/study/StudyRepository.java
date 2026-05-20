package app.infra.study;

import app.domain.study.Study;
import app.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class StudyRepository {

    public Study save(Study study) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {

            em.getTransaction().begin();
            em.persist(study);
            em.getTransaction().commit();
            return study;

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    public Study update(Study study) {

        EntityManager em =
                HibernateUtil.getEntityManager();

        try {

            em.getTransaction().begin();

            Study merged = em.merge(study);

            em.getTransaction().commit();

            return merged;

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {

            em.close();
        }
    }

    public void delete(Long id) {

        EntityManager em =
                HibernateUtil.getEntityManager();

        try {

            em.getTransaction().begin();

            Study study =
                    em.find(Study.class, id);

            if (study != null) {
                em.remove(study);
            }

            em.getTransaction().commit();

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {

            em.close();
        }
    }

    public Study findById(Long id) {

        EntityManager em =
                HibernateUtil.getEntityManager();

        try {

            return em.find(Study.class, id);

        } finally {

            em.close();
        }
    }

    public List<Study> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery(
                    """
                    SELECT DISTINCT s
                    FROM Study s
                    """,
                    Study.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

}
