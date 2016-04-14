package com.lyy.hitogether.activity.fragment;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;
import cn.bmob.v3.listener.CloudCodeListener;

import com.lyy.hitogether.mydialog.SweetAlertDialog;
import com.lyy.hitogether.util.HttpUtils;

public abstract class BaseFragment extends Fragment implements Callback {
	protected static final int GET_SUCCESS = 0x110;
	protected static final int GET_FAILD = 0x111;
	protected Handler baseHandler = new Handler(this);
	protected boolean isVisible;

	 protected ProgressDialog baseProgress = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		initProgress();
		super.onCreate(savedInstanceState);
	}

	private void initProgress() {
		baseProgress=new ProgressDialog(getActivity());
		baseProgress.setTitle("Loading");
	}

	protected void postAsync(String mosth, JSONObject params) {
		Log.i("BaseFragment", "postAsync");
		HttpUtils.getHttpData(getActivity(), mosth, null,
				new CloudCodeListener() {

					@Override
					public void onSuccess(Object results) {
						Log.i("BaseFragment", "onSuccess");
						Message msg = Message.obtain();
						msg.what = GET_SUCCESS;
						msg.obj = results;
						baseHandler.sendMessage(msg);

					}

					@Override
					public void onFailure(int code, String arg1) {
						Log.i("BaseFragment", "onFailure");
						Message msg = Message.obtain();
						msg.what = GET_FAILD;
						msg.obj = code;
						baseHandler.sendMessage(msg);
					}
				});

	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}

	}

	protected void onVisible() {
		Log.i("TAG", "1");
		lazyLoad();
	}

	protected void onInvisible() {

	}

	protected abstract void lazyLoad();

	public void ShowLog(String msg) {
		Log.i(">>>>", msg);
	}

	public void ShowLog(int msg) {
		Log.i(">>>>", msg + "");
	}

	Toast mToast;

	public void ShowToast(final int resId) {

		if (mToast == null) {
			mToast = Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(resId);
		}
		mToast.show();

	}

	public void ShowToast(final String string) {

		if (mToast == null) {
			mToast = Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(string);
		}
		mToast.show();

	}

}
