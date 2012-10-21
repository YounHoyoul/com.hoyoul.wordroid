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
@Table(name="WR_FOLDER")
public class Folder {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer id;
	@Column(name="NAME")
	private String name;
	@Column(name="DESCRIPTION")
	private String description;
	
	//@OneToMany(mappedBy="folder",fetch=FetchType.LAZY)
	//private List<Wordset> wordsets = new ArrayList<Wordset>();
	
	@ManyToOne/*(cascade={CascadeType.ALL})*/
	@JoinColumn(name="USERID")
	private User folderUser;

	@ManyToOne/*(cascade={CascadeType.ALL})*/
    @JoinColumn(name="PARENT_FOLDERID")
	private Folder parentFolder;
	
	@OneToMany(mappedBy="parentFolder",fetch=FetchType.LAZY,cascade={CascadeType.ALL})
	private List<Folder> childrenFolder = new ArrayList<Folder>();
	
	public Folder(){
		
	}
	
	public Folder(String name, String description) {
		this.name=name;
		this.description=description;
	}

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

	public User getFolderUser() {
		return folderUser;
	}

	public void setFolderUser(User user) {
		this.folderUser = user;
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
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
}
