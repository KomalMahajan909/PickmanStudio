package com.itechvision.ecrobo.pickman.Chatman.Arrivals.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ReturnAllocationActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ReturnStockActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 1/21/2019.
 */

public class GetReturnOrder {

    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    public static int lotpresent = 0;
    String TAG = "GetReturnOrder";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        ReturnStockActivity act = (ReturnStockActivity) activity;
        lotpresent =0;


        String order_id = "";
        String flag = "";

        Long qtyCount = 0L;
        if (list.size() > 0) {
            JsonHash row = (JsonHash) list.get(0);

            JsonArray list2 = row.getJsonArrayOrNull("order");
            // collect all data from response
            JsonHash row2 = (JsonHash) list2.get(0);
            order_id = row2.getStringOrNull("order_id");
            if(row2.containsKey("sendback_flag"))
                flag = row2.getStringOrNull("sendback_flag");


            if (row.containsKey("order_sub")) {

                JsonArray list3 = row.getJsonArrayOrNull("order_sub");


                if (list3 != null) {
                    for (int i = 0; i < list3.size(); i++) {
                        String attribute = "0";
                        JsonHash row3 = (JsonHash) list3.get(i);

                        Map<String, String> map = new HashMap<String, String>();
                        map.put("quantity", row3.getStringOrNull("num"));
                        map.put("barcode", row3.getStringOrNull("barcode"));
                        map.put("code", row3.getStringOrNull("code"));
                        map.put("orderSubId", row3.getStringOrNull("order_sub_id"));
                        map.put("processedCnt", "0");

//                        Log.e(TAG,"attribute_type  "+row3.getStringOrNull("attribute_type"));
//                        if(row3.containsKey("attribute_type"))
//                            attribute = row3.getStringOrNull("attribute_type");
//                        Log.e(TAG,"attribute_type11111111  "+attribute);
//                        if (Integer.parseInt(attribute) > 0)
//                            ++lotpresent;
//
//                        Log.e(TAG,"lotpresent11111111  "+lotpresent);
//                        qtyCount += Long.parseLong(row3.getStringOrNull("num"));
                        data.add(map);
                    }
                }
            }

        }
        act.orderID = order_id;


//        act.setProductList(data);
        act.startTimer();
//        if (lotpresent > 0 && act.orderrb.isChecked())
//            CommonDialogs.customToast(activity, "ロットか消費期限管理する商品が含まれているいるため注文ごとに検品することが出来ません。");
//
//        else {

        if(flag.equalsIgnoreCase("1"))
        {
            act.showReturndialog();
        }
        else {
            U.beepNext();
            act.setProc(ReturnStockActivity.PROC_RETURN_REASON);
        }
//        }
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);

        ((ReturnStockActivity) activity).setProc(ReturnStockActivity.PROC_ORDER_ID);

    }

}
