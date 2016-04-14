package com.lyy.hitogether.share;

import android.content.Context;
import android.util.Log;
import cn.bmob.im.BmobUserManager;
import cn.sharesdk.socialization.SocializationCustomPlatform;

import com.lyy.hitogether.R;
import com.lyy.hitogether.bean.MyUser;

public class MyPlatform extends SocializationCustomPlatform {
	private Context mContext;

	public MyPlatform(Context context) {
		super(context);
		mContext = context;
	}

	@Override
	protected boolean checkAuthorize(int arg0) {

		return false;
	}

	@Override
	public int getLogo() {

		return R.drawable.icon;
	}

	@Override
	protected UserBrief doAuthorize() {

		Log.i("TAG", "doAuthorize1");
		UserBrief userBrief = new UserBrief();

		MyUser currentUser = BmobUserManager.getInstance(mContext)
				.getCurrentUser(MyUser.class);
		Log.i("TAG", "doAuthorize2");
		userBrief.userAvatar = currentUser.getAvatar();
		Log.i("TAG", "doAuthorize3");
		userBrief.userGender = UserGender.Male;
		// userBrief.userGender = CommonUtils.bmobGender2Mob(currentUser
		// .getGender());
		Log.i("TAG", "doAuthorize4");
		userBrief.userId = currentUser.getObjectId();
		Log.i("TAG", "doAuthorize5");
		userBrief.userNickname = currentUser.getNick();
		Log.i("TAG", "doAuthorize6");
		userBrief.userVerifyType = UserVerifyType.Verified;

		Log.i("TAG", "doAuthorize7");

		return userBrief;
	}

	@Override
	protected String getName() {

		return "乐友游";
	}

}
