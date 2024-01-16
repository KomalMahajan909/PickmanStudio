package com.itechvision.ecrobo.pickman.Chatman.PickWork.DolphinArrival;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.itechvision.ecrobo.pickman.Activity.ScannerBindActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.widget.ListViewAdapter;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.itechvision.ecrobo.pickman.Chatman.BaseActivity.FINISH;

public class CaseQuantityActivity extends ScannerBindActivity {
    static SlidingMenu menu;
    SlideMenu slidemenu;

    @BindView(R.id.layout_main)RelativeLayout layoutMain;
    @BindView(R.id.scrollMain) ScrollView scrollMain;
    @BindView(R.id.case_quantity) TextView case_quantity;
    @BindView(R.id.plate_clear) TextView plate_clear;
    @BindView(R.id.arrival_date) TextView arrival_date;
    @BindView(R.id.productcode) TextView productcode;
    @BindView(R.id.productname) TextView productname;
    @BindView(R.id.case_val)  EditText case_val;
    @BindView(R.id.img_back) ImageView img_back;
    @BindView(R.id.txt_total_qty)TextView totalQty;
    @BindView(R.id.txt_total_plate)TextView total_plate;
    @BindView(R.id.txt_total_no)TextView txt_total_no;
    @BindView(R.id.quantity) TextView quantity;
    @BindView(R.id.total) EditText total;
    @BindView(R.id.plateno) EditText plateno;
    @BindView(R.id.lotno) TextView lotno;
    @BindView(R.id.list)  ListView list;

    int totalqty =0;
    private boolean showKeyboard;
    private boolean visible = false;
    private TextToSpeak mTextToSpeak;
    public static ArrayList<Map<String,String>> datta= new ArrayList<>();
    protected int mProcNo = 0;
    public static final int PROC_CASE = 1;
    public static final int PROC_PLATE = 2;
    public static final int PROC_TOTAL = 3;
    String caseQty = "",attribute_type= "", date ="", code ="", name= "",barcode = "",et_date="",
            lot = "",nyukaId= "", productId="",comment = "",ID = "", quant = "";
    String TAG = CaseQuantityActivity.class.getSimpleName();
    ListViewItems data ;
    InputMethodManager imm;
    int selectedPOS = 0;
    boolean listSelect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_case_quantity);
        ButterKnife.bind(this);

        if(!datta.isEmpty()) {
            datta.clear();
        }

        Log.d(TAG,"On Create ");
        mTextToSpeak = new TextToSpeak(this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(case_val, InputMethodManager.SHOW_IMPLICIT);

        Intent intent = getIntent();
        if(intent.hasExtra("caseQty")){
            caseQty = intent.getStringExtra("caseQty");
            attribute_type = intent.getStringExtra("attribute_type");
            date= intent.getStringExtra("arrival_date");
            code= intent.getStringExtra("code");
            name = intent.getStringExtra("product_name");
            lot = intent.getStringExtra("lot_no");
            nyukaId= intent.getStringExtra("nyuka_id");
            productId = intent.getStringExtra("product_id");
            comment = intent.getStringExtra("comment");
            ID = intent.getStringExtra("shopID");
            quant = intent.getStringExtra("quantity");
            barcode = intent.getStringExtra("barcode");
            et_date = intent.getStringExtra("date");

        }

        case_quantity.setText(caseQty);
        arrival_date.setText(date);
        productcode.setText(code);
        productname.setText(name);
        productname.setSelected(true);
        lotno.setText(lot);
        edit();
        editplate();
        edittotal();
        setProc(PROC_CASE);

        showKeyboard = BaseActivity.getaddKeyboard();
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        plate_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (case_val.getText().toString().length()==0){}else{
                    plateno.setText("");

                    setProc(PROC_PLATE);
                }
            }
        });
    }


    @OnClick(R.id.case_clear)void caseClear(){
        case_val.setText("");
        setProc(PROC_CASE);
    }

    @OnClick(R.id.total_clear)void totalClear(){
        if (plateno.getText().toString().length()==0){

        }else{
            total.setText("");
            setProc(PROC_TOTAL);
        }
    }



    @OnClick(R.id.checkbtn)void check(){
        if(datta.size()==0){

        }else {

            Intent c_qty = new Intent(CaseQuantityActivity.this, CaseCommentActivity.class);
            c_qty.putExtra("caseQty", caseQty);
            c_qty.putExtra("attribute_type", attribute_type);
            c_qty.putExtra("arrival_date", date);
            c_qty.putExtra("code", code);
            c_qty.putExtra("product_name", name);
            c_qty.putExtra("lot_no", lot);
            c_qty.putExtra("nyuka_id", nyukaId);
            c_qty.putExtra("product_id", productId);
            c_qty.putExtra("comment", comment);
            c_qty.putExtra("shopID", ID);
            c_qty.putExtra("quantity", quant);
            c_qty.putExtra("barcode", barcode);
            c_qty.putExtra("date", et_date);

            startActivityForResult(c_qty, 1);

        }
    }

    @OnClick(R.id.img_back)void back(){
        if(!datta.isEmpty())
            datta.clear();

        Intent i = new Intent();
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    public int convert(int x) {
        Resources r = this.getResources();
        int px = (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP,x, r.getDisplayMetrics() );
        return px;
    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        Log.e(TAG, "Proc Value : " + mProcNo);
        switch (procNo) {

            case PROC_CASE:
                _gt(R.id.case_val).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.case_val).setFocusableInTouchMode(true);
                _gt(R.id.plateno).setFocusableInTouchMode(false);
                _gt(R.id.total).setFocusableInTouchMode(false);
                _gt(R.id.case_val).requestFocus();
                plateno.setEnabled(false);
                total.setEnabled(false);
                case_val.setEnabled(true);
                _gt(R.id.plateno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.total).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));

                break;

            case PROC_PLATE:
                _sts(R.id.plateno,"");
                _gt(R.id.case_val).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.plateno).setFocusableInTouchMode(true);
                _gt(R.id.case_val).setFocusableInTouchMode(false);
                _gt(R.id.total).setFocusableInTouchMode(false);
                _gt(R.id.plateno).requestFocus();
                plateno.setEnabled(true);
                total.setEnabled(false);
                case_val.setEnabled(false);
                _gt(R.id.plateno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.total).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                imm.showSoftInput(plateno, InputMethodManager.SHOW_IMPLICIT);

                break;
            case PROC_TOTAL:

                _gt(R.id.case_val).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.plateno).setFocusableInTouchMode(false);
                _gt(R.id.case_val).setFocusableInTouchMode(false);
                plateno.setEnabled(false);
                total.setEnabled(true);
                case_val.setEnabled(false);
                _gt(R.id.total).requestFocus();
                _gt(R.id.total).setFocusableInTouchMode(true);
                _gt(R.id.plateno).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.total).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                imm.showSoftInput(total, InputMethodManager.SHOW_IMPLICIT);
                break;
        }
    }

    @Override
    public void inputedEvent(String buff) {
        Log.e(TAG,"inputedEvent");
        switch (mProcNo) {
            case PROC_CASE:
                String val = _gts(R.id.case_val);
                mTextToSpeak.startSpeaking(val);

                if (val.equals("")|| val.equals("0")) {
                    U.beepError(this, "ケース値が必要です");
                    _gt(R.id.case_val).setFocusableInTouchMode(true);
                    break;
                }
                if (!U.isNumber(val)) {
                    U.beepError(this, "数量は数値のみ入力可能です");
                    _gt(R.id.case_val).setFocusableInTouchMode(true);
                    break;
                }

                int qty = Integer.parseInt(val)*Integer.parseInt(caseQty);
                quantity.setText(qty+"");
                setProc(PROC_PLATE);
                break;

            case PROC_PLATE:
                //  String plate = _gts(R.id.plateno);
                String plate =plateno.getText().toString();
                Log.e("kjhb cjhcsjkhkj >>>> ",plate);
                if (plate.equals("")||plate.equals("0")) {
                    U.beepError(this, "ケース値が必要です");
                    _gt(R.id.plateno).setFocusableInTouchMode(true);
                    break;
                }
                if (!U.isNumber(plate)) {
                    U.beepError(this, "数量は数値のみ入力可能です");
                    _gt(R.id.plateno).setFocusableInTouchMode(true);
                    break;
                }

                mTextToSpeak.startSpeaking(plate);

                int totalqty = Integer.parseInt(quantity.getText().toString())*Integer.parseInt(plate);
                total.setText(totalqty+"");

                setProc(PROC_TOTAL);
                break;

            case PROC_TOTAL:
                String ttl =total.getText().toString();
                if (ttl.equals("")||ttl.equals("0")) {
                    U.beepError(this, "ケース値が必要です");
                    _gt(R.id.total).setFocusableInTouchMode(true);
                    break;
                }
                if (!U.isNumber(ttl)) {
                    U.beepError(this, "数量は数値のみ入力可能です");
                    _gt(R.id.total).setFocusableInTouchMode(true);
                    break;
                }
                mTextToSpeak.startSpeaking(ttl);

                Map<String,String> map1 = new HashMap<>();
                map1.put("case",case_val.getText().toString());
                //   map.put("case",_gts(R.id.case_val));
                map1.put("case_qty",caseQty);
                map1.put("totalQty",total.getText().toString());
                map1.put("qty",quantity.getText().toString());
                map1.put("platee",plateno.getText().toString());

                if(listSelect){
                    datta.remove(selectedPOS);
                    datta.add(selectedPOS,map1);
                }
                else
                    datta.add(map1);

                boolean resp1 = setList();
                if(!resp1){
                    setProc(PROC_TOTAL);
                    _gt(R.id.total).setFocusableInTouchMode(true);
                } else {
                    _sts(R.id.case_val, "");
                    _sts(R.id.plateno, "");
                    quantity.setText("");
                    total.setText("");
                    setProc(PROC_CASE);
                    listSelect= false;
                    Log.e(TAG, "vbnmcvbnvbn   "+selectedPOS);
                    selectedPOS = 0;
                }
                break;
        }
    }

    public void Next_button(View view){
        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());
        inputedEvent(s);
        Log.e(TAG,"next   "+s);
    }

    boolean setList(){
        ListViewItems data = new ListViewItems();
        String qty= "0", plateqty = "0",totalqty = "0";
        for (int i = 0; i < datta.size(); i++) {
            Map<String, String>map = new HashMap<>();
            map= datta.get(i);

            data.add(data.newItem()
                    .add(R.id.txt_1, map.get("case_qty"))
                    .add(R.id.txt_2,  map.get("case"))
                    .add(R.id.txt_3,  map.get("qty"))
                    .add(R.id.txt_4,  map.get("platee"))
                    .add(R.id.txt_5,map.get("totalQty")));

            qty = U.plusTo(qty,map.get("qty"));
            plateqty = U.plusTo(plateqty,map.get("platee"));
            totalqty = U.plusTo(totalqty, map.get("totalQty"));

        }

        if(U.compareNumeric(qty,quant) == -1) {
            U.beepError(this,"数量は合計数量を超えることはできません");
            if(!listSelect) {
                Log.e(TAG, ">>>>>>>>>>>>>>  "+listSelect);
                datta.remove(datta.size() - 1);
            }
            /*else {
            Log.e(TAG, ">>>>>>>>>>>>>>>>>>>  "+listSelect+selectedPOS);
            datta.remove(selectedPOS);
        }*/
            return false;

        } else if(U.compareNumeric(totalqty,quant) == -1){
            U.beepError(this,"数量は合計数量を超えることはできません");
            if(!listSelect) {
                Log.e(TAG, ">>>>>>>>>>>>>>>  "+listSelect);
                datta.remove(datta.size() - 1);
            }
            /*else {
                Log.e(TAG, ">>>>>>>>>>>>>>>>>>>  " + listSelect + selectedPOS);
                datta.remove(selectedPOS);
            }*/
            return false;
        } else {

            ListViewAdapter adapter = new ListViewAdapter(getApplicationContext(), data, R.layout.case_qty_list_row);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Map<String, String>map = new HashMap<>();
                    map= datta.get(position);

                    case_val.setText(map.get("case"));
                    quantity.setText(map.get("qty"));
                    total.setText(map.get("totalQty"));
                    plateno.setText(map.get("platee"));

                    listSelect = true;
                    selectedPOS = position;

//                    datta.remove(position);
                }
            });

            total_plate.setText(plateqty);
            totalQty.setText(qty);
            txt_total_no.setText(totalqty);
        }
        return true;
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2

        if (requestCode == 1) {
            if(data.hasExtra(FINISH)) {

                _sts(R.id.case_val, "");
                _sts(R.id.plateno, "");
                quantity.setText("");
                setProc(PROC_CASE);
            }
            else if(data.hasExtra("BACK")){
                mTextToSpeak.onStopSpeaking();
                if(!datta.isEmpty()){
                    datta.clear();
                }
                Intent i = new Intent();
                i.putExtra(FINISH,"finish");
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        }
    }
   /* @OnClick(R.id.next_btn) void nextbtn(){
        String s = mBuff.toString();
        mBuff.delete(0, mBuff.length());
        inputedEvent(s);
    }*/

    @Override
    public void clearEvent() {

    }

    @Override
    public void allclearEvent() {
        Toast.makeText(this,"Action not defined",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void skipEvent() {
        Toast.makeText(this,"Action not defined",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_CASE:    // バーコード
                _sts(R.id.case_val, buff);
                break;
            case PROC_PLATE:    // バーコード
                _sts(R.id.plateno, buff);
                break;
            case PROC_TOTAL:    // バーコード
                _sts(R.id.total, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if(mProcNo == PROC_CASE)
        {
            _sts(R.id.case_val, barcode);
        }
        else if(mProcNo==PROC_PLATE)
            _sts(R.id.plateno, barcode);
        else if(mProcNo==PROC_TOTAL)
            _sts(R.id.total, barcode);

        inputedEvent(barcode);
    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_CASE:
                _sts(R.id.case_val, barcode);
                break;
            case PROC_PLATE:
                _sts(R.id.plateno, barcode);
                break;
        }
    }

    @Override
    public void onBackPressed() {
//     super.onBackPressed();
    }

    public void edit(){
        try {
            case_val.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {

                        if (case_val.getText().toString().equalsIgnoreCase("")||case_val.getText().toString().equals("0")) {
                            U.beepError(CaseQuantityActivity.this, "ケース値が必要です");
                            _gt(R.id.case_val).setFocusableInTouchMode(true);
                        }else {

                            totalqty = Integer.parseInt(case_val.getText().toString()) * Integer.parseInt(caseQty);
                            quantity.setText("" + totalqty);
                            setProc(PROC_PLATE);
                            // hideKeyboard(CaseQuantityActivity.this);

                            return true;
                        }
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editplate(){

        try {
            plateno.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {

                        inputedEvent("");
//                        if (plateno.getText().toString().equalsIgnoreCase("")) {
//                            U.beepError(CaseQuantityActivity.this, "ケース値が必要です");
//                            _gt(R.id.plateno).setFocusableInTouchMode(true);
//                        }else {
//
//                            mTextToSpeak.startSpeaking(plateno.getText().toString());
//
//                            int totalqty = Integer.parseInt(quantity.getText().toString())*Integer.parseInt(plateno.getText().toString());
//                            total.setText(totalqty+"");
//
//                            setProc(PROC_TOTAL);
//
//                            hideKeyboard(CaseQuantityActivity.this);
//
//                        }
//                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edittotal(){

        try {
            total.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {

                        inputedEvent("");
//                        if (total.getText().toString().equalsIgnoreCase("")) {
//                            U.beepError(CaseQuantityActivity.this, "ケース値が必要です");
//                            _gt(R.id.total).setFocusableInTouchMode(true);
//                        }
//                        else if(!U.isNumber(total.getText().toString())){
//                            U.beepError(CaseQuantityActivity.this, "数量は数値のみ入力可能です");
//                            _gt(R.id.total).setFocusableInTouchMode(true);
//                        }
//                        else {
//
//                            datta.remove(datta.size()-1);
//                            String ttl =total.getText().toString();
//                            Log.e("kjhb cjhcsjkhkj >>>> ",ttl);
//
//                            mTextToSpeak.startSpeaking(ttl);
//
//                            Map<String,String> map1 = new HashMap<>();
//                            map1.put("case",case_val.getText().toString());
//                      //    map.put("case",_gts(R.id.case_val));
//                            map1.put("case_qty",caseQty);
//                            map1.put("totalQty",total.getText().toString());
//                            map1.put("qty",quantity.getText().toString());
//                            map1.put("platee",plateno.getText().toString());
//
//                            datta.add(map1);
//
//                            boolean resp1 = setList();
//                            if(!resp1){
//                                setProc(PROC_TOTAL);
//                                _gt(R.id.total).setFocusableInTouchMode(true);
//                            }
//                            else {
//                                _sts(R.id.case_val, "");
//                                _sts(R.id.plateno, "");
//                                quantity.setText("");
//                                total.setText("");
//                                setProc(PROC_CASE);
//                            }
//
//                            return true;
//                        }
                    }

                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}