package com.fanxiaotong.client.widget;


import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

public class MP extends PopupWindow {

	//private Context context;
	private LinearLayout userNameContainer;


	@SuppressWarnings("deprecation")
	public MP(Context context) {
		super(context);

		//this.context = context;

		View contentView = LayoutInflater.from(context).inflate(
				R.layout.poplistview, null);

		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setContentView(contentView);
		this.setOutsideTouchable(true);
		this.setFocusable(true);

		this.setBackgroundDrawable(new BitmapDrawable());

		Li.printlnLog("new mPopListView");
		
		userNameContainer = (LinearLayout) contentView.findViewById(R.id.user_name_container);

		initEvent();
	}
	
	public void addView(View view){
		userNameContainer.addView(view);
	}
	
	public LinearLayout getContainer(){
		return userNameContainer;
	}
	
	public void removeAll(){
		userNameContainer.removeAllViews();
	}
	
	public void removeAt(int i){
		userNameContainer.removeViewAt(i);
	}
	public void removeView(View view)
	{
		userNameContainer.removeView(view);
	}

	private void initEvent() {
	}

}
