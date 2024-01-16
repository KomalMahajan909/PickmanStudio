package com.itechvision.ecrobo.pickman.Chatman.Arrivals.api;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ArrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.RFIDArrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 6/6/2019.
 */

public class GetArrivaldetail {
    private String TAG = "GetArrivaldetail";

    TextToSpeak mTextToSpeak;


    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {


        mTextToSpeak = new TextToSpeak(activity);
        RFIDArrivalActivity act = (RFIDArrivalActivity) activity;

        String product_code = null;


        JsonHash row = (JsonHash) list.get(0);
        product_code = row.getStringOrNull("code");
        String serial = row.getStringOrNull("last_serial_no");
        String useRf = row.getStringOrNull("rfid_flag");


        mTextToSpeak.startSpeaking("scanning");
        act.startTimer();
        act._sts(R.id.sku, product_code);
        act._sts(R.id.serial,serial);

        if(useRf.equals("0"))
        act.orderRequestSettings = false;
        else
            act.orderRequestSettings = true;
        act.setLayout();
        if(act.orderRequestSettings){
            act.setProc(RFIDArrivalActivity.PROC_RFID);

        }
        else  {
            act.setProc(RFIDArrivalActivity.PROC_QTY);
        }

    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((RFIDArrivalActivity) activity).setProc(RFIDArrivalActivity.PROC_BARCODE);
    }

}
