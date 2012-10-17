package com.hoyoul.wordroid.dto;

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
@Table(name="WR_FOLDER")
public class Folder {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer id;
	@Column(name="NAME")
	private String name;
	@Column(name="DESCRIPTION")
	
	@OneToMany(mappedBy="folder",fetch=FetchType.LAZY)
	private List<Wordset> wordsets;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="USERID")
	private User user;

	@ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="PARENT_FOLDERID")
	private Folder parentFolder;
	
	@OneToMany(mappedBy="parentFolder",fetch=FetchType.LAZY)
	private List<Folder> childrenFolder;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Wordset> getWordsets() {
		return wordsets;
	}

	public void setWordsets(List<Wordset> wordsets) {
		this.wordsets = wordsets;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Folder getParentFolder() {
		return parentFolder;
	}

	public void setParentFolder(Folder parentFolder) {
		this.parentFolder = parentFolder;
	}

	public List<Folder> getChildrenFolder() {
		return childrenFolder;
	}

	public void setChildrenFolder(List<Folder> childrenFolder) {
		this.childrenFolder = childrenFolder;
	}
	
}
