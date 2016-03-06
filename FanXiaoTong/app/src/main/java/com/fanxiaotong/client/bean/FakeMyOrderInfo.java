package com.fanxiaotong.client.bean;


import java.io.Serializable;

public class FakeMyOrderInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 我的订单：菜品的id，菜品名， 数量，金额，状态。
	private int orderID; // 子订单的id
	private int foodID; // 菜品的id
	private String foodName;
	private int amount;
	private float price; // 子订单的单价和数量的乘积，即amount*price
	private int state; // 状态，例如做餐中，已到达等
	private String notice;
	private String photo;// 2014新增返回这个子订单的菜品的图片
	private int isRemarkedByUser = 0;// 这个主要是标识该订单是否被该用户评论过，这个该用户是指提交该订单的用户

	public FakeMyOrderInfo() {
	}

	public FakeMyOrderInfo(int orderID, int foodID, String foodName,
			int amount, float price, int state, String notice, String photo,
			int isRemarkedByUser) {
		super();
		this.orderID = orderID;
		this.foodID = foodID;
		this.foodName = foodName;
		this.amount = amount;
		this.price = price;
		this.state = state;
		this.notice = notice;
		this.photo = photo;
		this.isRemarkedByUser = isRemarkedByUser;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getIsRemarkedByUser() {
		return isRemarkedByUser;
	}

	public void setIsRemarkedByUser(int isRemarkedByUser) {
		this.isRemarkedByUser = isRemarkedByUser;
	}

	@Override
	public String toString() {
		return "FakeMyOrderInfo [amount=" + amount + ", foodID=" + foodID
				+ ", foodName=" + foodName + ", isRemarkedByUser="
				+ isRemarkedByUser + ", notice=" + notice + ", orderID="
				+ orderID + ", photo=" + photo + ", price=" + price
				+ ", state=" + state + "]";
	}

}
