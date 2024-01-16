package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.ShippingActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;

/**
 * Created by lenovo on 2/17/2019.
 */

public class NewAddPrint {
    String TAG = "NewAddPrint";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        if (list.size() > 0) {
            // collect all data from response
            JsonHash row = (JsonHash) list.get(0);
            String box_no = row.getStringOrNull("box_no");
            ShippingActivity.mBoxNo=Integer.parseInt(box_no);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
            ShippingActivity act = (ShippingActivity) activity;
            Log.d("DEBUG", "Success");
            act.sendFinalRequest();
//            ShippingActivity.count=0;
        }
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        Log.d("DEBUG", "valid");
    }



}
