package app.domain.topic;

import java.util.ArrayList;
import java.util.List;

public class Topic {

    private Long id;
    private String title;
    private String text;
    private List<Topic> listTopics;

    public Topic() {
        this.id = 0L;
        this.title = "";
        this.text = "";
        this.listTopics = new ArrayList<>();
    }

    public Topic(String title) {
        this.id = 0L;
        this.title = title;
        this.text = "";
        this.listTopics = new ArrayList<>();
    }

    public Topic(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.text = topic.getText();
        this.listTopics = topic.getListTopics();
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Topic> getListTopics() {
        return listTopics;
    }

    public void setListTopics(List<Topic> listTopics) {
        this.listTopics = listTopics;
    }

    public Topic searchTopicByTitle() {
        return null;
    }

    public boolean verifyUpdateInTitle(Topic topic) {
        if (!this.title.equals(topic.getTitle())) {
            return true;
        }
        return false;
    }

}
