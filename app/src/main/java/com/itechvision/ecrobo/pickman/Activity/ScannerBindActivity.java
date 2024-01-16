package com.itechvision.ecrobo.pickman.Activity;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.itechvision.ecrobo.pickman.HomeWatcher;
import com.itechvision.ecrobo.pickman.Util.HWKey;
import com.itechvision.ecrobo.pickman.Util.U;
import com.opticon.scannersdk.scanner.BarcodeEventListener;
import com.opticon.scannersdk.scanner.ReadData;
import com.opticon.scannersdk.scanner.Scanner;
import com.opticon.scannersdk.scanner.ScannerInfo;
import com.opticon.scannersdk.scanner.ScannerManager;
import com.opticon.scannersdk.scanner.ScannerType;
import com.opticon.settings.ScannerSettings;

import java.io.File;

import jp.co.opto.opnsdk.BluetoothServiceState;
import jp.co.opto.opnsdk.Opn2002BluetoothService;
import jp.co.opto.opnsdk.observer.IBluetoothObserver;

public abstract class ScannerBindActivity extends ECRActivity implements IBluetoothObserver, BarcodeEventListener {

    // キーバッファ、スキャンバッファ
    protected StringBuffer mBuff = new StringBuffer();
    protected boolean mIsCtrl = false;
    protected boolean mIsShift = false;
    HomeWatcher mHomeWatcher;
    protected Opn2002BluetoothService mBTService;
    Context context=this;
    public static boolean pinned =false;
    String barcode= "";
    private final KeyCharacterMap characterMap = KeyCharacterMap.load(KeyCharacterMap.FULL);
    private boolean mFirstBarcodeLatch;
    private boolean mSecondBarcodeLatch;
    private final static String TAG = ScannerBindActivity.class.getSimpleName();

    public ScannerManager scannerManager;
    public Scanner scanner;

    public   boolean isScanning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bind Bluetooth Service(Must bind service before start)

        mBTService = Opn2002BluetoothService.getInstance(this);
        mHomeWatcher = new HomeWatcher(this);

        mHomeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                {
                    Log.e("ScannerbindActivity", "lock not enabled");
                    if(pinned == false)
                        mBTService.stop();
                }
                // do something here...
            }
            @Override
            public void onRecentAppsPressed() {
            }
        });
        mHomeWatcher.startWatch();

        // Creates instance of the manager.
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(context);

        scannerManager = ScannerManager.getInstance(this);
        for(ScannerInfo info: scannerManager.getScannerInfoList()){
            //ソフトウェアスキャナ(端末内臓スキャナ)を操作したいとき
            if(info.getType() == ScannerType.SOFTWARE_SCANNER){
                scanner = scannerManager.getScanner(info);
                break;
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mBTService != null) {
            if (!Build.SERIAL.equals("0123456789ABCDEF") && !Build.SERIAL.equals("unknown")) {
                if (!mBTService.isEnabled()) {

//						BluetoothSettings.SetScaner(context);
                    Log.e("ScannerBindActivityyyyy","NO Bluetooth");
                    toast("Bluetooth is not available");
                    finish();
                    return;
                }
            }

            //必要に応じて、Bluetoothを有効にする設定画面を表示する
            mBTService.showBluetoothSetting(this);

            if (mBTService.getState() == BluetoothServiceState.none) {
                Log.e("ScannerBindActivityyyyy","Found  Bluetooth Connection");
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                    {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                        return;
                    }
                }
                mBTService.start();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mBTService != null){
            Log.e("ScannerbindActivity", "onResumeeeee");
            mBTService.addObserver(this);
        }

        if(scanner != null){
            scanner.addBarcodeEventListener(this);
            Log.d(TAG,""+scanner.init()) ;
        }
        setReceiver();
        refreshStatusLabel();
    }

    @Override
    protected void onPause() {
        if(mBTService != null){
            mBTService.removeObserver(this);
        }
        if(scanner != null){
            scanner.deinit();
            scanner.removeBarcodeEventListener();
        }
        if(receiver!= null){
            unregisterReceiver(receiver);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.e("ScannerbindActivity", "lock destroy not enabled");
        mHomeWatcher.stopWatch();
//		mBTService.stop();

        if(scanner != null){
            scanner.deinit();
            if(scanner.isConnected()) scanner.removeBarcodeEventListener();
        }
        super.onDestroy();

        trimCache(this);
    }
    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    private void refreshStatusLabel(){
        if(mBTService.getState().compareTo(BluetoothServiceState.connected) == 0) {
            Log.e("ScannerbindActivity", "Connected to bluetooth device");
            toast("Connected");
        }
        else{
            toast("Not connected to bluetooth device");
        }
    }


    public Activity disableItem(String id) {
        Log.e("ScannerbindActivity", "Disable Item");return this;
    }

    public String keyput(int keyCode) {

        Log.e("ScannerbindActivity", "KeyPutttttttttttt");
        return keyput(HWKey.valueOf(keyCode).getName());
    }


    public String keyput(String key) {
        Log.e("ScannerbindActivity", "Keyput1111");
        if (key.equals("↩") || key.equals("確定") ||  key.equals("ENTR") || key.equals("Entr")|| key.equals("ENT")) {
            String s = mBuff.toString();
            mBuff.delete(0, mBuff.length());
            inputedEvent(s);
        } else if (key.equals("CLR") || key.equals("クリア") || key.equals("Clr")) {
            mBuff.delete(0, mBuff.length());
            clearEvent();
        } else if (key.equals("SKIP") || key.equals("Skip") || key.equals("ｽｷｯﾌﾟ")) {
            Log.e("ScannerbindActivity", "Keyput115555");
            skipEvent();
        }
        else if (key.equals("ALL-c") || key.equals("ALL-C") || key.equals("全ｸﾘｱ")) {
            Log.e("ScannerbindActivity", "Keyput11333444");
            allclearEvent();
        }
        else if (key.equals("DEL") || key.equals("削除") || key.equals("Del")) {
            Log.e("ScannerbindActivity", "Keyput114444");
            U.chop(mBuff);
            deleteEvent(mBuff.toString());
        }
        else {
            mBuff.append(key);
            Log.e("ScannerbindActivity", "KeypressEventttt");
            keypressEvent(key, mBuff.toString());
        }
        return mBuff.toString();
    }

    public String getBuffer() {
//        BluetoothConnect.BluetoothSend(mBuff.toString());
        return mBuff.toString();
    }

    public abstract void inputedEvent(String buff);

    public abstract void clearEvent();
    public abstract void allclearEvent();
    public abstract void skipEvent();
    public abstract void keypressEvent(String key, String buff);

    public abstract void scanedEvent(String barcode);

    public abstract void enterEvent();

    public abstract void deleteEvent(String barcode);

    @Override
    public  void onReadData(ReadData readData){
        onBarcodeScanned(readData.getText());
    }
    @Override
    public void onImageData(Bitmap bitmap, byte[] byteArray, int i) {
        //スキャナから画像を取得した際に呼ばれます
        Log.d(TAG,"onImageData");
      /*  ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);*/
    }


    @Override
    public void receive(String data) {

        if (data != null) {
            if(data.length() <= 2) {

                char [] c = data.toCharArray();
                if(String.format("%04x", (int) c[0]).equals("000d"))
                    enterEvent();
            } else {

                data = data.replace((char) 0x0D, (char) 0x0A);
                data = data.replaceAll("" + (char) 0x6, "");
                data = data.replaceAll("" + (char) 0x15, "");
                data = data.trim();

                if (data.substring(data.length() - 1).equals("イ")) {
                    data = data.replace("イ", "");
                }

                mBuff.delete(0, mBuff.length());
                mBuff.append(data);
                scanedEvent(getBuffer().toString());
                mBuff.delete(0, mBuff.length());

            }
        }
    }

    @Override
    public void connected(BluetoothDevice bluetoothDevice) {
        toast("connected  " +bluetoothDevice);
        mBTService.enableAckNak();
        toast("connected ");

    }

    @Override
    public void connectFailed() {
        toast("connection failed");
    }

    @Override
    public void connectionLost() {
        toast("connection lost");
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                mIsCtrl = false;
                mIsShift = false;
                scanedEvent(getBuffer().toString());
                mBuff.delete(0, mBuff.length());
                return true;
            }

//			else if((event.getKeyCode() == KeyEvent.KEYCODE_DEL)){
//		    mIsCtrl = false;
//		    mIsShift = false;
//		    Log.d("DEBUG", "ScanEvent:" + String.valueOf(event.getKeyCode()) + ":DELETE");
//		    U.chop(mBuff);
//		    deleteEvent(mBuff.toString());
//
//	 }

            else if (event.getKeyCode() == KeyEvent.KEYCODE_CTRL_LEFT
                    || event.getKeyCode() == KeyEvent.KEYCODE_CTRL_RIGHT) {
                mIsCtrl = true;

                return true;
            } else if (event.getKeyCode() == KeyEvent.KEYCODE_SHIFT_LEFT){
                mIsShift = true;
            } else {
                if (mIsCtrl == true) {
                    Log.d("DEBUG", "ScanEvent(CANCEL):" + String.valueOf(event.getKeyCode()) + ":" + HWKey.valueOf(event.getKeyCode()).getName());
                } else {
                    Log.d("DEBUG", "ScanEvent:" + String.valueOf(event.getKeyCode()) + ":" + HWKey.valueOf(event.getKeyCode()).getName());
                    if (mIsShift) {
                        mBuff.append(HWKey.valueOf(KeyEvent.KEYCODE_SHIFT_LEFT + event.getKeyCode()).getName());
                    }else {
                        mBuff.append(HWKey.valueOf(event.getKeyCode()).getName());
                    }
                }
                mIsCtrl = false;
                mIsShift = false;
            }
        }

        else {
            if (event.getKeyCode() == 305 )
            {
                Log.e(">>>>>>>>>>>>>>>>>>>>>>>","<<<<<<<<<<<<<<<305");
                mFirstBarcodeLatch = false;
            }
            else if (event.getKeyCode() == 304) {
                Log.e(">>>>>>>>>>>>>>>>>>>>>>>","<<<<<<<<<<<<<<<304");
                mSecondBarcodeLatch = false;
            }
        }
//        if (event.getKeyCode() == KeyEvent.KEYCODE_SPACE && mFirstBarcodeLatch && mSecondBarcodeLatch) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_SPACE && mFirstBarcodeLatch && mSecondBarcodeLatch) {
            Log.e("Barcodeeee  iss",">>>>>>>>>>>>>>>>>>>>>>>>111111  "+event.getCharacters()+"     "+mBuff);
            onBarcodeScanned(getBuffer().toString());
            mBuff.delete(0, mBuff.length());
        }
        return super.dispatchKeyEvent(event);
    }

    private void onBarcodeScanned(String barcode) {
        // Handle th
        //
        //
        // e barcode in here.
        Log.e("Scanneddddd Barcodeeee",">>>>>>>>>>>>>>>>>>>>>>>>111111  "+barcode);
        mBuff.delete(0, mBuff.length());
        scanedEvent(barcode);
    }

    //intent sample
    MyReceiver receiver;
    void setReceiver(){
        IntentFilter filter = new IntentFilter("com.opticon.decode.action");
        filter.addCategory("com.opticon.decode.category");
        receiver = new MyReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                Log.e("receive intent",intent.toString()) ;
                Log.e("receive intent","barcode type:" + intent.getStringExtra("com.opticon.decode.barcode_type")) ;
                Log.e("receive intent","barcode data:" + intent.getStringExtra("com.opticon.decode.barcode_data")) ;
            }
        };
        registerReceiver(receiver, filter);
    }

    void changeIntentSettings(){
        ScannerSettings settings = scanner.getSettings();
        if(settings != null){
            settings.softwareScanner.h35.wedge.intentIsEnable = true;
            settings.softwareScanner.h35.wedge.intentAction = "com.opticon.decode.action";
            settings.softwareScanner.h35.wedge.intentCategory = "com.opticon.decode.category";
            settings.softwareScanner.h35.wedge.intentBarcodeType = "com.opticon.decode.barcode_type";
            settings.softwareScanner.h35.wedge.intentBarcodeData = "com.opticon.decode.barcode_data";
            settings.softwareScanner.h35.wedge.intentPackageName = "com.itechvision.ecrobo.pickman";

            scanner.setSettings(settings);
        }
    }

    public class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    @Override
    public void onTimeout() {
        //読み取りを開始し、バーコードを読み取らずに時間が経過した際に呼ばれます
        Log.d(TAG,"onTimeout");
    }

    @Override
    public void onDecodeStart() {
        //読み取り開始時に呼ばれます
        isScanning = true;
        Log.d(TAG,"onDecodeStart");
    }

    @Override
    public void onDecodeStop() {
        //読み取りの可否にかかわらず、読み取り終了時に呼ばれます
        isScanning = false;
        Log.d(TAG,"onDecodeStop");
    }

    @Override
    public void onConnect() {
        //スキャナとの接続が開始された際に呼ばれます
        Log.d(TAG,"onConnect");
        changeIntentSettings();
    }

    @Override
    public void onDisconnect() {
        //スキャナとの接続が終了した際に呼ばれます
        Log.d(TAG,"onDisconnect");
    }

}

