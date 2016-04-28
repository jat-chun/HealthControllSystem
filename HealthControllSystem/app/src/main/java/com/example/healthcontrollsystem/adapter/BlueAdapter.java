package com.example.healthcontrollsystem.adapter;

import java.util.List;

import com.example.healthcontrollsystem.R;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BlueAdapter extends BaseAdapter {

	private Context context;
	
	private List<BluetoothDevice> devices;
	
	private LayoutInflater inflater;
	
	public BlueAdapter(Context context,List<BluetoothDevice> devices) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.devices = devices;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (devices!=null) {
			return devices.size();
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
			view = inflater.inflate(R.layout.item_blue_item, null);
			holder = new ViewHolder();
			holder.tv_blue_name = (TextView) view.findViewById(R.id.tv_blue_name);
			view.setTag(holder);
		}else {
			holder = (ViewHolder) view.getTag();
		}
		holder.tv_blue_name.setText(devices.get(position).getName());
		return view;
	}
	
	public class ViewHolder{
		TextView tv_blue_name;
	}

}
