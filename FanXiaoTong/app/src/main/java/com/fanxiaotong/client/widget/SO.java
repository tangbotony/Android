package com.fanxiaotong.client.widget;

import com.fanxiaotong.client.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SO extends RelativeLayout {
	private TextView address;
	private TextView remark;
	private TextView orderTime;
	private OnOrderClickListener onOrderClickListener;
	 
	public SO(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.single_order_head, this);
		address = (TextView)findViewById(R.id.address);
		remark = (TextView)findViewById(R.id.remark);
		orderTime = (TextView)findViewById(R.id.order_time);
		//将灸。。。。。虫壞。。。。
		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SO.this.onClick();
			}
		});
	}
	public void setAddress(String address) {
		this.address.setText(address);
	}

	public void setRemark(String remark) {
		this.remark.setText(remark);
	}

	public void setOrderTime(String orderTime) {
		this.orderTime.setText(orderTime);
	}
	public interface OnOrderClickListener
	{
		public void onClick();
	}

	public void setOnRecomClickListener(OnOrderClickListener onOrderClickListener){
		this.onOrderClickListener = onOrderClickListener;
	}

	private void onClick(){
		if(onOrderClickListener != null){
			onOrderClickListener.onClick();
		}
	}
	
}
