package com.example.healthcontrollsystem.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.example.healthcontrollsystem.R;
import com.example.healthcontrollsystem.activity.EditPersonalDetailsActivity;
import com.example.healthcontrollsystem.activity.HealthTipsListActivity;
import com.example.healthcontrollsystem.activity.PlanActivity;
import com.example.healthcontrollsystem.service.AutoSaveService;
import com.example.healthcontrollsystem.service.StepService;
import com.example.healthcontrollsystem.utils.AppConfig;
import com.example.healthcontrollsystem.utils.KaliluUtils;
import com.example.healthcontrollsystem.utils.RSharePreference;
import com.example.healthcontrollsystem.utils.StepDetector;
import com.example.healthcontrollsystem.utils.ToastUtils;
import com.github.glomadrian.dashedcircularprogress.DashedCircularProgress;

public class DateStateFragment extends Fragment {

	View view;

	private TextView tv_edit_plan;

	//步数
	private AwesomeTextView atv_state_step_num;

	//进度条
	private DashedCircularProgress dcpg_state_step_count;

	private TextView tv_state_health_tips;

	private TextView tv_state_health_details;

	private TextView tv_state_kalilu;

	private BootstrapLabel bl_state_label;

	private double kalilu;

	private float weight = 0;

	private int plan_step;

	private boolean flag;

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (StepService.flag){

			}else {
				if (!StepService.flag){
					Intent intent = new Intent(getActivity(),StepService.class);
					getActivity().startService(intent);
				}
			}
			atv_state_step_num.setText(StepDetector.CURRENT_STEP+"");
			tv_state_kalilu.setText("您消耗的卡里路为："+ KaliluUtils.kalilu(weight,StepDetector.CURRENT_STEP));
			bl_state_label.setText("您设定奔跑的距离为"+KaliluUtils.distance(RSharePreference.getInt(AppConfig.PLAN_STEP,getActivity()))+",现在运动了"+KaliluUtils.distance(StepDetector.CURRENT_STEP));
			if (!flag){
				dcpg_state_step_count.setValue(StepDetector.CURRENT_STEP);
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_state, null);
		Intent intent = new Intent(getActivity(), AutoSaveService.class);
		getActivity().startService(intent);
		initView();
		initMotion();
		stepListener();
//		ToastUtils.showToast(RSharePreference.getString(AppConfig.DATE,getActivity()),getActivity());
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		//获取计划步数,还没设置的话则重新设为10000
		if (RSharePreference.getInt(AppConfig.PLAN_STEP,getActivity())==0){
			plan_step = 10000;
		}else{
			plan_step = RSharePreference.getInt(AppConfig.PLAN_STEP,getActivity());
		}
		bl_state_label.setText("您设定奔跑的距离为"+RSharePreference.getInt(AppConfig.PLAN_STEP,getActivity())+",现在运动了"+KaliluUtils.distance(StepDetector.CURRENT_STEP));
		tv_state_kalilu.setText("您消耗的卡里路为"+KaliluUtils.kalilu(weight,StepDetector.CURRENT_STEP));
		initMotion();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		//获取体重
		weight = RSharePreference.getFloat(AppConfig.WEIGHT,getActivity());
		//获取计划步数,还没设置的话则重新设为10000
		if (RSharePreference.getInt(AppConfig.PLAN_STEP,getActivity())==0){
			plan_step = 10000;
		}else{
			plan_step = RSharePreference.getInt(AppConfig.PLAN_STEP,getActivity());
		}
		tv_edit_plan = (TextView) view.findViewById(R.id.tv_edit_plan);
		tv_state_health_tips = (TextView) view.findViewById(R.id.tv_state_health_tips);
		atv_state_step_num = (AwesomeTextView) view.findViewById(R.id.atv_state_step_num);
		dcpg_state_step_count = (DashedCircularProgress) view.findViewById(R.id.dcpg_state_step_count);
		tv_state_health_details = (TextView) view.findViewById(R.id.tv_state_health_details);
		bl_state_label = (BootstrapLabel) view.findViewById(R.id.bl_state_label);
		tv_state_kalilu = (TextView)view.findViewById(R.id.tv_state_kalilu);
	}

	/**
	 * 初始化事件
	 */
	public void initMotion(){
		//设置步数,记得对int进行强转
		atv_state_step_num.setText(RSharePreference.getInt(AppConfig.STEP_TOTAL,getActivity())+"");
		//对进度环进行设置,设置最大最小值
		if (plan_step==0){
			dcpg_state_step_count.setMax(10000);
		}else {
			dcpg_state_step_count.setMax(plan_step);
		}
		dcpg_state_step_count.setMin(0);
		//监听
		tv_edit_plan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().startActivity(new Intent(getActivity(), PlanActivity.class));
			}
		});
		tv_state_health_tips.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().startActivity(new Intent(getActivity(), HealthTipsListActivity.class));
			}
		});
		tv_state_health_details.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (weight==0){
					ToastUtils.showToast("请先登录",getActivity());
				}else {
					getActivity().startActivity(new Intent(getActivity(), EditPersonalDetailsActivity.class));
				}
			}
		});
	}

	/**
	 * 更新界面
	 * 开启线程，死循环更新界面，循环中加入线程睡眠，最后通过handler处理UI界面
	 */
	public void stepListener(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (plan_step<StepDetector.CURRENT_STEP){
						flag = false;
					}
					handler.sendEmptyMessage(0);
				}
			}
		}).start();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
}
