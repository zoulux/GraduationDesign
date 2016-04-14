package com.lyy.hitogether.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {
	private boolean scrollble = true;

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	   public void setScanScroll(boolean scrollble){  
	        this.scrollble = scrollble;  
	    }  
	  
	  
	   @Override
	    public boolean onTouchEvent(MotionEvent arg0) {
	        // TODO Auto-generated method stub
	        if(scrollble){
	            return super.onTouchEvent(arg0);
	        }else{
	            return false;
	        }
	    }

	   @Override
	    public boolean onInterceptTouchEvent(MotionEvent arg0) {
	        // TODO Auto-generated method stub
	        if(scrollble){
	            return super.onInterceptTouchEvent(arg0);
	        }else{
	            return false;
	        }
	       
	    }
}
