package com.itechvision.ecrobo.pickman.Chatman.WWW6Server.DimaruShipping;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Chatman.Stock.SlipPrinterActivity;
 import com.itechvision.ecrobo.pickman.Models.DaimaruShippingSpecification.DaimaruCheckSpecification;
import com.itechvision.ecrobo.pickman.Models.DaimaruShippingSpecification.DaimaruCheckSpecificationnew;
import com.itechvision.ecrobo.pickman.Models.DaimaruShippingSpecification.DaimaruGetSpecification;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.SharedPrefrences;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DaimaruShippingSpecificationActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.boxsize) Spinner spinner;
    @BindView(R.id.submit) Button submit;
    @BindView(R.id.use) RadioGroup radiogUse;
    @BindView(R.id.btn_null) Button nullbtn;
    @BindView(R.id.layout_number) ViewGroup layout;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.boxlayout2) LinearLayout layout2;
    @BindView(R.id.boxlayout3) LinearLayout layout3;
    @BindView(R.id.trackfixedCheck) CheckBox trackCheck;
    @BindView(R.id.trackDelete)Button trackBtn;
    @BindView(R.id.trackfixLayout) LinearLayout trackLayout;
    @BindView(R.id.trackdigitLayout) LinearLayout trackEditLayout;
    @BindView(R.id.edtboxsize) EditText edtboxsize;

    public EditText boxsize;
    protected int mProcNo = 0;
    public TextToSpeak mTextToSpeak;
    public static final int PROC_TRACK_FIX = 1;
    public static final int PROC_BOXNO = 2;
    public static final int PROC_TRACKID = 3;
    public static final int PROC_QNTY =4;
    private boolean showKeyboard;
    public boolean setback = false;
    String selectedboxsize,BATCHID="";
    public boolean trackreq=false;
    public boolean btnpress= false;
    public String size="";
    private boolean visible = false;
    public Context mcontext=this;
    boolean inspectcomplete = false ;
    // if radio button box is selected
    boolean boxslect = true ,  trackbtnpress = false;
    protected List<Map<String, String>> mOrderList = null;
    public List<String> boxlist = new ArrayList<>();
    public List<String> tracklist = new ArrayList<>();
    public List<String> scanedtracklist = new ArrayList<>();
    public Map<String, String> mTarget = null;
    SharedPreferences spDomain;
    SharedPreferences sharedPreferences;
    public static final String DOMAINPREFERENCE = "domain";
    public static final String MYPREFERENCE = "MyPrefs";
    ECRApplication app=new ECRApplication();
    String adminID="";
    public static final int COMPLETE_INSPECT = 1;
    public static final int INCOMPLETE_INSPECT = 2;
    public String _lastUpdateQty = "0";
    public static int mRequestStatus =0;
    public static final int REQ_INITIAL = 1;
    public static final int REQ_TRACKING = 2;
    public static final int REQ_QTY = 3;
    String orderId = "", action = "",company ="",SlipPrinterScreen="";
    String TAG= DaimaruShippingSpecificationActivity.class.getSimpleName();
    ArrayList<String> sizes= new ArrayList<>();
    String arrsize[] = {"デフォルトサイズを選択","60","80","100","120","140","160","2","2.5","3","1","null"};
    public  static String BatchStatus ="";
   // microsoft

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_specification);

        ButterKnife.bind(DaimaruShippingSpecificationActivity.this);

        getIDs();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        Log.d(TAG,"On Create ");
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id",null);

        boxsize=(EditText)_gt(R.id.edtboxsize);

        Log.e(TAG,"chaeckValue  "+showKeyboard);
        showKeyboard = BaseActivity.getaddKeyboard();

        mTextToSpeak = new TextToSpeak(this);
        if(showKeyboard==true)
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
            visible = true;

            numbrbtn.setText("キーボードを隠す");
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e(TAG,"SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }

        Intent intent = getIntent();
        if(intent.hasExtra("action")){
            action = intent.getStringExtra("action");
            orderId = intent.getStringExtra("order_id");
            company = intent.getStringExtra("company");
            BATCHID = intent.getStringExtra("batch_id");
            SlipPrinterScreen  = intent.getStringExtra("slip_printer");
            Log.e(TAG,TAG+company+"!1111111");
        }

        if(boxslect){
            if(!sizes.isEmpty())
                sizes.clear();
            for (int i=0;i<arrsize.length;i++)
                sizes.add(arrsize[i]);

            nullbtn.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
        }

        if (mProcNo == 0) nextProcess();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mBuff.toString();
                mBuff.delete(0, mBuff.length());
                inputedEvent(s);

            }
        });

        String trackfix =SharedPrefrences.get_trackdigit(this);
        if(trackfix.equals("0") ||trackfix.equals("") ){
            trackCheck.setChecked(false);
            SharedPrefrences.set_trackCheck(DaimaruShippingSpecificationActivity.this,"0");

        } else {
            SharedPrefrences.set_trackCheck(DaimaruShippingSpecificationActivity.this,"1");
            trackCheck.setChecked(true);
            _sts(R.id.trackFixDigits, trackfix);
            trackBtn.setText("SAVE");
            TrackDelete();
        }

        trackCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (trackCheck.isChecked()) {
                    SharedPrefrences.set_trackCheck(DaimaruShippingSpecificationActivity.this,"1");
                    setProc(PROC_TRACK_FIX);
                } else {
                    if(trackbtnpress){
                        trackCheck.setChecked(true);
                        SharedPrefrences.set_trackCheck(DaimaruShippingSpecificationActivity.this,"1");
                        U.beepError(DaimaruShippingSpecificationActivity.this, null);
                        CommonDialogs.customToast(DaimaruShippingSpecificationActivity.this, "Please delete the location digits");
                    } else {
                        SharedPrefrences.set_trackCheck(DaimaruShippingSpecificationActivity.this,"0");
                        setProc(PROC_BOXNO);
                    }
                }
            }
        });
    }
    @OnClick (R.id.box_clear) void boxClear () {
        if(mProcNo <= 3) {
            setProc(PROC_BOXNO);
            boxsize.setText("");
        }
    }

    public void UseBOX (View view){
        switch (radiogUse.getCheckedRadioButtonId()) {
            case R.id.usebox:
                boxslect = true;
                if(!sizes.isEmpty())
                    sizes.clear();
                for (int i=0;i<arrsize.length;i++)
                    sizes.add(arrsize[i]);
                Log.e(TAG,"array    1"+sizes.get(0)+"   2"+sizes);
                setSpinner();

                nullbtn.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.VISIBLE);
                layout3.setVisibility(View.VISIBLE);
                break;

            case R.id.usepoly:
                boxslect = false;
                if(!sizes.isEmpty())
                    sizes.clear();
                for (int i=0;i<4;i++)
                    sizes.add(arrsize[i]);

                Log.e(TAG,"array  1"+sizes.get(0)+"   2"+sizes);
                setSpinner();

                nullbtn.setVisibility(View.GONE);
                layout2.setVisibility(View.GONE);
                layout3.setVisibility(View.GONE);
                break;
        }
    }

    private void setSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,sizes){
            // Disable click item < month current
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        Log.e("ShippingSpecification","Size  "+BaseActivity.getsizepos());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(BaseActivity.getsizepos());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0) {
                    Log.e("ShippingSpecification","Sizeee1111  "+position);
                    setBoxSize(sizes.get(position));
                    BaseActivity.setsizepos(position);
                    btnpress=false;
                    size="";
                    boxsize.setText(getBoxSize());
                    if(mProcNo==PROC_BOXNO)
                        inputedEvent(size);

                }
                if (position==0)
                    boxsize.setText("");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                U.beepError(DaimaruShippingSpecificationActivity.this,"サイズを選択");
                setBoxSize(sizes.get(0));
            }
        });


    }
    public void SizeSelect(View view) {
        switch(view.getId()){
            case R.id.btn_sixty:
                btnpress=true;
                size="60";
                boxsize.setText(size);
                if(mProcNo==PROC_BOXNO)
                    inputedEvent(size);
                break;
            case R.id.btn_eighty:
                btnpress=true;
                size="80";
                boxsize.setText(size);
                if(mProcNo==PROC_BOXNO)
                    inputedEvent(size);
                break;
            case R.id.btn_hundred:
                btnpress=true;
                size="100";
                boxsize.setText(size);
                if(mProcNo==PROC_BOXNO)
                    inputedEvent(size);
                break;
            case R.id.btn_onetwenty:
                btnpress=true;
                size="120";
                boxsize.setText(size);
                if(mProcNo==PROC_BOXNO)
                    inputedEvent(size);
                break;
            case R.id.btn_onefourty:
                btnpress=true;
                size="140";
                boxsize.setText(size);
                if(mProcNo==PROC_BOXNO)
                    inputedEvent(size);
                break;
            case R.id.btn_onesixty:
                btnpress=true;
                size="160";
                boxsize.setText(size);
                if(mProcNo==PROC_BOXNO)
                    inputedEvent(size);
                break;
            case R.id.btn_two:
                btnpress=true;
                size="2";
                boxsize.setText(size);
                if(mProcNo==PROC_BOXNO)
                    inputedEvent(size);
                break;
            case R.id.btn_twopfive:
                btnpress=true;
                size="2.5";
                boxsize.setText(size);
                if(mProcNo==PROC_BOXNO)
                    inputedEvent(size);
                break;
            case R.id.btn_three:
                btnpress=true;
                size="3";
                boxsize.setText(size);
                if(mProcNo==PROC_BOXNO)
                    inputedEvent(size);
                break;
            case R.id.btn_null:
                btnpress=true;
                size="null";
                boxsize.setText(size);
                if(mProcNo==PROC_BOXNO)
                    inputedEvent(size);
                break;
            case R.id.btn_one:
                btnpress=true;
                size="1";
                boxsize.setText(size);
                if(mProcNo==PROC_BOXNO)
                    inputedEvent(size);
                break;
        /*    default:
                btnpress=false;
                size="";
                boxsize.setText(getBoxSize());
                setProc(PROC_TRACKID);*/
        }
    }

    public void AddLayout(View view) {
        ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
        if(visible==false) {
            visible = true;

            numbrbtn.setText("キーボードを隠す");
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e(TAG,"SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);

        } else {
            visible = false;

            numbrbtn.setText("キーボードを表示");
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e(TAG,"SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }
    }

    public int convert(int x) {
        Resources r =mcontext.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                x, r.getDisplayMetrics()
        );
        return px;
    }

    public void setBoxSize(String size) {
        selectedboxsize = size;
    }

    public String getBoxSize() {
        return selectedboxsize;
    }

    //set title and icons on actionbar
    private void getIDs() {

        actionbarImplement(this, "出荷工程", " ", 0, true,false,false );
      //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);
        relLayout1.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;
            case R.id.notif_count_blue:
                ;
                break;

            default:
                break;
        }
    }

    @OnClick(R.id.trackDelete) void TrackDelete() {
        String track = _gts(R.id.trackFixDigits);
        if (track.equals("") || track.equals("0")) {
            U.beepError(this, "ロット番号が必要");
        }
        else if(!trackCheck.isChecked()){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if(trackBtn.getText().toString().equals("DELETE")){
                trackbtnpress = false;
                trackBtn.setText("SAVE");
                trackBtn.setBackground(getResources().getDrawable(R.drawable.green_rounded_corner_btn));
//                editor.putString("LocationDigits","0");
                _sts(R.id.trackFixDigits,"");
                editor.commit();
            }
            else {
                U.beepError(this, "ロット番号が必要");
            }
        } else {

            SharedPreferences.Editor editor = sharedPreferences.edit();

            if(trackBtn.getText().toString().equals("SAVE")){
                trackbtnpress = true;
                trackBtn.setText("DELETE");
                trackBtn.setBackground(getResources().getDrawable(R.drawable.red_rounded_corner_button));
//                editor.putString("trackDigits",track);
                SharedPrefrences.set_trackdigit(this,track);
                setProc(PROC_BOXNO);

            }
            else if(trackBtn.getText().toString().equals("DELETE")){
                trackbtnpress = false;
                trackBtn.setText("SAVE");
                trackBtn.setBackground(getResources().getDrawable(R.drawable.green_rounded_corner_btn));
                editor.putString("LocationDigits","0");
                _sts(R.id.trackFixDigits,"");
            }
            editor.commit();
        }
    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        switch (procNo) {
            case PROC_TRACK_FIX:
                Log.e(TAG, "SETPROCCCCC  PROC_TRACK_FIX ");
                _gt(R.id.trackFixDigits).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.trackingNumber).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.edtboxsize).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.trackingNumber).setFocusableInTouchMode(true);
                break;
            case PROC_TRACKID:
                _gt(R.id.trackFixDigits).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.trackingNumber).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(R.id.edtboxsize).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.trackingNumber).setFocusableInTouchMode(true);

                break;
            case PROC_BOXNO:
                _gt(R.id.trackFixDigits).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.trackingNumber).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.edtboxsize).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(R.id.edtboxsize).setFocusableInTouchMode(true);
                break;
            case PROC_QNTY:
                _gt(R.id.trackFixDigits).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.trackingNumber).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.edtboxsize).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                if (_gts(R.id.quantity).equals("")) _sts(R.id.quantity, "1");
                _gt(R.id.quantity).setFocusableInTouchMode(true);

                break;
        }
    }
    public void inputedEvent(String buff,boolean isScaned) {

        switch (mProcNo) {
            case PROC_TRACKID:	// バーコード
                String track = _gts(R.id.trackingNumber);
                int tracklength= 0;
                String trackfix ="";
                if (trackbtnpress) {
                    trackfix = _gts(R.id.trackFixDigits);
                    tracklength = Integer.parseInt(trackfix);
                }

                if ("".equals(track)) {
                    U.beepError(this, "数量は必須です");
                    _gt(R.id.trackingNumber).setFocusableInTouchMode(true);
                    break;
                }
                if(_gts(R.id.edtboxsize).equals("")){
                    U.beepError(this, "please select any Boxsize");
                    break;
                }
                else if(trackbtnpress && track.length()!= tracklength){
                    U.beepError(this, "問番の桁数が設定した値と違います");
                    _gt(R.id.trackingNumber).setFocusableInTouchMode(true);
                    break;
                }

                Globals.getterList = new ArrayList<>();

                Log.e(TAG,"shopidddddd  "+BaseActivity.getShopId());

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("mediacode",_gts(R.id.trackingNumber)));
                Globals.getterList.add(new ParamsGetter("mediacode_check","true"));
                Globals.getterList.add(new ParamsGetter("is_pickman","true"));

              //tag box_screen to check mediacode for 1 week without this tag it checks for 2 days
                Globals.getterList.add(new ParamsGetter("box_screen","1"));

                Log.e(TAG,"shopid  "+BaseActivity.getShopId()+"/ " +action +"/ "+company);

           /*   if( !action.equals("") && company.equals("0"))*/
                Globals.getterList.add(new ParamsGetter("order_id",orderId));
                Globals.getterList.add(new ParamsGetter("shipping_company",company));
                if (SlipPrinterScreen.equalsIgnoreCase("SlipPrinter")){
                    Globals.getterList.add(new ParamsGetter("batch_id",BATCHID));
                }else{

                }

                mRequestStatus = REQ_TRACKING;

                new MainAsyncTask(DaimaruShippingSpecificationActivity.this, Globals.Webservice.shippingStatus, 1, DaimaruShippingSpecificationActivity.this, "Form", Globals.getterList,true).execute();
                break;

            case PROC_BOXNO:
                if (edtboxsize.getText().toString().length()>3){
                    U.beepError(this, "桁数は3桁までです。");
                    _gt(R.id.edtboxsize).setFocusableInTouchMode(true);
                    break;
                }else

                if(BaseActivity.getsizepos()==0 && btnpress==false && this._gts(R.id.edtboxsize).equals(""))
                {
                    U.beepError(this, "サイズを選択");
                    _gt(R.id.edtboxsize).setFocusableInTouchMode(true);
                    break;
                }
                if(setback== false)
                    setProc(PROC_TRACKID);
                else if(inspectcomplete == true) {
                  /*  if(!mTarget.get("shipping_company").equals("0"))
                        Addtomap();*/

                    inspectcomplete = false;
                    boxlist.add(_gts(R.id.edtboxsize));
                    fixedRequest();
                }
                else{
                    /*if(!mTarget.get("shipping_company").equals("0"))
                        Addtomap();*/

                    setProc(PROC_QNTY);
                    setback= false;
                }
                break;

            case PROC_QNTY:
                String track1 = mTarget.get("mediacode");
                String qty = _gts(R.id.quantity);

                if(_gts(R.id.edtboxsize).equals("")){
                    U.beepError(this, "please select any Boxsize");
                    break;
                }

                if (isScaned) {

                    if(!mTarget.get("shipping_company").equals("0")){

                        boolean match = false;
                        for(String t :tracklist){
                            // if tracking no. matches the tracklist
                            if(t.equals(buff)){

                                Log.e(TAG,"List of box size becomes  >>>>>  "+ boxlist);

                                setback= true;
                                match = true;
                                _sts(R.id.trackingNumber,buff);
                                Log.e(TAG,"List of qty becomes  >>>>>  "+ qty);
                                qty = U.plusTo(qty, "1");
                                String processedCnt = mTarget.get("processedCnt");
                                if (!processedCnt.equals(_gts(R.id.quantity)))
                                    processedCnt = _gts(R.id.quantity);
                                mTarget.put("processedCnt", U.plusTo(processedCnt, "1"));
                                Log.e(TAG,"List of qty becomes  >>>>>  "+ mTarget.get("processedCnt"));

                                boxlist.add(_gts(R.id.edtboxsize));

                                if(btnpress){
                                    btnpress = false;
                                    String b = getBoxSize();
                                    boxsize.setText(b);
                                }


                                if (Integer.parseInt(mTarget.get("processedCnt")) <= Integer.parseInt(mTarget.get("quantity"))) {
                                    Log.e(TAG,"List of qty becomes  >>>>>111113333  "+ mTarget.get("quantity"));
                                    _sts(R.id.quantity, qty);
                                    if (Integer.parseInt(qty) > 1)
                                        mTextToSpeak.startSpeaking(qty);

                                    _lastUpdateQty = _gts(R.id.quantity);

                                    // remove matching tracking no. from the tracklist
                                    Addtomap();

                                    /* check if update in quantity need next action */
                                    if (mTarget.get("processedCnt").equals(mTarget.get("quantity"))) {
                                        fixedRequest();
                                    }
                                    else{
                                        // to move back to box no in multi tacking no. case
                                        if(getBoxSize().equals(""))
                                            setProc(PROC_BOXNO);

                                    }
                                }
                                break;
                            }
                        }
                        if(!match){
                            Log.e(TAG, "inputedEvent_QNTYY scanned Barcode match qnty INcomplete111111");
                            U.beepError(this, "問い合わせ番号が違います、また、未検品の問い合わせ番号があります。");
                            _gt(R.id.quantity).setFocusableInTouchMode(true);
                        }

                    } else {
                        if (buff.equals(track1)) {

                            boxlist.add(_gts(R.id.edtboxsize));
                            Log.e(TAG,"List of box size becomes  >>>>>  "+ boxlist);

                            setback= true;
                            if(btnpress){
                                btnpress = false;
                                boxsize.setText(getBoxSize());
                            }
                            qty = U.plusTo(qty, "1");
                            String processedCnt = mTarget.get("processedCnt");


                            if (!processedCnt.equals(_gts(R.id.quantity)))
                                processedCnt = _gts(R.id.quantity);
                            mTarget.put("processedCnt", U.plusTo(processedCnt, "1"));

                            //increase qunatity in mpackdata
                            if (Integer.parseInt(mTarget.get("processedCnt")) <= Integer.parseInt(mTarget.get("quantity"))) {

                                _sts(R.id.quantity, qty);
                                if (Integer.parseInt(qty) > 1)
                                    mTextToSpeak.startSpeaking(qty);

                                _lastUpdateQty = _gts(R.id.quantity);

                                Log.e(TAG,"processed  Qtyyyyyy   "+mTarget.get("processedCnt"));
                                /* check if update in quantity need next action */
                                if (mTarget.get("processedCnt").equals(mTarget.get("quantity"))) {
                                    fixedRequest();
                                }
                            }
                        } else {
                            U.beepError(this, "問い合わせ番号が違います、また、未検品の問い合わせ番号があります。");
                            _gt(R.id.quantity).setFocusableInTouchMode(true);

                        }}

                } else {
                    if ("".equals(qty)) {
                        Log.e(TAG, "inputedEvent_QNTYY empty ");
                        U.beepError(this, "数量は必須です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    } else if (!U.isNumber(qty)) {
                        U.beepError(this, "数量は数値のみ入力可能です");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    }



                    if (Integer.parseInt(qty) > 1)
                        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
                    mTarget.put("processedCnt" ,_gts(R.id.quantity));

                    if (mTarget.get("processedCnt").equals(mTarget.get("quantity"))) {
                        boxlist.add(_gts(R.id.edtboxsize));
                        if(btnpress){
                            btnpress = false;
                            boxsize.setText(getBoxSize());
                        }
                        if(!mTarget.get("shipping_company").equals("0"))
                            Addtomap();

                        fixedRequest();
                    } else {
                        Log.e(TAG, "inputedEvent_QNTYY INcomplete inspect");
                        U.beepError(this, "問い合わせ番号が違います、また、未検品の問い合わせ番号があります。");
                        _sts(R.id.quantity, "1");
                        _gt(R.id.quantity).setFocusableInTouchMode(true);
                        break;
                    }
                }
                break;
        }
    }

    void fixedRequest() {
        Log.e(TAG, "FixedRequesttt     "+ boxlist);

        stopTimer();

        Globals.getterList = new ArrayList<>();

        Globals.getterList.add(new ParamsGetter("admin_id",adminID));

        Globals.getterList.add(new ParamsGetter("mediacode",_gts(R.id.trackingNumber)));
        Globals.getterList.add(new ParamsGetter("time_taken", timeTaken().toString()));

        //tag box_screen to check mediacode for 1 week without this tag it checks for 2 days
        Globals.getterList.add(new ParamsGetter("box_screen","1"));

        if(!mTarget.get("shipping_company").equals("0")){
            StringBuffer track1 = new StringBuffer();

            for (String t1 : scanedtracklist) {
                track1.append("\t").append(t1);
            }
            Globals.getterList.add(new ParamsGetter("tracking_no",track1.substring(1).toString()));
        }
        Globals.getterList.add(new ParamsGetter("num",mTarget.get("processedCnt")));
        Globals.getterList.add(new ParamsGetter("is_pickman","true"));

        if(boxslect)
            Globals.getterList.add(new ParamsGetter("is_polythene","0"));
        else
            Globals.getterList.add(new ParamsGetter("is_polythene","1"));


        StringBuffer boxsize1 = new StringBuffer();

        for (String box : boxlist) {
            boxsize1.append("\t").append(box);
        }

        Globals.getterList.add(new ParamsGetter("boxsize",boxsize1.substring(1).toString()));
        Globals.getterList.add(new ParamsGetter("order_id",orderId));
        Globals.getterList.add(new ParamsGetter("shipping_company",company));
        if (SlipPrinterScreen.equalsIgnoreCase("SlipPrinter")){
            Globals.getterList.add(new ParamsGetter("batch_id",BATCHID));

        }else{

        }
        if(btnpress==true) {
            trackreq=true;
            mRequestStatus = REQ_QTY;
            new MainAsyncTask(DaimaruShippingSpecificationActivity.this, Globals.Webservice.shippingStatus, 1, DaimaruShippingSpecificationActivity.this, "Form", Globals.getterList,true).execute();

        }else {
            trackreq=true;
            mRequestStatus = REQ_QTY;
            new MainAsyncTask(DaimaruShippingSpecificationActivity.this, Globals.Webservice.shippingStatus, 1, DaimaruShippingSpecificationActivity.this, "Form", Globals.getterList,true).execute();
        }

    }

    public void nextProcess() {
        _sts(R.id.trackingNumber, "");
        setBadge3(0);
        setProc(PROC_BOXNO);
        _sts(R.id.quantity, "");
        _sts(R.id.edtboxsize, "");
        btnpress=false;
        boxlist.clear();
        setback =false;
        size="";
        setBoxSize("");


        if(!scanedtracklist.isEmpty())
            scanedtracklist.clear();
        if(showKeyboard==false) {
            visible = false;
            numbrbtn.setText("キーボードを表示");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);

            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
//			hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.GONE);
            Log.e(TAG, "SetlayoutMarginn");
            mainlayout.setLayoutParams(params);
        }

        setSpinner();
    }
    public void setTrack(ArrayList <String> list){
        tracklist = list;
    }

    public void nextWork() {
        Log.e(TAG, "nextworkkkkkk");
        String processedCnt = mTarget.get("processedCnt");
        mTarget.put("processedCnt", U.plusTo(processedCnt, "1"));

        tracklist.remove(_gts(R.id.trackingNumber));
        Log.e(TAG,"tracklist becomsss  "+ tracklist);

        scanedtracklist.add(_gts(R.id.trackingNumber));
        _sts(R.id.quantity, "1");

        mTextToSpeak.resetQueue();
        mTextToSpeak.startSpeaking(_gts(R.id.quantity));
        _lastUpdateQty = _gts(R.id.quantity);

        if(_gts(R.id.edtboxsize).equals("")){
            Log.e(TAG,"please select any Boxsize >>>>>");
            U.beepError(this, "please select any Boxsize");
        }
        else {
            boxlist.add(_gts(R.id.edtboxsize));
            if (btnpress) {
                btnpress = false;
                String b = getBoxSize();
                Log.e(TAG, "asdfghhh" + b);
                boxsize.setText(b);
            }

            if (mTarget.get("processedCnt").equals(mTarget.get("quantity"))) {
                Log.e(TAG, "Box size list becomes  "+boxlist);
                fixedRequest();
            }
            else {
                Log.e(TAG, "aaaaaaaaa");
                if (tracklist.size() > 0) {
                    if (getBoxSize().equals("")) {
                        setProc(PROC_BOXNO);
                        Log.e(TAG, "aaaaaaaa222   ");
                        setback = true;
                    } else{
                        Log.e(TAG, "aaaaaa44444");

                        setProc(PROC_QNTY);}
                } else
                    setProc(PROC_QNTY);
            }
        }
    }

    public void Addtomap()
    {
        tracklist.remove(_gts(R.id.trackingNumber));
        Log.e(TAG,"tracklist becomsss  "+ tracklist);

        scanedtracklist.add(_gts(R.id.trackingNumber));
        Log.e(TAG,"tracklist becomsss1111111  "+ scanedtracklist);

    }


    public void updateBadge3(String qtyCount)
    {
        Log.e(TAG,"updateBadge333333333  "+qtyCount);
        setBadge3(Integer.valueOf(qtyCount));
//      totalcount=Integer.parseInt(qtyCount);
    }
    @Override
    public void inputedEvent(String buff) {
        Log.e(TAG, "inputedEventtttttt");
        inputedEvent(buff, false);
    }

    @Override
    public void clearEvent() {
        mTextToSpeak.startSpeaking("clear");
//        U.beepBigsound(this, null);
        BaseActivity.setsizepos(0);
        nextProcess();
    }

    @Override
    public void allclearEvent() {
        CommonDialogs.customToast(this, "No action");
    }

    @Override
    public void skipEvent() {
        CommonDialogs.customToast(this, "No action");
    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_TRACKID:	// バーコード
                _sts(R.id.trackingNumber, buff);
                break;
            case PROC_BOXNO:	// バーコード
                _sts(R.id.edtboxsize, buff);
                break;
            case PROC_QNTY:	// バーコード
                _sts(R.id.quantity, buff);
                break;
            case PROC_TRACK_FIX:	// 移動先
                _sts(R.id.trackFixDigits, buff);
                break;
        }
    }

    @Override
    public void scanedEvent(String barcode) {
        if (!barcode.equals("")) {
            if (mProcNo == PROC_TRACKID) _sts(R.id.trackingNumber, barcode);}
        if (mProcNo == PROC_BOXNO){
            _sts(R.id.edtboxsize, barcode);
        }
        this.inputedEvent(barcode,true);
    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_TRACKID:	// バーコード
                _sts(R.id.trackingNumber, barcode);
                break;
            case PROC_BOXNO:	// バーコード
                _sts(R.id.edtboxsize, barcode);
                break;
            case PROC_QNTY:	// バーコード
                _sts(R.id.quantity, barcode);
                break;
            case PROC_TRACK_FIX:	// 移動先
                _sts(R.id.trackFixDigits, barcode);
                break;
        }
    }

    public void setProductList(Map<String, String> data) {
        Log.e("ShipSpecification", " setProductListt ");
        mTarget = data;
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    public void updateBadge1(String qtyCount) {
        setBadge1(Integer.valueOf(qtyCount));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mTextToSpeak.isSpeechReady){
            mTextToSpeak.onStopSpeaking();
            mTextToSpeak.resetQueue();
        }
    }

    @Override
    public void onPostSuccess(Object result, int flag, boolean isSucess) {
        HashMap<String, String> mHash=new HashMap<>();
        Log.e(TAG,result.toString());

        try {

            String response= result.toString();
            JsonPullParser parser = JsonPullParser.newParser(response);
            JsonHash map1 = JsonHash.fromParser(parser);
            Log.e(TAG," "+map1);

            String msg="";
            JsonArray result1;
            String code = map1.getStringOrNull("code");
            msg=map1.getStringOrNull("message");
            result1= map1.getJsonArrayOrNull("results");
            if (code == null) {
                Log.e(TAG,"CODEeee=====Null");
                CommonDialogs.customToast(getApplicationContext(),"Network error occured");
            }
            if ("0".equals(code) )
            {

                Log.e("SendLogs111",code+"  "+msg+"  "+result1);

                if(mRequestStatus == REQ_INITIAL)
                {
                    new DaimaruGetSpecification().post(code,msg,result1, mHash, DaimaruShippingSpecificationActivity.this);
                }
                else if(mRequestStatus == REQ_TRACKING)
                {
                    if (SlipPrinterScreen.equalsIgnoreCase("SlipPrinter")){
                        new DaimaruCheckSpecificationnew().post(code,msg,result1, mHash, DaimaruShippingSpecificationActivity.this);

                    }else{
                        new DaimaruCheckSpecification().post(code,msg,result1, mHash, DaimaruShippingSpecificationActivity.this);

                    }
                }
                else if(mRequestStatus == REQ_QTY) {
                    if (BaseActivity.getBoxSelected() == true ){
                        if(action.equals("shipping")){
                            Intent intent=new Intent(this, Daimaru_Shipping.class);
                            startActivity(intent);
                            finish();//finishing activity
                        }

                        else if (action.equals("slip_printer")){

                            if (SlipPrinterScreen.equalsIgnoreCase("SlipPrinter")){
                                if (BatchStatus.equalsIgnoreCase("2")){
                                    Intent intent=new Intent(this,SlipPrinterActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Intent intent=new Intent(this,SlipPrinterActivity.class);
                                    intent.putExtra("action","back");
                                    startActivity(intent);
                                    finish();
                                }
                            }else{
                                Intent intent=new Intent(this,SlipPrinterActivity.class);
                                intent.putExtra("action","back");
                                startActivity(intent);
                                finish();
                            }




                        }
                        else  new DaimaruGetSpecification().post(code,msg,result1, mHash, DaimaruShippingSpecificationActivity.this);

                    } else
                        new DaimaruGetSpecification().post(code,msg,result1, mHash, DaimaruShippingSpecificationActivity.this);
                }
            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(DaimaruShippingSpecificationActivity.this,LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            } else{
                if(mRequestStatus == REQ_INITIAL) {
                    new DaimaruGetSpecification().valid(code,msg,result1, mHash, DaimaruShippingSpecificationActivity.this);
                }
                else if(mRequestStatus == REQ_TRACKING)
                {
                    new DaimaruCheckSpecificationnew().valid(code,msg,result1, mHash, DaimaruShippingSpecificationActivity.this);
                }
                else if(mRequestStatus == REQ_QTY){
                    new DaimaruGetSpecification().valid(code,msg,result1, mHash, DaimaruShippingSpecificationActivity.this);
                }
            }
        }
        catch (Exception e) {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTextToSpeak.isSpeechReady){
            mTextToSpeak.onStopSpeaking();
            mTextToSpeak.resetQueue();}

    }


}
