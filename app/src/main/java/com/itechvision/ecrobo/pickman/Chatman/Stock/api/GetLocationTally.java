package com.itechvision.ecrobo.pickman.Chatman.Stock.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Stock.InventoryActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2/26/2019.
 */

public class GetLocationTally {

    public static int listsize=0;
    String TAG = "GetLocationTally";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        InventoryActivity act = ((InventoryActivity) activity);
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        listsize=list.size();
        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            Map<String, String> map = new HashMap<String, String>();

            map.put("location", row.getStringOrNull("location"));
            map.put("barcode", row.getStringOrNull("barcode"));
            map.put("code", row.getStringOrNull("code"));
            map.put("quantity", row.getStringOrNull("quantity"));
            map.put("lot", row.getStringOrNull("lot"));
            map.put("qtyScanned", "0");
            Log.e("GetLocationTally",""+map);
            data.add(map);

        }
        act.startTimer();
        act.setStockList(data);
        act.setProc(act.PROC_BARCODE);
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        InventoryActivity act = ((InventoryActivity) activity);
        U.beepError(activity, message);
        act.setProc(act.PROC_LOCATION);
    }

}
