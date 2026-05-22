package app.application.dto;

import java.util.ArrayList;
import java.util.List;

public class TopicDTO {

    private Long id;
    private String title;
    private Long studyId;
    private Long parentTopicId;
    private List<TopicDTO> listTopics = new ArrayList<>();

    public TopicDTO(
            Long id,
            String title,
            Long studyId,
            Long parentTopicId) {

        this.id = id;
        this.title = title;
        this.studyId = studyId;
        this.parentTopicId = parentTopicId;
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

    public Long getStudyId() {
        return studyId;
    }

    public void setStudyId(Long studyId) {
        this.studyId = studyId;
    }

    public Long getParentTopicId() {
        return parentTopicId;
    }

    public void setParentTopicId(Long parentTopicId) {
        this.parentTopicId = parentTopicId;
    }

    public List<TopicDTO> getListTopics() {
        return listTopics;
    }

    public void setListTopics(List<TopicDTO> listTopics) {
        this.listTopics = listTopics;
    }

    @Override
    public String toString() {
        return title;
    }
}
