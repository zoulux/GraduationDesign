package com.lyy.hitogether.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lyy.hitogether.R;
import com.lyy.hitogether.bean.MyUser;
import com.lyy.hitogether.global.App;
import com.lyy.hitogether.mydialog.SweetAlertDialog;
import com.lyy.hitogether.util.ConnectRong;
import com.lyy.hitogether.util.ConnectRong.MyConnectListener;
import com.lyy.hitogether.util.NetUtils;
import com.lyy.hitogether.util.ToastUtil;

import java.util.List;

import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.listener.SaveListener;
import io.rong.imlib.model.UserInfo;

public class LoginActivity extends BaseActivity {

    private EditText etName;
    private EditText etPwd;

    @ViewInject(R.id.id_loginBt)
    Button btLogin;

    @ViewInject(R.id.ll_action)
    LinearLayout llAction;
    //
    // final ClearActivity username = (ClearActivity)
    // findViewById(R.id.id_userName);
    // final ClearActivity password = (ClearActivity)
    // findViewById(R.id.id_userPwd);

    SweetAlertDialog sweetAlertDialog = null;
    // Բ��ͷ��
    // ��¼����
    // 登录按钮只能点一次，否走如果你连续速度很快的点登录按钮的话，dialog将会被连续触发，也就会产生很多个MainActivity
    //  private boolean isFirstClick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // �����ޱ�����
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login2);
        ViewUtils.inject(this);
        // ͸��״̬��
        getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        init();
    }

    private void init() {
        initView();
        initEvent();
        initAction();
    }

    private void initAction() {
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(llAction, "alpha", 0.5f, 1.0f);
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(llAction, "translationY", 0, -500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaAnimator, translationAnimator);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(800);
        animatorSet.start();
    }

    private void initEvent() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    private static final int HANDLE_WHAT = 0x123;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == HANDLE_WHAT) {
                Log.i("LoginActivity", "4");

                ConnectRong.connect(LoginActivity.this,
                        new MyConnectListener() {

                            @Override
                            public void onSuccess(List<UserInfo> users) {
                                Log.i("LoginActivity", "5");

                                if (users != null && users.size() > 0) {

                                    for (UserInfo userInfo : users) {
                                        Log.i("ConnectRong", userInfo.getName());
                                    }
                                }


                                LoginActivity.this.startActivity(new Intent(LoginActivity.this,
                                        MainActivity.class));
                                sweetAlertDialog.dismiss();
                                LoginActivity.this.finish();
                            }

                            @Override
                            public void onFaild(String str) {
                                sweetAlertDialog.dismiss();
                                showToast("连接失败");

                            }
                        });

                // /**
                // * 连接融云
                // */
                //
                // String token = BmobUser.getCurrentUser(LoginActivity.this,
                // MyUser.class).getToken();
                //
                // Log.i(BmobUser.getCurrentUser(LoginActivity.this,
                // MyUser.class)
                // .getNick(), token);
                // RongIM.connect(token, new ConnectCallback() {
                //
                // @Override
                // public void onSuccess(String arg0) {
                //
                // initUserInfo();
                // sweetAlertDialog.dismiss();
                //
                // List<Conversation> conversationList = RongIM
                // .getInstance().getRongIMClient()
                // .getConversationList(ConversationType.PRIVATE);
                // Log.i("conversationList", conversationList.get(0)
                // .getTargetId());
                //
                // startActivity(new Intent(LoginActivity.this,
                // MainActivity.class));
                // LoginActivity.this.finish();
                // }
                //
                // @Override
                // public void onError(ErrorCode arg0) {
                // showToast("连接失败");
                // LoginActivity.this.finish();
                // }
                //
                // @Override
                // public void onTokenIncorrect() {
                //
                // }
                // });

            }

        }

        ;

    };

    protected void login() {
        if (!NetUtils.isConnected(this)) {
            ToastUtil.message("请检查网络");
            return;

        }


        if (judge()) {
            Log.i("LoginActivity", "1");
            sweetAlertDialog.show();
            MyUser user = new MyUser();
            user.setUsername(etName.getText().toString());
            user.setPassword(etPwd.getText().toString());
            BmobUserManager.getInstance(LoginActivity.this)
                    .bindInstallationForRegister(user.getUsername());

            BmobUserManager.getInstance(LoginActivity.this).login(user,
                    new SaveListener() {

                        @Override
                        public void onSuccess() {
                            mHandler.sendEmptyMessage(HANDLE_WHAT);
                            App.getInsatnce().setCurrentUser(
                                    BmobUserManager.getInstance(LoginActivity.this)
                                            .getCurrentUser(MyUser.class));
                        }

                        @Override
                        public void onFailure(int arg0, String arg1) {
                            Log.i("LoginActivity", "3");
                            sweetAlertDialog.dismiss();
                            showToast("账号或密码有误");

                        }
                    });

        } else {
            sweetAlertDialog.dismiss();
            showToast("输入有误");
        }

    }

    protected void initUserInfo() {

    }

    private boolean judge() {
        if (!TextUtils.isEmpty(etPwd.getText().toString())
                && !TextUtils.isEmpty(etName.getText().toString())) {
            return true;
        }

        return false;

    }

    private void initView() {
        etName = (EditText) findViewById(R.id.id_userName);
        etPwd = (EditText) findViewById(R.id.id_userPwd);

        sweetAlertDialog = new SweetAlertDialog(LoginActivity.this, 5);
        sweetAlertDialog.setTitleText("正在登陆");
        sweetAlertDialog.showCancelButton(false);


    }


    @Override
    public void onBackPressed() {

        // sweetAlertDialog.dismiss();
        // Todo 随时取消登录
        super.onBackPressed();
        mHandler.removeMessages(HANDLE_WHAT);
    }

    @OnClick(R.id.id_newuser)
    public void newUser(View v) {

        Intent intent = new Intent(LoginActivity.this,
                RegisterActivityGetNumber.class);
        startActivity(intent);
    }

}
