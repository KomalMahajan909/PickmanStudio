package com.itechvision.ecrobo.pickman.Util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.itechvision.ecrobo.pickman.R;


public class ActionBar extends RelativeLayout {
    private Context mContext;

    ActionBar actionBar;
    public ActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mContext = context;

        LayoutInflater mInflater =  (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout rl = (RelativeLayout) mInflater.inflate(R.layout.action_bar, null);

        addView(rl);

    }}
