package app.infra.topic;

import app.application.topic.TopicDTO;
import app.domain.topic.Topic;
import app.infra.HibernateUtil;
import jakarta.persistence.EntityManager;

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

    public Topic update(TopicDTO dto) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {

            em.getTransaction().begin();

            Topic topic = em.find(Topic.class, dto.getId());

            if (topic != null) {
                topic.setTitle(dto.getTitle());
            }

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

    public void delete(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {

            em.getTransaction().begin();

            Topic topic = em.find(Topic.class, id);
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

            return em.find(Topic.class, id);

        } finally {
            em.close();
        }
    }

    public Topic findByIdWithTopics(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {
            return em.createQuery(
                            """
                            SELECT DISTINCT t
                            FROM Topic t
                            LEFT JOIN FETCH t.topicParent
                            LEFT JOIN FETCH t.listTopics
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

    public Topic findTopicFull(Long topicId) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {

            Topic topic = em.createQuery(
                            """
                            SELECT DISTINCT t
                            FROM Topic t
                            LEFT JOIN FETCH t.topicParent
                            LEFT JOIN FETCH t.listTopics
                            WHERE t.id = :id
                            """,
                            Topic.class
                    )
                    .setParameter("id", topicId)
                    .getSingleResult();

            for (Topic item : topic.getListTopics()) {
                loadTopicsChildren(item);
            }

            return topic;

        } finally {
            em.close();
        }
    }

    public void loadTopicsChildren(Topic topic) {
        topic.getListTopics().size();
        for (Topic child : topic.getListTopics()) {
            loadTopicsChildren(child);
        }
    }

    public void moveTopic(TopicDTO dto) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Topic topic = em.find(Topic.class, dto.getId());
            Topic topicParent = em.find(Topic.class, dto.getTopicParentId());

            topic.setStudy(null);
            topic.setTopicParent(topicParent);

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

}
