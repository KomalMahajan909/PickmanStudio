package com.itechvision.ecrobo.pickman.Chatman.Account;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonUtilities;
import com.itechvision.ecrobo.pickman.Util.U;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

public class SendLogActivity extends BaseActivity implements MainAsynListener, View.OnClickListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar)
    ActionBar actionbar;
    @BindView(R.id.sendlogbtn)
    Button sendbtn;

    File file=null;
    SharedPreferences sharedPreferences;
    public static final String MYPREFERENCE = "MyPrefs";
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    String TAG = SendLogActivity.class.getSimpleName();
//    private final static LogConfigurator mLogConfigrator = new LogConfigurator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_log);
        ButterKnife.bind(SendLogActivity.this);
        getIDs();
        Log.d(TAG,"On Create ");
        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        sendbtn.setOnClickListener(this);
    }

    @Override
    public void nextProcess() {

    }

    //set title and icons on actionbar
    private void getIDs() {
        // TODO Auto-generated method stub

        CommonUtilities.actionbarImplement(this, "ログ送信", " ",
                0, false,false,false );
        //menubarr
        menu = CommonUtilities.setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        CommonUtilities.relLayout1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;

            case R.id.sendlogbtn:
                sendLogFile();
                break;

            default:
                break;
        }
    }

    private void sendLogFile ()
    {
        file = extractLogToFileAndWeb();

        String adminID="";
        Globals.getterList = new ArrayList<>();
        adminID=spDomain.getString("admin_id",null);
        String shop =sharedPreferences.getString("shopID",null);
        ECRApplication app=new ECRApplication();
        Log.e("SendLogs","shopidddddd  "+BaseActivity.getShopId());
        Globals.getterList.add(new ParamsGetter("authId",app.getSerial()));
        Globals.getterList.add(new ParamsGetter("shop_id",BaseActivity.getShopId()));
        Globals.getterList.add(new ParamsGetter("admin_id",adminID));

        if (file != null) {
            Log.e("Send image path...", "" + file);
            Globals.getterList.add(new ParamsGetter("log_file", file));
        }
        new MainAsyncTask(this, Globals.Webservice.sendLog, 1, this, "Multipart", Globals.getterList).execute();

    }
    public File extractLogToFileAndWeb(){
        //set a file
        String model = Build.MODEL;

        String user = spDomain.getString("user_name","");
        if (!model.startsWith(Build.MANUFACTURER))
            model = Build.MANUFACTURER + " " + model;

        String fullName = "PickmanLog.txt";
        File mydir = this.getDir("mydir", Context.MODE_PRIVATE); //Creating an internal dir;
        File file = new File(mydir, fullName);

        //clears a file
        if(file.exists()){
            file.delete();
        }


//        File file = new File (fullName);
        InputStreamReader reader = null;
        FileWriter writer = null;
        try
        {

            String cmd = (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) ?
                    "logcat -d -v time MyApp:v dalvikvm:v System.err:v *:s" :"logcat -d -v time *:*";

//            String cmd= String.format("logcat -d -v threadtime *:*");

            // get input stream
            Process process = Runtime.getRuntime().exec(cmd);
            reader = new InputStreamReader (process.getInputStream());
            Log.e("SendLogs","File Name111111 ");
            // write output stream
            writer = new FileWriter (file);
            writer.write ("Android version: " +  Build.VERSION.SDK_INT + "\n");
            writer.write ("Device: " + model + "\n");
            writer.write ("App version: " + getResources().getString(R.string.version) + "\n");
            writer.write ("User Name  : " + user + "\n");
            writer.write ("IP Address : "+ getLocalIpAddress()+"\n");

//            writer.write();
            Log.e("SendLogs","File Name22222222 ");
            char[] buffer = new char[10000];
            do
            {
                int n = reader.read (buffer, 0, buffer.length);

                if (n == -1)
                    break;
                writer.write (buffer, 0, n);

            } while (true);

            reader.close();
            writer.close();
        }
        catch (IOException e)
        {
            if (writer != null)
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e1) {
                }

            // You might want to write a failure message to the log here.
            return null;
        }
        Log.e("SendLogs","File Name55555 ");
        return file;

    }

    @Override
    public void inputedEvent(String buff) {

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
    public void keypressEvent(String key, String buff) {

    }

    @Override
    public void scanedEvent(String barcode) {

    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {

    }
    @Override
    public void onPostSuccess(Object result, int flag, boolean isSucess) {
        Log.e("SendLogs",result.toString());
        try {

            String response= result.toString();
            JsonPullParser parser = JsonPullParser.newParser(response);
            JsonHash map = JsonHash.fromParser(parser);
            Log.e("SendLogs111"," "+map);
            String code = map.getStringOrNull("code");
            String msg="";
            JsonArray result1;
            if (code == null) {
                Log.e("ECZaikoClient","CODEeee============Nulllll");
                U.beepError(this, "ネットワーク接続でエラーが発生しました。インターネットに接続できるか確認して下さい。");
                System.out.print("FatalException UserValidationnnnn  ");

            }
            if ("0".equals(code) == true) {

                msg=map.getStringOrNull("message");
                result1= map.getJsonArrayOrNull("results");
                Log.e("SendLogs111",code+"  "+msg+"  "+result1);
                JsonHash row = (JsonHash) result1.get(0);

                String status="";

                if(row.containsKey("log_status"))
                    status=row.getStringOrNull("log_status");
                if(!status.equals("1"))
                {
                    U.beepError(this, "ログファイルは送信されません");
                }
                else {

                    Log.e("SendLogs222", "Statusssss  " + status);
                    U.beepKakutei(this,"送信完了");

                }
            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(SendLogActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })
                        .show();
            }
            else
                U.beepError(this, msg);
        }
        catch (Exception e)
        {
            System.out.print(e);
        }

    }

    @Override
    public void onPostError(int flag) {

    }
    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        Log.i(TAG, "***** IP="+ ip);
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(TAG, ex.toString());
        }
        return null;
    }
}
