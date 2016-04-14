package com.lyy.hitogether.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lyy.hitogether.R;
import com.lyy.hitogether.bean.FirstFragmentOfFriendbean;
import com.lyy.hitogether.view.CustomGalleryView;

public class FirstFragmentOfFriendAdapter extends
		MyBaseAdapter<FirstFragmentOfFriendbean> {

	private Context context;
	private List<String> praises = new ArrayList<String>();
	private List<String> collects = new ArrayList<String>();
	private List<String> orderNos = new ArrayList<String>();

	public FirstFragmentOfFriendAdapter(Context context,
			List<FirstFragmentOfFriendbean> commonDatas) {
		super(context, commonDatas);
		this.context = context;

	}

	// TextView textView;
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = commomInflater.inflate(
					R.layout.item_first_fragment_of_friend_listview, null);
			viewHolder.customGalleryView = (CustomGalleryView) convertView
					.findViewById(R.id.id_first_fragment_of_friend_list_customGalleryView);
			viewHolder.myFriendAvartar = (ImageView) convertView
					.findViewById(R.id.id_first_fragment_of_friend_list_avartar);
			viewHolder.myFriendName = (TextView) convertView
					.findViewById(R.id.id_first_fragment_of_friend_list_friend_name);
			viewHolder.sendTime = (TextView) convertView
					.findViewById(R.id.id_first_fragment_of_friend_list_send_time);
			viewHolder.yourDesc = (TextView) convertView
					.findViewById(R.id.id_first_fragment_of_friend_list_desc);
			viewHolder.praiseCount = (TextView) convertView
					.findViewById(R.id.id_first_fragment_of_friend_list_praise);
			viewHolder.talkToFriendBt = (Button) convertView
					.findViewById(R.id.id_first_fragment_of_friend_list_talk_to_friend_bt);
			viewHolder.collectionBt = (Button) convertView
					.findViewById(R.id.id_first_fragment_of_friend_list_collection);
			viewHolder.orderNoBt = (Button) convertView
					.findViewById(R.id.id_first_fragment_of_friend_list_No);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.myFriendAvartar.setImageResource(commonDatas.get(position)
				.getMyFriendAvartar());
		viewHolder.myFriendName.setText(commonDatas.get(position)
				.getMyFriendName());
		viewHolder.praiseCount.setText("赞");
		viewHolder.collectionBt.setText("收藏");
		viewHolder.orderNoBt.setText("订单编号");
		viewHolder.sendTime.setText(commonDatas.get(position).getSendTime());
		viewHolder.yourDesc.setText(commonDatas.get(position).getYourDesc());
		final String currentPraiseCount = commonDatas.get(position)
				.getPraiseCount() + "";
		final String currentCollect = commonDatas.get(position)
				.getCollectionId();
		final String orderNo = commonDatas.get(position).getOrderNo();
		viewHolder.orderNoBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (orderNos.contains(orderNo)) {
					viewHolder.orderNoBt.setText("订单编号");
					orderNos.remove(orderNo);
				} else {
					System.out.println(orderNo);
					viewHolder.orderNoBt.setText(orderNo);
					orderNos.add(orderNo);
					notifyDataSetChanged();
				}

			}
		});
		if (orderNos.contains(orderNo)) {
			viewHolder.orderNoBt.setText(orderNo);
		}

		viewHolder.praiseCount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (praises.contains(currentPraiseCount)) {
					viewHolder.praiseCount.setText("赞");
					praises.remove(currentPraiseCount);
				} else {
					viewHolder.praiseCount.setText(currentPraiseCount);
					praises.add(currentPraiseCount);
				}
			}
		});
		if (praises.contains(currentPraiseCount)) {
			viewHolder.praiseCount.setText(currentPraiseCount);
		}
		viewHolder.collectionBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (collects.contains(currentCollect)) {
					viewHolder.collectionBt.setText("取消收藏");
					notifyDataSetChanged();
					collects.remove(currentCollect);

				} else {
					viewHolder.collectionBt.setText("收藏");
					notifyDataSetChanged();
					collects.add(currentCollect);
				}
			}
		});
		if (collects.contains(currentCollect)) {
			viewHolder.collectionBt.setText("取消收藏");
		}
		viewHolder.talkToFriendBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, position + "", Toast.LENGTH_SHORT)
						.show();

			}
		});

		viewHolder.customGalleryView.setAdapter(new GalleryAdapter(context,
				getData(position)));
		return convertView;

	}

	private ArrayList<Integer> getData(int pos) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		int a = commonDatas.get(pos).getPicCount();
		for (int i = 0; i < a; i++) {
			if (i == 1) {
				list.add(R.drawable.p1);
			} else if (i % 2 == 0) {
				list.add(R.drawable.p2);
			} else if (i % 3 == 0) {
				list.add(R.drawable.p2);
			} else if (i % 4 == 0) {
				list.add(R.drawable.p4);
			}

		}
		return list;
	}

	class ViewHolder {
		CustomGalleryView customGalleryView;
		ImageView myFriendAvartar;
		TextView myFriendName;
		TextView sendTime;
		TextView yourDesc;
		TextView praiseCount;
		Button talkToFriendBt;
		Button collectionBt;
		Button orderNoBt;

	}
}
