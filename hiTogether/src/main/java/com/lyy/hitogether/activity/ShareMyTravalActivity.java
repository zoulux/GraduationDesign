package com.lyy.hitogether.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

import com.lyy.hitogether.R;
import com.lyy.hitogether.adapter.ShareMyaTravalAdapter;
import com.lyy.hitogether.util.DensityUtils;
import com.lyy.hitogether.view.CustomTitleBarView;
import com.lyy.hitogether.view.CustomTitleBarView.onLeftBarViewClickListener;
import com.lyy.hitogether.view.CustomTitleBarView.onRightBarViewClickListener;

import java.util.ArrayList;
import java.util.List;

public class ShareMyTravalActivity extends BaseActivity {
	// ���������
	private EditText shareText;
	private GridView mGridView;
	// ���ڱ����������
	private ShareMyaTravalAdapter adapter;

	private CustomTitleBarView customTitleBarView;

	List<String> getPicPath = new ArrayList<String>();

	private final int MY_ALBUM = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_my_traval);
		init();
	}

	/**
	 * ��ʼ��
	 */
	private void init() {
		initView();
		initGridView();
		initEvent();

	}

	/**
	 * ��ʼ��GridView
	 */
	private void initGridView() {
		getPicPath.add("");
		adapter = new ShareMyaTravalAdapter(this, getPicPath);
		mGridView = (GridView) findViewById(R.id.id_share_my_traval_gridview);
		mGridView.setAdapter(adapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == mGridView.getCount() - 1) {
					Intent intent = new Intent(ShareMyTravalActivity.this,
							MyAlbumActivity.class);
					startActivityForResult(intent, MY_ALBUM);
				}

			}
		});
	}

	// ѡ���ͼƬ������
	int picSize = 0;

	/**
	 * ѡ��ͼƬ�󷵻ص���ݼ���ѡ���ͼƬ
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case MY_ALBUM:

			getPicPath.clear();
			getPicPath = data.getStringArrayListExtra("path");
			getPicPath.add("");
			picSize = getPicPath.size();
			int Hposition = DensityUtils.dp2px(100)
					* picSize / 3;
			adapter = new ShareMyaTravalAdapter(ShareMyTravalActivity.this,
					getPicPath);
			mGridView.setAdapter(adapter);
			mGridView.smoothScrollToPosition(Hposition);
			break;

		default:
			break;
		}

		super.onActivityResult(requestCode, resultCode, data);

	}

	private void initView() {
		customTitleBarView = (CustomTitleBarView) findViewById(R.id.id_share_my_traval_CustomTitleBarView);

		// topBarLeft = (TopbarBtView) findViewById(R.id.id_topBarLeft);
		// topBarLeft.setVisibility(View.VISIBLE);
		// topBarLeft.setTopbarImageDrawable(R.drawable.back_indicator);
		// topBarLeft.setGravity(Gravity.CENTER_VERTICAL);
		//
		// topBarRight = (TopbarBtView) findViewById(R.id.id_topBarRight);
		// topBarRight.setVisibility(View.VISIBLE);
		// topBarRight.setTopbarImageDrawable(R.drawable.ic_menu_share_holo_dark);
		// topBarRight.setGravity(Gravity.CENTER_VERTICAL);

	}

	/**
	 * ��ʼ�������¼�
	 */
	private void initEvent() {
		customTitleBarView
				.setOnLeftBarViewClickListener(new onLeftBarViewClickListener() {

					@Override
					public void onclick(View v) {
						ShareMyTravalActivity.this.finish();

					}
				});

		customTitleBarView
				.setOnRightBarViewClickListener(new onRightBarViewClickListener() {

					@Override
					public void onclick(View v) {
						showToast("分享");

					}
				});
	}

}
