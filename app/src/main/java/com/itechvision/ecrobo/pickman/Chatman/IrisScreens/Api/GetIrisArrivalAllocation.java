package com.itechvision.ecrobo.pickman.Chatman.IrisScreens.Api;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.DirectarrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.IrisScreens.IrisDirectArrivalActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetIrisArrivalAllocation {
    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.spinner_dropdown_item;

    String TAG = "GetNewArrivalAllocation";


    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {

        ArrayList<String> data = new ArrayList<String>();
        ArrayList<String> locationArray = new ArrayList<String>();
        List<Map<String, String>> Datalist =  new ArrayList<Map<String, String>>();

        IrisDirectArrivalActivity act = (IrisDirectArrivalActivity) activity;
        long qty = 0;
        String productCode = "";
        String barcode = "";
        Log.e(TAG, "listSize  " + list.size());
        String product_code = null;
        String location_info = null;
        if(GetIrisArrivalDirect.arrivalList>0){

            data.add("   ロケ登録");
            locationArray.add("   ロケ登録");

            for (int i = 0; i < list.size(); i++) {
                JsonHash row = (JsonHash) list.get(i);
                Log.e(TAG, " " + row);
                String loc = row.getStringOrNull("location");

                if ("".equals(loc) == false) {
                    Log.e(TAG, "Location is z false");

                    if (i == 0) {
                        product_code = row.getStringOrNull("code");
                    }
//                    if (row.getStringOrNull("location") != null && row.getStringOrNull("location").equals("") == false) {
                    String location = (row.getStringOrNull("location") == "") ? "        " : row.getStringOrNull("location");
                    if(!location.equals("z") && !location.equals("y"))
                        DirectarrivalActivity.location_fixed = location;

                    int size = location.length();

                    Log.e(TAG, "sizee : " + size);
                    String quantity = (row.getStringOrNull("quantity") == "") ? "                " : row.getStringOrNull("quantity");


                    location_info = "      " + location + "           :          " + quantity;

                    data.add(location_info);

                    locationArray.add(row.getStringOrNull("location"));
                    // code for multicode
                    if(act.multicode){
                        Map<String,String> map = new HashMap<>();
                        map.put("location", row.getStringOrNull("location"));
                        map.put("quantity", row.getStringOrNull("quantity"));
                        map.put("code", row.getStringOrNull("code"));
                        Datalist.add(map);
                        Log.e(TAG, "Datalist  "+map);
                    }

                }
                if (qty == 0) qty = Long.parseLong(row.getStringOrNull("rest"));
                if (i == 0) productCode = row.getStringOrNull("code");

            }

            if (data.size() > 0) {
                Log.e(TAG, "Size greater than 0");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                        _singleItemRes, data);
                adapter.setDropDownViewResource(_dropdownRes);
                Spinner spinner = (Spinner) activity.findViewById(R.id.listAllocation);
                spinner.setAdapter(adapter);
            }
            U.beepSuccess();
            act.startTimer();


            act.setLocationArray(locationArray);

            if(Datalist.size()>0)
                act.setMulticodeLocations(Datalist);

            GetIrisArrivalDirect.arrivalList=0;
            //  act.LISTOPEN();
//            }
        }
        else {
            U.beepError(activity, message);
            ((IrisDirectArrivalActivity) activity).setProc(IrisDirectArrivalActivity.PROC_BARCODE);
        }
    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((IrisDirectArrivalActivity) activity).setProc(IrisDirectArrivalActivity .PROC_BARCODE);
    }



}
