package com.fanxiaotong.client.widget;

import com.fanxiaotong.client.utils.Im;
import com.fanxiaotong.client.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SH extends RelativeLayout {
	private ImageView foodLogo;
	private TextView foodName;
	private TextView foodCount;
	private TextView foodCost;
	private TextView state;
	private TextView remark;
	private LinearLayout orderLayout;
	private LinearLayout dishBtn;
	private LinearLayout commentBtn;
	private OnDishBtnClickListener onDishBtnClickListener;
	private OnCommentBtnClickListener onCommentBtnClickListener;
	
	public SH(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.single_history, this);
		orderLayout = (LinearLayout)findViewById(R.id.order_layout);
		foodLogo = (ImageView)findViewById(R.id.food_logo);
		foodName = (TextView)findViewById(R.id.food_name);
		foodCount = (TextView)findViewById(R.id.food_count);
		foodCost = (TextView)findViewById(R.id.food_cost);
		state = (TextView)findViewById(R.id.state);
		remark = (TextView)findViewById(R.id.remark_label);
		dishBtn = (LinearLayout)findViewById(R.id.dish_layout);
		commentBtn = (LinearLayout) findViewById(R.id.comment_btn);
		//将灸。。。。。虫壞。。。。
		dishBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onDishBtnClick();
			}
		});
		commentBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onCommentBtnClick();
			}
		});
	}
	
	public ImageView getFoodLogo() {
		return foodLogo;
	}

	@SuppressWarnings("deprecation")
	public void setFoodLogo(Drawable foodLogo) {
		this.foodLogo.setBackgroundDrawable(Im.toRoundBitmap(foodLogo));
	}


	public TextView getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark.setText(remark);
	}
	
	public LinearLayout getOrderLayout() {
		return orderLayout;
	}

	public void setOrderLayout(LinearLayout orderLayout) {
		this.orderLayout = orderLayout;
	}



	public TextView getState() {
		return state;
	}

	@SuppressWarnings("deprecation")
	public void setfoodLogoImg(Drawable oodLogo) {
		this.foodLogo.setBackgroundDrawable(oodLogo);
	}

	public void setFoodName(String foodName) {
		this.foodName.setText(foodName);
	}

	public void setFoodCount(String foodCount) {
		this.foodCount.setText(foodCount);
	}

	public void setFoodCost(String foodCost) {
		this.foodCost.setText(foodCost);
	}

	public void setState(String state) {
		this.state.setText(state);
	}
	
	public interface OnDishBtnClickListener
	{
		public void onClick();
	}
	public void setOnDishBtnClickListener(OnDishBtnClickListener onDishBtnClickListener){
		this.onDishBtnClickListener = onDishBtnClickListener;
	}
	private void onDishBtnClick(){
		if(onDishBtnClickListener != null){
			onDishBtnClickListener.onClick();
		}
	}
	
	public interface OnCommentBtnClickListener
	{
		public void onClick();
	}
	public void SetOnCommentBtnClickListener(OnCommentBtnClickListener onCommentBtnClickListener){
		this.onCommentBtnClickListener = onCommentBtnClickListener;
	}
	private void onCommentBtnClick(){
		if(onCommentBtnClickListener != null){
			onCommentBtnClickListener.onClick();
		}
	}
}
