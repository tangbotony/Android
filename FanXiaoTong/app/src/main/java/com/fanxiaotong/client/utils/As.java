package com.fanxiaotong.client.utils;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.drawable.Drawable;
import android.os.Handler;

public class As {
	   //为了加快速度，在内存中开启缓存（主要应用于重复图片较多时，或者同一个图片要多次被访问，比如在ListView时来回滚动）
	    public Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();
	    private ExecutorService executorService = Executors.newFixedThreadPool(10);    //固定10个线程来执行任务
	    private final Handler handler=new Handler();
	    public Drawable loadDrawable(final String baseUrl,final String imageUrl1, final ImageCallback callback) {
	        //如果缓存过就从缓存中取出数据
	    	final String fullUrl = baseUrl+imageUrl1;
	        if (imageCache.containsKey(fullUrl)) {
	        	//内存里面加载图片
	            SoftReference<Drawable> softReference = imageCache.get(fullUrl);
	            if (softReference.get() != null) {
	                return softReference.get();
	            }
	        }
	        //缓存中没有图像，则从网络上取出数据，并将取出的数据缓存到内存中
	         executorService.submit(new Runnable() {
	            public void run() {
	                try {
	                	if(Fi.isExist(imageUrl1))
	                	{
	                		//从缓存本地里面提取图片
	                		String newImagePath = Fi.ALBUM_PATH+Fi.getImagePathByImageURL(imageUrl1);
	                		Li.printlnLog("111111111111111111111111111111111111111");
	                		Li.printlnLog("本地获取图片的路径："+newImagePath);
	                		Li.printlnLog("111111111111111111111111111111111111111");
	                		final Drawable drawable = Drawable.createFromPath(newImagePath);
	                		imageCache.put(fullUrl, new SoftReference<Drawable>(drawable));
		                    handler.post(new Runnable() {
		                        public void run() {
		                           callback.imageLoaded(drawable);
		                        }
		                    });
	                	}else
	                	{
	                		//网络下载图片
	                		final Drawable drawable = Drawable.createFromStream(new URL(fullUrl).openStream(), "image.png");
		                    Li.printlnLog("drawable:"+drawable.toString());
	                		imageCache.put(fullUrl, new SoftReference<Drawable>(drawable));
	                		/*
	                		 * 把从网络下取的图片存入本地
	                		 */
	                		try {
	                			Li.printlnLog("图片的imageUrl1："+imageUrl1);
	        					Li.printlnLog("isExist1:"+Fi.isExist(imageUrl1));
	        					if(!Fi.isExist(imageUrl1))
	        					{
	        						Li.printlnLog("将图片存入本地");
	        						Fi.saveFile(drawable, imageUrl1);
	        					}
	        					Li.printlnLog("isExist2:"+Fi.isExist(imageUrl1));
	        				} catch (IOException e) {
	        					// TODO Auto-generated catch block
	        					e.printStackTrace();
	        				}
		                    handler.post(new Runnable() {
		                        public void run() {
		                        	Li.printlnLog("回调函数。。。。。。。。。。。。");
		                            Li.printlnLog("drawable:"+drawable.toString());
		                        	callback.imageLoaded(drawable);
		                        }
		                    });
	                	}
	                } catch (Exception e) {
	                    throw new RuntimeException(e);
	                }
	            }
	        });
	        return null;
	    }
	     //从网络上取数据方法
	    public static Drawable loadImageFromUrl(String imageUrl) {
	        try {
	            return Drawable.createFromStream(new URL(imageUrl).openStream(), "image.png");
	        } catch (Exception e) {
	            Li.printlnLog(new RuntimeException(e).toString());
	        	return null;
	        }
	    }
	    //对外界开放的回调接口
	    public interface ImageCallback {
	        //注意 此方法是用来设置目标对象的图像资源
	        public void imageLoaded(Drawable imageDrawable);
	    }
	}