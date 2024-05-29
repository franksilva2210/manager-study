package app.study.register;

import java.util.ArrayList;
import java.util.List;

public class Topic {
	
	private Long id;
	private String title;
	private String text;
	private List<Topic> listSubTopics;
	
	public Topic() {
		this.listSubTopics = new ArrayList<>();
	}
	
	public Topic(String title) {
		this.title = title;
		this.listSubTopics = new ArrayList<>();
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

	public List<Topic> getListSubTopics() {
		return listSubTopics;
	}

	public void setListSubTopics(List<Topic> listSubTopics) {
		this.listSubTopics = listSubTopics;
	}
	
}
