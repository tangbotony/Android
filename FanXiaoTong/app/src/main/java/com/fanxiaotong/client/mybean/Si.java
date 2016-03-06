package com.fanxiaotong.client.mybean;

public class Si {
	private int foodId;
	private int count;
	private String notice;
	public Si() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Si(int foodId, int count, String notice) {
		super();
		this.foodId = foodId;
		this.count = count;
		this.notice = notice;
	}
	public int getFoodId() {
		return foodId;
	}
	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}


}
