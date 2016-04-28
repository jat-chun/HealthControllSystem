package com.example.healthcontrollsystem.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.healthcontrollsystem.R;
import com.example.healthcontrollsystem.adapter.HealthTipsAdapter;
import com.tandong.sa.activity.SmartActivity;

public class HealthTipsListActivity extends SmartActivity {

	private ImageView head_back;
	private TextView head_save;
	private List<String> tips_list;
	private ListView lv_tips_list;
	
	private HealthTipsAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_health_tips_list);
		
		initView();
		initData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		lv_tips_list = (ListView) findViewById(R.id.lv_tips_list);
		head_save = (TextView) findViewById(R.id.head_save);
		head_save.setText("");
		head_back = (ImageView) findViewById(R.id.head_back);
		head_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	private void initData(){
		tips_list = new ArrayList<String>();
		tips_list.add("血压高怎么办");
		tips_list.add("低血压怎么调节呢");
		tips_list.add("呼吸过快了，你需要调节了");
		tips_list.add("呼吸过慢，你需要加强锻炼了");
		tips_list.add("心率高是什么回事呢");
		tips_list.add("心率低怎么调理");
		adapter = new HealthTipsAdapter(HealthTipsListActivity.this, tips_list);
		lv_tips_list.setAdapter(adapter);
		lv_tips_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putInt("position", position);
				bundle.putString("title", tips_list.get(position));
				gotoActivity(TipsActivity.class, false, bundle);
			}
		});
	}
}
