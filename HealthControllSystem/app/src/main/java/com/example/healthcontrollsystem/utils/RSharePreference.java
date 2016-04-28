package com.example.healthcontrollsystem.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class RSharePreference {
	
	public static SharedPreferences get(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences(AppConfig.SHAREPREFERENCE, Context.MODE_PRIVATE);
		return sharedPreferences;
	}

	public static void putFloat(String key,float value,Context context){
		get(context).edit().putFloat(key,value).commit();
	}

	public static float getFloat(String key,Context context){
		return get(context).getFloat(key,0);
	}
	
	public static void putInt(String key,int value,Context context){
		get(context).edit().putInt(key, value).commit();
	}
	
	public static int getInt(String key,Context context){
		return get(context).getInt(key, 0);
	}

	public static void putBoolean(String key,boolean value,Context context){
		get(context).edit().putBoolean(key, value).commit();
	}
	
	public static boolean getBoolean(String key,Context context){
		return get(context).getBoolean(key, false);
	}
	public static void putString(String key,String value,Context context){
		get(context).edit().putString(key, value).commit();
	}
	
	public static String getString(String key,Context context){
		return get(context).getString(key, "");
	}
}
