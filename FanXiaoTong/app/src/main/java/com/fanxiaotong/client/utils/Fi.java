package com.fanxiaotong.client.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

import com.fanxiaotong.client.config.ConfigurationFiles;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Fi {
	public final static String BASE_PATH = ConfigurationFiles.HTTP_PICTURE_PATH;
	//手机的上下文环境
	public final static String ALBUM_PATH = Environment.getExternalStorageDirectory().getPath() + "/fanxiaotong/";
	/** 
	 * 保存文件 
	 * @param bm 
	 * @param fileName 
	 * @throws IOException 
	 */  
	public static void saveFile(Drawable drawable, String fullFileName) throws IOException {
		Bitmap bm = ((BitmapDrawable)drawable).getBitmap();
		String fileFullPath = ALBUM_PATH;
		String fileName = "";
		StringTokenizer stoken=new StringTokenizer(fullFileName,"/");
		for(int i=0;i<=stoken.countTokens();i++)
		{
			fileFullPath = fileFullPath+ stoken.nextToken()+"/";
		}
		Li.printlnLog("fileFullPath:"+fileFullPath);
		fileName = stoken.nextToken();
		Li.printlnLog("fileName == " + fileName);
		StringTokenizer stoken1=new StringTokenizer(fileName,".");
		fileName = stoken1.nextToken();
		File dirFile = new File(fileFullPath);  
		if(!dirFile.exists()){  
			dirFile.mkdirs();
		}else if (dirFile.isDirectory()){
			File myCaptureFile = new File(fileFullPath + fileName);  
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));  
			bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);  
			bos.flush();  
			bos.close();  
		}

	}  

	public static boolean isExist(String path)
	{
		StringTokenizer newPath=new StringTokenizer(path,".");
		path = newPath.nextToken();
		Li.printlnLog("是否存在的路径："+path);
		File dirFile = new File(ALBUM_PATH+path);
		Li.printlnLog("是否存在："+dirFile.exists());
		return dirFile.exists();
	}
	public static String getImagePathByImageURL(String imageURL)
	{
		StringTokenizer newPath=new StringTokenizer(imageURL,".");
		String imagePath = newPath.nextToken();
		Li.printlnLog("++++++++++++++++++++++++++++++++++");
		Li.printlnLog("imagePath:"+imagePath);
		Li.printlnLog("++++++++++++++++++++++++++++++++++");
		return imagePath;
	}
	/*
	 * flag参数说明：如果为1代表是不图片需要圆角处理，0代表需要圆角处理
	 */
	public static void initImageBackground(String photoPath,ImageView imageView,int flag)
	{
		I.loadImage(BASE_PATH,photoPath,imageView,flag);
		/*if(!FileUtils.isExist(photoPath))
		{
			ImageLoader.loadImage(ConfigurationFiles.HTTP_PICTURE_PATH , photoPath,imageView,flag);
		}else
		{
			BitmapDrawable bitmapDrawable = new BitmapDrawable(FileUtils.ALBUM_PATH+photoPath);
			if(flag==1)
			{
				imageView.setBackgroundDrawable(ImageProcessor.getRoundCornerDrawable(bitmapDrawable, 0));
			}else if(flag == 2)
			{
				imageView.setBackgroundDrawable(ImageProcessor.toRoundBitmap(bitmapDrawable));
			}else
			{
				imageView.setBackgroundDrawable(ImageProcessor.getRoundCornerDrawable(bitmapDrawable, 30));
			}
		}*/
	}

	@SuppressLint("NewApi")
	public static void iniImageSrc(String photoPath,ImageView imageView)
	{
		I.loadImageSrc(BASE_PATH,photoPath,imageView);
		/*if(!FileUtils.isExist(photoPath))
		{
			
			ImageLoader.loadImageSrc(BASE_PATH,photoPath,imageView);
		}else
		{
			@SuppressWarnings("deprecation")
			BitmapDrawable bitmapDrawable = new BitmapDrawable(FileUtils.ALBUM_PATH+photoPath);
			imageView.setImageDrawable(bitmapDrawable);
		}*/
	}
	//detailFood
	/*
	 * 	ImageLoader.detailFoodInformationLoadImage(
						ConfigurationFiles.HTTP_PICTURE_PATH
						+ fakeFood.getPhoto(), announcementLayout,
						imageHandler);
	 * 
	 */
	@SuppressWarnings("deprecation")

	public static void detailFoodLogoImage(String photoPath,RelativeLayout announcementLayout,Handler imageHandler)
	{
		if(!Fi.isExist(photoPath))
		{
			I.detailFoodInformationLoadImage(ConfigurationFiles.HTTP_PICTURE_PATH+photoPath, announcementLayout,imageHandler);
		}else
		{
			BitmapDrawable bitmapDrawable = new BitmapDrawable(Fi.ALBUM_PATH+getImagePathByImageURL(photoPath));
			announcementLayout.setBackgroundDrawable(bitmapDrawable);
		}
	}
	
	
	
	
}
