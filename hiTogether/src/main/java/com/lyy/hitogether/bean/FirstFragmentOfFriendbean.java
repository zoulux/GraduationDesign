package com.lyy.hitogether.bean;

public class FirstFragmentOfFriendbean {
	private int myFriendAvartar;
	private String myFriendName;
	private int praiseCount;
	private String yourDesc;
	private String sendTime;
	private int picCount;
	private String orderNo;
	private String collectionId;

	public int getMyFriendAvartar() {
		return myFriendAvartar;
	}

	public void setMyFriendAvartar(int myFriendAvartar) {
		this.myFriendAvartar = myFriendAvartar;
	}

	public String getMyFriendName() {
		return myFriendName;
	}

	public void setMyFriendName(String myFriendName) {
		this.myFriendName = myFriendName;
	}

	public int getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getYourDesc() {
		return yourDesc;
	}

	public void setYourDesc(String yourDesc) {
		this.yourDesc = yourDesc;
	}

	public int getPicCount() {
		return picCount;
	}

	public void setPicCount(int picCount) {
		this.picCount = picCount;
	}

	

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	

	public String getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}

	public FirstFragmentOfFriendbean(int myFriendAvartar, String myFriendName,
			int praiseCount, String sendTime, String yourDesc) {
		super();
		this.myFriendAvartar = myFriendAvartar;
		this.myFriendName = myFriendName;
		this.praiseCount = praiseCount;
		this.sendTime = sendTime;
		this.yourDesc = yourDesc;
	}

	public FirstFragmentOfFriendbean(String myFriendName, int praiseCount,
			String yourDesc, String sendTime, int picCount, String orderNo,String collectionId) {
		super();
		this.myFriendName = myFriendName;
		this.praiseCount = praiseCount;
		this.yourDesc = yourDesc;
		this.sendTime = sendTime;
		this.picCount = picCount;
		this.orderNo = orderNo;
		this.collectionId = collectionId;
	}
	
	public FirstFragmentOfFriendbean(String myFriendName, 
			String yourDesc, String sendTime, int picCount, String orderNo) {
		super();
		this.myFriendName = myFriendName;
		this.yourDesc = yourDesc;
		this.sendTime = sendTime;
		this.picCount = picCount;
		this.orderNo = orderNo;
	
	}

}
