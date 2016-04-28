package com.example.healthcontrollsystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.example.healthcontrollsystem.R;
import com.example.healthcontrollsystem.activity.AboutUsActivity;
import com.example.healthcontrollsystem.activity.DetailsActivity;
import com.example.healthcontrollsystem.activity.EditPersonalDetailsActivity;
import com.example.healthcontrollsystem.activity.HealthCanKaoActivity;
import com.example.healthcontrollsystem.activity.HealthTipsListActivity;
import com.example.healthcontrollsystem.activity.LoginActivity;
import com.example.healthcontrollsystem.utils.AppConfig;
import com.example.healthcontrollsystem.utils.RSharePreference;
import com.example.healthcontrollsystem.utils.ToastUtils;
import com.example.healthcontrollsystem.view.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PersonalFragment extends Fragment{

	View view;
	
	private BootstrapCircleThumbnail civ_personal_image;
	private TextView tv_personal_user_name;
	private LinearLayout ll_personal_my_details;
	private LinearLayout ll_personal_edit_personal;
	private LinearLayout ll_personal_health;
	private LinearLayout ll_personal_health_tips;
	private LinearLayout ll_personal_about_us;
	private BootstrapButton bt_personal_exit_login;
	
	private boolean isLogin;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_personal, null);
		initView();
		initMotion();
		return view;
	}
	
	private void initView(){
		civ_personal_image = (BootstrapCircleThumbnail) view.findViewById(R.id.civ_personal_image);
		tv_personal_user_name = (TextView) view.findViewById(R.id.tv_personal_user_name);
		ll_personal_about_us = (LinearLayout) view.findViewById(R.id.ll_personal_about_us);
		ll_personal_edit_personal = (LinearLayout) view.findViewById(R.id.ll_personal_edit_personal);
		ll_personal_health = (LinearLayout) view.findViewById(R.id.ll_personal_health);
		ll_personal_health_tips = (LinearLayout) view.findViewById(R.id.ll_personal_health_tips);
		ll_personal_my_details = (LinearLayout) view.findViewById(R.id.ll_personal_my_details);
		bt_personal_exit_login = (BootstrapButton) view.findViewById(R.id.bt_personal_exit_login);
	}
	
	private void initMotion(){
		if (RSharePreference.getString("user_image", getActivity())==null) {
			isLogin = true;
			ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.ic_bleed, civ_personal_image);
		}else {
			ImageLoader.getInstance().displayImage(RSharePreference.getString("user_image", getActivity()), civ_personal_image);
		}
		if (isLogin) {
			tv_personal_user_name.setText(RSharePreference.getString("user_name", getActivity()));
		}else {
			tv_personal_user_name.setText("请登录");
		}
		
		ll_personal_health_tips.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), HealthTipsListActivity.class);
				getActivity().startActivity(intent);
			}
		});
		
		ll_personal_about_us.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getActivity().startActivity(new Intent(getActivity(),AboutUsActivity.class));
			}
		});
		
		ll_personal_health.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getActivity().startActivity(new Intent(getActivity(), HealthCanKaoActivity.class));
			}
		});
		
		ll_personal_edit_personal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getActivity().startActivity(new Intent(getActivity(), EditPersonalDetailsActivity.class));
			}
		});
		
		ll_personal_my_details.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getActivity().startActivity(new Intent(getActivity(), DetailsActivity.class));
			}
		});
		
		bt_personal_exit_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (RSharePreference.getBoolean(AppConfig.LOGIN, getActivity())) {
					finish();
				}else {
					ToastUtils.showToast("您还没登陆", getActivity());
				}
			}
		});
	}
	
	
	private void finish() {
		// TODO Auto-generated method stub
		RSharePreference.putInt(AppConfig.USER_AGE, 0, getActivity());
		RSharePreference.putString(AppConfig.USER_IMAGE, null, getActivity());
		RSharePreference.putString(AppConfig.USER_NAME, null, getActivity());
		RSharePreference.putString(AppConfig.USER_PASSWORD, null, getActivity());
		RSharePreference.putString(AppConfig.USER_PHONE, null, getActivity());
		RSharePreference.putString(AppConfig.USER_SEX, null, getActivity());
		getActivity().startActivity(new Intent(getActivity(),LoginActivity.class));
		getActivity().finish();
	}
}
