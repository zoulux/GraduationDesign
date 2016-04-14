package com.lyy.hitogether.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.listener.SaveListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lyy.hitogether.R;
import com.lyy.hitogether.bean.MyUser;
import com.lyy.hitogether.mydialog.SweetAlertDialog;

public class RegisterActivitySetPwd extends BaseActivity {

	private SweetAlertDialog mDialog;

	@ViewInject(R.id.id_et_pwd)
	private EditText pwd;
	@ViewInject(R.id.id_et_pwd_again)
	private EditText pwdAgain;

	@ViewInject(R.id.id_tv_number)
	private TextView mTextViewNum;

	private String num;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_set_pwd);
		ViewUtils.inject(this);
		initView();

		initData();

	}

	private void initView() {
		mDialog = new SweetAlertDialog(RegisterActivitySetPwd.this,
				SweetAlertDialog.PROGRESS_TYPE);

	}

	private void initData() {
		num = getIntent()
				.getStringExtra(RegisterActivityGetNumber.PHONE_NUMBER);
		if (TextUtils.isEmpty(num)) {
			mTextViewNum.setText("账号获取错误，请重试！");

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					RegisterActivitySetPwd.this.finish();

				}
			}, 2000);

		} else {
			mTextViewNum.setText(num);
		}

	}

	@OnClick(R.id.id_bt_submit)
	public void submit(View v) {

		if (isLegal()) {

			mDialog.show();
			register();
		}

	}

	private void register() {
		MyUser user = new MyUser();

		user.setUsername(num);
		user.setPassword(getText(pwd));

		String defaultPhoto = "http://file.bmob.cn/M02/CB/99/oYYBAFZdggCAdnfDAAAUqy00rwE554.jpg";
		user.setAvatar(defaultPhoto);

		String defaultNick = SystemClock.elapsedRealtime() + "";
		user.setNick(defaultNick);

		user.setMobilePhoneNumber(num);
		user.setMobilePhoneNumberVerified(true);

		user.setDeviceType("android");
		user.setInstallId(BmobInstallation
				.getInstallationId(RegisterActivitySetPwd.this));
		user.setModel(Build.MODEL);
		user.setBrand(Build.BRAND);
		user.setSdkVerSion(Build.VERSION.SDK_INT);

		user.signUp(RegisterActivitySetPwd.this, new SaveListener() {

			@Override
			public void onSuccess() {
				// getToken(num);

				mDialog.dismiss();

				Intent i = new Intent(RegisterActivitySetPwd.this,
						LoginActivity.class);
				// i.setAction(Intent.)
				startActivity(i);

			}

			@Override
			public void onFailure(int code, String err) {
				mDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);

				mDialog.setTitleText("code:" + code + "   err:" + err);
				ShowToast("code:" + code + "   err:" + err);

			}
		});

	}

	private String getText(EditText et) {

		return et.getText().toString();
	}

	private boolean isLegal() {
		if (TextUtils.isEmpty(getText(pwd))
				|| TextUtils.isEmpty(getText(pwdAgain))) {
			ShowToast("请填写完整");

			return false;
		}

		if (!getText(pwd).equals(getText(pwdAgain))) {
			ShowToast("两次输入不一致");
			return false;
		}

		return true;
	}
}
