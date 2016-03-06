package com.fanxiaotong.client.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.fanxiaotong.client.activity.L;
import com.fanxiaotong.client.activity.Sl;
import com.fanxiaotong.client.bean.FakeMyOrderForm;
import com.fanxiaotong.client.bean.FakeMyOrderInfo;
import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.utils.Fa;
import com.fanxiaotong.client.utils.Fi;
import com.fanxiaotong.client.utils.Ht;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.widget.MPr;
import com.fanxiaotong.client.widget.SO;
import com.fanxiaotong.client.widget.SOr;
import com.fanxiaotong.client.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("Wakelock")
public class O extends Fragment {
	// �����״��
	/*
	 * 1���ֻ��״ε�½���յ�״̬�����仯����ʱû����һ�ε�״̬��Ϣ��֪ͨΪ��������Ϣ�����仯�� 2�����������仯�����ش�����״̬������֪ͨ���仯��С����
	 */
	private static int refreshTime = -1;// ʱ��
	// public static boolean isConmingFromCart = false;
	// private static int isChanged=1003;//1002Ϊ�ı��ˣ�1003Ϊû�иı�
	public static ScrollView orderScroll;
	public static LinearLayout orderContainer;
	public static LinearLayout emptyOrderList;
	public static LinearLayout unlogin;
	public static ArrayList<FakeMyOrderForm> currentArrayList = new ArrayList<FakeMyOrderForm>();
	public static ArrayList<FakeMyOrderInfo> tempArrayList;
	public static boolean isVisible = false;// �����ҳ�Ƿ���û��ɼ��������ɼ�ʱ����̨�ļ�鵽�����и���ʱ����Ҫ���ĸ�ҳ��UI�������û��ɼ�ʱ����Ҫ����UI
	public static MPr mProgressDialog;
	private static NotificationManager notificationManager;// ״̬��֪ͨ������
	private static HashMap<String, Integer> stateHashMap;
	private static Dialog dialog;
	public static Timer timer;
	static Context context;
	private static FakeMyOrderInfo tempOldFakeMyOrderInfo,
			tempNewFakeMyOrderInfo;// ��ֻ��һ��ʳ��״̬�����仯ʱ����Ϊ�м�����洢�仯ǰ�ͱ仯�����Ϣ
	private static int foodCount;// ������¼״̬�仯��ʳ�����

	private static LinearLayout noNetworkLayout;
	/**
	 * timeFlag = 0 ����time�Ѿ�ֹͣ��timeFlag=1 ����timeû��ֹͣ
	 */
	public static int timeFlag = 1;

	static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			int flag = msg.what;
			String result = "";
			result = (String) msg.obj;
			switch (flag) {
			case ConfigurationFiles.ORDER_LIST_MESSAGE:
				try {
					if (result.equals(ConfigurationFiles.GET_INFORMATION_FAIL)) {
						// ����Ϊ��
						orderContainer.removeAllViews();
						emptyOrderList.setVisibility(LinearLayout.VISIBLE);
						unlogin.setVisibility(LinearLayout.GONE);
						orderScroll.setVisibility(ScrollView.GONE);
						noNetworkLayout.setVisibility(LinearLayout.GONE);
						currentArrayList.clear();
						if (isVisible)
							V.orderBtn
									.setBackgroundResource(R.drawable.orders_actived);
					} else {
						if (result.equals("[]")) {
							// ����Ϊ�գ���Ҫ��timerȡ����Ȼ�����ύ������ʱ������timer
							timer.cancel();
						}
						List<FakeMyOrderForm> list = Fa.getList(
								result, FakeMyOrderForm.class);
						Li.printlnLog("list:" + list);
						currentArrayList.clear();
						for (Iterator<FakeMyOrderForm> iterator = list
								.iterator(); iterator.hasNext();) {
							FakeMyOrderForm fakeMyOrderForm = (FakeMyOrderForm) iterator
									.next();
							currentArrayList.add(fakeMyOrderForm);
						}
						Li.printlnLog("isVisible" + isVisible);
						if (isVisible) {
							iniUI(context);
						}
						if (tempArrayList.size() != 0) {
							// tempArrayList�ĳ��Ȳ�Ϊ0��֤���и���
							compareOrderList();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case ConfigurationFiles.ORDER_STATE_MESSAGE:
				try {
					String resultState = (String) msg.obj;
					int state = Integer.parseInt(resultState);
					if (state == ConfigurationFiles.ORDER_STATE_CHANGE) {
						// isChanged = ConfigurationFiles.ORDER_STATE_CHANGE;
						Toast.makeText(context, "����״̬�����仯��", Toast.LENGTH_SHORT)
								.show();
						// ״̬�����仯����
						saveCurrentOrder();
						// VibratorUtil.Vibrate((Activity)context, 1000);
						// //��100ms
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case ConfigurationFiles.RECEIVE_FOOD_MESSAGE:
				if (result.equals(ConfigurationFiles.RECEIVE_FOOD_SUCCEED)) {
					Toast.makeText(context, "�ύ�ɹ�,�뵽��ʷ�����鿴��",
							Toast.LENGTH_SHORT).show();
					Li.printlnLog("orderNum---after receive: "
							+ msg.arg1);
					L.fakeMyInfo
							.setOrderNum(L.fakeMyInfo.getOrderNum()
									+ msg.arg1);
					Le.iniUI();
					getOrderList();
				} else {
					Toast.makeText(context, "�ύʧ�ܣ�������ύ��", Toast.LENGTH_SHORT)
							.show();
				}
				break;
			case ConfigurationFiles.RESUBMIT_ORDER_MESSAGE:
				if (result.equals(ConfigurationFiles.MODIFY_SUCCEED)) {
					Toast.makeText(context, "�ύ�ɹ���", Toast.LENGTH_SHORT).show();
					getOrderList();
				} else {
					Toast.makeText(context, "�ύʧ�ܣ�������ύ��", Toast.LENGTH_SHORT)
							.show();
				}
				break;
			case ConfigurationFiles.NO_INTERNET:
				Li.printlnLog("result:" + result);
				if (result.equals(ConfigurationFiles.GET_INFORMATION_FAIL)
						|| result.equals("[]")) {
					timeFlag = 0;
					timer.cancel();
				} else {
					unlogin.setVisibility(RelativeLayout.GONE);
					emptyOrderList.setVisibility(RelativeLayout.GONE);
					orderScroll.setVisibility(ScrollView.GONE);
					noNetworkLayout.setVisibility(LinearLayout.VISIBLE);
				}
				break;
			case ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE:
				Li.exit();
				Li.getExitDialog(context).show();
				break;

			case ConfigurationFiles.DELETE_ORDER_MESSAGE:
				if (result.equals(ConfigurationFiles.DELETE_ORDER_SUCCEED)) {
					Toast.makeText(context, "ɾ���ɹ�!", Toast.LENGTH_SHORT).show();
					getOrderList();
				}
				break;
			default:
				break;
			}
			if (mProgressDialog.isShowing())
				mProgressDialog.dismiss();
		};
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.order, null);
		orderScroll = (ScrollView) view.findViewById(R.id.order_scroll);
		noNetworkLayout = (LinearLayout) view
				.findViewById(R.id.no_network_layout);
		orderContainer = (LinearLayout) view.findViewById(R.id.order_container);
		emptyOrderList = (LinearLayout) view
				.findViewById(R.id.empty_order_list);
		unlogin = (LinearLayout) view.findViewById(R.id.unlogin_order);
		context = getActivity();
		mProgressDialog = new MPr(context);
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		
		if (isVisibleToUser) {
			isVisible = true;
			if (Sl.loginFlag) {
				if (currentArrayList.size() != 0) {
					mProgressDialog.show("������...");
					orderContainer.removeAllViews();
					unlogin.setVisibility(RelativeLayout.GONE);
					noNetworkLayout.setVisibility(LinearLayout.GONE);
					emptyOrderList.setVisibility(RelativeLayout.GONE);
					V.orderBtn.setBackgroundResource(R.drawable.orders_noticed_normal);
					getOrderList();
				} else {
					orderContainer.removeAllViews();
					unlogin.setVisibility(RelativeLayout.GONE);
					emptyOrderList.setVisibility(RelativeLayout.VISIBLE);
					orderScroll.setVisibility(ScrollView.GONE);
					noNetworkLayout.setVisibility(LinearLayout.GONE);
					V.orderBtn.setBackgroundResource(R.drawable.orders_actived);
				}
			} else {
				orderContainer.removeAllViews();
				unlogin.setVisibility(RelativeLayout.VISIBLE);
				orderScroll.setVisibility(ScrollView.GONE);
				emptyOrderList.setVisibility(RelativeLayout.GONE);
				noNetworkLayout.setVisibility(LinearLayout.GONE);
			}
		} else {
			// ���ɼ���
			isVisible = false;
		}
	}

	public static void iniUI(final Context context) {
		orderContainer.removeAllViews();
		SOr singleOrderView;
		List<FakeMyOrderInfo> orderInfoList;
		SO headView;
		for (int j = currentArrayList.size() - 1; j >= 0; j--) {
			FakeMyOrderForm fakeMyOrderForm = (FakeMyOrderForm) currentArrayList
					.get(j);
			headView = new SO(context);
			headView.setAddress(fakeMyOrderForm.getAddress());
			headView.setRemark(fakeMyOrderForm.getNotice());
			headView.setOrderTime(Li.changeDateToString(fakeMyOrderForm
					.getOrdered_date()));
			orderContainer.addView(headView);
			// ����Ӷ�����ͼ
			orderInfoList = fakeMyOrderForm.getOrderInfoList();
			for (Iterator<FakeMyOrderInfo> iterator2 = orderInfoList.iterator(); iterator2
					.hasNext();) {
				FakeMyOrderInfo fakeMyOrderInfo = (FakeMyOrderInfo) iterator2
						.next();
				singleOrderView = new SOr(context);
				singleOrderView.setFoodName(fakeMyOrderInfo.getFoodName());
				singleOrderView.setFoodCount(fakeMyOrderInfo.getAmount() + "");
				singleOrderView.setFoodCost(fakeMyOrderInfo.getPrice() + "");
				singleOrderView.getState().setText(
						getState(fakeMyOrderInfo.getState()));
				setColor(context, singleOrderView.getState(),
						fakeMyOrderInfo.getState());
				singleOrderView.setRemark("��ע��"
						+ (fakeMyOrderInfo.getNotice().equals("") ? "��"
								: fakeMyOrderInfo.getNotice()));
				Fi.initImageBackground(fakeMyOrderInfo.getPhoto(),
						singleOrderView.getFoodLogo(), 2);
				if (fakeMyOrderInfo.getState() == 3) {
					// �Ͳ���
					final String foodName = fakeMyOrderInfo.getFoodName();
					final int foodId = fakeMyOrderInfo.getOrderID();
					final int currentFinishCount = fakeMyOrderInfo.getAmount();
					// Integer.parseInt(singleOrderView.getFoodCount());
					singleOrderView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog = getDialog(context, foodName
									+ "  �����ͣ����Ƿ��ѽ��գ�", foodId,
									currentFinishCount);
							dialog.show();
						}
					});
				} else if (fakeMyOrderInfo.getState() == 6) {
					// ����ʱ
					final String foodName = fakeMyOrderInfo.getFoodName();
					final int foodId = fakeMyOrderInfo.getOrderID();
					singleOrderView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog = getOverTimeDialog(context, "���Ķ�����"
									+ foodName
									+ "�����ڳ�ʱ��δ����ҽ��գ���ϵͳ��Ϊ��ʱ�����빺�����Ʒ���������ύ!",
									foodId);
							dialog.show();
						}
					});

				} else if (fakeMyOrderInfo.getState() == 5) {
					final int foodId = fakeMyOrderInfo.getOrderID();
					singleOrderView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog = getRefuseDialog(context, foodId);
							dialog.show();

						}
					});
				}
				orderContainer.addView(singleOrderView);
			}
		}

		Li.printlnLog("currentArrayList.size()---:"
				+ currentArrayList.size());
		if(!Li.isConn(context))
		{
			orderContainer.removeAllViews();
			emptyOrderList.setVisibility(LinearLayout.GONE);
			unlogin.setVisibility(LinearLayout.GONE);
			orderScroll.setVisibility(ScrollView.GONE);
			noNetworkLayout.setVisibility(LinearLayout.VISIBLE);
		}else if (currentArrayList.size() == 0) {
			unlogin.setVisibility(RelativeLayout.GONE);
			emptyOrderList.setVisibility(RelativeLayout.VISIBLE);
			noNetworkLayout.setVisibility(LinearLayout.GONE);
			V.orderBtn
					.setBackgroundResource(R.drawable.orders_actived);
			orderScroll.setVisibility(ScrollView.GONE);
		} else if(currentArrayList.size() != 0){
			unlogin.setVisibility(RelativeLayout.GONE);
			emptyOrderList.setVisibility(RelativeLayout.GONE);
			noNetworkLayout.setVisibility(LinearLayout.GONE);
			orderScroll.setVisibility(ScrollView.VISIBLE);
			V.orderBtn.setBackgroundResource(R.drawable.orders_noticed_actived);
		}
		
	}

	public static void setColor(Context context, TextView textView, int state) {
		switch (state) {
		case 1:
			textView.setTextColor(context.getResources().getColor(
					R.color.waiting));
			break;
		case 2:
			textView.setTextColor(context.getResources().getColor(
					R.color.cooking));
			break;
		case 3:
			textView.setTextColor(context.getResources().getColor(R.color.send));
			break;
		case 4:
			textView.setTextColor(context.getResources().getColor(
					R.color.finish));
			break;
		case 5:
			textView.setTextColor(context.getResources().getColor(
					R.color.refuse));
			break;
		case 6:
			textView.setTextColor(context.getResources().getColor(
					R.color.refuse));
			break;
		}
	}

	// ����ʱ��
	public static void newTimer() {
		timer = new Timer();
		Li.printlnLog("��ʼʱ�ӣ�");
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				refreshTime++;
				Li.printlnLog("ʱ�䣺" + refreshTime);
				// TODO Auto-generated method stub
				Li.printlnLog("ִ�л�ȡ���ݲ���");
				refreshData();
			}
		};
		timer.schedule(task, 1000, 10000);
	}

	private static void refreshData() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Li.printlnLog("ˢ�¶�������....");
				if (currentArrayList.size() == 0 || tempArrayList.size() != 0) {
					String getOrderInformationURL = "";
					Message message = Message.obtain();
					Li.printlnLog("��ȡ������Ϣ...");
					getOrderInformationURL = ConfigurationFiles.HTTP_LIST_PATH
							+ Li
									.simpleEncryptParm(L.loginUserPhoneNum);
					String listString = "";
					listString = Ht.SendHttpClientPost(
							getOrderInformationURL, null, "utf-8", context);
					Li.printlnLog("listString:" + listString);
					if (listString.equals("")
							|| listString.equals(ConfigurationFiles.HTTP_ERROR)) {
						// ������ʴ���
						message.what = ConfigurationFiles.NO_INTERNET;
						message.obj = ConfigurationFiles.GET_INFORMATION_FAIL;
					} else {
						if (listString
								.equals(ConfigurationFiles.OTHER_PLACE_LOGIN)) {
							message.what = ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE;
						} else {
							message.what = ConfigurationFiles.ORDER_LIST_MESSAGE;
							message.obj = listString;
						}

					}
					handler.sendMessage(message);

				} else {
					Li.printlnLog("��ȡ����״̬...");
					String getOrderStateURL = "";
					Message message = Message.obtain();
					getOrderStateURL = ConfigurationFiles.HTTP_CHECK_ORDER_STATE
							+ Li
									.simpleEncryptParm(L.loginUserPhoneNum);
					String listString = Ht.SendHttpClientPost(
							getOrderStateURL, null, "utf-8", context);
					Li.printlnLog("listString:" + listString);
					if (listString == null || listString.equals("")
							|| listString.equals(ConfigurationFiles.HTTP_ERROR)
							|| listString.equals("[]")) {
						// ������ʴ���
						message.what = ConfigurationFiles.NO_INTERNET;
						message.obj = "";
					} else {
						if (listString
								.equals(ConfigurationFiles.OTHER_PLACE_LOGIN)) {
							message.what = ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE;
						} else {
							message.what = ConfigurationFiles.ORDER_STATE_MESSAGE;
							message.obj = listString;
						}

					}
					handler.sendMessage(message);
				}
			}
		};
		new Thread(runnable).start();
	}

	public static void loginSecceed() {
		// previousArrayList,currentArrayList,tempArrayList;
		tempArrayList = new ArrayList<FakeMyOrderInfo>();
		currentArrayList = new ArrayList<FakeMyOrderForm>();
		newTimer();
	}

	public static void getOrderList() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String getOrderInformationURL = "";
				Message message = Message.obtain();
				Li.printlnLog("��ȡ������Ϣ...");
				getOrderInformationURL = ConfigurationFiles.HTTP_LIST_PATH
						+ Li
								.simpleEncryptParm(L.loginUserPhoneNum);
				String listString = Ht.SendHttpClientPost(
						getOrderInformationURL, null, "utf-8", context);
				Li.printlnLog("listString:" + listString);
				if (listString.equals("")
						|| listString.equals(ConfigurationFiles.HTTP_ERROR)
						|| listString.equals("[]")) {
					// ������ʴ���
					message.what = ConfigurationFiles.NO_INTERNET;
					message.obj = ConfigurationFiles.GET_INFORMATION_FAIL;
				} else {
					if (listString.equals(ConfigurationFiles.OTHER_PLACE_LOGIN)) {
						message.what = ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE;
					} else {
						message.what = ConfigurationFiles.ORDER_LIST_MESSAGE;
						message.obj = listString;
					}

				}
				handler.sendMessage(message);
			}
		};
		new Thread(runnable).start();
	}

	public static void compareOrderList() {
		for (Iterator<FakeMyOrderForm> iterator = currentArrayList.iterator(); iterator
				.hasNext();) {
			FakeMyOrderForm fakeMyOrderForm = (FakeMyOrderForm) iterator.next();
			List<FakeMyOrderInfo> fakeMyOrderInfoList = fakeMyOrderForm
					.getOrderInfoList();
			foodCount = 0;
			for (Iterator<FakeMyOrderInfo> iterator2 = fakeMyOrderInfoList
					.iterator(); iterator2.hasNext();) {
				FakeMyOrderInfo newfakeMyOrderInfo = (FakeMyOrderInfo) iterator2
						.next();
				for (int i = tempArrayList.size() - 1; i >= 0; i--) {
					// �ȽϺ���
					notification(tempArrayList.get(i), newfakeMyOrderInfo);
				}
			}
		}
		stateHashMap = Li.getStateData(context);
		// ���֪ͨ��״̬�Ƿ��
		if (stateHashMap.get(ConfigurationFiles.NOTIFICATION_STATE) == 1) {
			if (foodCount == 1) {
				// ֻ��һ������״̬�����仯
				dealStateChange(tempOldFakeMyOrderInfo, tempNewFakeMyOrderInfo);
			} else if (foodCount > 1) {
				// �������״̬�����仯
				dealStateChange(foodCount);
			}
		} else {
			// ����û��֪ͨ�����𶯺����巽ʽ
			Li.selectReminder(tempNewFakeMyOrderInfo.getState(),
					stateHashMap, context);
		}

		tempArrayList.clear();
	}

	// ����״̬�ı����Ҫ���浱ǰ������Ϣ
	public static void saveCurrentOrder() {
		for (Iterator<FakeMyOrderForm> iterator = currentArrayList.iterator(); iterator
				.hasNext();) {
			FakeMyOrderForm fakeMyOrderForm = (FakeMyOrderForm) iterator.next();
			List<FakeMyOrderInfo> fakeMyOrderInfoList = fakeMyOrderForm
					.getOrderInfoList();
			for (Iterator<FakeMyOrderInfo> iterator2 = fakeMyOrderInfoList
					.iterator(); iterator2.hasNext();) {
				FakeMyOrderInfo fakeMyOrderInfo = (FakeMyOrderInfo) iterator2
						.next();
				tempArrayList.add(fakeMyOrderInfo);
			}

		}
	}

	public static void notification(FakeMyOrderInfo oldFakeMyOrderInfo,
			FakeMyOrderInfo newFakeMyOrderInfo) {
		if (oldFakeMyOrderInfo.getOrderID() == newFakeMyOrderInfo.getOrderID()
				&& oldFakeMyOrderInfo.getFoodID() == newFakeMyOrderInfo
						.getFoodID()) {
			String log = "";
			log = "�õ�һ����ͬ��ʳ�" + oldFakeMyOrderInfo.getFoodName();
			log = log + "\n��״̬��" + getState(oldFakeMyOrderInfo.getState())
					+ ",��״̬��" + getState(newFakeMyOrderInfo.getState());
			if (oldFakeMyOrderInfo.getState() != newFakeMyOrderInfo.getState()) {
				// ״̬�����仯
				tempOldFakeMyOrderInfo = oldFakeMyOrderInfo;
				tempNewFakeMyOrderInfo = newFakeMyOrderInfo;
				++foodCount;
			} else {
				// ״̬û�з����仯
				Li.printlnLog(log);
			}
		}
	}

	public static String getState(int state) {
		String stateString = "δ֪";
		switch (state) {
		case 1:
			stateString = "������";
			break;
		case 2:
			stateString = "������";
			break;
		case 3:
			stateString = "�Ͳ���";
			break;
		case 4:
			stateString = "�����";

			break;
		case 5:
			stateString = "�޷����";
			break;
		case 6:
			stateString = "����ʱ";
			break;
		}
		return stateString;
	}

	/*
	 * ֻ��һ��״̬�仯��֪ͨ��
	 */
	@SuppressWarnings("deprecation")
	public static void dealStateChange(FakeMyOrderInfo oldFakeMyOrderInfo,
			FakeMyOrderInfo newFakeMyOrderInfo) {
		Notification notification;// ״̬��֪ͨ
		RemoteViews notificationViews;// ״̬��֪ͨ��ʾ��view
		notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notification = new Notification();
		notification.icon = R.drawable.ic_launcher;// ����֪ͨ��Ϣ��ͼ��
		notification.tickerText = "���Ķ������µ���Ϣ����ע����գ�";// ����֪ͨ��Ϣ�ı���
		notificationViews = new RemoteViews(context.getPackageName(),
				R.layout.state_notification);

		notificationViews.setImageViewBitmap(
				R.id.food_icon,
				new BitmapDrawable(Fi.ALBUM_PATH
						+ Fi.getImagePathByImageURL(newFakeMyOrderInfo
								.getPhoto())).getBitmap());
		notificationViews.setTextViewText(R.id.food_title,
				newFakeMyOrderInfo.getFoodName());
		notificationViews.setTextViewText(R.id.time,
				Li.changeDateToTime(Calendar.getInstance().getTime()));
		notificationViews.setTextViewText(R.id.message,
				getNotificationMessage(newFakeMyOrderInfo.getState()));

		notification.contentView = notificationViews;
		setNotificationStyle(notification, newFakeMyOrderInfo.getState());// ������ʾ����
		Intent openintent = new Intent();// ������ת�¼�
		openintent.setClass(context, Sl.class);
		Sl.getViewPager().setCurrentItem(3);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				openintent, 0);
		notification.contentIntent = contentIntent;
		notificationManager.notify(0, notification);
		wakeUpScreen();// ������Ļ
	}

	// �ж������״̬�����仯��֪ͨ��
	public static void dealStateChange(int count) {
		Notification notification;// ״̬��֪ͨ
		RemoteViews notificationViews;// ״̬��֪ͨ��ʾ��view
		notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notification = new Notification();
		notification.icon = R.drawable.ic_launcher;// ����֪ͨ��Ϣ��ͼ��
		notification.tickerText = "���Ķ������µ���Ϣ����ע����գ�";// ����֪ͨ��Ϣ�ı���
		notificationViews = new RemoteViews(context.getPackageName(),
				R.layout.more_state_notification);
		// ����ʱ��
		notificationViews.setTextViewText(R.id.time,
				Li.changeDateToTime(Calendar.getInstance().getTime()));
		notificationViews.setTextViewText(R.id.content, "����" + count
				+ "������״̬�����ı䣬����鿴�ҵĶ������˽����顣");
		notification.contentView = notificationViews;
		setNotificationStyle(notification, 3);// ������ʾ����
		Intent openintent = new Intent();// ������ת�¼�
		openintent.setClass(context, Sl.class);
		Sl.getViewPager().setCurrentItem(3);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				openintent, 0);
		notification.contentIntent = contentIntent;
		notificationManager.notify(0, notification);
		wakeUpScreen();// ������Ļ
	}

	public static String getNotificationMessage(int newState) {
		String notificationString = "";
		switch (newState) {
		case 1:
			notificationString = "ʳƷ������";
			break;
		case 2:
			notificationString = "�������У�";
			break;
		case 3:
			notificationString = "�Ͳ��У�";
			break;
		case 4:
			notificationString = "����ɣ�";

			break;
		case 5:
			notificationString = "����ĳ��ԭ�򣬵��Ŀǰ�޷���ɸö�����ϵͳ������ɾ��������������ѯ��ҡ�";
			break;
		case 6:
			notificationString = "����ʱ,�����������ύ������";
			break;
		}
		return notificationString;

	}

	public static int setMessageColor(int newState) {
		int notificationColor = R.color.waiting;
		switch (newState) {
		case 1:
			notificationColor = R.color.waiting;
			break;
		case 2:
			notificationColor = R.color.cooking;
			break;
		case 3:
			notificationColor = R.color.send;
			break;
		case 4:
			notificationColor = R.color.finish;

			break;
		case 5:
			notificationColor = R.color.refuse;
			break;
		case 6:
			notificationColor = R.color.refuse;
			break;
		}
		return notificationColor;

	}

	// ����֪ͨ��֪ͨ�ķ�ʽ
	private static void setNotificationStyle(Notification notification,
			int state) {
		switch (state) {
		case 1:
			// ���Ͳ�
			break;
		case 2:
			// ������
			selectStyle(notification,
					stateHashMap.get(ConfigurationFiles.COOKING));
			break;
		case 3:
			// �Ͳ���
			selectStyle(notification,
					stateHashMap.get(ConfigurationFiles.SENDING));
			break;
		case 4:
			// ���
			break;
		case 5:
			// �ܾ�
			selectStyle(notification,
					stateHashMap.get(ConfigurationFiles.REFUSE));
			break;
		case 6:
			// ����ʱ
			selectStyle(notification,
					stateHashMap.get(ConfigurationFiles.OVERTIME));
			break;
		}
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("InlinedApi")
	public static void selectStyle(Notification notification, int style) {
		notification.defaults = Notification.DEFAULT_LIGHTS;
		notification.defaults = Notification.FLAG_HIGH_PRIORITY;
		switch (style) {
		case 0:
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			break;
		case 1:
			notification.defaults = Notification.DEFAULT_SOUND;
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			break;
		case 2:
			notification.defaults = Notification.DEFAULT_VIBRATE;
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			break;
		case 3:
			notification.defaults = Notification.DEFAULT_ALL;// ��������
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			break;
		default:
			break;
		}
		/*
		 * if(style==) { notification.defaults=Notification.DEFAULT_ALL;//��������
		 * notification.flags = Notification.FLAG_AUTO_CANCEL; }else {
		 * notification.defaults = Notification.DEFAULT_VIBRATE;
		 * notification.defaults = Notification.DEFAULT_LIGHTS;
		 * notification.flags = Notification.FLAG_AUTO_CANCEL; }
		 */
	}

	private static Dialog getDialog(final Context context,
			final String contentText, final int foodOrderId,
			final int currentFinishCount) {
		final Dialog dialog = new Dialog(context, R.style.myDialog);
		dialog.setContentView(R.layout.receive_food_dialog);
		ImageView closeBtn = (ImageView) dialog.findViewById(R.id.close_btn);
		TextView content = (TextView) dialog.findViewById(R.id.content);
		TextView noReceiveFoodBtn = (TextView) dialog
				.findViewById(R.id.no_receive_food_btn);
		TextView receiveBtn = (TextView) dialog.findViewById(R.id.receive_btn);
		content.setText(contentText);
		noReceiveFoodBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		closeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		receiveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mProgressDialog.show("ȷ���ջ�...");
				Li.printlnLog("orderNum---on click: "
						+ currentFinishCount);
				receiveFood(foodOrderId, currentFinishCount);
				dialog.dismiss();
			}
		});
		return dialog;
	}

	public static void receiveFood(final int orderDetailsID,
			final int currentFinishCount) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String getOrderInformationURL = "";
				Message message = Message.obtain();
				getOrderInformationURL = ConfigurationFiles.RECEIVE_FOOD_URL;
				Map<String, String> map = new HashMap<String, String>();
				map.put("phoneNumber", L.loginUserPhoneNum);
				map.put("orderDetailsID", orderDetailsID + "");
				String receiveString = Ht.SendHttpClientPost(
						getOrderInformationURL, map, "utf-8", context);
				if (receiveString.equals("")
						|| receiveString.equals(ConfigurationFiles.HTTP_ERROR)
						|| receiveString.equals("[]")) {
					// ������ʴ���
					message.what = ConfigurationFiles.NO_INTERNET;
					message.obj = "";
				} else {
					if (receiveString
							.equals(ConfigurationFiles.OTHER_PLACE_LOGIN)) {
						message.what = ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE;
					} else {
						message.what = ConfigurationFiles.RECEIVE_FOOD_MESSAGE;
						message.obj = receiveString;
						message.arg1 = currentFinishCount;
						Li.printlnLog("orderNum---is receiving: "
								+ currentFinishCount);
					}

				}
				handler.sendMessage(message);
			}
		};
		new Thread(runnable).start();
	}

	private static Dialog getOverTimeDialog(final Context context,
			final String contentText, final int foodOrderId) {
		final Dialog dialog = new Dialog(context, R.style.myDialog);
		dialog.setContentView(R.layout.overtime_dialog);
		ImageView closeBtn = (ImageView) dialog.findViewById(R.id.close_btn);
		TextView content = (TextView) dialog.findViewById(R.id.content);
		TextView giveUpButton = (TextView) dialog
				.findViewById(R.id.give_up_button);
		TextView submitBtn = (TextView) dialog.findViewById(R.id.submit_btn);
		content.setText(contentText);
		giveUpButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				deleteFood(foodOrderId);
				dialog.dismiss();
			}
		});
		closeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		submitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mProgressDialog.show("�ύ������...");
				reSubmitFood(foodOrderId);
				dialog.dismiss();
			}
		});

		return dialog;
	}

	public static void reSubmitFood(final int orderDetailsID) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String getOrderInformationURL = "";
				Message message = Message.obtain();
				getOrderInformationURL = ConfigurationFiles.RESUBMIT_ORDER_URL;
				Map<String, String> map = new HashMap<String, String>();
				map.put("phoneNumber", L.loginUserPhoneNum);
				map.put("orderDetailsID", orderDetailsID + "");
				String resubmitString = Ht.SendHttpClientPost(
						getOrderInformationURL, map, "utf-8", context);
				if (resubmitString.equals(null) || resubmitString.equals("")
						|| resubmitString.equals(ConfigurationFiles.HTTP_ERROR)
						|| resubmitString.equals("[]")) {
					// ������ʴ���
					message.what = ConfigurationFiles.NO_INTERNET;
					message.obj = "";
				} else {
					if (resubmitString
							.equals(ConfigurationFiles.OTHER_PLACE_LOGIN)) {
						message.what = ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE;
					} else {
						message.what = ConfigurationFiles.RESUBMIT_ORDER_MESSAGE;
						message.obj = resubmitString;
					}
				}
				handler.sendMessage(message);
			}
		};
		new Thread(runnable).start();
	}

	public static void wakeUpScreen() {
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		@SuppressWarnings("deprecation")
		WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
				| PowerManager.ACQUIRE_CAUSES_WAKEUP, "Gank");
		wl.acquire();
	}

	public static Dialog getRefuseDialog(final Context context,
			final int orderDetailsID) {
		final Dialog dialog = new Dialog(context, R.style.myDialog);
		dialog.setContentView(R.layout.delete_refuse_order_dialog);
		dialog.setCanceledOnTouchOutside(false);
		TextView delete = (TextView) dialog.findViewById(R.id.delete);
		ImageView dialogCloseBtn = (ImageView) dialog
				.findViewById(R.id.dialog_close_btn);

		dialogCloseBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				deleteFood(orderDetailsID);
				dialog.dismiss();
			}
		});
		return dialog;
	}

	public static void deleteFood(final int orderDetailsID) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {

				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mProgressDialog.show("�����С�");
					}
				});

				String deleteOrderInformationURL = "";
				Message message = Message.obtain();
				deleteOrderInformationURL = ConfigurationFiles.DELETE_ORDER;
				// orderDetailsID= &phoneNumber=
				Map<String, String> map = new HashMap<String, String>();
				map.put("phoneNumber", L.loginUserPhoneNum);
				map.put("orderDetailsID", orderDetailsID + "");
				String resubmitString = Ht.SendHttpClientPost(
						deleteOrderInformationURL, map, "utf-8", context);
				if (resubmitString.equals("")
						|| resubmitString.equals(ConfigurationFiles.HTTP_ERROR)) {
					message.what = ConfigurationFiles.HTTP_ERROR_MESSAGE;
				} else {
					message.what = ConfigurationFiles.DELETE_ORDER_MESSAGE;
					message.obj = resubmitString;
				}
				handler.sendMessage(message);
			}
		};
		new Thread(runnable).start();
	}
}
