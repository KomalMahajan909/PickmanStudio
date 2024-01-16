package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.NewPickingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.ShippingActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 5/28/2019.
 */

public class GetPickingOrdersList {
    String TAG = "GetPickingOrdersList";


    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        List<Map<String, String>> cList = new ArrayList<Map<String, String>>();

        Log.e(TAG, " " + list);
        final NewPickingActivity act = (NewPickingActivity) activity;

        String all_order_count = "0";


        if (list.size() > 0) {

            Log.e(TAG, " >>>>>>>>>>>>>>>>>" );
            // collect all data from response
            JsonHash row = (JsonHash) list.get(0);
            all_order_count = row.getStringOrNull("all_order_count");
            Log.e(TAG, " >>>>>>>>>>>>>>>>>all_order_count"+ all_order_count );


            if (row.containsKey("orders")) {
                Log.e(TAG, " >>>>>>>>>>>>>>>>>2222222222222222222" );
                JsonArray list2 = row.getJsonArrayOrNull("orders");
                for(int i = 0 ; i < list2.size(); i++) {

                    JsonHash row2 = (JsonHash) list2.get(i);
                    Log.e(TAG, ">>>>>>>>>3232323232");

                    HashMap<String, String> data = new HashMap<>();

                    data.put("order_no", row2.getStringOrNull("order_no"));
//                    if(!row2.getStringOrNull("batch_detail_name").equals("")){
                    String bname = row2.getStringOrNull("batch_detail_name");
                    Log.e(TAG, ">>>>>>>>>3232323232    "+bname);

                    data.put("batch_name", bname);
                    data.put("name",row2.getStringOrNull("name"));
//                        String[] splitdata =bname.split("\/");
//                }

                    cList.add(data);

                }}}
        Log.e(TAG, "category0Listttttt   "+cList+"");


        act.updateBadge1(all_order_count);

        act.setOrdersList(cList);



    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {

        U.beepError(activity, message);

    }

}
