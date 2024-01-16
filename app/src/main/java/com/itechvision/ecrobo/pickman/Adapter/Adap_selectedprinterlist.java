package com.itechvision.ecrobo.pickman.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Models.BatchPick.ProductList.ProductData;
import com.itechvision.ecrobo.pickman.Models.NewPrinter.SelectedprinterList.selectedlistdata;
import com.itechvision.ecrobo.pickman.R;

import java.util.ArrayList;

public class Adap_selectedprinterlist extends BaseAdapter {
    private Context context;
    private ArrayList<selectedlistdata> items;

    //public constructor
    public Adap_selectedprinterlist(Context context, ArrayList<selectedlistdata> items) {
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
                    inflate(R.layout.adap_slectedprinterlist, parent, false);
        }

         // get the TextView for item name and item description
        TextView categories = (TextView) convertView.findViewById(R.id.categoryname);
        TextView templete = (TextView) convertView.findViewById(R.id.tempname);
        TextView printername = (TextView) convertView.findViewById(R.id.Printername);
        TextView machinename = (TextView) convertView.findViewById(R.id.mechname);

        categories.setText(items.get(position).getAp_form_category_name());
        templete.setText(items.get(position).getAp_form_name());
        printername.setText(items.get(position).getPrinter_name());
        machinename.setText(items.get(position).getMachine_id());

         return convertView;
    }
}