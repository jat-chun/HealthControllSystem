package com.example.healthcontrollsystem.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

public class BlueToothUtils {

	private static BlueToothUtils instance;
	private Context context;
	private static BluetoothAdapter adapter;
	
	public BlueToothUtils(Context context){
		this.context = context.getApplicationContext();
	}
	
	public  static BlueToothUtils getInstance(Context context){
		if (instance == null) {
			instance = new BlueToothUtils(context);
		}
		return instance;
	}
	
	public static BluetoothAdapter openBluetooth(){
		//获取系统蓝牙默认适配器
		adapter = BluetoothAdapter.getDefaultAdapter();
		if (adapter.isEnabled()) {
			//强制打开蓝牙
			adapter.enable();
			adapter.cancelDiscovery();
		}
		
		return adapter;
	}
	
	
}
