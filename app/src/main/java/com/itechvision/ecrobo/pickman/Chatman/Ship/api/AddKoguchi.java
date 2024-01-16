package com.itechvision.ecrobo.pickman.Chatman.Ship.api;

import android.app.Activity;

import com.itechvision.ecrobo.pickman.Chatman.Ship.KoguchiActivity;
import com.itechvision.ecrobo.pickman.Util.U;

import net.vvakame.util.jsonpullparser.util.JsonArray;

import java.util.HashMap;

/**
 * Created by lenovo on 1/3/2019.
 */

public class AddKoguchi {

    public void post(String code, String message, JsonArray list,
                     HashMap<String, String> params, Activity activity) {


        U.beepKakutei(activity, "検品データを登録しました。");
        KoguchiActivity act = (KoguchiActivity) activity;
        act.nextProcess();

    }


    public void valid(String code, String message, JsonArray list,
                      HashMap<String, String> params, Activity activity) {
        U.beepError(activity, message);
        ((KoguchiActivity) activity).setProc(KoguchiActivity.PROC_TRACK_ID);
    }
}
