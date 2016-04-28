package com.example.healthcontrollsystem.domain;

import android.R.integer;

import com.lidroid.xutils.db.annotation.Id;

/**
 * �û�ʵ����
 * @author jat
 *
 */
public class User {

	@Id
	private int id;
	private String user_name;
	private String user_password;
	private String user_image;
	private int user_age;
	private String user_phone;
	private String user_sex;
	private double user_weight;

	public User(int id, String user_name, String user_password, String user_image, int user_age, String user_phone, String user_sex,
				double user_weight) {
		this.id = id;
		this.user_name = user_name;
		this.user_password = user_password;
		this.user_image = user_image;
		this.user_age = user_age;
		this.user_phone = user_phone;
		this.user_sex = user_sex;
		this.user_weight = user_weight;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_image() {
		return user_image;
	}
	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}
	public int getUser_age() {
		return user_age;
	}
	public void setUser_age(int user_age) {
		this.user_age = user_age;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getUser_sex() {
		return user_sex;
	}
	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}

	public double getUser_weight() {
		return user_weight;
	}

	public void setUser_weight(double user_weight) {
		this.user_weight = user_weight;
	}


	@Override
	public String toString() {
		return "User [user_name=" + user_name + ", user_password="
				+ user_password + ", user_image=" + user_image + ", user_age="
				+ user_age + ", user_phone=" + user_phone + ", user_sex="
				+ user_sex + "]";
	}
	
}
