package com.fanxiaotong.client.fragment;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fanxiaotong.client.R;
import com.fanxiaotong.client.activity.A;
import com.fanxiaotong.client.activity.S;
import com.fanxiaotong.client.activity.Sh;
import com.fanxiaotong.client.activity.Sug;
import com.fanxiaotong.client.bean.FakeVersion;
import com.fanxiaotong.client.config.ConfigurationFiles;
import com.fanxiaotong.client.utils.Fa;
import com.fanxiaotong.client.utils.Ht;
import com.fanxiaotong.client.utils.Li;
import com.fanxiaotong.client.utils.U;
import com.fanxiaotong.client.utils.Ut;
import com.fanxiaotong.client.widget.MPr;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Ri extends Fragment {
	private RelativeLayout setting, update, share, suggestion, about, more, quit;
	private Intent intent;
	private Tencent tencent;

	private MPr mProgressDialog;
	final String filePath = Environment.getExternalStorageDirectory()
			+ "/fanxiaotong";
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			int flag = msg.what;
			switch (flag) {
			case ConfigurationFiles.CHECK_UPDATE_MESSAGE:
				String result = (String) msg.obj;
				if (result.equals(ConfigurationFiles.IS_LATEST_VERSION)) {
					Toast.makeText(getActivity(), "当前版本已是最新版 :)",
							Toast.LENGTH_LONG).show();
				} else {
					getDialog(getActivity(), result).show();
				}
				break;
			case ConfigurationFiles.HTTP_ERROR_MESSAGE:
				Toast.makeText(getActivity(), "网络连接错误！", Toast.LENGTH_LONG)
						.show();
				break;
			default:
				break;
			}
			mProgressDialog.dismiss();
		};
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.right, null);
		tencent = Tencent.createInstance("101006494", getActivity());
		setting = (RelativeLayout) view.findViewById(R.id.setting);
		update = (RelativeLayout) view.findViewById(R.id.update);
		share = (RelativeLayout) view.findViewById(R.id.share);
		suggestion = (RelativeLayout) view.findViewById(R.id.suggestion);
		about = (RelativeLayout) view.findViewById(R.id.about);
		more = (RelativeLayout) view.findViewById(R.id.more);
		quit = (RelativeLayout) view.findViewById(R.id.quit);
		mProgressDialog = new MPr(getActivity());

		setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(getActivity(), S.class);
				startActivity(intent);

			}
		});
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkRefresh();
			}
		});
		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Bundle params = new Bundle();
				params.putString(Tencent.SHARE_TO_QQ_TITLE, "【饭小桶团队——QQ互联功能】");
				params.putString(Tencent.SHARE_TO_QQ_SUMMARY,
						"欢迎使用饭小桶软件，饭小桶——我们身边的订餐专家，更多信息请访问饭小桶官网：www.fanxiaotong.com——自饭小桶客户端");
				params.putString(Tencent.SHARE_TO_QQ_TARGET_URL,
						ConfigurationFiles.targetUrl);
				// 支持传多个imageUrl,传图片时使用
				ArrayList<String> imageUrls = new ArrayList<String>();
				params.putStringArrayList(Tencent.SHARE_TO_QQ_IMAGE_URL,
						imageUrls);
				tencent.shareToQzone(getActivity(), params, new IUiListener() {
					@Override
					public void onCancel() {
						Ut.toastMessage(getActivity(), "onCancel: ");
					}

					@Override
					public void onComplete(JSONObject response) {
						// TODO Auto-generated method stub
						Ut.toastMessage(getActivity(), "onComplete: "
								+ response.toString());
					}

					@Override
					public void onError(UiError e) {
						// TODO Auto-generated method stub
						Ut.toastMessage(getActivity(), "onComplete: "
								+ e.errorMessage, "e");
					}
				});
			}
		});
		suggestion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent(getActivity(), Sug.class);
				startActivity(intent);
			}
		});
		about.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent(getActivity(), A.class);
				startActivity(intent);

			}
		});
		more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent(getActivity(), Sh.class);
				startActivity(intent);
			}
		});
		quit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
				System.exit(0);
			}
		});
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private Dialog getDialog(final Context context, final String contentString) {
		final Dialog dialog = new Dialog(context, R.style.myDialog);
		dialog.setContentView(R.layout.update_dialog);
		ImageView closeBtn = (ImageView) dialog.findViewById(R.id.close_btn);
		TextView versionName = (TextView) dialog
				.findViewById(R.id.version_name);
		TextView publishDate = (TextView) dialog
				.findViewById(R.id.publish_date);
		TextView describe = (TextView) dialog.findViewById(R.id.describe);
		TextView cancelBtn = (TextView) dialog.findViewById(R.id.cancel_btn);
		TextView submitBtn = (TextView) dialog.findViewById(R.id.submit_btn);
		final FakeVersion fakeVersion = Fa.getSignal(contentString,
				FakeVersion.class);
		versionName.setText(fakeVersion.getVersion());
		publishDate.setText(fakeVersion.getReleasetime());
		describe.setText(fakeVersion.getDescribe());
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		closeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		submitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				download(getActivity(), fakeVersion.getUlr());
				dialog.dismiss();
			}
		});

		return dialog;
	}

	private void checkRefresh() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						mProgressDialog.show("检查更新");
					}
				});
				Map<String, String> map = new HashMap<String, String>();
				map.put("flagBit", ConfigurationFiles.IM_A_CUSTOMER);
				map.put("versionID", String.valueOf(Li
						.getAppVersionCode(getActivity())));

				String url = ConfigurationFiles.HTTP_CHECK_UPDATE_PATH;

				Li.printlnLog("检查更新！！！！！！！！！！！！！！！！！！");
				String result = Ht.SendHttpClientPost(url, map,
						"utf-8", getActivity());

				Message message = Message.obtain();
				if (result != null
						&& !result.equals(ConfigurationFiles.HTTP_ERROR)) {
					message.what = ConfigurationFiles.CHECK_UPDATE_MESSAGE;
					message.obj = result;
					handler.sendMessage(message);
				} else {
					message.what = ConfigurationFiles.HTTP_ERROR_MESSAGE;
					message.obj = "";
					handler.sendMessage(message);
				}
			}
		};

		new Thread(runnable).start();
	}

	private void download(Context context, String url) {
		/*
		 * Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		 * context.startActivity(intent);
		 */
		new U(url,filePath, "饭小桶.apk", getActivity()).start();
	}

}
