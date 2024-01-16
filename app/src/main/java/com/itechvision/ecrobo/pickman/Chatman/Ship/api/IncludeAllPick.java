package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.IncludeShippingActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;

import java.util.HashMap;

/**
 * Created by lenovo on 10/21/2018.
 */

public class IncludeAllPick {
    String TAG = "IncludeAllPick";
    //	protected String mNextBarcode = null;
    public static int empty=0;

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        Log.e("IncludeAllPick","allrowConttttt111111111111111111  ");
        IncludeShippingActivity act = (IncludeShippingActivity) activity;
        act.goback();

    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        IncludeShippingActivity act = (IncludeShippingActivity) activity;
//        act.removePackItem();
        act.setProc(act.PROC_BARCODE);

    }




}
