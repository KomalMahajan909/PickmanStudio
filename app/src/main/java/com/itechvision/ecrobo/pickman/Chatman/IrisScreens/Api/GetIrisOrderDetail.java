package com.itechvision.ecrobo.pickman.Chatman.IrisScreens.Api;

import android.app.Activity;

import com.itechvision.ecrobo.pickman.Chatman.IrisScreens.IrisPickingActivity;

import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2/6/2019.
 */

public class GetIrisOrderDetail {
    String TAG = "GetIrisOrderDetail";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        IrisPickingActivity act = (IrisPickingActivity) activity;

        if (list.size() > 0){
            // collect all data from response
            JsonHash row = (JsonHash) list.get(0);
            JsonArray list2 = row.getJsonArrayOrNull("data");
            for (int i=0; i< list2.size(); i++) {
                JsonHash row2 = (JsonHash) list2.get(i);
                Map<String, String> map = new HashMap<String, String>();
                map = (HashMap) row2;
                data.add(map);
            }
            act.inspectionData(data);
            act.initiatePopup();
            IrisPickingActivity.count =0;
        } else {
            String msg  = "No record found!";
            valid(code, msg, list, params, activity);
        }
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }

}
