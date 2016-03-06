

package com.fanxiaotong.client.widget;


import com.fanxiaotong.client.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

public class Se extends RadioGroup {

	public Se(Context context) {
		super(context);
	}

	public Se(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		changeButtonsImages();
	}

	private void changeButtonsImages(){
		int count = super.getChildCount();
		if(count > 1){
			super.getChildAt(0).setBackgroundResource(R.drawable.segment_radio_left);
			for(int i=1; i < count-1; i++){
				super.getChildAt(i).setBackgroundResource(R.drawable.segment_radio_mid);
			}
			super.getChildAt(count-1).setBackgroundResource(R.drawable.segment_radio_right);
		}else if (count == 1){
			super.getChildAt(0).setBackgroundResource(R.drawable.segment_radio_button);
		}
	}
}