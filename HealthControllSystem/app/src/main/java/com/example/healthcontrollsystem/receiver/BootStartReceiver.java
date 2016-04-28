package com.example.healthcontrollsystem.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.healthcontrollsystem.service.StepService;

/**
 * 开机自动开启计步器
 * Created by cj.chen on 2016/4/26.
 */
public class BootStartReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //开机自动开启计步器
        Intent intent1 = new Intent(context, StepService.class);
        context.startService(intent1);
    }
}
