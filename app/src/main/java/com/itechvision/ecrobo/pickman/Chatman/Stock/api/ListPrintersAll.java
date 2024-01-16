package com.itechvision.ecrobo.pickman.Chatman.Stock.api;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Stock.PrinterSelectActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lenovo on 3/5/2019.
 */

public class ListPrintersAll {
    private final int _singleItemRes = R.layout.printer_list_spinner_item;
    private final int _dropdownRes = R.layout.simple_spinner_dropdown_item;
    String TAG = "ListPrinterAll";

    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, final Activity activity) {
        // TODO 自動生成されたメソッド・スタブ
        ArrayList<String> data = new ArrayList<String>();
        ArrayList<String> ids = new ArrayList<String>();

        // List printers
        data.add("プリンタを選択");
        ids.add("0");
        Log.e(TAG," listtt "+list);
        JsonHash row1 = (JsonHash) list.get(0);
        Log.e(TAG," listtt row"+row1);
        JsonArray list2 = row1.getJsonArrayOrNull("ap_printer");
        for (int i = 0; i < list2.size(); i++) {
            JsonHash row = (JsonHash) list2.get(i);
            Log.e(TAG," listtt row"+row);
            if(row.containsKey("printer_name")){
                if (row.getStringOrNull("printer_name") != null && row.getStringOrNull("printer_name").equals("") == false) {
                    String printer =row.getStringOrNull("printer_name");
                    boolean match = findRepeat(data,printer);
                    if(!match){
                        data.add(printer);
                        ids.add(row.getStringOrNull("printer_id"));
                    }

                }}
        }

        // Adapterの作成

        if (data.size()> 0)
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                _singleItemRes, data);
            adapter.setDropDownViewResource(_dropdownRes);
            Spinner spinner = (Spinner) activity.findViewById(R.id.spinner_barcode_arrival);
            spinner.setAdapter(adapter);

            ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(activity.getApplicationContext(),
                    _singleItemRes, data);
            adapter5.setDropDownViewResource(_dropdownRes);
            Spinner spinner5 = (Spinner) activity.findViewById(R.id.spinner_barcode_slip);
            spinner5.setAdapter(adapter);


            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(activity.getApplicationContext(),
                    _singleItemRes, data);
            adapter1.setDropDownViewResource(_dropdownRes);
            Spinner spinner1 = (Spinner) activity.findViewById(R.id.spinner_integrated_printer);
            spinner1.setAdapter(adapter1);

            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(activity.getApplicationContext(),
                    _singleItemRes, data);
            adapter2.setDropDownViewResource(_dropdownRes);
            Spinner spinner2 = (Spinner) activity.findViewById(R.id.spinner_invoice_printer);
            spinner2.setAdapter(adapter2);

            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(activity.getApplicationContext(),
                    _singleItemRes, data);
            adapter3.setDropDownViewResource(_dropdownRes);
            Spinner spinner3 = (Spinner) activity.findViewById(R.id.spinner_postpay_printer);
            spinner3.setAdapter(adapter3);

            ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(activity.getApplicationContext(),
                    _singleItemRes, data);
            adapter4.setDropDownViewResource(_dropdownRes);
            Spinner spinner4 = (Spinner) activity.findViewById(R.id.spinner_csvfix);
            spinner4.setAdapter(adapter3);


        }
        PrinterSelectActivity act = (PrinterSelectActivity) activity;
        act.setPrinterArray(data);
        act.setPrinterIds(ids);
        act.setSelectedPRinters();

    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        // TODO 自動生成されたメソッド・スタブ
        U.beepError(activity, message);
        Log.d("LISTSHOPS", "Not Success");
    }
boolean findRepeat(ArrayList<String> list, String val){
        boolean match = false;
       for(String s :list){
           if(s.equals(val)){
               match = true;
               break;
           }
       }
       return match;
}
}
