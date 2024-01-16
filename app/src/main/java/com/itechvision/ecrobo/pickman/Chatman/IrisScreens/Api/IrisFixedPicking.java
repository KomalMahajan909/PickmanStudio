package com.itechvision.ecrobo.pickman.Chatman.IrisScreens.Api;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.IrisScreens.IrisPickingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.NewPickingActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.GetPiking;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.R.id;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class IrisFixedPicking {
    String TAG = "IrisFixedPicking";

    public static String fixbarcode=null;
    public static int empty=0;
    public static String fixlot=null;
    boolean permission = true;
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();


    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        //List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        GetPiking.getbarcode ="0";
        GetPiking.getlot ="0";
        Map<String, String> map = new HashMap<String, String>();

        final IrisPickingActivity act = (IrisPickingActivity) activity;
        String all_order_count = "0";
        String all_row_count = "0";
        String order_picking = "0";
        String tracking = null;
        String order_id = null;

        Log.e("TAG", ">>>>>>>>>>>>>>1111111  "+list);

        if (list.size() > 0){
            // collect all data from response
            JsonHash row = (JsonHash) list.get(0);
            all_order_count = row.getStringOrNull("all_order_count");

            String not_inspected = row.getStringOrNull("not_inspection_row_count");
            String short_inspected = row.getStringOrNull("shortage_row_count");

            Log.e(TAG, ">>>>>>>>>>2222222  "+list);
            String user ="";
            if(row.containsKey("user"))
                user = row.getStringOrNull("user");

            all_row_count = U.plusTo(not_inspected,short_inspected);
            if(row.containsKey("order_picking"))
                order_picking = row.getStringOrNull("order_picking");


            if(IrisPickingActivity.skipvalue == false &&  Integer.parseInt(all_row_count)==0){

                U.beepKakutei(act, null);

                if(BaseActivity.getRemarkPress()==true && !GetPiking.remark.equals("")){
                    act.showRemarkDialog(GetPiking.remark);
                }
            }


            int shotage= Integer.parseInt(short_inspected);
            Log.e("shortage", " "+shotage);
            if (shotage > 0 && !row.containsKey("data")) {

                new SweetAlertDialog(activity)
                        .setTitleText("欠品処理をした商品があります。")
                        .show();
            }


            act._sts(id.orderId, "");


            act.updateBadge1(all_order_count);
            act.updateBadge3(not_inspected);
            act.updateBadge2(short_inspected);

            act.clear=false;

            if( Integer.parseInt(all_row_count)==0)
                empty =1;


            if(row.containsKey("data") && !all_row_count.equals("0")) {
                Log.e("fixedpicking  "," data presentttt   ");
                JsonArray list2 = row.getJsonArrayOrNull("data");
                JsonHash row2 = (JsonHash) list2.get(0);
                map = (HashMap) row2;
                map.put("processedCnt", "0");
                map.put("case_count","0");

                // insert data into desire variables and fields for further processing.
                tracking = map.get("mediacode");
                order_id = map.get("order_id");
                String name = map.get("name");
                String barcode = map.get("barcode");
                fixbarcode= barcode.trim();

                String lot="";
                if(BaseActivity.getLotPress()==true) {
                    lot=map.get("lot");
                    fixlot= lot;
                }
                String location = map.get("location");
                String code0 = map.get("code");
                String quantity = map.get("quantity");
                String stndrd_one = map.get("spec1");
                String stndrd_two = map.get("spec2");
                String prod_name = map.get("product_name");
                String caseqnt = map.get ("case_qty");

                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("BoxID", IrisPickingActivity.mBoxNo+"");
                map1.put("location", row2.getStringOrNull("location"));
                map1.put("mediacode",row2.getStringOrNull("mediacode"));
                map1.put("order_id",row2.getStringOrNull("order_id"));
                map1.put("quantity", row2.getStringOrNull("quantity"));
                map1.put("barcode", row2.getStringOrNull("barcode"));
                map1.put("code", row2.getStringOrNull("code"));
                map1.put("orderSubId", row2.getStringOrNull("order_sub_id"));
                map1.put("multi_barcodes",row2.getStringOrNull("multi_barcodes"));
                map1.put("case_qty",row2.getStringOrNull("case_qty"));
                map1.put("product_stock_history_id",row2.getStringOrNull("product_stock_history_id"));
                map1.put("processedCnt", "0");
                map.put("case_count","0");
                data.add(map1);

                if(row2.containsKey("zone_alert")) {

                    String zone_alert = row2.getStringOrNull("zone_alert");
                    if(zone_alert.equals("1")){
                        // Create Object of Dialog class
                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                        // Set GUI of login screen
                        dialog.setContentView(R.layout.new_picking_dialog);
                        dialog.getWindow().setBackgroundDrawable(
                                new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        Window window = dialog.getWindow();
                        lp.copyFrom(window.getAttributes());
                        dialog.setCanceledOnTouchOutside(false);

                        // Init button of login GUI
                        TextView txt=(TextView)dialog.findViewById(id.txt) ;
                        txt.setText("出荷単位に注意！");
                        ImageView close=(ImageView)dialog.findViewById(id.icon_close);


                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        // Make dialog box visible.
                        dialog.show();
                    }
                }

                if (row2.containsKey("serial_no_flg")) {
                    String serialpresent = map.get("serial_no_flg");
                    IrisPickingActivity.serialPresent = serialpresent;
                    if(serialpresent.equals("1"))
                        act.setLayout();
                }
                String is_scanned = map.get("is_scanned");
                if (Integer.parseInt(is_scanned) == 1 && !user.equalsIgnoreCase("")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                    builder1.setMessage("ユーザー " +user +
                            " が作業中です。");
                    builder1.setCancelable(false);
                    builder1.setPositiveButton("この行をやる",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    permission = true;
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton("別を選択する",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    permission = false;
                                    dialog.cancel();
                                    act.setProc(IrisPickingActivity.PROC_LINE_NO);
//									act.dialog_location();

                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                if (barcode.length() >= 4)
                    act._sts(id.shortBarcode, barcode.substring(barcode.length() - 4));
                else

                    act._sts(id.shortBarcode, barcode);
                act.startTimer();
                act._sts(id.orderId,order_id);
                act._sts(id.barcode, "");
                act._sts(id.serialno, "");
                act._sts(id.quantity, "");
                act._sts(id.plateno,"");
                act._sts(id.location, location);
                act._sts(id.productCode, code0);
                act._sts(id.productQuantity, quantity);
                act._sts(id.standard1, stndrd_one);
                act._sts(id.standard2 , stndrd_two);
                act._stxtv(id.productname,prod_name);
                act.productName.setSelected(true);
                act._sts(id.numberOfLines,map.get("no"));
                act.serialList.clear();
                act._sts(id.caseqnt, caseqnt);
                if(caseqnt.equalsIgnoreCase("0")){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                    builder1.setMessage("製品のケース数量が 0 であるため、この製品を送信できません");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    permission = false;

                                    act.setProc(IrisPickingActivity.PROC_BARCODE);
                                    dialog.cancel();
                                }
                            });
                }

                act.repeatLocation(2000);
                act.setProc(IrisPickingActivity.PROC_BARCODE);
                act.currLineData(map);
                act.setProductList(data);

            } else {

                    Log.e("FixedPicking ","Running next process");
                    if(IrisPickingActivity.skipvalue == false &&  Integer.parseInt(all_row_count)==0) {
                        U.beepFinish(act, null);

                    act.nextProcess();
                    Log.e("FixedPicking ","Running next process");}
            }
        } else {
            String msg  = "No record found!";
            valid(code, msg, list, params, activity);
        }
    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        IrisPickingActivity act = (IrisPickingActivity) activity;
        act.removePackItem();
        U.beepError(activity, message);
    }

}
