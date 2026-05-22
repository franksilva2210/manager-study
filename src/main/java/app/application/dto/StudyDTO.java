package app.application.dto;

import java.util.ArrayList;
import java.util.List;

public class StudyDTO {

    private Long id;
    private String matter;
    private List<TopicDTO> listTopics = new ArrayList<>();

    public StudyDTO(Long id, String matter) {
        this.id = id;
        this.matter = matter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public List<TopicDTO> getListTopics() {
        return listTopics;
    }

    public void setListTopics(List<TopicDTO> listTopics) {
        this.listTopics = listTopics;
    }

    @Override
    public String toString() {
        return matter;
    }
}
