package com.itechvision.ecrobo.pickman.Chatman.IrisScreens.Api;

import android.app.Activity;

import com.itechvision.ecrobo.pickman.Chatman.IrisScreens.IrisPickingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.NewPickingActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;

/**
 * Created by lenovo on 2/6/2019.
 */

public class ListOrdersIrisCount {

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        IrisPickingActivity act = (IrisPickingActivity) activity;
        String all_order_count = "0";
        if (list.size() > 0) {
            // collect all data from response
            JsonHash row = (JsonHash) list.get(0);
            all_order_count = row.getStringOrNull("all_order_count");
        }
        act.updateBadge1(all_order_count);
    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }
}
