package com.fanxiaotong.client.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class FakeMyOrderForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int orderFormID;
	private String address;
	private Date ordered_date;
	private String notice;
	private List<FakeMyOrderInfo> orderInfoList;
	public FakeMyOrderForm() {
	}
	public FakeMyOrderForm(int orderFormID, String address, Date orderedDate,
			String notice, List<FakeMyOrderInfo> orderInfoList) {
		super();
		this.orderFormID = orderFormID;
		this.address = address;
		ordered_date = orderedDate;
		this.notice = notice;
		this.orderInfoList = orderInfoList;
	}
	public int getOrderFormID() {
		return orderFormID;
	}
	public void setOrderFormID(int orderFormID) {
		this.orderFormID = orderFormID;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getOrdered_date() {
		return ordered_date;
	}
	public void setOrdered_date(Date orderedDate) {
		ordered_date = orderedDate;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public List<FakeMyOrderInfo> getOrderInfoList() {
		return orderInfoList;
	}
	public void setOrderInfoList(List<FakeMyOrderInfo> orderInfoList) {
		this.orderInfoList = orderInfoList;
	}
	@Override
	public String toString() {
		return "FakeMyOrderForm [address=" + address + ", notice=" + notice
				+ ", orderFormID=" + orderFormID + ", orderInfoList="
				+ orderInfoList + ", ordered_date=" + ordered_date + "]";
	}

}
