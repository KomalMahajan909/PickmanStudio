package com.itechvision.ecrobo.pickman.Chatman.Stock.api;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ArrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.InventoryActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lenovo on 2/26/2019.
 */

public class ValidateBarcode {
    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.spinner_dropdown_item;
    String TAG = "ValidateBarcode";
    public boolean multicode = false;


    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        ArrayList<String> PartnoList =new ArrayList<String>();
        String pCode = "";
        String barcode = "";
        PartnoList.add("Select Part ");
        if(BaseActivity.getLotPress()==false) {
            InventoryActivity act = ((InventoryActivity) activity);
            for (int i = 0; i < list.size(); i++) {
                JsonHash row = (JsonHash) list.get(i);

                if(row.containsKey("multi_codes")){
                    multicode = true;
                    Log.e("GetArrivallll","multicode  " +multicode);
                }
                pCode = row.getStringOrNull("code");
                barcode = row.getStringOrNull("barcode");

                boolean match = findRepeat(row.getStringOrNull("code"), PartnoList);
                if(!match)
                    PartnoList.add(row.getStringOrNull("code"));

            }
            if(multicode) {
                act.partnoLayout.setVisibility(View.VISIBLE);
                act.getmultiPartarrivalList( multicode, PartnoList);


                if (PartnoList.size()> 1)
                {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                            _singleItemRes, PartnoList);
                    adapter.setDropDownViewResource(_dropdownRes);
                    Spinner spinner = (Spinner) activity.findViewById(R.id.partnoSpinner);
                    spinner.setAdapter(adapter);
                    if(PartnoList.size() == 2){
                        Log.e(TAG,"spinnerrrrr  ");
                        spinner.setSelection(1);
                    }

                }

                act.setProc(InventoryActivity.PROC_PARTNO);
            }
            else {

                act.setDataProc(pCode,barcode);
                act.setProc(InventoryActivity.PROC_QTY);
//                act.createNewEntry(pCode);
//                act.updateBadge1();
            }
        }
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        InventoryActivity act = ((InventoryActivity) activity);
        U.beepError(activity, message);
        act.setProc(act.PROC_BARCODE);
        act._sts(R.id.quantity, "");
        act._sts(R.id.productCode, "");
    }

    boolean findRepeat(String part,ArrayList<String> PartList){
        for(String x : PartList){
            if(x.equals(part)){
                return true;

            }
        }
        return false;
    }
}
