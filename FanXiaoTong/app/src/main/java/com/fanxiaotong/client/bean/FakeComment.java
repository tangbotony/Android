package com.fanxiaotong.client.bean;

import java.io.Serializable;
import java.util.Date;

public class FakeComment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String commentUser;  //评论的人
	private Date commentDate;  //评论的日期
	private String commentContent;//具体评论的内容
	private int level;   //评价的等级
	private String icon;//评论人的头像
	
	public FakeComment(){}
	public FakeComment(String commentUser, Date commentDate,
			String commentContent, int level, String icon) {
		super();
		this.commentUser = commentUser;
		this.commentDate = commentDate;
		this.commentContent = commentContent;
		this.level = level;
		this.icon = icon;
	}
	
	@Override
	public String toString() {
		return "FakeComment [commentContent=" + commentContent
				+ ", commentDate=" + commentDate + ", commentUser="
				+ commentUser + ", icon=" + icon + ", level=" + level + "]";
	}
	public String getCommentUser() {
		return commentUser;
	}
	public void setCommentUser(String commentUser) {
		this.commentUser = commentUser;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
