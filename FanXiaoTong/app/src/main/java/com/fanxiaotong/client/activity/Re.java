package com.fanxiaotong.client.activity;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.utils.Ht;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.widget.MPr;
import com.fanxiaotong.client.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Re extends Activity {
	private Button registerBtn;
	private EditText phoneNumberFiled;
	private EditText passwordFirst;
	private EditText passwordSecond;
	private EditText verificationFiled;
	private TextView verificationLabel;
	private String passwordFirstString;
	private String passwordSecondString;
	private String phoneNumberString;
	private String verificationString;
	private String realVerificationString;
	private EditText nickName;
	private TextView headImage;
	private TextView backBtn;
	private CheckBox agreeTheRule;
	private Timer timer;
	private TextView rule;
	private static int restTime = 60;
	private static boolean isLegalPhoneNumber = false;
	private static String illegalPhoneNum="";
	private MPr mProgressDialog;
	private LinearLayout headLogoCantianer;
	private ImageView headLogo;
	private static int switchFlag=0;
	private ImageView head1,head2,head3,head4,head5,head6;

	@SuppressLint("HandlerLeak")
	Handler registerHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			int flag = msg.what;
			String result = (String)msg.obj;

			switch (flag) {
			case ConfigurationFiles.REGISTER_MESSAGE:
				if(result.equals(ConfigurationFiles.REGISTER_SUCCEED)){
					Toast.makeText(Re.this, "注册成功，请返回登陆！", Toast.LENGTH_SHORT).show();
					finish();
				}else{
					Toast.makeText(Re.this, "注册失败，请从新注册！", Toast.LENGTH_SHORT).show();
				}
				break;
			case ConfigurationFiles.CHECK_PHONENUM_MESSAGE:
				Li.printlnLog("CHECK_PHONENUM_MESSAGE--result:" + result);

				if(result.equals(ConfigurationFiles.LEGAL_PHONENUMER)){
					isLegalPhoneNumber = true;
					//LittleUtil.printlnLog("Come ON!");
					Toast.makeText(Re.this, "号码可用！", Toast.LENGTH_SHORT).show();
					illegalPhoneNum = "";
				}else{
					isLegalPhoneNumber = false;
					Toast.makeText(Re.this, "该号码已存在，请从新填写！", Toast.LENGTH_SHORT).show();
					illegalPhoneNum = phoneNumberFiled.getText().toString();

				}
				break;


			case ConfigurationFiles.CHECK_VERIFICATION_MESSAGE:

				Li.printlnLog("CHECK_VERIFICATION_MESSAGE--result:" + result);

				if(result.endsWith(ConfigurationFiles.CANNOT_GET_VERIFICATION)){
					Toast.makeText(Re.this, "暂时无法获取验证码T.T", Toast.LENGTH_SHORT).show();
				}else if(result.endsWith(ConfigurationFiles.INCORRECT_PHONE_NUMBER)){
					Toast.makeText(Re.this, "你妹的！乱输个号码坑我呢！？o(╯□╰)o", Toast.LENGTH_SHORT).show();
				}else if(result.endsWith(ConfigurationFiles.NULL_PHONE_NUMBER)){
					Toast.makeText(Re.this, "小样，不填号码让我验证码发给谁啊？。", Toast.LENGTH_SHORT).show();
				}else{
					realVerificationString = result;
					Toast.makeText(Re.this, "正在发送验证码，请稍等~", Toast.LENGTH_SHORT).show();
					verificationFiled.setEnabled(true);
					setFetchVericationLabelClickable(false);
				}

				break;

			case ConfigurationFiles.NO_INTERNET:
				Toast.makeText(Re.this, "网络错误！", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
			mProgressDialog.dismiss();
		}

	};

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.register);
		mProgressDialog = new MPr(Re.this);
		registerBtn = (Button)findViewById(R.id.register_btn);
		phoneNumberFiled = (EditText)findViewById(R.id.phone_number_filed);
		passwordFirst = (EditText)findViewById(R.id.password_first);
		passwordSecond = (EditText)findViewById(R.id.password_second);
		verificationFiled = (EditText)findViewById(R.id.verification_filed);
		verificationLabel = (TextView)findViewById(R.id.verification_label);
		nickName = (EditText)findViewById(R.id.nick_name);
		backBtn = (TextView)findViewById(R.id.back_btn);
		agreeTheRule = (CheckBox)findViewById(R.id.agree_the_rule);
		rule = (TextView) findViewById(R.id.rule);
		head1 = (ImageView)findViewById(R.id.head_1);
		head2 = (ImageView)findViewById(R.id.head_2);
		head3 = (ImageView)findViewById(R.id.head_3);
		head4 = (ImageView)findViewById(R.id.head_4);
		head5 = (ImageView)findViewById(R.id.head_5);
		head6 = (ImageView)findViewById(R.id.head_6);
		headImage = (TextView)findViewById(R.id.head_image);
		head1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				headImage.setText("b_1_round");
			}
		});
		head2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				headImage.setText("b_2_round");
			}
		});

		head3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				headImage.setText("b_3_round");
			}
		});

		head4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				headImage.setText("g_1_round");
			}
		});

		head5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				headImage.setText("g_2_round");
			}
		});
		head6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				headImage.setText("g_3_round");
			}
		});
		passwordFirstString = "";
		passwordSecondString = "";
		phoneNumberString = "";
		verificationString = "";
		realVerificationString = "";
		headLogoCantianer = (LinearLayout)findViewById(R.id.head_logo_cantianer);
		headLogo = (ImageView)findViewById(R.id.head_logo);

		registerBtn.setEnabled(agreeTheRule.isChecked());
		rule.setVisibility(agreeTheRule.isChecked() ? TextView.VISIBLE : TextView.GONE);
		timer = new Timer();
		initEvent();
	}

	private void initEvent(){
		headLogo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(switchFlag==0)
				{
					headLogoCantianer.setVisibility(LinearLayout.VISIBLE);
					switchFlag=1;
				}else
				{
					headLogoCantianer.setVisibility(LinearLayout.GONE);
					switchFlag=0;
				}

			}
		});

		phoneNumberFiled.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (! hasFocus){
					Li.printlnLog("phoneNumerFiled--hasFocus?" + hasFocus);
					phoneNumberString = phoneNumberFiled.getText().toString();
					if (!phoneNumberString.equals(""))
					{
						mProgressDialog.show("号码验证中...");
						checkToRegister(0);
					}
				}
			}
		});

		passwordFirst.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (! hasFocus){
					if(passwordFirst.getText().toString().length()>5&&passwordFirst.getText().toString().length()<16)
					{
						Li.printlnLog("passwordFirst--hasFocus?" + hasFocus);
						passwordFirstString = passwordFirst.getText().toString();
					}else
					{
						Toast.makeText(Re.this, "密码格式不正确！", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		passwordSecond.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (! hasFocus){
					Li.printlnLog("passwordSecond--hasFocus?" + hasFocus);
					passwordSecondString = passwordSecond.getText().toString();
					if(!passwordFirstString.equals(passwordSecondString)){
						Toast.makeText(Re.this, "密码不一致！", Toast.LENGTH_LONG).show();
						passwordSecond.setText("");
					}
				}
			}
		});

		verificationLabel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				phoneNumberString = phoneNumberFiled.getText().toString();
				if(!phoneNumberString.equals("")){
					checkToRegister(0);
					Li.printlnLog("isLegalPhoneNumber---" + isLegalPhoneNumber);
					if (isLegalPhoneNumber){
						mProgressDialog.show("获取验证码...");
						checkToRegister(1);
					}
				} else {
					Toast.makeText(Re.this, "注册码为空！", Toast.LENGTH_SHORT).show();
				}
			}
		});

		agreeTheRule.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				registerBtn.setEnabled(isChecked);
				rule.setVisibility(isChecked ? TextView.VISIBLE : TextView.GONE);
			}
		});

		registerBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				passwordFirstString = passwordFirst.getText().toString();
				passwordSecondString = passwordSecond.getText().toString();
				phoneNumberString = phoneNumberFiled.getText().toString();
				verificationString = verificationFiled.getText().toString();

				if(phoneNumberString.equals(""))
				{
					Toast.makeText(Re.this, "请完整填写注册信息！", Toast.LENGTH_SHORT).show();
				}else if(phoneNumberString.equals(illegalPhoneNum))
				{
					Toast.makeText(Re.this, "电话号码不合法，请重新输入！", Toast.LENGTH_SHORT).show();
				}else if(passwordFirstString.equals("")) {
					Toast.makeText(Re.this, "密码不能为空！", Toast.LENGTH_LONG).show();
				}else if(passwordSecondString.equals("")) {
					Toast.makeText(Re.this, "请验证密码！", Toast.LENGTH_LONG).show();
				}else if(verificationString.equals("")){
					Toast.makeText(Re.this, "请填写验证码！", Toast.LENGTH_LONG).show();
				}else if(! passwordFirstString.equals(passwordSecondString))
				{
					Toast.makeText(Re.this, "密码不一致！", Toast.LENGTH_LONG).show();
					passwordSecond.setText("");
				}else if (! realVerificationString.equals(verificationString))
				{
					Toast.makeText(Re.this, "验证码错误！", Toast.LENGTH_LONG).show();
					verificationFiled.setText("");
				}else if(nickName.getText().toString().equals(""))
				{
					Toast.makeText(Re.this, "昵称不能为空！", Toast.LENGTH_SHORT).show();
				}else if(!agreeTheRule.isChecked())
				{
					Toast.makeText(Re.this, "请同意软件协议！", Toast.LENGTH_SHORT).show();
				}else if(headImage.getText().toString().equals(""))
				{
					Toast.makeText(Re.this, "请选择头像！", Toast.LENGTH_SHORT).show();
				}else
				{
					mProgressDialog.show("上传数据中...");
					checkToRegister(2);

				}
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

	private void checkToRegister(final int flag){

		phoneNumberString = phoneNumberFiled.getText().toString();

		if (phoneNumberString.equals("")){
			Toast.makeText(Re.this, "请填写电话号码！", Toast.LENGTH_SHORT).show();
			return;
		}

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Map<String, String> map = new HashMap<String, String>();
				Message message = Message.obtain();
				String result = "";
				switch(flag){
				//phoneNumber
				case 0:
					Li.printlnLog("验证手机号！");
					map.put("phoneNumberString", phoneNumberString);
					Li.printlnLog(map.toString());
					result=Ht.SendHttpClientPost(ConfigurationFiles.HTTP_CHECK_PHONENUM_PATH, map, "utf-8",Re.this);
					Li.printlnLog("注册："+ConfigurationFiles.HTTP_CHECK_PHONENUM_PATH);
					Li.printlnLog("返回码："+result);
					message.what = ConfigurationFiles.CHECK_PHONENUM_MESSAGE;
					break;
					//verification
				case 1:
					Li.printlnLog("请求验证码！");
					map.put("verificationPhoneNumber", phoneNumberString);
					result=Ht.SendHttpClientPost(ConfigurationFiles.HTTP_CHECK_VERIFICATION_PATH, map, "utf-8",Re.this);
					message.what = ConfigurationFiles.CHECK_VERIFICATION_MESSAGE;
					break;
					//register
				case 2:
					/*
					 * 
					 */
					Li.printlnLog("正在注册！");
					map.put("phoneNumberString", phoneNumberString);
					map.put("passwordString", passwordFirstString);
					map.put("name", nickName.getText().toString());
					map.put("icon", headImage.getText().toString());

					result=Ht.SendHttpClientPost(ConfigurationFiles.HTTP_REGISTER_PATH, map, "utf-8",Re.this);
					message.what = ConfigurationFiles.REGISTER_MESSAGE;
					break;
				}

				Li.printlnLog("result:"+result);

				if(result == null || result.equals(ConfigurationFiles.HTTP_ERROR)){
					message.what=ConfigurationFiles.NO_INTERNET;
				} else {
					message.obj = result;
				}

				registerHandler.sendMessage(message);
			}
		};
		new Thread(runnable).start();
	}

	private void setFetchVericationLabelClickable(boolean clickable){
		verificationLabel.setClickable(clickable);

		if (clickable){
			verificationLabel.setText(R.string.verification_label);
			timer.cancel();
		}else{
			restTime = 60;
			Li.printlnLog("what the fuck! -- " + restTime);

			timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					restTime --;
					registerHandler.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							verificationLabel.setText(restTime +"后可以重发！");
							if (restTime <= 0)
								setFetchVericationLabelClickable(true);
						}
					});

				}
			};
			timer.schedule(task, 1000, 1000);
		}
	}
}
