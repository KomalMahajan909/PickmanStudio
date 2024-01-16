package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.DBatchPickingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.DBatchPickingCategoryActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 4/24/2019.
 */

public class GetIncludeDbatchList {
    String TAG = "GetIncludeDbatchList";


    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        Log.e(TAG, " " + list);
        final DBatchPickingCategoryActivity act = (DBatchPickingCategoryActivity) activity;

        String count ="";
        Log.e(TAG, " >>>>>>>>>>>>>>>>>");
        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            Log.e(TAG,"<<<<<<<<<<<<<<<<<<<"+row);
            JsonArray products = row.getJsonArrayOrNull("include_products");
             count=  row.getStringOrNull("count_pending_batch_trays");
            Log.e(TAG,"<<<<<<<<<<<<<<<<<<<11111111111111111"+products);
            if (products != null) {
                for (int j=0;j<products.size();j++){
                    JsonHash row1 = (JsonHash) products.get(j);
                    Log.e(TAG,"<<<<<<<<<<<<<<<<<<<2222222222222"+row1);
                    HashMap<String, String> map = new HashMap<>();

                    map.put("tray_quantity",row1.getStringOrNull("tray_rest_quantity"));
                    map.put("tray",row1.getStringOrNull("tray_no"));

                    if(!row1.getStringOrNull("tray_rest_quantity").equals("0"))
                    data.add(map);
                    Log.e(TAG,"<<<<<<<<<<<<<<<<<<<3333333333333"+data);
                }
            }}
        if (!data.isEmpty()) {
       act.setProc(DBatchPickingCategoryActivity.PROC_TRAYNO);
       act.updateBadge1(count);
       act.getProductList(data);}
       else
            U.beepError(activity, "no results found");

    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {

        U.beepError(activity, message);
        ((DBatchPickingCategoryActivity) activity).setProc(DBatchPickingCategoryActivity.PROC_ORDERNO);

    }
}

