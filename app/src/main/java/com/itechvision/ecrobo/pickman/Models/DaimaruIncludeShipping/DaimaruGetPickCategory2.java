package com.itechvision.ecrobo.pickman.Models.DaimaruIncludeShipping;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.WWW6Server.DimaruShipping.Daimaru_IncludeShipping;
 import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 10/21/2018.
 */

public class DaimaruGetPickCategory2 {
    Map<String, String> map = new HashMap<String, String>();
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    String all_row_count = "0";
    String TAG = "GetPickCategory2";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        //List<Map<String, String>> data = new ArrayList<Map<String, String>>();
//        NewShipActivity.count=0;
        final Daimaru_IncludeShipping act = (Daimaru_IncludeShipping) activity;
        String all_order_count = "0";

        if (list.size() > 0){
            // collect all data from response
            JsonHash row = (JsonHash) list.get(0);
            final String short_inspected = row.getStringOrNull("shortage_row_count");
            all_order_count = row.getStringOrNull("all_order_count");

            String not_inspected = row.getStringOrNull("not_inspection_row_count");

            all_row_count = U.plusTo(not_inspected, short_inspected);

            Log.e(TAG,">>>>>>>>>>>>>>");

            if (row.containsKey("data")) {
                JsonArray list2 = row.getJsonArrayOrNull("data");
                JsonHash row2 = (JsonHash) list2.get(0);
                map = (HashMap) row2;
                map.put("processedCnt", "0");
                Log.e(TAG,"MAPPPPPPPPPPPPPPppp"+map);
                // insert data into desire variables and fields for further processing.
                final String tracking = map.get("mediacode");
                final String order_id = map.get("order_id");
                final String name = map.get("product_name");
                String barcode = map.get("barcode");
                final String location = map.get("location");
                final String code0 = map.get("code");
                final String pdt_quantity = map.get("quantity");
                String lot= map.get("lot");
                Log.e(TAG,"MAPPPPPPPPPPPPPPppp111111111111"+map);
                Map<String, String> map1 = new HashMap<String, String>();
//                map1.put("BoxID",ShippingActivity.mBoxNo+"");
                map1.put("location", row2.getStringOrNull("location"));
                map1.put("order_id",row2.getStringOrNull("order_id"));
                map1.put("quantity", row2.getStringOrNull("quantity"));
                map1.put("barcode", row2.getStringOrNull("barcode"));
                map1.put("code", row2.getStringOrNull("code"));
                map1.put("category", row2.getStringOrNull("category"));
                map1.put("orderSubId", row2.getStringOrNull("order_sub_id"));
                map1.put("processedCnt", "0");
                Log.e(TAG,"MAPPPPPPPPPPPPPPppp222222222222");
                data.add(map1);

                act.currLineData(map);


                Log.e(TAG,"33333333333333333333333");

                act._sts(R.id.quantity, "");

                act.setProductList(data);
                act.nextWork();



                Log.e(TAG,"444444444444444444444444444");

            } else {
                String msg  = "No data found!";
                valid(code, msg, list, params, activity);
                act._sts(R.id.quantity, "");

            }
        } else {
            String msg  = "No record found!";
            valid(code, msg, list, params, activity);
        }
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((Daimaru_IncludeShipping) activity).setProc(Daimaru_IncludeShipping.PROC_BARCODE);
    }




}
