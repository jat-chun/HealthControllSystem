package com.example.healthcontrollsystem.commemt;

import android.app.Application;

import com.example.healthcontrollsystem.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MyApplication extends Application{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		//初始化imageloader
		initImageLoader();
	}
	/**
	 * 初始化imageloader
	 */
	private void initImageLoader(){
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.mipmap.ic_xinlv)
		.showImageOnFail(R.mipmap.ic_xinlv).cacheInMemory(true)
		.cacheOnDisc(true).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
		.defaultDisplayImageOptions(defaultOptions)
		.discCacheSize(50 * 1024 * 1024)//
		.discCacheFileCount(100)// 缓存一百张图片
		.writeDebugLogs().build();
		ImageLoader.getInstance().init(config);
	}
}
