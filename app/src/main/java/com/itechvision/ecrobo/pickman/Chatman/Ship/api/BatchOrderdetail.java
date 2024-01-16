package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.BatchBoxActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 4/16/2019.
 */

public class BatchOrderdetail {

    String TAG = "BatchOrderdetail";


    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        List<Map<String, String>> cList = new ArrayList<Map<String, String>>();

        Map<String, String> map = new HashMap<String, String>();

        Log.e(TAG, " " + list);
        final BatchBoxActivity act = (BatchBoxActivity) activity;


        for (int i=0;i<list.size();i++){

            Log.e(TAG, " >>>>>>>>>>>>>>>>>");
            // collect all data from response
            JsonHash row = (JsonHash) list.get(i);

            HashMap<String, String> data = new HashMap<>();


            // insert data into desire variables and fields for further processing.

            data.put("order_id", row.getStringOrNull("order_id"));
            data.put("box_no", row.getStringOrNull("box_no"));

            cList.add(data);


            Log.e(TAG, "category0Listttttt   " + cList + "");

        }
                    act.getProductList(cList);
                    act.updateBadge3(cList.size()+"");


    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {

        U.beepError(activity, message);

    }

}
