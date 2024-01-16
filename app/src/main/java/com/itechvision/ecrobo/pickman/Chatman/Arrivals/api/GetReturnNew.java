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

public class GetReturnNew {
    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.simple_spinner_dropdown_item;
    int proc = ReturnActivity.getKey();
    private String TAG = "GetReturnNew";

    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {
        /*ListViewItems data = new ListViewItems();*/
        List<Map<String, String>> data = new ArrayList<>();
        ArrayList<String> nyukaIdArray = new ArrayList<String>();

        ArrayList<String> classificationdata =new ArrayList<String>();
        ArrayList<String> classificationArray =new ArrayList<String>();

        ArrayList<String> supplierdata =new ArrayList<String>();
        ArrayList<String> supplierArray =new ArrayList<String>();

        List<Map<String, String>> lotAndExpiration = new ArrayList<Map<String, String>>();

        String attribute= "0";

        String product_code = null , product_name = null;
        String supplierInfo = null;
        Boolean directToStock =false;
        boolean supplierpresent = false;
        Log.e("GetReturnNew", "posttttttttttttt");
        String quant = null;
        String barcode;
        directToStock=BaseActivity.getDirectToStock();
        ReturnActivity act = (ReturnActivity) activity;

//        if (BaseActivity.getLotPress() ==false) {
        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            Log.e("GetReturnNew", "" + row);

            if (i == 0) {
                product_code = row.getStringOrNull("code");
                product_name = row.getStringOrNull("name");

            }
            if (row.getStringOrNull("rsv_date") != null && row.getStringOrNull("rsv_date").equals("") == false) {
                Log.e("GetReturnNew", "Settttdataatttttttt");
                String rsv_date = (row.getStringOrNull("rsv_date") == "") ? "                " : row.getStringOrNull("rsv_date");
                String orderNo = (row.getStringOrNull("order_no") == "") ? "    " : row.getStringOrNull("order_no");
                String compName = (row.getStringOrNull("comp_name") == "") ? "    " : row.getStringOrNull("comp_name");
                String lot = row.getStringOrNull("lot") ;
                String expiration_date = row.getStringOrNull("expiration_date") ;
                String return_class_name = row.getStringOrNull("return_class_name") ;
                String return_class = row.getStringOrNull("shop_return_class") ;

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = mdformat.format(calendar.getTime());
                Log.e("Current Date", "Current Date " + strDate);
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
                map.put("lot",lot);
                map.put("expiration_date",expiration_date);
                map.put("return_class_name",return_class_name);
                map.put("return_class",return_class);
                data.add(map);
//                    data.add(supplierInfo);
                nyukaIdArray.add(row.getStringOrNull("nyuka_id"));
            }
            if (BaseActivity.getSupplierList() == true)
            {
                if (row.containsKey("suplier_ids"))

                {
                    JsonArray row2 = row.getJsonArrayOrNull("suplier_ids");
                    Log.e("GetArriavl", "suppliers Ids List  " + row2 + row2.size());
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
            else if(BaseActivity.getNoSupplierList() == true) {
                if (row.containsKey("customer_ids")) {
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
            if (row.containsKey("attribute_type") && BaseActivity.getLotPress()) {
                attribute = row.getStringOrNull("attribute_type");
                if (!attribute.equals("0")){
                    HashMap<String,String> map = new HashMap<>();
                    map.put("lot", row.getStringOrNull("lot"));
                    map.put("expiration_date", row.getStringOrNull("expiration_date"));
                    map.put("nyuka_id", row.getStringOrNull("nyuka_id"));
                    lotAndExpiration.add(map);
                    Log.e("GetArrivallll","lotttttt 111111   " +lotAndExpiration);
                }

            }

            if(row.containsKey("stock_ids")) {
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
        }
        if (data.size() > 0) {
//                Log.e("GetReturnNew", "datasize>>>>>>>00000000000");
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
//                        _singleItemRes, data);
//                adapter.setDropDownViewResource(_dropdownRes);
//                Spinner spinner = (Spinner) activity.findViewById(R.id.listArrival);
//                spinner.setAdapter(adapter);
//            }
            if(!nyukaIdArray.get(0).equals("999") && BaseActivity.getLotPress()){
                for (Map<String,String> map : lotAndExpiration){
                    Log.e(TAG, "Nukaa  "+nyukaIdArray.get(0));
                    String nyukaa = nyukaIdArray.get(0);
                    if(map.get("nyuka_id").equals(nyukaa)){
                        Log.e(TAG, "12131   "+nyukaIdArray.get(0));
                        if(attribute.equals("1")){
                            Log.e(TAG, "22    "+map.get("lot"));
                            act._sts(R.id.lotno,map.get("lot"));
                        }
                        else  if(attribute.equals("2")){
                            Log.e(TAG, "33 "+map.get("expiration_date"));
                            act._sts(R.id.expirationdate,map.get("expiration_date"));
                        }
                        else  if(attribute.equals("3")){
                            Log.e(TAG, "321 expiration"+map.get("expiration_date")  +"    lottt  "+map.get("lot"));
                            act._sts(R.id.expirationdate,map.get("expiration_date"));
                            act._sts(R.id.lotno,map.get("lot"));
                        }
                    }}
            }
        }

//        else if(nyukaIdArray.get(0).equals("999") && BaseActivity.getLotPress()){
//            if(attribute.equals("1")){
//                act.setProc(ReturnActivity.PROC_LOT_NO);
//            }
//            else  if(attribute.equals("2")){
//                act.setProc(ReturnActivity.PROC_EXPIRATION);
//            }
//            else  if(attribute.equals("3")){
//                act.setProc(ReturnActivity.PROC_LOT_NO);
//
//            }
//        }

        if(BaseActivity.getLotPress()){
            act.setAttributeValue(attribute);
            act.ArrivalLotdata(lotAndExpiration);
        }


        if (supplierdata.size()> 0 ) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(), _singleItemRes, supplierdata);
            adapter.setDropDownViewResource(_dropdownRes);
            Spinner spinner = (Spinner) activity.findViewById(R.id.returnclassificaion);
            LinearLayout layout =(LinearLayout)activity.findViewById(R.id.layout_returnclassificaion);
            layout.setVisibility(View.VISIBLE);
            spinner.setAdapter(adapter);
        }
        act.mTextToSpeak.startSpeaking("Scanning");
        act.startTimer();
        act._sts(R.id.quantity, "1");
        act.mTextToSpeak.resetQueue();
        act.mTextToSpeak.startSpeaking("1");
        act._sts(R.id.productCode, product_code);
        act._stxtv(R.id.productName, product_name);
        act.productName.setSelected(true);

        if(attribute.equals("0")){
            Log.e(TAG, "3333333121  ");
            act.setProc(ReturnActivity.PROC_QTY);}
        else if(attribute.equals("1")){
            Log.e(TAG, "333333331112221   lotnooo   " +act._gts(R.id.lotno));
            if(act._gts(R.id.lotno).equals(""))
                act.setProc(ReturnActivity.PROC_LOT_NO);
            else
                act.setProc(ReturnActivity.PROC_QTY);
            act.lotexist=true;
        }
        else if(attribute.equals("2") ){
            Log.e(TAG, "331112221   expiration   " +act._gts(R.id.expirationdate));
            if(act._gts(R.id.expirationdate).equals("")) {
                Log.e(TAG, "3331112221   expirationn11111   " +act._gts(R.id.expirationdate));
                act.setProc(ReturnActivity.PROC_EXPIRATION);
            }  else{
                Log.e(TAG, "3333311122211" );
                act.setProc(ReturnActivity.PROC_QTY);
            }
        }  else if(attribute.equals("3")){
            Log.e(TAG, "333331112221111   lotnooo   " +act._gts(R.id.lotno)+"expirationnnn    "+act._gts(R.id.expirationdate));
            if(act._gts(R.id.lotno).equals(""))
                act.setProc(ReturnActivity.PROC_LOT_NO);
            else if(act._gts(R.id.expirationdate).equals(""))
                act.setProc(ReturnActivity.PROC_EXPIRATION);
            else
                act.setProc(ReturnActivity.PROC_QTY);
            act.lotexist=true;
        }

//            U.beepNext();
//        }
//        else
        act.setclassificationIdArray(classificationArray);

        if(supplierpresent && (BaseActivity.getSupplierList()|| BaseActivity.getNoSupplierList())){
            Spinner spinner = (Spinner) activity.findViewById(R.id.returnclassificaion);
            spinner.setVisibility(View.VISIBLE);
            LinearLayout layout =(LinearLayout)activity.findViewById(R.id.layout_returnclassificaion);
            layout.setVisibility(View.VISIBLE);
            act.setsupplierIdArray(supplierArray);}
        act.setNyukaIdArray(nyukaIdArray);
        act.getarrivalList(data);

    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {

        U.beepError(activity, message);
        ((ReturnActivity) activity).setProc(ReturnActivity.PROC_BARCODE);

    }
}
