package app.domain.document;

import app.domain.study.Study;
import app.domain.topic.Topic;
import jakarta.persistence.*;

@Entity
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long id;

    @Column(name = "document_title")
    private String title;

    @Column(name = "document_content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", foreignKey = @ForeignKey(name = "fk_document_study"))
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", foreignKey = @ForeignKey(name = "fk_document_topic"))
    private Topic topic;

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

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
