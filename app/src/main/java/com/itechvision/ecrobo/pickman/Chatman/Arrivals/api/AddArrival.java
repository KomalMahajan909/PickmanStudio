package com.itechvision.ecrobo.pickman.Chatman.Arrivals.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ArrivalActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;

import java.util.HashMap;

/**
 * Created by lenovo on 12/13/2018.
 */

public class AddArrival {
    protected String mNextBarcode1 = null;
   String TAG= "AddArrival";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        //U.beepNext(activity, "検品データを登録しました。", 0);

        U.beepKakutei(activity, "検品データを登録しました。");
        ArrivalActivity act = ((ArrivalActivity) activity);
        mNextBarcode1 =act.isNextBarcode;
        act.nextProcess();
        Log.e(TAG , "NextBarcodeeeee   "+mNextBarcode1 + " isNextBarcode  "+ ArrivalActivity.mNextBarcode);

        if (ArrivalActivity.mNextBarcode)
            act.scanedEvent(mNextBarcode1);

        act.isNextBarcode = "";
        ArrivalActivity.mNextBarcode = false;
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }

}
