package com.lyy.hitogether.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class CustomGalleryView extends Gallery {

	private int galleryCenterPoint; 
	private final int maxRotateAngle = 70;
	private Camera camera;
	private Context context;
	
	private float mLastMotionX;// 滑动过程中，x方向的初始坐标
	private float mLastMotionY;// 滑动过程中，y方向的初始坐标
	private int mTouchSlop;// 手指大小的距离

	public CustomGalleryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		
		final ViewConfiguration configuration = ViewConfiguration
				.get(getContext());
		mTouchSlop = configuration.getScaledTouchSlop();
		setStaticTransformationsEnabled(true);
		camera = new Camera();
	}

	
	@Override
	protected boolean getChildStaticTransformation(View child, Transformation t) {
		int viewCenterPoint = getViewCenterPoint(child);
		int rotateAngle = 0;

		if (galleryCenterPoint != viewCenterPoint) { 

			float scale = (float) (galleryCenterPoint - viewCenterPoint)
					/ (float) child.getWidth();
			
			rotateAngle = (int) (scale * maxRotateAngle);

			if (Math.abs(rotateAngle) > maxRotateAngle) { // -51 -60 55 66
				rotateAngle = rotateAngle > 0 ? maxRotateAngle
						: -maxRotateAngle;
			}
		}

		t.clear();
		t.setTransformationType(Transformation.TYPE_MATRIX);

		transformationItem((ImageView) child, rotateAngle, t);
		return true;
	}

	
	private void transformationItem(ImageView iv, int rotateAngle,
			Transformation t) {
		camera.save(); 
		camera.translate(0, 0, 100f);
		int absRotateAngle = Math.abs(rotateAngle); 

		
		int zoom = (int) (absRotateAngle * 1.5 - 230);
		camera.translate(0, 0, zoom);
		
		
		int alpha = (int) (255 - absRotateAngle * 1.0);
		iv.setAlpha(alpha);

		camera.rotateY(rotateAngle);

		Matrix matrix = t.getMatrix();
		camera.getMatrix(matrix);

		matrix.preTranslate(-iv.getWidth() / 2, -iv.getHeight() / 2);

		matrix.postTranslate(iv.getWidth() / 2, iv.getHeight() / 2);
	
		camera.restore();
	}
	
//	@Override
//	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
//			float distanceY) {
//		// TODO Auto-generated method stub
//		return super.onScroll(e1, e2, distanceX, distanceY);
//	}

	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();// 获取触摸事件类型
		final float x = ev.getX();// 每次触摸事件的x坐标
		final float y = ev.getY();// 每次触摸事件的y坐标
		switch (action) {
		case MotionEvent.ACTION_DOWN:// 按下事件
			mLastMotionX = x;// 初始化每次触摸事件的x方向的初始坐标，即手指按下的x方向坐标
			mLastMotionY = y;// 初始化每次触摸事件的y方向的初始坐标，即手指按下的y方向坐标
			break;

		case MotionEvent.ACTION_MOVE:
			final int deltaX = (int) (mLastMotionX - x);// 每次滑动事件x方向坐标与触摸事件x方向初始坐标的距离
			final int deltaY = (int) (mLastMotionY - y);// 每次滑动事件y方向坐标与触摸事件y方向初始坐标的距离
			boolean xMoved = Math.abs(deltaX) > mTouchSlop
					&& Math.abs(deltaY / deltaX) < 1;
			// 判断触摸事件处理的传递方向，该业务中是，
			// x方向的距离大于手指，并且y方向滑动的距离小于x方向的滑动距离时，Gallery消费掉此次触摸事件
			// 如果需要，请在您的业务中，改变判断的逻辑
			if (xMoved) {// Gallery需要消费掉此次触摸事件
			 return false;// 返回true就不会将此次触摸事件传递给子View了，我的业务中是ListView
			}
			break;
		case MotionEvent.ACTION_UP:
			if (this.getSelectedItemPosition() < 1) {
				this.setSelection(1);
				return true;
			}
			
			break;
		case MotionEvent.ACTION_OUTSIDE:
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		case MotionEvent.ACTION_MASK:
			break;
		}
		return false;// 将此次触摸事件传递给子View，即ListView
		
	}
	
	

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		galleryCenterPoint = getGalleryCenterPoint();
	}

	
	private int getGalleryCenterPoint() {
		return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2
				+ getPaddingLeft();
	}

	
	private int getViewCenterPoint(View v) {
		return v.getWidth() / 2 + v.getLeft();
	}

}
