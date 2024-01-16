package com.itechvision.ecrobo.pickman.Adapter.BoxCashRegister;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxProductclickList.BoxProductlistclick;
import com.itechvision.ecrobo.pickman.R;

import java.util.List;



public class PackingBoxlistclickAdapter extends RecyclerView.Adapter<PackingBoxlistclickAdapter.ViewHolder > {
     List<BoxProductlistclick> list;
    private Context context;



    public PackingBoxlistclickAdapter(Context context, List<BoxProductlistclick> list ) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.adap_productbox_clicklist, parent, false);
        PackingBoxlistclickAdapter.ViewHolder viewHolder = new PackingBoxlistclickAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

           /*holder.bindData(list.get(position));*/

            //in some cases it will prevent unwanted situations

            // if true, your checkbox will be selected , else unselected

            holder.boxno.setText(list.get(position).getBox_no());
            holder.code.setText(list.get(position).getCode());
            holder.code.setSelected(true);
            holder.pname.setText(list.get(position).getProduct_name());
            holder.pname.setSelected(true);
            holder.qty.setText(list.get(position).getQuantity());
//            holder.status.setText(list.get(position).getStatus());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

//        private TextView status;
        private TextView boxno;
        private TextView code;
        private TextView pname;
        private TextView qty;


        public ViewHolder(View view) {
            super(view);
//            status = (TextView) view.findViewById(R.id.status);
            boxno = (TextView) view.findViewById(R.id.no);
            pname = (TextView) view.findViewById(R.id.pname);
            code = (TextView) view.findViewById(R.id.pcode);
            qty = (TextView) view.findViewById(R.id.qty);
        }
    }
        }






