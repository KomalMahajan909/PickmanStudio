package com.itechvision.ecrobo.pickman.Chatman.Arrivals.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ReturnStockActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
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

public class GetReturnTrack {List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    String TAG = "GetReturnTrack";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        ReturnStockActivity act = (ReturnStockActivity) activity;

        String order_id = null;
        String flag = "";

        Long qtyCount = 0L;
        if (list.size() > 0) {
            JsonHash row = (JsonHash) list.get(0);

            JsonArray list2 = row.getJsonArrayOrNull("order");
            // collect all data from response
            JsonHash row2 = (JsonHash) list2.get(0);
            order_id = row2.getStringOrNull("order_id");
            if(row2.containsKey("sendback_flag"))
                flag = row2.getStringOrNull("sendback_flag");


        }
        act.startTimer();
        U.beepNext();
        act.orderID = order_id;

        act._sts(R.id.orderId,order_id);


        if(flag.equalsIgnoreCase("1"))
        {
            act.showReturndialog();
        }
        else {
            if (act.returningnum.isChecked())
                act.setProc(ReturnStockActivity.PROC_PRICE);
            else
                act.setProc(ReturnStockActivity.PROC_RETURN_REASON);
        }




    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }

}
