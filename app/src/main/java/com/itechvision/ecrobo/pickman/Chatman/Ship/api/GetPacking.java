package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.PackSetActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 3/19/2019.
 */

public class GetPacking {
    String TAG = "GetPacking";


    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        //List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();

        final PackSetActivity act = (PackSetActivity) activity;
        String all_order_count = "0";
        String all_row_count = "0";
        String parentcode = null;
        String pack_id = null;
        String group= "";
        String user="";


        if (list.size() > 0){
            // collect all data from response
            JsonHash row = (JsonHash) list.get(0);
            all_order_count = row.getStringOrNull("total_count");

            String not_inspected = row.getStringOrNull("not_inspection_row_count");
            String short_inspected = row.getStringOrNull("shortage_row_count");
            pack_id = row.getStringOrNull("barcode");
            all_row_count  = U.plusTo(not_inspected, short_inspected);

            if (row.containsKey("data")) {

                JsonArray list2 = row.getJsonArrayOrNull("data");
                JsonHash row2 = (JsonHash) list2.get(0);
                map = (HashMap) row2;
                Log.e("GetPikingggggggggggg", " " + map);
                map.put("processedCnt", "0");

                // insert data into desire variables and fields for further processing.
                parentcode = map.get("parent_code");

                String prod_name = map.get("child_name");
                String barcode = map.get("child_barcode");


                String location = map.get("child_location");
                String code0 = map.get("child_code");
                String pdt_quantity = map.get("child_require_quantity");


                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("pack_id",row.getStringOrNull("barcode"));
                map1.put("barcode", map.get("child_barcode"));
                map1.put("code", map.get("child_code"));
                map1.put("quantity", map.get("child_require_quantity"));
                map1.put("location", map.get("child_location"));


                act.currLineData(map1);
                act.updateBadge1(all_order_count);
                act.updateBadge2(all_row_count);

                //act.speakProducts(all_row_count);

                if (act.isBarcodeChange) {
                    Log.e("GetPikinggggg", "BarCodeChangeeee");
                    act._sts(R.id.packId,pack_id);
                    act._sts(R.id.barcode, barcode.trim());
                    act._sts(R.id.quantity, "");
                    act._sts(R.id.parentCode,parentcode);
                    act._sts(R.id.location, location);
                    act._sts(R.id.productCode, code0);
                    act._stxtv(R.id.productname, prod_name);
                    act.prod_name.setSelected(true);
                    act._sts(R.id.productQuantity, pdt_quantity);

                    act.inputedEvent(barcode, true);

//					mTextToSpeak.startSpeaking("数量",pdt_quantity);
                } else {
                    Log.e("GetPikinggggg", "ELSE____BarCodeChangeeee");
                    act._sts(R.id.packId,pack_id);
                    act._sts(R.id.barcode, "");
                    act._sts(R.id.quantity, "");
                    act._sts(R.id.parentCode,parentcode);
                    act._sts(R.id.location, "");
                    act._sts(R.id.productCode, "");
                    act._stxtv(R.id.productname, "");
                    act._sts(R.id.productQuantity, "");


                    /* Conditional set next process whether barcode or trackingNo or orderNo */

                }
                act.startTimer();
                U.beepSiekai(activity,null);
                if(BaseActivity.getParentScanSelected()){
                    act.setProc(PackSetActivity.PROC_PARENTBARCODE);
                }
                else
                act.setProc(PackSetActivity.PROC_BARCODE);
            }



            else{
                Log.e("GetPikinggggg","No data found!!!!!!!!!!!!");
                String msg  = "No data found!";
                valid(code, msg, list, params, activity);
            }
        } else {
            Log.e("GetPikinggggg","No record found!!!!!!!!!!!!!");
            String msg  = "No record found!";
            valid(code, msg, list, params, activity);
        }
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }

}
