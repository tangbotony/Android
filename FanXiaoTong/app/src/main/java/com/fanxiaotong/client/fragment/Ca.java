package com.fanxiaotong.client.fragment;

import java.util.ArrayList;
import java.util.List;

import com.fanxiaotong.client.activity.L;
import com.fanxiaotong.client.activity.Sl;
import com.fanxiaotong.client.activity.Su;
import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.mybean.Fa;
import com.fanxiaotong.client.mybean.Si;
import com.fanxiaotong.client.utils.Car;
import com.fanxiaotong.client.utils.Fi;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.widget.SC;
import com.fanxiaotong.client.widget.SC.OnCartOrderClickListener;
import com.fanxiaotong.client.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Ca extends Fragment {
	private static int uiFlag = ConfigurationFiles.NEVER_SHOW;
	private static float allCoast = 0;
	public static Fa fakeOrder;
	// 保存从数据库获取的订单数据，课直接使用作初始化购物车中的单个订单
	// 使用前判断是否为空
	public static boolean isVisible = false;// 代表该页是否对用户可见，当不可见时，后台的检查到订单有更新时不需要更改该页的UI，当对用户可见时，需要更改UI
	public static ArrayList<SimpleOrder> orderList = new ArrayList<Ca.SimpleOrder>();
	public static LinearLayout foodOrderContainer;
	// private String fullImagePath;
	public static RelativeLayout submitCost;
	private TextView totalCost;
	public static LinearLayout unloginCart;
	public static LinearLayout emptyCart;
	private TextView totalCount;
	private int foodCount;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.cart, null);
		foodOrderContainer = (LinearLayout) view
				.findViewById(R.id.food_order_container);
		submitCost = (RelativeLayout) view.findViewById(R.id.submit_cost);
		unloginCart = (LinearLayout) view.findViewById(R.id.unlogin_order);
		emptyCart = (LinearLayout) view.findViewById(R.id.empty_cart);
		totalCount = (TextView) view.findViewById(R.id.total_count);
		submitCost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// getDialog(getActivity()).show();
				if (orderList.size() != 0) {
					productOrder();
					Intent intent = new Intent(getActivity(),
							Su.class);
					startActivity(intent);
				} else {
					Toast.makeText(getActivity(), "订单为空，无法提交！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		totalCost = (TextView) view.findViewById(R.id.total_cost);
		fakeOrder = new Fa();
		return view;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Li.printlnLog("on---start---isLogin: " + Sl.loginFlag);
		Li.printlnLog("on---start---uiFlag: " + uiFlag);
		if (uiFlag == ConfigurationFiles.NEVER_SHOW) {
			if (Sl.loginFlag) {
				refresh();
				setUiFlag(ConfigurationFiles.HAVE_SHOWED);
			} else {/*
					 * Toast.makeText(getActivity(), "请登录后在查看购餐车！",
					 * Toast.LENGTH_SHORT).show();
					 */
			}
		}
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);

		Li.printlnLog("visible---cartPage: " + isVisibleToUser);
		if (isVisibleToUser == true) {
			isVisible = true;
			if (uiFlag == ConfigurationFiles.NEVER_SHOW) {
				if (Sl.loginFlag) {
					refresh();
					setUiFlag(ConfigurationFiles.HAVE_SHOWED);
				} else {/*
						 * Toast.makeText(getActivity(), "请登录后在查看购餐车！",
						 * Toast.LENGTH_SHORT).show();
						 */
				}
			}
			refresh();
		} else if (isVisibleToUser == false) {
			isVisible = false;
		}
	}

	public void refresh() {
		foodOrderContainer.removeAllViews();
		if (Sl.loginFlag) {
			unloginCart.setVisibility(LinearLayout.GONE);
			initDataFromCartDB();
			SC singleCartOrderView;
			allCoast = 0;
			foodCount = 0;
			for (int i = 0; i < orderList.size(); i++) {
				final SimpleOrder simpleOrder = orderList.get(i);
				singleCartOrderView = new SC(getActivity());
				singleCartOrderView.setFoodName(simpleOrder.getFoodName());
				singleCartOrderView.setFoodCount(simpleOrder.getCount() + "份");
				foodCount = foodCount + simpleOrder.getCount();
				singleCartOrderView.setFoodCost(simpleOrder.getCost() + "");
				singleCartOrderView.setNotice("备注："
						+ (simpleOrder.getNotice().equals("") ? "无"
								: simpleOrder.getNotice()));
				allCoast = allCoast + simpleOrder.getCost();
				Li.printlnLog("图片路径：" + simpleOrder.getFoodPhotoName());
				// 修改
				Fi.initImageBackground(simpleOrder.getFoodPhotoName(),
						singleCartOrderView.getFoodLogo(), 2);

				singleCartOrderView.setJardiniere("配菜："
						+ simpleOrder.getJardiniere());
				singleCartOrderView.setFlavor("口味：" + simpleOrder.getFlavor());

				foodOrderContainer.addView(singleCartOrderView);
				singleCartOrderView
						.setOnCartOrderClickListener(new OnCartOrderClickListener() {
							@Override
							public void onClick() {
								getDialog(getActivity(), simpleOrder).show();
							}
						});
			}

			totalCount.setText(foodCount + "");
			totalCost.setText(allCoast + "");
			// 再加上
			if (orderList.size() != 0) {
				foodOrderContainer.addView(getEmptyLayout());
				emptyCart.setVisibility(LinearLayout.GONE);
				unloginCart.setVisibility(LinearLayout.GONE);
				submitCost.setVisibility(RelativeLayout.VISIBLE);
				startAnim();
				V.cartBtn
						.setBackgroundResource(R.drawable.cart_noticed_normal);
				if (isVisible) {
					V.cartBtn
							.setBackgroundResource(R.drawable.cart_noticed_actived);
				}
			} else {
				unloginCart.setVisibility(LinearLayout.GONE);
				emptyCart.setVisibility(LinearLayout.VISIBLE);
				submitCost.setVisibility(RelativeLayout.GONE);
				V.cartBtn
						.setBackgroundResource(R.drawable.cart_normal);
				if (isVisible) {
					V.cartBtn
							.setBackgroundResource(R.drawable.cart_actived);
				}
			}
		} else {
			emptyCart.setVisibility(LinearLayout.GONE);
			submitCost.setVisibility(RelativeLayout.GONE);
			unloginCart.setVisibility(LinearLayout.VISIBLE);
		}
	}

	/**
	 * 当出现结算按钮时，开始震动动画
	 */
	private void startAnim() {
		Animation shake = AnimationUtils.loadAnimation(getActivity(),
				R.anim.shake);
		if (submitCost.getVisibility() == RelativeLayout.VISIBLE)
			submitCost.startAnimation(shake);
	}

	private LinearLayout getEmptyLayout() {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				Li.dip2px(getActivity(), 60));
		LinearLayout emptyLayout = new LinearLayout(getActivity());
		emptyLayout.setLayoutParams(params);
		return emptyLayout;
	}

	/**
	 * 从购物车数据库初始化数据
	 * 
	 * @date 2013.12.27
	 */
	@SuppressWarnings("deprecation")
	private void initDataFromCartDB() {
		Car cartDbHelper = new Car(getActivity());
		Cursor orderCursor = cartDbHelper.select();
		orderCursor.requery();
		orderList.clear();
		int orderCount = orderCursor.getCount();
		Li.printlnLog("数据行数：------------" + orderCount);
		if (orderCount > 0) {
			while (orderCursor.moveToNext()) {
				String userPhoneNumber = orderCursor.getString(7);
				if (userPhoneNumber.equals(L.loginUserPhoneNum)) {
					int id = orderCursor.getInt(0);
					int foodId = orderCursor.getInt(1);
					int storeId = orderCursor.getInt(2);
					String foodName = orderCursor.getString(3);
					int count = orderCursor.getInt(4);
					float cost = Float.parseFloat(orderCursor.getString(5));
					String notice = orderCursor.getString(6);
					String foodPhotoName = orderCursor.getString(8);
					String flavor = orderCursor.getString(9);
					String jardiniere = orderCursor.getString(10);
					orderList.add(new SimpleOrder(id, foodId, storeId,
							foodName, count, cost, notice, userPhoneNumber,
							foodPhotoName, flavor, jardiniere));
				}
			}

		} else {
			emptyCart.setVisibility(LinearLayout.VISIBLE);
		}
		orderCursor.close();
		cartDbHelper.close();
	}

	public static int getUiFlag() {
		return uiFlag;
	}

	public static void setUiFlag(int uiFlag) {
		Ca.uiFlag = uiFlag;
	}

	@SuppressLint("HandlerLeak")
	private Dialog getDialog(final Context context, final SimpleOrder order) {
		System.out
				.println("order.equals(null)~~~~~~~~~~~" + order.equals(null));

		final Dialog dialog = new Dialog(context, R.style.myDialog);
		dialog.setContentView(R.layout.order_in_cart_dialog);

		ImageView buyFoodDialogCloseBtn = (ImageView) dialog
				.findViewById(R.id.buy_food_dialog_close_btn);
		TextView jardiniere = (TextView) dialog.findViewById(R.id.jardiniere);
		TextView flavor = (TextView) dialog.findViewById(R.id.flavor);
		final EditText remarkInput = (EditText) dialog
				.findViewById(R.id.remark_input);
		ImageView reduceFoodNumBtn = (ImageView) dialog
				.findViewById(R.id.reduce_food_num_btn);
		ImageView increaseFoodNumBtn = (ImageView) dialog
				.findViewById(R.id.increase_food_num_btn);
		final TextView foodNum = (TextView) dialog.findViewById(R.id.food_num);
		final TextView foodCost = (TextView) dialog
				.findViewById(R.id.food_cost);
		TextView foodNameDialog = (TextView) dialog
				.findViewById(R.id.food_name_dialog);

		TextView deleteBtn = (TextView) dialog.findViewById(R.id.delete_btn);
		TextView modifyBtn = (TextView) dialog.findViewById(R.id.modify_btn);

		foodNameDialog.setText(order.getFoodName());
		jardiniere.setText("配菜：" + order.getJardiniere());
		flavor.setText("口味：" + order.getFlavor());
		foodCost.setText(order.getCost() + "");
		foodNum.setText(String.valueOf(order.getCount()));
		remarkInput.setText(order.getNotice());

		deleteBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Car cartDbHelper = new Car(getActivity());
				cartDbHelper.delete(order.getId());
				dialog.dismiss();
				initDataFromCartDB();
				refresh();
				// Toast.makeText(getActivity(),
				// "点击后删除本地数据库对应内容，然后用initDataFromCartDB()方法从新获取数据,再refresh()",
				// Toast.LENGTH_SHORT).show();
			}
		});

		modifyBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Car cartDbHelper = new Car(getActivity());
				// Integer.parseInt(foodNum.getText().toString()),
				// Integer.parseInt(foodCost.getText().toString()),
				// noticeFiled.getText()+" "
				Li.printlnLog("数量："
						+ Integer.parseInt(foodNum.getText().toString()));
				Li.printlnLog("总价："
						+ Float.parseFloat(foodCost.getText().toString()));
				Li.printlnLog("备注：" + remarkInput.getText() + " ");
				// cartDbHelper.update(id, foodId, storeId, foodName, count,
				// cost, notice);
				cartDbHelper.update(order.getId(), order.getFoodId(),
						order.getStoreId(), order.getFoodName(),
						Integer.parseInt(foodNum.getText().toString()),
						Float.parseFloat(foodCost.getText().toString()),
						remarkInput.getText().toString(),
						order.getUserPhoneNumber(), order.getFoodPhotoName(),
						order.getFlavor(), order.getJardiniere());
				Li.printlnLog("数据更新结束。。。。");
				dialog.dismiss();
				initDataFromCartDB();
				refresh();
			}
		});

		buyFoodDialogCloseBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});

		reduceFoodNumBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int count = 0;// 订餐的个数
				count = Integer.parseInt(foodNum.getText().toString());
				count--;
				float cost = 0.0f;
				cost = order.getCost() / order.getCount() * count;
				if (count > 0) {
					foodNum.setText(String.valueOf(count));
					foodCost.setText(String.valueOf(cost));
				} else {
					Toast.makeText(getActivity(), "至少买一份哦~亲！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		increaseFoodNumBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int count = 0;// 订餐的个数
				count = Integer.parseInt(foodNum.getText().toString());
				count++;
				float cost = 0.0f;
				cost = order.getCost() / order.getCount() * count;
				foodNum.setText(String.valueOf(count));
				foodCost.setText(String.valueOf(cost));
				// changeFoodNum(count,foodNumHandler);
			}
		});

		return dialog;
	}

	public class SimpleOrder {
		private int id;
		private int foodId;
		private int storeId;
		private String foodName;
		private int count;
		private float cost;
		private String notice;
		private String userPhoneNumber;
		private String foodPhotoName;
		private String flavor;
		private String jardiniere;

		public SimpleOrder(int id, int foodId, int storeId, String foodName,
				int count, float cost, String notice, String userPhoneNumber,
				String foodPhotoName, String flavor, String jardiniere) {
			super();
			this.id = id;
			this.foodId = foodId;
			this.storeId = storeId;
			this.foodName = foodName;
			this.count = count;
			this.cost = cost;
			this.notice = notice;
			this.userPhoneNumber = userPhoneNumber;
			this.foodPhotoName = foodPhotoName;
			this.flavor = flavor;
			this.jardiniere = jardiniere;
		}

		public int getId() {
			return id;
		}

		public int getFoodId() {
			return foodId;
		}

		public int getStoreId() {
			return storeId;
		}

		public String getFoodName() {
			return foodName;
		}

		public int getCount() {
			return count;
		}

		public float getCost() {
			return cost;
		}

		public String getNotice() {
			return notice;
		}

		public String getUserPhoneNumber() {
			return userPhoneNumber;
		}

		public String getFoodPhotoName() {
			return foodPhotoName;
		}

		public void setFoodPhotoName(String foodPhotoName) {
			this.foodPhotoName = foodPhotoName;
		}

		public String getFlavor() {
			return flavor;
		}

		public String getJardiniere() {
			return jardiniere;
		}

		public void setFlavor(String flavor) {
			this.flavor = flavor;
		}

		public void setJardiniere(String jardiniere) {
			this.jardiniere = jardiniere;
		}
	}

	public void productOrder() {
		if (orderList.size() != 0) {
			fakeOrder = new Fa();
			List<Si> list = new ArrayList<Si>();
			Si singleOrder;
			for (int i = 0; i < orderList.size(); i++) {
				singleOrder = new Si();
				singleOrder.setCount(orderList.get(i).getCount());
				singleOrder.setFoodId(orderList.get(i).getFoodId());
				singleOrder.setNotice(orderList.get(i).getNotice());
				list.add(singleOrder);
			}
			fakeOrder.setOrderList(list);
		} else {
			Toast.makeText(getActivity(), "个购物车为空，请到商店购买食品！",
					Toast.LENGTH_SHORT).show();
		}
	}
}
