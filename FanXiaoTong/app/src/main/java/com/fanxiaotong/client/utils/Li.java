package com.fanxiaotong.client.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fanxiaotong.client.activity.L;
import com.fanxiaotong.client.activity.Sl;
import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.fragment.Ca;
import com.fanxiaotong.client.fragment.O;
import com.fanxiaotong.client.fragment.V;
import com.fanxiaotong.client.R;

public class Li {

	public Li() {
		// TODO Auto-generated constructor stub
	}

	public static int getScreenWidth(WindowManager windowManager) {
		DisplayMetrics metric = new DisplayMetrics();

		windowManager.getDefaultDisplay().getMetrics(metric);
		return metric.widthPixels; // 屏幕宽度（像素）
	}

	/**
	 * 返回日期 yy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String changeDateToString(Date date) {
		Li.printlnLog(date.toString());
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		StringBuffer buffer = new StringBuffer();
		buffer.append(calendar.get(Calendar.YEAR));
		buffer.append("-");
		buffer.append(oneToTwo(calendar.get(Calendar.MONTH)));
		buffer.append("-");
		buffer.append(oneToTwo(calendar.get(Calendar.DAY_OF_MONTH)));
		buffer.append(" ");
		buffer.append(oneToTwo(calendar.get(Calendar.HOUR_OF_DAY)));
		buffer.append(":");
		buffer.append(oneToTwo(calendar.get(Calendar.MINUTE)));
		buffer.append(":");
		buffer.append(oneToTwo(calendar.get(Calendar.SECOND)));
		return new String(buffer);
	}

	/**
	 * 返回时间 mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String changeDateToTime(Date date) {
		Li.printlnLog(date.toString());
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return (calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar
				.get(Calendar.MINUTE));
	}

	/**
	 * 返回日期 yy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String changeDateToStringAll(Date date) {
		Li.printlnLog(date.toString());
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		StringBuffer buffer = new StringBuffer();
		buffer.append(calendar.get(Calendar.YEAR));
		buffer.append("-");
		buffer.append(oneToTwo(calendar.get(Calendar.MONTH)));
		buffer.append("-");
		buffer.append(oneToTwo(calendar.get(Calendar.DAY_OF_MONTH)));
		buffer.append(" ");
		buffer.append(oneToTwo(calendar.get(Calendar.HOUR_OF_DAY)));
		buffer.append(":");
		buffer.append(oneToTwo(calendar.get(Calendar.MINUTE)));
		buffer.append(":");
		buffer.append(oneToTwo(calendar.get(Calendar.SECOND)));
		return new String(buffer);
	}

	private static String oneToTwo(int num) {
		if (num < 10)
			return "0" + num;
		else
			return "" + num;
	}

	/**
	 * 返回当前程序版本名
	 */
	public static int getAppVersionCode(Context context) {
		int versionCode = 0;
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			versionCode = packageInfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	public static void printlnLog(String log) {
		if (ConfigurationFiles.IS_DEBUGING) {
			System.out.println(log);
		}
	}

	/**
	 * 简单加密
	 * 
	 * @param str
	 * @return 密文
	 */
	public static String simpleEncrypt(String str) {
		char[] chars = str.toCharArray();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < chars.length - 1; i++) {
			buffer.append(chars[i] ^ ConfigurationFiles.FIX_KEY);
			buffer.append("-");
		}

		buffer.append(chars[chars.length - 1] ^ ConfigurationFiles.FIX_KEY);

		return new String(buffer);
	}
	/*
	 * @TangBo
	 * 2014.3.8 12:22
	 * 加密然后再加上"-"
	 */
	public static String simpleEncryptParm(String str)
	{
		if(str.equals(""))
		{
			return "";
		}
		else
		{
			return ConfigurationFiles.SPLIT_KEY+simpleEncrypt(str);
		}
		
	}
	
	
	
	
	

	/**
	 * 简单解密
	 * 
	 * @param str
	 * @return 明文
	 */
	public static String d(String str) {
		String[] chars = str.split(ConfigurationFiles.SPLIT_KEY);
		char[] charDatas = new char[chars.length];
		for (int i = 0; i < chars.length; i++) {
			charDatas[i] = (char) (Integer.parseInt(chars[i]) ^ ConfigurationFiles.FIX_KEY);
		}

		return new String(charDatas);
	}

	/**
	 * dp 转 px
	 * 
	 * @param context
	 * @param dpValue
	 * @return px值
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * px 转 dp
	 * 
	 * @param context
	 * @param pxValue
	 * @return dp值
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 通知栏设置： 值为0,代表既不响铃，也不震动 值为1，代表响铃 值为2，代表震动 值为3，代表震动加响铃
	 * notificationFlag=0代表关闭通知栏，notificationFlag=1代表打开通知栏
	 */
	public static HashMap<String, Integer> getStateData(Context context) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		SharedPreferences preferences = context
				.getSharedPreferences(
						ConfigurationFiles.SHAREDPREFERENCES_NAME,
						Context.MODE_PRIVATE);

		int cooking = preferences.getInt(ConfigurationFiles.COOKING, 3);
		int sending = preferences.getInt(ConfigurationFiles.SENDING, 3);
		int overTime = preferences.getInt(ConfigurationFiles.OVERTIME, 3);
		int refuse = preferences.getInt(ConfigurationFiles.REFUSE, 3);
		int notificationFlag = preferences.getInt(
				ConfigurationFiles.NOTIFICATION_STATE, 1);

		map.put(ConfigurationFiles.COOKING, cooking);
		map.put(ConfigurationFiles.SENDING, sending);
		map.put(ConfigurationFiles.OVERTIME, overTime);
		map.put(ConfigurationFiles.REFUSE, refuse);
		map.put(ConfigurationFiles.NOTIFICATION_STATE, notificationFlag);

		return map;

	}

	/**
	 * 值为0,代表既不响铃，也不震动 值为1，代表响铃 值为2，代表震动 值为3，代表震动加响铃
	 */
	public static void createRememberFile(String stateLabel, int type,
			Context context) {
		SharedPreferences preferences = context
				.getSharedPreferences(
						ConfigurationFiles.SHAREDPREFERENCES_NAME,
						Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt(stateLabel, type);
		editor.commit();
	}

	/**
	 * 删除指定目录下文件及目录
	 * 
	 * @param deleteThisPath
	 * @param filepath
	 * @return
	 */
	public static void deleteFolderFile(String filePath, boolean deleteThisPath)
			throws IOException {
		if (!TextUtils.isEmpty(filePath)) {
			File file = new File(filePath);

			if (file.isDirectory()) {// 处理目录
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFolderFile(files[i].getAbsolutePath(), true);
				}
			}
			if (deleteThisPath) {
				if (!file.isDirectory()) {// 如果是文件，删除
					file.delete();
				} else {// 目录
					if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
						file.delete();
					}
				}
			}
		}
	}

	public static void exit() {
		Ht.CookieContiner.remove("phoneNumber");// 清除Cookie
		L.loginUserPhoneNum = "";// 清除登陆号码
		Ca.foodOrderContainer.removeAllViews();
		Ca.submitCost.setVisibility(RelativeLayout.GONE);// 设置提交按钮不可见
		Ca.emptyCart.setVisibility(LinearLayout.GONE);
		Ca.unloginCart.setVisibility(LinearLayout.VISIBLE);
		Ca.orderList.clear();
		O.orderContainer.removeAllViews();
		O.unlogin.setVisibility(RelativeLayout.GONE);
		O.emptyOrderList.setVisibility(RelativeLayout.GONE);
		O.timer.cancel();// 退出时钟
		O.currentArrayList.clear();
		L.fakeMyInfo.setPhoneNumber("");
		V.setLoginImage(0);
		Sl.loginFlag = false;

		if (Sl.getmSlidingMenu().getLeftState()) {
			Sl.getmSlidingMenu().showLeftView();
		}
		Sl.getViewPager().setCurrentItem(1);
		Sl.getmSlidingMenu().setCanSliding(false, false);
	}

	public static Dialog getExitDialog(final Context context) {
		final Dialog dialog = new Dialog(context, R.style.myDialog);
		dialog.setContentView(R.layout.other_place_login_dialog);
		dialog.setCanceledOnTouchOutside(false);
		TextView confirm = (TextView) dialog.findViewById(R.id.confirm);
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, Sl.class);
				context.startActivity(intent);
				dialog.dismiss();
			}
		});
		return dialog;
	}

	/*
	 * 值为0,代表既不响铃，也不震动 值为1，代表响铃 值为2，代表震动 值为3，代表震动加响铃
	 */
	public static void setReminder(int type, Context context) {
		switch (type) {
		case 0:
			break;
		case 1:
			Ti.PlaySound(context);
			break;
		case 2:
			Vi.Vibrate((Activity) context, 2000);
			break;
		case 3:
			Ti.PlaySound(context);
			Vi.Vibrate((Activity) context, 2000);
			break;

		default:
			break;
		}
	}

	public static void selectReminder(int stateType,
			HashMap<String, Integer> stateMap, Context context) {
		switch (stateType) {
		case 1:
			// stateString="待接收";
			break;
		case 2:
			// stateString="做餐中";
			setReminder(stateMap.get(ConfigurationFiles.COOKING), context);
			break;
		case 3:
			// stateString="送餐中";
			setReminder(stateMap.get(ConfigurationFiles.SENDING), context);
			break;
		case 4:
			// stateString="已完成";
			break;
		case 5:
			// stateString="拒绝";
			setReminder(stateMap.get(ConfigurationFiles.REFUSE), context);
			break;
		case 6:
			// stateString="请求超时";
			setReminder(stateMap.get(ConfigurationFiles.OVERTIME), context);
			break;
		default:
			break;
		}
	}

	public static boolean isConn(Context context) {
		boolean bisConnFlag = false;
		ConnectivityManager conManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network = conManager.getActiveNetworkInfo();
		if (network != null) {
			bisConnFlag = conManager.getActiveNetworkInfo().isAvailable();
		}
		return bisConnFlag;
	}

	public static void GotoLogin(Context context) {
		Intent intent = new Intent(context, L.class);
		context.startActivity(intent);
	}

	public static void setHeadLogoBackground(ImageView imageView, String name) {
		if (name.equals("b_1_round")) {
			imageView.setBackgroundResource(R.drawable.b_1_round);
		} else if (name.equals("b_2_round")) {
			imageView.setBackgroundResource(R.drawable.b_2_round);
		} else if (name.equals("b_3_round")) {
			imageView.setBackgroundResource(R.drawable.b_3_round);
		} else if (name.equals("g_1_round")) {
			imageView.setBackgroundResource(R.drawable.g_1_round);
		} else if (name.equals("g_2_round")) {
			imageView.setBackgroundResource(R.drawable.g_2_round);
		} else if (name.equals("g_3_round")) {
			imageView.setBackgroundResource(R.drawable.g_3_round);
		} else {
			imageView.setBackgroundResource(R.drawable.b_1_round);
		}
	}

	public static void setHeadLogoSrc(ImageView imageView, String name) {
		if (name.equals("b_1_round")) {
			imageView.setImageResource(R.drawable.b_1_round);
		} else if (name.equals("b_2_round")) {
			imageView.setImageResource(R.drawable.b_2_round);
		} else if (name.equals("b_3_round")) {
			imageView.setImageResource(R.drawable.b_3_round);
		} else if (name.equals("g_1_round")) {
			imageView.setImageResource(R.drawable.g_1_round);
		} else if (name.equals("g_2_round")) {
			imageView.setImageResource(R.drawable.g_2_round);
		} else if (name.equals("g_3_round")) {
			imageView.setImageResource(R.drawable.g_3_round);
		} else {
			imageView.setImageResource(R.drawable.b_1_round);
		}
	}

	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
}