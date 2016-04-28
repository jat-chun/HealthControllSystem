package com.example.healthcontrollsystem.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.example.healthcontrollsystem.R;
import com.example.healthcontrollsystem.utils.AppConfig;
import com.example.healthcontrollsystem.utils.DateFormatUtils;
import com.example.healthcontrollsystem.utils.RSharePreference;
import com.tandong.sa.activity.SmartActivity;
/**
 * 引导页
 * @author jat
 *
 */
public class WelcomeActivity extends SmartActivity {

	private ViewPager vp_flipper ;
	private int guides[] = {R.mipmap.yindao_one, R.mipmap.yindao_two, R.mipmap.yindao_three};

	private List<View> listView;
	
	private LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (RSharePreference.getBoolean(AppConfig.ISFIRST, this)) {
			gotoActivity(LoginActivity.class, true);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		vp_flipper = (ViewPager) findViewById(R.id.vp_flipper);
		//把当前日期记录下来,目的为提交数据库
		RSharePreference.putString(AppConfig.DATE, DateFormatUtils.dateFormat(System.currentTimeMillis()),this);
		initMenu();
	}

	@SuppressLint("NewApi")
	public void initMenu(){
		listView = new ArrayList<View>();
		inflater = LayoutInflater.from(this);
		for (int i = 0; i < guides.length; i++) {
			View view = inflater.inflate(R.layout.pager_guide, null);
			
			BitmapFactory.Options opt = new BitmapFactory.Options();

			opt.inPreferredConfig = Bitmap.Config.RGB_565;

			opt.inPurgeable = true;

			opt.inInputShareable = true;

			InputStream is = getResources().openRawResource(

					guides[i]);

			Bitmap bm = BitmapFactory.decodeStream(is, null, opt);

			BitmapDrawable bd = new BitmapDrawable(getResources(), bm);
			
			view.setBackground(bd);
			
			if (i==guides.length-1) {
				Button bt_confirm = (Button) view.findViewById(R.id.bt_confirm);
				bt_confirm.setVisibility(View.VISIBLE);
				bt_confirm.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
						RSharePreference.putBoolean(AppConfig.ISFIRST, true, WelcomeActivity.this);
						finish();
					}
				});
			}
			
			listView.add(view);
		}
		
		MyViewPagerAdapter adapter = new MyViewPagerAdapter(listView);
		vp_flipper.setAdapter(adapter);
		vp_flipper.setOnPageChangeListener(adapter);
		vp_flipper.setCurrentItem(0);
	}

	/**
	 * 内部类适配器
	 * @author jat
	 *
	 */
	class MyViewPagerAdapter extends  PagerAdapter implements OnPageChangeListener{

		private List<View> listView;

		public MyViewPagerAdapter(List<View> listView) {
			// TODO Auto-generated constructor stub
			this.listView = listView;;
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listView.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			container.addView(listView.get(position));
			return listView.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView(listView.get(position));
			//			super.destroyItem(container, position, object);
		}

	}

}
