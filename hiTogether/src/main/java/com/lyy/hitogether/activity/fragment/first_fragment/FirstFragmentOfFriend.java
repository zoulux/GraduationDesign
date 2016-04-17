package com.lyy.hitogether.activity.fragment.first_fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lyy.hitogether.R;
import com.lyy.hitogether.activity.fragment.BaseFragment;
import com.lyy.hitogether.adapter.FriendAdapter;
import com.lyy.hitogether.bean.Demand;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FirstFragmentOfFriend extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FirstFragmentOfFriend";
    private View rootContainer;
    @Bind(R.id.rv_friend)
    RecyclerView recyclerView;
    private Context context;
    private FriendAdapter adapter;

    @Bind(R.id.sl_friend)
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        if (rootContainer == null) {
            this.rootContainer = inflater.inflate(R.layout.fragment_first_of_friend, container, false);
            ButterKnife.bind(this, rootContainer);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            adapter = new FriendAdapter();
            recyclerView.setAdapter(adapter);
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                    android.R.color.holo_orange_light, android.R.color.holo_red_light);
        }
        return rootContainer;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      //  swipeRefreshLayout.setRefreshing(true);
        postAsync("getDemand", null);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == BaseFragment.GET_SUCCESS) {
            Log.d(TAG, "handleMessage: " + msg.obj);
            Gson gson = new Gson();
            List<Demand> data = gson.fromJson(msg.obj.toString(), new TypeToken<List<Demand>>() {
            }.getType());
            adapter.setData(data);
        } else {
            showToast("加载失败");
        }
        swipeRefreshLayout.setRefreshing(false);

        return false;
    }

    @Override
    protected void lazyLoad() {


    }


    @Override
    public void onRefresh() {
        postAsync("getDemand", null);
    }
}
