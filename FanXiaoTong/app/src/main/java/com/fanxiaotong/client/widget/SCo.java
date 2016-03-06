package com.fanxiaotong.client.widget;

import com.fanxiaotong.client.R;
import com.fanxiaotong.client.utils.Li;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SCo extends RelativeLayout{
	private ImageView userHead;
	private TextView comment;
	private TextView userName;
	//private RelativeLayout time_layout;
	private TextView date;
	private TextView score;
	private TextView floor;
	
	
	public SCo(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.single_comment, this);
		userHead = (ImageView)findViewById(R.id.user_head);
		userName = (TextView) findViewById(R.id.user_name);
		comment = (TextView)findViewById(R.id.comment);
		date = (TextView)findViewById(R.id.date);
		score = (TextView)findViewById(R.id.score);
		floor = (TextView)findViewById(R.id.floor);
	}
	
	public TextView getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor.setText(floor+"");
	}

	public void setUserLogo(String userHeadCode)
	{
		//…Ë÷√Õ∑œÒ
		Li.setHeadLogoBackground(userHead, userHeadCode);
	}
	
	public void setUserName(String userName)
	{
		this.userName.setText(userName);
	}
	public void setComment(String comment) {
		if (comment == null)
			return;
		else
			this.comment.setText(comment);
	}
	public void setDate(String date) {
		this.date.setText(date);
	}
	public void setScore(String score) {
		this.score.setText(score);
	}
}
