package com.fanxiaotong.client.activity;

import com.fanxiaotong.client.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Sp extends Activity {
	boolean isFirstIn = false;

	private static final int GO_HOME = 1000;
	private static final int GO_GUIDE = 1001;
	private static final long SPLASH_DELAY_MILLIS = 3000;

	private static final String SHAREDPREFERENCES_NAME = "first_pref";
//	private ImageView fanImg;
//	private ImageView xiaoImg;
//	private ImageView tongImg;
//	private ImageView introImg;
	

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			case GO_GUIDE:
				goGuide();
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
//		fanImg = (ImageView) findViewById(R.id.fan);
//		xiaoImg = (ImageView) findViewById(R.id.xiao);
//		tongImg = (ImageView) findViewById(R.id.tong);
//		introImg = (ImageView) findViewById(R.id.intro);
		
//		initAnimation();
		
		init();
	}

	private void init() {
		SharedPreferences preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, MODE_PRIVATE);
		isFirstIn = preferences.getBoolean("isFirstIn", true);
		if (!isFirstIn) {
			mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
		}
	}
/*	@SuppressLint("NewApi")
	private void initAnimation(){
		Animation animGoingUp = AnimationUtils.loadAnimation(this, R.anim.going_up);
		Animation animGoingLeft = AnimationUtils.loadAnimation(this, R.anim.going_left);
		Animation animGoingRight = AnimationUtils.loadAnimation(this, R.anim.going_right);
		final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.intro_alpha);
		
		fanImg.startAnimation(animGoingUp);
		xiaoImg.startAnimation(animGoingRight);
		tongImg.startAnimation(animGoingLeft);
		introImg.startAnimation(animAlpha);
	}*/

	private void goHome() {
		Intent intent = new Intent(Sp.this, Sl.class);
		Sp.this.startActivity(intent);
		Sp.this.finish();
	}

	private void goGuide() {
		Intent intent = new Intent(Sp.this, G.class);
		Sp.this.startActivity(intent);
		Sp.this.finish();
	}
}
