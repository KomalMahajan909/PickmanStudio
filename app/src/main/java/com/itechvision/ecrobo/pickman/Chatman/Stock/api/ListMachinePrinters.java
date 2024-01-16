package com.itechvision.ecrobo.pickman.Chatman.Stock.api;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Stock.PrinterMachineActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 7/3/2019.
 */

public class ListMachinePrinters {
    private final int _singleItemRes = R.layout.printer_list_spinner_item;
        private final int _dropdownRes = R.layout.simple_spinner_dropdown_item;
        String TAG = "ListMachinePrinters";

        public void post(String code, String message, JsonArray list,
                         HashMap<String, String> params, final Activity activity) {
            // TODO 自動生成されたメソッド・スタブ
            ArrayList<String> data = new ArrayList<String>();
            ArrayList<Map<String,String>> list1 = new ArrayList<>();

            // List printers
            data.add("パソコン名選択");

            Log.e(TAG," listtt "+list);
            JsonHash row1 = (JsonHash) list.get(0);
            Log.e(TAG," listtt row"+row1);
            JsonArray list2 = row1.getJsonArrayOrNull("ap_printer");
            JsonHash list3 = row1.getJsonHashOrNull("machines");
            Log.e(TAG,"List3333  "+list3);
            for (int i = 0; i < list2.size(); i++) {
                String machine = "";

                HashMap <String,String> map1 = new HashMap<>();

                JsonHash row = (JsonHash) list2.get(i);
                Log.e(TAG," listtt row"+row);
                if(row.containsKey("printer_name")){
                    if (row.getStringOrNull("machine_id") != null && row.getStringOrNull("machine_id").equals("") == false) {
                        machine = row.getStringOrNull("machine_id");
                        boolean match = findRepeat(data,machine);
                    if(!match)
                    data.add(machine);

                        map1.put("machine", machine);
                        map1.put("printer",row.getStringOrNull("printer_name"));
                        map1.put("printerID",row.getStringOrNull("printer_id"));

                        list1.add(map1);
                    }}
            }

            // Adapterの作成

            if (data.size()> 0)
            {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                        _singleItemRes, data);
                adapter.setDropDownViewResource(_dropdownRes);
                Spinner spinner = (Spinner) activity.findViewById(R.id.barcode_machine);
                spinner.setAdapter(adapter);

                ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(activity.getApplicationContext(),
                        _singleItemRes, data);
                adapter5.setDropDownViewResource(_dropdownRes);
                Spinner spinner5 = (Spinner) activity.findViewById(R.id.barcodeSlipmachine);
                spinner5.setAdapter(adapter);


                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(activity.getApplicationContext(),
                        _singleItemRes, data);
                adapter1.setDropDownViewResource(_dropdownRes);
                Spinner spinner1 = (Spinner) activity.findViewById(R.id.integrated_machine);
                spinner1.setAdapter(adapter1);

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(activity.getApplicationContext(),
                        _singleItemRes, data);
                adapter2.setDropDownViewResource(_dropdownRes);
                Spinner spinner2 = (Spinner) activity.findViewById(R.id.invoice_machine);
                spinner2.setAdapter(adapter2);

                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(activity.getApplicationContext(),
                        _singleItemRes, data);
                adapter3.setDropDownViewResource(_dropdownRes);
                Spinner spinner3 = (Spinner) activity.findViewById(R.id.postpay_machine);
                spinner3.setAdapter(adapter3);

                ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(activity.getApplicationContext(),
                        _singleItemRes, data);
                adapter4.setDropDownViewResource(_dropdownRes);
                Spinner spinner4 = (Spinner) activity.findViewById(R.id.csv_machine);
                spinner4.setAdapter(adapter3);


                ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(activity.getApplicationContext(),
                        _singleItemRes, data);
                adapter6.setDropDownViewResource(_dropdownRes);
                Spinner spinner6 = (Spinner) activity.findViewById(R.id.file_machine);
                spinner6.setAdapter(adapter6);


            }
            PrinterMachineActivity act = (PrinterMachineActivity) activity;
            act.setPrinterArray(data);
            act.setMachineData(list3);
            boolean done = act.setSelectedPRinters();
            if(done)act.setPrinterData(list1);

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
