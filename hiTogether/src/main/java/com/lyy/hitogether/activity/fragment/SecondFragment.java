package com.lyy.hitogether.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.lyy.hitogether.R;
import com.lyy.hitogether.bean.Demand;
import com.lyy.hitogether.mydialog.SweetAlertDialog;
import com.lyy.hitogether.mydialog.SweetAlertDialog.OnSweetClickListener;
import com.lyy.hitogether.view.SwitchDesPopUpWindow;
import com.lyy.hitogether.view.SwitchDesPopUpWindow.onCorrectClickListener;
import com.lyy.hitogether.view.SwitchPeoplePopUpWindow;
import com.lyy.hitogether.view.SwitchPeoplePopUpWindow.onCorrectClickListener3;
import com.lyy.hitogether.view.SwitchTimePopUpWindow;
import com.lyy.hitogether.view.SwitchTimePopUpWindow.onCorrectClickListener2;

import java.util.Date;

import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.GetServerTimeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class SecondFragment extends BaseFragment implements OnClickListener {

    private Button bt;
    private View mBottomView;
    private TextView mTimeTextView;
    private TextView mDesTextView;
    private TextView mPeopleTextView;
    private SwitchDesPopUpWindow mSwitchDesPopUpWindow;
    private SwitchTimePopUpWindow mSwitchTimePopUpWindow;
    private SwitchPeoplePopUpWindow mSwitchPeoplePopUpWindow;
    private int mHeight;

    private long currrntTime = new Date().getTime();
    private SweetAlertDialog dialog;
    private OnekeyShare mShare;

    private View rootContaniner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootContaniner == null) {
            initDialog();
            initShare();
            rootContaniner = inflater.inflate(R.layout.fragment_second, container, false);
        }
        return rootContaniner;

    }

    private void initShare() {
        mShare = new OnekeyShare();
        mShare.disableSSOWhenAuthorize();
        mShare.setSite("乐友游");
        mShare.setTitleUrl("http://www.pgyer.com/hitogether");
        mShare.setText("带TA去旅行，乐友游...");
        mShare.setImageUrl("http://file.bmob.cn/M02/C8/8A/oYYBAFZb8vKAIZgRAAC1kp2SSaQ113.png");
        mShare.setUrl("http://www.pgyer.com/hitogether");
        mShare.setComment("我是乐友游的超级内容部分");
        mShare.setSiteUrl("http://www.pgyer.com/hitogether");

    }

    private void initDialog() {
        dialog = new SweetAlertDialog(getActivity(),
                SweetAlertDialog.SUCCESS_TYPE);

        dialog.setCancelText("否");
        dialog.setCancelClickListener(new OnSweetClickListenerFaild());
        dialog.setConfirmText("是");
        dialog.setTitleText("需求已提交，是否分享一下?");
        dialog.setConfirmClickListener(new OnSweetClickListenerSuccess());
        // dialog.setContentText();

    }

    private void getServerTime() {
        Bmob.getServerTime(getActivity(), new GetServerTimeListener() {

            @Override
            public void onSuccess(long time) {
                currrntTime = time;
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                showToast("获取服务器时间错误");

            }
        });

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initEvent();
    }

    private void initEvent() {
        bt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                handleData();

                // startActivity(new Intent(SecondFragment.this.getActivity(),
                // ShareMyTravalActivity.class));
            }
        });

    }

    protected void handleData() {
        if (!dataIsLegal()) {
            showToast("哥们，把三项数据都填了");
            return;
        }

        postDataToServer();

    }

    private void postDataToServer() {

        Demand d = new Demand();
        d.setDemandId(SystemClock.elapsedRealtime() + ""
                + (int) (Math.random() * 1000));
        d.setDemandProgress(Demand.demandProgressCommit);
        d.setDestination(getText(mDesTextView));
        d.setGoTime(getTime(mTimeTextView));

        d.setPeopleNum(Integer.parseInt(getText(mPeopleTextView).substring(0,
                getText(mPeopleTextView).length() - 1)));
        d.setUserId(BmobUserManager.getInstance(getActivity())
                .getCurrentUserObjectId());
        d.setZan(0);

        d.save(getActivity(), new SaveListener() {

            @Override
            public void onSuccess() {

                dialog.show();

            }

            @Override
            public void onFailure(int arg0, String arg1) {
                showToast("提交失败,再试一次吧...");
            }
        });

    }

    private String getTime(TextView tv) {
        String text = getText(tv);
        int yIndex = text.indexOf("年");
        int mIndex = text.indexOf("月");
        int dIndex = text.indexOf("日");

        String year = text.substring(0, yIndex);
        String mon = text.substring(yIndex + 1, mIndex);
        String day = text.substring(mIndex + 1, dIndex);

        return year + "-" + mon + "-" + day;
    }

    private BmobDate getTime() {
        // TODO Auto-generated method stub
        return BmobDate.createBmobDate("yyyy-MM-dd HH:mm:ss",
                String.valueOf(currrntTime / 1000));
    }

    private boolean dataIsLegal() {
        if (isEmpty(getText(mTimeTextView)) || isEmpty(getText(mDesTextView))
                || isEmpty(getText(mPeopleTextView))) {
            return false;
        }

        return true;
    }

    private boolean isEmpty(String text) {

        return TextUtils.isEmpty(text);
    }

    private String getText(TextView view) {

        return view.getText().toString();
    }

    private void initView(View view) {
        bt = (Button) view.findViewById(R.id.fragment_second_send_bt);
        mBottomView = view.findViewById(R.id.id_view);
        mTimeTextView = (TextView) view
                .findViewById(R.id.id_fragment_second_time);
        mDesTextView = (TextView) view
                .findViewById(R.id.id_fragment_second_destination);
        mPeopleTextView = (TextView) view
                .findViewById(R.id.id_fragment_second_people_count);
        mSwitchDesPopUpWindow = new SwitchDesPopUpWindow(getActivity());
        mSwitchTimePopUpWindow = new SwitchTimePopUpWindow(getActivity());
        mSwitchPeoplePopUpWindow = new SwitchPeoplePopUpWindow(getActivity());
        initListener();
        setPopUpWinDowListener();

    }

    private void initListener() {
        mTimeTextView.setOnClickListener(SecondFragment.this);
        mDesTextView.setOnClickListener(SecondFragment.this);
        mPeopleTextView.setOnClickListener(SecondFragment.this);

    }

    private void setPopUpWinDowListener() {
        WindowManager wm = (WindowManager) this.getActivity().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);

        mHeight = displayMetrics.heightPixels;
        mSwitchDesPopUpWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mSwitchTimePopUpWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mSwitchPeoplePopUpWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                lightOn();
            }
        });

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public boolean handleMessage(Message arg0) {

        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.id_fragment_second_destination:

                mSwitchDesPopUpWindow.showAsDropDown(mBottomView, 0, 0);
                setDesData();
                lightOff();
                break;
            case R.id.id_fragment_second_time:
                mSwitchTimePopUpWindow.showAsDropDown(mBottomView, 0, 0);
                setTimeData();
                lightOff();
                break;
            case R.id.id_fragment_second_people_count:
                mSwitchPeoplePopUpWindow.showAsDropDown(mBottomView, 0, 0);
                setPeopleData();
                lightOff();
                break;

        }

    }

    private void setPeopleData() {
        mSwitchPeoplePopUpWindow
                .setOnCorrectClickListener3(new onCorrectClickListener3() {

                    @Override
                    public void onCorrectClick3(View v, int count) {
                        mPeopleTextView.setText(count + "人");
                    }
                });

    }

    private void setTimeData() {
        mSwitchTimePopUpWindow
                .setOnCorrectClickListener2(new onCorrectClickListener2() {

                    @Override
                    public void onCorrectClick2(View v, String Year,
                                                String Month, String Day) {
                        mTimeTextView.setText(Year + "年" + Month + "月" + Day
                                + "日");

                    }
                });

    }

    private void setDesData() {
        mSwitchDesPopUpWindow
                .setOnCorrectClickListener(new onCorrectClickListener() {

                    @Override
                    public void onCorrectClick(View v, String provinve,
                                               String city) {
                        mDesTextView.setText(provinve
                                + mSwitchDesPopUpWindow.getBigLable() + city
                                + mSwitchDesPopUpWindow.getSmallLable());

                    }
                });

    }

    protected void lightOn() {
        WindowManager.LayoutParams lp = getActivity().getWindow()
                .getAttributes();
        lp.alpha = 1.0f;
        getActivity().getWindow().setAttributes(lp);

    }

    protected void lightOff() {
        WindowManager.LayoutParams lp = getActivity().getWindow()
                .getAttributes();
        lp.alpha = .3f;
        getActivity().getWindow().setAttributes(lp);

    }

    class OnSweetClickListenerSuccess implements OnSweetClickListener {

        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
            sweetAlertDialog.dismiss();
            mShare.setTitle(" 我们一起去" + getText(mDesTextView) + "吧！那就赶快下载这个App");
            mShare.show(getActivity());

        }

    }

    class OnSweetClickListenerFaild implements OnSweetClickListener {

        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
            sweetAlertDialog.dismiss();
        }

    }
}
