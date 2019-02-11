package com.web.check.DTO;

public class BoardDTO {
	private int b_bid;
	private String b_title;
	private int t_tid;
	private String b_admin;
	public int getB_bid() {
		return b_bid;
	}
	public void setB_bid(int b_bid) {
		this.b_bid = b_bid;
	}
	public String getB_title() {
		return b_title;
	}
	public void setB_title(String b_title) {
		this.b_title = b_title;
	}
	public int getT_tid() {
		return t_tid;
	}
	public void setT_tid(int t_tid) {
		this.t_tid = t_tid;
	}
	public String getB_admin() {
		return b_admin;
	}
	public void setB_admin(String b_admin) {
		this.b_admin = b_admin;
	}
}
