package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.TruckBatchPickingActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by lenovo on 7/24/2019.
 */

public class TruckBatchOrderDetail {
    private String TAG = "TruckBatchOrderDetail";


    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {
        ArrayList<Map<String, String>> arr = new ArrayList<>();
        TruckBatchPickingActivity act = (TruckBatchPickingActivity) activity;


        if (list.size() > 0){
            JsonHash row = (JsonHash) list.get(0);
            Log.e(TAG, ">>>>>>>>>>>" + row);


            if (TruckBatchPickingActivity.getAction.equals("completeOrders")) {
                JsonArray list1 = row.getJsonArrayOrNull("batch_orders_complete");
                Log.e(TAG, ">>>>>>>>>>>11111111111111111" + list1);

                for (int i = 0; i < list1.size(); i++) {
                    JsonHash row1 = (JsonHash) list1.get(i);
                    HashMap<String, String> map = new HashMap<>();

                    map.put("order_no", row1.getStringOrNull("order_no"));
                    map.put("batch_id", row1.getStringOrNull("batch_id"));

                    Log.e(TAG, ">>>>>>>>>>>11111mappppppp    " + map);
                    arr.add(map);
                }
            }


           else if (TruckBatchPickingActivity.getAction.equals("currentBatch")) {
                JsonArray list1 = row.getJsonArrayOrNull("batch_orders_remaining");
                Log.e(TAG, ">>>>>>>>>>>11111111111111111" + list1);

                for (int i = 0; i < list1.size(); i++) {
                    JsonHash row1 = (JsonHash) list1.get(i);
                    HashMap<String, String> map = new HashMap<>();

                    map.put("order_no", row1.getStringOrNull("order_no"));
                    map.put("batch_id", row1.getStringOrNull("batch_id"));

                    Log.e(TAG, ">>>>>>>>>>>11111mappppppp    " + map);
                    arr.add(map);
                }
            }


           else if (TruckBatchPickingActivity.getAction.equals("allBatch")) {
                JsonArray list1 = row.getJsonArrayOrNull("total_orders_remaining");
                Log.e(TAG, ">>>>>>>>>>>11111111111111111" + list1);

                for (int i = 0; i < list1.size(); i++) {
                    JsonHash row1 = (JsonHash) list1.get(i);
                    HashMap<String, String> map = new HashMap<>();

                    map.put("order_no", row1.getStringOrNull("order_no"));
                    map.put("batch_id", row1.getStringOrNull("batch_id"));

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
