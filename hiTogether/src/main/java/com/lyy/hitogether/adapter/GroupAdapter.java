package com.lyy.hitogether.adapter;

import java.util.List;

import com.lyy.hitogether.R;
import com.lyy.hitogether.bean.Group;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GroupAdapter extends MyBaseAdapter<Group> {

	public GroupAdapter(Context context, List<Group> data) {
		super(context, data);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;

		if (convertView == null) {
			convertView = commomInflater.inflate(R.layout.item_group, parent,
					false);
			viewHolder = new ViewHolder();
			viewHolder.avatar = (ImageView) convertView
					.findViewById(R.id.id_iv_avatar);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.id_tv_group_name);
			viewHolder.currentCount = (TextView) convertView
					.findViewById(R.id.id_tv_group_count_current);
			viewHolder.maxCount = (TextView) convertView
					.findViewById(R.id.id_tv_group_count_max);
			viewHolder.summary = (TextView) convertView
					.findViewById(R.id.id_tv_group_summary);
			viewHolder.addGroup = (Button) convertView
					.findViewById(R.id.id_bt_add_group);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Group group = commonDatas.get(position);

		ImageLoader.getInstance().displayImage(group.getGroupImg(),
				viewHolder.avatar, baseOptions);

		viewHolder.name.setText(group.getGroupName());
		viewHolder.currentCount.setText(group.getCurrentCount() + "");
		viewHolder.maxCount.setText(group.getMaxCount() + "");
		viewHolder.summary.setText(group.getSummary());
		// TODO 加入显示已加入不可点击，没加入显示加入

		// viewHolder.addGroup.setText(group.get)
		viewHolder.addGroup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				groupListener.onClick(v, position);
			}
		});
		return convertView;
	}

	GroupListener groupListener;

	public void setOnBtClick(GroupListener groupListener) {
		this.groupListener = groupListener;
	}

	public interface GroupListener {
		public void onClick(View v, int position);
	}

	class ViewHolder {
		ImageView avatar;
		TextView name;
		TextView currentCount;
		TextView maxCount;
		TextView summary;
		Button addGroup;

	}

}
