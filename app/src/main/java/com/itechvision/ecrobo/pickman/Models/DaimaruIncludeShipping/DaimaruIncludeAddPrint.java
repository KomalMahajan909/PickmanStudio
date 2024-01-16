package com.itechvision.ecrobo.pickman.Models.DaimaruIncludeShipping;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.WWW6Server.DimaruShipping.Daimaru_IncludeShipping;
import com.itechvision.ecrobo.pickman.Chatman.WWW6Server.DimaruShipping.Daimaru_Shipping;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;

/**
 * Created by lenovo on 2/17/2019.
 */

public class DaimaruIncludeAddPrint {
    String TAG = "IncludeAddPrint";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        if (list.size() > 0) {
            // collect all data from response
            JsonHash row = (JsonHash) list.get(0);
            String box_no = row.getStringOrNull("box_no");
            Daimaru_Shipping.mBoxNo=Integer.parseInt(box_no);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
            Daimaru_IncludeShipping act = (Daimaru_IncludeShipping) activity;
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

