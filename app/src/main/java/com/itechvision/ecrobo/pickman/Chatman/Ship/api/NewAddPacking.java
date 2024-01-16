package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.ShippingActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;

import java.util.HashMap;

/**
 * Created by lenovo on 2/17/2019.
 */

public class NewAddPacking {
    String TAG = "NewAddPacking";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        final ShippingActivity act = (ShippingActivity) activity;


        Log.e(TAG,"adddpackingggggggg  " + BaseActivity.getPackingList());

        Log.e(TAG, "getBoxSelected444444    "+BaseActivity.getBoxSelected() + "     "+"getShippingflag"+BaseActivity.getShippingflag());

        if(BaseActivity.getPackingList()==true && !act.clear){
            Log.e(TAG,"adddpackingggggggg111111111111111111  " + BaseActivity.getPackingList());
            if(act.nextbox==true && !act.complete){
                Log.e(TAG,"adddpackingggggggg111111111111111111  ");
                act.resetPackData();
                act.setBadge4(0);
                act.nextbox=false;

                setDialog(activity);

            }
            else
            {

                Log.e("addPackingggg","elseeee conditionnn ");
                if(ShippingActivity.mBoxNo > 0) {
                    Log.e("addPackingggg","elseeee conditionnn box>0");
                    act.showDialog1("作成されたボックス番号は" + ShippingActivity.mBoxNo + "です");
                    Log.e(TAG, "getBoxSelected333333333     "+BaseActivity.getBoxSelected() + "     "+"getShippingflag"+BaseActivity.getShippingflag());

                    if (BaseActivity.getBoxSelected() == true && !BaseActivity.getShippingflag()){
                        Log.e(TAG,"adddpackingggggggg111111111111111111  "+BaseActivity.getBoxSelected());
                        act.ToBoxsize();
                    }
                    else {
                    act.resetPackData();
                    act.updatePackBadge();
                    act.nextbox=false;}
//                    U.beepFinish(activity, "終了");
                }
            }
        } else if(act.clear==true){
            setDialog(activity);
            act.clearcall();
        }
        else if (BaseActivity.getBoxSelected() == true && !BaseActivity.getShippingflag()) {
            act.ToBoxsize();
        }
        else {
            Log.e("addPackingggg","No packing Listttttt ");
//            U.beepFinish(activity, "終了");
            act.nextProcess();
        }}


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
    }

    public void setDialog(Activity activity)
    {
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
        TextView txt=(TextView)dialog.findViewById(R.id.txt) ;
        txt.setText("作成されたボックス番号は"+ShippingActivity.mBoxNo+"です");
        ImageView close=(ImageView)dialog.findViewById(R.id.icon_close);

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
