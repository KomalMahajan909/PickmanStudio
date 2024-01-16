package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.BatchPickingActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 11/5/2018.
 */

public class GetBatchPickingNS {
    Map<String, String> map = new HashMap<String, String>();
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    String all_row_count = "0";
    String TAG = "GetBatchPickingNS";


    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
        ArrayList<String> inspectionstatus = new ArrayList<String>();

        final BatchPickingActivity act = (BatchPickingActivity) activity;
        String all_order_count = "0";
        String user="";
        String zone_alert= "";
        JsonArray result1;
        if (list.size() > 0){
            // collect all data from response
            JsonHash row1 = (JsonHash) list.get(0);
            final String short_inspected = row1.getStringOrNull("row_working");
            all_order_count = row1.getStringOrNull("row_complete");

            String not_inspected = row1.getStringOrNull("row_pending");

            all_row_count = U.plusTo(not_inspected, short_inspected);
            act.updateBadge1(all_order_count);

            Log.e(TAG,">>>>>>>>>>>>>>");
            result1 = row1.getJsonArrayOrNull("batchpick");

            for (int j = 0; j < result1.size(); j++) {

                JsonHash row = (JsonHash) result1.get(j);
                Log.e(TAG, ">>>>>>>>>3232323232" );


                    HashMap<String,String > data = new HashMap<>();
                    Log.e(TAG, " " + row);

                    // insert data into desire variables and fields for further processing.
                    data.put("barcode",row.getStringOrNull("barcode"));
                    data.put("code",row.getStringOrNull("code"));
                    data.put("quantity",row.getStringOrNull("quantity"));
                    data.put("product_name",row.getStringOrNull("product_name"));
                    data.put("location",row.getStringOrNull("location"));
//                        data.put("product_stock_history_id",map.get("product_stock_history_id"));
                    data.put("lot",row.getStringOrNull("lot"));

                    String cate = row.getStringOrNull("category");
                    if(cate.equals("0"))
                        data.put("category"," 商品");
                    else if(cate.equals("1"))
                        data.put("category"," 同梱物");
                    data.put("diff",row.getStringOrNull("diff"));
                    String diff = row.getStringOrNull("diff");
                    String qty = row.getStringOrNull("quantity");
                    String inspection_num = row.getStringOrNull("inspection_batch");
                    data.put("inspection_num",row.getStringOrNull("inspection_batch"));

                    data.put("is_scanned",row.getStringOrNull("is_scanned"));
                    data.put("scanned_user",row.getStringOrNull("scanned_user"));
                if(inspection_num.equals("0")){
                    data.put("ope","未");
                    inspectionstatus.add("0");
                }
                else if(!inspection_num.equals("0") && diff.equals(inspection_num) && qty.equals("0") ){
                    data.put("ope","済");
                    inspectionstatus.add("1");
                }
                else{
                    data.put("ope","中");
                    inspectionstatus.add("2");
                }
                    dataList.add(data);
                }
            if (row1.containsKey("batchbarcode")) {
                JsonArray list2 = row1.getJsonArrayOrNull("batchbarcode");
                JsonHash row2 = (JsonHash) list2.get(0);
                map = (HashMap) row2;
                map.put("processedCnt", "0");
                Log.e(TAG, "MAPPPPPPPPPPPPPPppp" + map);
                // insert data into desire variables and fields for further processing.

                Map<String, String> map1 = new HashMap<String, String>();
//                map1.put("BoxID",ShippingActivity.mBoxNo+"");
                map1.put("location", row2.getStringOrNull("location"));
                map1.put("quantity", row2.getStringOrNull("quantity"));
                map1.put("barcode", row2.getStringOrNull("barcode"));
                map1.put("code", row2.getStringOrNull("code"));
                map1.put("processedCnt", "0");
                Log.e(TAG, "MAPPPPPPPPPPPPPPppp222222222222");
                data.add(map1);

                if(row2.getStringOrNull("is_scanned").equals("1")){
                    String user1 = row2.getStringOrNull("scanned_user");
                    if(!act.adminID.equals(user1)){
                        act.setdialog("Barcode is already being used",activity,R.layout.dialog_popup);
                    }
                }
                Log.e(TAG,"33333333333333333333333");

                act._sts(R.id.quantity, "");
                act.currLineData(map);
                act.nextWork();
                Log.e(TAG,"444444444444444444444444444");

            } else {
                String msg  = "No data found!";
                valid(code, msg, list, params, activity);

            }
            act.setStatusList(inspectionstatus);
            act.getBatchList(dataList);

            act.updateBadge2(dataList.size()+"");
            act.updateBadge1(all_row_count);


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
