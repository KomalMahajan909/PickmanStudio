package com.itechvision.ecrobo.pickman.Adapter.ShippingSpecification;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.R;

import java.util.ArrayList;

public class BoxSelectAdapter extends RecyclerView.Adapter<BoxSelectAdapter.ViewHolder> {

    private String mData[];
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public BoxSelectAdapter(Context context, String data[], ItemClickListener onclick) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        mClickListener = onclick;
        this.context =context;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.box_button_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.myTextView.setText(mData[position]);
        if((position)/3 == 0){
            holder.myTextView.setBackground(context.getResources().getDrawable(R.drawable.rounded_btn_blue));
        }
        else if((position)/3 == 1){
            holder.myTextView.setBackground(context.getResources().getDrawable(R.drawable.red_rounded_button));
        }
        else if((position)/3== 2){
            holder.myTextView.setBackground(context.getResources().getDrawable(R.drawable.rounded_btn_green));
        }
        else if((position)/3== 3){
            holder.myTextView.setBackground(context.getResources().getDrawable(R.drawable.rounded_btn_blue));
        }
        else if((position)/3== 4){
            holder.myTextView.setBackground(context.getResources().getDrawable(R.drawable.red_rounded_button));
        }
        else
            holder.myTextView.setBackground(context.getResources().getDrawable(R.drawable.rounded_btn_green));

        holder.myTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) mClickListener.onItemClick( position);

            }
        });
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.info_text);

        }

    }

 /*   // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }*/


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick( int position );
    }
}


