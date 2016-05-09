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
		.showImageForEmptyUri(R.mipmap.icon)//设置图片为空时的默认图片
		.showImageOnFail(R.mipmap.icon).cacheInMemory(true)//设置加载失败时默认图片
		.cacheOnDisc(true).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
		.defaultDisplayImageOptions(defaultOptions)
		.discCacheSize(50 * 1024 * 1024)//缓存空间的大小
		.discCacheFileCount(100)// 缓存一百张图片
		.writeDebugLogs().build();
		//初始化设置
		ImageLoader.getInstance().init(config);
	}
}
