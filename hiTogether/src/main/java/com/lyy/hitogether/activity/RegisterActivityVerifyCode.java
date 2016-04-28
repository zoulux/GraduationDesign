package com.lyy.hitogether.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lyy.hitogether.R;
import com.lyy.hitogether.mydialog.SweetAlertDialog;
import com.lyy.hitogether.mydialog.SweetAlertDialog.OnSweetClickListener;

import org.simple.eventbus.EventBus;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.VerifySMSCodeListener;

public class RegisterActivityVerifyCode extends BaseActivity {

    @ViewInject(R.id.id_et_verify_code)
    private EditText verifyCode;

    @ViewInject(R.id.id_tv_phone)
    private TextView phoneTV;

    private String phone;

    private SweetAlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_verify_code);
        ViewUtils.inject(this);

        phone = getIntent().getStringExtra(
                RegisterActivityGetNumber.PHONE_NUMBER);

        initData();

        mDialog = new SweetAlertDialog(this);
        mDialog.setCancelable(false);
        mDialog.setConfirmText("确认");

    }

    private void initData() {
        if (TextUtils.isEmpty(phone)) {
            phoneTV.setText("请填写手机号后重试！");

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    RegisterActivityVerifyCode.this.finish();

                }
            }, 2000);

        } else {
            phoneTV.setText(phone);
        }

    }

    @OnClick(R.id.id_bt_verify)
    public void verify(View v) {

        BmobSMS.verifySmsCode(RegisterActivityVerifyCode.this, phone,
                verifyCode.getText().toString(), new VerifySMSCodeListener() {

                    @Override
                    public void done(BmobException ex) {
                        if (ex == null) {
                            mDialog.setTitleText("验证通过");
                            mDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                            mDialog.setConfirmClickListener(new SuccessOnSweetClickListener());
                            mDialog.show();

                            Log.i("BmobSMS", "验证通过");
                        } else {
                            mDialog.setTitleText("验证失败");
                            mDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);

                            mDialog.setConfirmClickListener(new FaildOnSweetClickListener());
                            mDialog.show();

                            Log.i("BmobSMS", "验证失败：code =" + ex.getErrorCode()
                                    + ",msg = " + ex.getLocalizedMessage());
                        }

                    }
                });

    }

    class SuccessOnSweetClickListener implements OnSweetClickListener {

        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {

            Intent i = new Intent(RegisterActivityVerifyCode.this,
                    RegisterActivitySetPwd.class);
            i.putExtra(RegisterActivityGetNumber.PHONE_NUMBER, phone);
            startActivity(i);
            sweetAlertDialog.dismiss();
            RegisterActivityVerifyCode.this.finish();
        }

    }

    class FaildOnSweetClickListener implements OnSweetClickListener {

        @Override
        public void onClick(SweetAlertDialog sweetAlertDialog) {
            EventBus.getDefault().post(1, RegisterActivityGetNumber.EVENT_COUNTDOWN);
            sweetAlertDialog.dismiss();
            RegisterActivityVerifyCode.this.finish();
        }
    }
}
