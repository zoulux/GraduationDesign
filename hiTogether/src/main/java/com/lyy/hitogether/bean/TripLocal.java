package com.lyy.hitogether.bean;

public class TripLocal {
	public int beanType;
	public String picPath;
	public String txt;

	public TripLocal() {
	}

	public TripLocal(int beanType, String picPath, String txt) {

		this.beanType = beanType;
		this.picPath = picPath;
		this.txt = txt;
	}

	@Override
	public String toString() {
		return "MyJourneySetBean [beanType=" + beanType + ", picPath="
				+ picPath + ", txt=" + txt + "]";
	}

	public static final int DETAIL = 0;
	public static final int BUTTON = 1;
	public static final int BEAN_TYPE_COUNT = 2;

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String path) {
		this.picPath = path;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public int getBeanType() {
		return beanType;
	}

	public void setBeanType(int beanType) {
		this.beanType = beanType;
	}

}
