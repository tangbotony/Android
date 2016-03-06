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
 * ScrollView����Ч����ʵ��
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
			// ����
			scrollBy(0, deltaX);

			x = nowX;
			// ����������ײ���������Ͳ����ٹ�������ʱ�ƶ�����
			if (isNeedMove()) {
				if (normal.isEmpty()) {
					// ���������Ĳ���λ��
					normal.set(inner.getLeft(), inner.getTop(), inner.getRight(), inner.getBottom());
				}
				// �ƶ�����
				inner.layout(inner.getLeft(), inner.getTop() - deltaX/2 , inner.getRight(), inner.getBottom()- deltaX/2 );
			}
			break;

		default:
			break;
		}
	}

	// ���������ƶ�
	public void animation() {
		// �����ƶ�����
		TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(), normal.top);
		ta.setDuration(200);
		inner.startAnimation(ta);
		// ���ûص������Ĳ���λ��
		inner.layout(normal.left, normal.top, normal.right, normal.bottom);
		normal.setEmpty();
	}
	// �Ƿ���Ҫ��������
	public boolean isNeedAnimation() {
		return !normal.isEmpty();
	}
	// �Ƿ���Ҫ�ƶ�����
	public boolean isNeedMove() {
		int offset = inner.getMeasuredWidth() - getWidth();
		int scrollX = getScrollX();
		if (scrollX == 0 || scrollX == offset) {
			return true;
		}
		return false;
	}
}
