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
import com.itechvision.ecrobo.pickman.Chatman.IrisScreens.Iris_Arrival_Activity;
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

public class GetIrisArrivalLot {

    private final int _singleItemRes = R.layout.arrival_spinner_item;
        private final int _dropdownRes = R.layout.spinner_dropdown_item;
        private String TAG = "GetArrival";
        public static int nyukacount =0;

        TextToSpeak mTextToSpeak;
        Dialog dialog;
        Context cxt ;

        public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

            nyukacount=0;
            mTextToSpeak = new TextToSpeak(activity);
            Iris_Arrival_Activity act = (Iris_Arrival_Activity) activity;
            dialog = new Dialog(activity);
            cxt = activity ;
            List<Map<String, String>> data = new ArrayList<>();

            ArrayList<String> classificationdata =new ArrayList<String>();

            ArrayList<String> nyukaIdArray = new ArrayList<String>();
            ArrayList<String> qntyArray = new ArrayList<String>();
            ArrayList<String> PartnoList =new ArrayList<String>();
            String product_code = "",name ="",comment="",SpecOne="",Spectwo="",Naukaid="";

            Boolean directToStock =false;

            int count =0;
            List<Map<String, String>> lotAndExpiration = new ArrayList<Map<String, String>>();

            //product is using lotno. or expiration date or not
            String attribute= "0";
            directToStock= BaseActivity.getDirectToStock();


            for (int i = 0; i < list.size(); i++) {
                JsonHash row = (JsonHash) list.get(i);
                if (i == 0) {
                    product_code = row.getStringOrNull("code");
                    name = row.getStringOrNull("name");
                    comment = row.getStringOrNull("comment");
                    SpecOne = row.getStringOrNull("spec1");
                    Spectwo = row.getStringOrNull("spec2");
                    Naukaid = row.getStringOrNull("nyuka_id");
                    //  ABC = row.getStringOrNull("abc_flag");

                }

                if (row.getStringOrNull("rsv_date") != null && row.getStringOrNull("rsv_date").equals("") == false) {

                    String rsv_date = (row.getStringOrNull("rsv_date") == "") ? "        " : row.getStringOrNull("rsv_date");
                    String spec1 = (row.getStringOrNull("spec1") == "") ? "        " : row.getStringOrNull("spec1");
                    String spec2 = (row.getStringOrNull("spec2") == "") ? "        " : row.getStringOrNull("spec2");
                    String commentRemark = (row.getStringOrNull("comment") == "") ? "        " : row.getStringOrNull("comment");
                    String compName = (row.getStringOrNull("comp_name") == "") ? "                " : row.getStringOrNull("comp_name");
                    String orderNo = (row.getStringOrNull("order_no") == "") ? "    " : row.getStringOrNull("order_no");
                    String cnt = U.minusTo(row.getStringOrNull("rsv_cnt"), row.getStringOrNull("act_cnt"));
                    //   String abc = (row.getStringOrNull("abc_flag") == "") ? "    " : row.getStringOrNull("abc_flag");


                        if (row.getStringOrNull("lot").equals(act._gts(R.id.lotno)) || row.getStringOrNull("lot").equalsIgnoreCase("")) {

                            HashMap<String, String> map = new HashMap();
                            map.put("sup_date", rsv_date);

                            map.put("comp_name", compName);

                            map.put("qty", U.minusTo(row.getStringOrNull("rsv_cnt"), row.getStringOrNull("act_cnt")));
                            map.put("order_no", orderNo);
                            map.put("code", row.getStringOrNull("code"));
                            map.put("nyuka_id", row.getStringOrNull("nyuka_id"));
                            map.put("spec1", row.getStringOrNull("spec1"));
                            map.put("spec2", row.getStringOrNull("spec2"));
                            map.put("comment", row.getStringOrNull("comment"));
                            map.put("name", row.getStringOrNull("name"));
                            map.put("case_qty", row.getStringOrNull("case_qty"));

                            //map.put("abc",abc);


                            if (row.containsKey("admin_id"))
                                map.put("admin_id", row.getStringOrNull("admin_id"));
                            else
                                map.put("admin_id", "");

                            data.add(map);


                            nyukaIdArray.add(row.getStringOrNull("nyuka_id"));
                            qntyArray.add(cnt);
                            if (!row.getStringOrNull("nyuka_id").equals("999"))
                                ++nyukacount;


                            String lotno = row.getStringOrNull("lot");
                            Log.e("GetArrival", "lot  " + lotno);
                            if (!lotno.equals("")) {
                                Log.e("GetArrivallll", "lott11  " + lotno);
                                count++;
                            }


                            Log.e(TAG, "BaseActivity.getLotPress     " + BaseActivity.getLotPress());


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
                                    Log.e(TAG, "lotAndExpiration     " + map1);
                                }
                            }

                    }
                }
            }

            if(data.size()>0) {
                Log.e(TAG, "lotAndExpirationnnnnnnnnn 1111111111    " + lotAndExpiration);
                if (nyukacount > 1)
                    CommonDialogs.customToast(activity, "複数の入荷予定が登録されています。");

                String Url = act.selectedUrl;


                if (((!Url.equals("https://air-logi-st.air-logi.com/service") || !Url.equals("https://api.air-logi.com/service")) && !BaseActivity.getShopId().equals("1090")) || BaseActivity.getLotPress() == false) {
                    if (nyukaIdArray.size() > 0 && !nyukaIdArray.get(0).equals("999")) {
                        for (Map<String, String> map : lotAndExpiration) {
                            Log.e(TAG, "Nukaaaaaaa  " + nyukaIdArray.get(0));
                            String nyukaa = nyukaIdArray.get(0);
                            if (map.get("nyuka_id").equals(nyukaa)) {

                               if (attribute.equals("3")) {
                                    Log.e(TAG, "333333333332221111   expirationnnnn" + map.get("expiration_date") + "     lottt  " + map.get("lot"));
                                    act._sts(R.id.expirationdate, map.get("expiration_date"));

                                }
                            }
                        }
                    }

                }
                mTextToSpeak.startSpeaking("scanning");

                act.ArrivalLotdata(lotAndExpiration);


                if (attribute.equals("0")) {
                    act.setProc(Iris_Arrival_Activity.PROC_QTY);
                } else if (attribute.equals("3")) {
                    if (act._gtxtv(R.id.expirationdate).equals(""))
                        act.setProc(Iris_Arrival_Activity.PROC_EXPIRATION);
                    else {
                        act.setProc(Iris_Arrival_Activity.PROC_QTY);
                    }
                    act.lotexist = true;
                }
                else act.setProc(Iris_Arrival_Activity.PROC_QTY);

                act._sts(R.id.productCode, product_code);
                act._stxtv(R.id.productName, name);
                act.productName.setSelected(true);

                act._sts(R.id.Remarks, comment);
                act._sts(R.id.Standard1, SpecOne);
                act._sts(R.id.Standard2, Spectwo);


                if ( !nyukaIdArray.get(0).equals("999") && data.get(0).get("qty").equalsIgnoreCase("0")) {
                    act._sts(R.id.quantity, "0");
                    act.quantity = "0";
                } else {
                    if (BaseActivity.get_CaseQtyCheckArrival()) {
                        act.quantity = data.get(0).get("case_qty");
                        act._sts(R.id.quantity, data.get(0).get("case_qty"));
                    } else {
                        act.quantity = "1";
                        act._sts(R.id.quantity, "1");
                    }
                }
                act.setNyukaIdArray(nyukaIdArray);
                act.setQntyArray(qntyArray);
                act.getarrivalList(data);
            }
            else{
                U.beepError(act,"lot number dont match");
               act.setProc(Iris_Arrival_Activity.PROC_LOT_NO);
            }

        }


        public void valid(String code, String message, JsonArray list,
                          HashMap<String, String> params, Activity activity) {
            U.beepError(activity, message);
            ((Iris_Arrival_Activity) activity).setProc(Iris_Arrival_Activity.PROC_LOT_NO);
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
            dialog.setCancelable(false);;
            dialog.setContentView(R.layout.dialogarrival);

            Button dialogButton = (Button) dialog.findViewById(R.id.ok);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
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

