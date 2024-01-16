package com.itechvision.ecrobo.pickman.WeightConnect;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.itechvision.ecrobo.pickman.Chatman.Tshipping.BoxCashRegisterActivity;
import com.itechvision.ecrobo.pickman.R;

import java.util.ArrayList;


public class ConfigActivity extends Activity implements View.OnClickListener {
	// デバッグ
    private static final String TAG = "ConfigActivity ";
    private static final boolean D = false;

    // インテントエクストラキー名
    public static final String EXTRA_CMD_LIST = "command_list";
    public static final String EXTRA_NEED_PAIRING = "need_pairing";

    // プリファレンスキー名
    public static final String PREF_SETTING = "setting";
    public static final String PREF_BAUD = "baud";
    public static final String PREF_DATABIT = "databit";
    public static final String PREF_PARITY = "parity";
    public static final String PREF_STOPBIT = "stopbit";
    public static final String PREF_PINCODE = "pincode";
    public static final String PREF_BT_NAME = "bt_name";

    // シリアルパラメータ設定
	// RS-232Cトランシーバの
	// 保証ボーレートはMin.115.2kbps / Typ.230.4kbps
	// それより高速な設定はできないようにする
	public static final String[][] BAUD = {
		{ "1200", "1200", "SU,12\r\n" },
		{ "2400", "2400", "SU,24\r\n" },
		{ "4800", "4800", "SU,48\r\n" },
		{ "9600", "9600", "SU,96\r\n" },
		{ "19200", "19.2", "SU,19\r\n" },
		{ "28800", "28.8", "SU,28\r\n" },
		{ "38400", "38.4", "SU,38\r\n" },
		{ "57600", "57.6", "SU,57\r\n" },
		{ "115200", "115K", "SU,11\r\n" },
		{ "230400", "230K", "SU,23\r\n" },
		//{ "460800", "460K", "SU,46\r\n" },
		//{ "921600", "921K", "SU,92\r\n" },
	};
	public static final String[][] DATABIT = {
		{ "7", "7", "S7,1\r\n" },
		{ "8", "8", "S7,0\r\n" },
	};
	public static final String[][] PARITY = {
		{ "None", "None", "SL,N\r\n" },
		{ "Odd", "Odd ", "SL,O\r\n" },
		{ "Even", "Even", "SL,E\r\n" },
	};
	public static final String[][] STOPBIT = {
		{ "1", "1", "SQ,0\r\n" },
		{ "2", "2", "SQ,256\r\n" },
	};
    
    // シリアルパラメータ設定インデックス
    private int mBaudIndex = 0;
    private int mDataIndex = 0;
    private int mParityIndex = 0;
    private int mStopIndex = 0;

    // レイアウトビュー
    private Button mButton06;		// 設定変更ボタン
    private Button mButton07;		// デフォルトボタン
    private Button mButton08;		// キャンセルボタン
    private Spinner mSpinner02;		// ボーレート選択用スピナー
    private Spinner mSpinner03;		// データビット選択用スピナー
    private Spinner mSpinner04;		// パリティ選択用スピナー
    private Spinner mSpinner05;		// ストップビット選択用スピナー
    private CheckBox mCheckBox01;	// PINコード設定用チェックボックス
    private EditText mEditText02;	// PINコード設定用エディットテキスト
    private CheckBox mCheckBox02;	// デバイス名設定用チェックボックス
    private EditText mEditText03;	// デバイス名設定用エディットテキスト

    // シリアルパラメータ設定 Array adapter
    private ArrayAdapter<String> mBaudArrayAdapter;
    private ArrayAdapter<String> mDataArrayAdapter;
    private ArrayAdapter<String> mParityArrayAdapter;
    private ArrayAdapter<String> mStopArrayAdapter;
    
    // Bluetooth設定
    private String mPinCode = null;
    private String mBtName = null;
    private String mDefultName = null;

    // 設定コマンド
    private ArrayList<String> mCmdList = new ArrayList<String>();

    /* ================================================================
	// 内容
	// デバイスの設定データから設定値を取得する

	  ***Settings***
	  BTA=000666510408
	  BTName=FireFly-0408
	  Baudrt(SW4)=115K
	  Parity=None
	  Mode  =Slav
	  Authen=0
	  Encryp=0
	  PinCod=1234
	  Bonded=0
	  Rem=NONE SET

	  ***Settings***
	  BTA=000666510408
	  BTName=ttt-0404
	  Baudrt=38.4
	  Parity=Even - 7BIT
	  Mode  =Slav
	  Authen=0
	  Encryp=0
	  PinCod=2234
	  Bonded=0
	  Rem=NONE SET

	//引数
	//  	String sourceBuf			設定データが格納されているStringクラスのポインタ
	//  	String searchBuf			設定項目文字列が格納されているStringクラスのポインタ
	//  	StringBufferchar parseBuf	設定値を格納するStringBufferクラスのポインタ
	//戻り値
	//  	TRUE	成功
	//  	FALSE	失敗
	// ================================================================
    */
    private boolean parseDeviceInfo(String sourceBuf, String searchBuf, StringBuffer parseBuf) {
		int start = 0;
		int end = 0;
		
		if (sourceBuf == null || searchBuf == null || parseBuf == null) {
			return false;
		}
		
        try {
    		String tempBuf = "";

    		start = sourceBuf.indexOf(searchBuf);
    		if(start == -1) {
    			return false;
    		}
    		tempBuf =  sourceBuf.substring(start);
    		start = tempBuf.indexOf("=");
    		if(start == -1) {
    			return false;
    		}
    		start += "=".length();
    		end = tempBuf.indexOf("\r\n");
    		if(end == -1) {
    			return false;
    		}

            // 第1引数はもちろん、第2引数も、文字列の範囲外を
            // 指定するとIndexOutOfBoundsException例外が投げられます。
    		tempBuf = tempBuf.substring(start, end);

    		parseBuf.setLength(0);
    		parseBuf.append(tempBuf);
        }
        catch( IndexOutOfBoundsException e ) {
            //e.printStackTrace();
            if (D) Log.e(TAG, "start:" + start);
            if (D) Log.e(TAG, "end:" + end);
			return false;
        }
		
		return true;
    }
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(D) Log.e(TAG, "+++ ON CREATE +++");
		setContentView(R.layout.activity_config);

		mButton06 = (Button) findViewById(R.id.Button06);
		mButton07 = (Button) findViewById(R.id.Button07);
		mButton08 = (Button) findViewById(R.id.Button08);
        mSpinner02 = (Spinner) findViewById(R.id.Spinner02);
        mSpinner03 = (Spinner) findViewById(R.id.Spinner03);
        mSpinner04 = (Spinner) findViewById(R.id.Spinner04);
        mSpinner05 = (Spinner) findViewById(R.id.Spinner05);
		mCheckBox01 = (CheckBox) findViewById(R.id.CheckBox01);
		mEditText02 = (EditText) findViewById(R.id.EditText02);
		mCheckBox02 = (CheckBox) findViewById(R.id.CheckBox02);
		mEditText03 = (EditText) findViewById(R.id.EditText03);

		mBaudArrayAdapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item);
		mBaudArrayAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item); 
		mDataArrayAdapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item);
		mDataArrayAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item); 
		mParityArrayAdapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item);
		mParityArrayAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item); 
		mStopArrayAdapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item);
		mStopArrayAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item); 
		
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}

		String setting = extras.getString(BoxCashRegisterActivity.SETTING);
        if (D) Log.d(TAG, "setting:" + setting);
        mDefultName = extras.getString(BoxCashRegisterActivity.DEFAULT_NAME);
        if (D) Log.d(TAG, "mDefultName:" + mDefultName);
		
		StringBuffer parseBuf = new StringBuffer("");
		String tempBuf = "";

		parseDeviceInfo(setting, "Baudrt", parseBuf);
		// 有効な文字数を4とする
		parseBuf.setLength(4);
        if (D) Log.d(TAG, "parseBuf1:" + parseBuf);
    	for (int i = 0; i < BAUD.length; i++) {
    		Log.d(TAG, "BAUD.length:" + BAUD.length);
    		if(BAUD[i][1].equals(parseBuf.toString())) {
    			mBaudIndex = i;
    	        if (D) Log.d(TAG, "mBaudIndex:" + i);
    		}
    		mBaudArrayAdapter.add(BAUD[i][0]);
        }

		SharedPreferences sp = getSharedPreferences(mDefultName, MODE_PRIVATE);
		tempBuf = sp.getString(PREF_DATABIT, "8");
		parseBuf.setLength(0);
		parseBuf.append(tempBuf);
    	for (int i = 0; i < DATABIT.length; i++) {
    		if(DATABIT[i][1].equals(parseBuf.toString())) {
    			mDataIndex = i;
    	        if (D) Log.d(TAG, "mDataIndex:" + i);
    		}
    		mDataArrayAdapter.add(DATABIT[i][0]);
        }
    	
		parseDeviceInfo(setting, "Parity", parseBuf);
		// 有効な文字数を4とする
		parseBuf.setLength(4);
        if (D) Log.d(TAG, "parseBuf3:" + parseBuf);
    	for (int i = 0; i < PARITY.length; i++) {
    		if(PARITY[i][1].equals(parseBuf.toString())) {
    			mParityIndex = i;
    	        if (D) Log.d(TAG, "mParityIndex:" + i);
    		}
    		mParityArrayAdapter.add(PARITY[i][0]);
        }
    	
		tempBuf = sp.getString(PREF_STOPBIT, "1");
		parseBuf.setLength(0);
		parseBuf.append(tempBuf);
    	for (int i = 0; i < STOPBIT.length; i++) {
    		if(STOPBIT[i][1].equals(parseBuf.toString())) {
    			mStopIndex = i;
    	        if (D) Log.d(TAG, "mStopIndex:" + i);
    		}
    		mStopArrayAdapter.add(STOPBIT[i][0]);
        }

        mSpinner02.setAdapter(mBaudArrayAdapter);
        mSpinner02.setSelection(mBaudIndex);
        mSpinner03.setAdapter(mDataArrayAdapter);
        mSpinner03.setSelection(mDataIndex);
        mSpinner04.setAdapter(mParityArrayAdapter);
        mSpinner04.setSelection(mParityIndex);
        mSpinner05.setAdapter(mStopArrayAdapter);
        mSpinner05.setSelection(mStopIndex);
    	
		parseDeviceInfo(setting, "PinCod", parseBuf);
        if (D) Log.d(TAG, "parseBuf5:" + parseBuf);
        mPinCode = parseBuf.toString();
        mEditText02.setText(parseBuf);

		parseDeviceInfo(setting, "BTName", parseBuf);
        if (D) Log.d(TAG, "parseBuf6:" + parseBuf);
        mBtName = parseBuf.toString();
        mEditText03.setText(parseBuf);
        
        // 何度も呼ばれる場合があることを念頭に入れてmCmdList は初期化しておく
        mCmdList.clear();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");

		mButton06.setOnClickListener(this);
		mButton07.setOnClickListener(this);
		mButton08.setOnClickListener(this);
		mCheckBox01.setOnClickListener(this);
		mCheckBox02.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) { 
	        case R.id.Button06: 
		        if (D) Log.d(TAG, "button06.onClick");
	        	int index;
	        	boolean needPairing = false;

		        index = mSpinner02.getSelectedItemPosition();
		        if(mBaudIndex != index)
		        {
			        mCmdList.add(BAUD[index][2]);
			        mBaudIndex = index;
		        }
		        
		        index = mSpinner03.getSelectedItemPosition();
		        if(mDataIndex != index)
		        {
			        mCmdList.add(DATABIT[index][2]);
			        mDataIndex = index;
		        }

		        index = mSpinner04.getSelectedItemPosition();
		        if(mParityIndex != index)
		        {
			        mCmdList.add(PARITY[index][2]);
			        mParityIndex = index;
		        }

		        index = mSpinner05.getSelectedItemPosition();
		        if(mStopIndex != index)
		        {
			        mCmdList.add(STOPBIT[index][2]);
	    			mStopIndex = index;
		        }
		        
				if (mCheckBox01.isChecked())
				{
					mPinCode = mEditText02.getText().toString();
			        if (D) Log.d(TAG, "mPinCode:" + mPinCode);
			        String tempString = String.format("SP,%s\r\n", mPinCode);
			        if (D) Log.d(TAG, "cmd(pincode):" + tempString);
			        mCmdList.add(tempString);
			        needPairing = true;
				}

				if (mCheckBox02.isChecked())
				{
					mBtName = mEditText03.getText().toString();
			        if (D) Log.d(TAG, "mBtName:" + mBtName);
			        String tempString = String.format("SN,%s\r\n", mBtName);
			        if (D) Log.d(TAG, "cmd(mBtName):" + tempString);
			        mCmdList.add(tempString);
				}

	            // result用インテントを作成しコマンド送信データを追加する
	            intent = new Intent();
	            intent.putExtra(EXTRA_CMD_LIST, mCmdList);
	            intent.putExtra(EXTRA_NEED_PAIRING, needPairing);

				// resultを設定してこのアクティビティを終了する
		        setResult(Activity.RESULT_OK, intent);

		        putPreferences(false);
		        finish();
	            break; 
	        case R.id.Button07: 
		        if (D) Log.d(TAG, "button07.onClick");

				Bundle extras = getIntent().getExtras();
				if (extras != null) {
					mBtName = extras.getString(BoxCashRegisterActivity.DEFAULT_NAME);
			        if (D) Log.d(TAG, "mBtName:" + mBtName);
			        String tempString = String.format("SN,%s\r\n", mBtName);
			        if (D) Log.d(TAG, "cmd(mBtName):" + tempString);
			        mCmdList.add(tempString);
				}

		        mCmdList.add("SF,1\r\n");
				
	            // result用インテントを作成しコマンド送信データを追加する
	            intent = new Intent();
	            intent.putExtra(EXTRA_CMD_LIST, mCmdList);
	            intent.putExtra(EXTRA_NEED_PAIRING, true);

				// resultを設定してこのアクティビティを終了する
		        setResult(Activity.RESULT_OK, intent);
		        
		        putPreferences(true);
		        finish();
	            break; 
	        case R.id.Button08: 
				// resultを設定してこのアクティビティを終了する
		        setResult(Activity.RESULT_CANCELED, new Intent());
		        finish();
	            break; 
	        case R.id.CheckBox01: 
				if (mCheckBox01.isChecked())
				{
					mEditText02.setFocusable(true);
					mEditText02.setFocusableInTouchMode(true);
					mEditText02.setEnabled(true);
				} else {
					mEditText02.setFocusable(false);
					mEditText02.setFocusableInTouchMode(false);
					mEditText02.setEnabled(false);
				}
	            break; 
	        case R.id.CheckBox02: 
				if (mCheckBox02.isChecked())
				{
					mEditText03.setFocusable(true);
					mEditText03.setFocusableInTouchMode(true);
					mEditText03.setEnabled(true);
				} else {
					mEditText03.setFocusable(false);
					mEditText03.setFocusableInTouchMode(false);
					mEditText03.setEnabled(false);
				}
	            break; 

		} 
		
	}
    
    @Override
    public boolean dispatchKeyEvent(final KeyEvent event)
    { 
        boolean status = false; 

        switch (event.getKeyCode()) { 
	        case KeyEvent.KEYCODE_BACK:
	        	if(event.getAction() == KeyEvent.ACTION_DOWN) {
	        		status = true;
	                setResult(Activity.RESULT_CANCELED, new Intent());
	                finish();            		
	        	}
	            break; 

	        // 他に引っかけたいキー 
        } 

        if (status) { 
            return true; 
        } 
        return super.dispatchKeyEvent(event); 
    } 

    /**
     * 接続されているデバイスのプリファレンス設定
     * @param isDefault  true:デフォルト
     */
    private void putPreferences(boolean isDefault) {
    	SharedPreferences sp = getSharedPreferences(mDefultName, MODE_PRIVATE);
    	SharedPreferences.Editor editor = sp.edit();
    	editor.putBoolean(PREF_SETTING, true);
    	if(isDefault) {
	    	editor.putString(PREF_BAUD, "115200");
	    	editor.putString(PREF_DATABIT, "8");
	    	editor.putString(PREF_PARITY, "None");
	    	editor.putString(PREF_STOPBIT, "1");
	    	editor.putString(PREF_PINCODE, "1234");
    	} else {
	    	editor.putString(PREF_BAUD, BAUD[mBaudIndex][0]);
	    	editor.putString(PREF_DATABIT, DATABIT[mDataIndex][0]);
	    	editor.putString(PREF_PARITY, PARITY[mParityIndex][0]);
	    	editor.putString(PREF_STOPBIT, STOPBIT[mStopIndex][0]);
	    	editor.putString(PREF_PINCODE, mPinCode);
    	}
    	editor.putString(PREF_BT_NAME, mBtName);
    	editor.commit();
    }
}
