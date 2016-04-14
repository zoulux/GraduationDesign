package com.lyy.hitogether.bean;

import java.io.Serializable;

public class HotScenic implements Serializable{
	public static final String TAG="HotScenic";
	/**
	 * 热门景点
	 */
	String hotId;// id
	String hotName;// 名字
	String photoPath;// 图片路径
	String extra; // 额外的
	String enjoy; // 正在参加的人
	String introduce;//景点介绍
	
	

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getEnjoy() {
		return enjoy;
	}

	public void setEnjoy(String enjoy) {
		this.enjoy = enjoy;
	}

	public String getHotId() {
		return hotId;
	}

	public void setHotId(String hotId) {
		this.hotId = hotId;
	}

	public String getHotName() {
		return hotName;
	}

	public void setHotName(String hotName) {
		this.hotName = hotName;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	@Override
	public String toString() {
		return "HotScenic [hotId=" + hotId + ", hotName=" + hotName
				+ ", photoPath=" + photoPath + ", extra=" + extra + ", enjoy="
				+ enjoy + "]";
	}

	
}
