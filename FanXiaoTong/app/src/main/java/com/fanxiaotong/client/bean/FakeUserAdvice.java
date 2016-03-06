package com.fanxiaotong.client.bean;


import java.util.Date;

/**
 * 返回给小波用的，是查询某个用户以前所有意见或是建议的内容
 * @author NB
 *
 */
public class FakeUserAdvice {
	
	private String content;
	private Date date;
	public FakeUserAdvice(){}
	public FakeUserAdvice(String content, Date date) {
		super();
		this.content = content;
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "FakeUserAdvice [content=" + content + ", date=" + date + "]";
	}
	
	

}
