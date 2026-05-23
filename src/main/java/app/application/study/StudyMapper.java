package app.application.study;

import app.application.topic.TopicDTO;
import app.application.topic.TopicMapper;
import app.domain.study.Study;
import app.domain.topic.Topic;

import java.util.ArrayList;
import java.util.List;

public class StudyMapper {

    public static StudyDTO toSimpleDTO(Study entity) {
        if (entity == null) {
            return null;
        }

        StudyDTO dto = new StudyDTO(
                entity.getId(),
                entity.getMatter()
        );

        return dto;
    }

    public static StudyDTO toDTO(Study entity) {
        if (entity == null) {
            return null;
        }

        StudyDTO dto = new StudyDTO(
                entity.getId(),
                entity.getMatter()
        );

        if (entity.getListTopics() != null && !entity.getListTopics().isEmpty()) {

            List<TopicDTO> listTopicsDto = new ArrayList<>();

            for (Topic topic : entity.getListTopics()) {
                listTopicsDto.add(TopicMapper.toSimpleDTO(topic));
            }

            dto.setListTopicsDto(listTopicsDto);
        }

        return dto;
    }

    public static Study toEntity(StudyDTO dto) {

        if (dto == null) {
            return null;
        }

        Study study = new Study();

        study.setId(dto.getId());
        study.setMatter(dto.getMatter());

        return study;
    }

}
