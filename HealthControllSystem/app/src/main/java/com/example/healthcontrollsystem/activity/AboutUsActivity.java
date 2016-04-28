package com.example.healthcontrollsystem.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.healthcontrollsystem.R;
import com.tandong.sa.activity.SmartActivity;

public class AboutUsActivity extends SmartActivity {
	
	private TextView head_save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutus);
		
		head_save = (TextView) findViewById(R.id.head_save);
		head_save.setText("");
	}
}
