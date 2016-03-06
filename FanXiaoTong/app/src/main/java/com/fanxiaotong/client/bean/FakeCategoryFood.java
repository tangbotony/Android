package com.fanxiaotong.client.bean;

import java.io.Serializable;

public class FakeCategoryFood implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//菜名，评星，周销量，价格，图片
	private int foodID;
	private String foodName;
	private float evaluate;
	private int saleNum;
	private float price;
	private String photo;
	public FakeCategoryFood(){}
	public FakeCategoryFood(int foodID, String foodName, float evaluate,
			int saleNum, float price, String photo) {
		super();
		this.foodID = foodID;
		this.foodName = foodName;
		this.evaluate = evaluate;
		this.saleNum = saleNum;
		this.price = price;
		this.photo = photo;
	}
	public int getFoodID() {
		return foodID;
	}
	public void setFoodID(int foodID) {
		this.foodID = foodID;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public float getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(float evaluate) {
		this.evaluate = evaluate;
	}
	public int getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
}
