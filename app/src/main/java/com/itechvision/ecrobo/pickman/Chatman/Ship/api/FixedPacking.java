package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.PackSetActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 3/19/2019.
 */

public class FixedPacking {
    public static String fixbarcode=null;
    public static String fixlot=null;
    private TextToSpeak mTextToSpeak;
    boolean permission = true;

    String TAG = "FixedPacking";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

      //List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();

        final PackSetActivity act = (PackSetActivity) activity;
        String all_order_count = "0";
        String all_row_count = "0";
        String parentcode = null;
        String pack_id = null;
        mTextToSpeak = new TextToSpeak(activity);
        if (list.size() > 0){
            // collect all data from response
            JsonHash row = (JsonHash) list.get(0);
            all_order_count = row.getStringOrNull("total_count");

            String not_inspected = row.getStringOrNull("not_inspection_row_count");
            String short_inspected = row.getStringOrNull("shortage_row_count");

            all_row_count = U.plusTo(not_inspected,short_inspected);

            act.updateBadge1(all_order_count);
            act.updateBadge2(all_row_count);

            if(row.containsKey("data") && !all_row_count.equals("0")) {
                JsonArray list2 = row.getJsonArrayOrNull("data");
                JsonHash row2 = (JsonHash) list2.get(0);
                map = (HashMap) row2;
                map.put("processedCnt", "0");
                pack_id = row.getStringOrNull("barcode");

                parentcode = map.get("parent_code");

                String name = map.get("child_name");
                String barcode = map.get("child_barcode");

                String location = map.get("child_location");
                String code0 = map.get("child_code");
                String quantity = map.get("child_require_quantity");

                act._sts(R.id.packId,pack_id);

                act._sts(R.id.barcode, "");
                act._sts(R.id.quantity, "");
                act._sts(R.id.location, "");
                act._sts(R.id.productCode, "");
                act._stxtv(R.id.productname, "");
                act._sts(R.id.productQuantity, "");

                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("pack_id",pack_id);
                map1.put("barcode", map.get("child_barcode"));
                map1.put("code", map.get("child_code"));
                map1.put("quantity", map.get("child_require_quantity"));
                map1.put("location", map.get("child_location"));
                map1.put("processedCnt","0");
                map1.put("parent_code",parentcode);

//              act._sts(R.id.productname,prod_name);
                act.currLineData(map1);
//              act.updateLineNo(map.get("no"));
                act.startTimer();

            if (act.isNextbarcode) {
              //  Log.e(TAG, "next barcodeeeee   "+act.isNextbarcode +"     "+act.mBarcode);


                act.scanedEvent(act.mBarcode);
                act.setProc(PackSetActivity.PROC_BARCODE);

                act.isNextbarcode= false;
                act.mBarcode ="";
            }  else {

             //   Log.e(TAG, "no  barcodeeeee  change "+act.isNextbarcode +"     "+act.mBarcode);
                act._sts(R.id.barcode, "");
                act.setProc(PackSetActivity.PROC_BARCODE);

                act._sts(R.id.quantity, "");
                act._sts(R.id.location, "");
                act._sts(R.id.productCode, "");
                act._stxtv(R.id.productname, "");
            }

            }else {
				/* Scan next order without updating top three color badges and orderId field */
                if(  Integer.parseInt(all_row_count)==0)
                    U.beepFinish(act, null);
                act.nextProcess();
                Log.e("FixedPicking ","Running next process");
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
