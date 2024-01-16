package com.itechvision.ecrobo.pickman.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Models.BatchPick.ProductList.ProductData;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;

import java.util.ArrayList;

public class Adap_productlist extends BaseAdapter {
    private Context context;
    private ArrayList<ProductData> items;

    //public constructor
    public Adap_productlist(Context context, ArrayList<ProductData> items) {
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
                    inflate(R.layout.adap_productlist, parent, false);
        }


         TextView batch = (TextView) convertView.findViewById(R.id.batch);
         TextView qty = (TextView) convertView.findViewById(R.id.qty);

         batch.setText(items.get(position).getBatch_detail_no());
         qty.setText(items.get(position).getQuantity());

         return convertView;
    }
}