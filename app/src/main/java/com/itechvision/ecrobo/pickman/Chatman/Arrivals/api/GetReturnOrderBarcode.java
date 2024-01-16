package com.itechvision.ecrobo.pickman.Chatman.Arrivals.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ReturnStockActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 1/21/2019.
 */

public class GetReturnOrderBarcode {
    String attribute= "0";
    String TAG = "GetReturnOrderBarcode",LotNo="",EXPDATE="";
//    public static String return_class = "" ;
    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        Log.e(TAG , ">>>>>>>>>>>>>>");
        ReturnStockActivity act = (ReturnStockActivity) activity;
        if (list.size() > 0){
            // collect all data from response
            JsonHash row = (JsonHash) list.get(0);
            Log.e(TAG , ">>>>>>>>>>>>>>111111");
            if (row.containsKey("data")) {
                JsonArray list2 = row.getJsonArrayOrNull("data");
                JsonHash row2 = (JsonHash) list2.get(0);

                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("order_sub_id", row2.getStringOrNull("order_sub_id"));
                map1.put("order_id",row2.getStringOrNull("order_id"));
                map1.put("quantity", row2.getStringOrNull("num"));
                map1.put("barcode", row2.getStringOrNull("barcode"));
                map1.put("code", row2.getStringOrNull("code"));
                map1.put("processedCnt", "0");

            //    map1.put("lot", row2.getStringOrNull("lot"));
            //    map1.put("expi", row2.getStringOrNull("expiration_date"));

             //    map1.put("return_class", row2.getStringOrNull("return_class"));

            //    return_class = row2.getStringOrNull("return_class");

                act._sts(R.id.quantity, "");
            //    act._sts(R.id.lotno, LotNo);
            //    act._sts(R.id.expidate, EXPDATE);
                act.currLineData(map1);
            //    act.nextWork();

            }

            else {
                String msg  = "No data found!";
                valid(code, msg, list, params, activity);
                act._sts(R.id.location, "");
                act._sts(R.id.quantity, "");
//
                act._sts(R.id.productCode, "");
             /*   act._sts(R.id.lotno, "");
                act._sts(R.id.expidate, "");*/
            }
        } else {
            String msg  = "No record found!";
            valid(code, msg, list, params, activity);
        }
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }
}
