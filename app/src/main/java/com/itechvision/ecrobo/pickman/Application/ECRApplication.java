package com.itechvision.ecrobo.pickman.Application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

//import com.bugsee.library.Bugsee;
import com.bugsee.library.Bugsee;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
//import com.crashlytics.android.Crashlytics;
//import com.crashlytics.android.answers.Answers;
//import com.crashlytics.android.answers.ContentViewEvent;


import java.io.File;
import java.util.UUID;

//import io.fabric.sdk.android.Fabric;


public class ECRApplication extends Application {

    protected String mShopId = null;
    protected Context mActiveContext = null;
    protected String mSerial = null;
    protected String mShopName = null;
    protected String mAdminId = null;

    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    public static final String ISUSERLOGIN = "isUserLogin";

    public String getShopId() {
        return mShopId;
    }

    public void setShopId(String mShopId) {
        this.mShopId = mShopId;
    }

    public void setShopName(String mShopName) {
        this.mShopName = mShopName;
    }

    public String getShopName() {
        return mShopName;
    }

    public String getSerial() {
        return mSerial;
    }

    public void setSerial(String serial) {
        this.mSerial = serial;
    }

    public String getAdminId(){
        return mAdminId;
    }

    public void setAdminId(String mAdminId){
        this.mAdminId = mAdminId;
    }

    public Context getActiveContext() {
        return mActiveContext;
    }

    public void setActiveContext(Context mActiveContext) {
        this.mActiveContext = mActiveContext;
    }

    public ECRApplication() {
        init();
    }

    public ECRApplication init() {

        setSerial(BaseActivity.get_DeviceID());

        /* mSerial = android.os.Build.SERIAL;

*/
      /*  mShopId = "563";
        mShopName = "SUGURKITCHEN（シュガーキッチン）";
        mAdminId = "";*/
        return this;
    }
    private static ECRApplication instance;

    @Override
    public void onCreate() {
//        Mint.initAndStartSession(this, "7185ea43");

        super.onCreate();

        instance = this;

        // TODO: Use your own attributes to track content views in your app
//        Answers.getInstance().logContentView(new ContentViewEvent()
//                .putContentName("Tweet")
//                .putContentType("Video")
//                .putContentId("1234")
//                .putCustomAttribute("Favorites Count", 20)
//                .putCustomAttribute("Screen Orientation", "Portait"));


    }


    public static String getDeviceId(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if (!TextUtils.isEmpty(androidId)) {
            String serial = Build.SERIAL;
            if (!"unknown".equalsIgnoreCase(serial)) {
                return androidId + serial;
            }
            return androidId;
        }

        File file = new File(context.getFilesDir(), "deviceId");
        file.mkdir();
        File[] files = file.listFiles();
        if (files.length > 0) {
            return files[0].getName();
        }
        String id = UUID.randomUUID().toString();
        (new File(file, id)).mkdir();
        return id;
    }


    public static ECRApplication getInstance() {
        return instance;
    }

    public void handleUncaughtException (Thread thread, Throwable e)
    {
//        Fabric.with(this, new Crashlytics());
        e.printStackTrace(); // not all Android versions will print the stack trace automatically
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= spDomain.edit();

        editor.putBoolean(ISUSERLOGIN, false);
        editor.commit();
        System.exit(1);


    }
}
