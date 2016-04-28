package com.example.healthcontrollsystem.domain;

import java.io.Serializable;
/**
 * 健康类实体
 * @author jat
 *
 */
public class Health implements Serializable{
	
	private int xinlv;
	private int bleed;
	private int max_xueya;
	private int min_xueya;
	private long date;
	public int getXinlv() {
		return xinlv;
	}
	public void setXinlv(int xinlv) {
		this.xinlv = xinlv;
	}
	public int getBleed() {
		return bleed;
	}
	public void setBleed(int bleed) {
		this.bleed = bleed;
	}
	public int getMax_xueya() {
		return max_xueya;
	}
	public void setMax_xueya(int max_xueya) {
		this.max_xueya = max_xueya;
	}
	public int getMin_xueya() {
		return min_xueya;
	}
	public void setMin_xueya(int min_xueya) {
		this.min_xueya = min_xueya;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	
}
