package app.ui.pane.left;

import app.application.study.StudyDTO;
import app.application.study.StudyMapper;
import app.application.topic.TopicDTO;
import app.application.topic.TopicMapper;
import app.domain.study.Study;
import app.domain.topic.Topic;
import app.infra.HibernateUtil;
import app.infra.study.StudyRepository;
import app.infra.topic.TopicRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PaneLeftService {

    private final StudyRepository studyRepository = new StudyRepository();
    private final TopicRepository topicRepository = new TopicRepository();

    public List<StudyDTO> consultAllStudyDto() {
        try {
            List<StudyDTO> listStudyDto = studyRepository.findAllDto();
            return listStudyDto;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar estudos.", e);
        }
    }

    public TopicDTO loadSimpleTopic(Long id) {
        Topic topic = topicRepository.findById(id);
        return TopicMapper.toSimpleDTO(topic);
    }

    public StudyDTO loadSimpleStudy(Long id) {
        Study study = studyRepository.findById(id);
        return StudyMapper.toSimpleDTO(study);
    }

    public void moveTopicToStudy(TopicDTO topicDragged, StudyDTO studyDestination) {
        if (topicDragged.getStudyId().equals(studyDestination.getId())) {
            return;
        }
        topicDragged.setStudyId(studyDestination.getId());
        topicRepository.updateStudyParent(topicDragged);
    }

    public void moveStudyToStudy(Long studyDraggedId, Long studyDestinationId) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Study studyDragged = em.createQuery(
                            """
                            SELECT DISTINCT s
                            FROM Study s
                            LEFT JOIN FETCH s.listTopics t
                            WHERE s.id = :id
                            """,
                            Study.class
                    )
                    .setParameter("id", studyDraggedId)
                    .getSingleResult();

            Study studyDestination = em.find(Study.class, studyDestinationId);

            Topic newTopic = new Topic();
            newTopic.setTitle(studyDragged.getMatter());
            newTopic.setStudy(studyDestination);

            em.persist(newTopic);

            for (Topic topic : studyDragged.getListTopics()) {
                topic.setStudy(null);
                topic.setTopicParent(newTopic);
                newTopic.getListTopics().add(topic);
            }

            em.remove(studyDragged);

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

    public void removeStudy(StudyDTO studyDto) {
        studyRepository.delete(studyDto.getId());
    }

}
