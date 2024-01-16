package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.DirectarrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.TotalPickListActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.TruckBatchActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by lenovo on 1/2/2020.
 */

public class GetPickData {
    String TAG = "GetPickData";


    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {

		/*ListViewItems data = new ListViewItems();*/
        Log.e(TAG, "listtttt  "+list);

        List<Map<String, String>> pickData = new ArrayList<Map<String, String>>();
        List<Map<String, String>> usedData = new ArrayList<Map<String, String>>();
        List<Map<String, String>> otheruserData = new ArrayList<Map<String, String>>();

        TotalPickListActivity act = (TotalPickListActivity) activity;

        JsonHash row = (JsonHash) list.get(0);
        JsonArray list1 = row.getJsonArrayOrNull("picking_none");


        for(int i = 0; i< list1.size(); i++)
        {
            JsonHash row2 = list1.getJsonHashOrNull(i);
            Map<String,String> map = (HashMap)row2;
            Log.e(TAG, "listtttt333333  "+row2);

            String val = row2.getStringOrNull("picking_no");

            if (!val.equals("")) {
                boolean match = findRepeat(val, pickData);
                if (!match) {
                    pickData.add(map);
                }
            }
        }

        JsonArray list2 = row.getJsonArrayOrNull("picking_user");

        Log.e(TAG, "listtttt2222222  "+list2);

        for(int i = 0; i< list2.size(); i++)
        {
            JsonHash row2 = list2.getJsonHashOrNull(i);
            Map<String,String> map = (HashMap)row2;
            Log.e(TAG, "listtttt333333  "+row2);

            String val = row2.getStringOrNull("picking_no");

            if (!val.equals("")) {
                boolean match = findRepeat(val, usedData);
                if (!match) {
                    usedData.add(map);
                }
            }
        }

        JsonArray list3 = row.getJsonArrayOrNull("picking_other");
        Log.e(TAG, "listtttt2222222  "+list3);

        for(int i = 0; i< list3.size(); i++)
        {
            JsonHash row2 = list3.getJsonHashOrNull(i);
            Map<String,String> map = (HashMap)row2;
            Log.e(TAG, "listtttt333333  "+row2);

            String val = row2.getStringOrNull("picking_no");

            if (!val.equals("")) {
                boolean match = findRepeat(val, otheruserData);
                if (!match) {
                    otheruserData.add(map);
                }
            }
        }

        if(pickData.size()>0 || usedData.size()>0 || otheruserData.size()>0 ){
            act.setListData(pickData,usedData,otheruserData);
            int size =usedData.size()+otheruserData.size();
            act.updateBadge1(usedData.size()+"");
            act.updateBadge2(size+"");

                act.setProc(TotalPickListActivity.PROC_BARCODE);

        }
        else {
            U.beepError(act,"選択リストが見つかりません");
        }

    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }

    boolean findRepeat(String val,List<Map<String, String>> list){
        for(Map<String, String> map : list){
            String x = map.get("picking_no");
            if(x.equals(val)){
                return true;

            }
        }
        return false;
    }
}
