package com.lyy.hitogether.provider;

import com.lyy.hitogether.activity.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import io.rong.imkit.R;
import io.rong.imkit.RongContext;
import io.rong.imkit.widget.provider.InputProvider.ExtendProvider;

public class PhotoCollectionsProvider extends ExtendProvider {

	private RongContext mContext;

	public PhotoCollectionsProvider(RongContext arg0) {
		super(arg0);
		this.mContext = arg0;
	}

	@Override
	public Drawable obtainPluginDrawable(Context context) {
		return context.getResources().getDrawable(R.drawable.rc_ic_picture);
	}

	@Override
	public CharSequence obtainPluginTitle(Context arg0) {
		return "图片";
	}

	@Override
	public void onPluginClick(View arg0) {
	//点击跳转至图片选择界面
		Intent intent=new Intent(mContext, MainActivity.class);
		
		startActivityForResult(intent, 110);

	}

}
