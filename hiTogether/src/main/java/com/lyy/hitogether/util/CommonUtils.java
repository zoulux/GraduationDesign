package com.lyy.hitogether.util;

import com.lyy.hitogether.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sharesdk.socialization.SocializationCustomPlatform.UserGender;

public class CommonUtils {

	public static DisplayImageOptions imageOptions = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.pictures_no)
			.bitmapConfig(Bitmap.Config.ARGB_8888)
			.showImageForEmptyUri(R.drawable.pictures_no)
			.showImageOnFail(R.drawable.pictures_no).cacheInMemory(true)
			.cacheOnDisk(true).displayer(new FadeInBitmapDisplayer(2000))
			.build();

	public static UserGender bmobGender2Mob(String gender) {
		if (gender.equals("0")) {
			return UserGender.Female;
		} else if (gender.equals("1")) {
			return UserGender.Male;
		} else {

			return UserGender.Unknown;
		}
	}

	public static void setTextView(TextView tv, String setTxt, String defaultTxt) {
		if (TextUtils.isEmpty(setTxt)) {
			tv.setText(defaultTxt);
		} else {
			tv.setText(setTxt);
		}
	}

	public static void setImageView(ImageView iv, String setPic,
			String defaultPic) {
		if (TextUtils.isEmpty(setPic)) {
			ImageLoader.getInstance()
					.displayImage(defaultPic, iv, imageOptions);
		} else {
			ImageLoader.getInstance().displayImage(setPic, iv, imageOptions);
		}

	}
}
