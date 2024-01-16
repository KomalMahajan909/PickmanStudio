package com.itechvision.ecrobo.pickman.Chatman.Stock.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Stock.MoveStockActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 1/10/2019.
 */

public class GetLocationStock {
    public static int listsize=0;
    String TAG = "GetLocationStock";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        MoveStockActivity act = ((MoveStockActivity) activity);
        MoveStockActivity.count =0;
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Log.e("GetLocationTally",""+list.size());
        listsize=list.size();

        act.setProc(act.PROC_BARCODE);
        U.beepNext();
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((MoveStockActivity) activity).setProc(MoveStockActivity.PROC_SOURCE);
    }

}
