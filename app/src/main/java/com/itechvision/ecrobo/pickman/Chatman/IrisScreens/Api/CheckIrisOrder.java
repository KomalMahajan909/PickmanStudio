package com.itechvision.ecrobo.pickman.Chatman.IrisScreens.Api;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.IrisScreens.IrisPickingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.NewPickingActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckIrisOrder {
    public static String getbarcode= null;
    public static String getlot= null;

    public static int track=0;
    public static int group=0;

    public static boolean permission = true;
    public  static String remark= "";

    String TAG = "CheckIrisOrder";


    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        final IrisPickingActivity act = (IrisPickingActivity) activity;

        String group_order= null;
        String group_no =null;


        if (list.size() > 0) {
            Log.e(TAG, ">>>>>>>>>>>>>>>111     "+list);
            // collect all data from response
            group = 0;
            JsonHash row = (JsonHash) list.get(0);
            Log.e(TAG, ">>>>>>>>>>>>>>>2222222222     "+row);



                Log.e(TAG, ">>>>>>>>>>>>>>>8888888888    "+group);
                act.GetRow();

        }
    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        IrisPickingActivity act = (IrisPickingActivity) activity;

        switch (BaseActivity.getOrderInfoBy()) {
            case SettingActivity.ORDER_ID:
                Log.e(TAG, "nextProcessssssssss_ORDER_ID");
                act.setProc(IrisPickingActivity.PROC_ORDERID);
        break;
        case SettingActivity.ORDER_NUMBER:
        Log.e(TAG, "nextProcessssssssss_ORDER_NUMBER");
        act._sts(R.id.orderNo, "");
        act.setProc(IrisPickingActivity.PROC_ORDER_NO);
        break;
        case SettingActivity.ORDER_TRACKING_NO:
        Log.e(TAG, "nextProcessssssssss_ORDER_TRACKING_NO");
        act.setProc(IrisPickingActivity.PROC_TRACKID);
        break;
    }
    }
}
