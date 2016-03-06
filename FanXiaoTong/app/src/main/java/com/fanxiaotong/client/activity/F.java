package com.fanxiaotong.client.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import com.fanxiaotong.client.bean.FakeCategoryFood;
import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.utils.Fa;
import com.fanxiaotong.client.utils.Ht;
import com.fanxiaotong.client.utils.I;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.widget.MPr;
import com.fanxiaotong.client.widget.SR;
import com.fanxiaotong.client.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class F extends Activity {
	private TextView backBtn, title;
	private LinearLayout elasticScrollView;
	private int categoryId;
	private String titleString;
	private int flag;
	private MPr mProgressDialog;

	@SuppressLint("HandlerLeak")
	Handler storeINformationHandler = new Handler() {
		@SuppressLint("ResourceAsColor")
		public void handleMessage(Message msg) {
			int flag = msg.what;
			if (flag == ConfigurationFiles.FOODS_INFORMATION_MESSAGE) {
				@SuppressWarnings("unchecked")
				ArrayList<FakeCategoryFood> foodList = (ArrayList<FakeCategoryFood>) msg.obj;
				SR singleRecomFoodView;
				// 添加线性布局
				if (foodList.size() > 0) {

					for (Iterator<FakeCategoryFood> iterator = foodList
							.iterator(); iterator.hasNext();) {
						FakeCategoryFood fakeCategoryFood = (FakeCategoryFood) iterator
								.next();
						singleRecomFoodView = new SR(
								F.this);
						singleRecomFoodView.setFoodId(fakeCategoryFood
								.getFoodID());
						singleRecomFoodView.setFoodName(fakeCategoryFood
								.getFoodName());
						singleRecomFoodView.setFoodPrice(fakeCategoryFood
								.getPrice() + "");
						singleRecomFoodView.setStarsNum(fakeCategoryFood
								.getEvaluate());
						singleRecomFoodView.setMonthSaledNum(fakeCategoryFood
								.getSaleNum() + "");
						I.loadImage(
								ConfigurationFiles.HTTP_PICTURE_PATH,
								fakeCategoryFood.getPhoto(),
								singleRecomFoodView.getFoodPicture(), 1);
						final int foodId = fakeCategoryFood.getFoodID();
						singleRecomFoodView
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										gotoFoodDetailPage(foodId);
									}
								});
						elasticScrollView.addView(singleRecomFoodView);
					}

				} else {
					Toast.makeText(F.this, "没有这样的外卖哦！",
							Toast.LENGTH_SHORT).show();
				}
			} else if (flag == ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE) {
				Li.exit();
				Li.getExitDialog(F.this).show();
			} else {
				// 网络错误...
				Toast.makeText(F.this, "网络有误！", Toast.LENGTH_SHORT)
						.show();
			}
			mProgressDialog.dismiss();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.foods);
		backBtn = (TextView) findViewById(R.id.back_btn);
		title = (TextView) findViewById(R.id.title);
		elasticScrollView = (LinearLayout) findViewById(R.id.food_linearLayout);
		categoryId = getIntent().getIntExtra("categoryId", 0);
		titleString = getIntent().getStringExtra("categoryString");
		flag = getIntent().getIntExtra("flag", 2);
		title.setText(titleString);

		Li.printlnLog("categoryId:" + categoryId);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mProgressDialog = new MPr(F.this);
		mProgressDialog.show("加载中...");
		refresh(categoryId);

	}

	public void refresh(final int categoryId) {
		Runnable runnable = new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				String foodInformationUrl = ConfigurationFiles.HTTP_FOODS_INFORMATION_PATH
						+  Li.simpleEncryptParm(categoryId+"");

				Li.printlnLog("on---error: flag == " + flag);
				Li
						.printlnLog("on---error: categoryId == " + categoryId);

				switch (flag) {
				case 0:
					try {
						foodInformationUrl = ConfigurationFiles.SEARCH_PATH
								+  Li.simpleEncryptParm((URLEncoder.encode(URLEncoder.encode(
										titleString, "gbk"))));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 1:
					foodInformationUrl = ConfigurationFiles.RECOM_PATH;
					break;
				case 2:
					foodInformationUrl = ConfigurationFiles.HTTP_FOODS_INFORMATION_PATH
							+  Li.simpleEncryptParm(categoryId+"");
					break;
				default:
					Li.printlnLog("on---error: flag为不可能出现的值！");
				}

				Li.printlnLog("foodInformationUrl:" + foodInformationUrl);
				String foodInformationString = Ht
						.SendHttpClientPost(foodInformationUrl, null, "utf-8",
								F.this);

				Li.printlnLog("foodInformationString:"
						+ foodInformationString);
				Message foodInformationMessage = Message.obtain();
				/*
				 * @Tangbo 2014.02.27.08.45
				 */
				if (!foodInformationString
						.equals(ConfigurationFiles.HTTP_ERROR)
						&& !foodInformationString
								.equals(ConfigurationFiles.GET_INFORMATION_FAIL)) {
					if (foodInformationString
							.equals(ConfigurationFiles.OTHER_PLACE_LOGIN)) {

						foodInformationMessage.what = ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE;
						storeINformationHandler
								.sendMessage(foodInformationMessage);
					} else {
						foodInformationMessage.what = ConfigurationFiles.FOODS_INFORMATION_MESSAGE;
						ArrayList<FakeCategoryFood> foodList = (ArrayList<FakeCategoryFood>) Fa
								.getList(foodInformationString,
										FakeCategoryFood.class);
						foodInformationMessage.obj = foodList;
						Li.printlnLog("fakeRestInfo:"
								+ foodList.toString());
						storeINformationHandler
								.sendMessage(foodInformationMessage);
					}
				} else {
					foodInformationMessage.what = ConfigurationFiles.NO_INTERNET;
					storeINformationHandler.sendMessage(foodInformationMessage);
				}
			}
		};
		new Thread(runnable).start();
	}

	private void gotoFoodDetailPage(int foodId) {
		Intent intent = new Intent();
		intent.putExtra("foodId", foodId);
		intent.setClass(F.this, D.class);
		startActivity(intent);
	}
}