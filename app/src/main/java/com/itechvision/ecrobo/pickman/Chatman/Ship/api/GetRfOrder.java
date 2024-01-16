package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.itechvision.ecrobo.pickman.Chatman.Arrivals.RFIDArrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.RFPickingActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.itechvision.ecrobo.pickman.Chatman.Ship.RFPickingActivity.PROC_INCLUDE_BARCODE;

/**
 * Created by lenovo on 7/18/2019.
 */

public class GetRfOrder {
    private String TAG = "GetRfOrder";

    TextToSpeak mTextToSpeak;


    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {
        // seperate lists for both RFIDs and Barcodes
        List<Map<String, String>> rfdata = new ArrayList<Map<String, String>>();
        List<String> rfIDlist =  new ArrayList<String>();
        List<Map<String, String>> barcodedata = new ArrayList<Map<String, String>>();
        List<Map<String, String>> includedata = new ArrayList<Map<String, String>>();

        mTextToSpeak = new TextToSpeak(activity);
        RFPickingActivity act = (RFPickingActivity) activity;

        String includeProd ="";
        String order_id ="";
        String order_no ="";

        JsonHash row = (JsonHash) list.get(0);
        Log.e(TAG,">>>>>>>>>>>"+row);

        if(row.containsKey("products"))
        {
            JsonArray result1 = row.getJsonArrayOrNull("products");
            Log.e(TAG,"rowww   "+result1 );
            int count = 0;
            for(int i =0; i < result1.size(); i++)
            {
                JsonHash row1 = (JsonHash) result1.get(i);
                if(i== 0){
                    order_id =  row1.getStringOrNull("order_id");
                    if(row1.containsKey("order_no"))
                    order_no =  row1.getStringOrNull("order_no");
                }


                Log.e(TAG,"rowww11111  "+row1 );
                if( row1.getStringOrNull("category").equals("0")){
                if(row1.getStringOrNull("rfid_flag").equals("1")){
                    String qty = row1.getStringOrNull("num");


                    Log.e(TAG,"rowww22222  "+qty );
                 for (int j=0;j< Integer.parseInt(qty);j++){

                     HashMap<String,String> map = new HashMap<>();
                            map.put("order_sub_id",row1.getStringOrNull("order_sub_id"));
                            map.put("barcode",row1.getStringOrNull("barcode"));
                            map.put("code",row1.getStringOrNull("code"));
                            map.put("rfid_flag",row1.getStringOrNull("rfid_flag"));
                            map.put("category",row1.getStringOrNull("category"));
                            map.put("order_id",row1.getStringOrNull("order_id"));

                            map.put("num","1");
                            map.put("scanned_rf","");

                            //to stor rfid scanned flag
                            map.put("scan_tag","0");
                            map.put("processedCnt", "0");
                            map.put("id",count+"");
                            rfdata.add(map);
                            rfIDlist.add(count+"");
                            ++count;
                        }
                    Log.e(TAG,"Mappppp   "+rfdata );
                    Log.e(TAG,"Listttt   "+rfIDlist );
                }
               else {
                    HashMap<String,String> map = new HashMap<>();
                    map = (HashMap) row1;
                    map.put("processedCnt", "0");
                    barcodedata.add(map);
                    Log.e(TAG,"Mappppp1111   "+barcodedata );
                }
               }
                else {
                    HashMap<String,String> map = new HashMap<>();
                    map = (HashMap) row1;
                    map.put("processedCnt", "0");
                    includedata.add(map);
                }
            }
        }

        mTextToSpeak.startSpeaking("Scan RFID");
        act.startTimer();
        act._sts(R.id.order_no,order_no);
        act.setincludeList(includedata);
        act.setRFIDs(rfdata,rfIDlist);
        act.setbarcodeList(barcodedata);
        act.setOrderID(order_id);


        int size1= rfIDlist.size()+ barcodedata.size();
        act.updateBadge1(includedata.size()+"");
        act.updateBadge2(size1+"");
        act.updateBadge3("0");

        if(!rfIDlist.isEmpty()){
            act.initRFList(rfdata);
            act.setProc(RFPickingActivity.PROC_RFID);
        }
        else if(!barcodedata.isEmpty()){
                act.initIncludeList(barcodedata);
            act.setProc(RFPickingActivity.PROC_BARCODE);}
        else {
                if(!includedata.isEmpty()) {
                    if (!BaseActivity.getsinclude())
                       act.setIncludePop();
                    else {

                        act.includeLayout.setVisibility(View.VISIBLE);
                        act.setProc(RFPickingActivity.PROC_INCLUDE_BARCODE);
                        act.initIncludeList(includedata);
                    }
                }
                else act.fixedRequest();
            }

    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        RFPickingActivity act = (RFPickingActivity) activity;
        act.setProc(RFPickingActivity.PROC_FRONTAGE);

    }

}
