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
 * 自定义Toast<br>
 * 在原Toast基础上简单定制了一下样式，并未做太复杂的工作<br>
 * 使用时先调用静态方法makeText创建实例，再调用静态方法show将其显示
 * @author zyjwsg@gmail.com
 */
public class MT {
	private MT(){};
	
	private static Toast toast;
	//参数viewGroup为创建自定义toast的View提供参数，一般由一个layout文件布局的toast的ViewGroup也直接由该文件获取
	//其余参数与原toast的makeText相同
	public static void makeText (Context context, ViewGroup viewGroup, CharSequence text, int duration) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View toastView = inflater.inflate(R.layout.mtoast, viewGroup);
		TextView toastText = (TextView)toastView.findViewById(R.id.toast_text);
		if (null != toastText)
			toastText.setText(text);
		toast = new Toast(context);
		//设置位置
		toast.setGravity(Gravity.CENTER, 0, 0);
		//设置持续时间，推荐只使用Toast.LENGTH_SHORT或Toast.LENGTH_LONG
		toast.setDuration(duration);
		toast.setView(toastView);
	}
	public static void show(){
		if (toast != null)
			toast.show();
	}
}
