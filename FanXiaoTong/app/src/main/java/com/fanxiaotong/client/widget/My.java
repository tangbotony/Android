package com.fanxiaotong.client.widget;

import com.fanxiaotong.client.utils.Li;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;
/**
 * ScrollView反弹效果的实现
 * @author acer
 */
public class My extends ScrollView {
	private View inner;
	private float x;
	private Rect normal = new Rect();	
	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			inner = getChildAt(0);
		}
		Li.printlnLog("getChildCount():" + getChildCount());
	}
	
	public My(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (inner == null) {
			return super.onTouchEvent(ev);
		} else {
			commOnTouchEvent(ev);
		}
		return super.onTouchEvent(ev);
	}

	public void commOnTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			x = ev.getY();
			break;
		case MotionEvent.ACTION_UP:

			if (isNeedAnimation()) {
				animation();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			final float preX = x;
			float nowX = ev.getY();
			int deltaX = (int) (preX - nowX);
			// 滚动
			scrollBy(0, deltaX);

			x = nowX;
			// 当滚动到最底部或者最顶部就不会再滚动，这时移动布局
			if (isNeedMove()) {
				if (normal.isEmpty()) {
					// 保存正常的布局位置
					normal.set(inner.getLeft(), inner.getTop(), inner.getRight(), inner.getBottom());
				}
				// 移动布局
				inner.layout(inner.getLeft(), inner.getTop() - deltaX/2 , inner.getRight(), inner.getBottom()- deltaX/2 );
			}
			break;

		default:
			break;
		}
	}

	// 开启动画移动
	public void animation() {
		// 开启移动动画
		TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(), normal.top);
		ta.setDuration(200);
		inner.startAnimation(ta);
		// 设置回到正常的布局位置
		inner.layout(normal.left, normal.top, normal.right, normal.bottom);
		normal.setEmpty();
	}
	// 是否需要开启动画
	public boolean isNeedAnimation() {
		return !normal.isEmpty();
	}
	// 是否需要移动布局
	public boolean isNeedMove() {
		int offset = inner.getMeasuredWidth() - getWidth();
		int scrollX = getScrollX();
		if (scrollX == 0 || scrollX == offset) {
			return true;
		}
		return false;
	}
}
