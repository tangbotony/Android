package com.fanxiaotong.client.bean;


import java.io.Serializable;
import java.util.ArrayList;

public class FakeCollection implements Serializable{

	/**
	 * �����ҵ��ղأ�����ʳƷ�͵���
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<FakeCategoryFood> foodList;
	private ArrayList<FakeRestaurant> restaurantList;
	
	public FakeCollection(){}
	public FakeCollection(ArrayList<FakeCategoryFood> foodList,
			ArrayList<FakeRestaurant> restaurantList) {
		super();
		this.foodList = foodList;
		this.restaurantList = restaurantList;
	}
	public ArrayList<FakeCategoryFood> getFoodList() {
		return foodList;
	}
	public void setFoodList(ArrayList<FakeCategoryFood> foodList) {
		this.foodList = foodList;
	}
	public ArrayList<FakeRestaurant> getRestaurantList() {
		return restaurantList;
	}
	public void setRestaurantList(ArrayList<FakeRestaurant> restaurantList) {
		this.restaurantList = restaurantList;
	}
	@Override
	public String toString() {
		return "FakeCollection [foodList=" + foodList + ", restaurantList="
				+ restaurantList + "]";
	}
	
	
	
	
}
