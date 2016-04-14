package com.lyy.hitogether.bean;

public class Group {
	/**
	 * 群组
	 */
	String groupId;// 群id
	String groupName;// 群名
	String maxCount; // 最大人数
	String currentCount;// 当前人数
	String introduction;// 介绍
	String summary; // 简介
	String groupImg;// 群头像
	String recentMsg;// 最后一条消息

	public String getRecentMsg() {
		return recentMsg;
	}

	public void setRecentMsg(String recentMsg) {
		this.recentMsg = recentMsg;
	}

	public String getGroupImg() {
		return groupImg;
	}

	public void setGroupImg(String groupImg) {
		this.groupImg = groupImg;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(String maxCount) {
		this.maxCount = maxCount;
	}

	public String getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(String currentCount) {
		this.currentCount = currentCount;
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

}
