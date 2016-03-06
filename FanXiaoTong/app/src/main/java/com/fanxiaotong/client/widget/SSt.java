package com.fanxiaotong.client.widget;

import com.fanxiaotong.client.R;
import com.fanxiaotong.client.utils.Li;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SSt extends LinearLayout {
	private int storeId = 0;
	private ImageView storeLogo;
	private TextView storeName;
	private TextView storeScoreNum;
	private TextView mainFood;
	private TextView salesVolume;
	private LinearLayout rootLayout;
	private OnStoreClickListener onStoreClickListener;

	public static int n;

	public SSt(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.single_store, this);
		storeLogo = (ImageView) findViewById(R.id.store_logo);
		storeName = (TextView) findViewById(R.id.store_name);
		storeScoreNum = (TextView) findViewById(R.id.store_score_num);
		salesVolume = (TextView) findViewById(R.id.sales_volume);
		mainFood = (TextView) findViewById(R.id.main_food);
		rootLayout = (LinearLayout) findViewById(R.id.single_store_layout);
		Li.printlnLog("-----------------" + n++);
		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SSt.this.onClick();
			}
		});
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public void setBackground(int flag) {
		if (flag == 1) {
			rootLayout.setBackgroundResource(R.drawable.single_store_actived);
		} else {
			rootLayout.setBackgroundResource(R.drawable.single_store_bg);
		}
	}

	public ImageView getStoreLogo() {
		Li.printlnLog("storeLogo:" + storeLogo);
		return storeLogo;
	}


	public void setStoreLogo(ImageView storeLogo) {
		this.storeLogo = storeLogo;
	}

	public SSt(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setStoreLogo(Drawable drawable) {
		storeLogo.setImageDrawable(drawable);
	}

	public void setStoreName(String storeName) {
		this.storeName.setText(storeName);
	}

	public void setStoreScore(float storeScore) {
		this.storeScoreNum.setText(storeScore+"");
	}

	public void setMainFood(String mainFood) {
		this.mainFood.setText(mainFood);
	}

	public void setSalesVolume(int salesVolume) {
		this.salesVolume.setText(salesVolume + "");
	}

	public interface OnStoreClickListener {
		public void onClick();
	}

	public void setOnStoreClickListener(
			OnStoreClickListener onStoreClickListener) {
		this.onStoreClickListener = onStoreClickListener;
	}

	private void onClick() {
		if (onStoreClickListener != null) {
			Li.printlnLog("onClick!!!!!--------");
			onStoreClickListener.onClick();
		}
	}

	public String toString() {
		return storeName.getText().toString();
	}

}
