package com.fanxiaotong.client.bean;
import java.io.Serializable;

public class FakeMyInfo implements Serializable{

	/**
	 * 收藏店铺数，收藏菜品数，累计购餐数，默认送餐地址，默认送餐电话
	 */
	private static final long serialVersionUID = 1L;
	private String icon;
	private String name;
	private String phoneNumber;
	private int restNum=0;
	private int foodNum=0;
	private int orderNum=0;
	private String address=null;
	private String defalutNumber=null;
	public FakeMyInfo(){}
	public FakeMyInfo(String icon, String name, String phoneNumber,
			int restNum, int foodNum, int orderNum, String address,
			String defalutNumber) {
		super();
		this.icon = icon;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.restNum = restNum;
		this.foodNum = foodNum;
		this.orderNum = orderNum;
		this.address = address;
		this.defalutNumber = defalutNumber;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getRestNum() {
		return restNum;
	}
	public void setRestNum(int restNum) {
		this.restNum = restNum;
	}
	public int getFoodNum() {
		return foodNum;
	}
	public void setFoodNum(int foodNum) {
		this.foodNum = foodNum;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDefalutNumber() {
		return defalutNumber;
	}
	public void setDefalutNumber(String defalutNumber) {
		this.defalutNumber = defalutNumber;
	}
	@Override
	public String toString() {
		return "FakeMyInfo [icon=" + icon + ", name=" + name + ", phoneNumber="
				+ phoneNumber + ", restNum=" + restNum + ", foodNum=" + foodNum
				+ ", orderNum=" + orderNum + ", address=" + address
				+ ", defalutNumber=" + defalutNumber + "]";
	}
	
}
