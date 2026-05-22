package app.application.topic.dto;

import app.domain.study.Study;
import app.domain.topic.Topic;

public class TopicMapper {

    public static TopicDTO toSimpleDTO(Topic entity) {
        if (entity == null) {
            return null;
        }

        TopicDTO dto = new TopicDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());

        if (entity.getStudy() != null) {
            dto.setStudyId(entity.getStudy().getId());
        }

        if (entity.getTopicParent() != null) {
            dto.setTopicParentId(entity.getTopicParent().getId());
        }

        return dto;
    }

    public static TopicDTO toDTO(Topic entity) {
        if (entity == null) {
            return null;
        }

        TopicDTO dto = new TopicDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());

        if (entity.getStudy() != null) {
            dto.setStudyId(entity.getStudy().getId());
        }

        if (entity.getTopicParent() != null) {
            dto.setTopicParentId(entity.getTopicParent().getId());
        }

        if (entity.getListTopics() != null && !entity.getListTopics().isEmpty()) {

            for (Topic subTopic : entity.getListTopics()) {

                TopicDTO subTopicDto = new TopicDTO();
                subTopicDto.setId(subTopic.getId());
                subTopicDto.setTitle(subTopic.getTitle());

                if (subTopic.getStudy() != null) {
                    subTopicDto.setStudyId(subTopic.getStudy().getId());
                }

                if (subTopic.getTopicParent() != null) {
                    subTopicDto.setTopicParentId(subTopic.getTopicParent().getId());
                }

                dto.getListTopicsDto().add(subTopicDto);
            }
        }

        return dto;
    }

    public static Topic toEntity(TopicDTO dto) {
        if (dto == null) {
            return null;
        }

        Topic entity = new Topic();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());

        if (dto.getStudyId() != null) {
            Study study = new Study();
            study.setId(dto.getStudyId());
            entity.setStudy(study);
        }

        if (dto.getTopicParentId() != null) {
            Topic topicParent = new Topic();
            topicParent.setId(dto.getTopicParentId());
            entity.setTopicParent(topicParent);
        }

        return entity;
    }

}
