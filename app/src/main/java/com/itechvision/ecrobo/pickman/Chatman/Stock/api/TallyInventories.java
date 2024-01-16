package com.itechvision.ecrobo.pickman.Chatman.Stock.api;

import android.app.Activity;

import com.itechvision.ecrobo.pickman.Chatman.Stock.InventoryActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;

import java.util.HashMap;

/**
 * Created by lenovo on 2/26/2019.
 */

public class TallyInventories {

    String TAG = "TallyInventories";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        InventoryActivity act = ((InventoryActivity) activity);
        act.closePopup();
        act.nextProcess();
        U.beepKakutei(activity, "検品データを登録しました。");
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        InventoryActivity act = ((InventoryActivity) activity);
        act.closePopup();
        U.beepError(activity, message);
    }
}
