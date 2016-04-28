package com.example.healthcontrollsystem.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.healthcontrollsystem.R;
import com.example.healthcontrollsystem.fragment.DataCenterFragment;
import com.example.healthcontrollsystem.fragment.DateStateFragment;
import com.example.healthcontrollsystem.fragment.PersonalFragment;
import com.example.healthcontrollsystem.utils.AppConfig;
import com.example.healthcontrollsystem.utils.OkHttpUtil;
import com.example.healthcontrollsystem.utils.RSharePreference;
import com.example.healthcontrollsystem.utils.ToastUtils;
import com.example.healthcontrollsystem.view.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tandong.sa.activity.SmartFragmentActivity;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 * @author jat
 *
 */
public class HomesActivity extends SmartFragmentActivity implements OnClickListener{

	private RadioGroup rg_groups;
	private ViewPager vp_home_page_content;
	//当前页码
	private int positions;
	
	private CircularImageView civ_homes_image;
	
	private Context context = HomesActivity.this;

	private TextView tv_login_register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_homes);

		init();
		refreshData();
	}

	private void refreshData() {
		// TODO Auto-generated method stub
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		String result = null;
		params.add(new BasicNameValuePair("appid", AppConfig.APP_ID+""));
		params.add(new BasicNameValuePair("third_appid", AppConfig.THIRD_APPID));
		params.add(new BasicNameValuePair("third_appsecret", AppConfig.THIRD_APPSECRET));
		params.add(new BasicNameValuePair("mac_key", RSharePreference.getString(AppConfig.MAC_KEY, HomesActivity.this)));
		params.add(new BasicNameValuePair(AppConfig.CALL_ID, RSharePreference.getString(AppConfig.CALL_ID, HomesActivity.this)));
		params.add(new BasicNameValuePair(AppConfig.ACCESS_TOKEN, RSharePreference.getString(AppConfig.ACCESS_TOKEN, HomesActivity.this)));
//		params.add(new BasicNameValuePair(name, value))
		params.add(new BasicNameValuePair("v", "1.0"));
		params.add(new BasicNameValuePair("l", "english"));
		try {
			result = OkHttpUtil.getStringFromServerWithParams(AppConfig.XIAOMI_URL, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 初始化
	 */
	private void init(){
		rg_groups = (RadioGroup) findViewById(R.id.rg_groups);
		tv_login_register = (TextView) findViewById(R.id.tv_home_head_login_register);
		if (RSharePreference.getBoolean(AppConfig.LOGIN, context)) {
			tv_login_register.setText("jat");
			//tv_login_register.setText(RSharePreference.getString(AppConfig.USER_NAME, context));
		}
		//下划线
		tv_login_register.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
		//设置登录监听
		tv_login_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				boolean isLogin = RSharePreference.getBoolean(AppConfig.LOGIN, HomesActivity.this);
				if (isLogin) {
					Intent intent = new Intent(HomesActivity.this, DetailsActivity.class);
					context.startActivity(intent);
				}else {
					Intent intent = new Intent(context,LoginActivity.class);
					context.startActivity(intent);
				}
			}
		});
		civ_homes_image = (CircularImageView) findViewById(R.id.civ_homes_image);
		if (RSharePreference.getString("user_image", this)==null) {
			ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.ic_bleed, civ_homes_image);
		}else {
			ImageLoader.getInstance().displayImage(RSharePreference.getString("user_image", this), civ_homes_image);
		}
		
		vp_home_page_content = (ViewPager) findViewById(R.id.vp_home_page_content);

		vp_home_page_content.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
		//预加载两项
		vp_home_page_content.setOffscreenPageLimit(2);
		vp_home_page_content.setOnPageChangeListener(new MyOnPageChangeListener());
		rg_groups.setOnCheckedChangeListener(new MyOnCheckChangeListener());
		vp_home_page_content.setCurrentItem(0);
		rg_groups.check(R.id.rb_bluetooth);
	}

	/**
	 * fragment适配器
	 * @author jat
	 *
	 */
	private class MyFragmentAdapter extends FragmentPagerAdapter{

		private List<Fragment> fragmentPage = new ArrayList<Fragment>();

		public MyFragmentAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
			//填充fragment
			fragmentPage.add(new DateStateFragment());
			fragmentPage.add(new DataCenterFragment());
			fragmentPage.add(new PersonalFragment());

		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return fragmentPage.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragmentPage.size();
		}

	}

	private class MyOnPageChangeListener implements OnPageChangeListener{

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
			int id = R.id.rb_bluetooth;

			switch (arg0) {
			case 0:
				id = R.id.rb_bluetooth;
				break;
			case 1:
				id = R.id.rb_data_center;
				break;
			case 2:
				id = R.id.rb_personal;
				break;

			default:
				break;
			}
			positions = arg0;
			rg_groups.check(id);
		}

	}

	private class MyOnCheckChangeListener implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {
			// TODO Auto-generated method stub
			switch (arg1) {
			case R.id.rb_bluetooth:
				positions = 0;
				vp_home_page_content.setCurrentItem(0);
				break;
			case R.id.rb_data_center:
				positions = 1;
				vp_home_page_content.setCurrentItem(1);
				break;
			case R.id.rb_personal:
				positions = 2;
				vp_home_page_content.setCurrentItem(2);
				break;
			default:
				break;
			}
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}
	
	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				ToastUtils.showToast("再按一次退出~", this);
				exitTime = System.currentTimeMillis();
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
