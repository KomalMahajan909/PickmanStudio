package com.itechvision.ecrobo.pickman.Chatman.Arrivals.api;

import android.app.Activity;

import com.itechvision.ecrobo.pickman.Chatman.PickWork.NewReturnStock.NewReturnStockActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 4/12/2019.
 */

public class GetReturnTracka {List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    String TAG = "GetReturnTracka";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        NewReturnStockActivity act = (NewReturnStockActivity) activity;

        String order_id = null;
        Long qtyCount = 0L;
        if (list.size() > 0) {
            JsonHash row = (JsonHash) list.get(0);

            JsonArray list2 = row.getJsonArrayOrNull("order");
            // collect all data from response
            JsonHash row2 = (JsonHash) list2.get(0);
            order_id = row2.getStringOrNull("order_no");

        }
            act.startTimer();
            U.beepNext();
            act._sts(R.id.orderId,order_id);

            act.setProc(NewReturnStockActivity.PROC_PRICE);


    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }

}
