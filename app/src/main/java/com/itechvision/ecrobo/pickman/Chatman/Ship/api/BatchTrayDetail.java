package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.DBatchPickingCategoryActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.NewPickingActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 5/12/2019.
 */

public class BatchTrayDetail {
        String TAG = "BatchTrayDetail";

        public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

            List<Map<String, String>> data = new ArrayList<Map<String, String>>();

            DBatchPickingCategoryActivity act = (DBatchPickingCategoryActivity) activity;
            String count = "0";
            if (list.size() > 0){
                // collect all data from response
                JsonHash row = (JsonHash) list.get(0);
                count = row.getStringOrNull("count_pending_batch_trays");
                JsonArray list2 = row.getJsonArrayOrNull("include_products");
                for (int i=0; i< list2.size(); i++) {
                    JsonHash row2 = (JsonHash) list2.get(i);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("tray",row2.getStringOrNull("tray_no"));
                    map.put("quantity",row2.getStringOrNull("tray_rest_quantity"));
                    data.add(map);
                }
                act.updateBadge1(count);
                if(!count.equals("0")){
                Log.e(TAG,">>>>>>>>>>>>>>"+data);
                act.getBatchList(data);
                act.initiatePopup();}
                else {
                    Log.e(TAG,">>>>>>>>>>>>>>No dataaaa"+data);
                    String msg  = "No record found!";
                    valid(code, msg, list, params, activity);
                }
            } else {
                String msg  = "No record found!";
                valid(code, msg, list, params, activity);
            }
        }


        public void valid(String code, String message, JsonArray list,
                          HashMap<String, String> params, Activity activity) {
            U.beepError(activity, message);
        }

    }


