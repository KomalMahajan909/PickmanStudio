package com.itechvision.ecrobo.pickman.Chatman.Stock.api;

import android.app.Activity;

import com.itechvision.ecrobo.pickman.Chatman.Stock.InventoryActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.HashMap;

/**
 * Created by lenovo on 2/26/2019.
 */

public class ValidateLotno {
    String TAG = "ValidateLotno";

    public void post(String code, String message, JsonArray list, HashMap<String, String> params, Activity activity) {
        InventoryActivity act = ((InventoryActivity) activity);
        JsonHash row = (JsonHash) list.get(0);
        String pCode = row.getStringOrNull("code");
        String barcode = "";
        if(row.containsKey("barcode"))
        row.getStringOrNull("barcode");
        act.createNewEntry(pCode,barcode);
        act.updateBadge1();
    }

    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        InventoryActivity act = ((InventoryActivity) activity);
        U.beepError(activity, message);
        act.setProc(act.PROC_LOT_NO);
        act._sts(R.id.quantity, "");
        act._sts(R.id.productCode, "");
    }

}
