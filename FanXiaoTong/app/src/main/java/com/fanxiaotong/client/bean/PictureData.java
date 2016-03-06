package com.fanxiaotong.client.bean;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PictureData {
	private RelativeLayout relativeLayout;
	private Drawable drawable;
	private ImageView imageView;
	public PictureData() {
		super();
	}
	public PictureData(RelativeLayout relativeLayout, Drawable drawable,
			ImageView imageView) {
		super();
		this.relativeLayout = relativeLayout;
		this.drawable = drawable;
		this.imageView = imageView;
	}
	
	public PictureData(RelativeLayout relativeLayout, Drawable drawable) {
		super();
		this.relativeLayout = relativeLayout;
		this.drawable = drawable;
	}
	
	public PictureData(ImageView imageView, Drawable drawable) {
		super();
		this.drawable = drawable;
		this.imageView = imageView;
	}
	
	
	public RelativeLayout getRelativeLayout() {
		return relativeLayout;
	}
	public Drawable getDrawable() {
		return drawable;
	}
	public ImageView getImageView() {
		return imageView;
	}
	public void setRelativeLayout(RelativeLayout relativeLayout) {
		this.relativeLayout = relativeLayout;
	}
	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	@Override
	public String toString() {
		return "PictureData [relativeLayout=" + relativeLayout + ", drawable="
				+ drawable + ", imageView=" + imageView + "]";
	}
}
