package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;
import android.util.Log;

import com.itechvision.ecrobo.pickman.Chatman.Ship.BoxSizeActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;

/**
 * Created by lenovo on 3/26/2019.
 */

public class SubmitBoxSize {
    String TAG = "SubmitBoxSize";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        BoxSizeActivity act = ((BoxSizeActivity) activity);
        String count="";

        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            count= row.getStringOrNull("all_order_count");
            Log.e("SubmitBoxSize",">>>>>>>>>>>>>  trackreq  "+BoxSizeActivity.trackreq);

        }
        if(BoxSizeActivity.trackreq == true){
            U.beepKakutei(activity, "検品データを登録しました。");
//            act.nextProcess();
            act.trackreq=false;
            act.btnpress=false;
            act.size="";
            act.btnpress=false;
            act.boxsize.setText(act.getBoxSize());
            act._sts(R.id.trackingNumber,"");
            act._sts(R.id.quantity,"");

            act.setProc(BoxSizeActivity.PROC_TRACKID);

        }

        act.updateBadge1(count);

    }


    public void valid(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((BoxSizeActivity) activity).setProc(BoxSizeActivity.PROC_TRACKID);
    }
}
