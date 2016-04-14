package com.lyy.hitogether.adapter;

import java.util.List;

import com.lyy.hitogether.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T> extends BaseAdapter {
	protected DisplayImageOptions baseOptions = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.icon)
			.bitmapConfig(Bitmap.Config.ARGB_8888)
			.showImageForEmptyUri(R.drawable.icon)
			.showImageOnFail(R.drawable.icon).cacheInMemory(true)
			.cacheOnDisk(true).displayer(new FadeInBitmapDisplayer(2000))
			.build();

	public LayoutInflater commomInflater;
	public List<T> commonDatas;

	public MyBaseAdapter(Context context, List<T> commonDatas) {
		super();
		this.commonDatas = commonDatas;
		commomInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return commonDatas.size();
	}

	@Override
	public T getItem(int position) {
		return commonDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public abstract View getView(int position, View convertView,
			ViewGroup parent);

}
