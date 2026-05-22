package app.infra.topic;

import app.application.dto.TopicDTO;
import app.domain.topic.Topic;
import app.infra.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TopicRepository {

    public Topic save(Topic topic) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {

            em.getTransaction().begin();
            em.persist(topic);
            em.getTransaction().commit();
            return topic;

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    public Topic update(Topic topic) {

        EntityManager em =
                HibernateUtil.getEntityManager();

        try {

            em.getTransaction().begin();

            Topic merged = em.merge(topic);

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

            Topic topic =
                    em.find(Topic.class, id);

            if (topic != null) {
                em.remove(topic);
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

    public Topic findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {
            return em.createQuery(
                            """
                            SELECT t
                            FROM Topic t
                            LEFT JOIN FETCH t.study
                            LEFT JOIN FETCH t.topicParent
                            WHERE t.id = :id
                            """,
                            Topic.class
                    )
                    .setParameter("id", id)
                    .getSingleResult();

        } finally {
            em.close();
        }
    }

    public List<Topic> findAll() {

        EntityManager em =
                HibernateUtil.getEntityManager();

        try {

            return em.createQuery(
                    "FROM Topic",
                    Topic.class
            ).getResultList();

        } finally {

            em.close();
        }
    }

    public List<Topic> findByStudy(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {
            return em.createQuery(
                    """
                    SELECT t
                    FROM Topic t
                    WHERE t.study.id = :id
                    AND t.topicParent IS NULL
                    """,
                    Topic.class
            )
            .setParameter("id", id)
            .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Topic> findAllRootTopics() {
        EntityManager em = HibernateUtil.getEntityManager();

        try {

            return em.createQuery(
                    """
                    SELECT t
                    FROM Topic t
                    WHERE t.topicParent IS NULL
                    """,
                    Topic.class
            ).getResultList();

        } finally {

            em.close();
        }
    }

    public List<Topic> findByTopicParent(Long parentTopicId) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {
            return em.createQuery(
                            """
                            SELECT t
                            FROM Topic t
                            WHERE t.topicParent.id = :parentTopicId
                            """,
                            Topic.class
                    )
                    .setParameter("parentTopicId", parentTopicId)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    public List<TopicDTO> findAllRootTopicsDto() {
        EntityManager em = HibernateUtil.getEntityManager();

        try {
            return em.createQuery(
                    """
                    SELECT new app.application.dto.TopicDTO(
                        t.id,
                        t.title,
                        t.study.id,
                        t.topicParent.id
                    )
                    FROM Topic t
                    WHERE t.topicParent IS NULL
                    ORDER BY t.title
                    """,
                    TopicDTO.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

}
