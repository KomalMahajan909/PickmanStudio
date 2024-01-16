package com.itechvision.ecrobo.pickman.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Models.DirectArrival.DirArrivalListData;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.Arrival_ID_Check.Arrival_ID_list_data;
import com.itechvision.ecrobo.pickman.R;

import java.util.ArrayList;

public class Adap_arrivalLoc_list extends BaseAdapter {
    private Context context;
    private ArrayList<DirArrivalListData> items;

    //public constructor
    public Adap_arrivalLoc_list(Context context, ArrayList<DirArrivalListData> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.list_allocation_row, parent, false);
        }

        TextView arrivalID_txt = (TextView) convertView.findViewById(R.id.arr_all_0);
        TextView code_txt = (TextView) convertView.findViewById(R.id.arr_all_1);
        TextView total_txt = (TextView) convertView.findViewById(R.id.arr_all_2);
        TextView comp_txt = (TextView) convertView.findViewById(R.id.arr_all_3);

        //sets the text for item name and item description from the current item object
        arrivalID_txt.setText(items.get(position).getLocation());
        arrivalID_txt.setSelected(true);
        code_txt.setText(items.get(position).getLot());
        code_txt.setSelected(true);
        total_txt.setText(items.get(position).getExpiration_date());
        comp_txt.setText(items.get(position).getQuantity());
        return convertView;
    }
}