package com.hoyoul.wordroid.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="WordroidWordset")
public class Wordset {
	
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer id;
	@Column(name="NAME")
	private String name;
	@Column(name="DESCRIPTION")
	private String description;
	@Column(name="REVERSE")
	private Boolean reverse;
	@Column(name="MAGIC7")
	private Boolean magic7;
	
	@OneToMany(mappedBy="wordset",fetch=FetchType.LAZY)
	private List<Word> words;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Word> getWords() {
		return words;
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getReverse() {
		return reverse;
	}

	public void setReverse(Boolean reverse) {
		this.reverse = reverse;
	}

	public Boolean getMagic7() {
		return magic7;
	}

	public void setMagic7(Boolean magic7) {
		this.magic7 = magic7;
	}

	public Wordset(){	
	}
	
	public Wordset(String name, String desc) {
		this.name = name;
		this.description =desc;
	}

}
