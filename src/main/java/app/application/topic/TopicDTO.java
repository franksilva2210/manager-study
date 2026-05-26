package app.application.topic;

import app.application.document.DocumentDTO;

import java.util.ArrayList;
import java.util.List;

public class TopicDTO {

    private Long id;
    private String title;
    private Long studyId;
    private String studyMatter;
    private Long topicParentId;
    private List<TopicDTO> listTopicsDto = new ArrayList<>();
    private List<DocumentDTO> listDocumentsDto = new ArrayList<>();

    public TopicDTO() {

    }

    public TopicDTO(
            Long id,
            String title,
            Long studyId,
            Long topicParentId) {

        this.id = id;
        this.title = title;
        this.studyId = studyId;
        this.topicParentId = topicParentId;
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

    public String getStudyMatter() {
        return studyMatter;
    }

    public void setStudyMatter(String studyMatter) {
        this.studyMatter = studyMatter;
    }

    public Long getTopicParentId() {
        return topicParentId;
    }

    public void setTopicParentId(Long topicParentId) {
        this.topicParentId = topicParentId;
    }

    public List<TopicDTO> getListTopicsDto() {
        return listTopicsDto;
    }

    public void setListTopicsDto(List<TopicDTO> listTopicsDto) {
        this.listTopicsDto = listTopicsDto;
    }

    public List<DocumentDTO> getListTextDto() {
        return listDocumentsDto;
    }

    public void setListTextDto(List<DocumentDTO> listDocumentsDto) {
        this.listDocumentsDto = listDocumentsDto;
    }

    @Override
    public String toString() {
        return title;
    }
}
