package com.hoyoul.wordroid.dto;

import java.util.List;

public class ReturnData {
	private Long total;
	private List rows;
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
}
