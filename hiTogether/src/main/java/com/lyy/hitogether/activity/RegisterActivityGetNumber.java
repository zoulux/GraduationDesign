package com.lyy.hitogether.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lyy.hitogether.R;
import com.lyy.hitogether.mydialog.SweetAlertDialog;
import com.lyy.hitogether.util.VerifyUtils;

import org.simple.eventbus.Subscriber;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;

public class RegisterActivityGetNumber extends BaseActivity {
    public static final String EVENT_COUNTDOWN = "EVENT_COUNTDOWN";

    @ViewInject(R.id.id_et_phone)
    private EditText phoneNumber;
    @ViewInject(R.id.id_bt_phone_next)
    private Button mButtonNext;

    public static final String PHONE_NUMBER = "PHONE_NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_get_number);
        ViewUtils.inject(this);
        org.simple.eventbus.EventBus.getDefault().register(this);
    }

    @Subscriber(tag = "EVENT_COUNTDOWN")
    public void countdown(int i) {
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    int time = 60;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if (time > 0) {
                    mButtonNext.setText(String.format("%d 秒后可以重新发送", time--));
                    mButtonNext.setClickable(false);
                    sendEmptyMessageDelayed(0, 1000);
                } else if (time == 0) {
                    time = 60;
                    mButtonNext.setClickable(true);
                    mButtonNext.setText("获取验证码");
                }

            }

        }
    };


    @OnClick(R.id.id_bt_phone_next)
    public void phoneNext(View v) {
        final String mobiles = phoneNumber.getText().toString();

        if (!VerifyUtils.isPhone(mobiles)) {
            showToast("请准确填写手机号");
            return;
        }


        final SweetAlertDialog dialog = new SweetAlertDialog(
                RegisterActivityGetNumber.this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText("请稍后");

        dialog.show();

        BmobSMS.requestSMSCode(RegisterActivityGetNumber.this, phoneNumber
                .getText().toString(), "SMSTem1", new RequestSMSCodeListener() {

            @Override
            public void done(Integer smsId, BmobException ex) {
                if (ex == null) {
                    Log.i("bmob", smsId + "Success");

                    Intent intent = new Intent(RegisterActivityGetNumber.this,
                            RegisterActivityVerifyCode.class);
                    intent.putExtra(PHONE_NUMBER, mobiles);
                    startActivity(intent);
                } else {
                    showToast("稍后再试一下吧");

                    Log.i("bmob", smsId + "Success" + "BmobException:  " + ex);

                }
                dialog.dismiss();

            }
        });

    }


    //	@OnClick(R.id.id_bt_phone_has_code)
    private void hasCode(View v) {
        Intent i = new Intent(RegisterActivityGetNumber.this, RegisterActivityVerifyCode.class);
        i.putExtra(PHONE_NUMBER, phoneNumber.getText()
                .toString());
        startActivity(i);
    }

}
