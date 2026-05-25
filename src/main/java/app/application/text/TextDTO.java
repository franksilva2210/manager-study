package app.application.text;

public class TextDTO {

    private Long id;

    private String content;

    private Long studyId;

    private Long topicId;

    public TextDTO() {

    }

    public TextDTO(Long id, String content, Long studyId, Long topicId) {
        this.id = id;
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
