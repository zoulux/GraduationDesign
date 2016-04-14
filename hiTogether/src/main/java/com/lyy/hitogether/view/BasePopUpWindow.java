package com.lyy.hitogether.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.lyy.hitogether.R;

public abstract class BasePopUpWindow extends PopupWindow {
	private Context context;

	// PopupWindow的宽度
	public int mWidth;
	// PopupWindow的高度
	public int mHeight;
	// 获取PopupWindow的view
	public View mConvertView;
	/**
	 * 设置布局和一些初始化操作,若proPortion为0，则width和height不能为0，反之亦然。
	 * @param context 
	 * @param source
	 * @param proPortion
	 */
	public void setLayout(Context context,int source,float proPortion,int width,int height) {
		this.context = context;
		
		mConvertView = LayoutInflater.from(context).inflate(source, null);
		setContentView(mConvertView);
		if (proPortion==0) {
			setWidth(width);
			setHeight(height);
		}else{
			culculateWidthAndHeight(proPortion);
			setWidth(mWidth);
			setHeight(mHeight);
		}
		
		setFocusable(true);
		setTouchable(true);
		setOutsideTouchable(true);
		setBackgroundDrawable(new BitmapDrawable());

		setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					dismiss();
					return true;
				}
				return false;
			}
		});
		initId();
		initEvent();
		setAnimationStyle(R.style.mypopwindow_anim_style);
	}
	
	public abstract void initId();
	public abstract void initEvent();

	private void culculateWidthAndHeight(float proPortion) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displayMetrics);

		mWidth = displayMetrics.widthPixels;
		mHeight = (int) (displayMetrics.heightPixels * proPortion);

	}
	/**
	 * 灯亮
	 * @param activity
	 */
	public void lightOn(Activity activity) {
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		lp.alpha = 1.0f;
		activity.getWindow().setAttributes(lp);

	}
	

	/**
	 * 灯灭
	 * @param activity
	 */
	public void lightOff(Activity activity) {
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		lp.alpha = .3f;
		activity.getWindow().setAttributes(lp);

	}

}
