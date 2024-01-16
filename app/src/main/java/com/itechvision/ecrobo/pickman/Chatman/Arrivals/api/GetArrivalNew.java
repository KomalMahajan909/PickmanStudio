package com.itechvision.ecrobo.pickman.Chatman.Arrivals.api;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ArrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.DirectarrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetArrivalNew {
    private String TAG = "GetArrivalNew";
    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.spinner_dropdown_item;
    DirectarrivalActivity act ;
    public static int nyukacount =0;
    public static int arrivalList=0;
    Dialog dialog;
    Context cxt ;
    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {

        /*ListViewItems data = new ListViewItems();*/
        TextToSpeak mTextToSpeak=new TextToSpeak(activity);

        act = (DirectarrivalActivity) activity;
        dialog = new Dialog(activity);
        cxt = activity ;

        List<Map<String, String>> data = new ArrayList<>();
        ArrayList<String> qntyArray = new ArrayList<String>();
        ArrayList<String> classificationdata =new ArrayList<String>();
        ArrayList<String> nyukaIdArray = new ArrayList<String>();
        ArrayList<String> classificationArray =new ArrayList<String>();
        ArrayList<String> defaultclassificationdata = new ArrayList<String>();
        List<Map<String, String>> lotAndExpiration = new ArrayList<Map<String, String>>();
        ArrayList<String> PartnoList =new ArrayList<String>();

        nyukacount =0;

        String quant = null;
        String product_code = null ;
        String product_name =  null;
        String supplierInfo = null,comment=null,SpecOne=null,Spectwo=null,Naukaid=""/*,ABC=""*/;

        //product is using lotno. or expiration date or not
        String attribute= "0";
        int count =0;
        String barcode = null;
        Log.e("GetArrivalNew", "Listsize " + list.size());
        arrivalList = list.size();
        boolean multicode = false;

        PartnoList.add("Select Part ");
        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            if (i == 0) {

                product_code = row.getStringOrNull("code");
                product_name = row.getStringOrNull("name");

                comment = row.getStringOrNull("comment");
                SpecOne = row.getStringOrNull("spec1");
                Spectwo = row.getStringOrNull("spec2");
                Naukaid = row.getStringOrNull("nyuka_id");
                //ABC = row.getStringOrNull("abc_flag");
            }

            if(row.containsKey("multi_codes")){
                multicode = true;
            }

            if (row.getStringOrNull("rsv_date") != null && row.getStringOrNull("rsv_date").equals("") == false) {
                String rsv_date = (row.getStringOrNull("rsv_date") == "") ? "        " : row.getStringOrNull("rsv_date");
                String compName = (row.getStringOrNull("comp_name") == "") ? "                " : row.getStringOrNull("comp_name");
                String orderNo = (row.getStringOrNull("order_no") == "") ? "    " : row.getStringOrNull("order_no");
                String cnt = U.minusTo(row.getStringOrNull("rsv_cnt"), row.getStringOrNull("act_cnt"));
                //    String abc = (row.getStringOrNull("abc_flag") == "") ? "    " : row.getStringOrNull("abc_flag");

                String spec1 = (row.getStringOrNull("spec1") == "") ? "        " : row.getStringOrNull("spec1");
                String spec2 = (row.getStringOrNull("spec2") == "") ? "        " : row.getStringOrNull("spec2");
                String commentRemark = (row.getStringOrNull("comment") == "") ? "        " : row.getStringOrNull("comment");

//                if (Integer.parseInt(cnt) > 0 || (row.getStringOrNull("nyuka_id").equals("999"))) {
                HashMap<String,String> map = new HashMap();
                map.put("sup_date",rsv_date);
                map.put("comp_name",compName);
                map.put("qty", U.minusTo(row.getStringOrNull("rsv_cnt"), row.getStringOrNull("act_cnt")) );
                map.put("order_no",orderNo);
                map.put("nyuka_id",row.getStringOrNull("nyuka_id"));
                map.put("stock_type_id",row.getStringOrNull("stock_type_id"));
                map.put("code",row.getStringOrNull("code"));

                map.put("spec1",row.getStringOrNull("spec1"));
                map.put("spec2",row.getStringOrNull("spec2"));
                map.put("comment",row.getStringOrNull("comment"));
                map.put("name",row.getStringOrNull("name"));
                String caseqty = "";
                if(row.containsKey("case_qty"))
                    caseqty = row.getStringOrNull("case_qty");


                map.put("case_qty",caseqty);
                //  map.put("abc",abc);
                if(row.containsKey("admin_id"))
                    map.put("admin_id", row.getStringOrNull("admin_id"));
                else
                    map.put("admin_id", "");
                data.add(map);

                boolean match = findRepeat(row.getStringOrNull("code"), PartnoList);
                if(!match)
                    PartnoList.add(row.getStringOrNull("code"));

                nyukaIdArray.add(row.getStringOrNull("nyuka_id"));
                if(!row.getStringOrNull("nyuka_id").equals("999"))
                    ++nyukacount;

                qntyArray.add(cnt);
//                }

                if (row.containsKey("attribute_type") && BaseActivity.getLotPress()) {
                    attribute = row.getStringOrNull("attribute_type");
                    if (!attribute.equals("0")){
                        HashMap<String,String> map1 = new HashMap<>();
                        map1.put("lot", row.getStringOrNull("lot"));
                        map1.put("expiration_date", row.getStringOrNull("expiration_date"));
                        map1.put("nyuka_id", row.getStringOrNull("nyuka_id"));

                        map1.put("arrival_exp_days", row.getStringOrNull("arrival_exp_days"));
                        map1.put("arrival_exp_flag", row.getStringOrNull("arrival_exp_flag"));

                        lotAndExpiration.add(map1);
                        Log.e("GetArrival","lotAndExpiration 111   " +lotAndExpiration);
                    }
                }
                if (row.containsKey("fix_location_flag"))
                {
                    DirectarrivalActivity.fixed_location_flag = row.getStringOrNull("fix_location_flag");
                }

                String lotno= row.getStringOrNull("lot");
                Log.e("GetArrival","lot  " +lotno);
                if (!lotno.equals(""))
                {
                    count++;
                }
            }

            if(row.containsKey("stock_ids") && i== 0)
            {
                act.defaultclassificationIdArray.clear();
                JsonArray row2= row.getJsonArrayOrNull("stock_ids");
                Log.e("GetArriavlll","Stock Ids List  "+row2  +row2.size());
                classificationdata.add("在庫区分を選択");
                classificationArray.add("");
                for (int j = 0; j < row2.size(); j++) {
                    JsonHash row1 = (JsonHash) row2.get(j);
                    String id = row1.getStringOrNull("id");
                    String name = row1.getStringOrNull("name");
                    boolean match = findRepeat(id, classificationArray);
                    if (!match) {

                        String classification = name + "  :  " + id;
                        classificationdata.add(classification);
                        defaultclassificationdata.add(classification);
                        classificationArray.add(row1.getStringOrNull("id"));
                    }
                }
            }
        }

        if(nyukacount>1)
            CommonDialogs.customToast(activity, "複数の入荷予定が登録されています。");

        String Url=act.selectedUrl;

//        if (!BaseActivity.getLotPress() || (BaseActivity.getLotPress() && count==0)) {

        if( !nyukaIdArray.isEmpty() && !BaseActivity.getShopId().equals("1090")|| BaseActivity.getLotPress() == false){

            if (data.size() > 0) {

                if(!nyukaIdArray.get(0).equals("999") && BaseActivity.getLotPress()){
                    for (Map<String,String> map : lotAndExpiration){
                        Log.e(TAG, "Nukaa  "+nyukaIdArray.get(0));
                        String nyukaa = nyukaIdArray.get(0);
                        if(map.get("nyuka_id").equals(nyukaa)){
                            Log.e(TAG, "111222333    "+nyukaIdArray.get(0));
                            if(attribute.equals("1")){
                                Log.e(TAG, "222    "+map.get("lot"));
                            }
                            else  if(attribute.equals("2")){
                                Log.e(TAG, "333   "+map.get("expiration_date"));
                                act._sts(R.id.expirationdate,map.get("expiration_date"));
                            }
                            else  if(attribute.equals("3")){
                                Log.e(TAG, "333222111   expiration"+map.get("expiration_date")  +"     lot  "+map.get("lot"));
                                act._sts(R.id.expirationdate,map.get("expiration_date"));

                            }
                        }}
                }
            }
            if (classificationdata.size()> 0)
            {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                        _singleItemRes, classificationdata);
                adapter.setDropDownViewResource(_dropdownRes);
                Spinner spinner = (Spinner) activity.findViewById(R.id.classificationspinner);
                spinner.setAdapter(adapter);
            }

            mTextToSpeak.startSpeaking("scanning");

            if(BaseActivity.getLotPress()){
                act.setAttributeValue(attribute);
                act.ArrivalLotdata(lotAndExpiration);
            }


            if(multicode) {
                act.partnoLayout.setVisibility(View.VISIBLE);
                act.getmultiPartarrivalList(data, multicode, PartnoList);

                act.setProc(DirectarrivalActivity.PROC_PARTNO);
                act.setNyukaIdArray(nyukaIdArray);
                act.setQntyArray(qntyArray);

                if (PartnoList.size()> 1)
                {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                            _singleItemRes, PartnoList);
                    adapter.setDropDownViewResource(_dropdownRes);
                    Spinner spinner = (Spinner) activity.findViewById(R.id.partnoSpinner);
                    spinner.setAdapter(adapter);
                    Log.e(TAG,"spinner  "+PartnoList.size());
                    if(PartnoList.size() > 0){
                        spinner.setSelection(1);
                        act._sts(R.id.Remarks, data.get(0).get("comment"));
                        act._sts(R.id.Standard1, data.get(0).get("spec1"));
                        act._sts(R.id.Standard2, data.get(0).get("spec2"));

                        act.productName.setText(data.get(0).get("name"));


                        act.pck0.setText(data.get( 0).get("sup_date"));
                        act.pck1.setText(data.get(0).get("order_no"));
                        act.pck2.setText(data.get(0).get("comp_name"));
                        act.pck3.setText(data.get(0).get("qty"));
                    }
                }


                act._stxtv(R.id.productName, product_name);
                act.productName.setSelected(true);

                act.setclassificationIdArray(classificationArray);

                act.setdefaultclassificationIdArray(classificationArray);
                act.setdefaultclassificationArray(defaultclassificationdata);
                act.setBadge1(classificationArray.size() - 1);
                //  act.setlocationList();
            }
            else {
                if(attribute.equals("0")){
                    Log.e(TAG, "333222111");

                    act.setProc(DirectarrivalActivity.PROC_QTY);}
                else    if (BaseActivity.getLotPress()) {

                    if (attribute.equals("1")) {
                        Log.e(TAG, "333111222   lotno   " + act._gts(R.id.lotno));
                        if (act._gts(R.id.lotno).equals("") )
                            act.setProc(DirectarrivalActivity.PROC_LOT_NO);
                        else{

                            act.setProc(DirectarrivalActivity.PROC_QTY);}
                        act.lotexist = true;
                    } else if (attribute.equals("2")) {

                        if (act._gts(R.id.expirationdate).equals("")) {
                            act.setProc(DirectarrivalActivity.PROC_EXPIRATION);
                        } else {
//                        act.showPopup();

                            act.setProc(DirectarrivalActivity.PROC_QTY);
                        }
                    } else if (attribute.equals("3")) {

                        Log.e(TAG, "333111222111   lotno   " + act._gts(R.id.lotno) + "expiration    " + act._gts(R.id.expirationdate));
                        if (act._gts(R.id.lotno).equals(""))
                            act.setProc(DirectarrivalActivity.PROC_LOT_NO);
                        else if (act._gts(R.id.expirationdate).equals("")) {
                            act.setProc(DirectarrivalActivity.PROC_EXPIRATION);
                        } else {
//

                            act.setProc(DirectarrivalActivity.PROC_QTY);
                        }
                        act.lotexist = true;
                    }


                }
                if(!Naukaid.equalsIgnoreCase("999")&& data.get(0).get("qty").equalsIgnoreCase("0")) {
                    act._sts(R.id.quantity, "0");
                }else {

                    act._sts(R.id.quantity, "1");
                }
                act._sts(R.id.productCode, product_code);
                act._stxtv(R.id.productName, product_name);
                act.productName.setSelected(true);

                if (Naukaid.equalsIgnoreCase("999")){
                    act._g(R.id.ll_comment).setVisibility(View.GONE);

                }else{
                    act._g(R.id.ll_comment).setVisibility(View.VISIBLE);
                }

                act._sts(R.id.Remarks, comment);
                act._sts(R.id.Standard1, SpecOne);
                act._sts(R.id.Standard2, Spectwo);
                //   act._sts(R.id.abc, ABC);
                Log.e(TAG, "nyuka  nyukaIdArray   "+nyukaIdArray);

                act.setNyukaIdArray(nyukaIdArray);
                act.setQntyArray(qntyArray);
                act.LocApi();

                act.setclassificationIdArray(classificationArray);

                act.setdefaultclassificationIdArray(classificationArray);
                act.setdefaultclassificationArray(defaultclassificationdata);
                act.setBadge1(classificationArray.size() - 1);

                act.getarrivalList(data);
            }
            act.setlocationList();
        }
        act.mRequestStatus = act.REQ_BARCODE2;
    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((DirectarrivalActivity) activity).setProc(DirectarrivalActivity.PROC_BARCODE);
    }

    boolean findRepeat(String part,ArrayList<String> PartList){
        for(String x : PartList){
            if(x.equals(part)){
                return true;
            }
        }
        return false;
    }

    public void dialog(){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogarrival);

        Button dialogButton = (Button) dialog.findViewById(R.id.ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                act.setProc(DirectarrivalActivity.PROC_QTY);
            }
        });

        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                cxt.startActivity(new Intent(cxt, SettingActivity.class));

            }
        });

        dialog.show();
    }

}
