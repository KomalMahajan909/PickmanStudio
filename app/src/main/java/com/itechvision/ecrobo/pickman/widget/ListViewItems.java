package com.itechvision.ecrobo.pickman.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewItems {
	
	//protected List<? extends Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
	protected List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
	
	public Item newItem() {
		return new Item();
	}
	
	public ListViewItems add(Item item) {
		mList.add(item.get());
		return this;
	}
	
	public int[] getTo() {
		if (mList.size() == 0) return null;
		Map<String, Object> map = mList.get(0);
		int[] x = new int[map.size()];
		int i = 0;
		for (Map.Entry<String, Object> e : map.entrySet()) {
			x[i] = Integer.parseInt(e.getKey());
			i++;
		}
		return x;
	}
	
	public String[] getFrom() {
		if (mList.size() == 0) return null;;
		Map<String, Object> map = mList.get(0);
		String[] x = new String[map.size()];
		int i = 0;
		for (Map.Entry<String, Object> e : map.entrySet()) {
			x[i] = e.getKey().toString();
			i++;
		}
		return x;
	}
	
	public List<? extends Map<String, Object>> getData() {
		return mList;
	}
	
	public class Item {
		
		protected Map<String, Object> mMap = new HashMap<String, Object>();
		
		public Map<String, Object> get() {
			return mMap;
		}
		
		public Item add(int key, String value) {
			mMap.put(String.valueOf(key), value);
			return this;
		}
	}

}
