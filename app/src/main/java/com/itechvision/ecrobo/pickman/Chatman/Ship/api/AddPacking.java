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
import com.itechvision.ecrobo.pickman.Chatman.Ship.NewPickingActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;

import java.util.HashMap;

/**
 * Created by lenovo on 2/7/2019.
 */

public class AddPacking {
    String TAG = "AddPacking";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        final NewPickingActivity act = (NewPickingActivity) activity;


        Log.e(TAG,"adddpackingggggggg  " + BaseActivity.getPackingList());
        if(BaseActivity.getPackingList()==true && !act.clear){
            Log.e(TAG,"adddpackingggggggg111111111111111111  " + BaseActivity.getPackingList());
            if(act.nextbox==true && !act.complete){
                Log.e(TAG,"adddpackingggggggg111111111111111111  ");
                act.resetPackData();
                act.updateBadge2("0");
                act.nextbox=false;

                setDialog(activity);

            }
            else
            {

                Log.e("addPackingggg","elseeee conditionnn ");
                if(NewPickingActivity.mBoxNo > 0) {
                    Log.e("addPackingggg","elseeee conditionnn box>0");

                    setDialog(activity);
                    act.nextProcess();
                    act.resetPackData();
                    act.updateBadge2("0");
                    act.nextbox=false;
                }
            }
        } else if(act.clear==true){
            setDialog(activity);
            act.clearcall();
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
    public void setDialog(Activity activity){
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
        txt.setText("作成されたボックス番号は"+NewPickingActivity.mBoxNo+"です");
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
