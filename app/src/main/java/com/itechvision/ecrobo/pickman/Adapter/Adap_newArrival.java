package com.itechvision.ecrobo.pickman.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.Arrival_ID_Check.Arrival_ID_list_data;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.Barcode_Check.Product_List_data;
import com.itechvision.ecrobo.pickman.R;

import java.util.ArrayList;

public class Adap_newArrival extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    String c;
    int ePos;
    ArrayList<Product_List_data> arrstate;

    public Adap_newArrival(Context context, ArrayList<Product_List_data> arrstate) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.arrstate = arrstate;
    }

    @Override
    public int getCount() {
        return arrstate.size() ;
    }

    @Override
    public Object getItem(int position) {
        return arrstate.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int arg0, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        ePos = arg0;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.adap_newarrival_list, null);
              holder = new ViewHolder();

              holder.pck_0 = (TextView) convertView.findViewById(R.id.pck_0);
              holder.pck_1 = (TextView)convertView.findViewById(R.id.pck_1);
              holder.pck_2 = (TextView)convertView.findViewById(R.id.pck_2);
              holder.pck_3 = (TextView)convertView.findViewById(R.id.pck_3);
              holder.pck_4 = (TextView)convertView.findViewById(R.id.pck_4);
              holder.pck_5 = (TextView)convertView.findViewById(R.id.pck_5);
              holder.ll_listdialog = (LinearLayout) convertView.findViewById(R.id.ll_listdialog);
              convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            holder.pck_0.setText(arrstate.get(arg0).getRsv_date());
            holder.pck_1.setText(arrstate.get(arg0).getCode());
            holder.pck_2.setText(arrstate.get(arg0).getComp_name());
            holder.pck_3.setText(arrstate.get(arg0).getQuantity());
            holder.pck_4.setText(arrstate.get(arg0).getLot());
            holder.pck_5.setText(arrstate.get(arg0).getExpiration_date());

        } catch (Exception e) {
             e.printStackTrace();
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
         return position;
    }

    @Override
    public int getViewTypeCount() {
         return getCount();
    }

    class ViewHolder {
        public LinearLayout ll_listdialog;
        public TextView pck_0,pck_1,pck_2,pck_3,pck_4,pck_5;
    }

}
