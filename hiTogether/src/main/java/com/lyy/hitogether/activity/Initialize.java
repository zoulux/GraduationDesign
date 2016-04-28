package com.lyy.hitogether.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lyy.hitogether.R;
import com.lyy.hitogether.util.Config;

import cn.bmob.v3.Bmob;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.socialization.Socialization;

public class Initialize extends BaseActivity {

	@ViewInject(R.id.iv_le)
	ImageView ivLe;


	@ViewInject(R.id.iv_you1)
	ImageView ivYou1;


	@ViewInject(R.id.iv_you2)
	ImageView ivYou2;

	@ViewInject(R.id.iv_arrow)
	ImageView ivArrow;

	@ViewInject(R.id.ll_dy)
	LinearLayout llDy;

	private  long duration = 500;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_initialize);

		ViewUtils.inject(this);
		Bmob.initialize(this, Config.APP_KEY); // 初始化bmob
		ShareSDK.initSDK(this); // 初始化mob
		ShareSDK.registerService(Socialization.class);


		AnimatorSet animatorSet=new AnimatorSet();
		animatorSet.playTogether(move(ivArrow, 0),move(ivYou2, 120),move(ivYou1, 150),move(ivLe, duration * 2),alphaDy(llDy,duration*3));
		animatorSet.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				startActivity(new Intent(Initialize.this, LoginActivity.class));
				Initialize.this.finish();
			}
		});
		animatorSet.start();
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
	public AnimatorSet move(final View view, long delay) {
		ObjectAnimator moveX = ObjectAnimator
				.ofFloat(view, "translationX", 0, 300);
		ObjectAnimator moveY = ObjectAnimator
				.ofFloat(view, "translationY", 0, -160, 0, -80, 0, -50, 0);
		moveX.setInterpolator(new LinearInterpolator());

		moveY.setInterpolator(new AccelerateInterpolator());

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.setStartDelay(delay);
		animatorSet.setDuration(duration);
		animatorSet.play(moveX).with(moveY);
		moveY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				if (view.getVisibility() == View.INVISIBLE) {
					view.setVisibility(View.VISIBLE);
				}
			}
		});

		return animatorSet;
	}

	public ObjectAnimator alphaDy(final View v,long delay) {

		ObjectAnimator moveX = ObjectAnimator
				.ofFloat(v, "alpha", 0, 1.0f);
		moveX.setDuration(duration);
		moveX.setStartDelay(delay);
		moveX.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				v.setVisibility(View.VISIBLE);
			}
		});
		return moveX;

	}
}
