package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.PackSetActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 3/19/2019.
 */

public class GetPackingParent {
    Map<String, String> map = new HashMap<String, String>();
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    String all_row_count = "0";
    String TAG ="GetPackingParent ";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        //List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        final PackSetActivity act = (PackSetActivity) activity;
        String all_order_count = "0";
        String packID="";

        if (list.size() > 0){
            // collect all data from response
            JsonHash row = (JsonHash) list.get(0);
            final String short_inspected = row.getStringOrNull("shortage_row_count");
            all_order_count = row.getStringOrNull("total_count");

            String not_inspected = row.getStringOrNull("not_inspection_row_count");
            packID= row.getStringOrNull("barcode");
            all_row_count = U.plusTo(not_inspected, short_inspected);
            act.updateBadge1(all_order_count);
            act.updateBadge2(all_row_count);
            act._sts(R.id.packId, packID);
//			act.updateBadge3(short_inspected);


            if (row.containsKey("data")) {
                JsonArray list2 = row.getJsonArrayOrNull("data");
                JsonHash row2 = (JsonHash) list2.get(0);
                map = (HashMap) row2;
                Log.e(TAG, " Data Presentttt");

                // insert data into desire variables and fields for further processing.

//                String parentCode = map.get()
                final String name = map.get("child_name");
                String barcode = map.get("child_barcode");
                final String location = map.get("child_location");
                final String code0 = map.get("child_code");
                String parent = "";
                if(map.containsKey("parent_code"))
                    parent  = map.get("parent_code");

                final String pdt_quantity = map.get("child_require_quantity");


         /*       act._sts(R.id.location, location);
                act._sts(R.id.quantity, "");
                act._sts(R.id.productCode, code0);
                act._stxtv(R.id.productname,name);
                act.prod_name.setSelected(true);
                act._sts(R.id.productQuantity, pdt_quantity);*/


                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("pack_id",row.getStringOrNull("barcode"));
                map1.put("barcode", map.get("child_barcode"));
                map1.put("code", map.get("child_code"));
                map1.put("quantity", map.get("child_require_quantity"));
                map1.put("location", map.get("child_location"));
                map1.put("parent_code", parent);
                map1.put("processedCnt", "0");

                act.startTimer();
                act.currLineData(map1);

                U.beepNext();
                act.setProc(PackSetActivity.REQ_BARCODE);



            } else {
                Log.e(TAG, "Nooooo Data Presentttt");
                String msg  = "No data found!";
                valid(code, msg, list, params, activity);
                act._sts(R.id.location, "");
                act._sts(R.id.quantity, "");
                act._sts(R.id.productCode, "");

            }
        } else {
            Log.e(TAG, "Nooooo Record  founddd  ");
            String msg  = "No record found!";
            valid(code, msg, list, params, activity);
        }
    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((PackSetActivity) activity).setProc(PackSetActivity.PROC_PARENTBARCODE);
    }
}
