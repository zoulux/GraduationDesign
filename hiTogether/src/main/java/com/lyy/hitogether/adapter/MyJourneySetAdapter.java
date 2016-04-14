package com.lyy.hitogether.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyy.hitogether.R;
import com.lyy.hitogether.bean.TripLocal;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyJourneySetAdapter extends MyBaseAdapter<TripLocal> implements OnClickListener{
	private DisplayImageOptions options = DisplayImageOptions.createSimple();

	private ItemClickListener mListener;

	public interface ItemClickListener {
		public void onClick(View v, int position);
	}

	public void setmListener(ItemClickListener mListener) {
		this.mListener = mListener;
	}

	public MyJourneySetAdapter(Context context,
			List<TripLocal> commonDatas) {
		super(context, commonDatas);

	}

	@Override
	public int getItemViewType(int position) {

		return commonDatas.get(position).getBeanType();
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return TripLocal.BEAN_TYPE_COUNT;
	}
	
	//所有的item不可点击
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}
	//下标position的item不可选中
	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		int type = getItemViewType(position);
		TripLocal bean = commonDatas.get(position);

		if (convertView == null) {
			holder = new ViewHolder();
			if (type == TripLocal.BUTTON) {
				
				convertView = commomInflater
						.inflate(R.layout.my_journey_set_item_bt, parent, false);
				holder.add = (Button) convertView.findViewById(R.id.id_bt_add);

			} else if (type == TripLocal.DETAIL) {

				convertView = commomInflater.inflate(R.layout.my_journey_set_item_detail, parent,
						false);

				holder.pic = (ImageView) convertView
						.findViewById(R.id.id_iv_pic);
				holder.txt = (TextView) convertView
						.findViewById(R.id.id_tv_txt);
				holder.editPic = (TextView) convertView
						.findViewById(R.id.id_bt_edit_photo);
				holder.editTxt = (TextView) convertView
						.findViewById(R.id.id_bt_edit_txt);

			}

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Log.i("TAGTAG", "position:" + position + "  type:" + type + "");
		if (type == TripLocal.BUTTON) {
			holder.add.setOnClickListener(this);
			holder.add.setTag(position + "");
		} else if (type == TripLocal.DETAIL) {
			Log.i("TAGTAG>>", holder.txt.getText() + "");
			holder.txt.setText(bean.getTxt());
			String path = bean.getPicPath();

			ImageLoader.getInstance().displayImage(path, holder.pic, options);

			holder.editPic.setTag(position + "");
			holder.editPic.setOnClickListener(this);

			holder.editTxt.setTag(position + "");
			holder.editTxt.setOnClickListener(this);

		}

		return convertView;
	}

	class ViewHolder {
		ImageView pic;
		TextView txt;

		TextView editPic;
		TextView editTxt;

		Button add;

	}

	@Override
	public void onClick(View v) {
		mListener.onClick(v, Integer.parseInt(v.getTag().toString()));
	}

}
