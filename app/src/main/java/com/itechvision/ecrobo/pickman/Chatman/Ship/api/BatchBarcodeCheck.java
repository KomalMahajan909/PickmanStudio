package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.BatchBoxActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.DBatchPickingActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 4/22/2019.
 */

public class BatchBarcodeCheck {

    String TAG = "BatchBarcodeCheck";


    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        Map<String, String> map = new HashMap<String, String>();

        Log.e(TAG, " " + list);
        final DBatchPickingActivity act = (DBatchPickingActivity) activity;
        HashMap<String, String> data = new HashMap<>();

            Log.e(TAG, " >>>>>>>>>>>>>>>>>");
            // collect all data from response
        for(int i=0;i< list.size();i++){
            JsonHash row = (JsonHash) list.get(i);



            // insert data into desire variables and fields for further processing.


           String box =  row.getStringOrNull("box_no");
           String qnty = row.getStringOrNull("rest_quantity");
           if(!qnty.equals("0")) {

               data.put("box_no", row.getStringOrNull("box_no"));
               data = (HashMap) row;
               data.put("processedCnt", "0");

               act.boxbtn.setText(box);
               act.qntybtn.setText(qnty);
               break;
           }

           }
           if(!data.isEmpty()){
               act.currLineData(data);
               act.nextWork();
           }
           else   U.beepError(activity,"Barcode already scanned");

    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {

        U.beepError(activity, message);

    }
}
