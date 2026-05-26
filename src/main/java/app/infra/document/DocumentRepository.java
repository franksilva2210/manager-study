package app.infra.document;

import app.application.document.DocumentDTO;
import app.domain.document.Document;
import app.infra.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DocumentRepository {

    public Document save(Document document) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {

            em.getTransaction().begin();
            em.persist(document);
            em.getTransaction().commit();

            return document;

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    public Document update(Document document) {

        EntityManager em =
                HibernateUtil.getEntityManager();

        try {

            em.getTransaction().begin();

            Document merged = em.merge(document);

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

    public Document update(DocumentDTO dto) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {

            em.getTransaction().begin();

            Document document = em.find(Document.class, dto.getId());

            if (document != null) {
                document.setTitle(dto.getTitle());
                document.setContent(dto.getContent());
            }

            em.getTransaction().commit();

            return document;

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
        EntityManager em = HibernateUtil.getEntityManager();

        try {

            em.getTransaction().begin();

            Document document = em.find(Document.class, id);

            if (document != null) {
                em.remove(document);
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

    public Document findById(Long id) {

        EntityManager em =
                HibernateUtil.getEntityManager();

        try {

            return em.find(Document.class, id);

        } finally {

            em.close();
        }
    }

    public List<Document> findByStudy(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {
            return em.createQuery(
                            """
                            SELECT doc
                            FROM Document doc
                            WHERE doc.study.id = :id
                            ORDER BY doc.id
                            """,
                            Document.class
                    )
                    .setParameter("id", id)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Document> findByTopic(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {
            return em.createQuery(
                            """
                            SELECT doc
                            FROM Document doc
                            WHERE doc.topic.id = :id
                            ORDER BY doc.id
                            """,
                            Document.class
                    )
                    .setParameter("id", id)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Document> findAll() {

        EntityManager em =
                HibernateUtil.getEntityManager();

        try {

            return em.createQuery(
                    "FROM Document",
                    Document.class
            ).getResultList();

        } finally {

            em.close();
        }
    }

    public List<Document> findAllStudyTexts() {
        EntityManager em = HibernateUtil.getEntityManager();

        try {
            return em.createQuery(
                    """
                    SELECT doc
                    FROM Document doc
                    WHERE doc.study IS NOT NULL
                    AND doc.topic IS NULL
                    """,
                    Document.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

}
