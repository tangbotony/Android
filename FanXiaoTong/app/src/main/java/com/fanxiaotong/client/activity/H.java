package com.fanxiaotong.client.activity;

import java.util.Iterator;
import java.util.List;

import com.fanxiaotong.client.bean.FakeMyOrderForm;
import com.fanxiaotong.client.bean.FakeMyOrderInfo;
import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.utils.Fa;
import com.fanxiaotong.client.utils.Fi;
import com.fanxiaotong.client.utils.Ht;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.widget.MPr;
import com.fanxiaotong.client.widget.SH;
import com.fanxiaotong.client.widget.SH.OnCommentBtnClickListener;
import com.fanxiaotong.client.widget.SH.OnDishBtnClickListener;
import com.fanxiaotong.client.widget.SO;
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

@SuppressLint("HandlerLeak")
public class H extends Activity {
	private MPr mProgressDialog;
	private LinearLayout foodLinearLayout;
	private TextView backBtn;
	private Intent intent;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			int flag = msg.what;
			if (flag == ConfigurationFiles.NO_INTERNET) {
				Toast.makeText(H.this, "网络发生错误，请检查网络！",
						Toast.LENGTH_SHORT).show();
			} else if (flag == ConfigurationFiles.NULL) {
				Toast.makeText(H.this, "服务器数据返回为空！",
						Toast.LENGTH_SHORT).show();
			} else if (flag == ConfigurationFiles.HISTORY_INFORMATION_MESSAGE) {
				@SuppressWarnings("unchecked")
				List<FakeMyOrderForm> list = (List<FakeMyOrderForm>) msg.obj;
				refreshView(list);
			} else if (flag == ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE) {
				Li.exit();
				Li.getExitDialog(H.this).show();
			} else if (flag == ConfigurationFiles.NULL_MESSAGE) {
				// 处理为空的情况
			}
			mProgressDialog.dismiss();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.history);
		foodLinearLayout = (LinearLayout) findViewById(R.id.food_linearLayout);
		backBtn = (TextView) findViewById(R.id.back_btn);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mProgressDialog = new MPr(H.this);

		refresh();
	}

	public void refresh() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {

				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mProgressDialog.show("加载中...");
					}
				});
				
				String historyStringUrl = ConfigurationFiles.HISTORYURL
						+  Li.simpleEncryptParm(L.loginUserPhoneNum);
				Li.printlnLog("historyStringUrl:" + historyStringUrl);
				String historyString = Ht.SendHttpClientPost(
						historyStringUrl, null, "utf-8", H.this);
				Li.printlnLog("historyString:" + historyString);
				Message message = Message.obtain();
				if (!historyString.equals("") && !historyString.equals("[]")) {
					if (!historyString.equals(ConfigurationFiles.HTTP_ERROR)) {
						if (historyString
								.equals(ConfigurationFiles.OTHER_PLACE_LOGIN)) {
							message.what = ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE;
							handler.sendMessage(message);
						} else if (historyString
								.equals(ConfigurationFiles.GET_INFORMATION_FAIL)) {
							message.what = ConfigurationFiles.NULL;
							handler.sendMessage(message);
						} else {
							message.what = ConfigurationFiles.HISTORY_INFORMATION_MESSAGE;
							List<FakeMyOrderForm> list = Fa.getList(
									historyString, FakeMyOrderForm.class);
							message.obj = list;
							Li.printlnLog("list:" + list);
							handler.sendMessage(message);
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
	
	private void refreshView(List<FakeMyOrderForm> list){
		SO headView;
		SH singleHistoryOrderView;
		List<FakeMyOrderInfo> orderInfoList;
		foodLinearLayout.removeAllViews();
		for (Iterator<FakeMyOrderForm> iterator = list.iterator(); iterator
				.hasNext();) {
			FakeMyOrderForm fakeMyOrderForm = (FakeMyOrderForm) iterator
					.next();
			headView = new SO(H.this);
			headView.setAddress(fakeMyOrderForm.getAddress());
			headView.setRemark(fakeMyOrderForm.getNotice());
			headView.setOrderTime(Li
					.changeDateToString(fakeMyOrderForm
							.getOrdered_date()));
			foodLinearLayout.addView(headView);
			// 添加子订单视图
			orderInfoList = fakeMyOrderForm.getOrderInfoList();

			for (Iterator<FakeMyOrderInfo> iterator2 = orderInfoList
					.iterator(); iterator2.hasNext();) {
				final FakeMyOrderInfo fakeMyOrderInfo = (FakeMyOrderInfo) iterator2
						.next();
				singleHistoryOrderView = new SH(
						H.this);
				singleHistoryOrderView.setFoodName(fakeMyOrderInfo
						.getFoodName());
				singleHistoryOrderView.setFoodCount(fakeMyOrderInfo
						.getAmount() + "");
				singleHistoryOrderView.setFoodCost(fakeMyOrderInfo
						.getPrice() + "");
				singleHistoryOrderView
						.setOnDishBtnClickListener(new OnDishBtnClickListener() {
							@Override
							public void onClick() {
								gotoFoodDetailPage(fakeMyOrderInfo
										.getFoodID());
							}
						});

				singleHistoryOrderView
						.SetOnCommentBtnClickListener(new OnCommentBtnClickListener() {

							@Override
							public void onClick() {
								intent = new Intent(
										H.this,
										C.class);
								intent.putExtra("foodInfor",
										fakeMyOrderInfo);
								startActivity(intent);
							}
						});
				if (fakeMyOrderInfo.getIsRemarkedByUser() == 1) {
					// singleHistoryOrderView.getState()
					singleHistoryOrderView.getState()
							.setBackgroundResource(
									R.drawable.blue_round_shape___);
					singleHistoryOrderView.setState("已评论");
				}
				// singleHistoryOrderView.setState(OrderListPageFragment.getState(state));
				singleHistoryOrderView.setRemark("备注："
						+ (fakeMyOrderInfo.getNotice().equals("") ? "无"
								: fakeMyOrderInfo.getNotice()));
				Fi.initImageBackground(
						fakeMyOrderInfo.getPhoto(),
						singleHistoryOrderView.getFoodLogo(), 2);

				foodLinearLayout.addView(singleHistoryOrderView);
			}
		}
	}

	private void gotoFoodDetailPage(int foodId) {
		Intent intent = new Intent();
		intent.putExtra("foodId", foodId);
		intent.setClass(H.this, D.class);
		startActivity(intent);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		refresh();
		super.onStart();
	}

}