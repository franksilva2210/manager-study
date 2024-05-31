package app.study.register;

import java.util.ArrayList;
import java.util.List;

public class Topic {
	
	private Long id;
	private String title;
	private String text;
	private List<Topic> listTopics;
	
	public Topic() {
		this.listTopics = new ArrayList<>();
	}
	
	public Topic(String title) {
		this.title = title;
		this.listTopics = new ArrayList<>();
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
	
}
