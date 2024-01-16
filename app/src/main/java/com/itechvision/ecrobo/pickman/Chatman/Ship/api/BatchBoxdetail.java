package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.DBatchPickingActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 4/22/2019.
 */

public class BatchBoxdetail {

    String TAG = "BatchBarcodeCheck";


    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        List<Map<String, String>> boxlist = new ArrayList<Map<String, String>>();

        Log.e(TAG, " " + list);
        final DBatchPickingActivity act = (DBatchPickingActivity) activity;


        Log.e(TAG, " >>>>>>>>>>>>>>>>>");
        for(int i= 0;i< list.size();i++) {
            // collect all data from response
            JsonHash row = (JsonHash) list.get(i);

            HashMap<String, String> data = new HashMap<>();


            // insert data into desire variables and fields for further processing.


            data.put("box_no",row.getStringOrNull("box_no"));
            data.put("box_status",row.getStringOrNull("box_status"));
            data.put("order_id", row.getStringOrNull("order_id"));
           boxlist.add(data);
        }



        act.getProductList(boxlist);



    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {

        U.beepError(activity, message);

    }
}
