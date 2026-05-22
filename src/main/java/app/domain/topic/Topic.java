package app.domain.topic;

import app.domain.study.Study;
import app.domain.text.Text;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Long id;

    @Column(name = "topic_title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", foreignKey = @ForeignKey(name = "fk_topic_study"))
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_parent_id", foreignKey = @ForeignKey(name = "fk_topic_parent"))
    private Topic topicParent;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Text> listText;

    @OneToMany(mappedBy = "topicParent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topic> listTopics;

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

    public List<Text> getListText() {
        return listText;
    }

    public void setListText(List<Text> listText) {
        this.listText = listText;
    }

    public List<Topic> getListTopics() {
        return listTopics;
    }

    public void setListTopics(List<Topic> listTopics) {
        this.listTopics = listTopics;
    }

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public Topic getTopicParent() {
        return topicParent;
    }

    public void setTopicParent(Topic topicParent) {
        this.topicParent = topicParent;
    }

}
