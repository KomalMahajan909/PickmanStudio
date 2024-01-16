package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Ship.DbatchIncludeActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.TruckBatchPickingActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lenovo on 5/13/2019.
 */

public class GetTruckPicking {
    private String TAG = "GetTruckPicking";


    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {
        HashMap <String, String> map = new HashMap<>();
        TruckBatchPickingActivity act = (TruckBatchPickingActivity) activity;

        String batchno ="";
        String company = "";
        String allrows = "";
        String orderNo = "";

        JsonHash row = (JsonHash) list.get(0);
        Log.e(TAG,">>>>>>>>>>>"+row);


       allrows = row.getStringOrNull("pending_row_counts");
       String finished_orders =row.getStringOrNull("batch_orders_complete_count");
       String remaining_orders = row.getStringOrNull("batch_orders_remaining_count");
       String totalorders = row.getStringOrNull("total_orders_remaining_count");

        if(row.containsKey("product_row")){
            JsonHash row1 = row.getJsonHashOrNull("product_row");
            Log.e(TAG,">>>>>>>>>>>11111111111111111"+row1);

            batchno = row1.getStringOrNull("batch_no");
            if(row1.containsKey("shipping_method_name"))
                company = row1.getStringOrNull("shipping_method_name");

            map.put("location", row1.getStringOrNull("location"));
            map.put("barcode",row1.getStringOrNull("barcode"));
            map.put("code",row1.getStringOrNull("code"));
            map.put("quantity",row1.getStringOrNull("rest_quantity"));
            map.put("frontage",row1.getStringOrNull("frontage_number"));
            map.put("order_sub_id",row1.getStringOrNull("order_sub_id"));
            map.put("psh_id",row1.getStringOrNull("psh_id"));
            map.put("truck_id",row1.getStringOrNull("truck_id"));
            map.put("batch_id",row1.getStringOrNull("batch_id"));
            map.put("order_id",row1.getStringOrNull("order_id"));
            map.put("color",row1.getStringOrNull("spec1"));
            map.put("size",row1.getStringOrNull("spec2"));
            map.put("cancel_flag",row1.getStringOrNull("cancel_flag"));
            map.put("processedCnt","0");

            orderNo =  row1.getStringOrNull("order_no");

            Log.e(TAG,">>>>>>>>>>>11111mappppppp    "+map);

        }
        if(map.get("cancel_flag").equals("1")){
            act.showMessage();
            act.cancelOrder.setVisibility(View.VISIBLE);
        }

        act.currLineData(map);
        act.setProc(TruckBatchPickingActivity.PROC_BARCODE);
        act._sts(R.id.shipping_company, company);
        act._sts(R.id.location,map.get("location"));
        act._sts(R.id.showfrontage,map.get("frontage"));
        act._sts(R.id.showbarcode,map.get("code"));
        act._sts(R.id.color,map.get("color"));
        act._sts(R.id.size,map.get("size"));
        act._sts(R.id.order_no,orderNo);

        act.updateBadge4(batchno);
        act.updateBadge1(totalorders);
        act.updateBadge2(remaining_orders);
        act.updateBadge3(finished_orders);

        act.repeatLocation(1000);

        act.startTimer();

    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }
}
