package com.example.healthcontrollsystem.domain;

import com.lidroid.xutils.db.annotation.Id;

import java.sql.Date;

public class Record {
	
	@Id
	private int id;
	private int user_id;
	private int step;
	private double kaluli;
	private double distance;
	private String date;
	private String week;
	private Date time;

	public Record(int id, int step, double kaluli, double distance, String date, String week) {
		this.id = id;
		this.step = step;
		this.kaluli = kaluli;
		this.distance = distance;
		this.date = date;
		this.week = week;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public double getKaluli() {
		return kaluli;
	}

	public void setKaluli(double kaluli) {
		this.kaluli = kaluli;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Record{" +
				"id=" + id +
				", user_id=" + user_id +
				", step=" + step +
				", kaluli=" + kaluli +
				", distance=" + distance +
				", date='" + date + '\'' +
				", week='" + week + '\'' +
				", time=" + time +
				'}';
	}
}
