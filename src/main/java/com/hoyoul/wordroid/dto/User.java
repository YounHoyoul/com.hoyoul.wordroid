package com.hoyoul.wordroid.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="WR_USER")
public class User {
	
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer id;
	@Column(name="NAME")
	private String name;
	@Column(name="LOGINID")
	private String loginId;
	@Column(name="PASSWORD")
	private String password;
	@Column(name="EMAIL")
	private String email;
	
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	private List<Wordset> wordsets = new ArrayList<Wordset>();
	
	@OneToMany(mappedBy="folderUser",fetch=FetchType.LAZY)
	private List<Folder> folders = new ArrayList<Folder>();;

	public User(){}
	
	public User(String name,String loginId,String password,String email){
		this.name=name;
		this.loginId=loginId;
		this.password=password;
		this.email=email;
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
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Wordset> getWordsets() {
		return wordsets;
	}
	public void setWordsets(List<Wordset> wordsets) {
		this.wordsets = wordsets;
	}
	public List<Folder> getFolders() {
		return folders;
	}
	public void setFolders(List<Folder> folders) {
		this.folders = folders;
	}
}
