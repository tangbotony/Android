package com.fanxiaotong.client.bean;

import java.io.Serializable;

public class FakeHomeCategory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String categoryName;  //类别的名字
	private String  photo;//类别的logo，实际上传的是地址
	private int categoryId;
	public FakeHomeCategory(){}
	public FakeHomeCategory(String categoryName, String photo, int categoryId) {
		super();
		this.categoryName = categoryName;
		this.photo = photo;
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	@Override
	public String toString() {
		return "FakeHomeCategory [categoryId=" + categoryId + ", categoryName="
				+ categoryName + ", photo=" + photo + "]";
	}
	
	
}
