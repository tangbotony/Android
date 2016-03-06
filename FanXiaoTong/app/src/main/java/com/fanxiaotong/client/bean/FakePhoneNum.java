package com.fanxiaotong.client.bean;


import java.io.Serializable;

public class FakePhoneNum implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String phoneNum;

	public FakePhoneNum()
	{
		this.phoneNum = "好像加载出错了";
	}
	public FakePhoneNum(String phoneNum) {
		super();
		this.phoneNum = phoneNum;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	@Override
	public String toString() {
		return "FakePhoneNum [phoneNum=" + phoneNum + "]";
	}
	
	
}
