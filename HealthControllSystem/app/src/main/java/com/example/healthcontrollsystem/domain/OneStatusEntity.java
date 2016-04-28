package com.example.healthcontrollsystem.domain;

import java.util.List;

/**
 * 一级状态实体类
 * @author 三人行技术开发团队
 */
public class OneStatusEntity {
	/* 状态名称 */
	private String statusName;
	/* 预计完成时间 */
	private String completeTime;
	/* 二级状态list */
	private List<Record> twoList;
	
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	public List<Record> getTwoList() {
		return twoList;
	}
	public void setTwoList(List<Record> twoList) {
		this.twoList = twoList;
	}
	
	
}
