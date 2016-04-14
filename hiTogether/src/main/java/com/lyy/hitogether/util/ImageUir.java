package com.lyy.hitogether.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;

public class ImageUir {
	private String journeySetPicPath;
	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");// 获取当前时间，进一步转化为字符串

	public File getNewFile() {
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/Hitogether/journeySet/" + format.format(new Date())
				+ ".jpg");
		journeySetPicPath = file.getAbsolutePath();
		return file;
	}

	public String getNewPath() {
		return journeySetPicPath;
	}

}
