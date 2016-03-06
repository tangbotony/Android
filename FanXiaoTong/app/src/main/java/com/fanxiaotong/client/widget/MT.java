package com.fanxiaotong.client.widget;

import com.fanxiaotong.client.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * �Զ���Toast<br>
 * ��ԭToast�����ϼ򵥶�����һ����ʽ����δ��̫���ӵĹ���<br>
 * ʹ��ʱ�ȵ��þ�̬����makeText����ʵ�����ٵ��þ�̬����show������ʾ
 * @author zyjwsg@gmail.com
 */
public class MT {
	private MT(){};
	
	private static Toast toast;
	//����viewGroupΪ�����Զ���toast��View�ṩ������һ����һ��layout�ļ����ֵ�toast��ViewGroupҲֱ���ɸ��ļ���ȡ
	//���������ԭtoast��makeText��ͬ
	public static void makeText (Context context, ViewGroup viewGroup, CharSequence text, int duration) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View toastView = inflater.inflate(R.layout.mtoast, viewGroup);
		TextView toastText = (TextView)toastView.findViewById(R.id.toast_text);
		if (null != toastText)
			toastText.setText(text);
		toast = new Toast(context);
		//����λ��
		toast.setGravity(Gravity.CENTER, 0, 0);
		//���ó���ʱ�䣬�Ƽ�ֻʹ��Toast.LENGTH_SHORT��Toast.LENGTH_LONG
		toast.setDuration(duration);
		toast.setView(toastView);
	}
	public static void show(){
		if (toast != null)
			toast.show();
	}
}
