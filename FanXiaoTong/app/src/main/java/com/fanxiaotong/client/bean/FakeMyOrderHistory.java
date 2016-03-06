package com.fanxiaotong.client.bean;


import java.io.Serializable;
import java.util.Date;

public class FakeMyOrderHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int foodID;
	private String foodName;
	private float price;   //子订单的价格，即买的份数乘以单价，amount*price
	private int amount;
	private Date date;//订餐的日期
	public FakeMyOrderHistory()
	{
		
	}
	public FakeMyOrderHistory(int foodID, String foodName, float price, int amount,
			Date date) {
		super();
		this.foodID = foodID;
		this.foodName = foodName;
		this.price = price;
		this.amount = amount;
		this.date = date;
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
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "MyOrderHistory [amount=" + amount + ", date=" + date
				+ ", foodID=" + foodID + ", foodName=" + foodName + ", price="
				+ price + "]";
	}
	
}
