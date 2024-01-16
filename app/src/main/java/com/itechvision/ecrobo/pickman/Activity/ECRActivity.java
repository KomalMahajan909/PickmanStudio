package com.itechvision.ecrobo.pickman.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ECRActivity  extends Activity {

	protected ProgressDialog mProgress;

	public ECRActivity() {
		super();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//  Thread.setDefaultUncaughtExceptionHandler(new ECRUncaughtExceptionHandler(getApplicationContext()));
 	}

	@Override
	protected void onStart() {
		super.onStart();
		//  ECRUncaughtExceptionHandler.SendBugReport(this);
	}
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("ECR", "Activity '" + this.toString() + "' is Destroied.");
	}

	public View _g(int id) {
		return this.findViewById(id);
	}

	public EditText _gt(int id) {
		return (EditText) this.findViewById(id);
	}

	public TextView _gtxtv(int id) {
		return (TextView) this.findViewById(id);
	}

	public ECRActivity _stxtv(int id, String v) {
		this._gtxtv(id).setText(v);
		return this;
	}

	public String _gts(int id) {
		return this._gt(id).getText().toString();
	}

	/***
	 * RadioListViewの値を取得
	 * @param id RadioListViewのリソースID
	 * @return ラジオの値
	 */
//    public RadioListView _gtv(int id) {
//        return (RadioListView) this._g(id);
//    }

	/***
	 * ListViewの値を取得
	 * @param id ListViewのリソースID
	 * @return ラジオの値
	 */
	public ListView _glv(int id) {
		return (ListView) this._g(id);
	}

	public Spinner _gsv(int id) {
		return (Spinner) this._g(id);
	}

//    public RadioListView _grlv(int id) {
//        RadioListView rlv = (RadioListView) this._g(id);
//        return rlv;
//    }

	/***
	 * EditTextの値を設定します。
	 * @param id RadioListViewのリソースID
	 * @param v 設定する文字列
	 * @return ECRActivity
	 */
	public ECRActivity _sts(int id, String v) {
		this._gt(id).setText(v);
		return this;
	}

	public void dialog(String title, String message) {
		ProgressDialog mDialog = new ProgressDialog(this);
		mDialog.setTitle(title);
		mDialog.setMessage(message);
		mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mDialog.show();
	}

	public void toast(String message, int length) {
		Toast.makeText(this, message, length).show();
	}

	public void toast(String message) {
		toast(message, Toast.LENGTH_LONG);
	}

}

