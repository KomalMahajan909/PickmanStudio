package com.itechvision.ecrobo.pickman.Adapter.LoopShip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.GetOrder.GetorderData;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.GetOrder.PendingData;
import com.itechvision.ecrobo.pickman.R;

import java.util.ArrayList;

public class Adap_loopShipOrderPendinglist extends BaseAdapter {
    private Context context;
    private ArrayList<PendingData> items;

    //public constructor
    public Adap_loopShipOrderPendinglist(Context context, ArrayList<PendingData> items) {
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
                    inflate(R.layout.work_picking_row, parent, false);
        }

        TextView wrk_pic_0 = (TextView) convertView.findViewById(R.id.wrk_pic_0);
        TextView wrk_pic_1 = (TextView) convertView.findViewById(R.id.wrk_pic_1);
        TextView wrk_pic_2 = (TextView) convertView.findViewById(R.id.wrk_pic_2);
        TextView wrk_pic_3 = (TextView) convertView.findViewById(R.id.wrk_pic_3);

        //sets the text for item name and item description from the current item object
        wrk_pic_0.setText(items.get(position).getCode());
        wrk_pic_1.setText(items.get(position).getProduct_qty());
        wrk_pic_2.setText(items.get(position).getQuantity());
        wrk_pic_3.setText(items.get(position).getCategory());
        return convertView;
    }
}