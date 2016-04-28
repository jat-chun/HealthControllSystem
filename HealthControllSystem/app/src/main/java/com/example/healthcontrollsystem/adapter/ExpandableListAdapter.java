package com.example.healthcontrollsystem.adapter;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.healthcontrollsystem.R;
import com.example.healthcontrollsystem.activity.HealthActivity;
import com.example.healthcontrollsystem.domain.Health;
import com.example.healthcontrollsystem.domain.OneStatusEntity;
import com.example.healthcontrollsystem.domain.Record;
import com.example.healthcontrollsystem.utils.DateFormatUtils;
/**
 * 时间轴适配器
 * @author jat
 *
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<OneStatusEntity> oneList;

	public ExpandableListAdapter(Context context,List<OneStatusEntity> oneList) {
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.oneList = oneList;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return oneList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if(oneList.get(groupPosition).getTwoList() == null){
			return 0;
		}else{
			return oneList.get(groupPosition).getTwoList().size();
		}
	}

	@Override
	public OneStatusEntity getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return oneList.get(groupPosition);
	}

	@Override
	public Record getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return oneList.get(groupPosition).getTwoList().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

		GroupViewHolder holder = new GroupViewHolder();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.one_status_item, null);
		}
		holder.groupName = (TextView) convertView.findViewById(R.id.one_status_name);
		holder.group_tiao = (TextView) convertView.findViewById(R.id.group_tiao);

		holder.groupName.setText(oneList.get(groupPosition).getStatusName());
		holder.group_tiao.setBackgroundColor(context.getResources().getColor(R.color.yellow));

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		ChildViewHolder viewHolder = null;
		final Record entity = getChild(groupPosition, childPosition);
		if (convertView != null) {
			viewHolder = (ChildViewHolder) convertView.getTag();
		} else {
			viewHolder = new ChildViewHolder();
			convertView = inflater.inflate(R.layout.two_status_item, null);
			viewHolder.childName = (TextView) convertView.findViewById(R.id.two_status_name);
			viewHolder.twoStatusTime = (TextView) convertView.findViewById(R.id.two_complete_time);
			viewHolder.tiao = (TextView) convertView.findViewById(R.id.tiao);
		}
		String string = "步行"+entity.getStep()+"步，消耗卡里路"+entity.getKaluli()+" ,共行走"+entity.getDistance()+"公里";
		
		viewHolder.childName.setText(string);
		
		viewHolder.twoStatusTime.setText(entity.getDate());

		viewHolder.tiao.setBackgroundColor(context.getResources().getColor(R.color.yellow));
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//				Toast.makeText(context, entity.getStatusName(), 0).show();
				Intent intent = new Intent(context, HealthActivity.class);
				Bundle bundle = new Bundle();
//				bundle.putSerializable("bean", entity);
				intent.putExtra("bean", bundle);
				context.startActivity(intent);
			}
		});
		convertView.setTag(viewHolder);
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

	private class GroupViewHolder {
		TextView groupName;
		public TextView group_tiao;
	}

	private class ChildViewHolder {
		public TextView childName;
		public TextView twoStatusTime;
		public TextView tiao;
	}

}
