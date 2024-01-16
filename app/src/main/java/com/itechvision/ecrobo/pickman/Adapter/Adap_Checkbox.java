package com.itechvision.ecrobo.pickman.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Models.BatchPick.BoxDetail.BoxData;
import com.itechvision.ecrobo.pickman.Models.BatchPick.ProductList.ProductData;
import com.itechvision.ecrobo.pickman.R;

import java.util.ArrayList;

public class Adap_Checkbox extends BaseAdapter {
    private Context context; //context
    private ArrayList<BoxData> items; //data source of the list adapter

  //public constructor
    public Adap_Checkbox(Context context, ArrayList<BoxData> items) {
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
                    inflate(R.layout.adap_grid, parent, false);
        }

         Button batch = (Button) convertView.findViewById(R.id.box);

         if (items.get(position).getStatus().equalsIgnoreCase("Finished")){
         batch.setBackgroundColor(Color.parseColor("#d9d9d9"));
         }else if(items.get(position).getStatus().equalsIgnoreCase("Pending")){
            batch.setBackgroundColor(Color.parseColor("#f4b183"));
         }else{
            batch.setBackgroundColor(Color.parseColor("#ffd966"));
         }
         batch.setText(items.get(position).getBatch_detail_no());

         return convertView;
    }
}