package com.example.healthcontrollsystem.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.example.healthcontrollsystem.R;
import com.example.healthcontrollsystem.domain.User;
import com.example.healthcontrollsystem.utils.AppConfig;
import com.example.healthcontrollsystem.utils.RSharePreference;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DetailsActivity extends Activity {
	
	private ImageView head_back;

	private TextView head_save;

	private BootstrapCircleThumbnail bct_details_image;
	private TextView tv_edit_username;
	private TextView tv_edit_sex;
	private TextView tv_edit_age;
	private TextView tv_edit_phone;
	private TextView tv_edit_weight;
	private User user;
	private DbUtils dbUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit_personal_details);

		dbUtils = DbUtils.create(this);

		initView();
		initData();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		// TODO Auto-generated method stub
		bct_details_image = (BootstrapCircleThumbnail)findViewById(R.id.bct_details_image);
		tv_edit_age = (TextView)findViewById(R.id.tv_edit_age);
		tv_edit_phone = (TextView)findViewById(R.id.tv_edit_phone);
		tv_edit_sex = (TextView)findViewById(R.id.tv_edit_sex);
		tv_edit_username = (TextView)findViewById(R.id.tv_edit_username);
		tv_edit_weight = (TextView) findViewById(R.id.tv_edit_weight);
		head_back = (ImageView) findViewById(R.id.head_back);
		head_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	/**
	 * 初始化数据。数据的展示,不需要修改
	 */
	public void initData(){
		try {
			user = dbUtils.findById(User.class, RSharePreference.getInt(AppConfig.USER_ID,this));
		} catch (DbException e) {
			e.printStackTrace();
		}
		if (user!=null){
			ImageLoader.getInstance().displayImage(user.getUser_image(),bct_details_image);
			tv_edit_age.setText(user.getUser_age()+"");
			tv_edit_phone.setText(user.getUser_phone());
			tv_edit_sex.setText(user.getUser_sex());
			tv_edit_username.setText(user.getUser_name());
			tv_edit_weight.setText(user.getUser_weight()+"");
		}
	}
}
