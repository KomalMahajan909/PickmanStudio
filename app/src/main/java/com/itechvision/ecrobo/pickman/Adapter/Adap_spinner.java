package com.itechvision.ecrobo.pickman.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Models.BatchPick.ProductList.ProductData;
import com.itechvision.ecrobo.pickman.Models.TruckPicking.ShippingCompanyData;
import com.itechvision.ecrobo.pickman.R;

import java.util.ArrayList;

public class Adap_spinner extends BaseAdapter {
    private Context context;
    private ArrayList<ShippingCompanyData> items;

    //public constructor
    public Adap_spinner(Context context, ArrayList<ShippingCompanyData> items) {
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
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.adap_spinner, parent, false);
        }


         TextView batch = (TextView) convertView.findViewById(R.id.text);
         batch.setText(items.get(position).getShipping_method()+" / "+items.get(position).getBatch_no());

         return convertView;
    }
}