package app.application.text;

import app.domain.study.Study;
import app.domain.text.Text;
import app.domain.topic.Topic;

public class TextMapper {

    public static TextDTO toDTO(Text entity) {
        TextDTO dto = new TextDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());

        if (entity.getStudy() != null) {
            dto.setStudyId(entity.getStudy().getId());
        }

        if (entity.getTopic() != null) {
            dto.setTopicId(entity.getTopic().getId());
        }

        return dto;
    }

    public static Text toEntity(TextDTO dto) {
        Text entity = new Text();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());

        if (dto.getStudyId() != null) {
            Study study = new Study();
            study.setId(dto.getStudyId());
            entity.setStudy(study);
        }

        if (dto.getTopicId() != null) {
            Topic topic = new Topic();
            topic.setId(dto.getTopicId());
            entity.setTopic(topic);
        }

        return entity;
    }
}
