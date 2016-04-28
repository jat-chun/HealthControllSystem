package com.example.healthcontrollsystem.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtils {

	/**
	 * 返回完整时间
	 * @param date
	 * @return
	 */
	public static String allDateFormat(long date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd mm:ss");
		String dateString = sdf.format(new Date(date));
		return dateString;
	}

	/**
	 * 返回完整时间
	 * @param date
	 * @return
	 */
	public static String dateFormat(long date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String dateString = sdf.format(new Date(date));
		return dateString;
	}

	
	/**
	 * 返回月份
	 * @param date
	 * @return
	 */
	public static String monthFormat(long date){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd mm:ss");
		String dateString = sdf.format(new Date(date));
		return dateString;
	}
	
	/**
	 * 返回钟点数
	 * @param date
	 * @return
	 */
	public static String clockFormat(long date){
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
		String dateString = sdf.format(new Date(date));
		return dateString;
	}

	/**
	 * 返回星期几
	 * @return
     */
	public static String weekFormat(){
		Calendar calendar = Calendar.getInstance();
		String mWay;
		mWay = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
		if("1".equals(mWay)){
			mWay ="天";
		}else if("2".equals(mWay)){
			mWay ="一";
		}else if("3".equals(mWay)){
			mWay ="二";
		}else if("4".equals(mWay)){
			mWay ="三";
		}else if("5".equals(mWay)){
			mWay ="四";
		}else if("6".equals(mWay)){
			mWay ="五";
		}else if("7".equals(mWay)){
			mWay ="六";
		}
		return "星期"+mWay;
	}
	
	
}
