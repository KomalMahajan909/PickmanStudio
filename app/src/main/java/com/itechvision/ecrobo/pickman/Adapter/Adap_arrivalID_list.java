package com.itechvision.ecrobo.pickman.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Models.BatchPick.ProductList.ProductData;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.Arrival_ID_Check.Arrival_ID_list_data;
import com.itechvision.ecrobo.pickman.R;

import java.util.ArrayList;

public class Adap_arrivalID_list extends BaseAdapter {
    private Context context;
    private ArrayList<Arrival_ID_list_data> items;

    //public constructor
    public Adap_arrivalID_list(Context context, ArrayList<Arrival_ID_list_data> items) {
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
                    inflate(R.layout.arrival_list_popup_row, parent, false);
        }

        TextView arrivalID_txt = (TextView) convertView.findViewById(R.id.arrivalID_txt);
        TextView code_txt = (TextView) convertView.findViewById(R.id.code_txt);
        TextView total_txt = (TextView) convertView.findViewById(R.id.total_txt);
        TextView comp_txt = (TextView) convertView.findViewById(R.id.comp_txt);

        //sets the text for item name and item description from the current item object
        arrivalID_txt.setText(items.get(position).getNyuka_id());
        arrivalID_txt.setSelected(true);
        code_txt.setText(items.get(position).getCode());
        code_txt.setSelected(true);
        total_txt.setText(items.get(position).getRsv_cnt());
        comp_txt.setText(items.get(position).getComplete_qty());
        return convertView;
    }
}