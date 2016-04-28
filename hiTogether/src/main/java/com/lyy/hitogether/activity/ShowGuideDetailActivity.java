package com.lyy.hitogether.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lyy.hitogether.R;
import com.lyy.hitogether.bean.MyUser;
import com.lyy.hitogether.bean.Service;
import com.lyy.hitogether.bean.Trip;
import com.lyy.hitogether.bean.TripItem;
import com.lyy.hitogether.util.CommonUtils;
import com.lyy.hitogether.util.Config;
import com.lyy.hitogether.view.CustomTitleBarView;
import com.lyy.hitogether.view.CustomTitleBarView.onLeftBarViewClickListener;
import com.lyy.hitogether.view.MyGuideDetailLayout;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import io.rong.imkit.RongIM;

public class ShowGuideDetailActivity extends BaseActivity implements
        OnClickListener {
    private MyGuideDetailLayout layout;
    private CustomTitleBarView customTitleBarView;
    private LinearLayout callHe;
    private Button talkToHe;
    private RatingBar grade;
    private TextView checkText;
    private ImageView alreadyCheckImg;
    private String targetUserId;
    private String targetUserName;

    @ViewInject(R.id.id_show_guide_detail_content)
    private TextView mTVDesc;

    @ViewInject(R.id.id_show_guide_detail_img)
    private ImageView mIVGuideDetail;
    @ViewInject(R.id.id_guid_detail_guid_avertar)
    private ImageView mIVGuideAvater;

    @ViewInject(R.id.id_tv_guid_name)
    private TextView mTVGuidName;
    @ViewInject(R.id.id_tv_service_name)
    private TextView mTVScenery;


    @ViewInject(R.id.tv_phone)
    private TextView tvPhone;

    @ViewInject(R.id.tv_money)
    private TextView tvMoney;


    private Service currentService;
    private MyUser currentUser;

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        loadServerData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_guide_detail);
        ViewUtils.inject(this);
        getIntentValue();
        initView();

    }

    private void getIntentValue() {
        Intent intent = getIntent();
        // targetUserId = intent.getStringExtra("targetUserId");
        // targetUserName = intent.getStringExtra("targetUserName");
        // System.out.println(targetUserId + ">>>>>>>>>" + targetUserName);
        currentService = (Service) intent.getSerializableExtra(Service.TAG);
        if (currentService == null) {
            finish();
        }

        currentUser = currentService.getUser();
    }

    private void initView() {
        layout = (MyGuideDetailLayout) findViewById(R.id.id_show_guide_detail_MyLayout);
        customTitleBarView = (CustomTitleBarView) findViewById(R.id.id_show_guide_detail_title);
        callHe = (LinearLayout) findViewById(R.id.id_show_guide_detail_bottom_call_he);
        talkToHe = (Button) findViewById(R.id.id_show_guide_detail_bottom_talk);
        grade = (RatingBar) findViewById(R.id.id_show_guide_detail_grade);
        checkText = (TextView) findViewById(R.id.id_show_guide_detail_check_text);
        alreadyCheckImg = (ImageView) findViewById(R.id.id_show_guide_detail_already_check_img);
        // alreadyCheckImg.setVisibility(View.GONE);

        fillData();

        // checkText.setText("已认证");
        // grade.setRating(1.5f);

        // layout.addImage(R.drawable.p3);
        // layout.addText("这是我们的第一站");
        // layout.addImage(R.drawable.girl);
        // layout.addText("这是我们的第二站,景点不错");
        // layout.addImage(R.drawable.p1);
        // layout.addText("这是我们的第三站，这个也很好");
        // layout.addImage(R.drawable.p2);
        // layout.addText("这是我们的最后一站，感觉不错吧");

        customTitleBarView.setLeftImageSuorce(R.drawable.back_indicator);
        // customTitleBarView.setCenterText("马云");
        customTitleBarView.setLeftImagePaddingLeft(10);
        customTitleBarView.setCenterTextColor(Color.WHITE);
        customTitleBarView.setCenterTextSize(18);

        setListener();

    }

    private void loadServerData() {
        BmobQuery<Trip> query = new BmobQuery<Trip>();
        query.addWhereEqualTo("user", currentUser);
        query.order("-createdAt");
        query.setLimit(1);
        query.findObjects(ShowGuideDetailActivity.this,
                new FindListener<Trip>() {

                    @Override
                    public void onSuccess(List<Trip> trips) {
                        if (trips != null && trips.size() != 0) {
                            Trip trip = trips.get(0);
                            Message msg = mHandler.obtainMessage();
                            msg.obj = trip.getData();
                            mHandler.sendMessage(msg);

                        }

                    }

                    @Override
                    public void onError(int arg0, String arg1) {

                    }
                });
    }

    private void fillData() {

        if (currentUser.getIsAuthentication() == null) {
            checkText.setText("未认证");
        } else if (!currentUser.getIsAuthentication()) {
            checkText.setText("未认证");
        } else {
            checkText.setText("已认证");
        }

        if (currentUser.getStar() == null) {
            grade.setRating(0);
        } else {
            grade.setRating(currentUser.getStar());
        }

        CommonUtils.setTextView(mTVGuidName, currentUser.getNick(), "昵称");
        if (currentUser.getNick() == null) {
            customTitleBarView.setCenterText("乐友游");

        } else {
            customTitleBarView.setCenterText(currentUser.getNick());
        }

        CommonUtils.setTextView(mTVDesc, currentService.getIntroduction(),
                "好地方，值得去旅行!");

        CommonUtils.setImageView(mIVGuideDetail, currentService.getShowImg(),
                Config.DEFAULT_PIC);
        CommonUtils.setImageView(mIVGuideAvater, currentUser.getAvatar(),
                Config.DEFAULT_AVATER);
        CommonUtils.setTextView(mTVScenery, currentService.getDestination(),
                "景点名称");
        tvPhone.setText(currentService.getUser().getMobilePhoneNumber());

        tvMoney.setText( String.format("¥%.2f",currentService.getPrice()));
    }

    private void setListener() {
        customTitleBarView
                .setOnLeftBarViewClickListener(new onLeftBarViewClickListener() {

                    @Override
                    public void onclick(View v) {
                        ShowGuideDetailActivity.this.finish();
                    }
                });
        callHe.setOnClickListener(this);
        talkToHe.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            // 打电话
            case R.id.id_show_guide_detail_bottom_call_he:
                callToguide();
                break;
            // 和导游聊天
            case R.id.id_show_guide_detail_bottom_talk:
                chatWithGuide();
                break;
        }
    }

    private void chatWithGuide() {
        RongIM.getInstance().startPrivateChat(this, currentUser.getObjectId(),
                "与" + currentUser.getNick() + "聊天中");

    }

    private void callToguide() {
        String tel = currentUser.getMobilePhoneNumber();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + tel));
        startActivity(intent);

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            List<TripItem> data = (List<TripItem>) msg.obj;

            for (TripItem item : data) {

                if (!TextUtils.isEmpty(item.getPhotoPath())) {
                    layout.addImage(item.getPhotoPath());

                }
                if (!TextUtils.isEmpty(item.getPhotoDescribe())) {
                    layout.addText(item.getPhotoDescribe());

                }
            }

        }

        ;
    };

}
