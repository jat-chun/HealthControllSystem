package com.example.healthcontrollsystem.adapter;

import com.example.healthcontrollsystem.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CanKaoAdapter extends BaseAdapter {
	
	private Context context;
	private LayoutInflater inflater;

	public CanKaoAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
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
			view = inflater.inflate(R.layout.item_cankao, null);
			holder = new ViewHolder();
			holder.iv_cankao_image = (ImageView) view.findViewById(R.id.iv_cankao_image);
			holder.tv_cankao_content = (TextView) view.findViewById(R.id.tv_cankao_content);
			view.setTag(holder);
		}else {
			holder = (ViewHolder) view.getTag();
		}
		if (position==0) {
			ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.ic_xueya, holder.iv_cankao_image);
			holder.tv_cankao_content.setText("正常血压参考值为60～140");
		}else if (position==1) {
			ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.ic_bleed, holder.iv_cankao_image);
			holder.tv_cankao_content.setText("正常呼吸范围为16～40次/分");
		}else {
			ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.ic_xinlv, holder.iv_cankao_image);
			holder.tv_cankao_content.setText("正常心率参考值为60～100次/分");
		}
		
		return view;
	}
	
	private class ViewHolder{
		private ImageView iv_cankao_image;
		private TextView tv_cankao_content;
	}

}
