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
import com.itechvision.ecrobo.pickman.Models.ReturnStock.ReturnStokeLotExpData;
import com.itechvision.ecrobo.pickman.R;

import java.util.ArrayList;

public class Adap_RetunStokeLot extends BaseAdapter {
    private Context context; //context
    private ArrayList<ReturnStokeLotExpData> items; //data source of the list adapter

  //public constructor
    public Adap_RetunStokeLot(Context context, ArrayList<ReturnStokeLotExpData> items) {
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
                    inflate(R.layout.adap_retunstock, parent, false);
        }

         TextView lot = (TextView) convertView.findViewById(R.id.lotno);
         TextView exp = (TextView) convertView.findViewById(R.id.expri);
         TextView qty = (TextView) convertView.findViewById(R.id.qty);

         lot.setText(items.get(position).getLot());
         exp.setText(items.get(position).getExpiration_date());
         qty.setText(items.get(position).getNum());


         return convertView;
    }
}