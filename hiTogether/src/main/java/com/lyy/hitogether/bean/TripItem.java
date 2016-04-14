package com.lyy.hitogether.bean;

public class TripItem {
	Integer id;
	String photoPath;
	String photoDescribe;

	@Override
	public String toString() {
		return "TripItem [photoPath=" + photoPath + ", photoDescribe="
				+ photoDescribe + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getPhotoDescribe() {
		return photoDescribe;
	}

	public void setPhotoDescribe(String photoDescribe) {
		this.photoDescribe = photoDescribe;
	}

}
