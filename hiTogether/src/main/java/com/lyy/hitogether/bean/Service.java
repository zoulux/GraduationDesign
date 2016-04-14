package com.lyy.hitogether.bean;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.datatype.BmobDate;

public class Service implements Serializable{
	public static final String TAG="Service";
	/**
	 * 向导单
	 */

	String userId; // 用户Id
	MyUser user;

	String userName;// 用户名
	String serviceId; // 向导单号
	String destination; // 目的地
	BmobDate goTime; // 出行时间
	Double price; // 价格
	String introduction;// 介绍
	String summary; // 简介
	List<String> photoPath;// 协同照片
	String showImg; // 首页展示的图片
	String extra; // 额外字段

	public MyUser getUser() {
		return user;
	}

	public void setUser(MyUser user) {
		this.user = user;
	}

	@Deprecated
	public String getUserName() {
		return userName;
	}

	@Deprecated
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getShowImg() {
		return showImg;
	}

	public void setShowImg(String showImg) {
		this.showImg = showImg;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	@Deprecated
	public String getUserId() {
		return userId;
	}

	@Deprecated
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public BmobDate getGoTime() {
		return goTime;
	}

	public void setGoTime(BmobDate goTime) {
		this.goTime = goTime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<String> getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(List<String> photoPath) {
		this.photoPath = photoPath;
	}

	@Override
	public String toString() {
		return "Service [userId=" + userId + ", user=" + user + ", userName="
				+ userName + ", serviceId=" + serviceId + ", destination="
				+ destination + ", goTime=" + goTime + ", price=" + price
				+ ", introduction=" + introduction + ", summary=" + summary
				+ ", photoPath=" + photoPath + ", showImg=" + showImg
				+ ", extra=" + extra + "]";
	}

}
