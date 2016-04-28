package com.example.healthcontrollsystem.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * tips实体类
 * @author jat
 *
 */
public class Tips implements Parcelable{
	
	private String tips_image;
	
	private String tips_name;
	
	private String tips_content;

	public String getTips_image() {
		return tips_image;
	}

	public void setTips_image(String tips_image) {
		this.tips_image = tips_image;
	}

	public String getTips_name() {
		return tips_name;
	}

	public void setTips_name(String tips_name) {
		this.tips_name = tips_name;
	}

	public String getTips_content() {
		return tips_content;
	}

	public void setTips_content(String tips_content) {
		this.tips_content = tips_content;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	

}
