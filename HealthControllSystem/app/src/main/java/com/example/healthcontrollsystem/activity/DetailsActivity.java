package com.example.healthcontrollsystem.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthcontrollsystem.R;

public class DetailsActivity extends Activity {
	
	private ImageView head_back;

	private TextView head_save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit_personal_details);
		
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
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
}
