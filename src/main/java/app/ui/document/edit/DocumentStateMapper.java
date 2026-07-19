package app.ui.document.edit;

import app.application.document.DocumentDTO;

public class DocumentStateMapper {

    public static void fillState(DocumentState state, DocumentDTO dto) {

        if (state == null || dto == null) {
            return;
        }

        state.idProperty().set(dto.getId());
        state.titleProperty().set(dto.getTitle());
        state.contentProperty().set(dto.getContent());
        state.studyIdProperty().set(dto.getStudyId());
        state.topicIdProperty().set(dto.getTopicId());
        state.documentDtoProperty().set(dto);
    }

    public static void fillDTO(DocumentDTO dto, DocumentState state) {

        if (dto == null || state == null) {
            return;
        }

        dto.setId(state.getId());
        dto.setTitle(state.getTitle());
        dto.setContent(state.getContent());
        dto.setStudyId(state.getStudyId());
        dto.setTopicId(state.getTopicId());
    }

}
