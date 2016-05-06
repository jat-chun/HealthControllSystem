package com.example.healthcontrollsystem.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 实现信号监听的计步的工具类
 *
 * Created by jat on 2016/4/22.
 */
public class StepDetector implements SensorEventListener {
    //记录现在的步数
    public static int CURRENT_STEP = 0;
    //sensitivity灵敏度
    public static float SENSITIVITY = 10;
    private float mLastValues[] = new float[3*2];
    private float mScale[] = new float[2];
    private float mYOffset;
    private Context context;
    /**
     *start、end 为计算一步结算与上一步结束的时间的间隔，具体往下看
     */
    //检测结束时间
    private static long end = 0;
    //检测开始时间
    private static long start = 0;

    //最后加速度方向
    private float mLastDirections[] = new float[3*2];
    private float mLastExtremes[][] = {new float[3*2],new float[3*2]};
    private float mLastDiff[] = new float[3*2];
    private int mLastMtch = -1;

    public StepDetector(Context context){
        this.context = context;
        int h = 480;
        mYOffset = h*0.5f;
        mScale[0] = -(h*0.5f*(1.0f/ SensorManager.STANDARD_GRAVITY*2));
        mScale[1] = -(h*0.5f*(1.0f/(SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
    }

    /**
     * 当传感器检测到的数值发生变化时会调用这个方法
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        //获取传感器
        Sensor sensor = event.sensor;
        //锁定传感器
        synchronized (this){
            //判读传感器类型是不是为重力感应加速度
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                float vSum = 0;
                for (int i=0 ; i<3 ; i++){
                    final float v = mYOffset + event.values[i]*mScale[1];
                    vSum+=v;
                }
                int k = 0;
                float v = vSum/3;

                float direction = (v>mLastValues[k]?1:(v<mLastValues[k]?-1:0));
                if (direction == -mLastDirections[k]){
                    //Direction changed
                    int exType = (direction>0?0:1);//minumum or maximum
                    mLastExtremes[exType][k] = mLastValues[k];
                    float diff = Math.abs(mLastExtremes[exType][k] - mLastExtremes[1-exType][k]);

                    if (diff>SENSITIVITY){
                        boolean isAlmostAsLargeAsPrevious = diff>(mLastDiff[k]*2/3);
                        boolean isPreviousLargeEnough = mLastDiff[k]>(diff/3);
                        boolean isNotContra = (mLastMtch != 1-exType);

                        if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra){
                            end = System.currentTimeMillis();
                            if (end - start > 500){//此为判断为走了一步
                                CURRENT_STEP++;
                                RSharePreference.putInt(AppConfig.STEP_TOTAL,CURRENT_STEP,context);
                                mLastMtch = exType;
                                start = end;
                            }
                        }else {
                            mLastMtch = -1;
                        }
                    }
                    mLastDiff[k] = diff;
                }
                mLastDirections[k] = direction;
                mLastValues[k] = v;
            }
        }

    }

    //当传感器的经度发生变化时就会调用此方法，在这里不需要用到这个类
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
