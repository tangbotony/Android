package com.fanxiaotong.client.widget;


import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SU extends RelativeLayout {

	private static int n = 0;
	private OnUserNameClickListener onSingleUserClickListener;
	private OnDeleteBtnClickListener onDeleteBtnClickListener;

	private TextView username;
	private ImageView deleteBtn;

	public SU(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Li.printlnLog("SingleUserView---" + n++);

		LayoutInflater.from(context).inflate(R.layout.single_user, this);

		username = (TextView) findViewById(R.id.user_name);
		deleteBtn = (ImageView) findViewById(R.id.delete_btn);

		initEvent();
	}

	private void initEvent() {
		username.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onUserNameClick();
			}
		});

		deleteBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onDeleteBtnClick();
			}
		});
	}

	public void setUserName(String text) {
		username.setText(text);
	}

	public String getUserName() {
		return username.getText().toString();
	}

	/**
	 * ���Զ���ؼ��󲿵��û����ĵ���¼������ӿ�
	 */
	public interface OnUserNameClickListener {
		public void onClick();
	}

	public void setOnUserNameClickListener(
			OnUserNameClickListener onSingleOrderClickListener) {
		this.onSingleUserClickListener = onSingleOrderClickListener;
	}

	private void onUserNameClick() {
		if (onSingleUserClickListener != null) {
			Li.printlnLog("onClick!!!!!-----onUserNameClick");
			onSingleUserClickListener.onClick();
		}
	}

	/**
	 * ���Զ���ؼ��Ҳ���ɾ����ť�ĵ���¼������ӿ�
	 */
	public interface OnDeleteBtnClickListener {
		public void onClick();
	}

	public void setOnDeleteBtnClickListener(
			OnDeleteBtnClickListener onDeleteBtnClickListener) {
		this.onDeleteBtnClickListener = onDeleteBtnClickListener;
	}

	private void onDeleteBtnClick() {
		if (onSingleUserClickListener != null) {
			Li.printlnLog("onClick!!!!!-----onDeleteBtnClick");
			onDeleteBtnClickListener.onClick();
		}
	}

	public String toString() {
		return username.getText().toString();
	}
}
