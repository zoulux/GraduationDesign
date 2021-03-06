package com.lyy.hitogether.activity.fragment.first_fragment;

import android.content.Context;
import android.net.Uri;
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
import com.lyy.hitogether.bean.MyUser;
import com.lyy.hitogether.global.App;
import com.lyy.hitogether.util.ToastUtil;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.listener.UpdateListener;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class FirstFragmentOfFriend extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FirstFragmentOfFriend";
    public static final String EVENT_DEMAND = "EVENT_DEMAND";
    public static final String EVENT_FRIEND_TALK = "EVENT_FRIEND_TALK";
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
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
            adapter = new FriendAdapter();
            recyclerView.setAdapter(adapter);
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                    android.R.color.holo_orange_light, android.R.color.holo_red_light);
        }
        ButterKnife.bind(this, rootContainer);
        EventBus.getDefault().register(this);
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


    @Subscriber(tag = EVENT_DEMAND)
    public void update(final int pos) {
        Demand demand = adapter.getData().get(pos);

        List<String> totalId = demand.getZanId();
        String currentId = App.getInsatnce().getCurrentUser().getObjectId();
        if (totalId == null) {
            return;
        }
        if (totalId.contains(currentId)) {
            ToastUtil.message("您已经为他点过赞了");
        } else {
            demand.setZan(demand.getZan() + 1);
            totalId.add(currentId);

            demand.update(getContext(), demand.getObjectId(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    adapter.notifyItemChanged(pos);
                }

                @Override
                public void onFailure(int i, String s) {

                }
            });


        }
    }


    @Subscriber(tag = EVENT_FRIEND_TALK)
    public void talk(int pos){
        MyUser user = adapter.getData().get(pos).getUser();

        RongIM.getInstance().startPrivateChat(getActivity(), user.getObjectId(),
                "与" + user.getNick() + "聊天中");
        if (!isContain(user.getObjectId())) {
            Uri uri = Uri.parse(user.getAvatar());
            App.getInsatnce()
                    .getUserInfos()
                    .add(new UserInfo(user.getObjectId(), user
                            .getNick(), uri));
        }

    }
    private boolean isContain(String objectId) {
        List<UserInfo> userInfos = App.getInsatnce().getUserInfos();
        for (UserInfo userInfo : userInfos) {
            if (userInfo.getUserId().equals(objectId)) {
                return true;
            }
        }
        return false;
    }

}
