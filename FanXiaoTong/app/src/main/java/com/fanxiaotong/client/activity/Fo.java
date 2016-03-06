package com.fanxiaotong.client.activity;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.fanxiaotong.client.R;
import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.utils.Ht;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.widget.MPr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Fo extends Activity {
	private TextView backBtn;
	private EditText account, password, passwordAgain, identifyingCode;
	private Button getCodeBtn, submitBtn;
	private LinearLayout newPwdLayout;
	private MPr mProgressDialog;
	private TextView result, time;

	private Timer timer, resultTimer;
	private static int restTime = 60;
	private static String verificationCode = "";
	private static String verificationPhoneNum = "";
	private static String verificationPhone = "";

	private int finalTime = 3;

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int flag = msg.what;
			Li.printlnLog("flag:" + flag);

			switch (flag) {
			case ConfigurationFiles.GET_VERIFICATION_CODE_MESSAGE:
				String resultVerificationString = (String) msg.obj;
				if (resultVerificationString
						.equals(ConfigurationFiles.NO_REGISTER)) {
					Toast.makeText(Fo.this, "���˺Ż�û��ע�ᣡ",
							Toast.LENGTH_SHORT).show();
				} else if (resultVerificationString
						.equals(ConfigurationFiles.OVER_TIMES)) {
					Toast.makeText(Fo.this, "�����һ������������ʮ�Σ�",
							Toast.LENGTH_SHORT).show();
				} else if (resultVerificationString.length() == 6) {
					newPwdLayout.setVisibility(LinearLayout.VISIBLE);
					verificationPhone = account.getText().toString();
					Toast.makeText(Fo.this, "��֤���Ѿ����ͣ������ĵȴ���",
							Toast.LENGTH_SHORT).show();
					verificationCode = resultVerificationString;

					setFetchVericationLabelClickable(false);
				}
				break;
			case ConfigurationFiles.MODIFY_PSD_MESSAGE:
				String modifyPsdResult = (String) msg.obj;
				if (modifyPsdResult.equals(ConfigurationFiles.pastDue)) {
					Toast.makeText(Fo.this, "��֤����ڣ�",
							Toast.LENGTH_SHORT).show();
				} else if (modifyPsdResult
						.equals(ConfigurationFiles.verifywrong)) {
					Toast.makeText(Fo.this, "��֤�����",
							Toast.LENGTH_SHORT).show();
				} else if (!modifyPsdResult
						.equals(ConfigurationFiles.MODIFY_PSD_FAIL)) {
					result.setVisibility(TextView.VISIBLE);
					time.setVisibility(TextView.VISIBLE);
					newPwdLayout.setVisibility(LinearLayout.GONE);
					getCodeBtn.setVisibility(Button.GONE);
					account.setVisibility(EditText.GONE);
					result.setText("�����޸ĳɹ����㱾�»���" + modifyPsdResult
							+ "���һ�����Ļ��ᣡ");
					time.setText("3������ص�¼����!");
					newTimer();
					// dialog.dismiss();
				} else {
					Toast.makeText(Fo.this, "�����ϴ�����������ύ��",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE:
				Li.exit();
				Li.getExitDialog(Fo.this).show();
				break;
			case ConfigurationFiles.HTTP_ERROR_MESSAGE:
				Toast.makeText(Fo.this, "��������", Toast.LENGTH_SHORT)
						.show();
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
		setContentView(R.layout.forget_password);
		iniView();
		iniEvent();
	}

	private void iniView() {
		backBtn = (TextView) findViewById(R.id.back_btn);
		account = (EditText) findViewById(R.id.account);
		password = (EditText) findViewById(R.id.password);
		passwordAgain = (EditText) findViewById(R.id.password_again);
		identifyingCode = (EditText) findViewById(R.id.identifying_code);
		getCodeBtn = (Button) findViewById(R.id.get_code_btn);
		submitBtn = (Button) findViewById(R.id.submit_btn);
		newPwdLayout = (LinearLayout) findViewById(R.id.new_pwd_layout);
		mProgressDialog = new MPr(Fo.this);
		result = (TextView) findViewById(R.id.result);
		time = (TextView) findViewById(R.id.time);
	}

	private void iniEvent() {
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		getCodeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (account.getText().toString().length() == 11) {
					mProgressDialog.show("��ȡ��֤����...");
					verificationPhoneNum = account.getText().toString();
					getVerificationCode(verificationPhoneNum);
				} else {
					Toast.makeText(Fo.this, "�ֻ��Ÿ�ʽ����ȷ",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		submitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!account.getText().toString().equals(verificationPhone)) {
					Toast.makeText(Fo.this,
							"�ύ��֤���ֻ��ŷ����仯������»�ȡ��֤�룡��", Toast.LENGTH_SHORT)
							.show();
					newPwdLayout.setVisibility(LinearLayout.GONE);
				} else if (!(password.getText().toString().length() > 5 && password
						.getText().toString().length() < 16)) {
					Toast.makeText(Fo.this, "���볤��Ϊ6��15λ",
							Toast.LENGTH_SHORT).show();
				} else if (!password.getText().toString()
						.equals(passwordAgain.getText().toString())) {
					Toast.makeText(Fo.this, "���벻һ�£��������д��",
							Toast.LENGTH_SHORT).show();
				} else if (identifyingCode.getText().toString().equals("")) {
					Toast.makeText(Fo.this, "��֤�벻��Ϊ�գ�",
							Toast.LENGTH_SHORT).show();
				} else {
					if (identifyingCode.getText().toString().length() != 6) {
						Toast.makeText(Fo.this, "��֤���ʽ����ȷ��",
								Toast.LENGTH_SHORT).show();
					} else {
						if (identifyingCode.getText().toString()
								.equals(verificationCode)) {
							mProgressDialog.show("�������ϴ���...");
							modifyPsd(account.getText().toString(),
									verificationCode, password.getText()
											.toString());
						} else {
							Toast.makeText(Fo.this, "��֤�벻��ȷ��",
									Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		});

		password.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!hasFocus) {
					if (password.getText().toString().length() > 5
							&& password.getText().toString().length() < 16) {
					} else {
						Toast.makeText(Fo.this, "�����ʽ����ȷ��",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		passwordAgain.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!hasFocus) {
					if (!passwordAgain.getText().toString()
							.equals(password.getText().toString())) {
						Toast.makeText(Fo.this, "���벻һ�£�",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		});

	}

	private void getVerificationCode(final String phone) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String verificationCodeString = Ht
						.SendHttpClientPost(
								ConfigurationFiles.GET_VERIFICATION_CODE
										+  Li.simpleEncryptParm(phone), null, "utf-8",
								Fo.this);
				Li.printlnLog("verificationCodeString:"
						+ verificationCodeString);
				Message message = Message.obtain();
				if (verificationCodeString.equals("")
						|| verificationCodeString
								.equals(ConfigurationFiles.HTTP_ERROR)) {
					message.what = ConfigurationFiles.HTTP_ERROR_MESSAGE;
					message.obj = "";
				} else {
					message.what = ConfigurationFiles.GET_VERIFICATION_CODE_MESSAGE;
					message.obj = verificationCodeString;
				}
				handler.sendMessage(message);
			}
		};
		new Thread(runnable).start();
	}

	private void setFetchVericationLabelClickable(boolean clickable) {
		getCodeBtn.setClickable(clickable);
		if (clickable) {
			getCodeBtn.setText(R.string.verification_label);
			timer.cancel();
		} else {
			restTime = 60;
			Li.printlnLog("what the fuck! -- " + restTime);
			timer = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					restTime--;
					getCodeBtn.post(new Runnable() {

						@Override
						public void run() {
							getCodeBtn.setText(restTime + "�������ط���");
							if (restTime <= 0)
								setFetchVericationLabelClickable(true);
						}
					});

				}
			};
			timer.schedule(task, 1000, 1000);
		}
	}

	private void modifyPsd(final String phone, final String verificationCode,
			final String psd) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				// phoneNumber=13889845930&verifyCode=122323&newPassword=adafsda
				Map<String, String> map = new HashMap<String, String>();
				map.put("phoneNumber", phone);
				map.put("verifyCode", verificationCode);
				map.put("newPassword", psd);
				String modifyPsdResult = Ht.SendHttpClientPost(
						ConfigurationFiles.MODIFY_PSD, map, "utf-8",
						Fo.this);
				Message message = Message.obtain();
				if (modifyPsdResult.equals("")
						|| modifyPsdResult
								.equals(ConfigurationFiles.HTTP_ERROR)) {
					message.what = ConfigurationFiles.MODIFY_PSD_MESSAGE;
					message.obj = "";
				} else {
					message.what = ConfigurationFiles.MODIFY_PSD_MESSAGE;
					message.obj = modifyPsdResult;
				}
				handler.sendMessage(message);
			}
		};
		new Thread(runnable).start();
	}

	public void newTimer() {
		resultTimer = new Timer();
		Li.printlnLog("��ʼʱ�ӣ�");
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				finalTime--;
				time.post(new Runnable() {

					@Override
					public void run() {
						time.setText(finalTime + "������ص�¼����!");
						if (finalTime <= 0)
							finish();
					}
				});

			}
		};
		resultTimer.schedule(task, 1000, 1000);
	}

}
