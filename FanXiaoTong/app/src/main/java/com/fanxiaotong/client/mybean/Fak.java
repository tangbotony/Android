package com.fanxiaotong.client.mybean;

public class Fak {
	private int cooking;
	private int sending;
	private int overTime;
	private int refuse;
	public Fak() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Fak(int cooking, int sending, int overTime, int refuse) {
		super();
		this.cooking = cooking;
		this.sending = sending;
		this.overTime = overTime;
		this.refuse = refuse;
	}
	public int getCooking() {
		return cooking;
	}
	public int getSending() {
		return sending;
	}
	public int getOverTime() {
		return overTime;
	}
	public int getRefuse() {
		return refuse;
	}
	public void setCooking(int cooking) {
		this.cooking = cooking;
	}
	public void setSending(int sending) {
		this.sending = sending;
	}
	public void setOverTime(int overTime) {
		this.overTime = overTime;
	}
	public void setRefuse(int refuse) {
		this.refuse = refuse;
	}
}
