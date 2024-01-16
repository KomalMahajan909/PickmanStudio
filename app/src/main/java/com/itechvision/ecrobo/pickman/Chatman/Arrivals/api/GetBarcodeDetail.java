package com.itechvision.ecrobo.pickman.Chatman.Arrivals.api;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.RFIDArrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.RFTagCheckActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetPiking;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by lenovo on 7/16/2019.
 */

public class GetBarcodeDetail {
    private String TAG = "GetBarcodeDetail";

    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.spinner_dropdown_item;

    TextToSpeak mTextToSpeak;


    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {

        ArrayList<String> arr = new ArrayList<String>();
        ArrayList<String> data = new ArrayList<String>();
        mTextToSpeak = new TextToSpeak(activity);
        RFTagCheckActivity act = (RFTagCheckActivity) activity;

        String product_code = null;


        JsonHash row = (JsonHash) list.get(0);
        if(row.containsKey("product_detail")){
        JsonHash row1 = (JsonHash) row.getJsonHashOrNull("product_detail");
        product_code = row1.getStringOrNull("code");}
        arr.add("Select RFID");
        if(row.containsKey("rf_ids")){
            JsonArray list1 = (JsonArray) row.getJsonArrayOrNull("rf_ids");
            for(int i=0; i< list1.size() ;i++ ) {
                JsonHash row2 = (JsonHash) list1.get(i);
                String rf_id = row2.getStringOrNull("rf_id");
                if(!findRepeat(arr,rf_id))
                arr.add(rf_id);
            }
        }
        Log.e(TAG,"Listttt    "+arr);
        mTextToSpeak.startSpeaking("scanning");
        act.startTimer();


        act.setRFlist(arr);

            String rf = "";
            for(String val : arr) {
                rf = val.replaceFirst("^0+(?!$)", "");
                data.add(rf);
            }
        if (data.size()> 0)
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                    _singleItemRes, data){
                @Override
                public boolean isEnabled(int position){
                    if(position == 0)
                    {
                        // Disable the second item from Spinner
                        return false;
                    }
                    else
                    {
                        return true;
                    }
                }};

            adapter.setDropDownViewResource(_dropdownRes);
            Spinner spinner = (Spinner) activity.findViewById(R.id.rfid_list);
            spinner.setAdapter(adapter);
        }
//            new SweetAlertDialog(activity)
//                    .setTitleText("Sku has multiple RFIDs \n" + rf)
//                    .show();

        act.setProc(RFTagCheckActivity.PROC_RFID);



    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((RFIDArrivalActivity) activity).setProc(RFIDArrivalActivity.PROC_BARCODE);
    }
  boolean findRepeat(ArrayList<String> list , String rf){
        for(String val : list){
            if(val.equals(rf))
                return true;
        }
        return false;
  }
}
