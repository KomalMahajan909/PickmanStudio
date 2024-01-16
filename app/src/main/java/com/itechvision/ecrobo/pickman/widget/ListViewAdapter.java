package com.itechvision.ecrobo.pickman.widget;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ListViewAdapter extends SimpleAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	protected int mResource = 0;
	private List<? extends Map<String, Object>> mData;
	protected String[] mFrom = null;
	protected int[] mTo = null;
	
	public ListViewAdapter(Context context, ListViewItems data, int resource) {
		super(context, data.getData(), resource, data.getFrom(), data.getTo());

		mContext = context;
		mData = data.getData();
		mResource = resource;
		mFrom = data.getFrom();
		mTo = data.getTo();

		this.mInflater =
			(LayoutInflater) context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE
			);
	}

	// 初期化
	public ListViewAdapter(Context context, List<? extends Map<String, Object>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);

		mContext = context;
		mData = data;
		mResource = resource;
		mFrom = from;
		mTo = to;

		this.mInflater =
			(LayoutInflater) context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE
			);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		Map<String, Object> row = mData.get(position);

		if (v == null) v = mInflater.inflate(mResource, null);
 		//for (Map.Entry<String, Object> e : row.entrySet()) {
		for (int i = 0; i < mFrom.length; i++) {
			View item = v.findViewById(mTo[i]);
			if (item instanceof TextView){
				Log.e("ListViewAdapter","Data being processed>>>>  " +row.get(mFrom[i]));
				((TextView) item).setText(row.get(mFrom[i]).toString());}
			else if (item instanceof RadioButton)
				((RadioButton) item).setText(row.get(mFrom[i]).toString());
		}
		return v;
	}
	
}