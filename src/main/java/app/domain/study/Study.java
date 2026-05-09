package app.domain.study;

import app.domain.topic.Topic;

import java.util.ArrayList;
import java.util.List;

public class Study {
	
	private Long id;
	private String matter;
	private List<String> listText;
	private List<Topic> listTopics;

	public Study() {
		this.id = 0L;
		this.matter = "";
		this.listText = new ArrayList<>();
		this.listTopics = new ArrayList<>();
	}

	public Study(String matter) {
		this.id = 0L;
		this.matter = matter;
		this.listText = new ArrayList<>();
		this.listTopics = new ArrayList<>();
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

	public List<String> getListText() {
		return listText;
	}

	public void setListText(List<String> listText) {
		this.listText = listText;
	}

	public List<Topic> getListTopics() {
		return listTopics;
	}

	public void setListTopics(List<Topic> listTopics) {
		this.listTopics = listTopics;
	}

	public Topic getTopicByTitle(String titleTopic) {
		for(Topic topic : listTopics) {
			if (topic.getTitle().equals(titleTopic)) {
				return topic;
			}
		}
		return null;
	}

	public Boolean existThisTopicInList(Long idTopic) {
		for(Topic topic : this.listTopics){
			if(topic.getId().equals(idTopic)) {
				return true;
			}
		}
		return false;
	}
}