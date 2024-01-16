package com.itechvision.ecrobo.pickman.Chatman.IrisScreens.Api;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.itechvision.ecrobo.pickman.Chatman.Account.SettingActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.IrisScreens.IrisDirectArrivalActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetIrisArrivalDirectLot {
    private String TAG = "GetArrivalNew";
    private final int _singleItemRes = R.layout.arrival_spinner_item;
    private final int _dropdownRes = R.layout.spinner_dropdown_item;
    IrisDirectArrivalActivity act;
    public static int nyukacount = 0;
    public static int arrivalList = 0;
    Dialog dialog;
    Context cxt;

    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {

        TextToSpeak mTextToSpeak = new TextToSpeak(activity);

        act = (IrisDirectArrivalActivity) activity;
        dialog = new Dialog(activity);
        cxt = activity;

        List<Map<String, String>> data = new ArrayList<>();
        ArrayList<String> qntyArray = new ArrayList<String>();
        ArrayList<String> classificationdata = new ArrayList<String>();
        ArrayList<String> nyukaIdArray = new ArrayList<String>();
        ArrayList<String> classificationArray = new ArrayList<String>();
        ArrayList<String> defaultclassificationdata = new ArrayList<String>();
        List<Map<String, String>> lotAndExpiration = new ArrayList<Map<String, String>>();


        nyukacount = 0;


        String product_code = null;
        String product_name = null;
        String comment = null, SpecOne = null, Spectwo = null, Naukaid = "";

        //product is using lotno. or expiration date or not
        String attribute = "0";
        int count = 0;
        String barcode = null;
        Log.e("GetArrivalNew", "Listsize " + list.size());
        arrivalList = list.size();


        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            if (i == 0) {

                product_code = row.getStringOrNull("code");
                product_name = row.getStringOrNull("name");

                comment = row.getStringOrNull("comment");
                SpecOne = row.getStringOrNull("spec1");
                Spectwo = row.getStringOrNull("spec2");
                Naukaid = row.getStringOrNull("nyuka_id");

            }


            if (row.getStringOrNull("rsv_date") != null && row.getStringOrNull("rsv_date").equals("") == false) {
                String rsv_date = (row.getStringOrNull("rsv_date") == "") ? "        " : row.getStringOrNull("rsv_date");
                String compName = (row.getStringOrNull("comp_name") == "") ? "                " : row.getStringOrNull("comp_name");
                String orderNo = (row.getStringOrNull("order_no") == "") ? "    " : row.getStringOrNull("order_no");
                String cnt = U.minusTo(row.getStringOrNull("rsv_cnt"), row.getStringOrNull("act_cnt"));

                String spec1 = (row.getStringOrNull("spec1") == "") ? "        " : row.getStringOrNull("spec1");
                String spec2 = (row.getStringOrNull("spec2") == "") ? "        " : row.getStringOrNull("spec2");
                String commentRemark = (row.getStringOrNull("comment") == "") ? "        " : row.getStringOrNull("comment");


                if (row.getStringOrNull("lot").equals(act._gts(R.id.lotno)) || row.getStringOrNull("lot").equalsIgnoreCase("")) {


                    HashMap<String, String> map = new HashMap();
                    map.put("sup_date", rsv_date);
                    map.put("comp_name", compName);
                    map.put("qty", U.minusTo(row.getStringOrNull("rsv_cnt"), row.getStringOrNull("act_cnt")));
                    map.put("order_no", orderNo);
                    map.put("nyuka_id", row.getStringOrNull("nyuka_id"));
                    map.put("code", row.getStringOrNull("code"));

                    map.put("spec1", row.getStringOrNull("spec1"));
                    map.put("spec2", row.getStringOrNull("spec2"));
                    map.put("comment", row.getStringOrNull("comment"));
                    map.put("name", row.getStringOrNull("name"));
                    map.put("case_qty", row.getStringOrNull("case_qty"));

                    if (row.containsKey("admin_id"))
                        map.put("admin_id", row.getStringOrNull("admin_id"));
                    else
                        map.put("admin_id", "");
                    data.add(map);


                    nyukaIdArray.add(row.getStringOrNull("nyuka_id"));
                    if (!row.getStringOrNull("nyuka_id").equals("999"))
                        ++nyukacount;

                    qntyArray.add(cnt);


                    if (row.containsKey("attribute_type") && BaseActivity.getLotPress()) {
                        attribute = row.getStringOrNull("attribute_type");
                        if (!attribute.equals("0")) {
                            HashMap<String, String> map1 = new HashMap<>();
                            map1.put("lot", row.getStringOrNull("lot"));
                            map1.put("expiration_date", row.getStringOrNull("expiration_date"));
                            map1.put("nyuka_id", row.getStringOrNull("nyuka_id"));

                            map1.put("arrival_exp_days", row.getStringOrNull("arrival_exp_days"));
                            map1.put("arrival_exp_flag", row.getStringOrNull("arrival_exp_flag"));

                            lotAndExpiration.add(map1);
                            Log.e("GetArrival", "lotAndExpiration 111   " + lotAndExpiration);
                        }
                    }
                    if (row.containsKey("fix_location_flag")) {
                        IrisDirectArrivalActivity.fixed_location = row.getStringOrNull("fix_location_flag");
                    }
                    String lotno = row.getStringOrNull("lot");
                    Log.e("GetArrival", "lot  " + lotno);
                    if (!lotno.equals("")) {
                        count++;
                    }



                }
            }
        }

      /*  if (nyukacount > 1)
            CommonDialogs.customToast(activity, "複数の入荷予定が登録されています。");
*/
        if (data.size() > 0) {

        if (!nyukaIdArray.isEmpty() ) {


                if (!nyukaIdArray.get(0).equals("999") && BaseActivity.getLotPress()) {
                    for (Map<String, String> map : lotAndExpiration) {
                        Log.e(TAG, "Nukaa  " + nyukaIdArray.get(0));
                        String nyukaa = nyukaIdArray.get(0);
                        if (map.get("nyuka_id").equals(nyukaa)) {
                            Log.e(TAG, "111222333    " + nyukaIdArray.get(0));
                           if (attribute.equals("2")) {
                                Log.e(TAG, "333   " + map.get("expiration_date"));
                                act._sts(R.id.expirationdate, map.get("expiration_date"));
                            } else if (attribute.equals("3")) {
                                Log.e(TAG, "333222111   expiration" + map.get("expiration_date") + "     lot  " + map.get("lot"));
                                act._sts(R.id.expirationdate, map.get("expiration_date"));

                            }
                        }
                    }
                }
            }


            mTextToSpeak.startSpeaking("scanning");


                act.ArrivalLotdata(lotAndExpiration);
            if (attribute.equals("3")) {

                if (act._gts(R.id.expirationdate).equals("")) {
                    act.setProc(IrisDirectArrivalActivity.PROC_EXPIRATION);
                } else {
//
                    if (BaseActivity.get_CaseQtyCheckArrival())
                        act.setProc(IrisDirectArrivalActivity.PROC_LOCATION);
                    else
                        act.setProc(IrisDirectArrivalActivity.PROC_QTY);
                }
            }
            else {
                if (BaseActivity.get_CaseQtyCheckArrival())
                    act.setProc(IrisDirectArrivalActivity.PROC_LOCATION);
                else
                    act.setProc(IrisDirectArrivalActivity.PROC_QTY);
            }

                act.lotexist = true;



        if (!Naukaid.equalsIgnoreCase("999") && data.get(0).get("qty").equalsIgnoreCase("0")) {
            act._sts(R.id.quantity, "0");
        } else {
            if (BaseActivity.get_CaseQtyCheckArrival()) {
                act._sts(R.id.quantity, data.get(0).get("case_qty"));
            } else
                act._sts(R.id.quantity, "1");
        }
        act._sts(R.id.productCode, product_code);
        act._stxtv(R.id.productName, product_name);
        act.productName.setSelected(true);

        if (Naukaid.equalsIgnoreCase("999")) {
            act._g(R.id.ll_comment).setVisibility(View.GONE);

        } else {
            act._g(R.id.ll_comment).setVisibility(View.VISIBLE);
        }

        act._sts(R.id.Remarks, comment);
        act._sts(R.id.Standard1, SpecOne);
        act._sts(R.id.Standard2, Spectwo);
        //   act._sts(R.id.abc, ABC);
        Log.e(TAG, "nyuka  nyukaIdArray   " + nyukaIdArray);

        act.setNyukaIdArray(nyukaIdArray);
        act.setQntyArray(qntyArray);
        act.LocApi();



        act.getarrivalList(data);

    }
        else{
            U.beepError(act,"lot number dont match");
            act.setProc(IrisDirectArrivalActivity.PROC_LOT_NO);
        }
    }





    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((IrisDirectArrivalActivity) activity).setProc(IrisDirectArrivalActivity.PROC_LOT_NO);
    }

    boolean findRepeat(String part, ArrayList<String> PartList) {
        for (String x : PartList) {
            if (x.equals(part)) {
                return true;
            }
        }
        return false;
    }

    public void dialog() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogarrival);

        Button dialogButton = (Button) dialog.findViewById(R.id.ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                act.setProc(IrisDirectArrivalActivity.PROC_QTY);
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
