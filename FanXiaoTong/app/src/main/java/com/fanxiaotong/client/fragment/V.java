package com.fanxiaotong.client.fragment;

import java.util.ArrayList;

import com.fanxiaotong.client.activity.F;
import com.fanxiaotong.client.activity.L;
import com.fanxiaotong.client.activity.Sl;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class V extends Fragment {
	private static RelativeLayout userLogin;
	private ImageView showRight;
	private TextView pageTitle;
	public static ImageView recomBtn, storeBtn, cartBtn, orderBtn, loginIco;
	private MyAdapter mAdapter;
	private ViewPager mPager;
	private RelativeLayout searchLayout;
	private ArrayList<Fragment> pagerItemList = new ArrayList<Fragment>();

	private ImageView searchBtn;
	private EditText searchInput;
	private String searchString;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.view_pager, null);
		userLogin = (RelativeLayout) mView.findViewById(R.id.login_btn);
		showRight = (ImageView) mView.findViewById(R.id.menu_btn);
		mPager = (ViewPager) mView.findViewById(R.id.pager);
		pageTitle = (TextView) mView.findViewById(R.id.page_title);
		recomBtn = (ImageView) mView.findViewById(R.id.recom_btn);
		searchLayout = (RelativeLayout) mView.findViewById(R.id.search_layout);
		searchBtn = (ImageView) mView.findViewById(R.id.search_btn);
		searchInput = (EditText) mView.findViewById(R.id.search_input);
		loginIco = (ImageView) mView.findViewById(R.id.login_ico);
		mPager.setOffscreenPageLimit(3);

		searchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				searchString = searchInput.getText().toString();
				Li.printlnLog("searchString:" + searchString);
				Li.printlnLog("关键字长度：" + searchString.length());
				if (searchString.equals("")) {
					Toast.makeText(getActivity(), "搜索内容不能为空！",
							Toast.LENGTH_SHORT).show();
				} else if (searchString.length() > 5) {
					Toast.makeText(getActivity(), "关键字大于6个！",
							Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent();
					intent.putExtra("flag", 0);
					intent.putExtra("categoryId", 0);
					intent.putExtra("categoryString", searchString);
					intent.setClass(getActivity(), F.class);
					startActivity(intent);
				}
			}
		});

		recomBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPager.setCurrentItem(0);
			}
		});

		storeBtn = (ImageView) mView.findViewById(R.id.store_btn);
		storeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPager.setCurrentItem(1);
			}
		});
		cartBtn = (ImageView) mView.findViewById(R.id.cart_btn);
		cartBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPager.setCurrentItem(2);
			}
		});
		orderBtn = (ImageView) mView.findViewById(R.id.order_btn);
		orderBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPager.setCurrentItem(3);
			}
		});

		Ho recomPage = new Ho();
		Sto storePage = new Sto();
		Ca cartPage = new Ca();
		O orderPage = new O();
		pagerItemList.add(recomPage);
		pagerItemList.add(storePage);
		pagerItemList.add(cartPage);
		pagerItemList.add(orderPage);
		mAdapter = new MyAdapter(getFragmentManager());
		mPager.setAdapter(mAdapter);
		setCurrentActived(0);

		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				Li.printlnLog("-----current position:" + position);

				if (myPageChangeListener != null)
					myPageChangeListener.onPageSelected(position);

				// 当前页面所在位置
				setCurrentActived(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int position) {
			}
		});

		return mView;
	}

	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		userLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ((SlidingActivity) getActivity()).showLeft();
				if (Sl.loginFlag) {
					((Sl) getActivity()).showLeft();
				} else {
					showLoginActivity();
				}
			}
		});

		showRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((Sl) getActivity()).showRight();
			}
		});
	}

	public ViewPager getMPager() {
		return mPager;
	}

	public boolean isFirst() {
		Li.printlnLog("~~~mPager.getCurrentItem()~~~"
				+ mPager.getCurrentItem());
		if (mPager.getCurrentItem() == 0)
			return true;
		else
			return false;
	}

	public boolean isEnd() {
		if (mPager.getCurrentItem() == pagerItemList.size() - 1)
			return true;
		else
			return false;
	}

	private void setCurrentActived(int position) {
		switch (position) {
		case 0:
			searchLayout.setVisibility(RelativeLayout.VISIBLE);
			pageTitle.setVisibility(RelativeLayout.GONE);
			recomBtn.setBackgroundResource(R.drawable.hot_actived);
			storeBtn.setBackgroundResource(R.drawable.stores_normal);
			if (O.currentArrayList.size() != 0) {
				orderBtn.setBackgroundResource(R.drawable.orders_noticed_normal);
			} else {
				orderBtn.setBackgroundResource(R.drawable.orders_normal);
			}
			if (Ca.orderList.size() != 0) {
				cartBtn.setBackgroundResource(R.drawable.cart_noticed_normal);
			} else {

				cartBtn.setBackgroundResource(R.drawable.cart_normal);
			}
			break;

		case 1:
			searchLayout.setVisibility(RelativeLayout.GONE);
			pageTitle.setVisibility(RelativeLayout.VISIBLE);
			pageTitle.setText(R.string.store);
			recomBtn.setBackgroundResource(R.drawable.hot_normal);
			storeBtn.setBackgroundResource(R.drawable.stores_actived);

			if (O.currentArrayList.size() != 0) {
				orderBtn.setBackgroundResource(R.drawable.orders_noticed_normal);
			} else {
				orderBtn.setBackgroundResource(R.drawable.orders_normal);
			}
			if (Ca.orderList.size() != 0) {
				cartBtn.setBackgroundResource(R.drawable.cart_noticed_normal);
			} else {

				cartBtn.setBackgroundResource(R.drawable.cart_normal);
			}
			break;

		case 2:
			searchLayout.setVisibility(RelativeLayout.GONE);
			pageTitle.setVisibility(RelativeLayout.VISIBLE);
			pageTitle.setText(R.string.cart);
			recomBtn.setBackgroundResource(R.drawable.hot_normal);
			storeBtn.setBackgroundResource(R.drawable.stores_normal);
			if (O.currentArrayList.size() != 0) {
				orderBtn.setBackgroundResource(R.drawable.orders_noticed_normal);
			} else {
				orderBtn.setBackgroundResource(R.drawable.orders_normal);
			}
			if (Ca.orderList.size() != 0) {
				cartBtn.setBackgroundResource(R.drawable.cart_noticed_actived);
			} else {
				cartBtn.setBackgroundResource(R.drawable.cart_actived);
			}
			break;

		case 3:
			searchLayout.setVisibility(RelativeLayout.GONE);
			pageTitle.setVisibility(RelativeLayout.VISIBLE);
			pageTitle.setText(R.string.orderlist);
			recomBtn.setBackgroundResource(R.drawable.hot_normal);
			storeBtn.setBackgroundResource(R.drawable.stores_normal);
			if (O.currentArrayList.size() != 0) {
				orderBtn.setBackgroundResource(R.drawable.orders_noticed_actived);
			} else {
				orderBtn.setBackgroundResource(R.drawable.orders_actived);
			}
			if (Ca.orderList.size() != 0) {
				cartBtn.setBackgroundResource(R.drawable.cart_noticed_normal);
			} else {

				cartBtn.setBackgroundResource(R.drawable.cart_normal);
			}
			break;
		default:
			break;
		}
	}

	public class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return pagerItemList.size();
		}

		@Override
		public Fragment getItem(int position) {

			Fragment fragment = null;
			if (position < pagerItemList.size())
				fragment = pagerItemList.get(position);
			else
				fragment = pagerItemList.get(0);

			return fragment;

		}
	}

	private MyPageChangeListener myPageChangeListener;

	public void setMyPageChangeListener(MyPageChangeListener l) {

		myPageChangeListener = l;

	}

	public interface MyPageChangeListener {
		public void onPageSelected(int position);
	}

	private void showLoginActivity() {
		Intent intent = new Intent();
		intent.setClass(getActivity(), L.class);
		startActivity(intent);
	}

	/*
	 * flag != 0;表示登录
	 */
	public static void setLoginImage(int flag) {
		// 设置LoginBtn背景
		if (flag != 0) {
			Li.setHeadLogoSrc(loginIco,
					L.fakeMyInfo.getIcon());
			// LittleUtil.setHeadLogo(loginIco,
			// LoginActivity.fakeMyInfo.getIcon());
		} else {
			// userLogin.setBackgroundResource(R.drawable.unlogin);
			loginIco.setImageResource(R.drawable.unlogin_selector);
		}
	}

}
