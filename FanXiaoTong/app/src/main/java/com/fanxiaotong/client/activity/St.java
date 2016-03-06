package com.fanxiaotong.client.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fanxiaotong.client.bean.FakeRestFoodInfo;
import com.fanxiaotong.client.bean.FakeRestInfo;
import com.fanxiaotong.client.bean.PictureData;
import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.fragment.Le;
import com.fanxiaotong.client.utils.Fa;
import com.fanxiaotong.client.utils.Ht;
import com.fanxiaotong.client.utils.I;
import com.fanxiaotong.client.utils.Im;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.widget.MPr;
import com.fanxiaotong.client.widget.SS;
import com.fanxiaotong.client.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class St extends Activity {

	private ScrollView rootLayout;
	private LinearLayout foodContainer;
	private ImageView callTheStore, collection;
	private TextView storeName;
	private TextView mainFood;
	private TextView storeScore;
	private TextView salesVolume;
	private String phoneNumber = "";
	private TextView announcement;
	private int storeId;
	private MPr mProgressDialog;

	private int isCollection = 0;

	@SuppressLint("HandlerLeak")
	Handler storeLogoHandler = new Handler() {
		@SuppressWarnings("deprecation")
		public void handleMessage(Message msg) {
			int flag = msg.what;
			if (flag == ConfigurationFiles.STORE_LOGO_IMAGE_MESSAGE) {
				PictureData pictureData = (PictureData) msg.obj;
				pictureData.getImageView()
						.setBackgroundDrawable(
								Im.toRoundBitmap(pictureData
										.getDrawable()));
			} else if (flag == ConfigurationFiles.COLLECTION_MESSAGE) {
				// 收藏
				String result = (String) msg.obj;
				if (!result.equals(ConfigurationFiles.HTTP_ERROR)) {
					if (result.equals(ConfigurationFiles.COLLECTION_SUCCEED)) {
						// Toast.makeText(DetailFoodInfoActivity.this, "收藏成功！",
						// Toast.LENGTH_SHORT).show();
						if (isCollection == 1) {
							L.fakeMyInfo
									.setRestNum(L.fakeMyInfo
											.getRestNum() - 1);
							// LeftFragment.iniUI();
							collection
									.setImageResource(R.drawable.heart_normal);
							isCollection = 0;
							Toast.makeText(St.this, "取消收藏成功！",
									Toast.LENGTH_SHORT).show();
						} else {
							L.fakeMyInfo
									.setRestNum(L.fakeMyInfo
											.getRestNum() + 1);
							Le.iniUI();
							collection
									.setImageResource(R.drawable.heart_actived);
							isCollection = 1;
							Toast.makeText(St.this, "收藏成功！",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(St.this, "数据提交失败，请重新提交！",
								Toast.LENGTH_SHORT).show();
					}
				}
			} else if (flag == ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE) {
				Li.exit();
				Li.getExitDialog(St.this).show();
			}
			mProgressDialog.dismiss();
		};
	};
	@SuppressLint("HandlerLeak")
	Handler storeINformationHandler = new Handler() {
		@SuppressLint("ResourceAsColor")
		public void handleMessage(Message msg) {
			int flag = msg.what;

			if (flag == ConfigurationFiles.STORE_INFORMATION_MESSAGE) {
				FakeRestInfo fakeRestInfo = (FakeRestInfo) msg.obj;
				isCollection = fakeRestInfo.getCollection();
				if (isCollection == 1) {
					collection.setImageResource(R.drawable.heart_actived);
				} else if (isCollection == 0) {
					collection.setImageResource(R.drawable.heart_normal);
				}
				storeName.setText(fakeRestInfo.getRestName());
				announcement.setText("公告：" + fakeRestInfo.getNotice());
				salesVolume.setText(fakeRestInfo.getSaleNum() + "");
				mainFood.setText("主售：" + fakeRestInfo.getSpecialty());
				storeScore.setText(fakeRestInfo.getEvaluate() + "");
				if (fakeRestInfo.getFakePhoneNumList().size() != 0) {
					phoneNumber = fakeRestInfo.getFakePhoneNumList().get(0)
							.getPhoneNum();
				}
				/*
				 * 
				 * 
				 */
				ArrayList<FakeRestFoodInfo> fakeRestFoodInfoList = fakeRestInfo
						.getFakeRestFoodInfoList();
				LinearLayout childLinearLayout;

				for (int i = 0; i < fakeRestFoodInfoList.size(); i++) {
					Li.printlnLog("0-------" + i);
					childLinearLayout = new LinearLayout(St.this);
					childLinearLayout.setLayoutParams(new LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT));
					childLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

					initChildLinearLayout(childLinearLayout,
							fakeRestFoodInfoList, i);

					if (++i < fakeRestFoodInfoList.size()) {
						Li.printlnLog("1-------" + i);
						initChildLinearLayout(childLinearLayout,
								fakeRestFoodInfoList, i);
					}
					foodContainer.addView(childLinearLayout);
					if (i + 1 < fakeRestFoodInfoList.size()) {
						ImageView imageView = new ImageView(
								St.this);
						imageView.setBackgroundResource(R.color.dividing_line);
						LayoutParams params = new LayoutParams(
								LayoutParams.MATCH_PARENT, 3);
						params.setMargins(20, 20, 20, 50);
						imageView.setLayoutParams(params);
						foodContainer.addView(imageView);
					}
				}
				rootLayout.setVisibility(ScrollView.VISIBLE);
			} else {
				// 网络错误......
			}
			mProgressDialog.dismiss();
		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.store_info);
		rootLayout = (ScrollView) findViewById(R.id.root_layout);
		rootLayout.setVisibility(ScrollView.INVISIBLE);
		callTheStore = (ImageView) findViewById(R.id.call_the_store);
		foodContainer = (LinearLayout) findViewById(R.id.single_food_container);
		storeName = (TextView) findViewById(R.id.store_name);
		mainFood = (TextView) findViewById(R.id.main_food);
		storeScore = (TextView) findViewById(R.id.store_score);
		collection = (ImageView) findViewById(R.id.collection);
		salesVolume = (TextView) findViewById(R.id.sales_volume);

		announcement = (TextView) findViewById(R.id.announcement);
		callTheStore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!phoneNumber.equals("")) {
					Intent intent = new Intent(Intent.ACTION_DIAL, Uri
							.parse("tel:" + phoneNumber));
					startActivity(intent);
				} else {
					Toast.makeText(St.this, "暂时未收录该店家的电话号码",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		storeId = getIntent().getIntExtra("storeId", 0);

		collection.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Sl.loginFlag) {
					mProgressDialog.show("加载中...");
					collectionFood();
				} else {
					Toast.makeText(St.this, "亲，只有登录后才能收藏啊！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		mProgressDialog = new MPr(St.this);
		mProgressDialog.show("加载中...");
		initStoreUI(storeId);
	}

	public void initStoreUI(final int storeId) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String storeInformationUrl = ConfigurationFiles.HTTP_STORE_INFORMATION_PATH
						+  Li.simpleEncryptParm(storeId+"");
				System.out
						.println("storeInformationUrl:" + storeInformationUrl);
				Map<String, String> map = new HashMap<String, String>();
				map.put("phoneNumber", L.loginUserPhoneNum);
				String storeInformationString = Ht
						.SendHttpClientPost(storeInformationUrl, map, "utf-8",
								St.this);
				Message storeInformationMessage = Message.obtain();
				/*
				 * @TangBo
				 */
				if (!storeInformationString
						.equals(ConfigurationFiles.HTTP_ERROR)
						&& !storeInformationString
								.equals(ConfigurationFiles.GET_INFORMATION_FAIL)) {
					if (storeInformationString
							.equals(ConfigurationFiles.OTHER_PLACE_LOGIN)) {
						storeInformationMessage.what = ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE;
						storeINformationHandler
								.sendMessage(storeInformationMessage);
					} else {
						storeInformationMessage.what = ConfigurationFiles.STORE_INFORMATION_MESSAGE;
						FakeRestInfo fakeRestInfo = Fa.getSignal(
								storeInformationString, FakeRestInfo.class);
						storeInformationMessage.obj = fakeRestInfo;
						Li.printlnLog("fakeRestInfo:"
								+ fakeRestInfo.toString());
						storeINformationHandler
								.sendMessage(storeInformationMessage);
					}
				} else {
					storeInformationMessage.what = ConfigurationFiles.NO_INTERNET;
					storeINformationHandler
							.sendMessage(storeInformationMessage);
				}
			}
		};
		new Thread(runnable).start();
	}

	private void initChildLinearLayout(LinearLayout childLinearLayout,
			ArrayList<FakeRestFoodInfo> fakeRestFoodInfoList, int i) {
		SS singleStoreFoodView = new SS(
				St.this);
		singleStoreFoodView.setLayoutWidth(getWindowManager());
		singleStoreFoodView.setFoodId(fakeRestFoodInfoList.get(i).getFoodID());
		singleStoreFoodView.setFoodName(fakeRestFoodInfoList.get(i)
				.getFoodName());
		singleStoreFoodView.setFoodPrice(fakeRestFoodInfoList.get(i).getPrice()
				+ "");
		Li.printlnLog("销售量：" + fakeRestFoodInfoList.get(i).getSaleNum());
		singleStoreFoodView.setMonthSaledNum(fakeRestFoodInfoList.get(i)
				.getSaleNum() + "");
		final int foodId = singleStoreFoodView.getFoodId();
		singleStoreFoodView.setStarsNum(fakeRestFoodInfoList.get(i)
				.getEvaluate());
		singleStoreFoodView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoFoodDetailPage(foodId);
			}
		});

		I.loadImage(ConfigurationFiles.HTTP_PICTURE_PATH,
				fakeRestFoodInfoList.get(i).getPhoto(),
				singleStoreFoodView.getFoodPicture(), 1);

		childLinearLayout.addView(singleStoreFoodView);
	}

	private void gotoFoodDetailPage(int foodId) {
		Intent intent = new Intent();
		intent.putExtra("foodId", foodId);
		intent.setClass(St.this, D.class);
		startActivity(intent);
	}

	// 收藏餐馆
	public void collectionFood() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Map<String, String> map = new HashMap<String, String>();
				map.put("phoneNumberString", L.loginUserPhoneNum);
				map.put("ID", +storeId + "");
				map.put("category", 1 + "");// category=0代表是收藏食品，category=1代表收藏店铺
				map.put("flag", isCollection + "");// flag=1为取消收藏，flag=0为添加收藏
				Li.printlnLog(map.toString());
				Li.printlnLog("收藏路径："
						+ ConfigurationFiles.HTTP_COLLECTION_FOOD_PATH);
				String result = Ht.SendHttpClientPost(
						ConfigurationFiles.HTTP_COLLECTION_FOOD_PATH, map,
						"utf-8", St.this);
				Message message = Message.obtain();
				if (result.equals(ConfigurationFiles.OTHER_PLACE_LOGIN)) {
					message.what = ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE;
				} else {
					message.obj = result;
					message.what = ConfigurationFiles.COLLECTION_MESSAGE;
				}
				storeLogoHandler.sendMessage(message);
			}
		};
		new Thread(runnable).start();
	}
}
