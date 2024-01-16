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

public class ShippingOrderDetailGroup {
    String TAG = "ShippingOrderDetail";

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


//                    map.put("name2",row2.getStringOrNull("name2"));
//                    map.put("order_no",row2.getStringOrNull("order_no"));

//                        map.put("order_id", row2.getStringOrNull("order_id"));
//                        map.put("location", row2.getStringOrNull("location"));
                    map.put("quantity", row2.getStringOrNull("quantity"));
                    map.put("squantity", row2.getStringOrNull("squantity"));
                    map.put("barcode", row2.getStringOrNull("barcode"));
                    map.put("code",row2.getStringOrNull("code"));
////                    map.put("processedCnt", "0");
////                    if (BaseActivity.getLotPress()==true)
////                        map.put("lotNo",row2.getStringOrNull("lot"));
//                        qtyCount += Long.parseLong(row2.getStringOrNull("quantity"));
//                    }
                    data.add(map);
                }
            }
        }


		/*act._sts(id.trackingNumber, tracking);*/ //not filled tracking number it filled through scanner
//        act.setOrderList(data);

        U.beepSuccess();
        act.startTimer();
        act.initiatePopup(data);
//        NewShipActivity.count=0;
    }



    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }

}
