package com.lyy.hitogether.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.socialization.Socialization;

import com.lidroid.xutils.ViewUtils;
import com.lyy.hitogether.R;
import com.lyy.hitogether.mydialog.SweetAlertDialog;
import com.lyy.hitogether.test.TestActivity;
import com.lyy.hitogether.util.Config;

public class Initialize extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_initialize);

		ViewUtils.inject(this);
		Bmob.initialize(this, Config.APP_KEY); // 初始化bmob
		ShareSDK.initSDK(this); // 初始化mob
		ShareSDK.registerService(Socialization.class);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				startActivity(new Intent(Initialize.this, LoginActivity.class));
				Initialize.this.finish();
			}
		}, 2000);

		// InitBmobAndRong.init(Initialize.this, new LinitLisetener() {
		//
		// @Override
		// public void success(String userId) {
		// showToast("连接成功");
		//
		// try {
		//
		// startActivity(new Intent(Initialize.this,
		// LoginActivity.class));
		// Initialize.this.finish();
		//
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }
		//
		// @Override
		// public void faild() {
		// Initialize.this.finish();
		// showToast("连接失败");
		//
		// }
		// });

		// // ��Ҫ�Ķ�
		// new Handler().postDelayed(new Runnable() {
		// public void run() {
		// startActivity(new Intent(Initialize.this, MainActivity.class));
		// Initialize.this.finish();
		// }
		// }, 1500);
	}

}
