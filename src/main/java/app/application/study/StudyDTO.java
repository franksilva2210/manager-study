package app.application.study;

import app.application.document.DocumentDTO;
import app.application.topic.TopicDTO;

import java.util.ArrayList;
import java.util.List;

public class StudyDTO {

    private Long id;
    private String matter;
    private List<TopicDTO> listTopicsDto = new ArrayList<>();
    private List<DocumentDTO> listDocumentsDto = new ArrayList<>();

    public StudyDTO() {

    }

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

    public List<TopicDTO> getListTopicsDto() {
        return listTopicsDto;
    }

    public void setListTopicsDto(List<TopicDTO> listTopicsDto) {
        this.listTopicsDto = listTopicsDto;
    }

    public List<DocumentDTO> getListDocumentsDto() {
        return listDocumentsDto;
    }

    public void setListDocumentsDto(List<DocumentDTO> listDocumentsDto) {
        this.listDocumentsDto = listDocumentsDto;
    }

    @Override
    public String toString() {
        return matter;
    }
}
