package com.fanxiaotong.client.widget;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SF extends LinearLayout {

	//private static int n = 0;

	private TextView feedback;
	private TextView date;

	public SF(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

		LayoutInflater.from(context).inflate(R.layout.single_feedback, this);

		feedback = (TextView) findViewById(R.id.feedback);
		date = (TextView) findViewById(R.id.date);

	}

	public void setFeedback(String text) {
		feedback.setText(text);
	}

	@SuppressLint("SimpleDateFormat")
	public void setDate(Date date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time;
		if (date != null) {
			time = Li.changeDateToString(date);
		} else {
			time = format.format(new Date());
		}

		this.date.setText(time);
	}

	public String toString() {
		return feedback.getText().toString();
	}
}
