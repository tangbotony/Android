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
	   //Ϊ�˼ӿ��ٶȣ����ڴ��п������棨��ҪӦ�����ظ�ͼƬ�϶�ʱ������ͬһ��ͼƬҪ��α����ʣ�������ListViewʱ���ع�����
	    public Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();
	    private ExecutorService executorService = Executors.newFixedThreadPool(10);    //�̶�10���߳���ִ������
	    private final Handler handler=new Handler();
	    public Drawable loadDrawable(final String baseUrl,final String imageUrl1, final ImageCallback callback) {
	        //���������ʹӻ�����ȡ������
	    	final String fullUrl = baseUrl+imageUrl1;
	        if (imageCache.containsKey(fullUrl)) {
	        	//�ڴ��������ͼƬ
	            SoftReference<Drawable> softReference = imageCache.get(fullUrl);
	            if (softReference.get() != null) {
	                return softReference.get();
	            }
	        }
	        //������û��ͼ�����������ȡ�����ݣ�����ȡ�������ݻ��浽�ڴ���
	         executorService.submit(new Runnable() {
	            public void run() {
	                try {
	                	if(Fi.isExist(imageUrl1))
	                	{
	                		//�ӻ��汾��������ȡͼƬ
	                		String newImagePath = Fi.ALBUM_PATH+Fi.getImagePathByImageURL(imageUrl1);
	                		Li.printlnLog("111111111111111111111111111111111111111");
	                		Li.printlnLog("���ػ�ȡͼƬ��·����"+newImagePath);
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
	                		//��������ͼƬ
	                		final Drawable drawable = Drawable.createFromStream(new URL(fullUrl).openStream(), "image.png");
		                    Li.printlnLog("drawable:"+drawable.toString());
	                		imageCache.put(fullUrl, new SoftReference<Drawable>(drawable));
	                		/*
	                		 * �Ѵ�������ȡ��ͼƬ���뱾��
	                		 */
	                		try {
	                			Li.printlnLog("ͼƬ��imageUrl1��"+imageUrl1);
	        					Li.printlnLog("isExist1:"+Fi.isExist(imageUrl1));
	        					if(!Fi.isExist(imageUrl1))
	        					{
	        						Li.printlnLog("��ͼƬ���뱾��");
	        						Fi.saveFile(drawable, imageUrl1);
	        					}
	        					Li.printlnLog("isExist2:"+Fi.isExist(imageUrl1));
	        				} catch (IOException e) {
	        					// TODO Auto-generated catch block
	        					e.printStackTrace();
	        				}
		                    handler.post(new Runnable() {
		                        public void run() {
		                        	Li.printlnLog("�ص�����������������������������");
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
	     //��������ȡ���ݷ���
	    public static Drawable loadImageFromUrl(String imageUrl) {
	        try {
	            return Drawable.createFromStream(new URL(imageUrl).openStream(), "image.png");
	        } catch (Exception e) {
	            Li.printlnLog(new RuntimeException(e).toString());
	        	return null;
	        }
	    }
	    //����翪�ŵĻص��ӿ�
	    public interface ImageCallback {
	        //ע�� �˷�������������Ŀ������ͼ����Դ
	        public void imageLoaded(Drawable imageDrawable);
	    }
	}