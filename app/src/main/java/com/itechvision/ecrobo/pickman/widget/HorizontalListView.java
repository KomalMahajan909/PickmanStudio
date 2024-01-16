package com.itechvision.ecrobo.pickman.widget;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.R;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 11/4/2019.
 */

public class HorizontalListView extends RecyclerView.Adapter {
    private List<? extends Map<String, Object>> mData;

    public HorizontalListView(ListViewItems data) {
        mData = data.getData();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.student_list_row, parent, false);
//        return new RecyclerView.ViewHolder(itemView);
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
