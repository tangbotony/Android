package com.fanxiaotong.client.activity;

import java.util.ArrayList;
import java.util.Iterator;

import com.fanxiaotong.client.bean.FakeCategoryFood;
import com.fanxiaotong.client.bean.FakeCollection;
import com.fanxiaotong.client.bean.FakeRestaurant;
import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.utils.Fa;
import com.fanxiaotong.client.utils.Fi;
import com.fanxiaotong.client.utils.Ht;
import com.fanxiaotong.client.utils.I;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.widget.MPr;
import com.fanxiaotong.client.widget.Se;
import com.fanxiaotong.client.widget.SS;
import com.fanxiaotong.client.widget.SSt;
import com.fanxiaotong.client.widget.SSt.OnStoreClickListener;
import com.fanxiaotong.client.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;

@SuppressLint("HandlerLeak")
public class M extends Activity {
	private TextView backBtn;
	private Se segmentedRadioGroup;
	private MPr mProgressDialog;
	private RadioButton buttonLeft, buttonRight;
	private LinearLayout linearLayout;
	public static int flagBit = 0;// flag=0代表显示收藏的菜品,flag=1代表显示收藏的菜馆
	private ArrayList<FakeCategoryFood> foodList;
	private ArrayList<FakeRestaurant> restaurantList;
	FakeRestaurant fakeRestaurant;
	SSt storeView;
	FakeCollection fakeCollection;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			int flag = msg.what;
			if (flag == ConfigurationFiles.NO_INTERNET) {
				Toast.makeText(M.this, "网络发生错误，请检查网络！",
						Toast.LENGTH_SHORT).show();
			} else if (flag == ConfigurationFiles.NULL) {
				Toast.makeText(M.this, "服务器数据返回为空！",
						Toast.LENGTH_SHORT).show();
			} else if (flag == ConfigurationFiles.MYCOLLECTION_INFORMATION_MESSAGE) {
				fakeCollection = (FakeCollection) msg.obj;
				foodList = fakeCollection.getFoodList();
				restaurantList = fakeCollection.getRestaurantList();
				linearLayout.removeAllViews();
				if (flagBit == 0) {
					buttonLeft.setClickable(true);
					showCollectionFood();
				} else {
					buttonRight.setChecked(true);
					showCollectionStore();
				}
			}else if(flag == ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE)
			{
				Li.printlnLog("***************************");
				Li.printlnLog("***************************");
				Li.exit();
				Li.getExitDialog(M.this).show();
			}else if(flag == ConfigurationFiles.NULL)
			{
				//处理为空的情况
			}
			mProgressDialog.dismiss();

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.my_collection);
		buttonLeft = (RadioButton) findViewById(R.id.button_left);
		buttonRight = (RadioButton) findViewById(R.id.button_right);
		backBtn = (TextView) findViewById(R.id.back_btn);
		segmentedRadioGroup = (Se) findViewById(R.id.segment_text);
		linearLayout = (LinearLayout) findViewById(R.id.collection_container);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		segmentedRadioGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						switch (checkedId) {
						case R.id.button_left:
							flagBit = 0;
							showCollectionFood();
							break;
						case R.id.button_right:
							flagBit = 1;
							showCollectionStore();
							break;
						default:
							break;
						}
					}
				});
		mProgressDialog = new MPr(M.this);
		mProgressDialog.show("加载中...");
		refresh();
	
	}

	public void refresh() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String myCollectionStringUrl = ConfigurationFiles.MYCOLLECTION_INFORMATION_URL+  Li.simpleEncryptParm(L.loginUserPhoneNum);
				Li.printlnLog("historyStringUrl:" + myCollectionStringUrl);
				String myCollectionString = Ht.SendHttpClientPost(myCollectionStringUrl, null, "utf-8",M.this);
				Li.printlnLog("historyString:" + myCollectionString);
				Message message = Message.obtain();
				if (!myCollectionString.equals("") && !myCollectionString.equals("[]")) {
					if (!myCollectionString.equals(ConfigurationFiles.HTTP_ERROR)) {
						if(myCollectionString.equals(ConfigurationFiles.OTHER_PLACE_LOGIN))
						{
							message.what = ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE;
							handler.sendMessage(message);
						}else//
						{
							//判断是否为空
							if(myCollectionString.equals(ConfigurationFiles.GET_INFORMATION_FAIL))
							{
								//为空
								message.what = ConfigurationFiles.NULL;
								handler.sendMessage(message);
							}else
							{
								message.what = ConfigurationFiles.MYCOLLECTION_INFORMATION_MESSAGE;
								FakeCollection fakeCollection = Fa.getSignal(myCollectionString, FakeCollection.class);
								message.obj = fakeCollection;
								Li.printlnLog("fakeCollection:"+ fakeCollection.toString());
								handler.sendMessage(message);
							}
						}
						
					} else {
						message.what = ConfigurationFiles.NO_INTERNET;
						handler.sendMessage(message);
					}
				} else {
					message.what = ConfigurationFiles.NULL;
					handler.sendMessage(message);
				}
			}
		};
		new Thread(runnable).start();
	}

	private void initChildLinearLayout(LinearLayout childLinearLayout,
			ArrayList<FakeCategoryFood> foodList, int i) {
		SS singleStoreFoodView = new SS(
				M.this);
		singleStoreFoodView.setLayoutWidth(getWindowManager());
		singleStoreFoodView.setFoodId(foodList.get(i).getFoodID());
		singleStoreFoodView.setFoodName(foodList.get(i).getFoodName());
		singleStoreFoodView.setFoodPrice(foodList.get(i).getPrice() + "");
		Li.printlnLog("销售量：" + foodList.get(i).getSaleNum());
		singleStoreFoodView.setMonthSaledNum(foodList.get(i).getSaleNum() + "");
		final int foodId = singleStoreFoodView.getFoodId();
		singleStoreFoodView.setStarsNum(foodList.get(i).getEvaluate());
		singleStoreFoodView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoFoodDetailPage(foodId);
			}
		});
		I.loadImage(ConfigurationFiles.HTTP_PICTURE_PATH, foodList
				.get(i).getPhoto(), singleStoreFoodView.getFoodPicture(), 1);
		childLinearLayout.addView(singleStoreFoodView);
	}

	private void gotoFoodDetailPage(int foodId) {
		Intent intent = new Intent();
		intent.putExtra("foodId", foodId);
		intent.setClass(M.this, D.class);
		startActivity(intent);
	}

	private void setSingleStoreOnClickListener(LinearLayout elasticScrollView) {
		for (int i = 0; i < elasticScrollView.getChildCount(); i++) {
			SSt singleStoreView = (SSt) elasticScrollView
					.getChildAt(i);
			final int storeId = singleStoreView.getStoreId();
			singleStoreView.setOnStoreClickListener(new OnStoreClickListener() {
				@Override
				public void onClick() {
					gotoStoreDetailPage(storeId);
				}
			});
		}
	}
	private void gotoStoreDetailPage(int storeId) {
		Intent intent = new Intent();
		intent.putExtra("storeId", storeId);
		intent.setClass(M.this, St.class);
		startActivity(intent);
	}

	public void showCollectionFood() {
		linearLayout.removeAllViews();
		LinearLayout childLinearLayout;
		for (int i = 0; i < foodList.size(); i++) {
			Li.printlnLog("运行到这了。。。。");
			childLinearLayout = new LinearLayout(M.this);
			childLinearLayout.setLayoutParams(new LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT));
			childLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

			initChildLinearLayout(childLinearLayout, foodList, i);

			if (++i < foodList.size()) {
				initChildLinearLayout(childLinearLayout, foodList, i);
			}
			linearLayout.addView(childLinearLayout);
			if (i + 1 < foodList.size()) {
				ImageView imageView = new ImageView(M.this);
				imageView.setBackgroundResource(R.color.dividing_line);
				LayoutParams params = new LayoutParams(
						LayoutParams.MATCH_PARENT, 3);
				params.setMargins(20, 20, 20, 50);
				imageView.setLayoutParams(params);
				linearLayout.addView(imageView);
			}
		}
	}

	public void showCollectionStore() {
		linearLayout.removeAllViews();
		Li.printlnLog("restaurantList:"+restaurantList.toString());
		for (Iterator<FakeRestaurant> iterator = restaurantList.iterator(); iterator
				.hasNext();) {
			fakeRestaurant = (FakeRestaurant) iterator.next();
			storeView = new SSt(M.this);
			storeView.setStoreId(fakeRestaurant.getId());
			storeView.setStoreName(fakeRestaurant.getRestaurantName());
			storeView.setMainFood("主售：" + fakeRestaurant.getSpecialty());
			storeView.setSalesVolume(fakeRestaurant.getSaleNum());
			storeView.setStoreScore(fakeRestaurant.getEvaluate());
			Li.printlnLog("FakeRestaurant.getPhoto():"
					+ fakeRestaurant.getPhoto());
			Fi.initImageBackground(fakeRestaurant.getPhoto(),
					storeView.getStoreLogo(), 1);
			linearLayout.addView(storeView);
		}
		setSingleStoreOnClickListener(linearLayout);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		refresh();
	}
}