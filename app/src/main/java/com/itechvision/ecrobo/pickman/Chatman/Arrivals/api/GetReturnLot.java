package com.itechvision.ecrobo.pickman.Chatman.Arrivals.api;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ReturnActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 12/24/2018.
 */

public class GetReturnLot {
    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.simple_spinner_dropdown_item;
    int proc = ReturnActivity.getKey();

    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {
        ReturnActivity act = (ReturnActivity) activity;
		/*ListViewItems data = new ListViewItems();*/
        List<Map<String, String>> data = new ArrayList<>();
        ArrayList<String> nyukaIdArray = new ArrayList<String>();
        ArrayList<String> classificationdata =new ArrayList<String>();
        ArrayList<String> classificationArray =new ArrayList<String>();
        ArrayList<String> supplierdata =new ArrayList<String>();
        ArrayList<String> supplierArray =new ArrayList<String>();
        Boolean directToStock =false;
        boolean supplierpresent = false;
        String product_code = null;
        String supplierInfo = null;
        Log.e("GetReturnNew", "posttttttttttttt");
        String quant=null;

        directToStock=BaseActivity.getDirectToStock();
        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            Log.e("GetReturnNew", "" + row);

            if (i == 0) {
                product_code = row.getStringOrNull("code");
            }
            if (row.getStringOrNull("rsv_date") != null && row.getStringOrNull("rsv_date").equals("") == false) {
                Log.e("GetReturnNew", "Settttdataatttttttttaaaaaaaaaaaa");
//                String quant = act._gt(R.id.quantity1).getText().toString();
//                String rsv_date = (row.getStringOrNull("rsv_date") == "") ? "        " : row.getStringOrNull("rsv_date").substring(5);
                String rsv_date = (row.getStringOrNull("rsv_date") == "") ? "                " : row.getStringOrNull("rsv_date");
                String orderNo = (row.getStringOrNull("order_no") == "") ? "    " : row.getStringOrNull("order_no");
                String compName = (row.getStringOrNull("comp_name") == "") ? "    " : row.getStringOrNull("comp_name");


                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = mdformat.format(calendar.getTime());
                Log.e("Current Date", "Current Dateeeee  " + strDate);
                if (rsv_date.equals(strDate.trim())) {
                    supplierInfo = rsv_date + "   :   " +
                            compName + "  :  " +
                            U.minusTo(row.getStringOrNull("rsv_cnt"), row.getStringOrNull("act_cnt")) + "   :   " +
                            orderNo;
                } else {
                    supplierInfo = rsv_date + "   :   " +
//                        compName + "  :  " +
                            U.minusTo(row.getStringOrNull("rsv_cnt"), row.getStringOrNull("act_cnt")) + "   :   " +
                            orderNo;
                }
                HashMap<String,String> map = new HashMap();
                map.put("sup_date",rsv_date);
                map.put("comp_name",compName);
                map.put("qty", U.minusTo(row.getStringOrNull("rsv_cnt"), row.getStringOrNull("act_cnt")) );
                map.put("order_no",orderNo);
                data.add(map);


//                data.add(supplierInfo);
                nyukaIdArray.add(row.getStringOrNull("nyuka_id"));
            }
            if(row.containsKey("stock_ids"))

            {
                JsonArray row2= row.getJsonArrayOrNull("stock_ids");
                Log.e("GetArriavllllllll","Stock Ids List  "+row2  +row2.size());
                classificationdata.add("在庫区分を選択");
                classificationArray.add("");
                for (int j = 0; j < row2.size(); j++) {
                    JsonHash row1 = (JsonHash) row2.get(j);
                    String id=row1.getStringOrNull("id");
                    String name=row1.getStringOrNull("name");
                    String classification= name + "  :  "+ id;
                    classificationdata.add(classification);
                    classificationArray.add(row1.getStringOrNull("id"));
                }
            }
            if (BaseActivity.getSupplierList() == true)
            {
                if (row.containsKey("suplier_ids"))

                {
                    JsonArray row2 = row.getJsonArrayOrNull("suplier_ids");
                    Log.e("GetArriavllllllll", "suppliers Ids List  " + row2 + row2.size());
                    supplierdata.add("仕入先を選択");
                    supplierArray.add("");
                    for (int j = 0; j < row2.size(); j++) {
                        JsonHash row1 = (JsonHash) row2.get(j);
                        String id = row1.getStringOrNull("id");
                        String name = row1.getStringOrNull("short_info");
                        String displayID = row1.getStringOrNull("disp_id");
                        String supplier = name + "  :  " + displayID + "  :  " + id;
                        supplierdata.add(supplier);
                        supplierArray.add(row1.getStringOrNull("id"));
                    }
                    supplierpresent = true;
                }
            }
            else if(BaseActivity.getNoSupplierList() == true)
            {
                if (row.containsKey("customer_ids"))

                {
                    JsonArray row2 = row.getJsonArrayOrNull("customer_ids");
                    Log.e("GetArriavllllllll", "customer_ids List  " + row2 + row2.size());
                    supplierdata.add("顧客を選択する");
                    supplierArray.add("");
                    for (int j = 0; j < row2.size(); j++) {
                        JsonHash row1 = (JsonHash) row2.get(j);
                        String id = row1.getStringOrNull("id");
                        String name = row1.getStringOrNull("short_info");
                        String displayID = row1.getStringOrNull("disp_id");
                        String supplier = name + "  :  " + displayID + "  :  " + id;
                        supplierdata.add(supplier);
                        supplierArray.add(row1.getStringOrNull("id"));
                    }
                    supplierpresent = true;
                }
            }
        }
//        if (data.size() > 0) {
//            Log.e("GetReturnNew", "datasize>>>>>>>00000000000");
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
//                    _singleItemRes, data);
//            adapter.setDropDownViewResource(_dropdownRes);
//            Spinner spinner = (Spinner) activity.findViewById(R.id.listArrival);
//            spinner.setAdapter(adapter);
//        }
//        if (classificationdata.size()> 0)
//        {
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
//                    _singleItemRes, classificationdata);
//            adapter.setDropDownViewResource(_dropdownRes);
//            Spinner spinner = (Spinner) activity.findViewById(R.id.classificationspinner);
//            spinner.setAdapter(adapter);
//        }
        if (supplierdata.size()> 0  )
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                    _singleItemRes, supplierdata);
            adapter.setDropDownViewResource(_dropdownRes);
            Spinner spinner = (Spinner) activity.findViewById(R.id.returnclassificaion);
            LinearLayout layout =(LinearLayout)activity.findViewById(R.id.layout_returnclassificaion);
            layout.setVisibility(View.VISIBLE);
            spinner.setAdapter(adapter);
        }
        act.mTextToSpeak.startSpeaking("Scanning");


        act.startTimer();
        act._sts(R.id.quantity, "1");
//        if (product_code.length() >= 4) {
//            act._sts(R.id.trimproductcode, product_code.substring(product_code.length() - 4));
//            act._sts(R.id.productCode, product_code.substring(0,product_code.length() - 4));
//        }else {
//            act._sts(R.id.trimproductcode, "");
            act._sts(R.id.productCode, product_code);


        act.setProc(ReturnActivity.PROC_QTY);
        U.beepNext();

//                U.beepSiekai(activity, null);
        act.setNyukaIdArray(nyukaIdArray);
        if(supplierpresent  && (BaseActivity.getSupplierList()|| BaseActivity.getNoSupplierList())){
            Spinner spinner = (Spinner) activity.findViewById(R.id.returnclassificaion);
            spinner.setVisibility(View.VISIBLE);
            act.setsupplierIdArray(supplierArray);}
        act.getarrivalList(data);
//        act.setclassificationIdArray(classificationArray);
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {

        ReturnActivity act = (ReturnActivity) activity;
        U.beepError(activity, message);
        act._sts(R.id.lotno, "");
        ((ReturnActivity) activity).setProc(ReturnActivity.PROC_LOT_NO);

    }
}
