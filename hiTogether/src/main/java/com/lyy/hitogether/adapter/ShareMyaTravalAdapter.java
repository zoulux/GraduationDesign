package com.lyy.hitogether.adapter;

import java.util.List;

import com.lyy.hitogether.R;
import com.lyy.hitogether.util.ImageLoader;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ShareMyaTravalAdapter extends MyBaseAdapter<String> {
	private List<String> imgIds;
	private boolean isFirst = true;

	public ShareMyaTravalAdapter(Context context, List<String> commonDatas) {
		super(context, commonDatas);
		imgIds = commonDatas;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {

			viewHolder = new ViewHolder();
			convertView = commomInflater.inflate(
					R.layout.item_share_my_traval_gridview, null);
			viewHolder.img = (ImageView) convertView
					.findViewById(R.id.id_item_share_my_tralval);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (position == commonDatas.size()-1) {
			viewHolder.img.setImageResource(R.drawable.ic_input_add);
		} else {
			viewHolder.img.setImageBitmap(ImageLoader.getInstance().getCompressedBitmap(viewHolder.img,imgIds.get(position)));
		}

		return convertView;

	}

	private class ViewHolder {
		ImageView img;
	}

}
