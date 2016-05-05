package com.example.healthcontrollsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.healthcontrollsystem.R;
import com.example.healthcontrollsystem.adapter.HealthAdapter;
import com.example.healthcontrollsystem.domain.Health;
import com.example.healthcontrollsystem.utils.DateFormatUtils;
import com.tandong.sa.activity.SmartActivity;

public class HealthActivity extends SmartActivity {

	private TextView tv_health_time;
	private ListView lv_health_listview;
	Health enity;
	HealthAdapter adapter;
	private ImageView head_back;
	private TextView head_save;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_health);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("bean");
		enity = (Health) bundle.getSerializable("bean");

		initView();
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		head_save = (TextView) findViewById(R.id.head_save);
		head_save.setText("");
		tv_health_time = (TextView) findViewById(R.id.tv_health_time);
		lv_health_listview = (ListView) findViewById(R.id.lv_health_listview);
		
		tv_health_time.setText(DateFormatUtils.allDateFormat(enity.getDate()));
		
		adapter = new HealthAdapter(HealthActivity.this, enity);
		lv_health_listview.setAdapter(adapter);
		
		head_back  =(ImageView) findViewById(R.id.head_back);
		head_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
