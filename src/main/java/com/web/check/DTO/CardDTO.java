package com.web.check.DTO;

public class CardDTO {
	private int c_cid;
	private String c_title;
	private String c_description;
	private int c_index;
	private int l_lid;
	public int getC_cid() {
		return c_cid;
	}
	public void setC_cid(int c_cid) {
		this.c_cid = c_cid;
	}
	public String getC_title() {
		return c_title;
	}
	public void setC_title(String c_title) {
		this.c_title = c_title;
	}
	public String getC_description() {
		return c_description;
	}
	public void setC_description(String c_description) {
		this.c_description = c_description;
	}
	public int getL_lid() {
		return l_lid;
	}
	public void setL_lid(int l_lid) {
		this.l_lid = l_lid;
	}
	public int getC_index() {
		return c_index;
	}
	public void setC_index(int c_index) {
		this.c_index = c_index;
	}
	
}
