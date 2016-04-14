package com.lyy.hitogether.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class Trip extends BmobObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3829164492171967979L;
	Integer id;
	MyUser user;
	List<TripItem> data;

	public Trip(Trip trip) {

		this.user = trip.user;
		this.data = trip.data;

	}

	public Trip() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MyUser getUser() {
		return user;
	}

	public void setUser(MyUser user) {
		this.user = user;
	}

	public List<TripItem> getData() {
		return data;
	}

	public void setData(List<TripItem> data) {
		this.data = data;
	}

}
