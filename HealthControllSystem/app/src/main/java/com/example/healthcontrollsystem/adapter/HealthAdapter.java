package com.example.healthcontrollsystem.adapter;

import com.example.healthcontrollsystem.R;
import com.example.healthcontrollsystem.domain.Health;
import com.example.healthcontrollsystem.view.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HealthAdapter extends BaseAdapter {

	private Context context;
	private Health enity;
	private LayoutInflater inflater;
	
	public HealthAdapter(Context context,Health enity) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.enity = enity;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (view==null) {
			view = inflater.inflate(R.layout.health_item, null);
			holder = new ViewHolder();
			holder.civ_health_item_lolgo = (CircularImageView) view.findViewById(R.id.iv_health_item_logo);
			holder.tv_health_item_cankao = (TextView) view.findViewById(R.id.tv_health_item_cankao);
			holder.tv_health_item_jianyi = (TextView) view.findViewById(R.id.tv_health_item_jianyi);
			holder.tv_health_item_real = (TextView) view.findViewById(R.id.tv_health_item_real);
			holder.tv_health_item_tips = (TextView) view.findViewById(R.id.tv_health_item_tips);
			view.setTag(holder);
		}else {
			holder = (ViewHolder) view.getTag();
		}
		if (position==0) {
			ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.ic_xueya, holder.civ_health_item_lolgo);
			holder.tv_health_item_cankao.setText("正常血压参考值为60～140");
			holder.tv_health_item_real.setText("您的血压值为"+enity.getMin_xueya()+"～"+enity.getMax_xueya());
			if (enity.getMin_xueya()<60) {
				holder.tv_health_item_jianyi.setText("您的血压偏低哦！");
				holder.tv_health_item_jianyi.setTextColor(Color.RED);
			}else if (enity.getMax_xueya()>140) {
				holder.tv_health_item_jianyi.setText("您的血压偏高哦！");
				holder.tv_health_item_jianyi.setTextColor(Color.RED);
			}else {
				holder.tv_health_item_jianyi.setText("您的血压处于正常状态下");
			}
		}else if (position==1) {
			ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.ic_bleed, holder.civ_health_item_lolgo);
			holder.tv_health_item_cankao.setText("正常呼吸范围为16～40次/分");
			holder.tv_health_item_real.setText("您的呼吸频率为"+enity.getBleed()+"次/分");
			if (enity.getBleed()<16) {
				holder.tv_health_item_jianyi.setText("您的呼吸频率较慢哦");
			}else if (enity.getBleed()>40) {
				holder.tv_health_item_jianyi.setText("您的呼吸叫快哦");
			}else {
				holder.tv_health_item_jianyi.setText("您的呼吸处于正常值");
			}
		}else {
			ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.ic_xinlv, holder.civ_health_item_lolgo);
			holder.tv_health_item_cankao.setText("正常心率参考值为60～100次/分");
			holder.tv_health_item_real.setText("您的心率为"+enity.getXinlv()+"次/分");
			if (enity.getXinlv()<60) {
				holder.tv_health_item_jianyi.setText("您的心率较慢，需要调节哦");
			}else if (enity.getXinlv()>100) {
				holder.tv_health_item_jianyi.setText("您的心率过快，要淡定哦");
			}else {
				holder.tv_health_item_jianyi.setText("您的心率处于一个正常状态下");
			}
		}
		return view;
	}
	
	private class ViewHolder{
		private CircularImageView civ_health_item_lolgo;
		private TextView tv_health_item_cankao;
		private TextView tv_health_item_real;
		private TextView tv_health_item_jianyi;
		private TextView tv_health_item_tips;
	}

}
