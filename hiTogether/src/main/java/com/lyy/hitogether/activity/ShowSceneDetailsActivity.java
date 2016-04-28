package com.lyy.hitogether.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lyy.hitogether.R;
import com.lyy.hitogether.bean.HotScenic;
import com.lyy.hitogether.share.MyPlatform;
import com.lyy.hitogether.view.CustomTitleBarView;
import com.lyy.hitogether.view.CustomTitleBarView.onLeftBarViewClickListener;
import com.lyy.hitogether.view.MyGuideOrTravalersLayout;
import com.lyy.hitogether.view.MyGuideOrTravalersLayout.onGuideItemClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Date;

import cn.bmob.im.BmobUserManager;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.socialization.QuickCommentBar;
import cn.sharesdk.socialization.Socialization;
import cn.sharesdk.socialization.SocializationCustomPlatform;

public class ShowSceneDetailsActivity extends BaseActivity implements
		OnClickListener {

	private MyGuideOrTravalersLayout mLayoutGuides;
	private MyGuideOrTravalersLayout mLayoutTravalers;
	// private TextView guidesMore;
	// private TextView travalersMore;

	private QuickCommentBar mQuickCommentBar;
	private OnekeyShare oks;
	private HotScenic mcurrentScenic;
	private Socialization socialization;
	private String topicId;

	private int platId;

	@ViewInject(R.id.id_tv_hot_scene_introduce)
	private TextView mTvIntroduce;

	@ViewInject(R.id.id_show_scen_detail_title)
	private CustomTitleBarView bar;

	@ViewInject(R.id.id_scene_detail_img)
	private ImageView detailImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_scene_details);
		ViewUtils.inject(this);
		initDates();
		initOnekeyShare();
		initView();
		initEvent();

	}

	private void initDates() {
		mcurrentScenic = (HotScenic) getIntent().getSerializableExtra(
				HotScenic.TAG);

		ImageLoader.getInstance().displayImage(mcurrentScenic.getPhotoPath(),
				detailImg, baseOptions);

		mTvIntroduce.setText(mcurrentScenic.getIntroduce());

	}

	private void initOnekeyShare() {
	
		
		socialization = ShareSDK.getService(Socialization.class);
		SocializationCustomPlatform platform = new MyPlatform(
				ShowSceneDetailsActivity.this);
		platId = ShareSDK.platformNameToId("lyy");

		socialization.setCustomPlatform(platform);

		oks = new OnekeyShare();
		oks.disableSSOWhenAuthorize();
		

		//
		bar.setCenterText(mcurrentScenic.getHotName());
		bar.setLeftImageSuorce(R.drawable.back_indicator);
		bar.setLeftImagePaddingLeft(10);
		bar.setCenterTextColor(Color.WHITE);
		bar.setCenterTextSize(18);
		oks.setTitle(" 一起去" + mcurrentScenic.getHotName() + "吧！那就赶快下载这个碉堡的App");
		oks.setTitleUrl("http://www.pgyer.com/hitogether");
		oks.setText("带TA去旅行，乐友游...");
		oks.setImageUrl(mcurrentScenic.getPhotoPath());
		oks.setUrl("http://www.pgyer.com/hitogether");
		oks.setComment("我是乐友游的超级内容部分");

		oks.setSite("乐友游");
		oks.setSiteUrl("http://www.pgyer.com/hitogether");
		
		

	}

	private void initView() {
		mQuickCommentBar = (QuickCommentBar) findViewById(R.id.id_qcb_reply);
		mLayoutTravalers = (MyGuideOrTravalersLayout) findViewById(R.id.id_travaler_layout);
		mLayoutGuides = (MyGuideOrTravalersLayout) findViewById(R.id.id_guide_layout);
		// guidesMore = (TextView) findViewById(R.id.id_guids_more);
		// travalersMore = (TextView) findViewById(R.id.id_travalers_more);

		mQuickCommentBar.setTopic(mcurrentScenic.getHotId(),
				mcurrentScenic.getHotName(), new Date().toString(),
				BmobUserManager.getInstance(ShowSceneDetailsActivity.this)
						.getCurrentUser().getNick());
		mQuickCommentBar.getBackButton().setVisibility(View.GONE);

		mQuickCommentBar.setOnekeyShare(oks);

	}

	private void initEvent() {
		// travalersMore.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// showToast("travalersMore");
		//
		// }
		// });
		//
		// guidesMore.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// showToast("guidesMore");
		//
		// }
		// });
		mLayoutGuides.addMyView(10);
		mLayoutTravalers.addMyView(12);
		mLayoutGuides
				.setOnGuideItemClickListener(new onGuideItemClickListener() {

					@Override
					public void onItemClick(View v, int pos) {
						showToast(pos + "");
					}
				});
		mLayoutTravalers
				.setOnGuideItemClickListener(new onGuideItemClickListener() {

					@Override
					public void onItemClick(View v, int pos) {
						showToast(pos + "");
					}
				});
		bar.setOnLeftBarViewClickListener(new onLeftBarViewClickListener() {
			
			@Override
			public void onclick(View v) {
					ShowSceneDetailsActivity.this.finish();
			}
		});

	}

	@Override
	public void onClick(View v) {

	}

}
