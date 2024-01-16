package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.DBatchPickingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.DBatchPickingCategoryActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.DbatchIncludeActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 5/11/2019.
 */

public class GetIncludeTray {
    private String TAG = "GetIncludeTray";


    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {
        Map<String, String> data = new HashMap<>();
        DBatchPickingCategoryActivity act = (DBatchPickingCategoryActivity) activity;

        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            Log.e(TAG, "<<<<<<<<<<<<<<<<<<<" + row);
            JsonArray products = row.getJsonArrayOrNull("include_products");
            Log.e(TAG, "<<<<<<<<<<<<<<<<<<<11111111111111111" + products);
            if (products != null) {
                for (int j = 0; j < products.size(); j++) {
                    JsonHash row1 = (JsonHash) products.get(j);
                    Log.e(TAG, "<<<<<<<<<<<<<<<<<<<2222222222222" + row1);
                    HashMap<String, String> map = new HashMap<>();

                    map.put("quantity", row1.getStringOrNull("tray_rest_quantity"));
                    map.put("tray", row1.getStringOrNull("tray_no"));
                    map.put("processedCnt", "0");


                    if (!row1.getStringOrNull("tray_rest_quantity").equals("0")) {
                        data.putAll(map);
                        Log.e(TAG, "<<<<<<<<<<<<<<<<<<<3333333333333" + data);
                        break;
                    }
                }
            }
        }

        if (!data.isEmpty()) {
            act.currLineData(data);
        } else {
            U.beepError(activity, "no results found");
        }

    }
    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((DBatchPickingCategoryActivity) activity).setProc(DBatchPickingCategoryActivity.PROC_TRAYNO);
    }

}
