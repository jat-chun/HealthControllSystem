package com.example.healthcontrollsystem.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.example.healthcontrollsystem.domain.User;
import com.example.healthcontrollsystem.utils.AppConfig;
import com.example.healthcontrollsystem.utils.OkHttpUtil;
import com.example.healthcontrollsystem.utils.RSharePreference;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
	
	private boolean isRemember;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject((String)msg.obj);
				if (isRemember) {
					RSharePreference.putString("user_password", jsonObject.getString("user_password"), TAG);
				}

				RSharePreference.putString("user_image", jsonObject.getString("user_image"), TAG);
				RSharePreference.putInt("id", jsonObject.getInt("id"), TAG);
				RSharePreference.putString("user_name", jsonObject.getString("user_name"), TAG);
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
		isRemember = intent.getBooleanExtra("remember", false);
		db = DbUtils.create(this);
		try {
			getDetails();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取个人资料
	 *
	 * @throws JSONException
     */
	private void getDetails() throws JSONException{
		String user_name = RSharePreference.getString("user_name", this);
		final JSONObject object = new JSONObject();
		object.put("user_name", user_name);
		OkHttpUtil.enqueue(OkHttpUtil.requestPostByJson(AppConfig.PERSONAL_DETAILS, object), new Callback() {
			@Override
			public void onFailure(Request request, IOException e) {

			}

			@Override
			public void onResponse(Response response) throws IOException {
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
	}

}
