package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.TruckBatchActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.TruckBatchPickingActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 7/25/2019.
 */

public class TruckBatchSkipDetails {
    private String TAG = "TruckBatchSkipDetails";


    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {
        ArrayList<Map<String, String>> arr = new ArrayList<>();
        TruckBatchPickingActivity act = (TruckBatchPickingActivity) activity;


        if (list.size() > 0){
            JsonHash row = (JsonHash) list.get(0);
            Log.e(TAG, ">>>>>>>>>>>" + row);


              if(row.containsKey("order_list")) {
                  JsonArray list1 = row.getJsonArrayOrNull("order_list");
                  Log.e(TAG, ">>>>>>>>>>>11111111111111111" + list1);

                      for (int i = 0; i < list1.size(); i++) {
                          JsonHash row1 = (JsonHash) list1.get(i);
                          HashMap<String, String> map = new HashMap<>();

                          map.put("order_no", row1.getStringOrNull("order_no"));
                          map.put("code", row1.getStringOrNull("code"));
                          map.put("location", row1.getStringOrNull("location"));
                          map.put("quantity", row1.getStringOrNull("quantity"));
                          map.put("rest_quantity", row1.getStringOrNull("rest_quantity"));

                          Log.e(TAG, ">>>>>>>>>>>11111mappppppp    " + map);
                          arr.add(map);
                      }

                  if (row.containsKey("skip_cancel_orders")) {
                      JsonArray list2 = row.getJsonArrayOrNull("skip_cancel_orders");
                      Log.e(TAG, ">>>>>>>>>>>11111111111111111" + list2);

                      for (int i = 0; i < list2.size(); i++) {
                          JsonHash row1 = (JsonHash) list2.get(i);
                          HashMap<String, String> map = new HashMap<>();

                          map.put("order_no", row1.getStringOrNull("order_no"));
                          map.put("code", row1.getStringOrNull("code"));
                          map.put("location", row1.getStringOrNull("location"));
                          map.put("quantity", row1.getStringOrNull("quantity"));
                          map.put("pick_quantity", row1.getStringOrNull("inspection_batch"));

                          Log.e(TAG, ">>>>>>>>>>>11111mappppppp    " + map);
                          arr.add(map);
                      }
                  }
                  if (!arr.isEmpty() && arr.size()>0){
                      act.setDialog(arr);
                  TruckBatchActivity.cancelList.addAll(arr);}
                  else {
                      U.beepFinish(act, null);
                      act.complete = true;
                      act.returnback();
                  }
              }
              else {
                      U.beepFinish(act, null);
                      act.complete = true;
                      act.returnback();
              }
        }
    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }
}
