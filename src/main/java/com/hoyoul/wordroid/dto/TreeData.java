package com.hoyoul.wordroid.dto;

public class TreeData {
	private Integer id;
	private String text;
	private String state;
	private TreeAttributes attributes = new TreeAttributes();
	
	public TreeAttributes getAttributes() {
		return attributes;
	}
	public void setAttributes(TreeAttributes attributes) {
		this.attributes = attributes;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	String type;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	
	
}
