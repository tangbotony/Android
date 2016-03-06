package com.fanxiaotong.client.widget;

import com.fanxiaotong.client.utils.Im;
import com.fanxiaotong.client.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SC extends LinearLayout {
	private LinearLayout orderLayout;
	private ImageView foodLogo;
	private TextView foodName;
	private TextView foodCount;
	private TextView foodCost;
	private TextView notice;
	private TextView flavor;
	private TextView jardiniere;
	private OnCartOrderClickListener onCartOrderClickListener;
	
	public SC(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.single_order_in_cart, this);
		
		orderLayout = (LinearLayout) findViewById(R.id.order_layout);
		foodLogo = (ImageView) findViewById(R.id.food_logo);
		foodName = (TextView) findViewById(R.id.food_name);
		foodCount = (TextView) findViewById(R.id.food_count);
		foodCost = (TextView) findViewById(R.id.food_cost);
		jardiniere = (TextView) findViewById(R.id.jardiniere);
		flavor = (TextView) findViewById(R.id.flavor);
		notice = (TextView) findViewById(R.id.notice);
		
		
		orderLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				SC.this.onClick();
				
			}
		});
	}
	public LinearLayout getOrderLayout() {
		return orderLayout;
	}

	public ImageView getFoodLogo() {
		return foodLogo;
	}

	@SuppressWarnings("deprecation")
	public void setFoodLogo(Drawable foodLogo) {
		this.foodLogo.setBackgroundDrawable(Im.toRoundBitmap(foodLogo));
	}

	public TextView getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName.setText(foodName);
	}

	public TextView getFoodCount() {
		return foodCount;
	}

	public void setFoodCount(String foodCount) {
		this.foodCount.setText(foodCount);
	}

	public TextView getFoodCost() {
		return foodCost;
	}

	public void setFoodCost(String foodCost) {
		this.foodCost.setText(foodCost);
	}
	
	public interface OnCartOrderClickListener {
		public void onClick();
	}

	public void setOnCartOrderClickListener(
			OnCartOrderClickListener onCartOrderClickListener) {
		this.onCartOrderClickListener = onCartOrderClickListener;
	}

	private void onClick() {
		if (onCartOrderClickListener != null) {
			onCartOrderClickListener.onClick();
		}
	}

	public void setNotice(String notice) {
		this.notice.setText(notice);
	}
	public void setFlavor(String flavor) {
		this.flavor.setText(flavor);
	}
	public void setJardiniere(String jardiniere) {
		this.jardiniere.setText(jardiniere);
	}

}
