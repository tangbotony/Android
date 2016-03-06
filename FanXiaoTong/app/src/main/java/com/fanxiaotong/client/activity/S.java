package com.fanxiaotong.client.activity;

import java.io.IOException;
import java.util.HashMap;

import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.utils.Fi;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class S extends Activity {
	private TextView backBtn,cooking,send,refuse,overtime,openCloeNotificationLabel;
	private RelativeLayout clearCache,cookingConter,sendConter,refuseConter,overtimeConter;
	private ToggleButton openCloseNotification;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.setting);
		backBtn =(TextView)findViewById(R.id.back_btn);
		cooking =(TextView)findViewById(R.id.cooking);
		send =(TextView)findViewById(R.id.send);
		refuse =(TextView)findViewById(R.id.refuse);
		overtime =(TextView)findViewById(R.id.overtime);

		clearCache =(RelativeLayout)findViewById(R.id.clear_cache);
		cookingConter =(RelativeLayout)findViewById(R.id.cookingConter);
		sendConter =(RelativeLayout)findViewById(R.id.sendConter);
		refuseConter =(RelativeLayout)findViewById(R.id.refuseConter);
		overtimeConter =(RelativeLayout)findViewById(R.id.overtimeConter);
		openCloeNotificationLabel  = (TextView)findViewById(R.id.open_cloe_notification_label);
		openCloseNotification = (ToggleButton)findViewById(R.id.open_close_notification);

		iniUI();


		openCloseNotification.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					Li.createRememberFile(ConfigurationFiles.NOTIFICATION_STATE, 1, S.this);
					iniUI();
				}else
				{
					Li.createRememberFile(ConfigurationFiles.NOTIFICATION_STATE, 0, S.this);
					iniUI();
				}
			}
		});
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		clearCache.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDialog(S.this).show();
			}
		});

		cookingConter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getChangeNOtifictionDialog(S.this, ConfigurationFiles.COOKING).show();
			}
		});

		sendConter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getChangeNOtifictionDialog(S.this, ConfigurationFiles.SENDING).show();
			}
		});

		refuseConter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getChangeNOtifictionDialog(S.this, ConfigurationFiles.REFUSE).show();
			}
		});

		overtimeConter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getChangeNOtifictionDialog(S.this, ConfigurationFiles.OVERTIME).show();
			}
		});

	}
	public void iniUI()
	{
		HashMap<String, Integer> stateMap = Li.getStateData(S.this);
		cooking.setText(getstateTypeString(stateMap.get(ConfigurationFiles.COOKING)));
		overtime.setText(getstateTypeString(stateMap.get(ConfigurationFiles.OVERTIME)));
		refuse.setText(getstateTypeString(stateMap.get(ConfigurationFiles.REFUSE)));
		send.setText(getstateTypeString(stateMap.get(ConfigurationFiles.SENDING)));
		if(stateMap.get(ConfigurationFiles.NOTIFICATION_STATE)==0)
		{
			openCloeNotificationLabel.setText("通知栏提示(关闭)");
			openCloseNotification.setChecked(false);
		}else
		{
			openCloeNotificationLabel.setText("通知栏提示(打开)");
			openCloseNotification.setChecked(true);
		}
	}
	public String getstateTypeString(int state)
	{
		String stateTypeString = "";
		switch (state) {
		case 0:
			stateTypeString ="无操作";
			break;

		case 1:
			stateTypeString ="响铃";
			break;
		case 2:
			stateTypeString ="震动";
			break;
		case 3:
			stateTypeString ="响铃震动";
			break;
		}
		return stateTypeString;
	}
	private Dialog getDialog(final Context context) {
		final Dialog dialog = new Dialog(context, R.style.myDialog);
		dialog.setContentView(R.layout.clear_cache_dialog);
		ImageView closeBtn = (ImageView)dialog.findViewById(R.id.close_btn);
		TextView giveUpButton = (TextView)dialog.findViewById(R.id.give_up_button);
		TextView clear = (TextView)dialog.findViewById(R.id.clear);
		giveUpButton.setOnClickListener(new OnClickListener() {
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

		clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Li.deleteFolderFile(Fi.ALBUM_PATH, true);
					dialog.dismiss();
					Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		return dialog;
	}



	private Dialog getChangeNOtifictionDialog(final Context context,final String stateLabel) {
		final Dialog dialog = new Dialog(context, R.style.myDialog);
		dialog.setContentView(R.layout.notifition_style_dialog);
		ImageView shakeDiabolo = (ImageView)dialog.findViewById(R.id.shake_diabolo);
		ImageView shake = (ImageView)dialog.findViewById(R.id.shake);
		ImageView diabolo = (ImageView)dialog.findViewById(R.id.diabolo);
		ImageView no = (ImageView)dialog.findViewById(R.id.no);
		shakeDiabolo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Li.createRememberFile(stateLabel, 3, context);
				iniUI();
				dialog.dismiss();
			}
		});

		shake.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Li.createRememberFile(stateLabel, 2, context);
				iniUI();
				dialog.dismiss();

			}
		});
		diabolo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Li.createRememberFile(stateLabel, 1, context);
				iniUI();
				dialog.dismiss();

			}
		});

		no.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Li.createRememberFile(stateLabel, 0, context);
				iniUI();
				dialog.dismiss();
			}
		});
		return dialog;
	}
}
