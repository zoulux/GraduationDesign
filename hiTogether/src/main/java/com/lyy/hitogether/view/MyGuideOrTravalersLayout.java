package com.lyy.hitogether.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.lyy.hitogether.R;
import com.lyy.hitogether.util.DensityUtils;



public class MyGuideOrTravalersLayout extends ViewGroup {
	private Context context;
	private onGuideItemClickListener mListener;

	public interface onGuideItemClickListener {
		void onItemClick(View v, int pos);
	}

	public void setOnGuideItemClickListener(onGuideItemClickListener mListener) {
		this.mListener = mListener;
	}

	List<CircleImageView> images = new ArrayList<CircleImageView>();

	public MyGuideOrTravalersLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	private int AllWidth = 0;

	public void addMyView(int count) {
		for (int i = 0; i < count; i++) {
			CircleImageView img = new CircleImageView(context);
			LayoutParams lp = new LayoutParams((int) DensityUtils.dp2px(context, 46),
					(int) DensityUtils.dp2px(context, 46));
			
			img.setImageResource(R.drawable.girl);
			img.setBorderColor(Color.BLACK);
			addView(img,lp);
		}

	}

	

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed) {
			int childCount = getChildCount();
			for (int i = 0; i < childCount; i++) {
				final View childView = getChildAt(i);
				int childWidth = childView.getMeasuredWidth();
				int cl, ct;
				cl = AllWidth;
				ct = 0;
				AllWidth += (childWidth + 6);
				if (AllWidth > getMeasuredWidth()) {
					removeViewAt(i - 1);
					break;
				} else {
					
					childView.layout(cl, ct, cl + childWidth, ct + childWidth);

				}
				final int pos = i;
				childView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (mListener != null) {
							mListener.onItemClick(childView, pos);
						}

					}
				});

			}
		}

	}

}
