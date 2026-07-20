package app.application.study;

import app.application.document.DocumentDTO;
import app.application.document.DocumentMapper;
import app.application.topic.TopicDTO;
import app.application.topic.TopicMapper;
import app.domain.study.Study;
import app.domain.document.Document;
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

        if (entity.getListDocuments() != null && !entity.getListDocuments().isEmpty()) {
            List<DocumentDTO> listDocumentsDto = new ArrayList<>();

            for (Document document : entity.getListDocuments()) {
                listDocumentsDto.add(DocumentMapper.toDTO(document));
            }

            dto.setListDocumentsDto(listDocumentsDto);
        }

        return dto;
    }

    public static StudyDTO toFullDTO(Study entity) {
        if (entity == null) {
            return null;
        }

        StudyDTO dto = new StudyDTO(
                entity.getId(),
                entity.getMatter()
        );

        if (entity.getListTopics() != null) {
            List<TopicDTO> listTopicsDto = new ArrayList<>();

            for (Topic topic : entity.getListTopics()) {
                listTopicsDto.add(TopicMapper.toFullDTO(topic));
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
