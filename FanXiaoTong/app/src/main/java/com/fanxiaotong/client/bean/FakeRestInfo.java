package com.fanxiaotong.client.bean;


import java.io.Serializable;
import java.util.ArrayList;

public class FakeRestInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//���������ǣ����ۡ��绰����������ͼƬ�����档+����������Ʒ����ʽ�����ھ�Ʒ�Ƽ���
	private int restID;
	private String restName;
	private float evaluate;
	private String specialty;
	private ArrayList<FakePhoneNum> fakePhoneNumList;  //�õ��Ĺ��ڵ绰�����һ��list���������ж���绰
	private int saleNum;
	private String notice;
	private String photo;
	private ArrayList<FakeRestFoodInfo> fakeRestFoodInfoList;  //�õ���������͹ݵ����в�Ʒ��Ϣ��list
	private int collection=0;//�Ƿ��û��ղ�  0����û�б��ղ�
	public FakeRestInfo(){}
	public FakeRestInfo(int restID, String restName, float evaluate,
			String specialty, ArrayList<FakePhoneNum> fakePhoneNumList,
			int saleNum, String notice, String photo,
			ArrayList<FakeRestFoodInfo> fakeRestFoodInfoList, int collection) {
		super();
		this.restID = restID;
		this.restName = restName;
		this.evaluate = evaluate;
		this.specialty = specialty;
		this.fakePhoneNumList = fakePhoneNumList;
		this.saleNum = saleNum;
		this.notice = notice;
		this.photo = photo;
		this.fakeRestFoodInfoList = fakeRestFoodInfoList;
		this.collection = collection;
	}
	public int getRestID() {
		return restID;
	}
	public void setRestID(int restID) {
		this.restID = restID;
	}
	public String getRestName() {
		return restName;
	}
	public void setRestName(String restName) {
		this.restName = restName;
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
	public ArrayList<FakePhoneNum> getFakePhoneNumList() {
		return fakePhoneNumList;
	}
	public void setFakePhoneNumList(ArrayList<FakePhoneNum> fakePhoneNumList) {
		this.fakePhoneNumList = fakePhoneNumList;
	}
	public int getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
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
	public ArrayList<FakeRestFoodInfo> getFakeRestFoodInfoList() {
		return fakeRestFoodInfoList;
	}
	public void setFakeRestFoodInfoList(
			ArrayList<FakeRestFoodInfo> fakeRestFoodInfoList) {
		this.fakeRestFoodInfoList = fakeRestFoodInfoList;
	}
	public int getCollection() {
		return collection;
	}
	public void setCollection(int collection) {
		this.collection = collection;
	}
	@Override
	public String toString() {
		return "FakeRestInfo [collection=" + collection + ", evaluate="
				+ evaluate + ", fakePhoneNumList=" + fakePhoneNumList
				+ ", fakeRestFoodInfoList=" + fakeRestFoodInfoList
				+ ", notice=" + notice + ", photo=" + photo + ", restID="
				+ restID + ", restName=" + restName + ", saleNum=" + saleNum
				+ ", specialty=" + specialty + "]";
	}
	
}
