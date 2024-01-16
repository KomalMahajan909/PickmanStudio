package com.itechvision.ecrobo.pickman.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Models.BatchPick.BatchpickResponse_result;
import com.itechvision.ecrobo.pickman.R;

import java.util.ArrayList;


public class AdapBatch_tas extends RecyclerView.Adapter<AdapBatch_tas.ViewHolder> {
    int flag = 0, ePos;
    ViewHolder view;
    private Context context;
    ArrayList<BatchpickResponse_result> arrstate;
    ViewHolder holder;
    public boolean filter = false;
    boolean edit = false;
    String type;
    onClickCallback onClick;
     Dialog dialog_Forgot;

    public interface onClickCallback {
        void onClick(int position);
    }

    public AdapBatch_tas(Context context, ArrayList<BatchpickResponse_result> arrstate, onClickCallback onclick) {
        this.context = context;
        this.arrstate = arrstate;
        this.onClick = onclick;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.adap_newbatchpick, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
         try {
            if (arrstate.get(position).getBatch_status().equalsIgnoreCase("0")){
                //   1- complete, 2- working,0-pending
                if(arrstate.get(position).getAdmin_id().equalsIgnoreCase("")) {
                    //   1- complete, 2- working,0-pending
                    holder.status.setText("未");
                    holder.status.setBackgroundColor(Color.parseColor("#777777"));
                }
                else {
                    holder.status.setText("中");
                    holder.status.setBackgroundColor(Color.parseColor("#f0ad4e"));
                }
            }else  if(arrstate.get(position).getBatch_status().equalsIgnoreCase("1")){
                holder.status.setText("中");
                holder.status.setBackgroundColor(Color.parseColor("#f0ad4e"));
            }else if(arrstate.get(position).getBatch_status().equalsIgnoreCase("3")){
                holder.status.setText("欠");
                holder.status.setBackgroundColor(Color.parseColor("#e60000"));

            }else{
                holder.status.setText("済");
                holder.status.setBackgroundColor(Color.parseColor("#5cb85c"));
            }
            holder.noumber.setText(arrstate.get(position).getBatch_no());
            holder.batchtype.setText(arrstate.get(position).getBatch_type());
            holder.qty.setText(arrstate.get(position).getBatch_order_count());
            holder.sku.setText(arrstate.get(position).getSku_count());

            holder.status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClick(position );
                }
            });
        }catch (Exception e){

        }

          /*  holder.addcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (arrstate.get(position).getCartkey().equalsIgnoreCase("")) {
                        onClick.onClick(position, "Addcart", holder.qty.getText().toString());
                    } else {
                        onClick.onClick(position, "updatecart", holder.qty.getText().toString());
                    }
                }
            });
*/

    }


    @Override
    public int getItemCount() {
        return arrstate.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private Button status;
        private TextView noumber;
        private TextView batchtype;
        private TextView qty;
        private TextView sku;

        public ViewHolder(View view) {
            super(view);

           status = (Button) view.findViewById(R.id.status);
           noumber = (TextView) view.findViewById(R.id.noumber);
           batchtype = (TextView) view.findViewById(R.id.batchtype);
           qty = (TextView) view.findViewById(R.id.qty);
           sku = (TextView) view.findViewById(R.id.sku);

        }
    }

    public void Clear() {
        this.arrstate.clear();
    }


}
