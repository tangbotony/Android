package com.fanxiaotong.client.bean;


import java.util.Date;

/**
 * ���ظ�С���õģ��ǲ�ѯĳ���û���ǰ����������ǽ��������
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
