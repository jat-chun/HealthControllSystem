package com.example.healthcontrollsystem.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.healthcontrollsystem.service.AutoSaveService;
import com.example.healthcontrollsystem.utils.ToastUtils;

/**
 * Created by cj.chen on 2016/4/22.
 */
public class AutoSaveReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context,AutoSaveService.class);
        ToastUtils.showToast("自动保存数据中...",context);
        context.startService(i);
    }
}
