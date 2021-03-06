package com.fanxiaotong.client.widget;

import java.util.Timer;
import java.util.TimerTask;

import com.fanxiaotong.client.R;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SSw extends RelativeLayout implements OnTouchListener {
	private Timer timer;
	/**
	 * 让菜单滚动，手指滑动�?要达到的速度�?
	 */
	public static final int SNAP_VELOCITY = 10;

	/**
	 * SlidingSwitcherView的宽度�??
	 */
	private int switcherViewWidth;

	/**
	 * 当前显示的元素的下标�?
	 */
	private int currentItemIndex;

	/**
	 * 菜单中包含的元素总数�?
	 */
	private int itemsCount;

	/**
	 * 各个元素的偏移边界�?��??
	 */
	private int[] borders;

	/**
	 * �?多可以滑动到的左边缘。�?�由菜单中包含的元素总数来定，marginLeft到达此�?�之后，不能再减少�??
	 * 
	 */
	private int leftEdge = 0;

	/**
	 * �?多可以滑动到的右边缘。�?�恒�?0，marginLeft到达此�?�之后，不能再增加�??
	 */
	private int rightEdge = 0;

	/**
	 * 记录手指按下时的横坐标�??
	 */
	private float xDown;

	/**
	 * 记录手指移动时的横坐标�??
	 */
	private float xMove;

	/**
	 * 记录手机抬起时的横坐标�??
	 */
	private float xUp;

	/**
	 * 菜单布局�?
	 */
	private LinearLayout itemsLayout;

	/**
	 * 标签布局�?
	 */
	private LinearLayout dotsLayout;

	/**
	 * 菜单中的第一个元素�??
	 */
	private View firstItem;

	/**
	 * 菜单中第�?个元素的布局，用于改变leftMargin的�?�，来决定当前显示的哪一个元素�??
	 */
	private MarginLayoutParams firstItemParams;

	/**
	 * 用于计算手指滑动的�?�度�?
	 */
	private VelocityTracker mVelocityTracker;

	/**
	 * 重写SlidingSwitcherView的构造函数，用于允许在XML中引用当前的自定义布�?�?
	 * 
	 * @param context
	 * @param attrs
	 */
	public SSw(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SSw);
		boolean isAutoPlay = a.getBoolean(R.styleable.SSw_auto_play, true);
		if (isAutoPlay) {
			startAutoPlay();
		}
		a.recycle();
	}

	/**
	 * 滚动到下�?个元素�??
	 */
	public void scrollToNext() {
		new ScrollTask().execute(-20);
	}

	/**
	 * 滚动到上�?个元素�??
	 */
	public void scrollToPrevious() {
		new ScrollTask().execute(20);
	}

	/**
	 * 滚动到第�?个元素�??
	 */
	public void scrollToFirstItem() {
		new ScrollToFirstItemTask().execute(20 * itemsCount);
	}

	/**
	 * 用于在定时器当中操作UI界面�?
	 */
	private Handler handler = new Handler();

	/**
	 * �?启图片自动播放功能，当滚动到�?后一张图片的时�?�，会自动回滚到第一张图片�??
	 */
	public void startAutoPlay() {
		this.timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (currentItemIndex == itemsCount - 1) {
					currentItemIndex = 0;
					handler.post(new Runnable() {
						@Override
						public void run() {
							scrollToFirstItem();
							refreshDotsLayout();
						}
					});
				} else {
					currentItemIndex++;
					handler.post(new Runnable() {
						@Override
						public void run() {
							scrollToNext();
							refreshDotsLayout();
						}
					});
				}
			}
		}, 3000, 3000);
	}

	/**
	 * 在onLayout中重新设定菜单元素和标签元素的参数�??
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			initializeItems();
			initializeDots();
		}
	}

	/**
	 * 初始化菜单元素，为每�?个子元素增加监听事件，并且改变所有子元素的宽度，让它们等于父元素的宽度�??
	 */
	private void initializeItems() {
		switcherViewWidth = getWidth();
		itemsLayout = (LinearLayout) getChildAt(0);
		itemsCount = itemsLayout.getChildCount();
		borders = new int[itemsCount];
		for (int i = 0; i < itemsCount; i++) {
			borders[i] = -i * switcherViewWidth;
			View item = itemsLayout.getChildAt(i);
			MarginLayoutParams params = (MarginLayoutParams) item.getLayoutParams();
			params.width = switcherViewWidth;
			item.setLayoutParams(params);
			item.setOnTouchListener(this);
		}
		leftEdge = borders[itemsCount - 1];
		firstItem = itemsLayout.getChildAt(0);
		firstItemParams = (MarginLayoutParams) firstItem.getLayoutParams();
	}

	/**
	 * 初始化标签元素�??
	 */
	private void initializeDots() {
		dotsLayout = (LinearLayout) getChildAt(1);
		refreshDotsLayout();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		createVelocityTracker(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 手指按下时，记录按下时的横坐�?
			changeAutoPlayState(false);
			xDown = event.getRawX();
			getParent().requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_MOVE:
			// 手指移动时，对比按下时的横坐标，计算出移动的距离，来调整左侧布局的leftMargin值，从�?�显示和隐藏左侧布局
			xMove = event.getRawX();
			int distanceX = (int) (xMove - xDown) - (currentItemIndex * switcherViewWidth);
			firstItemParams.leftMargin = distanceX;
			if (beAbleToScroll()) {
				firstItem.setLayoutParams(firstItemParams);
			}
			break;
		case MotionEvent.ACTION_UP:
			changeAutoPlayState(true);
			// 手指抬起时，进行判断当前手势的意图，从�?�决定是滚动到左侧布�?，还是滚动到右侧布局
			xUp = event.getRawX();
			if (beAbleToScroll()) {
				if (wantScrollToPrevious()) {
					if (shouldScrollToPrevious()) {
						currentItemIndex--;
						scrollToPrevious();
						refreshDotsLayout();
					} else {
						scrollToNext();
					}
				} else if (wantScrollToNext()) {
					if (shouldScrollToNext()) {
						currentItemIndex++;
						scrollToNext();
						refreshDotsLayout();
					} else {
						scrollToPrevious();
					}
				}
			}
			recycleVelocityTracker();
			break;
		}
		return true;
	}

	/**
	 * 当前是否能够滚动，滚动到第一个或�?后一个元素时将不能再滚动�?
	 * 
	 * @return 当前leftMargin的�?�在leftEdge和rightEdge之间返回true,否则返回false�?
	 */
	private boolean beAbleToScroll() {
		return firstItemParams.leftMargin < rightEdge && firstItemParams.leftMargin > leftEdge;
	}

	/**
	 * 判断当前手势的意图是不是想滚动到上一个菜单元素�?�如果手指移动的距离是正数，则认为当前手势是想要滚动到上�?个菜单元素�??
	 * 
	 * @return 当前手势想滚动到上一个菜单元素返回true，否则返回false�?
	 */
	private boolean wantScrollToPrevious() {
		return xUp - xDown > 0;
	}

	/**
	 * 判断当前手势的意图是不是想滚动到下一个菜单元素�?�如果手指移动的距离是负数，则认为当前手势是想要滚动到下�?个菜单元素�??
	 * 
	 * @return 当前手势想滚动到下一个菜单元素返回true，否则返回false�?
	 */
	private boolean wantScrollToNext() {
		return xUp - xDown < 0;
	}

	/**
	 * 判断是否应该滚动到下�?个菜单元素�?�如果手指移动距离大于屏幕的1/2，或者手指移动�?�度大于SNAP_VELOCITY�?
	 * 就认为应该滚动到下一个菜单元素�??
	 * 
	 * @return 如果应该滚动到下�?个菜单元素返回true，否则返回false�?
	 */
	private boolean shouldScrollToNext() {
		return xDown - xUp > switcherViewWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
	}

	/**
	 * 判断是否应该滚动到上�?个菜单元素�?�如果手指移动距离大于屏幕的1/2，或者手指移动�?�度大于SNAP_VELOCITY�?
	 * 就认为应该滚动到上一个菜单元素�??
	 * 
	 * @return 如果应该滚动到上�?个菜单元素返回true，否则返回false�?
	 */
	private boolean shouldScrollToPrevious() {
		return xUp - xDown > switcherViewWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
	}

	/**
	 * 刷新标签元素布局，每次currentItemIndex值改变的时�?�都应该进行刷新�?
	 */
	private void refreshDotsLayout() {
		if (dotsLayout != null)
			dotsLayout.removeAllViews();
		for (int i = 0; i < itemsCount; i++) {
			LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(0,
					LayoutParams.MATCH_PARENT);
			linearParams.weight = 1;
			RelativeLayout relativeLayout = new RelativeLayout(getContext());
			ImageView image = new ImageView(getContext());
			if (i == currentItemIndex) {
				image.setBackgroundResource(R.drawable.dot_selected);
			} else {
				image.setBackgroundResource(R.drawable.dot_unselected);
			}
			RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			relativeParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			relativeLayout.addView(image, relativeParams);
			dotsLayout.addView(relativeLayout, linearParams);
		}
	}

	/**
	 * 创建VelocityTracker对象，并将触摸事件加入到VelocityTracker当中�?
	 * 
	 * @param event
	 *            右侧布局监听控件的滑动事�?
	 */
	private void createVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}

	/**
	 * 获取手指在右侧布�?的监听View上的滑动速度�?
	 * 
	 * @return 滑动速度，以每秒钟移动了多少像素值为单位�?
	 */
	private int getScrollVelocity() {
		mVelocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) mVelocityTracker.getXVelocity();
		return Math.abs(velocity);
	}

	/**
	 * 回收VelocityTracker对象�?
	 */
	private void recycleVelocityTracker() {
		mVelocityTracker.recycle();
		mVelocityTracker = null;
	}

	/**
	 * �?测菜单滚动时，是否有穿越border，border的�?�都存储在{@link #borders}中�??
	 * 
	 * @param leftMargin
	 *            第一个元素的左偏移�??
	 * @param speed
	 *            滚动的�?�度，正数说明向右滚动，负数说明向左滚动�?
	 * @return 穿越任何�?个border了返回true，否则返回false�?
	 */
	private boolean isCrossBorder(int leftMargin, int speed) {
		for (int border : borders) {
			if (speed > 0) {
				if (leftMargin >= border && leftMargin - speed < border) {
					return true;
				}
			} else {
				if (leftMargin <= border && leftMargin - speed > border) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 找到离当前的leftMargin�?近的�?个border值�??
	 * 
	 * @param leftMargin
	 *            第一个元素的左偏移�??
	 * @return 离当前的leftMargin�?近的�?个border值�??
	 */
	private int findClosestBorder(int leftMargin) {
		int absLeftMargin = Math.abs(leftMargin);
		int closestBorder = borders[0];
		int closestMargin = Math.abs(Math.abs(closestBorder) - absLeftMargin);
		for (int border : borders) {
			int margin = Math.abs(Math.abs(border) - absLeftMargin);
			if (margin < closestMargin) {
				closestBorder = border;
				closestMargin = margin;
			}
		}
		return closestBorder;
	}

	class ScrollToFirstItemTask extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... speed) {
			int leftMargin = firstItemParams.leftMargin;
			while (true) {
				leftMargin = leftMargin + speed[0];
				// 当leftMargin大于0时，说明已经滚动到了第一个元素，跳出循环
				if (leftMargin > 0) {
					leftMargin = 0;
					break;
				}
				publishProgress(leftMargin);
				sleep(20);
			}
			return leftMargin;
		}

		@Override
		protected void onProgressUpdate(Integer... leftMargin) {
			firstItemParams.leftMargin = leftMargin[0];
			firstItem.setLayoutParams(firstItemParams);
		}

		@Override
		protected void onPostExecute(Integer leftMargin) {
			firstItemParams.leftMargin = leftMargin;
			firstItem.setLayoutParams(firstItemParams);
		}

	}

	class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... speed) {
			int leftMargin = firstItemParams.leftMargin;
			// 根据传入的�?�度来滚动界面，当滚动穿越border时，跳出循环�?
			while (true) {
				leftMargin = leftMargin + speed[0];
				if (isCrossBorder(leftMargin, speed[0])) {
					leftMargin = findClosestBorder(leftMargin);
					break;
				}
				publishProgress(leftMargin);
				// 为了要有滚动效果产生，每次循环使线程睡眠10毫秒，这样肉眼才能够看到滚动动画�?
				sleep(10);
			}
			return leftMargin;
		}

		@Override
		protected void onProgressUpdate(Integer... leftMargin) {
			firstItemParams.leftMargin = leftMargin[0];
			firstItem.setLayoutParams(firstItemParams);
		}

		@Override
		protected void onPostExecute(Integer leftMargin) {
			firstItemParams.leftMargin = leftMargin;
			firstItem.setLayoutParams(firstItemParams);
		}
	}

	/**
	 * 使当前线程睡眠指定的毫秒数�??
	 * 
	 * @param millis
	 *            指定当前线程睡眠多久，以毫秒为单�?
	 */
	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	
	/**
	 * 更改自动滚动状�??
	 */

	public void changeAutoPlayState(boolean State)
	{
		if(State)
		{
			startAutoPlay();
		}else if(!State && timer!=null)
		{
			timer.cancel();
			timer = null;
		}
	}
}
