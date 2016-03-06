package com.fanxiaotong.client.fragment;

import java.util.ArrayList;
import java.util.Map;

import com.fanxiaotong.client.activity.D;
import com.fanxiaotong.client.activity.F;
import com.fanxiaotong.client.activity.St;
import com.fanxiaotong.client.bean.FakeHomeAds;
import com.fanxiaotong.client.bean.FakeHomeCategory;
import com.fanxiaotong.client.bean.FakeHomePage;
import com.fanxiaotong.client.bean.FakeHomeRestaurant;
import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.utils.Fa;
import com.fanxiaotong.client.utils.Fi;
import com.fanxiaotong.client.utils.Ht;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.widget.MI;
import com.fanxiaotong.client.widget.MI.OnMImageSwitcherClickListener;
import com.fanxiaotong.client.widget.MPr;
import com.fanxiaotong.client.widget.Re;
import com.fanxiaotong.client.widget.Re.RefreshListener;
import com.fanxiaotong.client.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Ho extends Fragment {

	private Re mRefreshableView;
	private LinearLayout noNetworkLayout;
	private ScrollView contentScroll;
	private MPr mProgressDialog;
	private TextView recommend1Lable, recommend2Lable, shop1Dish1, shop1Dish2,
			shop1Dish3, shop1Dish4, shop1Dish5, shop1Dish6, shop1Dish7,
			shop1Dish8, shop2Dish1, shop2Dish2, shop2Dish3, shop2Dish4,
			shop2Dish5, shop2Dish6, shop2Dish7, shop2Dish8, shop3Dish1,
			shop3Dish2, shop3Dish3, shop3Dish4, shop3Dish5, shop3Dish6,
			shop3Dish7, shop3Dish8, dishKind1, dishKind2, dishKind3, dishKind4,
			dishKind5, dishKind6, dishKind7, dishKind8;
	private RelativeLayout shop1Dish9, shop2Dish9, shop3Dish9;
	private ImageView recommend1, recommend2, shop1, shop2, shop3;
	private LinearLayout dishKindContainer1, dishKindContainer2;

	private RelativeLayout recom1, recom2, recom3;

	private LinearLayout imgSwitcherContainer;

	private MI imgSwitcher;
	private int naverShow = ConfigurationFiles.NEVER_SHOW;

	/*
	 * private String[] imgUrls = new String[] {
	 * ConfigurationFiles.HTTP_PICTURE_PATH + "image/ads/20140217222534.jpg",
	 * ConfigurationFiles.HTTP_PICTURE_PATH + "image/ads/20140217222534.jpg",
	 * ConfigurationFiles.HTTP_PICTURE_PATH + "image/ads/20140217222534.jpg",
	 * ConfigurationFiles.HTTP_PICTURE_PATH + "image/ads/20140217222534.jpg",
	 * ConfigurationFiles.HTTP_PICTURE_PATH + "image/ads/20140217222534.jpg" };
	 */

	// private Drawable[] imgDrawables;

	// 定义用于接收数据的变量
	ArrayList<FakeHomeCategory> categoryList;
	ArrayList<FakeHomeRestaurant> restaurantList;
	ArrayList<FakeHomeAds> adsList;
	// Handler
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int flag = msg.what;
			if (flag == ConfigurationFiles.HOME_MESSAGE) {
				FakeHomePage fakeHomePage = (FakeHomePage) msg.obj;
				System.out.println("oooooooooooooooooooooooooooooooo");
				System.out.println("oooooooooooooooooooooooooooooooo");
				System.out.println("fakeHomePage:"+fakeHomePage.toString());
				System.out.println("oooooooooooooooooooooooooooooooo");
				System.out.println("oooooooooooooooooooooooooooooooo");
				adsList = fakeHomePage.getAdsList();
				
				imgSwitcher
						.setOnMImageSwitcherClickListener(new OnMImageSwitcherClickListener() {
							@Override
							public void onClick() {
								// TODO Auto-generated method stub
								switch (imgSwitcher.getCurrentPosition()) {
								case 0:
									setAdsClickListener(adsList.get(0)
											.getFlag(), adsList.get(0)
											.getRestFoodId());
									break;
								case 1:
									if (1 < adsList.size()) {
										setAdsClickListener(adsList.get(1)
												.getFlag(), adsList.get(1)
												.getRestFoodId());
									}
									break;
								case 2:
									if (2 < adsList.size()) {
										setAdsClickListener(adsList.get(2)
												.getFlag(), adsList.get(2)
												.getRestFoodId());
									}
									break;
								case 3:
									if (3 < adsList.size()) {
										setAdsClickListener(adsList.get(3)
												.getFlag(), adsList.get(3)
												.getRestFoodId());
									}
									break;
								case 4:
									if (4 < adsList.size()) {
										setAdsClickListener(adsList.get(4)
												.getFlag(), adsList.get(4)
												.getRestFoodId());
									}
									break;

								default:
									break;
								}
							}
						});
				categoryList = fakeHomePage.getCategoryList();
				restaurantList = fakeHomePage.getRestaurantList();

				// 设置种类
				if (categoryList.size() > 2) {
					FakeHomeCategory fakeHomeCategory = categoryList.get(0);
					recommend1Lable.setText("· "
							+ fakeHomeCategory.getCategoryName() + " ·");
					Fi.initImageBackground(fakeHomeCategory.getPhoto(),
							recommend1, 2);
					final int categoryId1 = fakeHomeCategory.getCategoryId();
					final String category1 = fakeHomeCategory.getCategoryName();
					recom2.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// 分类的URL
							gotoCategoryPage(categoryId1, category1, 2);
						}
					});

					fakeHomeCategory = categoryList.get(1);
					recommend2Lable.setText("· "
							+ fakeHomeCategory.getCategoryName() + " ·");
					Fi.initImageBackground(fakeHomeCategory.getPhoto(),
							recommend2, 2);
					final int categoryId2 = fakeHomeCategory.getCategoryId();
					final String category2 = fakeHomeCategory.getCategoryName();
					recom3.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// 分类2的URL
							gotoCategoryPage(categoryId2, category2, 2);
						}
					});
				}
				// 设置类型
				setCategory(categoryList);

				// 设置热店Top3
				setHotStoreTop3(restaurantList);
				showWhichLayout(1);
				if (mRefreshableView.isRefreshing())
					mRefreshableView.finishRefresh();

			} else if (flag == ConfigurationFiles.NULL) {
				showWhichLayout(2);
				if (mRefreshableView.isRefreshing())
					mRefreshableView.finishRefresh();
			} else {
				showWhichLayout(2);
				if (mRefreshableView.isRefreshing())
					mRefreshableView.finishRefresh();
			}
			if (mProgressDialog.isShowing())
				mProgressDialog.dismiss();
		}
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home, null);
		mRefreshableView = (Re) view
				.findViewById(R.id.refresh_root);
		noNetworkLayout = (LinearLayout) view
				.findViewById(R.id.no_network_layout);
		contentScroll = (ScrollView) view.findViewById(R.id.scro);
		imgSwitcherContainer = (LinearLayout) view
				.findViewById(R.id.mimage_switcher_container);
		imgSwitcher = new MI(getActivity());
		imgSwitcherContainer.addView(imgSwitcher);
		naverShow = ConfigurationFiles.HAVE_SHOWED;
		recommend1Lable = (TextView) view.findViewById(R.id.recommend_1_lable);
		recommend2Lable = (TextView) view.findViewById(R.id.recommend_2_lable);
		recommend1 = (ImageView) view.findViewById(R.id.recommend_1);
		recommend2 = (ImageView) view.findViewById(R.id.recommend_2);

		dishKindContainer1 = (LinearLayout) view
				.findViewById(R.id.dish_kind_container1);
		dishKindContainer2 = (LinearLayout) view
				.findViewById(R.id.dish_kind_container2);

		recom1 = (RelativeLayout) view.findViewById(R.id.recom_1);
		recom2 = (RelativeLayout) view.findViewById(R.id.recom_2);
		recom3 = (RelativeLayout) view.findViewById(R.id.recom_3);

		shop1 = (ImageView) view.findViewById(R.id.shop_1);
		shop2 = (ImageView) view.findViewById(R.id.shop_2);
		shop3 = (ImageView) view.findViewById(R.id.shop_3);

		// 分类
		dishKind1 = (TextView) view.findViewById(R.id.dish_kind_1);
		dishKind2 = (TextView) view.findViewById(R.id.dish_kind_2);
		dishKind3 = (TextView) view.findViewById(R.id.dish_kind_3);
		dishKind4 = (TextView) view.findViewById(R.id.dish_kind_4);
		dishKind5 = (TextView) view.findViewById(R.id.dish_kind_5);
		dishKind6 = (TextView) view.findViewById(R.id.dish_kind_6);
		dishKind7 = (TextView) view.findViewById(R.id.dish_kind_7);
		dishKind8 = (TextView) view.findViewById(R.id.dish_kind_8);

		shop1Dish1 = (TextView) view.findViewById(R.id.shop_1_dish_1);
		shop1Dish2 = (TextView) view.findViewById(R.id.shop_1_dish_2);
		shop1Dish3 = (TextView) view.findViewById(R.id.shop_1_dish_3);
		shop1Dish4 = (TextView) view.findViewById(R.id.shop_1_dish_4);
		shop1Dish5 = (TextView) view.findViewById(R.id.shop_1_dish_5);
		shop1Dish6 = (TextView) view.findViewById(R.id.shop_1_dish_6);
		shop1Dish7 = (TextView) view.findViewById(R.id.shop_1_dish_7);
		shop1Dish8 = (TextView) view.findViewById(R.id.shop_1_dish_8);
		shop1Dish9 = (RelativeLayout) view.findViewById(R.id.shop_1_dish_9);

		shop2Dish1 = (TextView) view.findViewById(R.id.shop_2_dish_1);
		shop2Dish2 = (TextView) view.findViewById(R.id.shop_2_dish_2);
		shop2Dish3 = (TextView) view.findViewById(R.id.shop_2_dish_3);
		shop2Dish4 = (TextView) view.findViewById(R.id.shop_2_dish_4);
		shop2Dish5 = (TextView) view.findViewById(R.id.shop_2_dish_5);
		shop2Dish6 = (TextView) view.findViewById(R.id.shop_2_dish_6);
		shop2Dish7 = (TextView) view.findViewById(R.id.shop_2_dish_7);
		shop2Dish8 = (TextView) view.findViewById(R.id.shop_2_dish_8);
		shop2Dish9 = (RelativeLayout) view.findViewById(R.id.shop_2_dish_9);

		shop3Dish1 = (TextView) view.findViewById(R.id.shop_3_dish_1);
		shop3Dish2 = (TextView) view.findViewById(R.id.shop_3_dish_2);
		shop3Dish3 = (TextView) view.findViewById(R.id.shop_3_dish_3);
		shop3Dish4 = (TextView) view.findViewById(R.id.shop_3_dish_4);
		shop3Dish5 = (TextView) view.findViewById(R.id.shop_3_dish_5);
		shop3Dish6 = (TextView) view.findViewById(R.id.shop_3_dish_6);
		shop3Dish7 = (TextView) view.findViewById(R.id.shop_3_dish_7);
		shop3Dish8 = (TextView) view.findViewById(R.id.shop_3_dish_8);
		shop3Dish9 = (RelativeLayout) view.findViewById(R.id.shop_3_dish_9);

		mProgressDialog = new MPr(getActivity());

		// 设置精品推荐的事件
		recom1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoCategoryPage(0, "精品推荐", 1);

			}
		});

		mRefreshableView.setRefreshListener(new RefreshListener() {

			@Override
			public void onRefresh(Re view) {
				// TODO Auto-generated method stub
				refresh();
			}
		});

		// 从网络获取数据

		refresh();
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	public void refresh() {

		Li.printlnLog("----------------------------is refreshing");
		Runnable runnable = new Runnable() {
			@Override
			public void run() {

				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mProgressDialog.show("加载中...");
					}
				});

				Li.printlnLog("homePageUrl:"
						+ ConfigurationFiles.HTTP_HOME_PATH);
				String homePageString = Ht.SendHttpClientPost(
						ConfigurationFiles.HTTP_HOME_PATH, null, "utf-8",
						getActivity());
				Li.printlnLog("homePageString:" + homePageString);
				Message message = Message.obtain();
				if (!homePageString.equals(ConfigurationFiles.HTTP_ERROR)) {
					if (!homePageString.equals("[]")) {
						if (homePageString
								.equals(ConfigurationFiles.GET_INFORMATION_FAIL)) {
							// 空
							message.what = ConfigurationFiles.NULL;
							handler.sendMessage(message);
						} else {
							FakeHomePage fakeHomePage = (FakeHomePage) Fa
									.getSignal(homePageString,
											FakeHomePage.class);
							message.what = ConfigurationFiles.HOME_MESSAGE;
							message.obj = fakeHomePage;
							handler.sendMessage(message);
						}

					} else {
						message.what = ConfigurationFiles.NULL;
						handler.sendMessage(message);
					}
				} else {
					message.what = ConfigurationFiles.NO_INTERNET;
					handler.sendMessage(message);
				}
			}
		};
		new Thread(runnable).start();
	}

	public void setCategory(ArrayList<FakeHomeCategory> categoryList) {
		FakeHomeCategory fakeHomeCategory;
		if (categoryList.size() < 2) {
			dishKindContainer1.setVisibility(LinearLayout.GONE);
			dishKindContainer2.setVisibility(LinearLayout.GONE);
		} else if (categoryList.size() < 6) 
		{
			dishKindContainer1.setVisibility(LinearLayout.VISIBLE);
			dishKindContainer2.setVisibility(LinearLayout.GONE);
			if (2 < categoryList.size()) {
				fakeHomeCategory = categoryList.get(2);
				Li.printlnLog("dishKind1:" + dishKind1);
				dishKind1.setText(fakeHomeCategory.getCategoryName());
				final int categoryId1 = fakeHomeCategory.getCategoryId();
				final String category1 = fakeHomeCategory.getCategoryName();
				dishKind1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// 设置跳转事件
						gotoCategoryPage(categoryId1, category1, 2);
					}
				});
			} else {
				dishKind1.setText("");
			}

			if (3 < categoryList.size()) {
				fakeHomeCategory = categoryList.get(3);
				dishKind2.setText(fakeHomeCategory.getCategoryName());
				final int categoryId2 = fakeHomeCategory.getCategoryId();
				final String category2 = fakeHomeCategory.getCategoryName();
				dishKind2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// 设置跳转事件
						gotoCategoryPage(categoryId2, category2, 2);
					}
				});
			} else {
				dishKind2.setText("");
			}

			if (4 < categoryList.size()) {
				fakeHomeCategory = categoryList.get(4);
				dishKind3.setText(fakeHomeCategory.getCategoryName());
				final int categoryId3 = fakeHomeCategory.getCategoryId();
				final String category3 = fakeHomeCategory.getCategoryName();
				dishKind3.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// 设置跳转事件
						gotoCategoryPage(categoryId3, category3, 2);
					}
				});
			} else {
				dishKind3.setText("");
			}
			if (5 < categoryList.size()) {
				fakeHomeCategory = categoryList.get(5);
				dishKind4.setText(fakeHomeCategory.getCategoryName());
				final int categoryId4 = fakeHomeCategory.getCategoryId();
				final String category4 = fakeHomeCategory.getCategoryName();
				dishKind4.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// 设置跳转事件
						gotoCategoryPage(categoryId4, category4, 2);
					}
				});
			} else {
				dishKind4.setText("");
			}

		} else {
			dishKindContainer1.setVisibility(LinearLayout.VISIBLE);
			dishKindContainer2.setVisibility(LinearLayout.VISIBLE);
			if (2 < categoryList.size()) {
				fakeHomeCategory = categoryList.get(2);
				dishKind1.setText(fakeHomeCategory.getCategoryName());
				final int categoryId1 = fakeHomeCategory.getCategoryId();
				final String category1 = fakeHomeCategory.getCategoryName();
				dishKind1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// 设置跳转事件
						gotoCategoryPage(categoryId1, category1, 2);
					}
				});

			} else {
				dishKind1.setText("");
			}

			if (3 < categoryList.size()) {
				fakeHomeCategory = categoryList.get(3);
				dishKind2.setText(fakeHomeCategory.getCategoryName());
				final int categoryId2 = fakeHomeCategory.getCategoryId();
				final String category2 = fakeHomeCategory.getCategoryName();
				dishKind2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// 设置跳转事件
						gotoCategoryPage(categoryId2, category2, 2);
					}
				});
			} else {
				dishKind2.setText("");
			}

			if (4 < categoryList.size()) {
				fakeHomeCategory = categoryList.get(4);
				dishKind3.setText(fakeHomeCategory.getCategoryName());
				final int categoryId3 = fakeHomeCategory.getCategoryId();
				final String category3 = fakeHomeCategory.getCategoryName();
				dishKind3.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// 设置跳转事件
						gotoCategoryPage(categoryId3, category3, 2);
					}
				});
			} else {
				dishKind3.setText("");
			}
			if (5 < categoryList.size()) {
				fakeHomeCategory = categoryList.get(5);
				dishKind4.setText(fakeHomeCategory.getCategoryName());
				final int categoryId4 = fakeHomeCategory.getCategoryId();
				final String category4 = fakeHomeCategory.getCategoryName();
				dishKind4.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// 设置跳转事件
						gotoCategoryPage(categoryId4, category4, 2);
					}
				});
			} else {
				dishKind4.setText("");
			}

			if (6 < categoryList.size()) {
				fakeHomeCategory = categoryList.get(6);
				dishKind5.setText(fakeHomeCategory.getCategoryName());
				final int categoryId5 = fakeHomeCategory.getCategoryId();
				final String category5 = fakeHomeCategory.getCategoryName();
				dishKind5.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// 设置跳转事件
						gotoCategoryPage(categoryId5, category5, 2);
					}
				});
			} else {
				dishKind5.setText("");
			}

			if (7 < categoryList.size()) {
				fakeHomeCategory = categoryList.get(7);
				dishKind6.setText(fakeHomeCategory.getCategoryName());
				final int categoryId6 = fakeHomeCategory.getCategoryId();
				final String category6 = fakeHomeCategory.getCategoryName();
				dishKind6.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// 设置跳转事件
						gotoCategoryPage(categoryId6, category6, 2);
					}
				});
			} else {
				dishKind6.setText("");
			}

			if (8 < categoryList.size()) {
				fakeHomeCategory = categoryList.get(8);
				dishKind7.setText(fakeHomeCategory.getCategoryName());
				final int categoryId7 = fakeHomeCategory.getCategoryId();
				final String category7 = fakeHomeCategory.getCategoryName();
				dishKind7.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// 设置跳转事件
						gotoCategoryPage(categoryId7, category7, 2);
					}
				});
			} else {
				dishKind7.setText("");
			}
			if (9 < categoryList.size()) {
				fakeHomeCategory = categoryList.get(9);
				dishKind8.setText(fakeHomeCategory.getCategoryName());
				final int categoryId8 = fakeHomeCategory.getCategoryId();
				final String category8 = fakeHomeCategory.getCategoryName();
				dishKind8.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// 设置跳转事件
						gotoCategoryPage(categoryId8, category8, 2);
					}
				});
			} else {
				dishKind8.setText("");
			}

		}
	}

	public void setHotStoreTop3(ArrayList<FakeHomeRestaurant> hotStoreTop3) {
		if (hotStoreTop3.size() > 1) {
			FakeHomeRestaurant fakeHomeRestaurant;
			// 设置第一个hotStore
			fakeHomeRestaurant = hotStoreTop3.get(0);
			// 设置图片
			Fi.initImageBackground(fakeHomeRestaurant.getPhoto(), shop1,
					1);
			// 设置图片的事件
			final int restId1 = fakeHomeRestaurant.getRestId();
			shop1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					gotoStoreDetailPage(restId1);
				}
			});
			ArrayList<Map<String, Object>> foodList = fakeHomeRestaurant
					.getFoodList();
			int foodSize = foodList.size();
			if (0 < foodSize) {
				shop1Dish1.setText((foodList.get(0).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(0).get("id")
						.toString());
				shop1Dish1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop1Dish1.setText("");
			}

			if (1 < foodSize) {
				shop1Dish2.setText((foodList.get(1).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(1).get("id")
						.toString());
				shop1Dish2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop1Dish2.setText("");
			}

			if (2 < foodSize) {
				shop1Dish3.setText((foodList.get(2).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(0).get("id")
						.toString());
				shop1Dish3.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop1Dish3.setText("");
			}

			if (3 < foodSize) {
				shop1Dish4.setText((foodList.get(3).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(3).get("id")
						.toString());
				shop1Dish4.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop1Dish4.setText("");
			}

			if (4 < foodSize) {
				shop1Dish5.setText((foodList.get(4).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(4).get("id")
						.toString());
				shop1Dish5.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop1Dish5.setText("");
			}

			if (5 < foodSize) {
				shop1Dish6.setText((foodList.get(5).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(5).get("id")
						.toString());
				shop1Dish6.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop1Dish6.setText("");
			}

			if (6 < foodSize) {
				shop1Dish7.setText((foodList.get(6).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(6).get("id")
						.toString());
				shop1Dish7.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop1Dish7.setText("");
			}
			if (7 < foodSize) {
				shop1Dish8.setText((foodList.get(7).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(7).get("id")
						.toString());
				shop1Dish8.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop1Dish8.setText("");
			}
			if (8 < foodSize) {
				shop1Dish9.setVisibility(TextView.VISIBLE);
				shop1Dish9.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoStoreDetailPage(restId1);
					}
				});
			}
			// 设置第二个topSTore2
			fakeHomeRestaurant = hotStoreTop3.get(1);
			// 设置图片
			Fi.initImageBackground(fakeHomeRestaurant.getPhoto(), shop2,
					1);
			// 设置图片的事件
			final int restId2 = fakeHomeRestaurant.getRestId();
			shop2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					gotoStoreDetailPage(restId2);
				}
			});
			foodList = fakeHomeRestaurant.getFoodList();
			foodSize = foodList.size();
			if (0 < foodSize) {
				shop2Dish1.setText((foodList.get(0).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(0).get("id")
						.toString());
				shop2Dish1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop2Dish1.setText("");
			}

			if (1 < foodSize) {
				shop2Dish2.setText((foodList.get(1).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(1).get("id")
						.toString());
				shop2Dish2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop2Dish2.setText("");
			}

			if (2 < foodSize) {
				shop2Dish3.setText((foodList.get(2).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(0).get("id")
						.toString());
				shop2Dish3.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop2Dish3.setText("");
			}

			if (3 < foodSize) {
				shop2Dish4.setText((foodList.get(3).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(3).get("id")
						.toString());
				shop2Dish4.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop2Dish4.setText("");
			}

			if (4 < foodSize) {
				shop2Dish5.setText((foodList.get(4).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(4).get("id")
						.toString());
				shop2Dish5.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop2Dish5.setText("");
			}

			if (5 < foodSize) {
				shop2Dish6.setText((foodList.get(5).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(5).get("id")
						.toString());
				shop2Dish6.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop2Dish6.setText("");
			}

			if (6 < foodSize) {
				shop2Dish7.setText((foodList.get(6).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(6).get("id")
						.toString());
				shop2Dish7.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop2Dish7.setText("");
			}

			if (7 < foodSize) {
				shop2Dish8.setText((foodList.get(7).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(7).get("id")
						.toString());
				shop2Dish8.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop2Dish8.setText("");
			}

			if (8 < foodSize) {
				shop2Dish9.setVisibility(TextView.VISIBLE);
				shop2Dish9.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						gotoStoreDetailPage(restId2);
					}
				});
			}

			// 设置第三个热店
			fakeHomeRestaurant = hotStoreTop3.get(2);
			// 设置图片
			Fi.initImageBackground(fakeHomeRestaurant.getPhoto(), shop3,
					1);
			// 设置图片的事件
			final int restId3 = fakeHomeRestaurant.getRestId();
			shop3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					gotoStoreDetailPage(restId3);
				}
			});
			foodList = fakeHomeRestaurant.getFoodList();
			foodSize = foodList.size();
			if (0 < foodSize) {
				shop3Dish1.setText((foodList.get(0).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(0).get("id")
						.toString());
				shop3Dish1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop3Dish1.setText("");
			}

			if (1 < foodSize) {
				shop3Dish2.setText((foodList.get(1).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(1).get("id")
						.toString());
				shop3Dish2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop3Dish2.setText("");
			}

			if (2 < foodSize) {
				shop3Dish3.setText((foodList.get(2).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(0).get("id")
						.toString());
				shop3Dish3.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop3Dish3.setText("");
			}

			if (3 < foodSize) {
				shop3Dish4.setText((foodList.get(3).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(3).get("id")
						.toString());
				shop3Dish4.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop3Dish4.setText("");
			}

			if (4 < foodSize) {
				shop3Dish5.setText((foodList.get(4).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(4).get("id")
						.toString());
				shop3Dish5.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop3Dish5.setText("");
			}

			if (5 < foodSize) {
				shop3Dish6.setText((foodList.get(5).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(5).get("id")
						.toString());
				shop3Dish6.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop3Dish6.setText("");
			}

			if (6 < foodSize) {
				shop3Dish7.setText((foodList.get(6).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(6).get("id")
						.toString());
				shop3Dish7.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop3Dish7.setText("");
			}

			if (7 < foodSize) {
				shop3Dish8.setText((foodList.get(7).get("name")).toString());
				final int foodId1 = Integer.parseInt(foodList.get(7).get("id")
						.toString());
				shop3Dish8.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoFoodDetailPage(foodId1);
					}
				});
			} else {
				shop3Dish8.setText("");
			}

			if (8 < foodSize) {
				shop3Dish9.setVisibility(TextView.VISIBLE);
				shop3Dish9.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						gotoStoreDetailPage(restId3);
					}
				});
			}
		} else {
			Toast.makeText(getActivity(), "网络数据格式有误", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void gotoStoreDetailPage(int storeId) {
		Intent intent = new Intent();
		intent.putExtra("storeId", storeId);
		intent.setClass(getActivity(), St.class);
		startActivity(intent);
	}

	private void gotoFoodDetailPage(int foodId) {
		Intent intent = new Intent();
		intent.putExtra("foodId", foodId);
		intent.setClass(getActivity(), D.class);
		startActivity(intent);
	}

	/*
	 * flag参数说明：0——搜索界面，1——精品推荐，2——食品种类界面
	 */
	private void gotoCategoryPage(int categoryId, String categoryString,
			int flag) {
		Intent intent = new Intent();
		intent.putExtra("flag", flag);
		intent.putExtra("categoryId", categoryId);
		intent.putExtra("categoryString", categoryString);
		intent.setClass(getActivity(), F.class);
		startActivity(intent);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		adsAutoScroll(isVisibleToUser);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (stopAutoScroll) {
			adsAutoScroll(true);
			stopAutoScroll = false;
		}
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		adsAutoScroll(false);
		stopAutoScroll = true;
	}

	private boolean stopAutoScroll = false;

	private void adsAutoScroll(boolean flag) {
		if (flag) {
			if (naverShow == ConfigurationFiles.NEVER_SHOW) {

			} else {
				imgSwitcher.setAutoPlay(true);
				imgSwitcher.startTimer(2000);
			}
		} else {
			if (naverShow == ConfigurationFiles.NEVER_SHOW) {

			} else {
				imgSwitcher.stopTimer();
				imgSwitcher.setAutoPlay(false);
			}
		}
	}

	/*
	 * 首页图片跳转 type = 0代表菜品，1代表餐馆
	 */
	public void setAdsClickListener(int type, int id) {

		Intent intent = new Intent();
		if (type == 0) {
			intent.putExtra("foodId", id);
			intent.setClass(getActivity(), D.class);
		} else if (type == 1) {
			intent.putExtra("storeId", id);
			intent.setClass(getActivity(), St.class);
		}
		startActivity(intent);
	}

	/**
	 * 设置界面的可见性
	 * 
	 * @param which
	 * <br>
	 *            0 界面置空<br>
	 *            1 显示内容<br>
	 *            2 显示断网提示
	 */
	private void showWhichLayout(int which) {
		if (contentScroll != null && noNetworkLayout != null) {

			switch (which) {
			case 0:
				contentScroll.setVisibility(ScrollView.GONE);
				noNetworkLayout.setVisibility(LinearLayout.GONE);
				break;
			case 1:
				contentScroll.setVisibility(ScrollView.VISIBLE);
				noNetworkLayout.setVisibility(LinearLayout.GONE);
				break;
			case 2:
				contentScroll.setVisibility(ScrollView.GONE);
				noNetworkLayout.setVisibility(LinearLayout.VISIBLE);
				break;
			default:
				break;
			}

		} else {
			Li.printlnLog("界面包含空内容！");

		}

	}

}