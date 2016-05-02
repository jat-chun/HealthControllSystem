package com.example.healthcontrollsystem.activity;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.healthcontrollsystem.R;
import com.example.healthcontrollsystem.R.string;
import com.example.healthcontrollsystem.service.UserInformationService;
import com.example.healthcontrollsystem.utils.AppConfig;
import com.example.healthcontrollsystem.utils.RSharePreference;
import com.example.healthcontrollsystem.utils.ToastUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.tandong.sa.activity.SmartActivity;
/**
 * 登录
 * @author jat
 *
 */
public class LoginActivity extends SmartActivity {

	//返回
	private ImageView head_back;
	private TextView head_save;
	//注册按钮
	private TextView tv_register;
	//登录按钮
	private BootstrapButton bt_login_login;
	//用户名
	private EditText et_login_name;
	//密码
	private EditText et_login_password;
	//是否记住密码
	private CheckBox cb_login_remember;
	//请求头编码格式
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	//客户端
	OkHttpClient client = new OkHttpClient();
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			String result = (String) msg.obj;
//			ToastUtils.showToast(result,LoginActivity.this);
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
				//登录成功
				if (jsonObject.getString("result").equals("success")) {
					if (cb_login_remember.isChecked()) {
						savePreferenceString("user_name", et_login_name.getText().toString());
						savePreferenceBoolean(AppConfig.LOGIN, true);
					}
					gotoActivity(HomesActivity.class, true);
					Intent intent = new Intent(LoginActivity.this, UserInformationService.class);
//					intent.putExtra("remember", cb_login_remember.isChecked());
					LoginActivity.this.startService(intent);
				}else {
					ToastUtils.showToast("登录失败，请验证您的用户名或者密码是否正确", LoginActivity.this);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (RSharePreference.getBoolean(AppConfig.LOGIN, this)) {
			startActivity(new Intent(this, HomesActivity.class));
			finish();
		}
		setContentView(R.layout.activity_login);
		init();

	}

	/**
	 * 初始化
	 */
	private void init(){
//		cb_login_remember = (CheckBox) findViewById(R.id.cb_login_remember);
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
		et_login_name = (EditText) findViewById(R.id.et_login_name);
		et_login_password = (EditText) findViewById(R.id.et_login_password);
		tv_register = (TextView) findViewById(R.id.head_save);
		tv_register.setText("注册");
		//下划线
		tv_register.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
		tv_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
				finish();
			}
		});
		bt_login_login = (BootstrapButton) findViewById(R.id.bt_login_login);
		bt_login_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				RSharePreference.putBoolean(AppConfig.LOGIN, true, getApplicationContext());
//				Intent intent = new Intent(LoginActivity.this, HomesActivity.class);
//				startActivity(intent);
//				finish();
				if (!et_login_name.getText().toString().equals("")&&!et_login_password.getText().toString().equals("")) {
					try {
						login();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	/**
	 * 登录
	 * @throws JSONException
	 */
	private void login() throws JSONException{
		final JSONObject object = new JSONObject();
		String user_name = et_login_name.getText().toString();
		String user_password = et_login_password.getText().toString();
		object.put("user_name", user_name);
		object.put("user_password", user_password);

		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				RequestBody body = RequestBody.create(JSON, object.toString());

				Request request = new Request.Builder()
				.url(AppConfig.BASE_URL+AppConfig.USER_LOGIN)
				.post(body)
				.build();

				Response response = null;
				try {
					response = client.newCall(request).execute();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.d("messageerror", e.getMessage().toString());
				}
				Message message = new Message();
				message.what = 2;
				try {
					message.obj = response.body().string();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendMessage(message);
			}
		});
		thread.start();
	}

}