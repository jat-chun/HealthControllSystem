package com.example.healthcontrollsystem.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.healthcontrollsystem.R;
import com.example.healthcontrollsystem.adapter.CanKaoAdapter;
import com.tandong.sa.activity.SmartActivity;

public class HealthCanKaoActivity extends SmartActivity {

	private TextView head_save;
	private ListView lv_cankao_list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cankao);
		head_save = (TextView) findViewById(R.id.head_save);
		lv_cankao_list = (ListView) findViewById(R.id.lv_cankao_list);
		lv_cankao_list.setAdapter(new CanKaoAdapter(this));
	}
	
}
