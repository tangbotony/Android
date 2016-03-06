package com.fanxiaotong.client.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class FakeHomeRestaurant implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String restName;
	private String photo;//餐馆的logo，实际上传的是地址
	private ArrayList<Map<String,Object>> foodList; //属于这个餐馆的food的集合。
	private int restId;
	public FakeHomeRestaurant(){}
	public FakeHomeRestaurant(String restName, String photo,
			ArrayList<Map<String, Object>> foodList, int restId) {
		super();
		this.restName = restName;
		this.photo = photo;
		this.foodList = foodList;
		this.restId = restId;
	}
	public String getRestName() {
		return restName;
	}
	public void setRestName(String restName) {
		this.restName = restName;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public ArrayList<Map<String, Object>> getFoodList() {
		return foodList;
	}
	public void setFoodList(ArrayList<Map<String, Object>> foodList) {
		this.foodList = foodList;
	}
	public int getRestId() {
		return restId;
	}
	public void setRestId(int restId) {
		this.restId = restId;
	}
	@Override
	public String toString() {
		return "FakeHomeRestaurant [foodList=" + foodList + ", photo=" + photo
				+ ", restId=" + restId + ", restName=" + restName + "]";
	}
	
	
}
