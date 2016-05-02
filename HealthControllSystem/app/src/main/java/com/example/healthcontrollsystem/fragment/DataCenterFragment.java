package com.example.healthcontrollsystem.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.healthcontrollsystem.R;
import com.example.healthcontrollsystem.adapter.ExpandableListAdapter;
import com.example.healthcontrollsystem.domain.Health;
import com.example.healthcontrollsystem.domain.OneStatusEntity;
import com.example.healthcontrollsystem.domain.Record;
import com.example.healthcontrollsystem.domain.TwoStatusEntity;
import com.example.healthcontrollsystem.utils.AppConfig;
import com.example.healthcontrollsystem.utils.KaliluUtils;
import com.example.healthcontrollsystem.utils.RSharePreference;
import com.example.healthcontrollsystem.utils.StepDetector;
import com.example.healthcontrollsystem.utils.ToastUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;

public class DataCenterFragment extends Fragment{

	View view;

	LayoutInflater inflater ;

	private ExpandableListView elv_data_list;
	
	private List<OneStatusEntity> oneList;
	
	private String TAG = "DataCenterFragment";

	private BarChart bc_data_center;

	private List<BarEntry> entries = new ArrayList<BarEntry>();

	private List<String> labels = new ArrayList<>();
	private ExpandableListAdapter adapter;

	private BarDataSet dataSet;
	private BarData data;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		inflater= LayoutInflater.from(getActivity());
		view = inflater.inflate(R.layout.fragment_data_center, null);
		elv_data_list = (ExpandableListView) view.findViewById(R.id.elv_data_list);
		bc_data_center = (BarChart) view.findViewById(R.id.bc_data_center);
//		elv_data_list.
		putInitData();
		init2();
		return view;
	}

	private void init2(){
		entries.add(new BarEntry(10000,0));
		entries.add(new BarEntry(5000,1));
		entries.add(new BarEntry(15000,2));
		entries.add(new BarEntry(2000,3));
		entries.add(new BarEntry(25000,4));
		entries.add(new BarEntry(StepDetector.CURRENT_STEP,5));
		ToastUtils.showToast(RSharePreference.getInt(AppConfig.STEP_TOTAL,getActivity())+"",getActivity());
		labels.add("星期一");
		labels.add("星期二");
		labels.add("星期三");
		labels.add("星期四");
		labels.add("星期五");
		labels.add("星期六");
		dataSet = new BarDataSet(entries,"步行数");
		data = new BarData(labels,dataSet);
		dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
		LimitLine line = new LimitLine(10f);
		bc_data_center.setData(data);
		bc_data_center.setDescriptionTextSize(10f);
		bc_data_center.animateXY(5000,5000);
		bc_data_center.setDescription("数据统计图");

	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		
	}
	
	/**
	 * 初始化
	 */
	private void init() {
		// TODO Auto-generated method stub
		
		
	}

	private void putInitData() {
//		String[] strArray = new String[]{"健康系数"};
//
//		String[] timeStr1 = new String[]{"步行12.5公里，消耗卡路里100 2016年4月20日", "步行12.5公里，消耗卡路里100 2016年4月20日", "步行12.5公里，消耗卡路里100 2016年4月20日", "步行12.5公里，消耗卡路里100 2016年4月20日", "步行12.5公里，消耗卡路里100 2016年4月20日", "步行12.5公里，消耗卡路里100 2016年4月20日", "步行12.5公里，消耗卡路里100 2016年4月20日", "步行12.5公里，消耗卡路里100 2016年4月20日", "步行12.5公里，消耗卡路里100 2016年4月20日"};

		oneList = new ArrayList<OneStatusEntity>();

		OneStatusEntity oneStatusEntity = new OneStatusEntity();

		List<Record> twoList = new ArrayList<>();
		for (int i=0 ; i<6 ; i++){
			Record record = null;
			if (i==0){
				record  = new Record(i,StepDetector.CURRENT_STEP, KaliluUtils.kalilu(RSharePreference.getFloat(AppConfig.WEIGHT,getActivity()),StepDetector.CURRENT_STEP),KaliluUtils.distance(StepDetector.CURRENT_STEP),"2016年4月30","星期六");
			}else if (i==1){
				record = new Record(i,25000,KaliluUtils.kalilu(RSharePreference.getFloat(AppConfig.WEIGHT,getActivity()),25000),KaliluUtils.distance(25000),"2016年4月29","星期五");
			}else if (i==2){
				record = new Record(i,2000,KaliluUtils.kalilu(RSharePreference.getFloat(AppConfig.WEIGHT,getActivity()),2000),KaliluUtils.distance(2000),"2016年4月28","星期四");
			}else if (i==3){
				record = new Record(i,15000,KaliluUtils.kalilu(RSharePreference.getFloat(AppConfig.WEIGHT,getActivity()),15000),KaliluUtils.distance(15000),"2016年4月27","星期三");
			}else if (i==4){
				record = new Record(i,5000,KaliluUtils.kalilu(RSharePreference.getFloat(AppConfig.WEIGHT,getActivity()),5000),KaliluUtils.distance(5000),"2016年4月26","星期二");
			}else if (i==5){
				record = new Record(i,10000,KaliluUtils.kalilu(RSharePreference.getFloat(AppConfig.WEIGHT,getActivity()),10000),KaliluUtils.distance(10000),"2016年4月25","星期一");
			}

			twoList.add(record);
		}

		oneStatusEntity.setTwoList(twoList);
		oneStatusEntity.setStatusName("健康系数时间轴");
		oneStatusEntity.setCompleteTime("2016年");
		oneList.add(oneStatusEntity);


		adapter =  new ExpandableListAdapter(getActivity(), oneList);

		elv_data_list.setAdapter(adapter);
		elv_data_list.setGroupIndicator(null); // 去掉默认带的箭头

		// 遍历所有group,将所有项设置成默认展开
		int groupCount = elv_data_list.getCount();
		for (int i = 0; i < groupCount; i++) {
			elv_data_list.expandGroup(i);
		}
	}
}
