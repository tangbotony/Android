package com.fanxiaotong.client.bean;


import java.io.Serializable;

public class FakeMyOrderInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// �ҵĶ�������Ʒ��id����Ʒ���� ��������״̬��
	private int orderID; // �Ӷ�����id
	private int foodID; // ��Ʒ��id
	private String foodName;
	private int amount;
	private float price; // �Ӷ����ĵ��ۺ������ĳ˻�����amount*price
	private int state; // ״̬�����������У��ѵ����
	private String notice;
	private String photo;// 2014������������Ӷ����Ĳ�Ʒ��ͼƬ
	private int isRemarkedByUser = 0;// �����Ҫ�Ǳ�ʶ�ö����Ƿ񱻸��û����۹���������û���ָ�ύ�ö������û�

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
