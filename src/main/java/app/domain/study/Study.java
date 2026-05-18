package app.domain.study;

import app.domain.text.Text;
import app.domain.topic.Topic;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "study")
public class Study {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "study_id")
	private Long id;

	@Column(name = "study_matter")
	private String matter;

	@OneToMany(mappedBy = "study", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Text> listText;

	@OneToMany(mappedBy = "study", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Topic> listTopics;
	
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

}