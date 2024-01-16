package com.itechvision.ecrobo.pickman.Chatman.Arrivals.api;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.DirectReturnActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lenovo on 12/19/2018.
 */

public class GetDirectReturnAllocation {
    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.simple_spinner_dropdown_item;


    public void post(String code, String message, JsonArray list,
            HashMap<String, String> params, Activity activity) {

        ArrayList<String> data = new ArrayList<String>();
        ArrayList<String> locationArray = new ArrayList<String>();
        long qty = 0;
        String productCode = "";

        String product_code = null;
        String location_info = null;

        data.add("   ロケ登録");
        locationArray.add("   ロケ登録");
        if(GetDirectReturn.returnlist.size()>0){
                for (int i = 0; i < list.size(); i++) {
                    JsonHash row = (JsonHash) list.get(i);
                    String loc = row.getStringOrNull("location");

                    if ("".equals(loc) == false) {

                        if (i == 0) {
                            product_code = row.getStringOrNull("code");
                        }

                        String location = (row.getStringOrNull("location") == "") ? "        " : row.getStringOrNull("location");
                        if(!location.equals("z") && !location.equals("y"))
                            DirectReturnActivity.location_fixed = location;

                        String quantity = (row.getStringOrNull("quantity") == "") ? "                " : row.getStringOrNull("quantity");
                        location_info = "      " + location + "           :          " + quantity;
                        data.add(location_info);
                        locationArray.add(row.getStringOrNull("location"));
                    }
                    if (qty == 0) qty = Long.parseLong(row.getStringOrNull("rest"));
                    if (i == 0) productCode = row.getStringOrNull("code");
                }

                if (data.size() > 0) {

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                    _singleItemRes, data);
                    adapter.setDropDownViewResource(_dropdownRes);
                    Spinner spinner = (Spinner) activity.findViewById(R.id.listAllocation);
                    spinner.setAdapter(adapter);
                }


                DirectReturnActivity act = (DirectReturnActivity) activity;
                act.startTimer();
                act.getarrivalList(GetDirectReturn.returnlist);
                act.setLocationArray(locationArray);
        }
        else {
            U.beepError(activity, message);
            ((DirectReturnActivity) activity).setProc(DirectReturnActivity.PROC_BARCODE);
        }
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        if(DirectReturnActivity.returndata){
            U.beepError(activity, message);
            Log.e("GetNewArrivalAllocation", "datasize>>>>>>>111111" +DirectReturnActivity.returndata);
        }
        DirectReturnActivity.returndata= false;
        ((DirectReturnActivity) activity).setProc(DirectReturnActivity.PROC_BARCODE);
    }
}
