package com.fanxiaotong.client.bean;


import java.io.Serializable;

public class FakeHomeAds implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int restFoodId;
	private String photo;
	private int flag;//0代表菜品，1代表餐馆
	public FakeHomeAds(){}
	public FakeHomeAds(int restFoodId, String photo, int flag) {
		super();
		this.restFoodId = restFoodId;
		this.photo = photo;
		this.flag = flag;
	}
	public int getRestFoodId() {
		return restFoodId;
	}
	public void setRestFoodId(int restFoodId) {
		this.restFoodId = restFoodId;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "FakeHomeAds [flag=" + flag + ", photo=" + photo
				+ ", restFoodId=" + restFoodId + "]";
	}
	
}
