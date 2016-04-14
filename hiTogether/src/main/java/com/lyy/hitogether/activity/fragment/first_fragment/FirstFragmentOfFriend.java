package com.lyy.hitogether.activity.fragment.first_fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;
import cn.bmob.im.util.BmobUtils;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobDate;

import com.lyy.hitogether.R;
import com.lyy.hitogether.activity.fragment.BaseFragment;
import com.lyy.hitogether.adapter.FirstFragmentOfFriendAdapter;
import com.lyy.hitogether.adapter.PictureAndTextAdapter;
import com.lyy.hitogether.bean.FirstFragmentOfFriendbean;

public class FirstFragmentOfFriend extends BaseFragment {

	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_first_of_friend, null);
		
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView(view);
		initData();
	}

	private void initView(View view) {
		listView = (ListView) view
				.findViewById(R.id.id_first_fragment_of_friend_list);
	}

	private void initData() {
		listView.setAdapter(new FirstFragmentOfFriendAdapter(
				FirstFragmentOfFriend.this.getActivity(), getDatas()));

	}

	int a = 0;
	int left = 0;

	private List<FirstFragmentOfFriendbean> getDatas() {
		List<FirstFragmentOfFriendbean> list = new ArrayList<FirstFragmentOfFriendbean>();
		for (int i = 0; i < 20; i++) {
			a = (int) (Math.random() * 10);
			left = 1000 + (int) (Math.random() * 1000);
			list.add(new FirstFragmentOfFriendbean("大雁" + i, i,
					"我想去火星啊！！！" + i, "12:" + i, a, "8888888888888" + left,
					String.valueOf(i)));
		}

		return list;
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub

	}

}
