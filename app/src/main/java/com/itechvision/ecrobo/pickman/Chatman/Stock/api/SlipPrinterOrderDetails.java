package com.itechvision.ecrobo.pickman.Chatman.Stock.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.TruckBatchPickingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.SlipPrinterActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 8/8/2019.
 */

public class SlipPrinterOrderDetails {
    private String TAG = "SlipPrinterOrderDetails";


    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {
        ArrayList<Map<String, String>> arr = new ArrayList<>();
        SlipPrinterActivity act = (SlipPrinterActivity) activity;


        if (list.size() > 0){
            JsonHash row = (JsonHash) list.get(0);
            Log.e(TAG, ">>>>>>>>>>>" + row);
            String total = row.getStringOrNull("total_orders");
            String remaining = row.getStringOrNull("remaining_orders");


            if (SlipPrinterActivity.getAction.equals("total_list")) {
                JsonArray list1 = row.getJsonArrayOrNull("total_order_list");
                Log.e(TAG, ">>>>>>>>>>>11111111111111111" + list1);

                for (int i = 0; i < list1.size(); i++) {
                    JsonHash row1 = (JsonHash) list1.get(i);
                    HashMap<String, String> map = new HashMap<>();

                    map.put("order_no", row1.getStringOrNull("order_no"));
                    map.put("code", row1.getStringOrNull("code"));

                    Log.e(TAG, ">>>>>>>>>>>11111mappppppp    " + map);
                    arr.add(map);
                }
            }


            else if (SlipPrinterActivity.getAction.equals("remaining_list")) {
                JsonArray list1 = row.getJsonArrayOrNull("unprint_order_list");
                Log.e(TAG, ">>>>>>>>>>>11111111111111111" + list1);

                for (int i = 0; i < list1.size(); i++) {
                    JsonHash row1 = (JsonHash) list1.get(i);
                    HashMap<String, String> map = new HashMap<>();

                    map.put("order_no", row1.getStringOrNull("order_no"));
                    map.put("code", row1.getStringOrNull("code"));

                    Log.e(TAG, ">>>>>>>>>>>11111mappppppp    " + map);
                    arr.add(map);
                }
            }

            if(!arr.isEmpty())
                act.setInfo(arr);

        }
    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }
}

