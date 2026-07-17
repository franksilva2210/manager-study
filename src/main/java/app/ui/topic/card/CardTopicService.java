package app.ui.topic.card;

import app.application.topic.TopicDTO;
import app.application.topic.TopicMapper;
import app.domain.study.Study;
import app.domain.topic.Topic;
import app.infra.HibernateUtil;
import app.infra.study.StudyRepository;
import app.infra.topic.TopicRepository;
import app.ui.util.BusinessException;
import jakarta.persistence.EntityManager;

public class CardTopicService {

    private TopicRepository topicRepository = new TopicRepository();
    private StudyRepository studyRepository = new StudyRepository();

    public TopicDTO loadSimpleTopic(Long id) {
        Topic topic = topicRepository.findById(id);
        return TopicMapper.toSimpleDTO(topic);
    }

    public void moveTopicToTopic(
            TopicDTO topicDragged,
            TopicDTO topicDestination) {

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
                        "Não é possível mover o tópico. O\n" +
                        "tópico já está no nível mais alto do estudo.\n" +
                        "Você pode criar um novo estudo e arrastar os sub-tópicos\n" +
                        "deste tópico para lá."
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
}
