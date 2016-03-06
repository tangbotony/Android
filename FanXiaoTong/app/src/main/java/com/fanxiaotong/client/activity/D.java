package com.fanxiaotong.client.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fanxiaotong.client.R;
import com.fanxiaotong.client.bean.FakeComment;
import com.fanxiaotong.client.bean.FakeFood;
import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.fragment.Ca;
import com.fanxiaotong.client.fragment.Le;
import com.fanxiaotong.client.fragment.V;
import com.fanxiaotong.client.utils.Car;
import com.fanxiaotong.client.utils.Fa;
import com.fanxiaotong.client.utils.Fi;
import com.fanxiaotong.client.utils.Ht;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.widget.MPr;
import com.fanxiaotong.client.widget.SCo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class D extends Activity {
	/*
	 * food_name can_order food_score week_sale_num comment_num food_jardiniere
	 * food_flavor annoucement Button show_comment_btn more_comment_btn
	 * LinearLayout show_comment_head comment_container
	 */
	private RelativeLayout rootLayout;
	private RelativeLayout enterCartBtn;
	private ImageView storeBigImg, collect, storeLogo;
	private TextView foodName, canOrder, foodScore, weekSaleNum, commentNum,
	foodJardiniere, foodFlavor, annoucement, price, addCart;
	private Button showCommentBtn, moreCommentBtn;
	private LinearLayout showCommentHead, commentContainer;
	private MPr mProgressDialog;
	/*
	 * 其它变量声明
	 */
	private int storeId = 0;
	private FakeFood fakeFood;
	private int foodId, count = 0;
	public float foodPrice;
	private int isCollection = 0;// isCollection=0代表没有，isCollection=1代表已经收藏
	private int floor = 1;

	// 处理从网络取回来的foodId信息
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@SuppressLint("ResourceAsColor")
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			int flag = msg.what;
			if (flag == ConfigurationFiles.FOOD_INFORMATION_MESSAGE) {
				fakeFood = (FakeFood) msg.obj;
				foodPrice = fakeFood.getPrice();
				isCollection = fakeFood.getCollection();
				Li.printlnLog("isCollection:" + isCollection);
				if (isCollection == 1) {
					collect.setImageResource(R.drawable.heart_actived);
				} else {
					collect.setImageResource(R.drawable.heart_normal);
				}
				Fi.initImageBackground(fakeFood.getFoodPhoto(),
						storeBigImg, 1);
				foodName.setText(fakeFood.getFoodName());
				if (fakeFood.getSellOut() == 0) {
					canOrder.setText("已售罄");
					canOrder.setTextColor(getResources().getColor(
							R.color.text_red));
				} else {
					canOrder.setText("可订餐");
				}
				Fi.iniImageSrc(fakeFood.getRestPhoto(), storeLogo);
				foodScore.setText(fakeFood.getEvaluate() + "");
				weekSaleNum.setText(fakeFood.getSaleNum() + "");
				commentNum.setText(fakeFood.getCommentNum() + "");
				foodJardiniere.setText(fakeFood.getDishes());
				foodFlavor.setText(fakeFood.getTaste());
				annoucement.setText(fakeFood.getNotice());
				price.setText(fakeFood.getPrice() + "");
				storeId = fakeFood.getRestId();
				addCart.setEnabled(true);
				rootLayout.setVisibility(RelativeLayout.VISIBLE);
			} else if (flag == ConfigurationFiles.FOOD_COMMENT_MESSAGE) {
				int nullFlag = (int) msg.arg1;// 判断是否为空
				if (nullFlag == 1) {
					showCommentBtn.setVisibility(Button.GONE);
					showCommentHead.setVisibility(LinearLayout.VISIBLE);
					count++;
					ArrayList<FakeComment> foodCommonList = new ArrayList<FakeComment>();
					foodCommonList = (ArrayList<FakeComment>) msg.obj;
					SCo commentView;
					for (Iterator<FakeComment> iterator = foodCommonList
							.iterator(); iterator.hasNext();) {
						FakeComment fakeComment = (FakeComment) iterator.next();
						commentView = new SCo(
								D.this);
						commentView.setUserName(fakeComment.getCommentUser());
						commentView.setFloor(floor++);
						commentView.setComment(fakeComment.getCommentContent());
						commentView.setScore(fakeComment.getLevel() + "");
						commentView.setDate(Li
								.changeDateToString(fakeComment
										.getCommentDate()));
						commentView.setUserLogo(fakeComment.getIcon());
						commentContainer.addView(commentView);
					}
					moreCommentBtn.setVisibility(Button.VISIBLE);
					// 加载
				} else if (flag == ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE) {
					// 代表其它地方有登陆
					Li.exit();
					Li.getExitDialog(D.this)
					.show();
				} else {
					if (count == 0) {
						Toast.makeText(D.this, "目前暂无评论！",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(D.this, "没有跟多评论了！",
								Toast.LENGTH_SHORT).show();
					}
				}
			} else if (flag == ConfigurationFiles.COLLECTION_MESSAGE) {
				// 收藏
				String result = (String) msg.obj;
				Li.printlnLog("收藏返回result:" + result);
				if (!result.equals(ConfigurationFiles.HTTP_ERROR)) {
					if (result.equals(ConfigurationFiles.COLLECTION_SUCCEED)) {
						// Toast.makeText(DetailFoodInfoActivity.this, "收藏成功！",
						// Toast.LENGTH_SHORT).show();
						if (isCollection == 1) {
							collect.setImageResource(R.drawable.heart_normal);
							isCollection = 0;
							L.fakeMyInfo
							.setFoodNum(L.fakeMyInfo
									.getFoodNum() - 1);
							Le.iniUI();
							Toast.makeText(D.this,
									"取消收藏成功！", Toast.LENGTH_SHORT).show();
						} else {
							L.fakeMyInfo
							.setFoodNum(L.fakeMyInfo
									.getFoodNum() + 1);
							Le.iniUI();
							collect.setImageResource(R.drawable.heart_actived);
							isCollection = 1;
							Toast.makeText(D.this,
									"收藏成功！", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(D.this,
								"数据提交失败，请重新提交！", Toast.LENGTH_SHORT).show();
					}
				}
				Li.printlnLog("isCollection:" + isCollection);
			}
			mProgressDialog.dismiss();
		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.detail_food_info);
		/*
		 * Imae 部分
		 */
		rootLayout = (RelativeLayout) findViewById(R.id.root_layout);
		rootLayout.setVisibility(RelativeLayout.INVISIBLE);
		storeBigImg = (ImageView) findViewById(R.id.store_big_img);
		collect = (ImageView) findViewById(R.id.collect);
		storeLogo = (ImageView) findViewById(R.id.store_logo);
		/*
		 * TextView 部分
		 */
		foodName = (TextView) findViewById(R.id.food_name);
		canOrder = (TextView) findViewById(R.id.can_order);
		foodScore = (TextView) findViewById(R.id.food_score);
		weekSaleNum = (TextView) findViewById(R.id.week_sale_num);
		commentNum = (TextView) findViewById(R.id.comment_num);
		foodJardiniere = (TextView) findViewById(R.id.food_jardiniere);
		foodFlavor = (TextView) findViewById(R.id.food_flavor);
		annoucement = (TextView) findViewById(R.id.annoucement);
		showCommentHead = (LinearLayout) findViewById(R.id.show_comment_head);
		price = (TextView) findViewById(R.id.price);
		addCart = (TextView) findViewById(R.id.add_cart);
		showCommentBtn = (Button) findViewById(R.id.show_comment_btn);
		moreCommentBtn = (Button) findViewById(R.id.more_comment_btn);
		enterCartBtn = (RelativeLayout) findViewById(R.id.enter_cart);

		/*
		 * LinearLayout部分
		 */
		showCommentHead = (LinearLayout) findViewById(R.id.show_comment_head);
		commentContainer = (LinearLayout) findViewById(R.id.comment_container);

		/*
		 * 接收上一页传回来的foodId
		 */
		foodId = getIntent().getIntExtra("foodId", -1);
		Li
		.printlnLog("foodId-------------------------------------------------:"
				+ foodId);
		/*
		 * 从网络获取食物信息的数据
		 */
		mProgressDialog = new MPr(D.this);
		mProgressDialog.show("加载中...");
		refresh(foodId);
		/*
		 * 设置图标上的事件
		 */
		collect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Sl.loginFlag) {
					mProgressDialog.show("加载中...");
					collectionFood();
				} else {
					Toast.makeText(D.this,
							"亲，只有登录后才能收藏啊！", Toast.LENGTH_SHORT).show();
					Li.GotoLogin(D.this);
				}
			}
		});
		addCart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (canOrder.getText().equals("可订餐")) {
					if (L.loginUserPhoneNum.equals("")) {
						Toast.makeText(D.this, "请先登录！",
								Toast.LENGTH_SHORT).show();
						Li.GotoLogin(D.this);
					} else {
						getDialog(D.this).show();
					}
				} else {
					Toast.makeText(D.this,
							"该食品已售空，或店主正在休息中…", Toast.LENGTH_SHORT).show();
				}
			}
		});
		storeLogo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				gotoStoreDetailPage(storeId);
			}
		});
		// 评论的事件
		showCommentBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mProgressDialog.show("加载中...");
				getComment(foodId, count);
			}
		});
		moreCommentBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Li.printlnLog("count:" + count);
				mProgressDialog.show("加载中...");
				getComment(foodId, count);
			}
		});

		// 进入购餐车
		enterCartBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Sl.getViewPager().setCurrentItem(2);
				Intent intent = new Intent(D.this,
						Sl.class);
				startActivity(intent);
			}
		});
	}

	public void getComment(final int foodId, final int count) {
		if (foodId == -1)
			return;
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String foodCommentUrl = ConfigurationFiles.commentUrl + Li.simpleEncryptParm(foodId+"")
						+  Li.simpleEncryptParm("&count=") +  Li.simpleEncryptParm(count+"");
				Li.printlnLog("foodCommentUrl:" + foodCommentUrl);

				String foodCommentString = Ht.SendHttpClientPost(
						foodCommentUrl, null, "utf-8",
						D.this);
				System.out
				.println("foodInformationString:" + foodCommentString);
				Message message = Message.obtain();
				if (!foodCommentString.equals(ConfigurationFiles.HTTP_ERROR)) {
					if (foodCommentString
							.equals(ConfigurationFiles.OTHER_PLACE_LOGIN)) {
						message.what = ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE;
						handler.sendMessage(message);
					} else if (!foodCommentString
							.equals(ConfigurationFiles.NO_COMENT)) {
						Li.printlnLog("到这了。。。");
						message.what = ConfigurationFiles.FOOD_COMMENT_MESSAGE;
						message.obj = Fa.getList(foodCommentString,
								FakeComment.class);
						message.arg1 = 1;
						handler.sendMessage(message);
					} else {
						message.what = ConfigurationFiles.FOOD_COMMENT_MESSAGE;
						message.arg1 = 0;
						handler.sendMessage(message);
					}
				} else {
					message.what = ConfigurationFiles.NO_INTERNET;
					handler.sendMessage(message);
				}
			}
		};
		new Thread(runnable).start();
	}

	@SuppressLint("HandlerLeak")
	private Dialog getDialog(final Context context) {
		final Dialog dialog = new Dialog(context, R.style.myDialog);
		dialog.setContentView(R.layout.buy_food_dialog);

		ImageView buyFoodDialogCloseBtn = (ImageView) dialog
				.findViewById(R.id.buy_food_dialog_close_btn);
		TextView jardiniere = (TextView) dialog.findViewById(R.id.jardiniere);
		TextView flavor = (TextView) dialog.findViewById(R.id.flavor);
		final EditText remarkInput = (EditText) dialog
				.findViewById(R.id.remark_input);
		ImageView reduceFoodNumBtn = (ImageView) dialog
				.findViewById(R.id.reduce_food_num_btn);
		ImageView increaseFoodNumBtn = (ImageView) dialog
				.findViewById(R.id.increase_food_num_btn);
		final TextView foodNum = (TextView) dialog.findViewById(R.id.food_num);
		TextView submitBtn = (TextView) dialog.findViewById(R.id.submit_btn);
		final TextView foodCost = (TextView) dialog
				.findViewById(R.id.food_cost);
		TextView foodNameDialog = (TextView) dialog
				.findViewById(R.id.food_name_dialog);

		foodNameDialog.setText(foodName.getText());
		jardiniere.setText("配菜：" + foodJardiniere.getText());
		flavor.setText("口味：" + foodFlavor.getText());
		foodCost.setText(foodPrice + "");
		foodNum.setText("1");
		submitBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View view) {
				if (Sl.loginFlag) {
					int count = Integer.parseInt(foodNum.getText().toString());
					Li.printlnLog("fakeFood.getFoodID():"
							+ fakeFood.getFoodID());
					Li.printlnLog("fakeFood.getRestId():"
							+ fakeFood.getRestId());
					Li.printlnLog(" fakeFood.getFoodName():"
							+ fakeFood.getFoodName());
					Li.printlnLog("count:" + count);
					// * 修改
					// * 2014.1.10;19:10
					addOrderToCartDB(fakeFood.getFoodID(),
							fakeFood.getRestId(), fakeFood.getFoodName(),
							count, count * fakeFood.getPrice(), remarkInput
							.getText().toString(),
							L.loginUserPhoneNum, fakeFood
							.getFoodPhoto(), fakeFood.getTaste(),
							fakeFood.getDishes());
					Li.printlnLog("数据插入结束");
					Ca.setUiFlag(ConfigurationFiles.NEVER_SHOW);
					enterCartBtn.setVisibility(RelativeLayout.VISIBLE);
					dialog.dismiss();
				} else {
					Toast.makeText(D.this, "请先登陆!",
							Toast.LENGTH_SHORT).show();
					Li.GotoLogin(D.this);
				}
			}
		});
		buyFoodDialogCloseBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		reduceFoodNumBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int count = 0;// 订餐的个数
				count = Integer.parseInt(foodNum.getText().toString());
				count--;
				if (count > 0) {
					foodNum.setText(String.valueOf(count));
					foodCost.setText(String.valueOf(count * foodPrice));
				} else {
					Toast.makeText(D.this, "至少买一份哦~亲！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		increaseFoodNumBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int count = 0;// 订餐的个数
				count = Integer.parseInt(foodNum.getText().toString());
				count++;
				foodNum.setText(String.valueOf(count));
				foodCost.setText(String.valueOf(count * foodPrice));
			}
		});
		return dialog;
	}

	// 从网络拉取foodId的信息
	public void refresh(final int foodId) {
		if (foodId == -1)
			return;
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String foodInformationUrl = ConfigurationFiles.HTTP_FOOD_INFORMATION_PATH
						+  Li.simpleEncryptParm(foodId+"");
				Li.printlnLog("foodInformationUrl:" + foodInformationUrl);
				Map<String, String> map = new HashMap<String, String>();
				map.put("phoneNumber", L.loginUserPhoneNum);
				String foodInformationString = Ht
						.SendHttpClientPost(foodInformationUrl, map, "utf-8",
								D.this);
				Li.printlnLog("foodInformationString:"
						+ foodInformationString);
				Message message = Message.obtain();
				if (!foodInformationString
						.equals(ConfigurationFiles.HTTP_ERROR)
						&& !foodInformationString
						.equals(ConfigurationFiles.GET_INFORMATION_FAIL)
						&& !foodInformationString.equals("")) {
					if (foodInformationString
							.equals(ConfigurationFiles.OTHER_PLACE_LOGIN)) {
						message.what = ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE;
						handler.sendMessage(message);
					} else {
						message.what = ConfigurationFiles.FOOD_INFORMATION_MESSAGE;
						message.obj = Fa.getSignal(
								foodInformationString, FakeFood.class);
						handler.sendMessage(message);
					}
				} else {
					message.what = ConfigurationFiles.NO_INTERNET;
					handler.sendMessage(message);
				}
			}
		};
		new Thread(runnable).start();

	}

	// 跳转
	private void gotoStoreDetailPage(int storeId) {
		Intent intent = new Intent();
		intent.putExtra("storeId", storeId);
		intent.setClass(D.this, St.class);
		startActivity(intent);
	}

	public void collectionFood() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Map<String, String> map = new HashMap<String, String>();
				map.put("phoneNumberString", L.loginUserPhoneNum);
				Li.printlnLog("电话号码：" + L.loginUserPhoneNum);
				map.put("ID", foodId + "");
				Li.printlnLog("食品的Id：" + foodId);
				map.put("category", 0 + "");// category=0代表是收藏食品，category=1代表收藏店铺
				Li.printlnLog("category：" + 0);
				map.put("flag", isCollection + "");// flag=1为取消收藏，flag=0为添加收藏

				Li.printlnLog("flag：" + isCollection);
				Li.printlnLog(map.toString());
				Li.printlnLog("收藏路径："
						+ ConfigurationFiles.HTTP_COLLECTION_FOOD_PATH);
				String result = Ht.SendHttpClientPost(
						ConfigurationFiles.HTTP_COLLECTION_FOOD_PATH, map,
						"utf-8", D.this);
				Message message = Message.obtain();
				if (result.equals(ConfigurationFiles.OTHER_PLACE_LOGIN)) {
					message.what = ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE;
					handler.sendMessage(message);
				} else {
					message.obj = result;
					message.what = ConfigurationFiles.COLLECTION_MESSAGE;
					handler.sendMessage(message);
				}
			}
		};
		new Thread(runnable).start();
	}

	/**
	 * 添加菜品至购物车数据库
	 * 
	 * @date 2013.12.27 3:21
	 */
	private void addOrderToCartDB(int foodId, int storeId, String foodName,
			int count, float cost, String notice, String userPhoneNumber,
			String foodPhotoName, String flavor, String jardiniere) {
		Car cartDbHelper = new Car(
				D.this);
		Cursor orderCursor = cartDbHelper.selectByfoodID(foodId);
		Li.printlnLog("按foodID查找！！！！！！！！！！" + orderCursor.getCount());
		Li.printlnLog("Notice:  " + notice);
		Li.printlnLog("foodPhotoName:" + foodPhotoName);
		if (orderCursor.moveToFirst()) {
			int id = orderCursor.getInt(0);
			int foodIdInDb = orderCursor.getInt(1);
			int storeIdInDb = orderCursor.getInt(2);
			String foodNameInDb = orderCursor.getString(3);
			int countInDb = orderCursor.getInt(4);
			float costInDb = Float.parseFloat(orderCursor.getString(5));
			String noticeInDb = orderCursor.getString(6);
			String userPhoneNumberInDb = orderCursor.getString(7);
			String foodFlavor = orderCursor.getString(8);
			String foodJardiniere = orderCursor.getString(9);
			Li.printlnLog(id + ", " + foodIdInDb + ", " + storeIdInDb
					+ ", " + foodNameInDb + ", " + countInDb + ", " + costInDb);
			countInDb += count;
			costInDb += cost;
			cartDbHelper.update(id, foodIdInDb, storeIdInDb, foodNameInDb,
					countInDb, costInDb, noticeInDb, userPhoneNumberInDb,
					foodPhotoName, foodFlavor, foodJardiniere);
		} else {
			cartDbHelper.insert(foodId, storeId, foodName, count, cost, notice,
					userPhoneNumber, foodPhotoName, flavor, jardiniere);
		}
		cartDbHelper.close();
		V.cartBtn
		.setBackgroundResource(R.drawable.have_goods_selector);
		Toast.makeText(D.this, "新订单已提交至购物车了哦~亲！",
				Toast.LENGTH_SHORT).show();
	}
}