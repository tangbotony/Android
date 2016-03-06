package com.fanxiaotong.client.bean;

import java.io.Serializable;

public class FakeFood implements Serializable{
	//���������ǣ����������۸�ͼƬ���������������۾������ݣ��Ƿ����ꡣ�����档

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int restId;
	private String restPhoto;
	private int foodID;
	private String foodName;
	private float evaluate;//���ǣ����ȼ�
	private int saleNum;  //������
	private String dishes;
	private String taste;
	private float price;
	private String foodPhoto;
	private int commentNum;  //��������
	private int sellOut=0;  //�Ƿ�����  1����û�����꣬0�����Ѿ�����
	private String notice; // ����(��ҵĹ���)��
	private int collection=0;//�Ƿ��û�  1�����Ѿ��ղأ�0����û���ղ�
	public FakeFood(){}
	public FakeFood(int restId, String restPhoto, int foodID, String foodName,
			float evaluate, int saleNum, String dishes, String taste,
			float price, String foodPhoto, int commentNum, int sellOut,
			String notice, int collection) {
		super();
		this.restId = restId;
		this.restPhoto = restPhoto;
		this.foodID = foodID;
		this.foodName = foodName;
		this.evaluate = evaluate;
		this.saleNum = saleNum;
		this.dishes = dishes;
		this.taste = taste;
		this.price = price;
		this.foodPhoto = foodPhoto;
		this.commentNum = commentNum;
		this.sellOut = sellOut;
		this.notice = notice;
		this.collection = collection;
	}
	public int getRestId() {
		return restId;
	}
	public void setRestId(int restId) {
		this.restId = restId;
	}
	public String getRestPhoto() {
		return restPhoto;
	}
	public void setRestPhoto(String restPhoto) {
		this.restPhoto = restPhoto;
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
	public String getDishes() {
		return dishes;
	}
	public void setDishes(String dishes) {
		this.dishes = dishes;
	}
	public String getTaste() {
		return taste;
	}
	public void setTaste(String taste) {
		this.taste = taste;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getFoodPhoto() {
		return foodPhoto;
	}
	public void setFoodPhoto(String foodPhoto) {
		this.foodPhoto = foodPhoto;
	}
	public int getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	public int getSellOut() {
		return sellOut;
	}
	public void setSellOut(int sellOut) {
		this.sellOut = sellOut;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public int getCollection() {
		return collection;
	}
	public void setCollection(int collection) {
		this.collection = collection;
	}
	
}
