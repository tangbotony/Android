package com.fanxiaotong.client.bean;


import java.io.Serializable;
import java.util.ArrayList;

public class FakeHomePage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<FakeHomeCategory> categoryList;  
	private ArrayList<FakeHomeRestaurant> restaurantList;
	private ArrayList<FakeHomeAds> adsList;
	public FakeHomePage(){}
	public FakeHomePage(ArrayList<FakeHomeCategory> categoryList,
			ArrayList<FakeHomeRestaurant> restaurantList,
			ArrayList<FakeHomeAds> adsList) {
		super();
		this.categoryList = categoryList;
		this.restaurantList = restaurantList;
		this.adsList = adsList;
	}
	public ArrayList<FakeHomeCategory> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(ArrayList<FakeHomeCategory> categoryList) {
		this.categoryList = categoryList;
	}
	public ArrayList<FakeHomeRestaurant> getRestaurantList() {
		return restaurantList;
	}
	public void setRestaurantList(ArrayList<FakeHomeRestaurant> restaurantList) {
		this.restaurantList = restaurantList;
	}
	public ArrayList<FakeHomeAds> getAdsList() {
		return adsList;
	}
	public void setAdsList(ArrayList<FakeHomeAds> adsList) {
		this.adsList = adsList;
	}
	@Override
	public String toString() {
		return "FakeHomePage [adsList=" + adsList + ", categoryList="
				+ categoryList + ", restaurantList=" + restaurantList + "]";
	}
	
	
	
}
