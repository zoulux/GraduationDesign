package com.lyy.hitogether.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

public class MyLoginView extends ViewGroup {
	private onLoginListener loginListener;
	
	public  interface onLoginListener{
		void onClick(View v);
	}
	
	public void setOnLoginListener(onLoginListener loginListener){
		this.loginListener = loginListener;
	}
	public onLoginListener getLoginListener(){
		return loginListener;
	}
	
	public MyLoginView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyLoginView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public MyLoginView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed) {
			theEnd();
			int childCount = getChildCount();
			for (int i = 0; i < childCount; i++) {
				final View childView = getChildAt(i);
				int childHeight = childView.getMeasuredHeight();
				int startT = 0;
				switch (i) {
				case 0:
					startT = getMeasuredHeight() / 4;
					break;
				case 1:
					startT = getMeasuredHeight() / 4;
					break;
				case 2:
					startT = getMeasuredHeight() / 4;
					break;
				case 3:
					startT = getMeasuredHeight() / 4;
					break;

				}
				Animation trans = new TranslateAnimation(0, 0, startT, 0);
				trans.setDuration(800);
				trans.setFillAfter(true);
				trans.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						childView.setVisibility(View.VISIBLE);

					}
				});
				childView.startAnimation(trans);
				if (i == childCount - 2) {
				
					childView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							loginListener.onClick(childView);

						}
					});
				}
			}

		}
	}

	private void theEnd() {
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View childView = getChildAt(i);
			// /childView.setVisibility(View.INVISIBLE);
			int childWidth = childView.getMeasuredWidth();
			int childHeight = childView.getMeasuredHeight();
			// System.out.println(childWidth+"         "+childHeight);
			int cl = 0, ct = 0;
			switch (i) {
			case 0:
				cl = 0;
				ct = getMeasuredHeight() / 4 - childHeight;
				childView.layout(cl, ct, childWidth + cl, childHeight + ct);
				break;
			case 1:
				cl = 0;
				ct = getMeasuredHeight() / 4;
				childView.layout(cl, ct, childWidth + cl, childHeight + ct);
				break;
			case 2:
				cl = 10;
				ct = getMeasuredHeight() / 4 + childHeight * 3 / 2;
				childView
						.layout(cl, ct, childWidth + cl - 20, childHeight + ct);
				break;
			case 3:
				cl = 0;
				ct = getMeasuredHeight() / 4;
				childView.layout(cl, ct, childWidth + cl, childHeight + ct);
				break;

			}

		}
	}

}
