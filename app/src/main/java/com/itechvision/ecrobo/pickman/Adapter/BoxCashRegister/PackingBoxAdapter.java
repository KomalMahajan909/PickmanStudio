package com.itechvision.ecrobo.pickman.Adapter.BoxCashRegister;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.Packing.PackListData;
import com.itechvision.ecrobo.pickman.R;

import java.util.List;

public class PackingBoxAdapter  extends RecyclerView.Adapter<PackingBoxAdapter.ViewHolder >{
    OnCheckBoxClick onCheckBoxClick;
    OnlistClick onlistClick;
    List<PackListData> list;
    private Context context;



    public PackingBoxAdapter(Context context, List<PackListData> list, OnCheckBoxClick onCheckBoxClick ,OnlistClick onlistClick ) {
        this.context = context;
        this.list = list;
        this.onCheckBoxClick = onCheckBoxClick;
        this.onlistClick = onlistClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.adap_packing_box_list_row, parent, false);
        PackingBoxAdapter.ViewHolder viewHolder = new PackingBoxAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PackingBoxAdapter.ViewHolder holder, final int position) {

        /*holder.bindData(list.get(position));*/

        //in some cases it will prevent unwanted situations
        holder.checkbox.setOnCheckedChangeListener(null);

        // if true, your checkbox will be selected , else unselected
        holder.checkbox.setChecked(list.get(position).isChecked());

        holder.boxno.setText(list.get(position).getBox_no());
        holder.boxcode.setText(list.get(position).getEms_box_code());
//            holder.status.setText(list.get(position).getStatus());


        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(holder.getAdapterPosition()).setChecked(isChecked);
                onCheckBoxClick.onCheckboxItemClickListener(list.get(position),isChecked);
            }
        });

        holder.boxno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onlistClick.OnlistClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //        private TextView status;
        private TextView boxno;
        private TextView boxcode;
        private CheckBox checkbox;

        public ViewHolder(View view) {
            super(view);
//            status = (TextView) view.findViewById(R.id.status);
            boxno = (TextView) view.findViewById(R.id.boxno);
            boxcode = (TextView) view.findViewById(R.id.box_code);
            checkbox = (CheckBox) view.findViewById(R.id.checkbox);
        }

        public void bindData(PackListData cashList) {
            if (!cashList.isChecked()) {
                checkbox.setChecked(false);
            }
            else
                checkbox.setChecked(true);
        }
    }
    public interface OnCheckBoxClick {
        void onCheckboxItemClickListener(PackListData val,boolean isChecked);

    }

    public interface OnlistClick {
        void OnlistClick( int postion);

    }

/*    public void Clear() {
        this.list.clear();
    }*/
}



