package com.fanxiaotong.client.bean;



import java.io.Serializable;


public class FakeRestaurant implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String restaurantName;
	private String  specialty;
	private String photo;
	private int saleNum;
	private float evaluate;
	
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public float getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(float evaluate) {
		this.evaluate = evaluate;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public int getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
	}
	
	public FakeRestaurant()
	{
		this.id = 0;
		restaurantName = "好像出错了";
		this.specialty = "好像出错了";
		this.photo = "好像出错了";
		this.saleNum = 0;
		this.evaluate = 0;
	}
	public FakeRestaurant(int id, String restaurantName, String specialty,
			String photo, int saleNum, float evaluate) {
		super();
		this.id = id;
		this.restaurantName = restaurantName;
		this.specialty = specialty;
		this.photo = photo;
		this.saleNum = saleNum;
		this.evaluate = evaluate;
	}
	@Override
	public String toString() {
		return "FakeRestaurant [evaluate=" + evaluate + ", id=" + id
				+ ", photo=" + photo + ", restaurantName=" + restaurantName
				+ ", saleNum=" + saleNum + ", specialty=" + specialty + "]";
	}
	
	
	
	
	
}
