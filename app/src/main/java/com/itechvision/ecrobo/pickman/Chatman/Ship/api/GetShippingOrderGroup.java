package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;

import com.itechvision.ecrobo.pickman.Chatman.Ship.NewShippingGroupActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 10/26/2019.
 */

public class GetShippingOrderGroup {
    String TAG = "GetShippingOrder";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        final NewShippingGroupActivity act = (NewShippingGroupActivity) activity;
        String name = null;
        String tracking = null;
        String order_id = null;
        Long qtyCount = 0L;

        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);

            JsonArray list2 = row.getJsonArrayOrNull("data");

            if (list2 != null) {
                for (int j = 0; j < list2.size(); j++) {
                    JsonHash row2 = (JsonHash) list2.get(j);
                    Map<String, String> map = new HashMap<String, String>();


                    map.put("name2",row2.getStringOrNull("name2"));
                    map.put("order_no",row2.getStringOrNull("order_no"));


                    data.add(map);
                }
            }
        }


		/*act._sts(id.trackingNumber, tracking);*/ //not filled tracking number it filled through scanner
        act.setOrderList(data);
//        NewShippingGroupActivity.count=0;
        U.beepSuccess();
        act.startTimer();
        act.setOrderbadge(data);

    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }

}
