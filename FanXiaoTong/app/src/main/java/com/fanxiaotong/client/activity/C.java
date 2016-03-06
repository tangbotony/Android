package com.fanxiaotong.client.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fanxiaotong.client.R;
import com.fanxiaotong.client.bean.FakeComment;
import com.fanxiaotong.client.bean.FakeMyOrderInfo;
import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.utils.Fa;
import com.fanxiaotong.client.utils.Fi;
import com.fanxiaotong.client.utils.Ht;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.widget.MPr;
import com.fanxiaotong.client.widget.SCo;
import com.fanxiaotong.client.widget.SH;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class C extends Activity {
	private LinearLayout orderContainer, commentContainer;
	private TextView noFeedback;
	private LinearLayout stars;
	private ImageView score1, score2, score3, score4, score5;
	private Button send;
	private TextView backBtn;
	private FakeMyOrderInfo fakeMyOrderInfo;
	private EditText comment;
	private int starForFood = -1;
	private int floor = 0;
	private MPr mProgressDialog;

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			int flag = msg.what;
			String result = "";
			switch (flag) {
			case ConfigurationFiles.NO_INTERNET:
				Toast.makeText(C.this, "网络出错，请从新提交！",
						Toast.LENGTH_SHORT).show();
				break;
			case ConfigurationFiles.NULL_MESSAGE:
				noFeedback.setVisibility(TextView.VISIBLE);
				commentContainer.setVisibility(LinearLayout.GONE);
				break;
			case ConfigurationFiles.GET_FOOD_COMMENT:
				result = (String) msg.obj;
				ArrayList<FakeComment> comments = (ArrayList<FakeComment>) Fa
						.getList(result, FakeComment.class);
				refreshView(comments);
				
				break;

			case ConfigurationFiles.SEND_FOOD_COMMENT_MESSAGE:
				result = (String) msg.obj;
				if (result.equals(ConfigurationFiles.SUGGEST_SUCCEED)) {
					Toast.makeText(C.this, "评论成功！",
							Toast.LENGTH_SHORT).show();
					comment.setText("");
					starForFood = -1;
					refreshComment();
				} else {
					Toast.makeText(C.this, "网络出错，请从新提交！",
							Toast.LENGTH_SHORT).show();
				}
				break;

			default:
				break;
			}
			
			mProgressDialog.dismiss();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment);
		// 接收数据HIstory传送过来的数据
		fakeMyOrderInfo = (FakeMyOrderInfo) getIntent().getSerializableExtra(
				"foodInfor");
		initView();
		initEvent();

		refreshComment();
		iniUI();
	}

	private void initView() {
		backBtn = (TextView) findViewById(R.id.back_btn);
		orderContainer = (LinearLayout) findViewById(R.id.order_container);
		commentContainer = (LinearLayout) findViewById(R.id.comment_container);
		noFeedback = (TextView) findViewById(R.id.no_feedback);
		comment = (EditText) findViewById(R.id.coment);
		stars = (LinearLayout) findViewById(R.id.stars);
		score1 = (ImageView) findViewById(R.id.score_1);
		score2 = (ImageView) findViewById(R.id.score_2);
		score3 = (ImageView) findViewById(R.id.score_3);
		score4 = (ImageView) findViewById(R.id.score_4);
		score5 = (ImageView) findViewById(R.id.score_5);
		send = (Button) findViewById(R.id.send);
		backBtn = (TextView) findViewById(R.id.back_btn);
		mProgressDialog = new MPr(C.this);

	}

	public void iniUI() {

		if (fakeMyOrderInfo == null) {
			Li.printlnLog("fakeMyOrderInfo:" + fakeMyOrderInfo);
			return;
		}

		stars.setVisibility(LinearLayout.GONE);
		SH singleHistoryOrderView = new SH(
				C.this);

		singleHistoryOrderView.setFoodName(fakeMyOrderInfo.getFoodName());
		singleHistoryOrderView.setFoodCount(fakeMyOrderInfo.getAmount() + "");
		singleHistoryOrderView.setFoodCost(fakeMyOrderInfo.getPrice() + "");
		singleHistoryOrderView.setRemark("备注："
				+ (fakeMyOrderInfo.getNotice().equals("") ? "无"
						: fakeMyOrderInfo.getNotice()));
		Fi.initImageBackground(fakeMyOrderInfo.getPhoto(),
				singleHistoryOrderView.getFoodLogo(), 2);
		singleHistoryOrderView.getState().setVisibility(TextView.GONE);
		singleHistoryOrderView.findViewById(R.id.dot_line).setVisibility(
				ImageView.GONE);
		singleHistoryOrderView.findViewById(R.id.dish_layout).setEnabled(false);
		singleHistoryOrderView.setEnabled(false);
		singleHistoryOrderView.setClickable(false);
		orderContainer.addView(singleHistoryOrderView);
	}

	private void initEvent() {
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		comment.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					stars.setVisibility(LinearLayout.VISIBLE);
				}else{
					stars.setVisibility(LinearLayout.GONE);
				}
			}
		});
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (comment.getText().toString().equals("")) {
					Toast.makeText(C.this, "评论内容不能为空！",
							Toast.LENGTH_SHORT).show();
				} else if (starForFood == -1) {
					Toast.makeText(C.this, "请为该菜品评分！",
							Toast.LENGTH_SHORT).show();
				} else {
					sendComment();
				}
				comment.clearFocus();
			}
		});

		score1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setStarBG();
				starForFood = 1;
				score1.setImageResource(R.drawable.blue_circle_shape__);

			}
		});
		score2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setStarBG();
				starForFood = 2;
				score2.setImageResource(R.drawable.blue_circle_shape__);

			}
		});
		score3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setStarBG();
				starForFood = 3;
				score3.setImageResource(R.drawable.blue_circle_shape__);
			}
		});
		score4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setStarBG();
				starForFood = 4;
				score4.setImageResource(R.drawable.blue_circle_shape__);
			}
		});
		score5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setStarBG();
				starForFood = 5;
				score5.setImageResource(R.drawable.blue_circle_shape__);
			}
		});

	}

	private void refreshView(ArrayList<FakeComment> comments) {
		commentContainer.removeAllViews();
		floor = 0;
		if (comments.size() > 0) {
			noFeedback.setVisibility(TextView.GONE);
			commentContainer.setVisibility(LinearLayout.VISIBLE);
			Li.printlnLog("comments:" + comments.size());
			for (int i = 0; i < comments.size() - 1; i++) {
				FakeComment fakeComment = (FakeComment) comments.get(i);
				commentContainer.addView(makeCommentView(fakeComment));
				commentContainer.addView(makeDividingLine());
			}
			commentContainer.addView(makeCommentView((FakeComment) comments
					.get(comments.size() - 1)));

		}
	}

	private SCo makeCommentView(FakeComment comment) {

		SCo commentView = new SCo(
				C.this);
		commentView.findViewById(R.id.dot_line).setVisibility(ImageView.GONE);
		commentView.setUserName(comment.getCommentUser());
		commentView.setFloor(++floor);
		commentView.setComment(comment.getCommentContent());
		commentView.setScore(comment.getLevel() + "");
		commentView.setDate(Li.changeDateToString(comment
				.getCommentDate()));
		commentView.setUserLogo(comment.getIcon());

		return commentView;

	}

	public void refreshComment() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {

				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mProgressDialog.show();
					}
				});

				String commentURL = ConfigurationFiles.GET_FOOD_COMMENT_BY_USER;

				Map<String, String> map = new HashMap<String, String>();
				map.put("foodID", fakeMyOrderInfo.getFoodID() + "");
				map.put("phoneNumber", L.loginUserPhoneNum);

				Li.printlnLog("commentURL:" + commentURL);
				String commentString = Ht.SendHttpClientPost(
						commentURL, map, "utf-8", C.this);
				Li.printlnLog("commentString:" + commentString);
				Message message = Message.obtain();
				if (commentString.equals(ConfigurationFiles.HTTP_ERROR)) {
					message.what = ConfigurationFiles.NO_INTERNET;
				} else if (commentString
						.equals(ConfigurationFiles.GET_SUGGESTION_FAIL)
						|| commentString.equals("")
						|| commentString.equals("[]")) {
					message.what = ConfigurationFiles.NULL_MESSAGE;
				} else {
					message.what = ConfigurationFiles.GET_FOOD_COMMENT;
					message.obj = commentString;
				}
				handler.sendMessage(message);

			}
		};
		new Thread(runnable).start();
	}

	public void sendComment() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String sendCommentURL = ConfigurationFiles.SEND_FOOD_COMMENT;
				// phoneNumber=13190016190&orderDetailsID=2&remarkContent=不好吃啊&foodLevel=4
				Map<String, String> map = new HashMap<String, String>();
				map.put("phoneNumber", L.loginUserPhoneNum);
				map.put("remarkContent", comment.getText().toString());
				map.put("orderDetailsID", fakeMyOrderInfo.getOrderID() + "");
				map.put("foodLevel", starForFood + "");
				Li.printlnLog("map:" + map.toString());
				String commentStringResult = Ht.SendHttpClientPost(
						sendCommentURL, map, "utf-8", C.this);
				Message message = Message.obtain();
				if (commentStringResult
						.equals(ConfigurationFiles.SUGGEST_SUCCEED)) {
					message.what = ConfigurationFiles.SEND_FOOD_COMMENT_MESSAGE;
					message.obj = commentStringResult;
				} else {
					message.what = ConfigurationFiles.HTTP_ERROR_MESSAGE;
				}
				handler.sendMessage(message);
			}
		};
		new Thread(runnable).start();
	}

	public void setStarBG() {
		score1.setImageResource(android.R.color.transparent);
		score2.setImageResource(android.R.color.transparent);
		score3.setImageResource(android.R.color.transparent);
		score4.setImageResource(android.R.color.transparent);
		score5.setImageResource(android.R.color.transparent);
	}

	private ImageView makeDividingLine() {
		ImageView img = new ImageView(C.this);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				Li.dip2px(C.this, 1));
		img.setLayoutParams(params);
		img.setBackgroundColor(getResources().getColor(R.color.light_gray_));

		return img;
	}

}
