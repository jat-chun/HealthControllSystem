package com.example.healthcontrollsystem.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.healthcontrollsystem.R;
import com.example.healthcontrollsystem.activity.TipsActivity;
import com.example.healthcontrollsystem.domain.Tips;
import com.example.healthcontrollsystem.view.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TipsAdapter extends BaseAdapter {
	
	private Context context;
	
	private LayoutInflater inflater;
	
	private List<Tips> tipsList;;
	
	public TipsAdapter(Context context,List<Tips> tipsList) {
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.tipsList = tipsList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tipsList.size();
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
	public View getView(final int arg0, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (view==null) {
			view = inflater.inflate(R.layout.tips_list_item, null);
			holder = new ViewHolder();
			holder.civ_tips_logo = (CircularImageView) view.findViewById(R.id.civ_tips_logo);
			holder.tv_tips_item_name = (TextView) view.findViewById(R.id.tv_tips_item_name);
			view.setTag(holder);
		}else {
			holder = (ViewHolder) view.getTag();
		}
		ImageLoader.getInstance().displayImage(tipsList.get(arg0).getTips_image(), holder.civ_tips_logo);
		holder.tv_tips_item_name.setText(tipsList.get(arg0).getTips_name());
		//跳转tips activity
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, TipsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putParcelable("tips", tipsList.get(arg0));
				intent.putExtras(bundle);
				context.startActivity(intent);
			}
		});
		return view;
	}
	
	private class ViewHolder{
		private CircularImageView civ_tips_logo;
		private TextView tv_tips_item_name;
	}

}
