package com.fanxiaotong.client.fragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.fanxiaotong.client.activity.H;
import com.fanxiaotong.client.activity.L;
import com.fanxiaotong.client.activity.M;
import com.fanxiaotong.client.activity.Sl;
import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.utils.Ht;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.widget.MPr;
import com.fanxiaotong.client.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class Le extends Fragment {
	private RelativeLayout myCollect;
	private RelativeLayout historyOrder, defaultAddress, defaultPhone;
	private TextView name, phoneNum;
	private Button exit;
	private static TextView collectStoreNum;
	private static TextView collectFoodNum;
	private static TextView allBuyNum;
	private TextView defaultUserPhoneNum;
	private TextView defaultUserAddress;
	private LinearLayout collectStore, collectFood, allBuy;
	private ImageView headView;
	private Dialog dialog;
	private int flag = 0;// ����flag˵����flag=0�����޸�Ĭ���Ͳ͵�ַ,flag=1�����޸�Ĭ���Ͳ͵绰����
	private String temp;// �����洢�޸ĵ�ֵ
	private MPr progressDialog;
	private TextView changePhoneNum;

	private String newPhoneNumber;
	private MPr mProgressDialog;

	private Timer timer;
	private static boolean isLegalPhoneNumber = false;

	private static int restTime = 60;

	private TextView verificationLabel;
	private String realVerificationString = "";
	private String confirmPhoneNumber = "";

	@SuppressLint("HandlerLeak")
	Handler changePhoneNumberHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int flag = msg.what;
			String result = (String) msg.obj;
			switch (flag) {
			case ConfigurationFiles.CHECK_PHONENUM_MESSAGE:
				Li.printlnLog("CHECK_PHONENUM_MESSAGE--result:" + result);

				if (result.equals(ConfigurationFiles.LEGAL_PHONENUMER)) {
					isLegalPhoneNumber = true;
					// LittleUtil.printlnLog("Come ON!");
					Toast.makeText(getActivity(), "������ã�", Toast.LENGTH_SHORT)
							.show();
				} else {
					isLegalPhoneNumber = false;
					Toast.makeText(getActivity(), "�ú����Ѵ��ڣ��������д��",
							Toast.LENGTH_SHORT).show();
				}
				break;

			case ConfigurationFiles.CHECK_VERIFICATION_MESSAGE:

				Li.printlnLog("CHECK_VERIFICATION_MESSAGE--result:"
						+ result);

				if (result.endsWith(ConfigurationFiles.CANNOT_GET_VERIFICATION)) {
					Toast.makeText(getActivity(), "��ʱ�޷���ȡ��֤��T.T",
							Toast.LENGTH_SHORT).show();
				} else if (result
						.endsWith(ConfigurationFiles.INCORRECT_PHONE_NUMBER)) {
					Toast.makeText(getActivity(), "���õģ��������������أ���o(�s���t)o",
							Toast.LENGTH_SHORT).show();
				} else if (result
						.endsWith(ConfigurationFiles.NULL_PHONE_NUMBER)) {
					Toast.makeText(getActivity(), "С�����������������֤�뷢��˭������",
							Toast.LENGTH_SHORT).show();
				} else {
					realVerificationString = result;
					confirmPhoneNumber = newPhoneNumber;
					setFetchVericationLabelClickable(false);
				}

				break;

			case ConfigurationFiles.NO_INTERNET:
				Toast.makeText(getActivity(), "�������", Toast.LENGTH_SHORT)
						.show();
				break;
			case ConfigurationFiles.CHANGE_PHONE_NUMBER_INFORMATION:
				Li.printlnLog("�޸ĵ绰���룡");
				Li.printlnLog("result��" + result);
				if (result.equals(ConfigurationFiles.HTTP_ERROR)) {
					Toast.makeText(getActivity(), "�������", Toast.LENGTH_SHORT)
							.show();
				} else {
					try {

						if (result
								.equals(ConfigurationFiles.LEFT_MODIFY_SUCCEED)) {
							dialog.dismiss();
							Toast.makeText(getActivity(), "�޸ĳɹ��������µ�¼��",
									Toast.LENGTH_SHORT).show();
							// �˻ص�¼����
							Ht.CookieContiner.remove("phoneNumber");
							L.loginUserPhoneNum = "";
							Ca.foodOrderContainer
									.removeAllViews();
							Ca.submitCost
									.setVisibility(RelativeLayout.GONE);// �����ύ��ť���ɼ�
							Ca.emptyCart
									.setVisibility(LinearLayout.GONE);
							Ca.unloginCart
									.setVisibility(LinearLayout.VISIBLE);
							Ca.orderList.clear();
							V.setLoginImage(0);
							L.fakeMyInfo.setPhoneNumber("");
							O.orderContainer
									.removeAllViews();
							O.unlogin
									.setVisibility(RelativeLayout.GONE);
							O.timer.cancel();
							O.currentArrayList.clear();
							Sl.loginFlag = false;
							((Sl) getActivity()).showLeft();
							// -----------------------------------------------------------------------
							Sl.getmSlidingMenu().setCanSliding(
									false, false);
							Intent intent = new Intent(getActivity(),
									L.class);
							startActivity(intent);
						} else {
							Toast.makeText(getActivity(), "�������",
									Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
						e.printStackTrace();
						Toast.makeText(getActivity(), "�������",
								Toast.LENGTH_SHORT).show();
					}
				}

				break;
			case ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE:
				Li.exit();
				Li.getExitDialog(getActivity()).show();
				break;

			default:
				break;
			}
			mProgressDialog.dismiss();
		};
	};

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			int message = msg.what;
			if (message == ConfigurationFiles.LEFT_CHANGE_INFORMATION) {
				String result = (String) msg.obj;
				Li.printlnLog("result:" + result);
				if (result.equals("")
						|| result.equals(ConfigurationFiles.HTTP_ERROR)) {
					Toast.makeText(getActivity(), "���緢���쳣��������ύ��",
							Toast.LENGTH_SHORT).show();
				} else {
					try {
						if (result
								.equals(ConfigurationFiles.LEFT_MODIFY_SUCCEED)) {
							Li.printlnLog("flag:" + flag);
							if (flag == 0) {
								L.fakeMyInfo.setAddress(temp);
								dialog.dismiss();
								defaultUserAddress.setText(temp);
							} else {
								L.fakeMyInfo.setDefalutNumber(temp);
								dialog.dismiss();
								defaultUserPhoneNum.setText(temp);
							}

						} else {
							Toast.makeText(getActivity(), "���緢���쳣��������ύ��",
									Toast.LENGTH_SHORT).show();
						}

					} catch (Exception e) {
						e.printStackTrace();
						Toast.makeText(getActivity(), "���緢���쳣��������ύ��",
								Toast.LENGTH_SHORT).show();
					}
				}
			} else if (message == ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE) {
				Li.exit();
				Li.getExitDialog(getActivity());
			}
			progressDialog.dismiss();
		};
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Li.printlnLog("left---onCreateView");
		View view = inflater.inflate(R.layout.left, null);
		defaultAddress = (RelativeLayout) view
				.findViewById(R.id.default_address);
		defaultPhone = (RelativeLayout) view.findViewById(R.id.default_phone);
		name = (TextView) view.findViewById(R.id.name);
		phoneNum = (TextView) view.findViewById(R.id.phone_num);
		changePhoneNum = (TextView) view.findViewById(R.id.change_phone_num);
		collectStoreNum = (TextView) view.findViewById(R.id.collect_store_num);
		collectFoodNum = (TextView) view.findViewById(R.id.collect_food_num);
		allBuyNum = (TextView) view.findViewById(R.id.all_buy_num);
		defaultUserPhoneNum = (TextView) view
				.findViewById(R.id.default_user_phone_num);
		defaultUserAddress = (TextView) view
				.findViewById(R.id.default_user_address);
		myCollect = (RelativeLayout) view.findViewById(R.id.my_collect);
		historyOrder = (RelativeLayout) view.findViewById(R.id.history_order);
		exit = (Button) view.findViewById(R.id.exit);
		allBuy = (LinearLayout) view.findViewById(R.id.all_buy);
		collectStore = (LinearLayout) view.findViewById(R.id.collect_store);
		headView = (ImageView) view.findViewById(R.id.head_view);
		collectFood = (LinearLayout) view.findViewById(R.id.collect_food);

		headView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		defaultAddress.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				flag = 0;
				dialog = getDialog(getActivity());
				dialog.show();
			}
		});

		defaultPhone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 1;
				dialog = getDialog(getActivity());
				dialog.show();
			}
		});
		changePhoneNum.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog = getChangePhoneDialog(getActivity());
				dialog.show();
			}
		});
		collectStore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				M.flagBit = 1;
				Intent intent = new Intent(getActivity(),
						M.class);
				startActivity(intent);
			}
		});
		collectFood.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				M.flagBit = 0;
				Intent intent = new Intent(getActivity(),
						M.class);
				startActivity(intent);
			}
		});
		allBuy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), H.class);
				startActivity(intent);

			}
		});
		myCollect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						M.class);
				startActivity(intent);

			}
		});
		historyOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), H.class);
				startActivity(intent);
			}
		});
		exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Ht.CookieContiner.remove("phoneNumber");
				L.loginUserPhoneNum = null;
				Ca.foodOrderContainer.removeAllViews();
				Ca.submitCost.setVisibility(RelativeLayout.GONE);// �����ύ��ť���ɼ�
				Ca.emptyCart.setVisibility(LinearLayout.GONE);
				Ca.orderList.clear();
				Ca.unloginCart
						.setVisibility(LinearLayout.VISIBLE);
				O.orderContainer.removeAllViews();
				O.unlogin
						.setVisibility(RelativeLayout.GONE);
				O.currentArrayList.clear();
				O.timer.cancel();
				L.fakeMyInfo.setPhoneNumber("");
				V.setLoginImage(1);
				Sl.loginFlag = false;
				V.setLoginImage(0);
				Sl.getViewPager().setCurrentItem(0);
				((Sl) getActivity()).showLeft();
				Sl.getmSlidingMenu().setCanSliding(false, false);

			}
		});
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Li.printlnLog("left---onActivityCreated");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Li.printlnLog("left---onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		Li.printlnLog("left---onResume");

		if (Sl.loginFlag) {
			Li.setHeadLogoBackground(headView,
					L.fakeMyInfo.getIcon());
			name.setText(L.fakeMyInfo.getName());
			phoneNum.setText(L.fakeMyInfo.getPhoneNumber());
			Li.printlnLog("collectStoreNum:" + collectStoreNum.toString());
			Li.printlnLog("collectFoodNum:" + collectFoodNum.toString());
			Li.printlnLog("allBuyNum:" + allBuyNum.toString());
			collectStoreNum.setText(L.fakeMyInfo.getRestNum() + "");
			collectFoodNum.setText(L.fakeMyInfo.getFoodNum() + "");
			allBuyNum.setText(L.fakeMyInfo.getOrderNum() + "");
			defaultUserAddress.setText(L.fakeMyInfo.getAddress()
					.equals("") ? "��δ����Ĭ���Ͳ͵�ַ" : L.fakeMyInfo
					.getAddress());
			defaultUserPhoneNum.setText(L.fakeMyInfo
					.getDefalutNumber());
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Li.printlnLog("left---onPause");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Li.printlnLog("left---onStop");
	}

	// �Ի��� ����flag˵����flag=0�����޸�Ĭ���Ͳ͵�ַ,flag=1�����޸�Ĭ���Ͳ͵绰����
	private Dialog getDialog(final Context context) {
		final Dialog dialog = new Dialog(context, R.style.myDialog);
		dialog.setContentView(R.layout.dialog_in_left);
		TextView dialogTitle = (TextView) dialog
				.findViewById(R.id.dialog_title);
		final EditText editText = (EditText) dialog
				.findViewById(R.id.content_input);
		TextView modifyBtn = (TextView) dialog.findViewById(R.id.modify_btn);
		ImageView clouseBtn = (ImageView) dialog
				.findViewById(R.id.dialog_close_btn);
		if (flag == 0) {
			dialogTitle.setText("�޸�Ĭ���Ͳ͵�ַ");
			editText.setHint("������Ĭ���Ͳ͵�ַ��");
			if (!L.fakeMyInfo.getAddress().equals("")) {
				editText.setText(L.fakeMyInfo.getAddress());
			}
		} else {
			dialogTitle.setText("�޸�Ĭ���Ͳ͵绰");
			editText.setHint("������Ĭ�ϵ��Ͳ͵绰���룡");
			editText.setInputType(InputType.TYPE_CLASS_PHONE);
			if (!L.fakeMyInfo.getDefalutNumber().equals("")) {
				editText.setText(L.fakeMyInfo.getDefalutNumber());
			}
		}
		clouseBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		modifyBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("phoneNumber",
						L.fakeMyInfo.getPhoneNumber());
				String editTextContent = editText.getText().toString();
				if (flag == 0) {
					if (editTextContent.equals("")) {
						Toast.makeText(getActivity(), "Ĭ�ϵ�ַ����Ϊ�գ�",
								Toast.LENGTH_SHORT).show();
					} else {

						progressDialog = new MPr(context);
						progressDialog.show("�����ύ��...");
						submitOrder(editTextContent, flag);
					}
				} else {
					if (editTextContent.length() != 11) {
						Toast.makeText(getActivity(), "�޸ĵ��ֻ��ű���Ϊ11λ",
								Toast.LENGTH_SHORT).show();
					} else {
						progressDialog = new MPr(getActivity());
						progressDialog.show("�����ύ��...");
						submitOrder(editTextContent, flag);
					}
				}

			}
		});
		return dialog;
	}

	private void submitOrder(final String editTextContent, final int flag) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				temp = editTextContent;
				Map<String, String> map = new HashMap<String, String>();
				map.put("phoneNumber",
						L.fakeMyInfo.getPhoneNumber());
				String result = "";
				if (flag == 0) {
					map.put("newDefaultAddress", editTextContent);
					result = Ht.SendHttpClientPost(
							ConfigurationFiles.DEFAULT_ADDRESS_URL, map,
							"utf-8", getActivity());
				} else {
					map.put("newDefaultNumber", editTextContent);
					result = Ht.SendHttpClientPost(
							ConfigurationFiles.DEFAULT_PHONE_NUMBER_URL, map,
							"utf-8", getActivity());
				}
				Message message = Message.obtain();
				if (result.equals(ConfigurationFiles.OTHER_PLACE_LOGIN)) {
					message.what = ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE;
				} else {
					message.what = ConfigurationFiles.LEFT_CHANGE_INFORMATION;
					message.obj = result;
				}
				handler.sendMessage(message);
			}
		};
		new Thread(runnable).start();
	}

	private Dialog getChangePhoneDialog(final Context context) {
		final Dialog dialog = new Dialog(context, R.style.myDialog);
		dialog.setContentView(R.layout.change_phone_number_dialog);
		dialog.setCanceledOnTouchOutside(false);
		final EditText newPhone = (EditText) dialog
				.findViewById(R.id.new_phone_input);
		TextView modifyBtn = (TextView) dialog.findViewById(R.id.modify_btn);
		final EditText verificationFiled = (EditText) dialog
				.findViewById(R.id.verification_filed);
		verificationLabel = (TextView) dialog
				.findViewById(R.id.verification_label);
		mProgressDialog = new MPr(context);

		newPhone.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					newPhoneNumber = newPhone.getText().toString();

					if (newPhoneNumber.length() != 11) {
						Toast.makeText(context, "�ֻ��ű���Ϊ11λ��",
								Toast.LENGTH_SHORT).show();
					} else {
						if (isLegalPhoneNumber) {
							if (!newPhoneNumber.equals(confirmPhoneNumber)
									&& !confirmPhoneNumber.equals("")) {
								Toast.makeText(context,
										"��֤�ĵ绰���뷢���ı䣬����»�ȡ��֤�룡",
										Toast.LENGTH_SHORT).show();
								isLegalPhoneNumber = false;
								realVerificationString = "";
							}
						} else {
							mProgressDialog.show("������֤��...");
							checkToRegister(0);
						}
					}
				}
			}
		});
		verificationLabel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				newPhoneNumber = newPhone.getText().toString();
				if (newPhoneNumber.length() != 11) {
					Toast.makeText(context, "�ֻ��ű���Ϊ11λ��", Toast.LENGTH_SHORT)
							.show();
				} else {
					if (isLegalPhoneNumber) {
						mProgressDialog.show("������֤����...");
						checkToRegister(1);
					} else {
						mProgressDialog.show("������֤��...");
						checkToRegister(0);
						Li.printlnLog("isLegalPhoneNumber---"
								+ isLegalPhoneNumber);
						if (isLegalPhoneNumber) {
							mProgressDialog.show("������֤����...");
							checkToRegister(1);
						}
					}
				}
			}
		});
		ImageView clouseBtn = (ImageView) dialog
				.findViewById(R.id.dialog_close_btn);

		clouseBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		modifyBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (newPhone.getText().toString().equals("")
						|| verificationFiled.getText().toString().equals("")) {
					Toast.makeText(context, "��������ݲ���Ϊ�գ�", Toast.LENGTH_SHORT)
							.show();
				} else {
					if (realVerificationString.equals("")
							|| realVerificationString.length() != 6) {
						Toast.makeText(context, "������������»�ȡ��֤�룡",
								Toast.LENGTH_SHORT).show();
					} else {
						if (verificationFiled.getText().toString()
								.equals(realVerificationString)) {
							// �ύ�޸�����
							if (confirmPhoneNumber.length() == 11) {
								mProgressDialog.show("�����ϴ���...");
								checkToRegister(2);
							}
						} else {
							Toast.makeText(context, "��֤������",
									Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		});
		return dialog;
	}

	private void checkToRegister(final int flag) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Map<String, String> map = new HashMap<String, String>();
				Message message = Message.obtain();
				String result = "";
				switch (flag) {
				// phoneNumber
				case 0:
					Li.printlnLog("��֤�ֻ��ţ�");
					map.put("phoneNumberString", newPhoneNumber);
					Li.printlnLog(map.toString());
					result = Ht.SendHttpClientPost(
							ConfigurationFiles.HTTP_CHECK_PHONENUM_PATH, map,
							"utf-8", getActivity());
					Li.printlnLog("ע�᣺"
							+ ConfigurationFiles.HTTP_CHECK_PHONENUM_PATH);
					Li.printlnLog("�����룺" + result);
					message.what = ConfigurationFiles.CHECK_PHONENUM_MESSAGE;
					break;
				// verification
				case 1:
					Li.printlnLog("������֤�룡");
					map.put("verificationPhoneNumber", newPhoneNumber);
					result = Ht.SendHttpClientPost(
							ConfigurationFiles.HTTP_CHECK_VERIFICATION_PATH,
							map, "utf-8", getActivity());
					message.what = ConfigurationFiles.CHECK_VERIFICATION_MESSAGE;
					break;
				// register
				case 2:
					Li.printlnLog("�ύ�޸ĵĵ绰���룡");
					// accountNumber=13889845930&newAccountNumber=13190016190
					map.put("phoneNumber",
							L.fakeMyInfo.getPhoneNumber());
					map.put("newAccountNumber", confirmPhoneNumber);
					result = Ht.SendHttpClientPost(
							ConfigurationFiles.CHANGE_PHONE_NUMBER_URL, map,
							"utf-8", getActivity());
					message.what = ConfigurationFiles.CHANGE_PHONE_NUMBER_INFORMATION;
					break;
				}

				Li.printlnLog("result:" + result);

				if (result == null
						|| result.equals(ConfigurationFiles.HTTP_ERROR)) {
					message.what = ConfigurationFiles.NO_INTERNET;
				} else if (result.equals(ConfigurationFiles.OTHER_PLACE_LOGIN)) {
					message.what = ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE;
				} else {
					message.obj = result;
				}

				changePhoneNumberHandler.sendMessage(message);
			}
		};
		new Thread(runnable).start();
	}

	private void setFetchVericationLabelClickable(boolean clickable) {
		verificationLabel.setClickable(clickable);
		if (clickable) {
			verificationLabel.setText(R.string.verification_label);
			timer.cancel();
		} else {
			restTime = 60;
			Li.printlnLog("what the fuck! -- " + restTime);

			timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					restTime--;
					changePhoneNumberHandler.post(new Runnable() {

						@Override
						public void run() {
							verificationLabel.setText(restTime + "������ط���");
							if (restTime <= 0)
								setFetchVericationLabelClickable(true);
						}
					});

				}
			};
			timer.schedule(task, 1000, 1000);
		}
	}

	public static void iniUI() {
		Li.printlnLog("data---left---collectFoodNum: "
				+ collectFoodNum.getText());
		Li.printlnLog("data---left---collectStoreNum: "
				+ collectStoreNum.getText());
		Li
				.printlnLog("data---left---allBuyNum: " + allBuyNum.getText());
		collectFoodNum.setText(String.valueOf(L.fakeMyInfo
				.getFoodNum()));
		collectStoreNum.setText(String.valueOf(L.fakeMyInfo
				.getRestNum()));
		allBuyNum
				.setText(String.valueOf(L.fakeMyInfo.getOrderNum()));
	}
}