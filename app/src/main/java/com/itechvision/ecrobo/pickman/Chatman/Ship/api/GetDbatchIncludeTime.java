package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Ship.DbatchIncludeActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 5/9/2019.
 */

public class GetDbatchIncludeTime {
        private String TAG = "GetDbatchIncludeTime";


        public void post(String code, String message, JsonArray list,
                         HashMap<String, String> params, Activity activity) {
            List<Map<String, String>> data = new ArrayList<>();
            DbatchIncludeActivity act = (DbatchIncludeActivity) activity;

            for (int i = 0; i < list.size(); i++) {
                JsonHash row = (JsonHash) list.get(i);
                Log.e(TAG,"<<<<<<<<<<<<<<<<<<<"+row);
            JsonArray products = row.getJsonArrayOrNull("include_products");
                Log.e(TAG,"<<<<<<<<<<<<<<<<<<<11111111111111111"+products);
            if (products != null) {
                for (int j=0;j<products.size();j++){
                    JsonHash row1 = (JsonHash) products.get(j);
                    Log.e(TAG,"<<<<<<<<<<<<<<<<<<<2222222222222"+row1);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("location",row1.getStringOrNull("location"));
                    map.put("code",row1.getStringOrNull("code"));
                    map.put("barcode",row1.getStringOrNull("barcode"));
                    map.put("quantity",row1.getStringOrNull("quantity"));
                    map.put("tray_quantity",row1.getStringOrNull("tray_quantity"));
                    map.put("tray",row1.getStringOrNull("tray_no"));

                    data.add(map);
                    Log.e(TAG,"<<<<<<<<<<<<<<<<<<<3333333333333"+data);
                }
            }}

            if(data.size()> 0 ){
            act.setProductsArray(data);
            act._sts(R.id.barcode,"");
            act._sts(R.id.quantity,"");
            act._sts(R.id.trayno,"");
            act._sts(R.id.totalQuantity,"");
            act.setProc(DbatchIncludeActivity.PROC_BARCODE);}
            else U.beepError(activity, "no product found");
        }
        public void valid(String code, String message, JsonArray list,
                          HashMap<String, String> params, Activity activity) {
            U.beepError(activity, message);
        }
    }



