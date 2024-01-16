package com.itechvision.ecrobo.pickman.Adapter.LoopShip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Models.DirectArrival.DirArrivalListData;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.GetOrder.GetorderData;
import com.itechvision.ecrobo.pickman.R;

import java.util.ArrayList;

public class Adap_loopShipOrderlist extends BaseAdapter {
    private Context context;
    private ArrayList<GetorderData> items;

    //public constructor
    public Adap_loopShipOrderlist(Context context, ArrayList<GetorderData> items) {
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
                    inflate(R.layout.order_list_row, parent, false);
        }



        TextView NO = (TextView) convertView.findViewById(R.id.odr_pic_0);
        TextView order_no = (TextView) convertView.findViewById(R.id.odr_pic_1);
        TextView name = (TextView) convertView.findViewById(R.id.odr_pic_2);
        TextView batch_name = (TextView) convertView.findViewById(R.id.odr_pic_3);


        //sets the text for item name and item description from the current item object
        NO.setText(String.valueOf(position + 1));
        order_no.setText(items.get(position).getOrder_no());
        name.setText(items.get(position).getName());
        batch_name.setText(items.get(position).getBatch_detail_name());
        return convertView;
    }
}