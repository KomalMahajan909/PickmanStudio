package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;

import com.itechvision.ecrobo.pickman.Chatman.Ship.KoguchiActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;

/**
 * Created by lenovo on 1/3/2019.
 */

public class GetKoguchi {
    String TAG = "GetKoguchi";

    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {

		/*ListViewItems data = new ListViewItems();*/

        KoguchiActivity act = (KoguchiActivity) activity;
        String orderID="";
        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);

            orderID= row.getStringOrNull("order_id");
        }
        act.startTimer();
        act.setProc(KoguchiActivity.PROC_QTY);
        act._sts(R.id.quantity, "1");
        act.getOrderId(orderID);
    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((KoguchiActivity) activity).setProc(KoguchiActivity.PROC_TRACK_ID);
    }
}
