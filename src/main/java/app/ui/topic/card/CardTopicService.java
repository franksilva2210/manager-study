package app.ui.topic.card;

import app.application.topic.TopicDTO;
import app.application.topic.TopicMapper;
import app.domain.document.Document;
import app.domain.study.Study;
import app.domain.topic.Topic;
import app.infra.HibernateUtil;
import app.infra.topic.TopicRepository;
import app.ui.util.BusinessException;
import jakarta.persistence.EntityManager;

public class CardTopicService {

    private TopicRepository topicRepository = new TopicRepository();

    public TopicDTO loadSimpleTopic(Long id) {
        Topic topic = topicRepository.findById(id);
        return TopicMapper.toSimpleDTO(topic);
    }

    public void moveTopicToTopic(TopicDTO topicDragged, TopicDTO topicDestination) {
        if (topicDragged.getId().equals(topicDestination.getId())) {
            return;
        }
        topicDragged.setTopicParentId(topicDestination.getId());
        topicRepository.updateTopicParent(topicDragged);
    }

    public void removeTopic(TopicDTO topicDto) {
        topicRepository.delete(topicDto.getId());
    }

    public void moveOneLevelUp(TopicDTO topicDto) throws BusinessException {
        EntityManager em = HibernateUtil.getEntityManager();

        try {

            em.getTransaction().begin();

            Topic topic = em.find(Topic.class, topicDto.getId());

            if (topic.getTopicParent() == null && topic.getStudy() != null) {
                throw new BusinessException(
                        "Não é possível mover o tópico acima. O\n" +
                        "tópico já está no topo do estudo.\n" +
                        "Você pode usar a opção: Converter em novo estudo."
                );
            }

            Topic topicParent = topic.getTopicParent();

            if (topicParent.getTopicParent() != null && topicParent.getStudy() == null) {
                topic.setTopicParent(topicParent.getTopicParent());
            } else if (topicParent.getTopicParent() == null && topicParent.getStudy() != null) {
                topic.setTopicParent(null);
                topic.setStudy(topicParent.getStudy());
            } else {
                throw new Exception("Falha: erro inesperado.");
            }

            em.getTransaction().commit();

        } catch (BusinessException e) {

            em.getTransaction().rollback();
            throw e;

        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void convertTopicToStudy(Long topicId) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Topic topicCurrent = em.createQuery(
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

            Study newStudy = new Study();
            newStudy.setMatter(topicCurrent.getTitle());

            em.persist(newStudy);

            for (Topic topic : topicCurrent.getListTopics()) {
                topic.setStudy(newStudy);
                topic.setTopicParent(null);
                newStudy.getListTopics().add(topic);
            }

            for (Document document : topicCurrent.getListDocuments()) {
                document.setStudy(newStudy);
                document.setTopic(null);
                newStudy.getListDocuments().add(document);
            }

            em.remove(topicCurrent);

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
