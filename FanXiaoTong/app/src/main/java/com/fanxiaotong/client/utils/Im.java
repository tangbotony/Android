package com.fanxiaotong.client.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Im {
	@SuppressWarnings("deprecation")
	public static  Drawable getRoundCornerDrawable(Drawable drawable, int pixels) { 
		
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap bitmap = bd.getBitmap();
		
		if (bitmap == null){
			Li.printlnLog("Bitmap bitmap = bd.getBitmap();---" + bitmap);
			return null;
		}
		
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),  
				bitmap.getHeight(), Config.ARGB_8888);  
		Canvas canvas = new Canvas(output);  
		final int color = 0xff424242;  
		final Paint paint = new Paint();  
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());  
		final RectF rectF = new RectF(rect);  
		final float roundPx = pixels;  
		paint.setAntiAlias(true);  
		canvas.drawARGB(0, 0, 0, 0);  
		paint.setColor(color);  
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);  
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
		canvas.drawBitmap(bitmap, rect, rect, paint);  
		return new BitmapDrawable(output);  
	}  


	/**
	 * 转换图片成圆形
	 * @param bitmap 传入Bitmap对象
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Drawable toRoundBitmap(Drawable drawable) {
		
		if (drawable == null)
			return null;
		
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap bitmap = bd.getBitmap();
		
		if (bitmap == null)
			return drawable;
		
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
		if (width <= height) {
			roundPx = (width-20) / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = (height-20) / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width,
				height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
		final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return new BitmapDrawable(output);
	}

}
