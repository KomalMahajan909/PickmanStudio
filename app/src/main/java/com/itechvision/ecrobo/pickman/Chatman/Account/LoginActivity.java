package com.itechvision.ecrobo.pickman.Chatman.Account;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.itechvision.ecrobo.pickman.Activity.ScannerBindActivity;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Ship.TruckBatchActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.R.id;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.CommonFunctions;
import com.itechvision.ecrobo.pickman.Util.U;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

public class LoginActivity extends ScannerBindActivity implements MainAsynListener {

    @BindView(R.id.logIn)
    Button loginBtn;
    @BindView(R.id.domain) Spinner domainName;
    @BindView(id.language) Spinner language;
    @BindView(R.id.deviceID)  TextView deviceId;
    @BindView(R.id.username) EditText username;
    @BindView(R.id.password) EditText password;

    private int REQUEST_APP_UPDATE = 1991;
    public static String user=null;
    public static String appVersion = null;
    protected int mProcNo = 0;    //
    private String TAG = LoginActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;
    SharedPreferences spDomain;
    AppUpdateManager appUpdateManager;
    public static final String DOMAINPREFERENCE = "domain";
    public static final String MYPREFERENCE = "MyPrefs";
    public static final String ISUSERLOGIN = "isUserLogin";
    public static final int PROC_USERNAME = 1;
    int pos = 0;
    public static final int PROC_PASS = 2;
    String item;
    boolean urlset = false;
    String domain[] = {"01.AIR-LOGI","02.AIR-LOGI-SPOOL","03.AIR-LOGI-ST-D","04.BEELOGi_Biz","05.BEELOGi_Biz-SSL","06.BEELOGi_Net","07.BEELOGi_Net-SSL","08.BEELOGi_ST","09.AIR-LOGI_ST2" ,"10.AIR-LOGI-ST2-ECS","11.AIR-LOGI-ST3","12.AIR-LOGI-ST4"/*, "ITECH", "ITECH-1"*//*,"SUKHRAJ","SUKHRAJ-1"*/ };
    ECRApplication app = new ECRApplication();
    String adminID = "";
    String langArr[] = {"Choose Language","English", "Japanese"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(LoginActivity.this);

        File file = new File(Environment.getExternalStorageDirectory() + "/" + "PickmanLog.txt");
        file.delete();

        deviceId.setText(((ECRApplication) getApplicationContext()).getSerial());
        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        String shopname = BaseActivity.getShopinfo();
        SharedPreferences.Editor editor = sharedPreferences.edit();
   //   editor.putBoolean("PackingList", BaseActivity.getPackingList());
        editor.putString("shopName", shopname);
        editor.commit();

        final Intent i = getIntent();
        if (i != null) {
            if (i.hasExtra("ACTION")) {
                if (i.getStringExtra("ACTION").equals("logout")) {
                    ((ECRApplication) getApplication()).setAdminId("");
                    SharedPreferences.Editor edit = spDomain.edit();
                    edit.putBoolean(ISUSERLOGIN, false);
                    edit.putString("admin_id", "");
                    edit.putString("user_name", "");
                    edit.putString("MacAddress","");
                    edit.commit();
                    CommonFunctions.setAccessPoint("");
                    CommonFunctions.baseUrl="";

                    editor.putString("shopID", "");
                    editor.putString("shopName", "");
                    editor.commit();
                    BaseActivity.setShopinfo("");
                    BaseActivity.setshoppos(0);
                    Log.e("LoginActivity", "Shopname And id" + sharedPreferences.getString("shopID", null));

                    BaseActivity.setbarcodeMachinePos(0);
                    BaseActivity.setbarcodeselectedPrinterPOS(0);
                    BaseActivity.setbarcodeselectedPrinterID("");
                    BaseActivity.setBarcodeSlipMachinePos(0);
                    BaseActivity.setBarcodeSlipselectedPrinterPOS(0);
                    BaseActivity.setBarcodeSlipselectedPrinterID("");
                    BaseActivity.setinvoiceMachinePos(0);
                    BaseActivity.setinvoiceselectedPrinterPOS(0);
                    BaseActivity.setinvoiceselectedPrinterID("");
                    BaseActivity.setintegratedMachinePos(0);
                    BaseActivity.setintegratedselectedPrinterPOS(0);
                    BaseActivity.setintegratedselectedPrinterID("");
                    BaseActivity.setPostPayMachinePos(0);
                    BaseActivity.setPostPayselectedPrinterPOS(0);
                    BaseActivity.setPostPayselectedPrinterID("");
                    BaseActivity.setCsvMachinePos(0);
                    BaseActivity.setCsvselectedPrinterPOS(0);
                    BaseActivity.setCsvselectedPrinterID("");

                }
            }
        }

            appUpdateManager = AppUpdateManagerFactory.create(this);
            Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

            appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        // For a flexible update, use AppUpdateType.FLEXIBLE
                        && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
                    // Request the update.
                    try {
                        appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        IMMEDIATE,
                       LoginActivity.this,
                        REQUEST_APP_UPDATE);

                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }});

        if(!TruckBatchActivity.cancelList .isEmpty())
            TruckBatchActivity.cancelList.clear();

        if (mProcNo == 0) nextProcess();
        loginBtn = (Button) _g(R.id.logIn);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, domain);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     // Apply the adapter to the spinner
        domainName.setAdapter(adapter);
        domainName.setSelection(BaseActivity.getdomainpos());
        Log.e("Login Activity ", "Domainnnnn  " + BaseActivity.getdomainpos());
        domainName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                 CommonDialogs.customToast(LoginActivity.this, "Selected: " + item);
                SharedPreferences.Editor editor = spDomain.edit();
                String url = null;
                if (position > 0) {
                    editor.putString("domaintag", item);

                    if(item.equals("01.AIR-LOGI"))
                      url="https://api.air-logi.com/service";

                     else if(item.equals("02.AIR-LOGI-SPOOL"))
                        url="https://www3.air-logi.com/service";
                    else if(item.equals("03.AIR-LOGI-ST-D"))
                        url="https://staging.air-logi.com/service";
                    else if(item.equals("04.AIR-LOGI_ST3"))
                        url="http://13.230.66.94/service";

                    else if(item.equals("09.AIR-LOGI_ST2"))
                        url = "https://st2.air-logi.com/service";

                    else if(item.equals("04.BEELOGi_Biz"))
                        url = "http://www.beelogi.biz/service";

                    else if(item.equals("05.BEELOGi_Biz-SSL"))
                        url = "https://www.beelogi.biz/service";

                    else if(item.equals("06.BEELOGi_Net"))
                        url = "http://beelogi.net/service";

                    else if(item.equals("07.BEELOGi_Net-SSL"))
                        url = "https://beelogi.net/service";

                    else if(item.equals("08.BEELOGi_ST"))
                        url = "https://beelogi-st.air-logi.com/service";

                    else if (item.equals("10.AIR-LOGI-ST2-ECS"))
                        url = "https://staging2.air-logi.com/service";

                    else if(item.equals("11.AIR-LOGI-ST3"))
                        url = "https://staging3.air-logi.com/service";

                    else if(item.equals("12.AIR-LOGI-ST4"))
                        url = "https://staging4.air-logi.com/service";

                    else if(item.equals("ITECH"))
                        url = "http://airlogi.itechvision.in/service";

                    else if(item.equals("ITECH-1"))
                        url = "http://air-logi.itechvision.in/service";

                    else if(item.equals("SUKHRAJ"))
                        url = "https://airlogi.sukhraj.me/service";

                    else if(item.equals("SUKHRAJ-1"))
                        url = "https://air-logi.sukhraj.me/service";


                    editor.putString("domain", url);
                    editor.putInt("domainPosition",0);
                    BaseActivity.setdomainpos(position);
                    BaseActivity.setUrl(url);
                    editor.commit();
                    CommonFunctions.setAccessPoint(url);

                    U.beepSuccess(LoginActivity.this, "Domain name updated");
                    urlset = true;
                } else {
                    url = "https://api.air-logi.com/service";
                    editor.putString("domain", url);
                    editor.putString("domaintag", "01.AIR-LOGI");
                    BaseActivity.setdomainpos(0);
                    BaseActivity.setUrl(url);
                    editor.commit();
                    CommonFunctions.setAccessPoint(url);

                    Log.e("Login Activity ", "URL selected is set as " + url);
                    U.beepSuccess(LoginActivity.this, "Domain name updated");
                    urlset = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SharedPreferences.Editor editor = spDomain.edit();
                String initialUrl = spDomain.getString("domain", "https://api.air-logi.com/service");
                editor.putString("domain", "https://api.air-logi.com/service");
                editor.putString("domaintag", "01.AIR-LOGI");
                BaseActivity.setdomainpos(0);
                BaseActivity.setUrl(initialUrl);
                editor.commit();
                urlset = false;
                CommonFunctions.setAccessPoint(initialUrl);

            }
        });

        firstAction();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("LoginActivity", "domainPosition1111  " + BaseActivity.getdomainpos());
                if (urlset == false || BaseActivity.getdomainpos() == 0) {
                    SharedPreferences.Editor editor = spDomain.edit();
                    String initialUrl = spDomain.getString("domain", "https://api.air-logi.com/service");

                    editor.putString("domain", "https://api.air-logi.com/service");
                    editor.putString("domaintag", "01.AIR-LOGI");
                    BaseActivity.setdomainpos(0);
                    BaseActivity.setUrl("https://api.air-logi.com/service");
                    editor.commit();
                    CommonFunctions.setAccessPoint(initialUrl);

                }if (isFormValidate()) {
                    // TODO: Go login
                    Log.e("LoginActivity", "domainPosition  " + BaseActivity.getdomainpos());
                    Log.d("DEBUG", "Send login request");
                    login_action();
                } else {
                    CommonDialogs.customToast(LoginActivity.this,"Username, Password not null");
                }
            }
        });

        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, langArr);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        language.setAdapter(adapter1);
        language.setSelection(BaseActivity.getlangpos());

        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position != 0 ) {
                    BaseActivity.setlangpos(position);
                 } else {
                    BaseActivity.setlangpos(0);
                 }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                BaseActivity.setlangpos(0);
             }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_APP_UPDATE) {
            if (resultCode != RESULT_OK) {

            }
        }
    }


    public void nextProcess() {
        setProc(PROC_USERNAME);
    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        switch (procNo) {
            case PROC_USERNAME:
                username.setFocusable(true);
                username.requestFocus();
                break;
            case PROC_PASS:
                password.setFocusable(true);
                password.requestFocus();
                break;
        }

    }

    public void login_action() {
        Globals.getterList = new ArrayList<>();
        Globals.getterList.add(new ParamsGetter("authId",app.getSerial()));
        Globals.getterList.add(new ParamsGetter("username", _gts(id.username)));
        Globals.getterList.add(new ParamsGetter("admin_id","LOGIN"));
        Globals.getterList.add(new ParamsGetter("password", _gts(id.password)));

        new MainAsyncTask(this, Globals.Webservice.Login, 1, LoginActivity.this, "Form", Globals.getterList).execute();

    }

    public Boolean isFormValidate() {
        Log.e("LoginActivity", "form validateeee  ");
        if ((username.getText()).equals("") || (password.getText()).equals(""))
            return false;
        if (isInvalidCharInUsername(_gts(R.id.username)))
            return false;
        return true;
    }

    private boolean isInvalidCharInUsername(String username) {
        String strProhibited = "";
        strProhibited = "\\"; // \
        if (username.indexOf(strProhibited) != -1) {
            return true;
        }

        strProhibited = "\""; // "
        if (username.indexOf(strProhibited) != -1) {
            return true;
        }

        strProhibited = "*"; // *
        if (username.indexOf(strProhibited) != -1) {
            return true;
        }

        strProhibited = "!"; // !
        if (username.indexOf(strProhibited) != -1) {
            return true;
        }


        strProhibited = "?"; // ?
        if (username.indexOf(strProhibited) != -1) {
            return true;
        }

        strProhibited = "&"; // &
        if (username.indexOf(strProhibited) != -1) {
            return true;
        }

        strProhibited = "("; // (
        if (username.indexOf(strProhibited) != -1) {
            return true;
        }

        strProhibited = ")"; // )
        if (username.indexOf(strProhibited) != -1) {
            return true;
        }

        strProhibited = "$"; // $
        if (username.indexOf(strProhibited) != -1) {
            return true;
        }

        strProhibited = "#"; // #
        if (username.indexOf(strProhibited) != -1) {
            return true;
        }

        strProhibited = "="; // =
        if (username.indexOf(strProhibited) != -1) {
            return true;
        }

        strProhibited = ";"; // ;
        if (username.indexOf(strProhibited) != -1) {
            return true;
        }

        strProhibited = "'"; // '
        if (username.indexOf(strProhibited) != -1) {
            return true;
        }
        return false;
    }

    public void userLoggedIn(String adminId, String role, String warehouseId) {
        Log.d("DEBUG", "User logged in successfully");
        CommonDialogs.customToast(LoginActivity.this,"Logged In");
        String user = _gt(id.username).getText().toString().trim();

        // TODO Save login session
        SharedPreferences.Editor edit = spDomain.edit();
        edit.putString("admin_id", adminId);
        edit.putString("user_name", user);
        edit.putString("role", role);
        edit.putString("warehouse_id", warehouseId);
        edit.putBoolean(ISUSERLOGIN, true);
        edit.commit();
        callNextActivity(adminId);
    }

    private void callNextActivity(String adminId) {
        Intent i = new Intent(this, SettingActivity.class);
        String user = _gt(id.username).getText().toString().trim();
        i.putExtra("ADMINID", adminId);
        i.putExtra("USERNAME", user);
        startActivity(i);
        this.finish();
    }

    private boolean isUserLogin() {
        return spDomain.getBoolean(ISUSERLOGIN, false);
    }

    private void firstAction() {
        Log.e("Login Activity ", "Domainnnnn admin set " + spDomain.getString("admin_id", ""));
        if (isUserLogin())
            callNextActivity(spDomain.getString("admin_id", ""));
    }

    @Override
    public void clearEvent() {

    }

    @Override
    public void allclearEvent() {

    }

    @Override
    public void skipEvent() {

    }

    @Override
    public void inputedEvent(String buff) {
        switch (mProcNo) {
            case PROC_USERNAME:
                setProc(PROC_PASS);
                break;
            case PROC_PASS:
                loginBtn.performClick();
                break;
        }
    }

    @Override
    public void keypressEvent(String key, String buff) {

    }

    @Override
    public void scanedEvent(String barcode) {
        if (!barcode.equals("")) {
            if (mProcNo == PROC_USERNAME) {
                _sts(id.username, barcode);
            } else if (mProcNo == PROC_PASS) {
                _sts(id.password, barcode);
            }
            this.inputedEvent(barcode);
        }
    }

    @Override
    public void deleteEvent(String barcode) {

    }

    @Override
    public void onBackPressed() {
       //super.onBackPressed();
    }

    @Override
    public void enterEvent() {

    }

    @Override
    protected void onResume() {
        super.onResume();

     appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
     @Override
     public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                      try {
                        appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo, IMMEDIATE, LoginActivity.this,
                        REQUEST_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }

    @Override
    public void onPostSuccess(Object result, int flag, boolean isSucess) {
        String result1 = result.toString();

        if (isSucess) {
            if (result != null) {
                try {

                    if (flag == 1) {
                        Globals.jsonObj = new JSONObject(result1);
                        String code = Globals.jsonObj.getString("code");
                        String msg = Globals.jsonObj.getString("message");
                        Globals.jsonArr = Globals.jsonObj.getJSONArray("results");

                        U.beepKakutei(this, null);


                        if (code == null) {
                            Log.e(TAG, "CODEeee=======Nulllll");
                            U.beepError(this, "ネットワーク接続でエラーが発生しました。インターネットに接続できるか確認して下さい。");
                            System.out.print("FatalException UserValidationnnnn  ");
                        //  throw new HttpRequestException("Json形式ではない戻り値です。" + result);
                        }
                        else if ("0".equals(code)) {
                            JSONObject row = (JSONObject)Globals.jsonArr.get(0);

                            Log.e("SendLogs1113333333333", code + "  " + msg + "  " + result1);
                            user= _gt(R.id.username).getText().toString().trim();
                            appVersion =_gtxtv(id.version).getText().toString().trim();
                            if ( row.has("role_id") &&  row.has("warehouse_id"))
                                userLoggedIn(row.getString("admin_id"),row.getString("role_id"),row.getString("warehouse_id"));
                            else if(row.has("role_id")|| !row.has("warehouse_id"))
                                userLoggedIn(row.getString("admin_id"),row.getString("role_id"),"");
                            else
                                userLoggedIn(row.getString("admin_id"),"","");
                        } else
                            U.beepError(this,msg);
                    }
                } catch (Exception e) {
                    System.out.print(e);
                }
            }
        }
    }

    @Override
    public void onPostError(int flag) {

    }
}