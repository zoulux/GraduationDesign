package com.lyy.hitogether.view;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

import com.lyy.hitogether.R;


public class MyJourneyPopupWindow extends BasePopUpWindow implements
		OnClickListener {

	private Context context;
	private Button mClickToAlbum;
	private Button mClickToCamera;
	
	private onToAlbumClickListener mToAlbumClickListener;
	private onToCameraClickListener mToCameraClickListener;
	
	public interface onToAlbumClickListener{
		void onToAlbumClick(View v);
	}
	
	public interface onToCameraClickListener{
		void onToCameraClick(View v);
	}
	
	public void setOnToAlbumClickListener(onToAlbumClickListener mToAlbumClickListener){
		this.mToAlbumClickListener = mToAlbumClickListener;
	}
	
	public void setOnToCameraClickListener(onToCameraClickListener mToCameraClickListener){
		this.mToCameraClickListener = mToCameraClickListener;
	}

	public MyJourneyPopupWindow(Context context) {
		this.context = context;
		setLayout(context, R.layout.my_journey_set_pop_view, 0, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
	}

	@Override
	public void initId() {
		mClickToAlbum = (Button) mConvertView
				.findViewById(R.id.id_bt_pick_photo);
		mClickToCamera = (Button) mConvertView
				.findViewById(R.id.id_bt_pick_camera);
	}

	@Override
	public void initEvent() {
		mClickToAlbum.setOnClickListener(this);
		mClickToCamera.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.id_bt_pick_photo:
			if (mToAlbumClickListener!=null) {
				mToAlbumClickListener.onToAlbumClick(v);
			}
			break;

		case R.id.id_bt_pick_camera:
			if (mToCameraClickListener!=null) {
				mToCameraClickListener.onToCameraClick(v);
			}
			break;

		}
	}

}
