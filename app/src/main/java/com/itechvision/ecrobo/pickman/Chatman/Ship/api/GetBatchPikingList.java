package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.BatchPickingOrder;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 10/30/2018.
 */

public class GetBatchPikingList {
    String TAG = "GetNewBatchPikingList";


    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        ArrayList<String> inspectionstatus = new ArrayList<String>();
        JsonArray result1;
        BatchPickingOrder act = (BatchPickingOrder) activity;
        for (int i = 0; i < list.size(); i++) {

            JsonHash row1 = (JsonHash) list.get(i);


//                    Log.e("GetNewBatchPikingList",">>>>>>>>>>>>>>>Batchno."+row.getStringOrNull("batch_no"));
            result1 = row1.getJsonArrayOrNull("batchlist");

            for (int j = 0; j < result1.size(); j++) {

                JsonHash row = (JsonHash) result1.get(j);
                Map<String, String> map = new HashMap<String, String>();
                map.put("batch_no", row.getStringOrNull("batch_no"));

                map.put("batch_id", row.getStringOrNull("batch_id"));

                map.put("batch_order_count", row.getStringOrNull("batch_order_count"));


                map.put("batch_type", row.getStringOrNull("batch_type"));

                String sku = row.getStringOrNull("sku_count");
                if (sku.equals("") || sku.equals(null)) {
                    sku = "0";
                }
                map.put("sku_count", sku);
                inspectionstatus.add(row.getStringOrNull("batch_status"));
                map.put("batch_status",row.getStringOrNull("batch_status"));
                if (row.getStringOrNull("batch_status").equals("0"))
                    map.put("status", "未");
                else if (row.getStringOrNull("batch_status").equals("1"))
                    map.put("status", "中");
                else if (row.getStringOrNull("batch_status").equals("2"))
                    map.put("status", "済");
                data.add(map);

            }}

            act.setStatusList(inspectionstatus);
            Log.e(TAG, "DAtaaaaaaaaaa    " + data);
            act.setBatchbadge(data);
            U.beepSuccess();
        }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }
}
