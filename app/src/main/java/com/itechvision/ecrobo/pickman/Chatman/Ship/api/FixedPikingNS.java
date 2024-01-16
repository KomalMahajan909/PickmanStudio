package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.ShippingActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;

/**
 * Created by lenovo on 10/21/2018.
 */

public class FixedPikingNS  {
    String TAG = "FixedPikingNS";
    //	protected String mNextBarcode = null;
    public static int empty=0;

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        ShippingActivity act = (ShippingActivity) activity;

        String all_order_count = null;
        String all_row_count = null;
        if (list.size() > 0) {
            // collect all data from response
            JsonHash row = (JsonHash) list.get(0);
            all_order_count = row.getStringOrNull("all_order_count");

            String not_inspected = row.getStringOrNull("not_inspection_row_count");
            String short_inspected = row.getStringOrNull("shortage_row_count");
            Log.e("FixedPikinggggg","allrowConttttt654321123456 ");
            if(not_inspected.equals(""))
                not_inspected="0";
            if(short_inspected.equals(""))
                short_inspected="0";
            Log.e("FixedPikinggggg","allrowConttttt1212121221313 ");
            all_row_count = U.plusTo(not_inspected, short_inspected);
            Log.e("FixedPikinggggg","allrowConttttt  "+all_row_count);

            act.updateBadge1(all_row_count);
            act.updateBadge3( all_order_count );


            Log.e("FixedPickingNS",">>>>>>>>>>>>>>>>>"+act.mNextBarcode);
            Log.e("FixedPickingNS",">>>>>>>>>>>>>>>>>nextscreennnnn "+BaseActivity.getsinclude());


            if (act.mNextBarcode) {
                act._sts(R.id.barcode, "");
                act._sts(R.id.barcode, act.isNextBarcode);
                act.setProc(act.PROC_BARCODE);
                act.inputedEvent(act.isNextBarcode, true);
                act.removeData(act.inspectionNo);

                if(ShippingActivity.inspectionNo== 1)
                    act.call();
                else
                    act.removeData(ShippingActivity.inspectionNo);
                Log.e("FixedPickingNS","222222222235555555555555");

            }

           else if (all_row_count.equals("0") ){
                Log.e("FixedPickingNS","111111111111");
//

                //if Box selected on setting screen
                if(BaseActivity.getBoxNo()){
                    act.boxSize = "";
                    act._sts(R.id.trackingNumber, "");
                    act._sts(R.id.barcode, "");
                    act._sts(R.id.quantity, "");
                    act._sts(R.id.totalQuantity, "");
                    act._sts(R.id.lotno, "");
                    act._sts(R.id.orderId, "");
                    act._sts(R.id.productCode, "");
                    act._sts(R.id.orderName,"");
                    act._sts(R.id.productName, "");

                    act.cProductList.put("Koguchi_processed_count","0");
                    act.setProc(ShippingActivity.PROC_BOX);
                    Log.e("FixedPickingNS","11111111111133333333");

                }
                else {
                    U.beepFinish(activity,null);
                    Log.e(TAG, "getBoxSelected11111     "+BaseActivity.getBoxSelected() + "     "+"getShippingflag"+BaseActivity.getShippingflag());
                    if(BaseActivity.getPrinterSelected()) {
                        act.nextProcess1();
                        act.sendSelectedPrinter();
                    }
                    else if (BaseActivity.getPackingList() == true) {

                        Log.e("FixedPickingNS ", "Running next process111111111122222222");
                        if ((Integer.parseInt(not_inspected) == 0 && Integer.parseInt(short_inspected) != 0) || Integer.parseInt(all_row_count) == 0)
                            act.complete = true;
                        if (!act.packing)
                            act.printRequest();

                        act.nextProcess1();

                        act.packing = false;
                    }
                    else if (BaseActivity.getBoxSelected() == true && !BaseActivity.getShippingflag()) {
                       act.ToBoxsize();
                    }
                    else
                        act.nextProcess();
                }
            } else if (!all_row_count.equals("0") && !row.containsKey("data") && BaseActivity.getsinclude()){
//                act.removeData(act.inspectionNo);
                Log.e("FixedPickingNS","1111111111116666666666");
                act.gotoIncludeScreen();
            }
            else if (!all_row_count.equals("0") && !row.containsKey("data") && !BaseActivity.getsinclude()){
                Log.e("FixedPickingNS","111111111111");

                 //if Box selected on setting screen
                if(BaseActivity.getBoxNo()){
                    act.boxSize = "";
                    act._sts(R.id.trackingNumber, "");
                    act._sts(R.id.barcode, "");
                    act._sts(R.id.quantity, "");
                    act._sts(R.id.totalQuantity, "");
                    act._sts(R.id.lotno, "");
                    act._sts(R.id.orderId, "");
                    act._sts(R.id.productCode, "");
                    act._sts(R.id.orderName,"");
                    act._sts(R.id.productName, "");

                    act.cProductList.put("Koguchi_processed_count","0");
                    act.setProc(ShippingActivity.PROC_BOX);
                    Log.e("FixedPickingNS","11111111111133333333");
                }
                else {//if printer selected
                    U.beepFinish(activity,null);
                    Log.e(TAG, "getBoxSelected2222222     "+BaseActivity.getBoxSelected() + "     "+"getShippingflag"+BaseActivity.getShippingflag());

                    if(BaseActivity.getPrinterSelected()) {
                    act.nextProcess1();
                    act.sendSelectedPrinter();
                    }

                   else if (BaseActivity.getPackingList() == true) {

                    Log.e("FixedPickingNS ", "Running next process111111111122222222");
                    if ((Integer.parseInt(not_inspected) == 0 && Integer.parseInt(short_inspected) != 0) || Integer.parseInt(all_row_count) == 0)
                        act.complete = true;

                    if (!act.packing)
                        act.printRequest();

                    act.nextProcess1();

                    act.packing = false;
                }
                else if (BaseActivity.getBoxSelected() == true && !BaseActivity.getShippingflag())
                    act.ToBoxsize();
                else act.nextProcess();
            }
            }
            else {
                Log.e("FixedPickingNS","222222222222222    "+ShippingActivity.inspectionNo );
                act._sts(R.id.barcode, "");
                act._sts(R.id.quantity, "");
                act._sts(R.id.productName, "");
                act._sts(R.id.productCode, "");
                act._sts(R.id.totalQuantity, "");
                act.setProc(act.PROC_BARCODE);
                if(ShippingActivity.inspectionNo== 1)
                    act.call();
                else
                act.removeData(ShippingActivity.inspectionNo);
                Log.e("FixedPickingNS","2222222222333333333333333");


            }

            if(act.getOrderDetail)
                act.getDetail();

        }
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        ShippingActivity act = (ShippingActivity) activity;
        if (code.equals("10209")) {
            act.setProc(act.PROC_BARCODE);
        }
        else {
        U.beepError(activity, message);


        act.setProc(act.PROC_BARCODE);

    }}


//	public ECZaikoClientListner setNext(String nextBarcode) {
//		mNextBarcode = nextBarcode;
//		return this;
//	}

}

