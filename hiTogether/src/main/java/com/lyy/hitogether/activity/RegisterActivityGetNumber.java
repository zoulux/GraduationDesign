package com.lyy.hitogether.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lyy.hitogether.R;
import com.lyy.hitogether.mydialog.SweetAlertDialog;

public class RegisterActivityGetNumber extends BaseActivity {
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
		registerBoradcastReceiver();
	}

	private void registerBoradcastReceiver() {

		IntentFilter ifFilter = new IntentFilter();
		ifFilter.addAction(RegisterActivityVerifyCode.ACTION_BROADCASTRECIVER);
		registerReceiver(faildBroadcastReceiver, ifFilter);
	}

	@OnClick(R.id.id_bt_phone_next)
	public void phoneNext(View v) {
		/*
		 * SimpleDateFormat format = new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); String sendTime =
		 * format.format(new Date());
		 * BmobSMS.requestSMS(RegisterActivityGetNumber.this, phoneNumber
		 * .getText().toString(), "整点报时：" + new Date(), sendTime, new
		 * RequestSMSCodeListener() {
		 * 
		 * @Override public void done(Integer smsId, BmobException exc) { if
		 * (exc == null) { Log.i("sms", smsId + "成功"); } else {
		 * 
		 * Log.i("sms", "errorCode = " + exc.getErrorCode() + ",errorMsg = " +
		 * exc.getLocalizedMessage()); } } });
		 */

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
					intent.putExtra(PHONE_NUMBER, phoneNumber.getText()
							.toString());
					startActivity(intent);
				} else {
					ShowToast("稍后再试一下吧");

					Log.i("bmob", smsId + "Success" + "BmobException:  " + ex);

				}
				dialog.dismiss();

			}
		});

	}

	private BroadcastReceiver faildBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			setButtonText();
			faildBroadcastReceiver.abortBroadcast();
		}
	};

	int i = 50;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mButtonNext.setText((i--) + "秒后重试");

			if (msg.what == 0) {
				mButtonNext.setClickable(true);
				mButtonNext.setText("下一步");
				i = 50;
			}
		};
	};

	protected void setButtonText() {

		mButtonNext.setClickable(false);

		new Thread() { 
			public void run() {
				while (i > 0) {
					try {
						Thread.sleep(1000);
						mHandler.sendEmptyMessage(i);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			};
		}.start();

	}

//	@OnClick(R.id.id_bt_phone_has_code)
	private void hasCode(View v) {
		Intent i=new Intent(RegisterActivityGetNumber.this, RegisterActivityVerifyCode.class);
		i.putExtra(PHONE_NUMBER, phoneNumber.getText()
				.toString());
		startActivity(i);
		
		

	}
}
