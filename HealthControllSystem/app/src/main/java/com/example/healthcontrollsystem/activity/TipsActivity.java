package com.example.healthcontrollsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthcontrollsystem.R;
import com.tandong.sa.activity.SmartActivity;

public class TipsActivity extends SmartActivity {
	
	private ImageView head_back;
	private TextView head_save;
	
	private TextView tv_tips_name;
	private TextView tv_tips_content;
	private String title;
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tips);
		Intent intent = getIntent();
		position = intent.getIntExtra("position", 0);
		title = intent.getStringExtra("title");
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		head_back = (ImageView) findViewById(R.id.head_back);
		head_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		head_save = (TextView) findViewById(R.id.head_save);
		head_save.setText("");
		tv_tips_name = (TextView) findViewById(R.id.tv_tips_title);
		tv_tips_content = (TextView) findViewById(R.id.tv_tips_content);
		tv_tips_name.setText(title);
		if (position==0) {
			tv_tips_content.setText("	"+getResources().getString(R.string.high_xueya));
		}else if (position==1) {
			tv_tips_content.setText("	"+getResources().getString(R.string.low_xueya));
		}else if (position==2) {
			tv_tips_content.setText("	"+getResources().getString(R.string.fast_bleed));
		}else if (position==3) {
			tv_tips_content.setText("	"+getResources().getString(R.string.low_bleed));
		}else if (position==4) {
			tv_tips_content.setText("	"+getResources().getString(R.string.fast_xinlv));
		}else {
			tv_tips_content.setText("	"+getResources().getString(R.string.low_xinlv));
		}
	}
}
