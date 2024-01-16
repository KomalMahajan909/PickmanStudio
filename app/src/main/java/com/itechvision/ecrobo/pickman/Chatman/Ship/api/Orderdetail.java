package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.ShippingActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 10/25/2018.
 */

public class Orderdetail {

    public  static String remark= "";
    String TAG = "Orderdetail";


    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        List<Map<String, String>> cList = new ArrayList<Map<String, String>>();

        Map<String, String> map = new HashMap<String, String>();
//        ShippingActivity.count=0;
        Log.e(TAG, " " + list);
        final ShippingActivity act = (ShippingActivity) activity;

        String all_order_count = "0";
        String all_row_count = "0";



        if (list.size() > 0) {
            remark= "";
            Log.e(TAG, " >>>>>>>>>>>>>>>>>" );
            // collect all data from response
            JsonHash row = (JsonHash) list.get(0);
            all_order_count = row.getStringOrNull("all_order_count");
            Log.e(TAG, " >>>>>>>>>>>>>>>>>all_order_count"+ all_order_count );
            String not_inspected = row.getStringOrNull("not_inspection_row_count");
            Log.e(TAG, " >>>>>>>>>>>>>>>>>not_inspection_row_count"+ not_inspected );

            String short_inspected = row.getStringOrNull("shortage_row_count");

            Log.e(TAG, " >>>>>>>>>>>>>>>>>shortage_row_count"+ short_inspected );


            all_row_count = U.plusTo(not_inspected, short_inspected);


            if (row.containsKey("data")) {
                Log.e(TAG, " >>>>>>>>>>>>>>>>>2222222222222222222" );
                JsonArray list2 = row.getJsonArrayOrNull("data");
                for(int i = 0 ; i < list2.size(); i++) {
                    String category = "";
                    Log.e(TAG, "1111111111 " + list2);
                    JsonHash row2 = (JsonHash) list2.get(i);
                    Log.e(TAG, ">>>>>>>>>3232323232" );
                    map = (HashMap) row2;
                    if(! map.get("quantity").equals("0")){
                        HashMap<String,String > data = new HashMap<>();
                        Log.e(TAG, " " + map);

                        // insert data into desire variables and fields for further processing.

                        data.put("code",map.get("code"));
                        data.put("quantity",map.get("inspection_num"));
                        data.put("product_qty",map.get("product_qty"));
                        if(map.get("category").equals("0"))
                        data.put("category","商品");
                        else  if(map.get("category").equals("1"))
                            data.put("category","同梱物");
                        else
                            data.put("category","作業");


                     cList.add(data);
                }

                Log.e(TAG, "category0Listttttt   "+cList+"");

                act.updateBadge1(all_row_count);
                act.updateBadge3(all_order_count );
                act.getProductList(cList);

               }}}
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {

        U.beepError(activity, message);

    }

}
