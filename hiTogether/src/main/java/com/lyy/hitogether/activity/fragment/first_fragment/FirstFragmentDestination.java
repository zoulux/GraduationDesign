package com.lyy.hitogether.activity.fragment.first_fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.lyy.hitogether.R;
import com.lyy.hitogether.activity.ShowGuideDetailActivity;
import com.lyy.hitogether.activity.fragment.BaseFragment;
import com.lyy.hitogether.adapter.MyPagerAdapter;
import com.lyy.hitogether.adapter.ThirdFragmentAdapter;
import com.lyy.hitogether.adapter.ThirdFragmentAdapter.OnThirdFragmentBtListener;
import com.lyy.hitogether.adapter.ThirdFragmentAdapter.onThirdFragmentAllViewClickListener;
import com.lyy.hitogether.bean.MyUser;
import com.lyy.hitogether.bean.Service;
import com.lyy.hitogether.global.App;
import com.lyy.hitogether.mydialog.SweetAlertDialog;
import com.lyy.hitogether.view.MyViewPager;

import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class FirstFragmentDestination extends BaseFragment {

    private PullToRefreshGridView mGridView;
    private ThirdFragmentAdapter thirdFragmentAdapter;
    //
    private boolean isPrepared;
    private List<Service> list;

    // private PullToRefreshGridView gridView;
    private SweetAlertDialog sweetAlertDialog;

    private MyViewPager myViewPager;

    // private SweetAlertDialog alertDialog;

    private View rootContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootContainer==null){
            rootContainer=inflater.inflate(R.layout.fragment_first_destination, null);
            isPrepared = true;
            init(rootContainer);

            lazyLoad();
        }

        return rootContainer;
    }

    private void initIndicator() {
        ILoadingLayout startLabels = mGridView.getLoadingLayoutProxy(true,
                false);
        startLabels.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("松开刷新数据");// 下来达到一定距离时，显示的提示

        // ILoadingLayout endLabels = gridView.getLoadingLayoutProxy(
        // false, true);
        // endLabels.setPullLabel("你可劲拉，拉2...");// 刚下拉时，显示的提示
        // endLabels.setRefreshingLabel("好嘞，正在刷新2...");// 刷新时
        // endLabels.setReleaseLabel("你敢放，我就敢刷新2...");// 下来达到一定距离时，显示的提示

    }

    private class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Thread.sleep(2000);
                postAsync("getAllService", null);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // mListItems.add("" + mItemCount++);
            // mAdapter.notifyDataSetChanged();
            // Call onRefreshComplete when the list has been refreshed.
            mGridView.onRefreshComplete();
        }
    }

    private void init(View view) {
        initView(view);

        // initEvent();
    }

    private void initEvent() {
        thirdFragmentAdapter
                .setOnThirdFragmentBtListener(new OnThirdFragmentBtListener() {

                    @Override
                    public void onBtclick(View v, int position) {
                        // ShowToast(position + "");

                        chatWithGuide(position);
                        if (!isContain(position)) {
                            MyUser user = list.get(position).getUser();
                            Uri uri = Uri.parse(user.getAvatar());
                            App.getInsatnce()
                                    .getUserInfos()
                                    .add(new UserInfo(user.getObjectId(), user
                                            .getNick(), uri));
                            // ConnectRong.updateFrindsInfo(null);
                        }

                    }
                });

        thirdFragmentAdapter
                .setOnThirdFragmentAllViewClickListener(new onThirdFragmentAllViewClickListener() {

                    @Override
                    public void onThirdFragmentAllViewClick(View v, int pos) {

                        Intent intent = new Intent(new Intent(
                                FirstFragmentDestination.this.getActivity(),
                                ShowGuideDetailActivity.class));
                        intent.putExtra(Service.TAG, list.get(pos));
                        // intent.putExtra(name, value)
                        // intent.putExtra("targetUserId",
                        // list.get(pos).getUser()
                        // .getObjectId());
                        // intent.putExtra("targetUserName", list.get(pos)
                        // .getUser().getUsername());
                        startActivity(intent);

                    }
                });
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

    private void initView(View view) {
        // alertDialog = new SweetAlertDialog(getActivity(),
        // SweetAlertDialog.PROGRESS_TYPE);
        mGridView = (PullToRefreshGridView) view
                .findViewById(R.id.id_gridview_des);
        myViewPager = (MyViewPager) view
                .findViewById(R.id.id_viewpager_fragment_first_dec);
        initAdapter();

        sweetAlertDialog = new SweetAlertDialog(
                FirstFragmentDestination.this.getActivity(), 5);
        sweetAlertDialog.setTitleText("加载中...");
        sweetAlertDialog.showCancelButton(false);

    }

    private MyPagerAdapter adapter;

    public void initAdapter() {
        adapter = new MyPagerAdapter();
        ImageView v = new ImageView(getActivity());
        v.setLayoutParams(new LayoutParams(-1, -1));
        v.setScaleType(ScaleType.FIT_XY);
        v.setImageResource(R.drawable.p1);

        ImageView v2 = new ImageView(getActivity());
        v2.setLayoutParams(new LayoutParams(-1, -1));
        v2.setScaleType(ScaleType.FIT_XY);
        v2.setImageResource(R.drawable.p2);

        ImageView v3 = new ImageView(getActivity());
        v3.setLayoutParams(new LayoutParams(-1, -1));
        v3.setScaleType(ScaleType.FIT_XY);
        v3.setImageResource(R.drawable.p3);
        adapter.addItem("", v);
        adapter.addItem("", v2);
        adapter.addItem("", v3);
        myViewPager.setAdapter(adapter);

        // adapter.getp
        myViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(final int arg0) {
                /**
                 * 当用手切换页面时，来获得当前的view，从而给每个view设置监听
                 */
                adapter.getItem(arg0).v
                        .setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Toast.makeText(
                                        FirstFragmentDestination.this
                                                .getActivity(), arg0 + "",
                                        Toast.LENGTH_SHORT).show();

                            }
                        });

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        /**
         * 根据message获取的值来设定ViewPager的item
         */
        controllMyViewPager();
        new Thread(new MyThread()).start();

    }

    private Handler mHandler;

    /**
     * 根据message获取的值来设定ViewPager的item
     */
    private void controllMyViewPager() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                final int count = msg.arg1;

                switch (count) {
                    case 1:
                        myViewPager.setCurrentItem(0, true);
                        break;
                    case 2:
                        myViewPager.setCurrentItem(1, true);
                        break;

                    case 3:
                        myViewPager.setCurrentItem(2, true);
                        break;

                }

            }
        };

    }

    private int count = 1;

    public class MyThread extends Thread {

        @Override
        public void run() {
            super.run();
            /**
             * 有三个页面，且每过三秒自动切换一次
             */
            PagerItemlogic();

        }
    }

    @Override
    protected void lazyLoad() {

        if (!isPrepared || !isVisible) {
            // Log.i("lazyLoad1", isPrepared + ":" + isPrepared);
            return;
        }

        // Log.i("lazyLoad2", isPrepared + ":" + isPrepared);

        // baseProgress.show();
        sweetAlertDialog.show();
        postAsync("getAllService", null);
    }

    /**
     * 有三个页面，且每过三秒自动切换一次
     */
    public synchronized void PagerItemlogic() {
        while (true) {

            try {
                Thread.sleep(3000);
                if (count > 4) {
                    break;
                } else {
                    if (count == 4) {
                        count = 1;
                    }
                    /**
                     * 当每个页面自动切换时，获得当前的view，然后做监听
                     */
                    setItemListener(count - 1);
                    Message message = Message.obtain();
                    message.arg1 = count;
                    count++;
                    mHandler.sendMessage(message);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 当每个页面自动切换时，获得当前的view，然后做监听
     */
    private synchronized void setItemListener(final int count) {
        adapter.getItem(count).v.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(FirstFragmentDestination.this.getActivity(),
                        count + "", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onPause() {

        isVisible = false;
        sweetAlertDialog.dismiss();
        // baseProgress.dismiss();

        isPrepared = false;

        super.onPause();
    }

    @Override
    public boolean handleMessage(Message msg) {
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

    private void handleFaild(String string) {
        // baseProgress.dismiss();
        sweetAlertDialog.dismiss();
    }

    private void handleSuccess(String json) {
        // baseProgress.dismiss();
        sweetAlertDialog.dismiss();
        Gson gson = new Gson();
        list = gson.fromJson(json, new TypeToken<List<Service>>() {
        }.getType());
        initIndicator();
        Log.i("TAG", list.toString());
        thirdFragmentAdapter = new ThirdFragmentAdapter(getActivity(), list);
        mGridView.setAdapter(thirdFragmentAdapter);
        initEvent();

    }

    public void setCountMax() {

        count = 100;

    }

    public void setCountMin() {
        count = 1;

    }

    @Override
    public void onResume() {

        super.onResume();
        mGridView.setOnRefreshListener(new OnRefreshListener2<GridView>() {

            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<GridView> refreshView) {
                Log.e("TAG", "onPullDownToRefresh"); // Do work to
                String label = DateUtils.formatDateTime(
                        FirstFragmentDestination.this.getActivity(),
                        System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                new GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<GridView> refreshView) {
                Log.e("TAG", "onPullUpToRefresh"); // Do work to refresh
                // the list here.
                new GetDataTask().execute();
            }
        });
    }

}
