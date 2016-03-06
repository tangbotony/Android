package com.fanxiaotong.client.widget;


import com.fanxiaotong.client.R;

import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MPr extends Dialog {

	private ImageView progressCircle_0;
	private ImageView progressCircle_2;
	private ImageView progressCircle_4;
	private TextView tipView;

	private Context context;

	public MPr(Context context) {
		super(context, R.style.myDialog);
		// TODO Auto-generated constructor stub
		this.setContentView(R.layout.my_progress_dialog);
		this.setCanceledOnTouchOutside(false);

		this.context = context;

		progressCircle_0 = (ImageView) findViewById(R.id.progress_circle_0);
		progressCircle_2 = (ImageView) findViewById(R.id.progress_circle_2);
		progressCircle_4 = (ImageView) findViewById(R.id.progress_circle_4);
		tipView = (TextView) findViewById(R.id.tip);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		show("");
	}
	
	public void show(String tip) {
		// TODO Auto-generated method stub
		super.show();
		
		tipView.setText(tip);
		Animation animRotate_0 = AnimationUtils.loadAnimation(context, R.anim.rotate_0);
		Animation animRotate_2 = AnimationUtils.loadAnimation(context, R.anim.rotate_2);
		Animation animRotate_4 = AnimationUtils.loadAnimation(context, R.anim.rotate_4);
		progressCircle_0.startAnimation(animRotate_0);
		progressCircle_2.startAnimation(animRotate_2);
		progressCircle_4.startAnimation(animRotate_4);
	}

}
