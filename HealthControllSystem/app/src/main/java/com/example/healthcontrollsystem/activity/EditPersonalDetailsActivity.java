package com.example.healthcontrollsystem.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.healthcontrollsystem.R;
import com.example.healthcontrollsystem.domain.User;
import com.example.healthcontrollsystem.utils.ToastUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tandong.sa.activity.SmartActivity;

import org.w3c.dom.Text;

public class EditPersonalDetailsActivity extends SmartActivity {

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}
	};
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
	AlertDialog dialog;
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

	public void initMotion(){
		if (user!=null){
			tv_edit_username.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showTextDialog(0,user.getUser_name());
				}
			});
			tv_edit_phone.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showTextDialog(1,user.getUser_phone());
				}
			});
			tv_edit_age.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showTextDialog(2,user.getUser_age()+"");
				}
			});
		}

	}

	public void initData(){
		try {
			user = dbUtils.findFirst(User.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		if (user!=null){
			tv_edit_weight.setText(user.getUser_weight()+"");
			tv_edit_username.setText(user.getUser_name());
			tv_edit_sex.setText(user.getUser_sex());
			tv_edit_phone.setText(user.getUser_phone());
			tv_edit_age.setText(user.getUser_age()+"");
			ImageLoader.getInstance().displayImage(user.getUser_image(),bct_details_image);
		}
	}

	public void showTextDialog(final int i, String value){
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.edit_details_edittext_dialog,null);
		TextView tv_edit_dialog_name = (TextView)view.findViewById(R.id.tv_edit_dialog_name);
		final BootstrapEditText bet_edit_dialog_content = (BootstrapEditText)view.findViewById(R.id.bet_edit_dialog_content);
		switch (i){
			case 0:
				tv_edit_dialog_name.setText("用户名");
				break;
			case 1:
				tv_edit_dialog_name.setText("电话");
				//设置只能输入数字
				bet_edit_dialog_content.setInputType(InputType.TYPE_CLASS_NUMBER);
				break;
			case 2:
				tv_edit_dialog_name.setText("年龄");
				bet_edit_dialog_content.setInputType(InputType.TYPE_CLASS_NUMBER);
				break;
			case 3:
				tv_edit_dialog_name.setText("体重");
				bet_edit_dialog_content.setInputType(InputType.TYPE_CLASS_NUMBER);
				break;
			case 4:
				tv_edit_dialog_name.setText("邮箱");
				break;
		}
		bet_edit_dialog_content.setText(value);
		AlertDialog.Builder ab = new AlertDialog.Builder(EditPersonalDetailsActivity.this);
		ab.setView(view);
		dialog = ab.create();
		dialog.setButton(0, "确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String text = bet_edit_dialog_content.getText().toString().trim();
				if (text == null){
					ToastUtils.showToast("输入不能为空",EditPersonalDetailsActivity.this);
				}else {
					Message message = new Message();
					message.arg1 = i;
					message.obj = text;
					handler.sendMessage(message);
					dialog.dismiss();
				}
			}
		});
		dialog.show();

	}
}
