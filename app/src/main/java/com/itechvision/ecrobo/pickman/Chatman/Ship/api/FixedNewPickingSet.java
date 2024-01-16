package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.NewPackingSetActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by lenovo on 3/25/2019.
 */

public class FixedNewPickingSet {
    String TAG = "FixedNewPickingSet";
    public static String fixbarcode=null;
    public static String fixlot=null;
    private TextToSpeak mTextToSpeak;
    boolean permission = true;

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        //List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        Map<String, String> map = new HashMap<String, String>();

        final NewPackingSetActivity act = (NewPackingSetActivity) activity;
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
            int shotage= Integer.parseInt(short_inspected);
            if (shotage > 0 && !row.containsKey("data")) {
                new SweetAlertDialog(activity)
                        .setTitleText("欠品処理をした商品があります。")
                        .show();

                Log.e(TAG, "No data presentttt   ");
            }
            if(row.containsKey("data") && !all_row_count.equals("0")) {
                JsonArray list2 = row.getJsonArrayOrNull("data");
                JsonHash row2 = (JsonHash) list2.get(0);
                map = (HashMap) row2;

                pack_id = row.getStringOrNull("barcode");
                // insert data into desire variables and fields for further processing.

                parentcode = map.get("parent_code");

                String name = map.get("child_name");
                String barcode = map.get("child_barcode");


                String location = map.get("child_location");
                String code0 = map.get("child_code");
                String quantity = map.get("child_require_quantity");


                act._sts(R.id.packId,pack_id);

                act._sts(R.id.barcode, "");

                act._sts(R.id.location, location);
                act._sts(R.id.quantity, "");
                act._sts(R.id.productCode, code0);
                act._stxtv(R.id.productname,name);
                act.productName.setSelected(true);
                act._sts(R.id.productQuantity, quantity);



                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("pack_id",pack_id);
                map1.put("barcode", map.get("child_barcode"));
                map1.put("code", map.get("child_code"));
                map1.put("quantity", map.get("child_require_quantity"));
                map1.put("location", map.get("child_location"));
                map1.put("processedCnt", "0");

//                act._sts(R.id.productname,prod_name);
                act.currLineData(map1);
//                act.updateLineNo(map.get("no"));
                act.startTimer();
                act.setProc(NewPackingSetActivity.PROC_BARCODE);


            } else {
				/* Scan next order without updating top three color badges and orderId field */
                if( NewPackingSetActivity.skipvalue==false && Integer.parseInt(all_row_count)==0)
                    U.beepFinish(act, null);
                act.nextProcess();
                Log.e(TAG,"Running next process");
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
