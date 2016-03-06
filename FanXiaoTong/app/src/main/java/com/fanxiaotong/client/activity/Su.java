package com.fanxiaotong.client.activity;


import java.util.HashMap;
import java.util.Map;

import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.fragment.Ca;
import com.fanxiaotong.client.fragment.O;
import com.fanxiaotong.client.mybean.Fa;
import com.fanxiaotong.client.utils.Car;
import com.fanxiaotong.client.utils.Ht;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.utils.Ob;
import com.fanxiaotong.client.widget.MPr;
import com.fanxiaotong.client.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Su extends Activity {
	private Button submitBtn;
	private EditText address,phoneNum,notice;
	private TextView backBtn;
	private static int submitFlag = 0;//submitFlag=0������û�Ŀǰû������Ĭ���Ͳ͵�ַ��submitFlag=1������û�Ŀǰ�Ѿ�����Ĭ���Ͳ͵�ַ
	private MPr mProgressDialog;
	private Dialog dialog;
	private 
	@SuppressLint("HandlerLeak")
	Handler submitOrderHander = new Handler()
	{
		public void handleMessage(Message msg) 
		{
			int flag = msg.what;
			if(flag==ConfigurationFiles.SUBMIT_ORDWER_MESSAGE)
			{
				String result = (String)msg.obj;
				Li.printlnLog("�����룺"+result);
				if(result.equals(ConfigurationFiles.HTTP_ERROR))
				{
					//HttpClient�������벻��200�������
					Toast.makeText(Su.this, "�������Ӵ����������ύ��", Toast.LENGTH_SHORT).show();
				}else if(result.equals(ConfigurationFiles.SUBMIT_ORDER_SUCCEED))
				{
					Li.printlnLog("�ύ�������ص�result:"+result);
					//����������ȷ�������ύ������
					dialog.dismiss();
					//�ύ�ɹ�����Ҫ�ж�ʱ���Ƿ���ֹ
					if(O.timeFlag==0)
					{
						O.newTimer();
						O.timeFlag = 1;
					}
					if(L.fakeMyInfo.getAddress().equals(""))
					{
						L.fakeMyInfo.setAddress(address.getText().toString());
					}
					Toast.makeText(Su.this, "�����ύ�ɹ����뵽�ҵĶ����в鿴��", Toast.LENGTH_SHORT).show();
					Car cartDbHelper = new Car(Su.this);
					cartDbHelper.deleteByPhoneNum(L.fakeMyInfo.getPhoneNumber());
					Ca.submitCost.setVisibility(RelativeLayout.GONE);
					Ca.orderList.clear();
					Sl.getViewPager().setCurrentItem(3);
					
					finish();
				}else 
				{
					//����ʧ�ܵı�־
					Toast.makeText(Su.this, "������æ�������ԣ�",Toast.LENGTH_SHORT).show();
				}
			}else if(flag == ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE)
			{
				Li.exit();
				Li.getExitDialog(Su.this).show();
			}
			mProgressDialog.dismiss();
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.submit);
		//address,phoneNum,notice;
		address  = (EditText)findViewById(R.id.address);
		phoneNum = (EditText)findViewById(R.id.phone_num);
		notice = (EditText)findViewById(R.id.notice);
		submitBtn = (Button)findViewById(R.id.submit_btn);
		backBtn = (TextView)findViewById(R.id.back_btn);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		setDefaultValue();
		submitBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!address.getText().toString().equals(""))
				{
					if(!phoneNum.getText().toString().equals(""))
					{
						dialog = getDialog(Su.this);
						dialog.show();
					}else
					{
						Toast.makeText(Su.this, "�Ͳ͵绰����Ϊ�գ�", Toast.LENGTH_SHORT).show();
					}
				}else
				{
					Toast.makeText(Su.this, "�Ͳ͵�ַ����Ϊ�գ�", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	private Dialog getDialog(final Context context) {
		final Dialog dialog = new Dialog(context, R.style.myDialog);
		dialog.setContentView(R.layout.confirm_order_information_dialog);
		dialog.setCanceledOnTouchOutside(false);
		final TextView addressDialog = (TextView)dialog.findViewById(R.id.address);
		final TextView receivePhoneNum = (TextView)dialog.findViewById(R.id.receive_phone_num);
		final TextView remark = (TextView)dialog.findViewById(R.id.remark);
		ImageView clouseBtn = (ImageView)dialog.findViewById(R.id.dialog_close_btn);
		receivePhoneNum.setText(phoneNum.getText().toString());
		TextView confirm = (TextView)dialog.findViewById(R.id.confirm);
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mProgressDialog  = new MPr(context);
				mProgressDialog.show("�����ύ��...");
				Ca.fakeOrder.setAddress(address.getText().toString());
				Ca.fakeOrder.setPhoneNumber(phoneNum.getText().toString());
				Ca.fakeOrder.setNotice(remark.getText().toString());
				submitOrder(Ca.fakeOrder);
			}
		});
		String remarkString = notice.getText().toString();
		remark.setText(remarkString.equals("")?"��":remarkString);
		
		addressDialog.setText(address.getText().toString().equals("")?"��":address.getText().toString());
		
		clouseBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		return dialog;
	}
	public void setDefaultValue()
	{
		if(!L.fakeMyInfo.getAddress().equals(""))
		{
			submitFlag = 1;
			address.setText(L.fakeMyInfo.getAddress());
		}
		if(!L.fakeMyInfo.getPhoneNumber().equals(""))
		{
			phoneNum.setText(L.fakeMyInfo.getPhoneNumber());
		}
	}
	private void submitOrder(final Fa fakeOrder)
	{
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String orderJson = Ob.createJsonString(fakeOrder);
				if(!orderJson.equals(""))
				{
					Message orderMessage = Message.obtain();
					Map<String, String> map = new HashMap<String, String>();
					map.put("order", orderJson);
					map.put("flagBit", submitFlag+"");
					Li.printlnLog("flagBit:"+submitFlag);
					String result = Ht.SendHttpClientPost(ConfigurationFiles.HTTP_SUBMIT_ORDER_PATH+ Li.simpleEncryptParm(L.loginUserPhoneNum), map, "utf-8",Su.this);
					Li.printlnLog("�����ύ�����"+result);
					if(result.equals(ConfigurationFiles.OTHER_PLACE_LOGIN))
					{
						orderMessage.what =ConfigurationFiles.OTHER_PLACE_LOGIN_MESSAGE;
					}else
					{
						orderMessage.what = ConfigurationFiles.SUBMIT_ORDWER_MESSAGE;
						orderMessage.obj = result;
					}
					submitOrderHander.sendMessage(orderMessage);
				}else
				{
					//Toast.makeText(getActivity(), "�����ﳵΪ�գ��뵽�̵깺��ʳƷ��", Toast.LENGTH_SHORT).show();
				}
			}
		};
		new Thread(runnable).start();
	}
}