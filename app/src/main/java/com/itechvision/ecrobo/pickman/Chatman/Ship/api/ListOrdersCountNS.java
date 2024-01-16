package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;

import com.itechvision.ecrobo.pickman.Chatman.Ship.NewShippingGroupActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;

/**
 * Created by lenovo on 10/26/2019.
 */

public class ListOrdersCountNS {
    String TAG = "ListOrdersCountNS";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        NewShippingGroupActivity act = (NewShippingGroupActivity) activity;
        String all_order_count = "0";
        if (list.size() > 0) {
            // collect all data from response
            JsonHash row = (JsonHash) list.get(0);
            all_order_count = row.getStringOrNull("all_order_count");
        }
        act.updateBadge1(all_order_count);
//        NewShippingGroupActivity.count=0;
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }
}
