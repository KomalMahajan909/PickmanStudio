package com.itechvision.ecrobo.pickman.Chatman.WWW6Server;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.ArrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.R.id;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GetArrivalserver6 {
    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.spinner_dropdown_item;
    private String TAG = "GetArrival";
    public static int nyukacount =0;
    //for multiple partno. and single barcode
    public boolean multicode = false;
    TextToSpeak mTextToSpeak;
    Dialog dialog;
    Context cxt ;

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        nyukacount=0;
        mTextToSpeak = new TextToSpeak(activity);
        ArrivalActivity act = (ArrivalActivity) activity;
        dialog = new Dialog(activity);
        cxt = activity ;
        List<Map<String, String>> data = new ArrayList<>();

        ArrayList<String> classificationdata =new ArrayList<String>();

        ArrayList<String> nyukaIdArray = new ArrayList<String>();
        ArrayList<String> qntyArray = new ArrayList<String>();
        ArrayList<String> PartnoList =new ArrayList<String>();
        String product_code = "",name ="",comment="",SpecOne="",Spectwo="",Naukaid="",ABC="";

        Boolean directToStock =false;

        int count =0;
        List<Map<String, String>> lotAndExpiration = new ArrayList<Map<String, String>>();

        //product is using lotno. or expiration date or not
        String attribute= "0";
        directToStock=BaseActivity.getDirectToStock();
       PartnoList.add("Select Part ");

        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            if (i == 0) {
                product_code = row.getStringOrNull("code");
                name = row.getStringOrNull("name");
                comment = row.getStringOrNull("comment");
                SpecOne = row.getStringOrNull("spec1");
                Spectwo = row.getStringOrNull("spec2");
                Naukaid = row.getStringOrNull("nyuka_id");
                ABC = row.getStringOrNull("abc_flag");

            }

            if(row.containsKey("multi_codes")){
                multicode = true;
                Log.e("GetArrivall","multicode  " +multicode);
            }

            if (row.getStringOrNull("rsv_date") != null && row.getStringOrNull("rsv_date").equals("") == false) {

                String rsv_date = (row.getStringOrNull("rsv_date") == "") ? "        " : row.getStringOrNull("rsv_date");
                String spec1 = (row.getStringOrNull("spec1") == "") ? "        " : row.getStringOrNull("spec1");
                String spec2 = (row.getStringOrNull("spec2") == "") ? "        " : row.getStringOrNull("spec2");
                String commentRemark = (row.getStringOrNull("comment") == "") ? "        " : row.getStringOrNull("comment");
                String compName = (row.getStringOrNull("comp_name") == "") ? "                " : row.getStringOrNull("comp_name");
                String orderNo = (row.getStringOrNull("order_no") == "") ? "    " : row.getStringOrNull("order_no");
                String cnt = U.minusTo(row.getStringOrNull("rsv_cnt"), row.getStringOrNull("act_cnt"));
                String abc = (row.getStringOrNull("abc_flag") == "") ? "    " : row.getStringOrNull("abc_flag");

                if (Integer.parseInt(cnt) > 0 || (row.getStringOrNull("nyuka_id").equals("999"))) {

                    HashMap<String,String> map = new HashMap();
                    map.put("sup_date",rsv_date);
                    map.put("comp_name",compName);

                    map.put("qty", U.minusTo(row.getStringOrNull("rsv_cnt"), row.getStringOrNull("act_cnt")) );
                    map.put("order_no",orderNo);
                    map.put("code",row.getStringOrNull("code"));
                    map.put("nyuka_id",row.getStringOrNull("nyuka_id"));
                    map.put("spec1",row.getStringOrNull("spec1"));
                    map.put("spec2",row.getStringOrNull("spec2"));
                    map.put("comment",row.getStringOrNull("comment"));
                    map.put("name",row.getStringOrNull("name"));
                    map.put("abc",abc);



                    if(row.containsKey("admin_id"))
                        map.put("admin_id", row.getStringOrNull("admin_id"));
                    else
                        map.put("admin_id", "");

                    data.add(map);

                    boolean match = findRepeat(row.getStringOrNull("code"), PartnoList);
                    if(!match)
                        PartnoList.add(row.getStringOrNull("code"));

                    nyukaIdArray.add(row.getStringOrNull("nyuka_id"));
                    qntyArray.add(cnt);
                    if(!row.getStringOrNull("nyuka_id").equals("999"))
                        ++nyukacount;
                }

                String lotno= row.getStringOrNull("lot");
                Log.e("GetArrival","lot  " +lotno);
                if (!lotno.equals(""))
                {Log.e("GetArrivallll","lott11  " +lotno);
                    count++;
                }


            }
            Log.e(TAG, "BaseActivity.getLotPress     "+BaseActivity.getLotPress());


            if (row.containsKey("attribute_type") && BaseActivity.getLotPress()) {
                attribute = row.getStringOrNull("attribute_type");
                if (!attribute.equals("0")){
                    HashMap<String,String> map = new HashMap<>();
                    map.put("lot", row.getStringOrNull("lot"));
                    map.put("expiration_date", row.getStringOrNull("expiration_date"));
                    map.put("nyuka_id", row.getStringOrNull("nyuka_id"));

                    map.put("arrival_exp_days", row.getStringOrNull("arrival_exp_days"));
                    map.put("arrival_exp_flag", row.getStringOrNull("arrival_exp_flag"));
                  /*  if (map.containsKey("arrival_exp_days")){
                        map.put("arrival_exp_days", row.getStringOrNull("arrival_exp_days"));
                        map.put("arrival_exp_flag", row.getStringOrNull("arrival_exp_flag"));
                    }
*/


                    lotAndExpiration.add(map);
                    Log.e(TAG, "lotAndExpiration     "+map);
                }
            }
        }
        Log.e(TAG, "lotAndExpirationnnnnnnnnn 1111111111    "+lotAndExpiration);
        if(nyukacount>1 && !multicode)
            CommonDialogs.customToast(activity, "複数の入荷予定が登録されています。");

        String Url=act.selectedUrl;

        if(((!Url.equals("https://air-logi-st.air-logi.com/service") || !Url.equals("https://api.air-logi.com/service"))&& !BaseActivity.getShopId().equals("1090"))|| BaseActivity.getLotPress() == false){

            if(!nyukaIdArray.get(0).equals("999") && BaseActivity.getLotPress()){
                for (Map<String,String> map : lotAndExpiration){
                    Log.e(TAG, "Nukaaaaaaa  "+nyukaIdArray.get(0));
                    String nyukaa = nyukaIdArray.get(0);
                    if(map.get("nyuka_id").equals(nyukaa)){

                        if(attribute.equals("1")){
                            act._sts(id.lotno,map.get("lot"));
                        }
                        else  if(attribute.equals("2")){

                            Log.e(TAG, "33333333333   "+map.get("expiration_date"));
                            act._sts(id.expirationdate,map.get("expiration_date"));
                        }
                        else  if(attribute.equals("3")){
                            Log.e(TAG, "333333333332221111   expirationnnnn"+map.get("expiration_date")  +"     lottt  "+map.get("lot"));
                            act._sts(id.expirationdate,map.get("expiration_date"));
                            act._sts(id.lotno,map.get("lot"));
                        }
                    }}
            }

            mTextToSpeak.startSpeaking("scanning");
            if(BaseActivity.getLotPress()) {
                act.setAttributeValue(attribute);
                act.ArrivalLotdata(lotAndExpiration);
            }else{
                if (Integer.parseInt(attribute)!=0){
                }
            }

            act.startTimer();

           /* if(multicode) {
                act.partnoLayout.setVisibility(View.VISIBLE);
                act.getmultiPartarrivalList(data, multicode, PartnoList);
                act.setNyukaIdArray(nyukaIdArray);
                act.setQntyArray(qntyArray);

                if (PartnoList.size()> 1)
                {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                            _singleItemRes, PartnoList);
                    adapter.setDropDownViewResource(_dropdownRes);
                    Spinner spinner = (Spinner) activity.findViewById(id.partnoSpinner);
                    spinner.setAdapter(adapter);
                    if(PartnoList.size() == 2){
                        Log.e(TAG,"spinnerrrrr  ");
                        spinner.setSelection(1);
                        act._sts(R.id.Remarks, data.get(1).get("comment"));
                        act._sts(R.id.Standard1, data.get(1).get("spec1"));
                        act._sts(R.id.Standard2, data.get(1).get("spec2"));
                        act.productName.setText(data.get(1).get("name"));
                    }
                }
                act.setProc(ArrivalActivity.PROC_PARTNO);
            }*/

            if(multicode) {
                act.partnoLayout.setVisibility(View.VISIBLE);
                act.getmultiPartarrivalList(data, multicode, PartnoList);
                act.setNyukaIdArray(nyukaIdArray);
                act.setQntyArray(qntyArray);

                if (PartnoList.size()> 1)
                {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(),
                            _singleItemRes, PartnoList);
                    adapter.setDropDownViewResource(_dropdownRes);
                    Spinner spinner = (Spinner) activity.findViewById(id.partnoSpinner);
                    spinner.setAdapter(adapter);
                    if(PartnoList.size()>0){
                        Log.e(TAG,"spinnerrrrr  ");
                        spinner.setSelection(1);

                        act._sts(id.Remarks, data.get(1).get("comment"));
                        act._sts(id.Standard1, data.get(1).get("spec1"));
                        act._sts(id.Standard2, data.get(1).get("spec2"));
                        act.productName.setText(data.get(1).get("name"));



                        act.pck0.setText(data.get(1).get("sup_date"));
                        act.pck1.setText(data.get(1).get("order_no"));
                        act.pck2.setText(data.get(1).get("comp_name"));
                        act.pck3.setText(data.get(1).get("qty"));
                        act.pck2.setSelected(true);
                    }
                }
                act.setProc(ArrivalActivity.PROC_PARTNO);
            }  else {

                if (attribute.equals("0")) {
                    act.setProc(ArrivalActivity.PROC_QTY);
                }else if (attribute.equals("1")) {

                            Log.e(TAG, "3333333311122211111111   lotnooo   " + act._gts(id.lotno));
                            if (act._gts(id.lotno).equals(""))
                                act.setProc(ArrivalActivity.PROC_LOT_NO);
                            else
                                act.setProc(ArrivalActivity.PROC_QTY);
                            act.lotexist = true;
                        } else if (attribute.equals("2")) {

                            if (act._gts(id.expirationdate).equals("")) {
                                act.setProc(ArrivalActivity.PROC_EXPIRATION);
                            } else {
//                        act.showPopup("入荷指示に消費期限が含まれています。");
                                act.setProc(ArrivalActivity.PROC_QTY);
                            }
                        } else if (attribute.equals("3")) {

                            if (act._gts(id.lotno).equals(""))
                                act.setProc(ArrivalActivity.PROC_LOT_NO);
                            else if (act._gts(id.expirationdate).equals(""))
                                act.setProc(ArrivalActivity.PROC_EXPIRATION);
                            else{
//                        act.showPopup("入荷指示に消費期限が含まれています。");
                                act.setProc(ArrivalActivity.PROC_QTY);}
                            act.lotexist = true;
                        }
                    }
/*

                        new android.app.AlertDialog.Builder(activity, R.style.DialogThemee)
                                // .setTitle("Error!")
                                .setMessage("MENUにて「Lot No./Exp.」が\n" +
                                        "選択されていません。\n" +
                                        "作業を進めますか？")
                                .setCancelable(false)
                                .setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        act.nextProcess();

                                    }
                                })
                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        act.setProc(ArrivalActivity.PROC_QTY);

                                    }
                                })

                                .show();
*/








                act._sts(id.quantity, "1");
//            mTextToSpeak.startSpeaking("1");
                if (Naukaid.equalsIgnoreCase("999")){
                    act._g(id.ll_comment).setVisibility(View.GONE);

                }else{
                    act._g(id.ll_comment).setVisibility(View.VISIBLE);
                }
                act._sts(id.productCode, product_code);
                act._stxtv(id.productName, name);
                act.productName.setSelected(true);

                act._sts(id.Remarks, comment);
                act._sts(id.Standard1, SpecOne);
                act._sts(id.Standard2, Spectwo);
                act._sts(id.abc, ABC);

                act.quantity = "1";

                act.setNyukaIdArray(nyukaIdArray);
                act.setQntyArray(qntyArray);
                act.getarrivalList(data);

                act.LocApi();


            }
        }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((ArrivalActivity) activity).setProc(ArrivalActivity.PROC_BARCODE);
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

        Button dialogButton = (Button) dialog.findViewById(id.ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button cancel = (Button) dialog.findViewById(id.cancel);
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