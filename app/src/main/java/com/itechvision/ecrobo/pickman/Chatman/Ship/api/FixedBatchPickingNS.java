package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.BatchPickingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.ShippingActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
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

public class FixedBatchPickingNS {
    String TAG = "FixedBatchPickingNS";
    //	protected String mNextBarcode = null;
    public static int empty=0;
    private TextToSpeak mTextToSpeak;
    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
        ArrayList<String> inspectionstatus = new ArrayList<String>();

        Log.e(TAG,"allrowConttttt111111111111111111  ");
        BatchPickingActivity act = (BatchPickingActivity) activity;
        mTextToSpeak = new TextToSpeak(act);
        String all_order_count = null;
        String all_row_count = null;
        JsonArray result1;
        if (list.size() > 0) {
            // collect all data from response
            Log.e(TAG,"allrowConttttt222222222222222222 ");
            JsonHash row1 = (JsonHash) list.get(0);
            Log.e(TAG,"allrowConttttt11111111111112222222222222 ");
            all_order_count = row1.getStringOrNull("row_complete");

            String not_inspected = row1.getStringOrNull("row_pending");
            String short_inspected = row1.getStringOrNull("row_working");
            Log.e(TAG,"allrowConttttt654321123456 ");

            Log.e(TAG,"allrowConttttt1212121221313 ");
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
            all_row_count = U.plusTo(not_inspected, short_inspected);
            Log.e(TAG,"allrowConttttt  "+all_row_count);

            if(all_row_count.equals("0")){
                act.goback();
            }
            else if (act.mNextBarcode) {

                act.setProc(act.PROC_BARCODE);
                act._sts(R.id.barcode, "");
                act._sts(R.id.barcode, act.isNextBarcode);
                act._sts(R.id.quantity,"");

                act.setStatusList(inspectionstatus);
                act.updateBadge2(dataList.size() + "");
                act.updateBadge1(all_row_count);
                act.getBatchList(dataList);



                act.inputedEvent(act.isNextBarcode, true);
                Log.e("FixedPickingNS","222222222235555555555555");
                act.mNextBarcode = false;
            }

             else {
                 act._sts(R.id.barcode,"");
                 act._sts(R.id.quantity,"");
                 act.setStatusList(inspectionstatus);
                 act.updateBadge2(dataList.size() + "");

                 act.updateBadge1(all_row_count);
                 act.getBatchList(dataList);

                 act.setProc(BatchPickingActivity.PROC_BARCODE);
                 act.scroll();
                 Log.e(TAG, ">>>>>>>>>>>>>>>>>" + act.mNextBarcode);
             }
        }

    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        ShippingActivity act = (ShippingActivity) activity;
        if (code.equals("10209")) {
            act.setProc(act.PROC_BARCODE);
        }
        else {
            U.beepError(activity, message);
            act.setProc(act.PROC_BARCODE);

        }}


//	public ECZaikoClientListner setNext(String nextBarcode) {
//		mNextBarcode = nextBarcode;
//		return this;
//	}

}
