package com.example.healthcontrollsystem.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.Window;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.healthcontrollsystem.R;
import com.example.healthcontrollsystem.utils.AppConfig;
import com.example.healthcontrollsystem.utils.RSharePreference;
import com.tandong.sa.activity.SmartActivity;

/**
 * 计划activity
 * Created by cj.chen on 2016/4/26.
 */
public class PlanActivity extends SmartActivity {

    private BootstrapEditText bet_plan_step_num;
    private BootstrapButton bbtn_plan_sure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_plan);
        init();
    }

    public void init(){
        bet_plan_step_num = (BootstrapEditText) findViewById(R.id.bet_plan_step_num);
        bbtn_plan_sure = (BootstrapButton)findViewById(R.id.bbtn_plan_sure);
        //初始化步数
        if (RSharePreference.getInt(AppConfig.PLAN_STEP,this)==0){
            bet_plan_step_num.setText("10000");
        }else {
            bet_plan_step_num.setText(RSharePreference.getInt(AppConfig.PLAN_STEP,this)+"");
        }
        //监听按钮，保存数据
        bbtn_plan_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证输入是否正确
                if (bet_plan_step_num.getText().toString().trim().equals("")||Integer.valueOf(bet_plan_step_num.getText().toString().trim())==0){
                    showToast("请填写后再确定");
                }else{
                    RSharePreference.putInt(AppConfig.PLAN_STEP,Integer.valueOf(bet_plan_step_num.getText().toString().trim()),PlanActivity.this);
                    finish();
                }
            }
        });
    }
}
