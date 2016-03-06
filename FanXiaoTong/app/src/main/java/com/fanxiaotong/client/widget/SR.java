package com.fanxiaotong.client.widget;

import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SR extends LinearLayout {

	private ImageView foodPicture;
	private int foodId = 0;
	private TextView foodName;
	private TextView monthSaledNum;
	private TextView foodPrice;
	private TextView starNum;
//	private RelativeLayout singleStoreFoodLayout;

	private OnStoreClickListener onStoreClickListener;

	public SR(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.single_recom_food, this);
		foodPicture = (ImageView) findViewById(R.id.food_picture);
		foodName = (TextView) findViewById(R.id.food_name);
		starNum = (TextView) findViewById(R.id.stars_num);
		monthSaledNum = (TextView) findViewById(R.id.month_saled_num);
		foodPrice = (TextView) findViewById(R.id.food_price);
		
		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SR.this.onClick();
			}
		});

	}

	
	public void setLayoutWidth(WindowManager windowManager){
		this.setLayoutParams(new LayoutParams(Li.getScreenWidth(windowManager)/2, LayoutParams.WRAP_CONTENT));
	}
	
	
	public TextView getStarsNumTextView()
	{
		return this.starNum;
	}
	
	public void setStarsNum(float eve)
	{
		this.starNum.setText(eve+"");
	}
	public ImageView getFoodPicture() {
		return foodPicture;
	}

	public void setFoodPicture(ImageView foodPicture) {
		this.foodPicture = foodPicture;
	}

	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	public String getFoodName() {
		return foodName.getText().toString();
	}

	public String getMonthSaledNum() {
		return monthSaledNum.getText().toString();
	}

	

	public String getFoodPrice() {
		return foodPrice.getText().toString();
	}

	@SuppressWarnings("deprecation")
	public void setFoodPicture(Drawable foodPicture) {
		this.foodPicture.setBackgroundDrawable(foodPicture);
	}

	public void setFoodName(String foodName) {
		this.foodName.setText(foodName);
	}


	public void setMonthSaledNum(String monthSaledNum) {
		this.monthSaledNum.setText(monthSaledNum);
	}

	public void setFoodPrice(String foodPrice) {
		this.foodPrice.setText(foodPrice);
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
			onStoreClickListener.onClick();
		}
	}
}
