package com.itechvision.ecrobo.pickman.Chatman.Stock.api;

import android.app.Activity;

import com.itechvision.ecrobo.pickman.Chatman.Stock.StockChangeActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;

import java.util.HashMap;

/**
 * Created by lenovo on 3/18/2019.
 */

public class AddStockProduct {
    String TAG = "GetStockProduct";

    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {


        StockChangeActivity act = (StockChangeActivity) activity;
        act.nextProcess();


    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((StockChangeActivity) activity).setProc(StockChangeActivity.PROC_BARCODE);
    }

}
