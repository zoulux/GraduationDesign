package com.lyy.hitogether.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class MyPagerAdapter extends PagerAdapter {

	private List<PagerItem> mPagerItems;

	private List<View> mAllView;
	private int position;

	public MyPagerAdapter() {
		super();
		mPagerItems = new ArrayList<MyPagerAdapter.PagerItem>();
	}

	public int getPositionItem() {
		return position;
	}

	public void addItem(String title, View v) {
		mPagerItems.add(new PagerItem(v, title));
	}

	public PagerItem getItem(int position) {
		return mPagerItems.get(position);
	}

	public void removeItem(int position) {
		mPagerItems.remove(position);
	}

	public void removeAll() {
		mPagerItems.clear();
	}

	@Override
	public int getCount() {
		return mPagerItems.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == ((PagerItem) arg1).v;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView(mPagerItems.get(position).v);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return mPagerItems.get(position).title;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPager) container).addView(mPagerItems.get(position).v);
		return mPagerItems.get(position);
	}

	@Override
	public void setPrimaryItem(View container, int position, Object object) {
		
		this.position = position;
	}


	public class PagerItem {
		public View v;
		public String title;

		public PagerItem(View v, String title) {
			this.v = v;
			this.title = title;
		}

	}

}
