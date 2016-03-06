package com.fanxiaotong.client.widget;

import java.util.ArrayList;

import com.fanxiaotong.client.R;
import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.utils.Li;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

public class MC extends View {

	private ArrayList<MyPoint> myPoint;
	// private ArrayList<ArrayList<MyPoint>> myLines;
	private Paint painter;
	private int scrWidth;

	public MC(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	public void invalidate(int x, int y) {
		// TODO Auto-generated method stub

		y = 0 - y;

		if (x > scrWidth) {
			x = scrWidth + ConfigurationFiles.WAVELENGH;
			myPoint.add(new MyPoint(x, y));
			myPoint.remove(0);
			for (int i = 0; i < myPoint.size(); i++) {
				myPoint.get(i).x -= ConfigurationFiles.WAVELENGH;
			}

			Li.printlnLog("myPoint-------->" + myPoint.size());
		} else {
			myPoint.add(new MyPoint(x, y));
		}

		Li.printlnLog("point.size:" + myPoint.size());

		super.invalidate();
	}

	public void setWindowManager(WindowManager windowManager) {
		scrWidth = Li.getScreenWidth(windowManager);
	}

	public void clear() {
		myPoint.clear();
	}

	// --------------------------------------------界面绘制--------------------------------------------//
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		Li.printlnLog("height---" + getHeight());
		Li.printlnLog("height-dp---"
				+ Li.px2dip(getContext(), getHeight()));

		for (int i = 0; i < myPoint.size(); i++) {

			// MyPoint tmpPoint_0 = myPoint.get(i);
			// MyPoint tmpPonit_1 = myPoint.get(i + 1);

			// canvas.drawLine(tmpPoint_0.x, tmpPoint_0.y
			// * ConfigurationFiles.SWING_KEY + getHeight(), tmpPonit_1.x,
			// tmpPonit_1.y * ConfigurationFiles.SWING_KEY + getHeight(),
			// painter);

			MyPoint tmpPoint = myPoint.get(i);

			canvas.drawLine(tmpPoint.x, tmpPoint.y
					* ConfigurationFiles.SWING_KEY + getHeight(), tmpPoint.x,
					getHeight(), painter);

		}
	}

	// 初始化界面
	private void initView() {
		// myLines = new ArrayList<ArrayList<MyPoint>>();
		painter = new Paint();
		painter.setColor(getResources().getColor(R.color.light_blue));
		myPoint = new ArrayList<MyPoint>();
	}
}

class MyPoint {
	float x;
	float y;

	public MyPoint(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
