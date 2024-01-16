package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.BatchPickingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.ShippingActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 11/2/2018.
 */

public class GetBatchPicking
{


    public  static String remark= "";
    String TAG = "GetBatchPicking";



    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
        ArrayList<String> inspectionstatus = new ArrayList<String>();

        Log.e(TAG, " " + list);
        final BatchPickingActivity act = (BatchPickingActivity) activity;

        String all_order_count = "0";
        String all_row_count = "0";
        JsonArray result1;
        if (list.size() > 0) {
            remark= "";
            Log.e(TAG, " >>>>>>>>>>>>>>>>>" );
            for(int i = 0 ; i < list.size(); i++) {
                JsonHash row1 = (JsonHash) list.get(i);
                all_order_count = row1.getStringOrNull("row_complete");

                String not_inspected = row1.getStringOrNull("row_pending");
                String short_inspected = row1.getStringOrNull("row_complete");

                all_row_count  = U.plusTo(not_inspected, short_inspected);

//                    Log.e("GetNewBatchPikingList",">>>>>>>>>>>>>>>Batchno."+row.getStringOrNull("batch_no"));
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
                Log.e("GetPikinggggg", "ELSE____BarCodeChangeeee      " + BaseActivity.getsinclude());

                             act.setStatusList(inspectionstatus);
                act.updateBadge2(dataList.size()+"");

                act.updateBadge1(all_row_count);

                act.getBatchList(dataList);




                    Log.e(TAG, dataList+"");

//                    Log.e("GetPikinggggg", "ELSE____BarCodeChangeeee");
//                    act.startTimer();

                    act._sts(R.id.barcode, "");
                    act._sts(R.id.quantity, "");
                    act.setProc(BatchPickingActivity.PROC_BARCODE);




//                }
//                } else {
//                    Log.e("GetPikinggggg", "No data found!!!!!!!!!!!!");
//                    String msg = "Didnt Got Box No.";
//                    valid(code, msg, list, params, activity);
//                }

                }
        } else {
            Log.e(TAG, "No record found!!!!!!!!!!!!!");
            String msg = "No record found!";
            valid(code, msg, list, params, activity);
        }
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        ShippingActivity act = (ShippingActivity) activity;
        U.beepError(activity, message);
        act.setProc(ShippingActivity.PROC_ORDERID);
    }

}