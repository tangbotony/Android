package com.fanxiaotong.client.activity;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fanxiaotong.client.bean.FakeUserAdvice;
import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.utils.Fa;
import com.fanxiaotong.client.utils.Ht;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.widget.MPr;
import com.fanxiaotong.client.widget.SF;
import com.fanxiaotong.client.R;




import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Sug extends Activity {
	private TextView backBtn;
	private TextView sendBtn;
	private EditText feedbackInput;
	private LinearLayout feedbackContainer;
	private TextView noFeedback;
	private EditText email;
	private MPr mProgressDialog;

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			int flag = msg.what;

			switch (flag) {
			case ConfigurationFiles.MESSAGE_GET_SUGGESTION:
				String result = (String) msg.obj;
				if (result != null && !result.equals("")) {
					if (!result.equals(ConfigurationFiles.GET_SUGGESTION_FAIL)) {
						List<FakeUserAdvice> suggestions = (List<FakeUserAdvice>) Fa.getList(result, FakeUserAdvice.class);
						refreshView(suggestions);
					} else {
					}
				} else {
					Toast.makeText(Sug.this, "数据为空",
							Toast.LENGTH_SHORT).show();
				}
				break;

			case ConfigurationFiles.MESSAGE_SEND_SUGGESTION:
				String result_ = (String) msg.obj;
				if (result_ != null&& result_.equals(ConfigurationFiles.SUGGEST_SUCCEED)) {
					Toast.makeText(Sug.this, "反馈成功！",
							Toast.LENGTH_SHORT).show();
					feedbackInput.setText("");
					if(!L.loginUserPhoneNum.equals(""))
					{
						refreshData();
					}
				} else {
					Toast.makeText(Sug.this, "反馈失败！",
							Toast.LENGTH_SHORT).show();
				}

				break;
			case ConfigurationFiles.HTTP_ERROR_MESSAGE:
				Toast.makeText(Sug.this, "网络连接错误！",
						Toast.LENGTH_LONG).show();
				break;
			case ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE:
				Li.exit();
				Li.getExitDialog(Sug.this).show();
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
		setContentView(R.layout.suggestion);

		initView();
		initEvent();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(Sl.loginFlag)
		{
			refreshData();
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initView() {
		backBtn = (TextView) findViewById(R.id.back_btn);
		sendBtn = (TextView) findViewById(R.id.send_btn);
		feedbackInput = (EditText) findViewById(R.id.feedback_input);
		feedbackContainer = (LinearLayout) findViewById(R.id.feedback_container);
		noFeedback = (TextView) findViewById(R.id.no_feedback);
		email = (EditText)findViewById(R.id.email);

		mProgressDialog = new MPr(Sug.this);

		feedbackContainer.setVisibility(LinearLayout.GONE);
	}

	private void initEvent() {
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		sendBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (feedbackInput.getText().toString().equals("")) {
					Toast.makeText(Sug.this, "亲，还是先填下内容吧！",
							Toast.LENGTH_SHORT).show();
				} else {
					if(email.getText().toString().equals(""))
					{
						feedback();
					}else
					{
						if(Li.checkEmail(email.getText().toString()))
						{
							feedback();
						}else
						{
							Toast.makeText(Sug.this, "邮箱格式不正确！",Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		});
	}
	private ImageView makeDividingLine() {
		ImageView img = new ImageView(Sug.this);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				Li.dip2px(Sug.this, 1));
		img.setLayoutParams(params);
		img.setBackgroundColor(getResources().getColor(R.color.light_gray_));

		return img;
	}

	private void feedback() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String feedback = feedbackInput.getText().toString();
				String emailString = email.getText().toString();
				String url = ConfigurationFiles.HTTP_SEND_ADVICE_PATH;
				Map<String, String> map = new HashMap<String, String>();
				map.put("advice", feedback);
				map.put("email",emailString);
				map.put("phoneNumber", L.loginUserPhoneNum);

				String result = Ht.SendHttpClientPost(url, map,"utf-8",Sug.this);

				Message message = Message.obtain();
				if (result != null && !result.equals(ConfigurationFiles.HTTP_ERROR)) {
					if(result.equals(ConfigurationFiles.OTHER_PLACE_LOGIN))
					{
						message.what = ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE;
					}else
					{
						message.what = ConfigurationFiles.MESSAGE_SEND_SUGGESTION;
						message.obj = result;
					}

					handler.sendMessage(message);
				} else {
					message.what = ConfigurationFiles.HTTP_ERROR_MESSAGE;
					message.obj = "";
					handler.sendMessage(message);
				}
			}
		};

		new Thread(runnable).start();
	}

	private void refreshData() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Li.printlnLog("获取曾发过的意见！");
				handler.post(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mProgressDialog.show("刷新中");
					}
				});
				String url = ConfigurationFiles.HTTP_GET_SUGGESTION_PATH+  Li.simpleEncryptParm(L.loginUserPhoneNum);
				Li.printlnLog("路径：" + url);

				String result = Ht.SendHttpClientPost(url, null,
						"utf-8",Sug.this);
				Li.printlnLog("result:" + result);

				Message message = Message.obtain();
				if (result != null && !result.equals(ConfigurationFiles.HTTP_ERROR)) {
					if(result.equals(ConfigurationFiles.OTHER_PLACE_LOGIN))
					{
						message.what = ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE;
					}else
					{
						message.what = ConfigurationFiles.MESSAGE_GET_SUGGESTION;
						message.obj = result;
					}
					handler.sendMessage(message);
				} else {
					Li.printlnLog("网络错误！！！！！！！！！！！！！！！！！");
					message.what = ConfigurationFiles.HTTP_ERROR_MESSAGE;
					message.obj = "";
					handler.sendMessage(message);
				}
			}
		};

		new Thread(runnable).start();
	}

	private void refreshView(List<FakeUserAdvice> suggestions) {

		Li.printlnLog("刷新页面！");

		feedbackContainer.removeAllViews();

		if (suggestions.size() > 0) {
			feedbackContainer.setVisibility(LinearLayout.VISIBLE);
			noFeedback.setVisibility(LinearLayout.GONE);
		} else {
			feedbackContainer.setVisibility(LinearLayout.GONE);
			noFeedback.setVisibility(LinearLayout.VISIBLE);
		}
		SF singleFeedbackView;
		FakeUserAdvice suggestion;
		for (int i = 0; i < suggestions.size(); i++) {
			suggestion = (FakeUserAdvice) suggestions.get(i);
			singleFeedbackView = new SF(Sug.this);
			singleFeedbackView.setFeedback(suggestion.getContent());
			singleFeedbackView.setDate(suggestion.getDate());
			Li.printlnLog("----date:" + suggestion.getDate());
			Li.printlnLog(singleFeedbackView.toString());
			feedbackContainer.addView(singleFeedbackView);
			if(i!=suggestions.size()-1)
			{
				feedbackContainer.addView(makeDividingLine());
			}
		}

	}
}
