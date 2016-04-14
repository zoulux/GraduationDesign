package com.lyy.hitogether.activity;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient.ErrorCode;
import io.rong.imlib.RongIMClient.OperationCallback;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import cn.bmob.v3.listener.CloudCodeListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lyy.hitogether.R;
import com.lyy.hitogether.adapter.GroupAdapter;
import com.lyy.hitogether.adapter.GroupAdapter.GroupListener;
import com.lyy.hitogether.bean.Group;
import com.lyy.hitogether.global.App;
import com.lyy.hitogether.util.HttpUtils;

public class GroupActivity extends BaseActivity implements GroupListener {

	private Context mContext = GroupActivity.this;
	private String Tag = GroupActivity.class.getSimpleName();

	private List<Group> mData = null;
	private GroupAdapter groupAdapter = null;
	@ViewInject(R.id.id_lv_group_list)
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);
		ViewUtils.inject(this);

		init();

	}

	private void init() {
		mData = App.getInsatnce().getGroupList();
		groupAdapter = new GroupAdapter(mContext, mData);
		mListView.setAdapter(groupAdapter);
		groupAdapter.setOnBtClick(this);

	}

	@Override
	public void onClick(View v, int position) {
		final Group group = mData.get(position);
		RongIM.getInstance()
				.getRongIMClient()
				.joinGroup(group.getGroupId(), group.getGroupName(),
						new OperationCallback() {

							@Override
							public void onSuccess() {
								ShowToast("加入成功");
								RongIM.getInstance().startGroupChat(mContext,
										group.getGroupId(), "标题");

							}

							@Override
							public void onError(ErrorCode errorCode) {
								ShowToast("加入失败");

							}
						});
	}
}
