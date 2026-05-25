package app.application.text;

import app.domain.study.Study;
import app.domain.text.Text;
import app.domain.topic.Topic;

public class TextMapper {

    public static TextDTO toDTO(Text text) {
        TextDTO dto = new TextDTO();
        dto.setId(text.getId());
        dto.setContent(text.getContent());

        if (text.getStudy() != null) {
            dto.setStudyId(text.getStudy().getId());
        }

        if (text.getTopic() != null) {
            dto.setTopicId(text.getTopic().getId());
        }

        return dto;
    }

    public static Text toEntity(TextDTO dto) {
        Text text = new Text();
        text.setId(dto.getId());
        text.setContent(dto.getContent());

        if (dto.getStudyId() != null) {
            Study study = new Study();
            study.setId(dto.getStudyId());
            text.setStudy(study);
        }

        if (dto.getTopicId() != null) {
            Topic topic = new Topic();
            topic.setId(dto.getTopicId());
            text.setTopic(topic);
        }

        return text;
    }
}
