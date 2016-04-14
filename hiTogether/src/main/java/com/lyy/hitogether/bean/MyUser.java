package com.lyy.hitogether.bean;

import java.util.List;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.datatype.BmobDate;

public class MyUser extends BmobChatUser {
	String gender; // 0 女 1 男 2UnKnow
	String birthday;
	List<String> label;
	Boolean isAuthentication; // 是否实名认证
	String identity; // 身份证号
	Float star; // 用户评星
	List<String> collectionDemands; // 收藏单号
	Integer age;
	String token;

	Integer sdkVerSion; // sdk版本
	String model;
	String brand;

	public Integer getSdkVerSion() {
		return sdkVerSion;
	}

	public void setSdkVerSion(Integer sdkVerSion) {
		this.sdkVerSion = sdkVerSion;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public List<String> getCollectionDemands() {
		return collectionDemands;
	}

	public void setCollectionDemands(List<String> collectionDemands) {
		this.collectionDemands = collectionDemands;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public List<String> getLabel() {
		return label;
	}

	public void setLabel(List<String> label) {
		this.label = label;
	}

	public Boolean getIsAuthentication() {
		return isAuthentication;
	}

	public void setIsAuthentication(Boolean isAuthentication) {
		this.isAuthentication = isAuthentication;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Float getStar() {
		return star;
	}

	public void setStar(Float star) {
		this.star = star;

	}

	@Override
	public String toString() {
		return "MyUser [gender=" + gender + ", birthday=" + birthday
				+ ", isAuthentication=" + isAuthentication + "]";
	}

}
