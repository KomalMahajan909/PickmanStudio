package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.DirectarrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.TruckBatchActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 11/19/2019.
 */

public class GetShipmentList {
    String TAG = "GetShipmentList";
    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.spinner_dropdown_item;

    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {

		/*ListViewItems data = new ListViewItems();*/
        List<String> arrayList = new ArrayList<>();
        List<Map<String, String>> Data = new ArrayList<Map<String, String>>();
        JsonArray row1= null;
        TruckBatchActivity act = (TruckBatchActivity) activity;

        Log.e(TAG, "listtttt  "+list);
        JsonHash row = list.getJsonHashOrNull(0);
        row1 = row.getJsonArrayOrNull("batch_order");
        arrayList.add("選択する");
        for(int i = 0; i< row1.size(); i++)
        {
            JsonHash row2 = row1.getJsonHashOrNull(i);
            Map<String,String> map = (HashMap)row2;
            String val ="";

            val = row2.getStringOrNull("val");


            if (!val.equals("")) {
                boolean match = findRepeat(val, arrayList);
                if (!match) {
                    arrayList.add(val);
                    Data.add(map);
                }
            }
        }

        if(arrayList.size()>1){
            act.setShipmentList(Data);
            Spinner spinner = (Spinner) activity.findViewById(R.id.shipmentSpinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                    _singleItemRes, arrayList);
            adapter.setDropDownViewResource(_dropdownRes);
            spinner.setAdapter(adapter);
            act.setProc(TruckBatchActivity.PROC_SHIPMENT);
            if(arrayList.size() == 2){
                Log.e(TAG,"spinnerrrrr  111");
                spinner.setSelection(1);
            }
        }
        else {
            U.beepError(act,"シップメントタイプリストが見つかりません");
        }

    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((DirectarrivalActivity) activity).setProc(DirectarrivalActivity.PROC_BARCODE);
    }

    boolean findRepeat(String val,List<String> list){
        for(String x : list){
            if(x.equals(val)){
                return true;

            }
        }
        return false;
    }
}
