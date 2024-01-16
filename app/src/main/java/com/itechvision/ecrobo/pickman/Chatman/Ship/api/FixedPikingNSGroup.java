package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.NewShippingGroupActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 10/26/2019.
 */

public class FixedPikingNSGroup {
    String TAG = "FixedPikingNS";
    public static int empty=0;

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        NewShippingGroupActivity act = (NewShippingGroupActivity) activity;
        String all_order_count = null;
        String all_row_count = null;

        act.orderID = act._gts(R.id.orderId);
        if (list.size() > 0) {
            // collect all data from response

            JsonHash row = (JsonHash) list.get(0);
            all_order_count = row.getStringOrNull("all_order_count");

            String not_inspected = row.getStringOrNull("not_inspection_row_count");
            String short_inspected = row.getStringOrNull("shortage_row_count");
            String ordersub=null;
            String order= null;
            all_row_count = U.plusTo(not_inspected, short_inspected);
            Log.e("FixedPikinggggg","allrowConttttt  "+all_row_count);
            if(GetPickingOrderGroup.group==1 && !all_row_count.equals("0"))
            {
                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("order_id", row.getStringOrNull("order_id"));
                map1.put("order_sub_id",row.getStringOrNull("order_sub_id"));
                Log.e("FixedPickingNS","ordersub id List" + map1);
                ordersub= row.getStringOrNull("order_sub_id");
                order=row.getStringOrNull("order_id");
                if(NewShippingGroupActivity.ordergot==false){
                    act.orderi=order;
                    NewShippingGroupActivity.ordergot=true;
                }
//				act.order.add(map1);

            }
            act.updateBadge1(all_order_count);
            act.updateBadge3(not_inspected);

            act.updateBadge2(all_row_count);


            Log.e("FixedPickingNS","mNextBarcodeeeee"+act.mNextBarcode);

            if (act.mNextBarcode) {
                act._sts(R.id.barcode, act.isNextBarcode);
                act.setProc(act.PROC_BARCODE);
                act.inputedEvent(act.isNextBarcode, true);
                act.mNextBarcode = false;
                act.isNextBarcode = "";
            }

            else if (all_row_count.equals("0")){

                empty=1;
                act.nextProcess1();
                if(BaseActivity.getRemarkPress()==true && !GetPickingOrderGroup.remark.equals(""))
                    act.showDialog(GetPickingOrderGroup.remark);

                if(BaseActivity.getPackingList()==true){
                    act.printRequest();}
                else {
                    U.beepFinish(activity, "終了");
                    act.nextProcess();
                }
                //act.nextProcess();
            } else {
                act._sts(R.id.barcode, "");
                act._sts(R.id.serialno,"");
                act._sts(R.id.quantity, "");
                act._sts(R.id.location, "");
                act._sts(R.id.productCode, "");
                act._sts(R.id.productQuantity, "");
                act.setProc(act.PROC_BARCODE);
            }
            if(!act.serialList.isEmpty())
                act.serialList.clear();
        }
    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        NewShippingGroupActivity act = (NewShippingGroupActivity) activity;
        act.removePackItem();
        act.setProc(act.PROC_BARCODE);

    }

}
