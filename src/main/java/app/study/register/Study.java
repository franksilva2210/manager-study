package app.study.register;

import java.util.ArrayList;
import java.util.List;

public class Study {
	
	private Long id;
	private String matter;
	private String text;
	private List<Topic> listTopics;

	public Study() {
		this.matter = "";
		this.text = "";
		this.listTopics = new ArrayList<>();
	}

	public Study(String matter) {
		this.matter = matter;
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

	public List<Topic> getListTopics() {
		return listTopics;
	}

	public void setListTopics(List<Topic> listTopics) {
		this.listTopics = listTopics;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}