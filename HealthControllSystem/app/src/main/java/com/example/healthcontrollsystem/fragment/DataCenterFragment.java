package com.example.healthcontrollsystem.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.andview.refreshview.XRefreshView;
import com.example.healthcontrollsystem.R;
import com.example.healthcontrollsystem.adapter.ExpandableListAdapter;
import com.example.healthcontrollsystem.domain.OneStatusEntity;
import com.example.healthcontrollsystem.domain.Record;
import com.example.healthcontrollsystem.utils.AppConfig;
import com.example.healthcontrollsystem.utils.KaliluUtils;
import com.example.healthcontrollsystem.utils.OkHttpUtil;
import com.example.healthcontrollsystem.utils.RSharePreference;
import com.example.healthcontrollsystem.utils.StepDetector;
import com.example.healthcontrollsystem.utils.ToastUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataCenterFragment extends Fragment{

	View view;

	LayoutInflater inflater ;

	//时间轴列表
	private ExpandableListView elv_data_list;
	//上下拉刷新XRefreshView
	private XRefreshView xrv_data_center;
	//时间轴的一级列表
	private List<OneStatusEntity> oneList;
	
	private String TAG = "DataCenterFragment";
	//柱形图
	private BarChart bc_data_center;
	//柱形图实体类
	private List<BarEntry> entries = new ArrayList<BarEntry>();
	//柱形图label
	private List<String> labels = new ArrayList<>();
	//时间轴适配器
	private ExpandableListAdapter adapter;
	//柱形图数据集
	private BarDataSet dataSet;
	//柱形图数据
	private BarData data;

	private int indexPage = 0;

	private int pageSize = 7;

	//判断是否第一次加载
	private Boolean isFirst = true;

	private DbUtils dbUtils;

	List<Record> twoList;

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}
	};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		inflater= LayoutInflater.from(getActivity());
		view = inflater.inflate(R.layout.fragment_data_center, null);
		dbUtils = DbUtils.create(getActivity());
		elv_data_list = (ExpandableListView) view.findViewById(R.id.elv_data_list);
		bc_data_center = (BarChart) view.findViewById(R.id.bc_data_center);
		xrv_data_center = (XRefreshView)view.findViewById(R.id.xrv_data_center);
		refresh();
//		elv_data_list.
		putInitData();
		init2();
		getListData(indexPage);
		return view;
	}

	/**
	 * 刷新设置
	 */
	public void refresh(){
		//设置是否可以上下拉刷新
		xrv_data_center.setPullLoadEnable(true);
		xrv_data_center.setPullRefreshEnable(true);
		//设置是否可以自动刷新
		xrv_data_center.setAutoRefresh(true);
		xrv_data_center.setAutoLoadMore(true);
		//设置最迟刷新时间
		xrv_data_center.restoreLastRefreshTime(System.currentTimeMillis());
		//刷新监听
		xrv_data_center.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener(){
			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						//停止下拉刷新
						xrv_data_center.stopRefresh();
						indexPage = 0;
						getListData(indexPage);
					}
				}, 2000);
			}
			@Override
			public void onLoadMore(boolean isSlience) {
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						//停止上拉刷新
						xrv_data_center.stopLoadMore();
						indexPage = indexPage+1;
						getListData(indexPage);
					}
				}, 2000);
			}

			@Override
			public void onRelease(float direction) {
				super.onRelease(direction);
				if (direction > 0) {
					ToastUtils.showToast("下拉",getActivity());
				} else {
					ToastUtils.showToast("上拉",getActivity());
				}
			}
		});

	}

	//获取列表数据
	private void getListData(int index){
		//封装请求参数
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("user_id",RSharePreference.getInt(AppConfig.USER_ID,getActivity()));
			jsonObject.put("index",index);
			jsonObject.put("pageSize",pageSize);
		}catch (Exception e){
			e.printStackTrace();
		}
		OkHttpUtil.enqueue(OkHttpUtil.requestPostByJson(AppConfig.GETRECORDLIST, jsonObject), new Callback() {
			@Override
			public void onFailure(Request request, IOException e) {
				//请求失败而且是第一次请求时调用本地缓存数据进行显示
				if (isFirst){
					twoList = new ArrayList<Record>();
					try {
						twoList = dbUtils.findAll(Record.class);
					} catch (DbException e1) {
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void onResponse(Response response) throws IOException {
				isFirst = false;
			}
		});
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

		twoList = new ArrayList<>();
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
