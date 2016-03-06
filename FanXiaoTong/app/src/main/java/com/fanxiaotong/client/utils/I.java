package com.fanxiaotong.client.utils;

import java.io.IOException;
import java.net.URL;

import com.fanxiaotong.client.bean.PictureData;
import com.fanxiaotong.client.config.ConfigurationFiles;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class I {
	//private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
	private static As asyncImageLoader3 = new As();
	public  static void detailFoodInformationLoadImage(final String url, final RelativeLayout relativeLayout,final Handler handler) {
		Thread thread = new Thread(){
			@Override
			public void run() {
				Drawable drawable = null;
				try {
					drawable = Drawable.createFromStream(new URL(url).openStream(), "image.png");
					Li.printlnLog("drawable:"+drawable);
				} catch (IOException e) {
				}
				Message message= handler.obtainMessage();
				PictureData pictureData = new PictureData(relativeLayout, drawable);
				message.obj = pictureData;
				message.what = ConfigurationFiles.DETAIL_FOOD_INFORMATION;
				handler.sendMessage(message);
			}
		};
		thread.start();
		thread = null;
	}

	
	
	//载入图片通过ImageView
	public  static void storeLogoLoadImage(final String url, final ImageView imageView,final Handler handler) {
		Thread thread = new Thread(){
			@Override
			public void run() {
				Drawable drawable = null;
				try {
					drawable = Drawable.createFromStream(new URL(url).openStream(), "image.png");
					Li.printlnLog("drawable:"+drawable);
				} catch (IOException e) {
				}
				Message message= handler.obtainMessage();
				PictureData pictureData = new PictureData(imageView, drawable);
				message.obj = pictureData;
				message.what = ConfigurationFiles.STORE_LOGO_IMAGE_MESSAGE;
				handler.sendMessage(message);
			}
		};
		thread.start();
		thread = null;
	}
	@SuppressWarnings("deprecation")
	public static void loadImage(final String baseUrl,final String fullImagePath,final ImageView imageView,final int flag) {
		//如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
		Drawable cacheImage = asyncImageLoader3.loadDrawable(baseUrl,fullImagePath,new As.ImageCallback() {
			//请参见实现：如果第一次加载url时下面方法会执行
			public void imageLoaded(Drawable imageDrawable) {
				if(flag ==1)
				{
					imageView.setBackgroundDrawable(Im.getRoundCornerDrawable(imageDrawable, 0));
				}else if(flag ==2)
				{
					imageView.setBackgroundDrawable(Im.toRoundBitmap(imageDrawable));
				}else
				{
					imageView.setBackgroundDrawable(Im.getRoundCornerDrawable(imageDrawable, 30));
				}
				/*try {
					LittleUtil.printlnLog("isExist:1"+FileUtils.isExist(fullImagePath));
					if(!FileUtils.isExist(fullImagePath))
					{
						FileUtils.saveFile(imageDrawable, fullImagePath);
					}
					LittleUtil.printlnLog("fullImagePath:"+fullImagePath);
					LittleUtil.printlnLog("isExist:"+FileUtils.isExist(fullImagePath));
				} catch (IOException e) {
					e.printStackTrace();
				}*/
			}
		});
		if(cacheImage!=null){
			if(flag ==1)
			{
				imageView.setBackgroundDrawable(Im.getRoundCornerDrawable(cacheImage, 0));
			}else if(flag ==2)
			{
				imageView.setBackgroundDrawable(Im.toRoundBitmap(cacheImage));
			}else
			{
				imageView.setBackgroundDrawable(Im.getRoundCornerDrawable(cacheImage, 30));
			}
		}
	}
	
	//第一个参数是上下文环境，第二个参数是图片的相对位置
	public static void loadImageSrc(final String baseUrl,final String fullImagePath, final ImageView imageView) {
		//如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
		Drawable cacheImage = asyncImageLoader3.loadDrawable(baseUrl,fullImagePath,new As.ImageCallback() {
			//请参见实现：如果第一次加载url时下面方法会执行
			public void imageLoaded(Drawable imageDrawable) {
				Li.printlnLog("开始设置图标：");
				Li.printlnLog("imageView:"+imageView.toString());
				Li.printlnLog("imageDrawable:"+imageDrawable.toString());
				imageView.setImageDrawable(imageDrawable);
				/*try {
					LittleUtil.printlnLog("isExist2:"+FileUtils.isExist(fullImagePath));
					if(!FileUtils.isExist(fullImagePath))
					{
						FileUtils.saveFile(imageDrawable, fullImagePath);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		});
		if(cacheImage!=null){
			imageView.setImageDrawable(cacheImage);
		}
	}
}
