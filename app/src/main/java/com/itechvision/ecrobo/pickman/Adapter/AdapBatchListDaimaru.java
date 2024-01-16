package com.itechvision.ecrobo.pickman.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.BatchListModel.BatchListData;
import com.itechvision.ecrobo.pickman.R;

import java.util.ArrayList;

public class AdapBatchListDaimaru extends RecyclerView.Adapter<AdapBatchListDaimaru.ViewHolder> {

    ArrayList<BatchListData> arrstate;
    onClickCallback onClick;
    int eop = 0;

    public interface onClickCallback {
        void onClick(int position);
    }

    public AdapBatchListDaimaru(Context context, ArrayList<BatchListData> arrstate, onClickCallback onclick) {
        this.arrstate = arrstate;
        this.onClick = onclick;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.adap_batchlist_daimaru, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            if (arrstate.get(position).getBatch_status().equalsIgnoreCase("0")){
                //   1- complete, 2- working,0-pending

                holder.status.setText("未");
                holder.status.setBackgroundColor(Color.parseColor("#777777"));
            }else  if(arrstate.get(position).getBatch_status().equalsIgnoreCase("1")){
                holder.status.setText("中");
                holder.status.setBackgroundColor(Color.parseColor("#f0ad4e"));
            }else if(arrstate.get(position).getBatch_status().equalsIgnoreCase("2")){
                holder.status.setText("了");
                holder.status.setBackgroundColor(Color.parseColor("#e60000"));

            }else{
                holder.status.setText("済");
                holder.status.setBackgroundColor(Color.parseColor("#5cb85c"));
            }
            holder.batchnumber.setText(arrstate.get(position).getBatch_no());

            if(arrstate.get(position).getBatch_remarks().equalsIgnoreCase("1")) {
                holder.remark.setText("あり");
            }
            else
                holder.remark.setText("なし");


            holder.user.setText(arrstate.get(position).getUsername());
            holder.user.setSelected(true);


            holder.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onClick.onClick(position );
                }
            });
            holder.status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onClick.onClick(position );
                }
            });
        }catch (Exception e) {
        }
    }



    @Override
    public int getItemCount() {
        return arrstate.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private Button status;
        private TextView batchnumber;
        private TextView remark;
        private TextView user;
        private LinearLayout main;

        public ViewHolder(View view) {
            super(view);

            main = (LinearLayout)view.findViewById(R.id.main);
            status = (Button) view.findViewById(R.id.status);
            batchnumber = (TextView) view.findViewById(R.id.batchno);
            remark = (TextView) view.findViewById(R.id.remark);
            user = (TextView) view.findViewById(R.id.user);

        }
    }

    public void Clear() {
        this.arrstate.clear();
    }


}
