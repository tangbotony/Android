package com.fanxiaotong.client.mybean;

import java.util.List;

public class Fa {
	private String phoneNumber;
	private List<Si> orderList;
	private String address;
	private String notice;
	public Fa() {
		super();
	}
	public Fa(String phoneNumber, List<Si> orderList,
			String address, String notice) {
		super();
		this.phoneNumber = phoneNumber;
		this.orderList = orderList;
		this.address = address;
		this.notice = notice;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public List<Si> getOrderList() {
		return orderList;
	}
	public String getAddress() {
		return address;
	}
	public String getNotice() {
		return notice;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setOrderList(List<Si> orderList) {
		this.orderList = orderList;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
}


