package com.hoyoul.wordroid.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
//@Table(name="WR_WORDSET")
public class Wordset extends Folder {
	
	@Column(name="REVERSE")
	private Boolean reverse = false;
	@Column(name="MAGIC7")
	private Boolean magic7 = false;
	
	@OneToMany(mappedBy="wordset",fetch=FetchType.LAZY,cascade={CascadeType.ALL})
	private List<Word> words = new ArrayList<Word>();
		
	public List<Word> getWords() {
		return words;
	}

	public void setWords(List<Word> words) {
		this.words = words;
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
		super(name,desc);
	}

}
