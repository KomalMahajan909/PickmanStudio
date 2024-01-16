package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;

import android.content.Context;

import android.util.Log;


import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.NewPickingActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckOrder {
    public static String getbarcode= null;
    public static String getlot= null;

    public static int track=0;
    public static int group=0;

    public static boolean permission = true;
    public  static String remark= "";

    String TAG = "GetPiking";
    Context ctx ;
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();


    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        final NewPickingActivity act = (NewPickingActivity) activity;

        String group_order= null;
        String group_no =null;


        if (list.size() > 0) {
            Log.e(TAG, ">>>>>>>>>>>>>>>111     "+list);
            // collect all data from response
            group = 0;
            JsonHash row = (JsonHash) list.get(0);
            Log.e(TAG, ">>>>>>>>>>>>>>>2222222222     "+row);


            if (row.containsKey("group")) {
                Log.e(TAG, ">>>>>>>>>>>>>>>33333333333     "+row);

                group_order = row.getStringOrNull("group");
                if (group_order.equals("1")) {

                    group = 1;
                    group_no = row.getStringOrNull("group_order_no");
                    Log.e(TAG, ">>>>>>>>>>>>>>>666666666666    "+group_no);
                    act.groupOrderno = group_no;
                }
            }
            if(group == 1 && !group_no.equals("")){
                Log.e(TAG, ">>>>>>>>>>>>>>>7777777777    "+group);
                act.GetRowGroup();
            }
            else{
                Log.e(TAG, ">>>>>>>>>>>>>>>8888888888    "+group);
                act.GetRow();
            }
        }
    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        NewPickingActivity act = (NewPickingActivity) activity;

        switch (BaseActivity.getOrderInfoBy()) {
            case SettingActivity.ORDER_ID:
                Log.e(TAG, "nextProcessssssssss_ORDER_ID");
                act.setProc(NewPickingActivity.PROC_ORDERID);
        break;
        case SettingActivity.ORDER_NUMBER:
        Log.e(TAG, "nextProcessssssssss_ORDER_NUMBER");
        act._sts(R.id.orderNo, "");
        act.setProc(NewPickingActivity.PROC_ORDER_NO);
        break;
        case SettingActivity.ORDER_TRACKING_NO:
        Log.e(TAG, "nextProcessssssssss_ORDER_TRACKING_NO");
        act.setProc(NewPickingActivity.PROC_TRACKID);
        break;
    }
    }
}
