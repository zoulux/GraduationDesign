package com.lyy.hitogether.adapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lyy.hitogether.R;
import com.lyy.hitogether.activity.ShareMyTravalActivity;
import com.lyy.hitogether.util.ImageLoader;
import com.lyy.hitogether.util.SPUtils;
import com.lyy.hitogether.util.ImageLoader.Type;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageAdapter extends MyBaseAdapter<String> {
	private static Set<String> mSelectedImg = new HashSet<String>();
	private String mDirPath;
	private List<String> chooiceImgPaths = new ArrayList<String>();
	private Context context;
	private List<ImageView> imageViews = new ArrayList<ImageView>();


	public interface onAdapterItemClickListener {
		void onAdapterItemClick(View v, int count);
	}

	public onAdapterItemClickListener mAdAdapterItemClickListener;

	public void setAdapterItemClickListener(
			onAdapterItemClickListener adapterItemClickListener) {

		System.out.println(mAdAdapterItemClickListener == null);
		this.mAdAdapterItemClickListener = adapterItemClickListener;
	}

	public ImageAdapter(Context context, String mDirPath, List<String> mImgPath) {
		super(context, mImgPath);
		this.mDirPath = mDirPath;
		this.context = context;
		mSelectedImg.clear();
		chooiceImgPaths.clear();
	}

	private ImageLoader imageLoader;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {

			convertView = commomInflater
					.inflate(R.layout.item_gride_view, null);
			viewHolder = new ViewHolder();
			viewHolder.mImg = (ImageView) convertView
					.findViewById(R.id.id_item_image);
			viewHolder.mSelect = (ImageButton) convertView
					.findViewById(R.id.id_item_select);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// ����״̬
		viewHolder.mImg.setImageResource(R.drawable.pictures_no);
		viewHolder.mSelect.setImageResource(R.drawable.picture_unselected);
		viewHolder.mImg.setColorFilter(null);
		imageLoader = ImageLoader.getInstance(3, Type.LIFO);
		imageLoader.LoadImage(mDirPath + "/" + commonDatas.get(position),
				viewHolder.mImg);
		final String filePath = mDirPath + "/" + commonDatas.get(position);
		

		viewHolder.mImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (mSelectedImg.contains(filePath)) {
					mSelectedImg.remove(filePath);
					chooiceImgPaths.remove(filePath);
					
					viewHolder.mImg.setColorFilter(null);
					viewHolder.mSelect
							.setImageResource(R.drawable.picture_unselected);
					imageViews.remove(viewHolder.mImg);
				} else {

					if (mSelectedImg.size() > 8) {
						Toast.makeText(context, "最多选九张", 1).show();
					} else {
						mSelectedImg.add(filePath);
						chooiceImgPaths.add(filePath);
						
						viewHolder.mImg.setColorFilter(Color
								.parseColor("#77000000"));
						viewHolder.mSelect
								.setImageResource(R.drawable.pictures_selected);
						imageViews.add(viewHolder.mImg);
					}

				}
				if (mAdAdapterItemClickListener != null) {

					mAdAdapterItemClickListener.onAdapterItemClick(
							viewHolder.mImg, mSelectedImg.size());

				}

			}
		});
		if (mSelectedImg.contains(filePath)) {
			viewHolder.mImg.setColorFilter(Color.parseColor("#77000000"));
			viewHolder.mSelect.setImageResource(R.drawable.pictures_selected);
		}

		return convertView;
	}

	/**
	 * ��ȡ��ѡ�е�ͼƬ ����Ŀ
	 * 
	 * @return
	 */
	public int getSelectedImgSize() {
		return chooiceImgPaths.size();
	}

	public List<String> getSelectedImgPath() {
		return chooiceImgPaths;
	}

	public List<ImageView> getSelectedImageView() {
		return imageViews;
	}

	private class ViewHolder {
		ImageView mImg;
		ImageButton mSelect;

	}

	public void end(Context context) {
		imageLoader.end(context);
	}


}
