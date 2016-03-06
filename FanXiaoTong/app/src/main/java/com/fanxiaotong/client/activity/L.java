package com.fanxiaotong.client.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fanxiaotong.client.bean.FakeMyInfo;
import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.fragment.O;
import com.fanxiaotong.client.fragment.V;
import com.fanxiaotong.client.utils.Car;
import com.fanxiaotong.client.utils.Fa;
import com.fanxiaotong.client.utils.Ht;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.utils.Us;
import com.fanxiaotong.client.widget.MP;
import com.fanxiaotong.client.widget.MPr;
import com.fanxiaotong.client.widget.SU;
import com.fanxiaotong.client.widget.SU.OnDeleteBtnClickListener;
import com.fanxiaotong.client.widget.SU.OnUserNameClickListener;
import com.fanxiaotong.client.R;

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
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class L extends Activity {
	private ImageView deletePhone, morePhone;
	private TextView register;
	private EditText phoneNumberFiled;
	private EditText passwordFirst;
	private CheckBox remberPsdCheck;
	private Button loginBtn;
	private TextView forgetPwd;
	private MPr mProgressDialog;
	private String phoneNumberString, passwordString;
	public static String loginUserPhoneNum = "";
	public static int flag = 0;// flag=0���������Զ���¼��flag=1���������Զ���¼����
	public static FakeMyInfo fakeMyInfo;
	MP mPopListView;
	SU singleUserView;
	public static ArrayList<SimpleUser> userList = new ArrayList<L.SimpleUser>();
	@SuppressLint("HandlerLeak")
	Handler loginHandler = new Handler() {
		public void handleMessage(Message msg) {
			int flag = msg.what;
			Li.printlnLog("flag:" + flag);
			if (flag == ConfigurationFiles.LOGIN_MESSAGE) {
				String result = (String) msg.obj;
				Li.printlnLog("result:" + result);
				if (result.equals(ConfigurationFiles.HTTP_ERROR)) {
					Li.printlnLog("�������");
					Toast.makeText(L.this, "���緢���쳣��",
							Toast.LENGTH_SHORT).show();
				} else if (result.equals(ConfigurationFiles.LOGIN_FAIL)) {
					Toast.makeText(L.this, "�û��������벻��ȷ!",
							Toast.LENGTH_SHORT).show();
				} else {
					try {
						int returnCode = Integer.parseInt(result);
						Li.printlnLog("returnCode:" + returnCode);
					} catch (Exception e) {
						if (remberPsdCheck.isChecked()) {
							if (!isInDbByPhone(phoneNumberFiled.getText()
									.toString())) {
								Us userDbHelper = new Us(
										L.this);
								userDbHelper.insert(1, phoneNumberFiled
										.getText().toString(), passwordFirst
										.getText().toString(), "", 1);
							}
						}
						Li.printlnLog("��¼���صĽ����������" + result);
						fakeMyInfo = Fa.getSignal(result,
								FakeMyInfo.class);
						Li.printlnLog("fakeMyInfo:"
								+ fakeMyInfo.toString());
						// ��½�ɹ�,��Ӷ�Ӧ�Ĵ���
						loginUserPhoneNum = phoneNumberFiled.getText()
								.toString();
						Sl.loginFlag = true;
						// ����ʱ��
						O.loginSecceed();
						finish();
						// LittleUtil.printlnLog("fakeMyInfo:"+fakeMyInfo.toString());
						// Toast.makeText(LoginActivity.this, "��½�ɹ�",
						// Toast.LENGTH_LONG).show();
						// ���õ�¼��İ�ť����
						V.setLoginImage(1);
					}
				}
			} else if (flag == ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE) {
				Li.exit();
				Li.getExitDialog(L.this).show();
			} else {
				Toast.makeText(L.this, "��ȡ��֤��ʧ�ܣ�����»�ȡ��",
						Toast.LENGTH_SHORT).show();
			}
			mProgressDialog.dismiss();
		};
	};

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.login);
		deletePhone = (ImageView) findViewById(R.id.delete_phone);
		morePhone = (ImageView) findViewById(R.id.more_phone);
		mPopListView = new MP(L.this);
		phoneNumberFiled = (EditText) findViewById(R.id.phone_number_filed);
		passwordFirst = (EditText) findViewById(R.id.password_first);
		remberPsdCheck = (CheckBox) findViewById(R.id.rember_password_check);
		forgetPwd = (TextView) findViewById(R.id.forget_pwd);
		loginBtn = (Button) findViewById(R.id.login_btn);
		register = (TextView) findViewById(R.id.register);
		initDataFromUserDB();

		deletePhone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				phoneNumberFiled.setText("");
				passwordFirst.setText("");
			}
		});

		if (userList.size() != 0) {
			phoneNumberFiled.setText(userList.get(userList.size() - 1)
					.getPhoneNumber());
			passwordFirst.setText(userList.get(userList.size() - 1)
					.getUserPassword());
			remberPsdCheck.setChecked(true);
		}

		morePhone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopListView = new MP(L.this);
				for (Iterator<SimpleUser> iterator = userList.iterator(); iterator
						.hasNext();) {
					singleUserView = new SU(L.this);
					final SimpleUser simpleUser = (SimpleUser) iterator.next();
					singleUserView.setUserName(simpleUser.getPhoneNumber());
					singleUserView
							.setOnUserNameClickListener(new OnUserNameClickListener() {

								@Override
								public void onClick() {
									phoneNumberFiled.setText(simpleUser
											.getPhoneNumber());
									passwordFirst.setText(simpleUser
											.getUserPassword());
									mPopListView.dismiss();
								}
							});
					singleUserView
							.setOnDeleteBtnClickListener(new OnDeleteBtnClickListener() {
								@Override
								public void onClick() {
									getDialog(L.this).show();
								}
							});

					mPopListView.addView(singleUserView);
				}
				mPopListView.showAsDropDown(morePhone);
			}
		});
		phoneNumberFiled.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus
						&& !phoneNumberFiled.getText().toString().equals("")) {
					deletePhone.setVisibility(ImageView.VISIBLE);
				} else {
					deletePhone.setVisibility(ImageView.GONE);
				}
			}
		});
		forgetPwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * dialog = getForgetPsdDialog(LoginActivity.this);
				 * dialog.show();
				 */
				Intent intent = new Intent(L.this,
						Fo.class);
				startActivity(intent);

			}
		});
		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showRegisterActivity();
			}
		});
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				phoneNumberString = phoneNumberFiled.getText().toString();
				passwordString = passwordFirst.getText().toString();
				Li.printlnLog("password:" + passwordString);
				if (phoneNumberString.equals("") || passwordString.equals("")) {
					Toast.makeText(L.this, "�ֻ��Ż����벻��Ϊ�գ�",
							Toast.LENGTH_SHORT).show();
				} else {
					mProgressDialog = new MPr(L.this);
					mProgressDialog.show("��½��...");
					login();
				}
			}
		});
	}

	private void showRegisterActivity() {
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		intent.setClass(L.this, Re.class);
		startActivity(intent);
	}

	private void login() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Map<String, String> map = new HashMap<String, String>();
				map.put("phoneNumberString", phoneNumberString);
				map.put("passwordString", passwordString);
				Li.printlnLog("��½��");
				Li.printlnLog("�������ӵ�½��ʼ��������������������������");
				System.out
						.println("��½·����" + ConfigurationFiles.HTTP_LOGIN_PATH);
				String result = Ht.SendHttpClientPost(
						ConfigurationFiles.HTTP_LOGIN_PATH, map, "utf-8",
						L.this);
				Li.printlnLog("result:" + result);
				Message message = Message.obtain();
				if (result != null
						&& !result.equals(ConfigurationFiles.HTTP_ERROR)) {
					if (result.equals(ConfigurationFiles.OTHER_PLACE_LOGIN)) {
						message.what = ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE;
						loginHandler.sendMessage(message);
					} else {
						message.what = ConfigurationFiles.LOGIN_MESSAGE;
						message.obj = result;
						loginHandler.sendMessage(message);
					}
				} else {
					Li.printlnLog("������󣡣�������������������������������");
					message.what = ConfigurationFiles.LOGIN_MESSAGE;
					message.obj = result;
					loginHandler.sendMessage(message);
				}
			}
		};
		new Thread(runnable).start();
	}

	class SimpleUser {
		public int id;
		public int userId;
		public String phoneNumber;
		public String userPassword;
		public String address;
		public int userLevel;

		public SimpleUser() {
			super();
		}

		public SimpleUser(int id, int userId, String phoneNumber,
				String userPassword, String address, int userLevel) {
			super();
			this.id = id;
			this.userId = userId;
			this.phoneNumber = phoneNumber;
			this.userPassword = userPassword;
			this.address = address;
			this.userLevel = userLevel;
		}

		public int getId() {
			return id;
		}

		public int getUserId() {
			return userId;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public String getUserPassword() {
			return userPassword;
		}

		public String getAddress() {
			return address;
		}

		public int getUserLevel() {
			return userLevel;
		}

		public void setId(int id) {
			this.id = id;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public void setUserPassword(String userPassword) {
			this.userPassword = userPassword;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public void setUserLevel(int userLevel) {
			this.userLevel = userLevel;
		}

		@Override
		public String toString() {
			return "SimpleUser [id=" + id + ", userId=" + userId
					+ ", phoneNumber=" + phoneNumber + ", userPassword="
					+ userPassword + ", address=" + address + ", userLevel="
					+ userLevel + "]";
		}
	}

	/**
	 * �ӹ��ﳵ���ݿ��ʼ������
	 * 
	 * @date 2013.12.27
	 */
	@SuppressWarnings("deprecation")
	private void initDataFromUserDB() {
		Us cartDbHelper = new Us(L.this);
		Cursor orderCursor = cartDbHelper.select();
		orderCursor.requery();
		userList.clear();
		int orderCount = orderCursor.getCount();
		Li.printlnLog("����������------------" + orderCount);
		if (orderCount > 0) {
			int id;
			int userId;
			String phoneNumber;
			String userPassword;
			String address;
			int userLevel;
			while (orderCursor.moveToNext()) {
				id = orderCursor.getInt(0);
				userId = orderCursor.getInt(1);
				phoneNumber = orderCursor.getString(2);
				userPassword = orderCursor.getString(3);
				address = orderCursor.getString(4);
				userLevel = orderCursor.getInt(5);
				userList.add(new SimpleUser(id, userId, phoneNumber,
						userPassword, address, userLevel));

			}
		}
		orderCursor.close();
		cartDbHelper.close();
	}

	private boolean isInDbByPhone(String phone) {
		for (Iterator<SimpleUser> iterator = userList.iterator(); iterator
				.hasNext();) {
			SimpleUser simpleUser = (SimpleUser) iterator.next();
			if (simpleUser.getPhoneNumber().equals(phone)) {
				Li.printlnLog("���û��Ѿ����ڱ��ص����ݿ⣡");
				return true;
			}

		}
		return false;
	}

	private void deleteUserByphone(String phoneNumber) {
		Li.printlnLog("����ɾ����������������������");
		Us dbHelper = new Us(L.this);
		for (Iterator<SimpleUser> iterator = userList.iterator(); iterator
				.hasNext();) {
			SimpleUser simpleUser = (SimpleUser) iterator.next();
			Li.printlnLog("simpleUser:" + simpleUser.getPhoneNumber());
			if (simpleUser.getPhoneNumber().equals(phoneNumber)) {
				dbHelper.delete(simpleUser.getId());
				return;
			}

		}
	}

	private Dialog getDialog(final Context context) {
		final Dialog dialog = new Dialog(context, R.style.myDialog);
		dialog.setContentView(R.layout.dialog_in_login);
		final CheckBox deleteDb = (CheckBox) dialog
				.findViewById(R.id.delete_db);
		TextView textView = (TextView) dialog.findViewById(R.id.content);
		TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
		TextView delete = (TextView) dialog.findViewById(R.id.delete);
		textView.setText("��ȷ��ɾ����СͰ�˺�" + singleUserView.getUserName() + "��?");
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Li.printlnLog("------------------------------------");
				Li.printlnLog("ɾ���û�����" + singleUserView.getUserName());
				if (phoneNumberFiled.getText().toString()
						.equals(singleUserView.getUserName())) {
					phoneNumberFiled.setText("");
					passwordFirst.setText("");
				}
				if (deleteDb.isChecked()) {
					Car cartDbHelper = new Car(
							L.this);
					cartDbHelper.deleteByPhoneNum(singleUserView.getUserName());
					deleteUserByphone(singleUserView.getUserName());
					mPopListView.removeView(singleUserView);
					initDataFromUserDB();
					dialog.dismiss();

				} else {
					mPopListView.removeAll();
					deleteUserByphone(singleUserView.getUserName());
					mPopListView.removeView(singleUserView);
					initDataFromUserDB();
					dialog.dismiss();
				}
			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();

			}
		});
		return dialog;
	}
}
