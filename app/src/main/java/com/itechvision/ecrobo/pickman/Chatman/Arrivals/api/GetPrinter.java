package com.itechvision.ecrobo.pickman.Chatman.Arrivals.api;

import android.app.Activity;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ArrivalActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;

/**
 * Created by lenovo on 12/13/2018.
 */

public class GetPrinter {

    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {
        JsonHash row = (JsonHash) list.get(0);
//        ((ArrivalActivity) activity).setPrinter(row.getStringOrNull("host"));
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);

        ((ArrivalActivity) activity).setProc(ArrivalActivity.PROC_BARCODE);
    }
}
