package com.example.healthcontrollsystem.utils;

import android.content.Context;

/**
 * Created by Administrator on 2015/11/20.
 */
public class DisplayUtils {
    public static int getScreenDpi(Context context){
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static float getScreenDensity(Context context){
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getScreenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int dpToPx(int value,Context context){
        return (int) (value * getScreenDensity(context) + 0.5f);

    }

    public static int pxToDp(int value,Context context){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (value / getScreenDensity(context) + 0.5f);
    }
}
