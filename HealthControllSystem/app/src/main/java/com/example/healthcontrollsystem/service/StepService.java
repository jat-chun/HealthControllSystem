package com.example.healthcontrollsystem.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import com.example.healthcontrollsystem.utils.AppConfig;
import com.example.healthcontrollsystem.utils.RSharePreference;
import com.example.healthcontrollsystem.utils.StepDetector;

/**
 * Created by cj.chen on 2016/4/22.
 */
public class StepService extends Service {

    public static Boolean flag = false;
    private SensorManager sensorManager;
    //检测计步类
    private StepDetector stepDetector;
    private PowerManager powerManager;//电源管理服务
    private PowerManager.WakeLock wakeLock;//屏幕灯

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        StepDetector.CURRENT_STEP = RSharePreference.getInt(AppConfig.STEP_TOTAL,StepService.this);
        //开启自动存储服务的判断
        Intent intent = new Intent(StepService.this,AutoSaveService.class);
        StepService.this.startService(intent);
        //开始的时候开启一个线程，因为后台服务也是在主线程中运行的，这样可以更加安全，不会出现主线程阻塞的情况
        new Thread(new Runnable() {
            @Override
            public void run() {
                startStepDetector();
            }
        }).start();
    }

    private void startStepDetector(){
        flag = true;
        stepDetector = new StepDetector(this);
        //获取传感器管理器的实例
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        //获得传感器的类型，这里获得的类型是加速度传感器
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //此方法用来注册，只有注册过才会生效，参数：SensorEventListener的实例，Sensor的实例，更新速率
        sensorManager.registerListener(stepDetector,sensor,SensorManager.SENSOR_DELAY_FASTEST);
        //电源管理服务
        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK|PowerManager.ACQUIRE_CAUSES_WAKEUP,"jat");
        wakeLock.acquire();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = false;
        if (stepDetector!=null){
            sensorManager.unregisterListener(stepDetector);
        }
        wakeLock.release();
    }
}
