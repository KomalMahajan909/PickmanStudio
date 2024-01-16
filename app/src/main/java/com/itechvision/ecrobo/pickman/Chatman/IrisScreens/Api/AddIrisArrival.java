package com.itechvision.ecrobo.pickman.Chatman.IrisScreens.Api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.IrisScreens.Iris_Arrival_Activity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;

import java.util.HashMap;

public class AddIrisArrival {
    protected String mNextBarcode1 = null;
    String TAG= "AddArrival";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        //U.beepNext(activity, "検品データを登録しました。", 0);

        U.beepKakutei(activity, "検品データを登録しました。");
        Iris_Arrival_Activity act = ((Iris_Arrival_Activity) activity);
        mNextBarcode1 =act.isNextBarcode;
        act.nextProcess();
        Log.e(TAG , "NextBarcodeeeee   "+mNextBarcode1 + " isNextBarcode  "+ Iris_Arrival_Activity.mNextBarcode);

        if (Iris_Arrival_Activity.mNextBarcode)
            act.scanedEvent(mNextBarcode1);

        act.isNextBarcode = "";
        Iris_Arrival_Activity.mNextBarcode = false;
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }

}
