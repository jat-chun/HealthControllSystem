package com.example.healthcontrollsystem.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.example.healthcontrollsystem.utils.AppConfig;
import com.example.healthcontrollsystem.utils.DateFormatUtils;
import com.example.healthcontrollsystem.utils.KaliluUtils;
import com.example.healthcontrollsystem.utils.OkHttpUtil;
import com.example.healthcontrollsystem.utils.RSharePreference;
import com.example.healthcontrollsystem.utils.StepDetector;
import com.example.healthcontrollsystem.utils.ToastUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 自动验证上传数据到服务器
 * Created by cj.chen on 2016/4/22.
 */
public class AutoSaveService extends Service {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String body = (String) msg.obj;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //判断时间上次开启的时候是不是当天，如果是当天则跳过，否则就上传数据到服务器
//        if (!dateCompile()){
            //存进当天日期
            RSharePreference.putString(AppConfig.DATE,DateFormatUtils.dateFormat(System.currentTimeMillis()),this);
            //将步数置0
            RSharePreference.putInt(AppConfig.STEP_TOTAL,0,this);
            //修改计步器里面的步数
            StepDetector.CURRENT_STEP = 0;
//        }
    }

    //验证时间是不是当天
    public boolean dateCompile(){
        //判断时间上次开启的时候是不是当天，是则返回true，否则false
        return RSharePreference.getString(AppConfig.DATE,AutoSaveService.this).equals(DateFormatUtils.dateFormat(System.currentTimeMillis()))?true:false;
    }

    /**
     * 上传数据到服务器
     */
    private void postToServer(){
        if (RSharePreference.getString(AppConfig.USER_ID,this)==null){
            ToastUtils.showToast("用户没登录，请先登录",this);
        }else {
            //将用户id，步数，日期，卡里路等上传到服务器,以键值对的方式，保持请求类型不变
            //打包请求参数
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(AppConfig.USER_ID,RSharePreference.getString(AppConfig.USER_ID,AutoSaveService.this));
                jsonObject.put("step", StepDetector.CURRENT_STEP);
                jsonObject.put(AppConfig.DATE,DateFormatUtils.dateFormat(System.currentTimeMillis()));
                jsonObject.put(AppConfig.WEEK,DateFormatUtils.weekFormat());
                jsonObject.put("kalilu", KaliluUtils.kalilu(RSharePreference.getFloat(AppConfig.WEIGHT,AutoSaveService.this),StepDetector.CURRENT_STEP));
                jsonObject.put("distance", KaliluUtils.distance(StepDetector.CURRENT_STEP));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //开启异步服务器提交数据
            OkHttpUtil.enqueue(OkHttpUtil.requestPostByJson(AppConfig.ADD_RECORD,jsonObject), new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String body = response.body().toString();
                    Message message = new Message();
                    message.arg1 = 0;
                    message.obj = body;
                    handler.sendMessage(message);
                }
            });
        }
    }


}
