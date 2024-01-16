package com.itechvision.ecrobo.pickman.Models.DaimaruIncludeShipping;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.WWW6Server.DimaruShipping.Daimaru_IncludeShipping;
import com.itechvision.ecrobo.pickman.Chatman.WWW6Server.DimaruShipping.Daimaru_Shipping;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;

/**
 * Created by lenovo on 10/21/2018.
 */

public class DimaruFixedPickCategory2
{
    String TAG = "FixedPickCategory2";
    //	protected String mNextBarcode = null;


    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        Log.e(TAG,"allrowConttttt111111111111111111  ");
        Daimaru_IncludeShipping act = (Daimaru_IncludeShipping) activity;
        String all_order_count = null;
        String all_row_count = null;
        if (list.size() > 0) {
            // collect all data from response
            Log.e(TAG,"allrowConttttt222222222222222222 ");
            JsonHash row = (JsonHash) list.get(0);
            Log.e(TAG,"allrowConttttt11111111111112222222222222 ");
            all_order_count = row.getStringOrNull("all_order_count");

            String not_inspected = row.getStringOrNull("not_inspection_row_count");
            String short_inspected = row.getStringOrNull("shortage_row_count");
            Log.e(TAG,"allrowConttttt654321123456 ");
            if(not_inspected.equals(""))
                not_inspected="0";
            if(short_inspected.equals(""))
                short_inspected="0";
            Log.e(TAG,"allrowConttttt1212121221313 ");
            all_row_count = U.plusTo(not_inspected, short_inspected);
            Log.e(TAG,"allrowConttttt  "+all_row_count);
            Daimaru_Shipping.includenotinspected = all_row_count;
            Log.e(TAG,">>>>>>>>>>>>>>>>>"+act.mNextBarcode);
            if (act.mNextBarcode) {
                act._sts(R.id.barcode, "");

                act._sts(R.id.barcode, act.isNextBarcode);
                act.setProc(act.PROC_BARCODE);
                act.inputedEvent(act.isNextBarcode, true);
                if(Daimaru_IncludeShipping.inspectionNo == 1)
                    act.call();
                else
                    act.removeData(Daimaru_IncludeShipping.inspectionNo);

            }

            else if (!row.containsKey("data")){
                Daimaru_Shipping.includenotinspected = all_row_count;
                Log.e(TAG,"111111111111     "+Daimaru_Shipping.includenotinspected);

                if(Integer.parseInt(Daimaru_Shipping.includenotinspected)==0){
                    if(BaseActivity.getBoxNo()){
                        act.boxSize = "";
                        act._sts(R.id.barcode, "");
                        act._sts(R.id.quantity, "");

                        act.mTarget.put("Koguchi_processed_count","0");
                        act.setProc(Daimaru_IncludeShipping.PROC_BOX);
                        Log.e("FixedPickingNS","11111111111133333333");
                    }
                    else {
                    if(BaseActivity.printerPress){
                        act.sendSelectedPrinter();
                    }
                    else {
                    if(BaseActivity.getPackingList()== true){
                        if( (Integer.parseInt(not_inspected)==0 && Integer.parseInt(short_inspected)!=0) || Integer.parseInt(all_row_count)==0)
                            act.complete = true;
                        if(!act.packing)
                           act.printRequest();

//                        act.nextProcess1();

                        act.packing = false;}
                    else if (BaseActivity.getBoxSelected() == true && !BaseActivity.getShippingflag())
                        act.ToBoxsize();
                    else

                    act.nextProcess();}}
                }else {
                    act.goback();

                    if (Daimaru_IncludeShipping.inspectionNo == 1)
                        act.call();
                    else
                        act.removeData(Daimaru_IncludeShipping.inspectionNo);
                }

            } else {
                Log.e(TAG,"111111112222222");
                act._sts(R.id.barcode, "");
                act._sts(R.id.quantity, "");
                act.setProc(act.PROC_BARCODE);
                if(Daimaru_IncludeShipping.inspectionNo == 1)
                    act.call();
                else
                act.removeData(Daimaru_IncludeShipping.inspectionNo);
                Daimaru_Shipping.includenotinspected = all_row_count;
                act.mNextBarcode=false;
                act.isNextBarcode = "";

            }
        }
    }



    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        Daimaru_IncludeShipping act = (Daimaru_IncludeShipping) activity;
//        act.removePackItem();
        act.setProc(act.PROC_BARCODE);

    }


//	public ECZaikoClientListner setNext(String nextBarcode) {
//		mNextBarcode = nextBarcode;
//		return this;
//	}
}
