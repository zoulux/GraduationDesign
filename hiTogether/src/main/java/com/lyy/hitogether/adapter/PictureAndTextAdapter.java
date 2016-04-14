package com.lyy.hitogether.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lyy.hitogether.R;
import com.lyy.hitogether.bean.HotScenic;
import com.lyy.hitogether.bean.Picture;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PictureAndTextAdapter extends MyBaseAdapter<HotScenic> {

	public PictureAndTextAdapter(Context context, List<HotScenic> commonDatas) {
		super(context, commonDatas);
	}

	private static List<Picture> pictures;

	// public PictureAndTextAdapter(String[] titles, int[] images, Context
	// context)
	// {
	// super(context,Arrays.asList(titles));
	// pictures = new ArrayList<Picture>();
	// for (int i = 0; i < images.length; i++)
	// {
	// Picture picture = new Picture(titles[i], images[i]);
	// pictures.add(picture);
	// }
	//
	// }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = commomInflater.inflate(R.layout.picture_text_item,
					null);
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
			viewHolder.enjoy = (TextView) convertView
					.findViewById(R.id.id_tv_enjoy);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.title.setText(commonDatas.get(position).getHotName());
		ImageLoader.getInstance().displayImage(
				commonDatas.get(position).getPhotoPath(), viewHolder.image,
				baseOptions);

		viewHolder.enjoy.setText(commonDatas.get(position).getEnjoy() + "人");

		// viewHolder.image.setImageResource(pictures.get(position).getImageId());
		return convertView;
	}

}

class ViewHolder {
	public TextView title;
	public ImageView image;
	public TextView enjoy;
}
