package app.infra.text;

import app.application.text.TextDTO;
import app.domain.text.Text;
import app.infra.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TextRepository {

    public Text save(Text text) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {

            em.getTransaction().begin();
            em.persist(text);
            em.getTransaction().commit();

            return text;

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    public Text update(Text text) {

        EntityManager em =
                HibernateUtil.getEntityManager();

        try {

            em.getTransaction().begin();

            Text merged = em.merge(text);

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

    public Text update(TextDTO dto) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {

            em.getTransaction().begin();

            Text text = em.find(Text.class, dto.getId());

            if (text != null) {
                text.setTitle(dto.getTitle());
                text.setContent(dto.getContent());
            }

            em.getTransaction().commit();

            return text;

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

            Text text =
                    em.find(Text.class, id);

            if (text != null) {
                em.remove(text);
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

    public Text findById(Long id) {

        EntityManager em =
                HibernateUtil.getEntityManager();

        try {

            return em.find(Text.class, id);

        } finally {

            em.close();
        }
    }

    public List<Text> findByStudy(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {
            return em.createQuery(
                            """
                            SELECT text
                            FROM Text text
                            WHERE text.study.id = :id
                            """,
                            Text.class
                    )
                    .setParameter("id", id)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Text> findByTopic(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {
            return em.createQuery(
                            """
                            SELECT text
                            FROM Text text
                            WHERE text.topic.id = :id
                            """,
                            Text.class
                    )
                    .setParameter("id", id)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Text> findAll() {

        EntityManager em =
                HibernateUtil.getEntityManager();

        try {

            return em.createQuery(
                    "FROM Text",
                    Text.class
            ).getResultList();

        } finally {

            em.close();
        }
    }

    public List<Text> findAllStudyTexts() {
        EntityManager em = HibernateUtil.getEntityManager();

        try {
            return em.createQuery(
                    """
                    SELECT tx
                    FROM Text tx
                    WHERE tx.study IS NOT NULL
                    AND tx.topic IS NULL
                    """,
                    Text.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

}
