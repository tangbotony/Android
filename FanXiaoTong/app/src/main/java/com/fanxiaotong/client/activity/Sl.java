package com.fanxiaotong.client.activity;

import com.fanxiaotong.client.fragment.Le;
import com.fanxiaotong.client.fragment.Ri;
import com.fanxiaotong.client.fragment.V;
import com.fanxiaotong.client.fragment.V.MyPageChangeListener;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.widget.SM;
import com.fanxiaotong.client.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

public class Sl extends FragmentActivity {
	static SM mSlidingMenu;
	Le leftFragment;
	Ri rightFragment;
	static V viewPageFragment;
	public static boolean loginFlag = false;
	private static int onStartTimeAfterLogin = 0;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.main);
		init();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		initListener();

		Li.printlnLog("onStartTimeAfterLogin~~~~~~"
				+ onStartTimeAfterLogin);

		if (Sl.loginFlag && viewPageFragment.isFirst()) {

			if (0 == onStartTimeAfterLogin) {
				mSlidingMenu.setCanSliding(true, false);
				showLeft();
			}
			++onStartTimeAfterLogin;
		}
	}

	private void init() {
		mSlidingMenu = (SM) findViewById(R.id.slidingMenu);
		mSlidingMenu.setLeftView(getLayoutInflater().inflate(
				R.layout.left_frame, null));
		mSlidingMenu.setRightView(getLayoutInflater().inflate(
				R.layout.right_frame, null));
		mSlidingMenu.setCenterView(getLayoutInflater().inflate(
				R.layout.center_frame, null));

		FragmentTransaction t = this.getSupportFragmentManager()
				.beginTransaction();
		leftFragment = new Le();
		t.replace(R.id.left_frame, leftFragment);

		rightFragment = new Ri();
		t.replace(R.id.right_frame, rightFragment);

		viewPageFragment = new V();
		t.replace(R.id.center_frame, viewPageFragment);
		t.commit();
	}

	private void initListener() {
		viewPageFragment.setMyPageChangeListener(new MyPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				if (viewPageFragment.isFirst()) {
					if (Sl.loginFlag) {
						mSlidingMenu.setCanSliding(true, false);
					} else {
						mSlidingMenu.setCanSliding(false, false);
					}

				} else if (viewPageFragment.isEnd()) {
					mSlidingMenu.setCanSliding(false, true);
				} else {
					mSlidingMenu.setCanSliding(false, false);
				}
			}
		});
	}

	public void showLeft() {
		mSlidingMenu.showLeftView();
	}

	public void showRight() {
		mSlidingMenu.showRightView();
	}

	//
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			if (Sl.loginFlag) {
				showLeft();
			} else {
				showLoginActivity();
			}
			break;
		case KeyEvent.KEYCODE_BACK:
			showRight();
			break;

		default:
			break;
		}

		return true;
	}

	private void showLoginActivity() {
		Intent intent = new Intent();
		intent.setClass(Sl.this, L.class);
		startActivity(intent);
	}

	public static SM getmSlidingMenu() {
		return mSlidingMenu;
	}

	public static ViewPager getViewPager() {
		return viewPageFragment.getMPager();
	}
}
