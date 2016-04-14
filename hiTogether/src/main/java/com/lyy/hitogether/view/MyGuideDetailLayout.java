package com.lyy.hitogether.view;

import com.lyy.hitogether.util.CommonUtils;
import com.lyy.hitogether.util.Config;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyGuideDetailLayout extends LinearLayout {
	private Context context;
	private ImageLoader mImageLoader = ImageLoader.getInstance();

	public MyGuideDetailLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(VERTICAL);
		this.context = context;
	}

	public void addImage(String souece) {

		ImageView img = new ImageView(context);
		mImageLoader.displayImage(souece, img, CommonUtils.imageOptions);
		img.setScaleType(ScaleType.FIT_XY);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 400);
		addView(img, lp);
	}

	public void addImage(int souece) {

		ImageView img = new ImageView(context);
		img.setImageResource(souece);
		img.setScaleType(ScaleType.CENTER_CROP);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 400);
		addView(img, lp);
	}

	public void addText(String text) {
		TextView tv = new TextView(context);
		tv.setText(text);
		addView(tv);
	}

}
