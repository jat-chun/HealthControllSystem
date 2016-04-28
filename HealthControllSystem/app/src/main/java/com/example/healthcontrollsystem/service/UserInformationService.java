package com.example.healthcontrollsystem.service;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.healthcontrollsystem.domain.User;
import com.example.healthcontrollsystem.utils.AppConfig;
import com.example.healthcontrollsystem.utils.RSharePreference;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
/**
 * 获取个人详细信息
 * @author jat
 *
 */
public class UserInformationService extends Service {
	
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	OkHttpClient client = new OkHttpClient();
	
	private Context TAG = UserInformationService.this;
	
	DbUtils db;
	
//	private boolean isRemember;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject((String)msg.obj);
//				if (isRemember) {
//					RSharePreference.putString("user_password", jsonObject.getString("user_password"), TAG);
//				}
				
//				RSharePreference.putString("user_phone", jsonObject.getString("user_phone"), TAG);
				RSharePreference.putString("user_image", jsonObject.getString("user_image"), TAG);
//				RSharePreference.putString("user_sex", jsonObject.getString("user_sex"), TAG);
//				RSharePreference.putInt("user_age", jsonObject.getInt("user_age"), TAG);
				RSharePreference.putInt("id", jsonObject.getInt("id"), TAG);
				RSharePreference.putFloat(AppConfig.WEIGHT,(float)jsonObject.getDouble("user_weight"),TAG);

				User user = new User(jsonObject.getInt("id"),jsonObject.getString("user_name"),jsonObject.getString("user_password"),jsonObject.getString("user_image"),jsonObject.getInt("user_age"),
						jsonObject.getString("user_phone"),jsonObject.getString("user_sex"),jsonObject.getDouble("user_weight")) ;
				try {
					db.save(user);
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					db.close();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
//		isRemember = intent.getBooleanExtra("remember", false);
		db = DbUtils.create(this);
		try {
			getDetails();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void getDetails() throws JSONException{
		String user_name = RSharePreference.getString("user_name", this);
		final JSONObject object = new JSONObject();
		object.put("user_name", user_name);
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				RequestBody body = RequestBody.create(JSON, object.toString());

				Request request = new Request.Builder()
				.url(AppConfig.BASE_URL+AppConfig.COMFIRM_USERNAME)
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
				message.what = 1;
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
