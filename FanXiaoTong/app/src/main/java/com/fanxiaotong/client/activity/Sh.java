package com.fanxiaotong.client.activity;

import com.fanxiaotong.client.R;
import com.fanxiaotong.client.bean.FakeCategoryFood;
import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.utils.Fa;
import com.fanxiaotong.client.utils.Ht;
import com.fanxiaotong.client.utils.I;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.utils.Sho;
import com.fanxiaotong.client.widget.MC;
import com.fanxiaotong.client.widget.MPr;
import com.fanxiaotong.client.widget.SR;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Sh extends Activity {

	private Sho shoutTread;
	private ToggleButton recodeSwitch;
	private MC mCanvasView;
	private LinearLayout foodContainer;
	private LinearLayout tipLayout;
	private TextView clearBtn;
	private TextView backBtn;
	private ScrollView foodContainerScroll;
	private SR recomFoodView;
	private MPr mProgressDialog;

	private int deltX;

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			int flag = msg.what;

			switch (flag) {
			case 0:
				mCanvasView.invalidate(deltX, msg.arg2);
				deltX += ConfigurationFiles.WAVELENGH;
				break;

			case ConfigurationFiles.MESSAGE_I_WANT_A_DISH:
				getFoodInformation();
				recodeSwitch.setChecked(false);
				break;
			case ConfigurationFiles.SHOUT_FOOD_MESSAGE:
				String foodResult = (String) msg.obj;
				FakeCategoryFood fakeCategoryFood = Fa.getSignal(
						foodResult, FakeCategoryFood.class);
				refreshView(fakeCategoryFood);
				break;
			case ConfigurationFiles.HTTP_ERROR_MESSAGE:
				if (mProgressDialog.isShowing())
					mProgressDialog.dismiss();
				Toast.makeText(Sh.this, "Õ¯¬Á”–ŒÛ£°", Toast.LENGTH_SHORT)
						.show();
			default:
				break;
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shout);

		foodContainerScroll = (ScrollView) findViewById(R.id.food_container_scroll);
		tipLayout = (LinearLayout) findViewById(R.id.tip_layout);
		backBtn = (TextView) findViewById(R.id.back_btn);
		clearBtn = (TextView) findViewById(R.id.clear_btn);
		foodContainer = (LinearLayout) findViewById(R.id.food_container);
		recodeSwitch = (ToggleButton) findViewById(R.id.recode_switch);
		mCanvasView = (MC) findViewById(R.id.m_canvas_view);
		foodContainer = (LinearLayout) findViewById(R.id.food_container);
		mProgressDialog = new MPr(Sh.this);
		mCanvasView.setWindowManager(getWindowManager());

		initEvent();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		recodeSwitch.setChecked(false);
		Li.printlnLog("Shout-------stop");
		super.onPause();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initEvent() {

		recodeSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton view, boolean isChecked) {
				// TODO Auto-generated method stub
				recodeSwitch.setChecked(isChecked);
				if (shoutTread == null || shoutTread.isStop()) {
					tipLayout.setVisibility(LinearLayout.GONE);
					shoutTread = new Sho(handler, 0);
					shoutTread.start();
				} else {
					shoutTread.stopShout();
					mCanvasView.clear();
					deltX = 0;
				}
			}
		});

		clearBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				foodContainer.removeAllViews();
				tipLayout.setVisibility(LinearLayout.VISIBLE);
			}
		});

		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void refreshView(FakeCategoryFood fakeCategoryFood) {

		recomFoodView = new SR(Sh.this);
		recomFoodView.setFoodId(fakeCategoryFood.getFoodID());
		recomFoodView.setFoodName(fakeCategoryFood.getFoodName());
		recomFoodView.setFoodPrice(fakeCategoryFood.getPrice() + "");
		recomFoodView.setStarsNum(fakeCategoryFood.getEvaluate());
		recomFoodView.setMonthSaledNum(fakeCategoryFood.getSaleNum() + "");
		I.loadImage(ConfigurationFiles.HTTP_PICTURE_PATH,
				fakeCategoryFood.getPhoto(), recomFoodView.getFoodPicture(), 1);
		final int foodId = fakeCategoryFood.getFoodID();
		recomFoodView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoFoodDetailPage(foodId);
			}
		});

		foodContainer.addView(recomFoodView);

		handler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				foodContainerScroll.fullScroll(ScrollView.FOCUS_DOWN);
				if (mProgressDialog.isShowing())
					mProgressDialog.dismiss();
			}
		});
	}

	private void getFoodInformation() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {

				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mProgressDialog.show("º”‘ÿ÷–");
					}
				});
				String foodURL = ConfigurationFiles.SHOUT_FOOD_URL
						+  Li.simpleEncryptParm(L.loginUserPhoneNum);
				String foodStringResult = Ht.SendHttpClientPost(
						foodURL, null, "utf-8", Sh.this);
				Message message = Message.obtain();
				if (foodStringResult.equals(ConfigurationFiles.HTTP_ERROR)
						|| foodStringResult.equals("[]")) {
					message.what = ConfigurationFiles.HTTP_ERROR_MESSAGE;
				} else {
					message.what = ConfigurationFiles.SHOUT_FOOD_MESSAGE;
					message.obj = foodStringResult;
					Li
							.printlnLog("foodId---------------foodStringResult:"
									+ foodStringResult);
				}
				handler.sendMessage(message);
			}
		};
		new Thread(runnable).start();
	}

	private void gotoFoodDetailPage(int foodId) {
		Intent intent = new Intent();
		intent.putExtra("foodId", foodId);
		Li
				.printlnLog("foodId--------------------------------------------in shoutActivity:"
						+ foodId);
		intent.setClass(Sh.this, D.class);
		startActivity(intent);
	}

}
