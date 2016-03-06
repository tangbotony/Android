package com.fanxiaotong.client.widget;

import java.util.Timer;
import java.util.TimerTask;

import com.fanxiaotong.client.R;
import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.utils.As;
import com.fanxiaotong.client.utils.Li;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher.ViewFactory;

/**
 * 滚动图像控件
 * 
 * @author 周阳
 * @version 201403030001
 */
public class MI extends RelativeLayout implements ViewFactory {

	private OnMImageSwitcherClickListener onMImageSwitcherClickListener;
	private ImageSwitcher imageSwitcher;
	private RadioGroup dotGroup;
	private Context context;
	private float downX;
	private int currentPosition;
	private static Timer timer;

	/**
	 * 0表示目前该往左走<br>
	 * 1表示该往右走
	 */
	private int leftOrRight = 0;
	private boolean canAutoPlay = true;
	
	
//	private String picPath = LittleUtil.simpleDecrypt(ConfigurationFiles.HTTP_PICTURE_PATH);
	private String picPath = ConfigurationFiles.HTTP_PICTURE_PATH;

	private String[] imgUrls = new String[] {
			picPath	+ "image/ads/1.jpg",
			picPath + "image/ads/2.jpg",
			picPath + "image/ads/3.jpg",
			picPath + "image/ads/4.jpg",
			picPath + "image/ads/5.jpg" };

	private int[] imageIds = new int[] { R.drawable.top5_1,
			R.drawable.test_store_logo, R.drawable.tong, R.drawable.fan,
			R.drawable.xiao };

	private Drawable[] imgDrawables;

	private Handler handler = new Handler();

	public MI(Context context, Drawable[] imgDrawables) {
		super(context);
		this.imgDrawables = imgDrawables;
		initView(context);
		initEvent();
	}

	public MI(Context context, int[] imageIds) {
		super(context);
		this.imageIds = imageIds;
		initView(context);
		initEvent();
	}

	public MI(Context context) {
		super(context);
		initImgDatas();
		initView(context);
		initEvent();
	}

	private void initView(Context context) {
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.m_image_switcher, this);

		imageSwitcher = (ImageSwitcher) findViewById(R.id.image_switcher);
		dotGroup = (RadioGroup) findViewById(R.id.radio_group);
		imageSwitcher.setFactory(this);

		setCurrentImg();
		// imageSwitcher.setImageResource(imageIds[currentPosition]);
		setDotPosition(currentPosition);

	}

	private void initEvent() {

		startTimer(2000);
	}

	private void initImgDatas() {

		imgDrawables = new BitmapDrawable[5];

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int count;
				while (true) {
					count = 0;
					for (int i = 0; i < imgDrawables.length; i++) {
						// LittleUtil.printlnLog("ImageUrl:"+imgUrls[i]);
						if (imgDrawables[i] == null) {
							imgDrawables[i] = As
									.loadImageFromUrl(imgUrls[i]);
						} else {
							count++;
						}
					}
					if (count == 5) {
						break;
					}
				}

			}
		}).start();
	}

	public void setImgs(int[] imgIds) {
		this.imageIds = imgIds;
	}

	public int[] getImgIds() {
		return imageIds;
	}

	private void setCurrentImg() {
		// for (int i = 0; i <= getMaxPosition(); i++) {
		// LittleUtil.printlnLog("imgDrawables---" + i + "---" +
		// imgDrawables[i]);
		// }
		imageSwitcher.setImageDrawable(imgDrawables[currentPosition]);
	}

	private void setDotPosition(int i) {
		RadioButton dot = (RadioButton) dotGroup.getChildAt(i);
		dot.setChecked(true);
	}

	@Override
	public View makeView() {
		final ImageView img = new ImageView(context);
		img.setBackgroundColor(getResources().getColor(
				android.R.color.transparent));
		img.setScaleType(ImageView.ScaleType.FIT_XY);
		img.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return img;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		getParent().requestDisallowInterceptTouchEvent(true);
		Li.printlnLog("currentPosition:" + currentPosition);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Li.printlnLog("img_down");
			downX = event.getX();
			stopTimer();
			break;
		case MotionEvent.ACTION_UP:
			Li.printlnLog("img_up");
			float lastX = event.getX();
			if (lastX > downX) {
				if (currentPosition > 0) {
					startAnimation(0);
				} else {
					/*
					 * Toast.makeText(context, "已经是第一张", Toast.LENGTH_SHORT)
					 * .show();
					 */
				}

				leftOrRight = 0;
				startTimer(3000);
			} else if (lastX < downX) {
				if (currentPosition < getMaxPosition()) {
					startAnimation(1);
				} else {
					/*
					 * Toast.makeText(context, "已经是最后一张", Toast.LENGTH_SHORT)
					 * .show();
					 */
				}

				leftOrRight = 1;
				startTimer(3000);
			} else {
				if (!onImageSwitcherClick())
					startTimer(3000);
			}

			break;

		case MotionEvent.ACTION_MOVE:
			Li.printlnLog("img_move");
			break;
		}
		return true;
	}

	/**
	 * 0 向左<br>
	 * 1 向右
	 * 
	 * @param leftOrRight
	 */
	private void startAnimation(int leftOrRight) {

		int inAnimation = 0;
		int outAnimation = 0;

		switch (leftOrRight) {
		case 0:
			inAnimation = R.anim.left_in;
			outAnimation = R.anim.right_out;
			currentPosition--;

			break;

		case 1:
			inAnimation = R.anim.right_in;
			outAnimation = R.anim.left_out;
			currentPosition++;

			break;

		default:
			break;
		}

		imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(context,
				inAnimation));
		imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(context,
				outAnimation));
		// imageSwitcher.setImageResource(imageIds[currentPosition]);
		setCurrentImg();
		setDotPosition(currentPosition);
	}

	private void autoPlay() {
		if (currentPosition == 0)
			leftOrRight = 1;
		if (currentPosition == getMaxPosition())
			leftOrRight = 0;

		startAnimation(leftOrRight);
	}

	public void startTimer(long delay) {
		if (canAutoPlay && timer == null) {
			timer = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					handler.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							autoPlay();
						}
					});
				}
			};
			timer.schedule(task, delay, 3000);
		}
	}

	public void stopTimer() {
		if (canAutoPlay && timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	/**
	 * 若要停止自动滚动，建议先调用stopTimer()，再设置canAutoPlay为false
	 * 
	 * @param autoPlay
	 */
	public void setAutoPlay(boolean autoPlay) {
		this.canAutoPlay = autoPlay;
	}

	public boolean getCanAutoPlay() {
		return canAutoPlay;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public int getMaxPosition() {
		return 4;
	}

	/**
	 * 单击事件
	 */
	public interface OnMImageSwitcherClickListener {
		public void onClick();
	}

	public void setOnMImageSwitcherClickListener(
			OnMImageSwitcherClickListener onMImageSwitcherClickListener) {
		this.onMImageSwitcherClickListener = onMImageSwitcherClickListener;
	}

	public boolean onImageSwitcherClick() {
		if (this.onMImageSwitcherClickListener != null) {
			this.onMImageSwitcherClickListener.onClick();
			return true;
		} else {
			return false;
		}
	}
}
