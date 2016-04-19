package com.lyy.hitogether.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lyy.hitogether.R;
import com.lyy.hitogether.activity.ShowSceneDetailsActivity;
import com.lyy.hitogether.adapter.GuideAdapter;
import com.lyy.hitogether.bean.HotScenic;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ThirdFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private View rootContainer;

    @Bind(R.id.sl_guide)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.rv_guide)
    RecyclerView recyclerView;

    @Bind(R.id.header_guide)
    RecyclerViewHeader recyclerViewHeader;

    private GuideAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("ThirdFragment", "onCreateView");

        if (rootContainer == null) {
            rootContainer = inflater.inflate(R.layout.fragment_third, container, false);
            ButterKnife.bind(this, rootContainer);
            EventBus.getDefault().register(this);
            init();
        }

        ButterKnife.bind(this, rootContainer);
        return rootContainer;
    }

    private void init() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        recyclerViewHeader.attachTo(recyclerView, true);

        adapter = new GuideAdapter();
        recyclerView.setAdapter(adapter);


        getData();
    }

    private void getData() {
        swipeRefreshLayout.setRefreshing(true);

        postAsync("getAllHotScenic", null);
    }

    @Subscriber(tag = HotScenic.TAG)
    public void jumpInfo(HotScenic hotScenic) {
        Intent intent = new Intent(getContext(), ShowSceneDetailsActivity.class);
        intent.putExtra(HotScenic.TAG, hotScenic);
        startActivity(intent);
    }


    /**
     * 返回正确结果，接下来去写适配器
     *
     * @param json json数据
     */
    protected void handleSuccess(String json) {
        Gson gson = new Gson();
        List<HotScenic> data = gson.fromJson(json,
                new TypeToken<List<HotScenic>>() {
                }.getType());

        adapter.setData(data);
    }

    /**
     * 返回错误结果，提示。。
     *
     * @param code 错误代码
     */

    protected void handleFaild(String code) {
        showToast("请检查网络");
    }

    /**
     * 不可见不加载，没准备好不加载
     */
    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onPause() {
        isVisible = false;
        super.onPause();
    }

    @Override
    public boolean handleMessage(Message msg) {
        swipeRefreshLayout.setRefreshing(false);
        switch (msg.what) {
            case GET_SUCCESS:
                handleSuccess(msg.obj.toString());
                break;
            case GET_FAILD:
                handleFaild(msg.obj.toString());
                break;
            default:
                break;
        }

        return false;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onRefresh() {
        getData();
    }


}
