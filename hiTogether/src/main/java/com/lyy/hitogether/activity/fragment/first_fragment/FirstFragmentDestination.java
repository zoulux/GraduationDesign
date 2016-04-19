package com.lyy.hitogether.activity.fragment.first_fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lyy.hitogether.R;
import com.lyy.hitogether.activity.fragment.BaseFragment;
import com.lyy.hitogether.adapter.DeatinationAdapter;
import com.lyy.hitogether.adapter.InfiniteAdapter;
import com.lyy.hitogether.bean.RoundImg;
import com.lyy.hitogether.bean.Service;
import com.lyy.hitogether.global.App;
import com.lyy.hitogether.util.ToastUtil;
import com.zanlabs.widget.infiniteviewpager.InfiniteViewPager;
import com.zanlabs.widget.infiniteviewpager.indicator.CirclePageIndicator;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class FirstFragmentDestination extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.rv_destination)
    RecyclerView rvDestination;
    @Bind(R.id.header_destination)
    RecyclerViewHeader headerDestination;
    @Bind(R.id.sr_destination)
    SwipeRefreshLayout srDestination;

    @Bind(R.id.infinite_viewpager)
    InfiniteViewPager viewPager;

    @Bind(R.id.infinite_indicator)
    CirclePageIndicator indicator;


    private InfiniteAdapter infiniteAdapter;

    private List<Service> list;
    private DeatinationAdapter adapter;

    private View rootContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootContainer == null) {
            rootContainer = inflater.inflate(R.layout.fragment_first_destination, null);
            ButterKnife.bind(this, rootContainer);
            init();
        }


        return rootContainer;
    }

    private void init() {
        rvDestination.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        headerDestination.attachTo(rvDestination, true);
        srDestination.setOnRefreshListener(this);
        srDestination.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        adapter = new DeatinationAdapter();
        rvDestination.setAdapter(adapter);

        postAsync("getAllService", null);
        postAsync("getRoundImg", null);


        infiniteAdapter = new InfiniteAdapter();
        viewPager.setAdapter(infiniteAdapter);
        viewPager.setAutoScrollTime(5000);

        indicator.setViewPager(viewPager);
    }


    private void initEvent() {
//        thirdFragmentAdapter
//                .setOnThirdFragmentBtListener(new OnThirdFragmentBtListener() {
//
//                    @Override
//                    public void onBtclick(View v, int position) {
//                        // ShowToast(position + "");
//
//                        chatWithGuide(position);
//                        if (!isContain(position)) {
//                            MyUser user = list.get(position).getUser();
//                            Uri uri = Uri.parse(user.getAvatar());
//                            App.getInsatnce()
//                                    .getUserInfos()
//                                    .add(new UserInfo(user.getObjectId(), user
//                                            .getNick(), uri));
//                            // ConnectRong.updateFrindsInfo(null);
//                        }
//
//                    }
//                });
//
//        thirdFragmentAdapter
//                .setOnThirdFragmentAllViewClickListener(new onThirdFragmentAllViewClickListener() {
//
//                    @Override
//                    public void onThirdFragmentAllViewClick(View v, int pos) {
//
//                        Intent intent = new Intent(new Intent(
//                                FirstFragmentDestination.this.getActivity(),
//                                ShowGuideDetailActivity.class));
//                        intent.putExtra(Service.TAG, list.get(pos));
//                        // intent.putExtra(name, value)
//                        // intent.putExtra("targetUserId",
//                        // list.get(pos).getUser()
//                        // .getObjectId());
//                        // intent.putExtra("targetUserName", list.get(pos)
//                        // .getUser().getUsername());
//                        startActivity(intent);
//
//                    }
//                });
    }

    protected void chatWithGuide(int position) {
        String targetUserId = list.get(position).getUser().getObjectId();
        String targetUserName = list.get(position).getUser().getUsername();
        RongIM.getInstance().startPrivateChat(getActivity(), targetUserId,
                "与" + targetUserName + "聊天中");

    }

    protected boolean isContain(int position) {

        for (UserInfo user : App.getInsatnce().getUserInfos()) {

            if (user.getUserId().equals(
                    list.get(position).getUser().getObjectId())) {
                return true;
            }
        }

        return false;
    }


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
        switch (msg.what) {
            case GET_SUCCESS:
                if (msg.arg1 == 1) {
                    handleSuccessRound(msg.obj.toString());
                } else {
                    handleSuccessService(msg.obj.toString());
                }

                break;
            case GET_FAILD:
                handleFaild(msg.obj.toString());
                break;
            default:
                break;
        }

        return false;
    }

    private void handleSuccessRound(String json) {
        Gson gson = new Gson();
        List<RoundImg> list = gson.fromJson(json, new TypeToken<List<RoundImg>>() {
        }.getType());

        Log.i("TAG", list.toString());
        //  initEvent();
        infiniteAdapter.setData(list);
        viewPager.startAutoScroll();
    }

    private void handleFaild(String string) {
        ToastUtil.message(string);
    }

    private void handleSuccessService(String json) {

        Gson gson = new Gson();
        list = gson.fromJson(json, new TypeToken<List<Service>>() {
        }.getType());
        Log.i("TAG", list.toString());
        //  initEvent();

        adapter.setData(list);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (viewPager != null)
            viewPager.startAutoScroll();
    }

    @Override
    public void onStop() {
        if (viewPager != null)
            viewPager.stopAutoScroll();
        super.onStop();
    }
}
