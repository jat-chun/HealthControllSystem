package com.example.healthcontrollsystem.utils;

import java.text.DecimalFormat;

/**
 * 计算卡里路
 * Created by cj.chen on 2016/4/28.
 */
public class KaliluUtils {

    final static DecimalFormat decimalFormat = new DecimalFormat("#.##");
    /**\
     * 卡里路公式 = 体重（kg）*行程（km）*1.036
     * @param weight
     * @param step
     * @return
     */
    public static double kalilu(double weight,int step){
        if (weight==0){
            weight = 100;
        }
        double kalilu = (weight/2)*(step*0.7*0.001)*1.036;
        return Double.valueOf(decimalFormat.format(kalilu));
    }

    /**
     * 返回路程
     * @param step
     * @return
     */
    public static double distance(int step){
        return Double.valueOf(decimalFormat.format(step*0.7*0.001));
    }


}
