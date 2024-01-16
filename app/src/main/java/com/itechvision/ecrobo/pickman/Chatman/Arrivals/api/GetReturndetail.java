package com.itechvision.ecrobo.pickman.Chatman.Arrivals.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.RFIDArrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.RFID_ReturnActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;

/**
 * Created by lenovo on 6/26/2019.
 */

public class GetReturndetail{
    private String TAG = "GetReturndetail";

        TextToSpeak mTextToSpeak;


        public void post(String code, String message, JsonArray list,
                         HashMap<String, String> params, Activity activity) {


            mTextToSpeak = new TextToSpeak(activity);
            RFID_ReturnActivity act = (RFID_ReturnActivity) activity;

            String product_code = null;


            JsonHash row = (JsonHash) list.get(0);
            product_code = row.getStringOrNull("code");
//            String useRf = row.getStringOrNull("rfid_flag");


            mTextToSpeak.startSpeaking("scanning");
            act.startTimer();
            act._sts(R.id.sku, product_code);


            if(!act.orderRequestSettings)
               act.setSerial();
//            else
//                act.orderRequestSettings = true;
//            act.setLayout();

            if(act.orderRequestSettings){
//                act.setProc(RFID_ReturnActivity.PROC_RFID);
//            }
//            else  {
                act.setProc(RFID_ReturnActivity.PROC_QTY);
            }

        }


        public void valid(String code, String message, JsonArray list,
                          HashMap<String, String> params, Activity activity) {
            U.beepError(activity, message);
            RFID_ReturnActivity act = (RFID_ReturnActivity) activity;
            if(act.orderRequestSettings)
            act.setProc(RFIDArrivalActivity.PROC_BARCODE);
            else  act.setProc(RFIDArrivalActivity.PROC_RFID);
        }

}