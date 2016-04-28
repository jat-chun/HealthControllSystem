package com.example.healthcontrollsystem.adapter;

import java.util.List;

import com.example.healthcontrollsystem.R;
import com.example.healthcontrollsystem.view.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HealthTipsAdapter extends BaseAdapter {

	private Context context;
	private List<String> tips_list;
	private LayoutInflater inflater;
	
	public HealthTipsAdapter(Context context,List<String> tips_list) {
		// TODO Auto-generated constructor stub
		this.tips_list = tips_list;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (tips_list!=null) {
			return tips_list.size();
		}
		return 0;
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
			view = inflater.inflate(R.layout.tips_list_item, null);
			holder = new ViewHolder();
			holder.civ_tips_logo = (CircularImageView) view.findViewById(R.id.civ_tips_logo);
			holder.tv_tips_name = (TextView) view.findViewById(R.id.tv_tips_item_name);
			view.setTag(holder);
		}else {
			holder = (ViewHolder) view.getTag();
		}
		if (position==0) {
			ImageLoader.getInstance().displayImage("drawable://"+R.mipmap.high_xueya, holder.civ_tips_logo);
		}else if (position==1) {
			ImageLoader.getInstance().displayImage("drawable://"+R.mipmap.low_xueya, holder.civ_tips_logo);
		}else if (position==2) {
			ImageLoader.getInstance().displayImage("drawable://"+R.mipmap.fast_bleed, holder.civ_tips_logo);
		}else if (position==3) {
			ImageLoader.getInstance().displayImage("drawable://"+R.mipmap.low_bleed, holder.civ_tips_logo);
		}else if (position==4) {
			ImageLoader.getInstance().displayImage("drawable://"+R.mipmap.fast_xinlv, holder.civ_tips_logo);
		}else {
			ImageLoader.getInstance().displayImage("drawable://"+R.mipmap.low_xinlv, holder.civ_tips_logo);
		}
		holder.tv_tips_name.setText(tips_list.get(position));
		return view;
	}
	
	private class ViewHolder{
		private CircularImageView civ_tips_logo;
		private TextView tv_tips_name;
	}

}
