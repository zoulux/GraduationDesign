package com.lyy.hitogether.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyy.hitogether.R;
import com.lyy.hitogether.bean.Service;
import com.lyy.hitogether.bean.ThirdFragmentBean;
import com.lyy.hitogether.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ThirdFragmentAdapter extends MyBaseAdapter<Service> {
	private OnThirdFragmentBtListener mThirdFragmentBtListener;
	private onThirdFragmentAllViewClickListener mThirdFragmentAllViewClickListener;

	public interface OnThirdFragmentBtListener {
		void onBtclick(View v, int position);
	}

	public interface onThirdFragmentAllViewClickListener {
		void onThirdFragmentAllViewClick(View v, int pos);
	}

	public void setOnThirdFragmentBtListener(
			OnThirdFragmentBtListener thirdFragmentBtListener) {
		this.mThirdFragmentBtListener = thirdFragmentBtListener;

	}

	public void setOnThirdFragmentAllViewClickListener(
			onThirdFragmentAllViewClickListener mThirdFragmentAllViewClickListener) {
		this.mThirdFragmentAllViewClickListener = mThirdFragmentAllViewClickListener;

	}

	public ThirdFragmentAdapter(Context context, List<Service> commonDatas) {
		super(context, commonDatas);

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = commomInflater.inflate(R.layout.item_third_fragment,
					null);

			viewHolder.guideImage = (CircleImageView) convertView
					.findViewById(R.id.id_third_fragment_guide_avartar);
			viewHolder.guideName = (TextView) convertView
					.findViewById(R.id.id_third_fragment_guide_name);
			viewHolder.scenImage = (ImageView) convertView
					.findViewById(R.id.id_third_fragment_scen_img);
			viewHolder.description = (TextView) convertView
					.findViewById(R.id.id_third_fragment_desc);
			viewHolder.place = (TextView) convertView
					.findViewById(R.id.id_third_fragment_place);
			viewHolder.grade = (RatingBar) convertView
					.findViewById(R.id.id_third_fragment_grade);
			viewHolder.apointmentBt = (Button) convertView
					.findViewById(R.id.id_fragment_bt_appointment);
			viewHolder.allView = (RelativeLayout) convertView
					.findViewById(R.id.id_third_fragment_all_view);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Service service = commonDatas.get(position);

		ImageLoader.getInstance().displayImage(service.getShowImg(),
				viewHolder.scenImage, baseOptions);
		ImageLoader.getInstance().displayImage(service.getUser().getAvatar(),
				viewHolder.guideImage, baseOptions);

		viewHolder.guideName.setText(service.getUser().getNick());
		// viewHolder.guideImage.setImageResource(R.drawable.icon);
		viewHolder.description.setText(service.getSummary());
		viewHolder.place.setText(service.getDestination());
		viewHolder.grade.setRating(service.getUser().getStar());
		viewHolder.apointmentBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mThirdFragmentBtListener != null) {
					mThirdFragmentBtListener.onBtclick(v, position);
				}

			}
		});

		viewHolder.allView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mThirdFragmentAllViewClickListener != null) {
					mThirdFragmentAllViewClickListener
							.onThirdFragmentAllViewClick(v, position);
				}
			}
		});

		return convertView;
	}

	public class ViewHolder {
		ImageView scenImage;
		CircleImageView guideImage;
		TextView guideName;
		TextView praisedCount;
		TextView guideFee;
		TextView description;
		TextView place;
		RatingBar grade;
		Button apointmentBt;
		RelativeLayout allView;
	}

}
