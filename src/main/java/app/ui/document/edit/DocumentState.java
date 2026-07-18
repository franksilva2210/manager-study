package app.ui.document.edit;

import app.application.document.DocumentDTO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DocumentState {

    /* Id */

    private final ObjectProperty<Long> id = new SimpleObjectProperty<>();

    public Long getId() {
        return id.get();
    }

    public ObjectProperty<Long> idProperty() {
        return id;
    }

    /* Title */

    private final StringProperty title = new SimpleStringProperty("");

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    /* Content */

    private final StringProperty content = new SimpleStringProperty("");

    public String getContent() {
        return content.get();
    }

    public StringProperty contentProperty() {
        return content;
    }

    /* Study Id */

    private final ObjectProperty<Long> studyId =
            new SimpleObjectProperty<>();

    public Long getStudyId() {
        return studyId.get();
    }

    public ObjectProperty<Long> studyIdProperty() {
        return studyId;
    }

    /* Topic id */

    private final ObjectProperty<Long> topicId =
            new SimpleObjectProperty<>();

    public Long getTopicId() {
        return topicId.get();
    }

    public ObjectProperty<Long> topicIdProperty() {
        return topicId;
    }

    /* Aux */

    private final ObjectProperty<DocumentDTO> documentDTO =
            new SimpleObjectProperty<>();

    public DocumentDTO getDocumentDTO() {
        return documentDTO.get();
    }

    public ObjectProperty<DocumentDTO> documentDtoProperty() {
        return documentDTO;
    }
}
