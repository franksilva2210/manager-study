package app.application.document;

public class DocumentDTO {

    private Long id;
    private String title;
    private String content;
    private Long studyId;
    private Long topicId;

    public DocumentDTO() {
        content = new String();
    }

    public DocumentDTO(Long id, String title, String content, Long studyId, Long topicId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.studyId = studyId;
        this.topicId = topicId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getStudyId() {
        return studyId;
    }

    public void setStudyId(Long studyId) {
        this.studyId = studyId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }
}
