package com.fanxiaotong.client.fragment;

import java.util.Iterator;
import java.util.List;

import com.fanxiaotong.client.activity.St;
import com.fanxiaotong.client.bean.FakeRestaurant;
import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.utils.Fa;
import com.fanxiaotong.client.utils.Fi;
import com.fanxiaotong.client.utils.Ht;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.widget.MPr;
import com.fanxiaotong.client.widget.Re;
import com.fanxiaotong.client.widget.Re.RefreshListener;
import com.fanxiaotong.client.widget.SSt;
import com.fanxiaotong.client.widget.SSt.OnStoreClickListener;
import com.fanxiaotong.client.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Sto extends Fragment {
	private Re mRefreshableView;
	private MPr mProgressDialog;
	private LinearLayout noNetworkLayout;
	private LinearLayout storesContainer;
	private static int uiFlag = ConfigurationFiles.NEVER_SHOW;

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		FakeRestaurant FakeRestaurant;
		SSt storeView;

		public void handleMessage(Message msg) {
			int flag = msg.what;
			if (flag == ConfigurationFiles.STORE_MESSAGE) {
				@SuppressWarnings("unchecked")
				List<FakeRestaurant> stores = (List<FakeRestaurant>) msg.obj;
				storesContainer.removeAllViews();
				noNetworkLayout.setVisibility(LinearLayout.GONE);
				for (Iterator<FakeRestaurant> iterator = stores.iterator(); iterator
						.hasNext();) {
					FakeRestaurant = (FakeRestaurant) iterator.next();
					storeView = new SSt(getActivity());
					storeView.setStoreId(FakeRestaurant.getId());
					storeView.setStoreName(FakeRestaurant.getRestaurantName());
					storeView
							.setMainFood("主售：" + FakeRestaurant.getSpecialty());
					storeView.setSalesVolume(FakeRestaurant.getSaleNum());
					storeView.setStoreScore(FakeRestaurant.getEvaluate());
					Li.printlnLog("FakeRestaurant.getPhoto():"
							+ FakeRestaurant.getPhoto());
					Fi.initImageBackground(FakeRestaurant.getPhoto(),
							storeView.getStoreLogo(), 1);
					storesContainer.addView(storeView);
				}
				setSingleStoreOnClickListener(storesContainer);
			} else {
				storesContainer.removeAllViews();
				noNetworkLayout.setVisibility(LinearLayout.VISIBLE);
			}
			if (mRefreshableView.isRefreshing())
				mRefreshableView.finishRefresh();
			mProgressDialog.dismiss();
		};
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.store, null);

		noNetworkLayout = (LinearLayout) view
				.findViewById(R.id.no_network_layout);

		storesContainer = (LinearLayout) view
				.findViewById(R.id.store_linearLayout);

		mRefreshableView = (Re) view
				.findViewById(R.id.refresh_root);
		mRefreshableView.setRefreshListener(new RefreshListener() {

			public void onRefresh(Re view) {
				// TODO Auto-generated method stub
				refresh();
			}
		});
		mProgressDialog = new MPr(getActivity());
		return view;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser == true) {
			if (uiFlag == ConfigurationFiles.NEVER_SHOW) {
				mProgressDialog.show("加载中...");
				refresh();
				uiFlag = ConfigurationFiles.HAVE_SHOWED;
			}
		} else if (isVisibleToUser == false) {
		}
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Li.printlnLog("Store----------onActivityCreated");
	}

	public void refresh() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String storesString = Ht.SendHttpClientPost(
						ConfigurationFiles.HTTP_STORE_PATH, null, "utf-8",
						getActivity());
				Message message = Message.obtain();
				System.out
						.println("storesString.equals(ConfigurationFiles.HTTP_ERROR):"
								+ storesString
										.equals(ConfigurationFiles.HTTP_ERROR));
				if (!storesString.equals(ConfigurationFiles.HTTP_ERROR)
						&& !storesString
								.equals(ConfigurationFiles.GET_INFORMATION_FAIL)) {
					List<FakeRestaurant> stores = Fa.getList(
							storesString, FakeRestaurant.class);
					message.what = ConfigurationFiles.STORE_MESSAGE;
					message.obj = stores;
					Li.printlnLog("haveMSG:" + message.what);
					handler.sendMessage(message);
				} else {
					message.what = ConfigurationFiles.NO_INTERNET;
					handler.sendMessage(message);
				}
			}
		};
		new Thread(runnable).start();
	}

	private void setSingleStoreOnClickListener(LinearLayout storesContainer) {
		for (int i = 0; i < storesContainer.getChildCount(); i++) {
			SSt singleStoreView = (SSt) storesContainer
					.getChildAt(i);
			Li.printlnLog("singleStoreView:" + singleStoreView);
			final int storeId = singleStoreView.getStoreId();
			singleStoreView.setOnStoreClickListener(new OnStoreClickListener() {
				@Override
				public void onClick() {
					gotoStoreDetailPage(storeId);
				}
			});
		}
	}

	private void gotoStoreDetailPage(int storeId) {
		Intent intent = new Intent();
		intent.putExtra("storeId", storeId);
		intent.setClass(getActivity(), St.class);
		startActivity(intent);
	}
}
