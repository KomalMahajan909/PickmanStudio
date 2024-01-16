package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;

import com.itechvision.ecrobo.pickman.Chatman.Ship.ShippingSpecificationActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;

/**
 * Created by lenovo on 12/27/2018.
 */

public class GetSpecification {
    String TAG = "GetSpecification";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {

        ShippingSpecificationActivity act = ((ShippingSpecificationActivity) activity);
        String count="";

        for (int i = 0; i < list.size(); i++) {
            JsonHash row = (JsonHash) list.get(i);
            count= row.getStringOrNull("all_order_count");

        }
        if(act.trackreq==true){
            U.beepKakutei(activity, "設定を登録しました");
            act.trackreq=false;
            act.btnpress=false;
            act.size="";
            act.btnpress=false;
            act.boxlist.clear();
            act.setback =false;
            act.boxsize.setText(act.getBoxSize());
            act._sts(R.id.trackingNumber,"");
            act._sts(R.id.quantity,"");
            if(!act.scanedtracklist.isEmpty())
                act.scanedtracklist.clear();
            if(!act.boxlist.isEmpty())
                act.boxlist.clear();
            if(act._gts(R.id.edtboxsize).equals(""))
                act.setProc(ShippingSpecificationActivity.PROC_BOXNO);
            else
                act.setProc(ShippingSpecificationActivity.PROC_TRACKID);

        }
        act.updateBadge1(count);

    }


    public void valid(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((ShippingSpecificationActivity) activity).setProc(ShippingSpecificationActivity.PROC_TRACKID);
    }
}
