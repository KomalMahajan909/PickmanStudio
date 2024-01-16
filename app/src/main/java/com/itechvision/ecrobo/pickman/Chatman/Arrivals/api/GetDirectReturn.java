package com.itechvision.ecrobo.pickman.Chatman.Arrivals.api;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.DirectReturnActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ReturnAllocationActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GetDirectReturn {
    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.simple_spinner_dropdown_item;
    public static List<Map<String, String>> returnlist=new ArrayList<>();
    private String TAG = "GetDirectReturn",goodclass="",badclass="";
 public static   Spinner spinner ;
    int pos =0;
    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {

        TextToSpeak mTextToSpeak=new TextToSpeak(activity);
        List<Map<String, String>> data = new ArrayList<>();
        ArrayList<String> nyukaIdArray = new ArrayList<String>();
        ArrayList<String> classificationdata =new ArrayList<String>();
        ArrayList<String> defaultclassificationdata = new ArrayList<String>();
        ArrayList<String> classificationArray =new ArrayList<String>();
        ArrayList<String> supplierdata =new ArrayList<String>();
        ArrayList<String> supplierArray =new ArrayList<String>();

        List<Map<String, String>> lotAndExpiration = new ArrayList<Map<String, String>>();

        String attribute= "0";
        String product_code = null, product_name = null;
        String supplierInfo = null;
        boolean supplierpresent = false;

        DirectReturnActivity act = (DirectReturnActivity) activity;

        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            Log.e(TAG, "" + row);

            if (i == 0) {
                product_code = row.getStringOrNull("code");
                product_name = row.getStringOrNull("name");
            }
            if (row.getStringOrNull("rsv_date") != null && row.getStringOrNull("rsv_date").equals("") == false) {

                String rsv_date = (row.getStringOrNull("rsv_date") == "") ? "                " : row.getStringOrNull("rsv_date");
                String orderNo = (row.getStringOrNull("order_no") == "") ? "    " : row.getStringOrNull("order_no");
                String compName = (row.getStringOrNull("comp_name") == "") ? "    " : row.getStringOrNull("comp_name");
                String lot = row.getStringOrNull("lot") ;
                String expiration_date = row.getStringOrNull("expiration_date") ;
                String return_class_name = row.getStringOrNull("return_class_name") ;
                String return_class = row.getStringOrNull("shop_return_class") ;
                   goodclass= row.getStringOrNull("classification_g") ;
                  badclass= row.getStringOrNull("classification_b") ;

/*
                DirectReturnActivity.Good= row.getStringOrNull("classification_g") ;
                DirectReturnActivity.Bad  = row.getStringOrNull("classification_b");*/

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = mdformat.format(calendar.getTime());
                Log.e(TAG, "Current Date  " + strDate);
                if (rsv_date.equals(strDate.trim())) {
                    supplierInfo = rsv_date + "   :   " + compName + "  :  " + U.minusTo(row.getStringOrNull("rsv_cnt"), row.getStringOrNull("act_cnt")) + "   :   " +
                            orderNo;
                } else {
                    supplierInfo = rsv_date + "   :   " +
//                  compName + "  :  " +
                            U.minusTo(row.getStringOrNull("rsv_cnt"), row.getStringOrNull("act_cnt")) + "   :   " +
                            orderNo;
                }
                HashMap<String,String> map = new HashMap();
                map.put("sup_date",rsv_date);
                map.put("comp_name",compName);
                map.put("qty", U.minusTo(row.getStringOrNull("rsv_cnt"), row.getStringOrNull("act_cnt")) );
                map.put("order_no",orderNo);
                map.put("expiration_date",expiration_date);
                map.put("lot",lot);
                map.put("return_class_name",return_class_name);
                map.put("return_class",return_class);
                map.put("goodclass",goodclass);
                map.put("badclass",badclass);
                data.add(map);
                if (row.containsKey("fix_location_flag")) {
                    DirectReturnActivity.fixed_location = row.getStringOrNull("fix_location_flag");
                }
                nyukaIdArray.add(row.getStringOrNull("nyuka_id"));
            }

            if (row.containsKey("attribute_type") && BaseActivity.getLotPress()) {
                attribute = row.getStringOrNull("attribute_type");
                if (!attribute.equals("0")){
                    HashMap<String,String> map = new HashMap<>();
                    map.put("lot", row.getStringOrNull("lot"));
                    map.put("expiration_date", row.getStringOrNull("expiration_date"));
                    map.put("nyuka_id", row.getStringOrNull("nyuka_id"));
                    lotAndExpiration.add(map);
                    Log.e(TAG,"lot 1   " +lotAndExpiration);
                }

            }

            if (row.containsKey("stock_ids")) {
                JsonArray row2 = row.getJsonArrayOrNull("stock_ids");
                Log.e(TAG, "stock_ids Ids List  " + row2 + row2.size());
                classificationdata.add("在庫区分");
                classificationArray.add("");
                for (int j = 0; j < row2.size(); j++) {
                    JsonHash row1 = (JsonHash) row2.get(j);
                    String id = row1.getStringOrNull("id");
                    String name = row1.getStringOrNull("name");
                    String classification = name +   "  :  " + id;
                    classificationArray.add(row1.getStringOrNull("id"));
                    classificationdata.add(classification);
                    defaultclassificationdata.add(classification);
                }
            }

            if (BaseActivity.getSupplierList() == true) {
                if (row.containsKey("suplier_ids")) {
                    JsonArray row2 = row.getJsonArrayOrNull("suplier_ids");
                    Log.e(TAG, "suppliers Ids List  " + row2 + row2.size());
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
                    Log.e(TAG, "customer_ids List  " + row2 + row2.size());
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
        returnlist = data;
        if (data.size() > 0) {

            if(!nyukaIdArray.get(0).equals("999") && BaseActivity.getLotPress()){
                for (Map<String,String> map : lotAndExpiration){

                    String nyukaa = nyukaIdArray.get(0);
                    if(map.get("nyuka_id").equals(nyukaa)){

                        if(attribute.equals("1")){
                            act._sts(R.id.lotno,map.get("lot"));
                        }
                        else  if(attribute.equals("2")){
                            act._sts(R.id.expirationdate,map.get("expiration_date"));
                        }
                        else  if(attribute.equals("3")){
                            act._sts(R.id.expirationdate,map.get("expiration_date"));
                            act._sts(R.id.lotno,map.get("lot"));
                        }
                    }}
            }
        }

        if(BaseActivity.getLotPress()){
            act.setAttributeValue(attribute);
            act.ArrivalLotdata(lotAndExpiration);}

        if (classificationdata.size()> 0)
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                    _singleItemRes, classificationdata);
            adapter.setDropDownViewResource(_dropdownRes);
            spinner = (Spinner) activity.findViewById(R.id.classificationspinner);
            spinner.setAdapter(adapter);
/*
            if (!goodclass.equals("0") && DirectReturnActivity.Classificationtag.equals("0")){
                pos = act.ClasificationPostion(goodclass);
              // spinner.setSelection(pos);
            }else if(!badclass.equals("0") && DirectReturnActivity.Classificationtag.equals("1")){
                pos=  act.ClasificationPostion(badclass);
            }

             spinner.setSelection(pos);*/


        }
        if (supplierdata.size()> 0 )
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                    _singleItemRes, supplierdata);
            adapter.setDropDownViewResource(_dropdownRes);
            Spinner spinner = (Spinner) activity.findViewById(R.id.returnclassificaion);
            LinearLayout layout =(LinearLayout)activity.findViewById(R.id.layout_returnclassificaion);
            layout.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);
            spinner.setAdapter(adapter);
        }

        mTextToSpeak.startSpeaking("scanning");

        act.startTimer();
        act._sts(R.id.quantity, "1");
        act.mTextToSpeak.resetQueue();
        act.mTextToSpeak.startSpeaking("1");
        act._sts(R.id.productCode, product_code);
        act._stxtv(R.id.productName , product_name);
        act.productName.setSelected(true);

        if(supplierpresent && (BaseActivity.getSupplierList()|| BaseActivity.getNoSupplierList())){
            Spinner spinner = (Spinner) activity.findViewById(R.id.returnclassificaion);
            spinner.setVisibility(View.VISIBLE);
            LinearLayout layout =(LinearLayout)activity.findViewById(R.id.layout_returnclassificaion);
            layout.setVisibility(View.VISIBLE);
            act.setsupplierIdArray(supplierArray);}

        act.setNyukaIdArray(nyukaIdArray);
        act.setclassificationIdArray(classificationArray);





        if(attribute.equals("0")){
            act.setProc(DirectReturnActivity.PROC_QTY);}
        else if(attribute.equals("1")){
            if(act._gts(R.id.lotno).equals(""))
                act.setProc(DirectReturnActivity.PROC_LOT_NO);
            else
                act.setProc(DirectReturnActivity.PROC_QTY);
            act.lotexist=true;
        }
        else if(attribute.equals("2") ){
            if(act._gts(R.id.expirationdate).equals("")) {
                act.setProc(DirectReturnActivity.PROC_EXPIRATION);
            }  else{
                act.setProc(DirectReturnActivity.PROC_QTY);
            }
        } else if(attribute.equals("3")){
            if(act._gts(R.id.lotno).equals(""))
                act.setProc(DirectReturnActivity.PROC_LOT_NO);
            else if(act._gts(R.id.expirationdate).equals(""))
                act.setProc(DirectReturnActivity.PROC_EXPIRATION);
            else
                act.setProc(DirectReturnActivity.PROC_QTY);
            act.lotexist=true;
        }

        act.setlocationList();



    }
    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        DirectReturnActivity.returndata = true;
        U.beepError(activity, message);
        ((DirectReturnActivity) activity).setProc(DirectReturnActivity.PROC_BARCODE);

    }
}
