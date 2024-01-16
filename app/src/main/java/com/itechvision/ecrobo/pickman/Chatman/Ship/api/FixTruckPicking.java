package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.itechvision.ecrobo.pickman.Chatman.Ship.TruckBatchActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.TruckBatchPickingActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by lenovo on 5/16/2019.
 */

public class FixTruckPicking {

        private String TAG = "FixTruckPicking";
    SweetAlertDialog  pDialog1;

        public void post(String code, String message, JsonArray list,
                         HashMap<String, String> params, Activity activity) {
            HashMap<String, String> map = new HashMap<>();
            TruckBatchPickingActivity act = (TruckBatchPickingActivity) activity;

            String batchno = "";
            String pendingrow = "";
            String shortage = "";
            String allrowcount = "0";
            String orderNo = "";

            if (list.size() > 0){
            JsonHash row = (JsonHash) list.get(0);
            Log.e(TAG, ">>>>>>>>>>>" + row);
            String locreset = "0";
            shortage = row.getStringOrNull("shortage_row_counts");

            pendingrow = row.getStringOrNull("pending_row_counts");

            String finished_orders = row.getStringOrNull("batch_orders_complete_count");

            String remaining_orders = row.getStringOrNull("batch_orders_remaining_count");

            String totalorders = row.getStringOrNull("total_orders_remaining_count");

            if(row.containsKey("location_reset")){
                locreset = row.getStringOrNull("location_reset");
                act.loc_reset = Integer.parseInt(locreset);
            }


            allrowcount = U.plusTo(shortage, pendingrow);
            if (Integer.parseInt(pendingrow) > 0 && !row.containsKey("product_row")) {
                new SweetAlertDialog(activity)
                        .setTitleText("欠品処理をした商品があります。")
                        .show();

                Log.e(TAG, "No data presentttt   ");

            }
            Log.e(TAG, "No data presentttt   " +allrowcount);
            if (row.containsKey("product_row") && !allrowcount.equals("0")) {
                JsonHash row1 = row.getJsonHashOrNull("product_row");
                Log.e(TAG, ">>>>>>>>>>>11111111111111111" + row1);

                batchno = row1.getStringOrNull("batch_no");

                map.put("location", row1.getStringOrNull("location"));
                map.put("barcode", row1.getStringOrNull("barcode"));
                map.put("code", row1.getStringOrNull("code"));
                map.put("quantity", row1.getStringOrNull("rest_quantity"));
                map.put("frontage", row1.getStringOrNull("frontage_number"));
                map.put("order_sub_id", row1.getStringOrNull("order_sub_id"));
                map.put("psh_id", row1.getStringOrNull("psh_id"));
                map.put("truck_id", row1.getStringOrNull("truck_id"));
                map.put("batch_id", row1.getStringOrNull("batch_id"));
                map.put("order_id", row1.getStringOrNull("order_id"));
                map.put("color", row1.getStringOrNull("spec1"));
                map.put("size", row1.getStringOrNull("spec2"));
                map.put("cancel_flag",row1.getStringOrNull("cancel_flag"));

                map.put("processedCnt", "0");

                orderNo =  row1.getStringOrNull("order_no");

                Log.e(TAG, ">>>>>>>>>>>11111mappppppp    " + map);


                act._sts(R.id.location, "");
                act._sts(R.id.showfrontage, "");
                act._sts(R.id.showbarcode, "");
                act._sts(R.id.color, "");
                act._sts(R.id.size, "");
                act._sts(R.id.barcode, "");
                act._sts(R.id.frontage, "");
                act._sts(R.id.quantity, "");
                act._sts(R.id.order_no,"");




                if(map.get("cancel_flag").equals("1")){
                act.showMessage();
                    act.cancelOrder.setVisibility(View.VISIBLE);
                }

                act.currLineData(map);
                act.setProc(TruckBatchPickingActivity.PROC_BARCODE);

                act._sts(R.id.location, map.get("location"));
                act._sts(R.id.showfrontage, map.get("frontage"));
                act._sts(R.id.showbarcode, map.get("code"));
                act._sts(R.id.color, map.get("color"));
                act._sts(R.id.size, map.get("size"));
                act._sts(R.id.order_no,orderNo);

                act.repeatLocation(1000);


                act.updateBadge4(batchno);
                act.updateBadge1(totalorders);
                act.updateBadge2(remaining_orders);
                act.updateBadge3(finished_orders);


            } else {
                if (allrowcount.equals("0") && act.cancelcount > 0)
                    act.getCancelledlist();
                else {
                    U.beepFinish(act, null);
                    act.complete = true;
                    act.returnback();
                }

            }
        }
            else {
                U.beepError(activity,"no data found");
            }
        }
        public void valid(String code, String message, JsonArray list,
                          HashMap<String, String> params, Activity activity) {
            U.beepError(activity, message);
            ((TruckBatchPickingActivity) activity).setProc(TruckBatchPickingActivity.PROC_BARCODE);
        }
    }


