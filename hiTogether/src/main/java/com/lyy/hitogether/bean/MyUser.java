package com.lyy.hitogether.bean;

import java.util.List;

import cn.bmob.im.bean.BmobChatUser;

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
	String picture;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MyUser myUser = (MyUser) o;

		if (gender != null ? !gender.equals(myUser.gender) : myUser.gender != null) return false;
		if (birthday != null ? !birthday.equals(myUser.birthday) : myUser.birthday != null)
			return false;
		if (label != null ? !label.equals(myUser.label) : myUser.label != null) return false;
		if (isAuthentication != null ? !isAuthentication.equals(myUser.isAuthentication) : myUser.isAuthentication != null)
			return false;
		if (identity != null ? !identity.equals(myUser.identity) : myUser.identity != null)
			return false;
		if (star != null ? !star.equals(myUser.star) : myUser.star != null) return false;
		if (collectionDemands != null ? !collectionDemands.equals(myUser.collectionDemands) : myUser.collectionDemands != null)
			return false;
		if (age != null ? !age.equals(myUser.age) : myUser.age != null) return false;
		if (token != null ? !token.equals(myUser.token) : myUser.token != null) return false;
		if (sdkVerSion != null ? !sdkVerSion.equals(myUser.sdkVerSion) : myUser.sdkVerSion != null)
			return false;
		if (model != null ? !model.equals(myUser.model) : myUser.model != null) return false;
		return brand != null ? brand.equals(myUser.brand) : myUser.brand == null;

	}

	@Override
	public int hashCode() {
		int result = gender != null ? gender.hashCode() : 0;
		result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
		result = 31 * result + (label != null ? label.hashCode() : 0);
		result = 31 * result + (isAuthentication != null ? isAuthentication.hashCode() : 0);
		result = 31 * result + (identity != null ? identity.hashCode() : 0);
		result = 31 * result + (star != null ? star.hashCode() : 0);
		result = 31 * result + (collectionDemands != null ? collectionDemands.hashCode() : 0);
		result = 31 * result + (age != null ? age.hashCode() : 0);
		result = 31 * result + (token != null ? token.hashCode() : 0);
		result = 31 * result + (sdkVerSion != null ? sdkVerSion.hashCode() : 0);
		result = 31 * result + (model != null ? model.hashCode() : 0);
		result = 31 * result + (brand != null ? brand.hashCode() : 0);
		return result;
	}
}
