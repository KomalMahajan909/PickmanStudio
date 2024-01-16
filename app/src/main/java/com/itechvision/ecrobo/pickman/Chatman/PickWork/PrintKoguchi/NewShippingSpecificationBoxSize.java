package com.itechvision.ecrobo.pickman.Chatman.PickWork.PrintKoguchi;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.NewKoguchiBoxSize.OrderIdScanKoguchiReq;
import com.itechvision.ecrobo.pickman.Models.NewKoguchiBoxSize.OrderIdScanKoguchiResponse;
import com.itechvision.ecrobo.pickman.Models.NewKoguchiBoxSize.SubmitKoguchiboxsize.SubmitKoguchiBoxsizeReq;
import com.itechvision.ecrobo.pickman.Models.NewKoguchiBoxSize.SubmitKoguchiboxsize.SubmitkoguchiboxsizeResponse;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class NewShippingSpecificationBoxSize extends BaseActivity implements View.OnClickListener , DataManager.NewKoguchiOrderIDScancall ,DataManager.SubmitKoguchiBoxsizecall{
    /* static SlidingMenu menu;
     SlideMenu slidemenu;*/
  //  @BindView(R.id.actionbar) ActionBar actionbar;
    @BindView(R.id.add_layout) Button numbrbtn;
    @BindView(R.id.boxsize) Spinner spinner;
    @BindView(R.id.submit) Button submit;
    @BindView(R.id.btn_null) Button nullbtn;
    @BindView(R.id.layout_number) ViewGroup layout;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.boxlayout2) LinearLayout layout2;
    @BindView(R.id.boxlayout3) LinearLayout layout3;
    @BindView(R.id.orderid) TextView orderid;
    @BindView(R.id.trackingNumber) EditText trackingNumber;
    @BindView(R.id.edtboxsize) EditText boxsize;
    @BindView(R.id.back)
    ImageView back;

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
    boolean boxslect = true ,  trackscan = false;
    protected List<Map<String, String>> mOrderList = null;
    public List<String> boxlist = new ArrayList<>();
    public List<String> tracklist = new ArrayList<>();
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
    String ORDERID = "", KoguchiCount = "",Comapany ="",ScannedKoguchi="0" ,FirstScan="";
    String TAG= NewShippingSpecificationBoxSize.class.getSimpleName();
    ArrayList<String> sizes= new ArrayList<>();
    String arrsize[] = {"デフォルトサイズを選択","60","80","100","120","140","160","2","2.5","3","1","null"};
    public  static String BatchStatus ="";
    DataManager manager;
    progresBar progress;

    //  microsoft
    DataManager.NewKoguchiOrderIDScancall getOrderIdscan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newshippingspec_boxsize);
        ButterKnife.bind(NewShippingSpecificationBoxSize.this);
        getOrderIdscan = this;

        Intent intent = getIntent();

        Log.d(TAG,"On Create ");
        ORDERID=intent.getStringExtra("orderid");
        Log.e("gdsjfhufhgjkdfhbkudgjb",">>>> :- "+ORDERID);
        orderid.setText(intent.getStringExtra("orderid"));

        getIDs();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        sharedPreferences = getSharedPreferences(MYPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id",null);
        manager = new DataManager();
        progress = new progresBar(this);

        progress.Show();
        OrderIdScanKoguchiReq req = new OrderIdScanKoguchiReq(adminID,ORDERID,app.getSerial(),BaseActivity.getShopId());
        manager.NewKoguchiOrderIDScan(req,getOrderIdscan);

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
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void nextProcess() {
        setSpinner() ;
    }

    @OnClick (R.id.box_clear) void boxClear () {
        if(mProcNo <= 3) {
            setProc(PROC_BOXNO);
            boxsize.setText("");
        }
    }


    private void setSpinner()
    {
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

        Log.e("ShippingSpecification","Sizeee  "+BaseActivity.getsizepos());

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
                U.beepError(NewShippingSpecificationBoxSize.this,"サイズを選択");
                setBoxSize(sizes.get(0));
            }
        });


    }
    public void SizeSelect(View view)
    {
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
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

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
            Log.e(TAG,"SetlayoutMargin");
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

    public String getBoxSize()
    {
        return selectedboxsize;
    }

    //set title and icons on actionbar
    private void getIDs() {

      //  actionbarImplement(this, "SC送り状CSV", " ", R.drawable.back, true,false,false );
        //menubarr
        // menu = setSlidingMenu(this);
        //slidemenu = new SlideMenu(menu, this);
       // relLayout1.setOnClickListener(this);
       // btnBlue.setOnClickListener(this);
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


    public void setProc(int procNo) {
        mProcNo = procNo;
        switch (procNo) {
            case PROC_TRACKID:
                _gt(R.id.trackingNumber).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(R.id.edtboxsize).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.trackingNumber).setFocusableInTouchMode(true);

                break;
            case PROC_BOXNO:
                _gt(R.id.trackingNumber).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off));
                _gt(R.id.edtboxsize).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on));
                _gt(R.id.edtboxsize).setFocusableInTouchMode(true);
                break;

        }
    }
    public void inputedEvent(String buff,boolean isScaned) {

        switch (mProcNo) {
            case PROC_TRACKID:	// バーコード
                String track = _gts(R.id.trackingNumber);

                if ("".equals(track)) {
                    U.beepError(this, "数量は必須です");
                    _gt(R.id.trackingNumber).setFocusableInTouchMode(true);
                    break;
                }else{
                    if (Comapany.equals("0")){
                        if (trackscan){
                            if (FirstScan.equals(track)){
                                setProc(PROC_BOXNO);
                            }else{
                                U.beepError(this,"問番の桁数が設定した値と違います");
                            }
                        }else{
                            FirstScan = track;
                            trackscan= true;
                            setProc(PROC_BOXNO);
                        }
                    }else {
                         boolean match = false;
                        for (int i=0; i<tracklist.size();i++){
                            if ( tracklist.get(i).equals(track)){
                                U.beepError(this,"送状番号同じです。");
                                match = true;
                                break;
                            }else{

                         }

                        }

                        if (match==false){
                            tracklist.add(track);
                            setProc(PROC_BOXNO);
                            //  _sts(R.id.edtboxsize,"");

                            if (setback){
                                if (btnpress) {
                                    btnpress = false;
                                    String b = getBoxSize();
                                    Log.e(TAG, "asdfghhh" + b);
                                    boxsize.setText(b);

                                }
                                setback = false;
                            }

                        }
                      /*  if (tracklist.size()>0){
                             for (String string : tracklist) {
                                if (string.matches(track)) {
                                    U.beepError(this,"送状番号同じです。");
                                } else {
                                    tracklist.add(track);
                                    setProc(PROC_BOXNO);
                                    //  _sts(R.id.edtboxsize,"");

                                    if (setback){
                                        if (btnpress) {
                                            btnpress = false;
                                            String b = getBoxSize();
                                            Log.e(TAG, "asdfghhh" + b);
                                            boxsize.setText(b);

                                        }
                                        setback = false;
                                    }
                                }

                            }

                        }else{
                                     tracklist.add(track);
                                    setProc(PROC_BOXNO);
                                    //  _sts(R.id.edtboxsize,"");

                                    if (setback){
                                        if (btnpress) {
                                            btnpress = false;
                                            String b = getBoxSize();
                                            Log.e(TAG, "asdfghhh" + b);
                                            boxsize.setText(b);

                                        }
                                        setback = false;
                                    }

                        }

*/

                    }
                }
                 break;

            case PROC_BOXNO:
                if( _gts(R.id.edtboxsize).equals(""))
                {
                    U.beepError(this, "サイズを選択");
                    _gt(R.id.edtboxsize).setFocusableInTouchMode(true);
                    break;
                }
                if (Comapany.equals("0")){
                    boxlist.add(_gts(R.id.edtboxsize));

                    String processedCnt =KoguchiCount;

                    ScannedKoguchi= U.plusTo(ScannedKoguchi, "1");
                    //increase qunatity in mpackdata
                    if (Integer.parseInt(KoguchiCount) >= Integer.parseInt(ScannedKoguchi)) {

                            /* check if update in quantity need next action */
                            if (ScannedKoguchi.equals(KoguchiCount)) {

                                StringBuffer boxSize = new StringBuffer();

                                for (String t1 : boxlist) {
                                    boxSize.append(",").append(t1);
                                }

                                progress.Show();
                                SubmitKoguchiBoxsizeReq req = new SubmitKoguchiBoxsizeReq(adminID,app.getSerial(),BaseActivity.getShopId(),boxSize.substring(1).toString(),ORDERID,trackingNumber.getText().toString());
                                manager.SubmitKoguchiBoxsize(req,this);

                             }else{

                             //   _sts(R.id.edtboxsize,"");
                             //   setProc(PROC_BOXNO);
                                setProc(PROC_TRACKID);
                                /*if (btnpress) {
                                    btnpress = false;
                                    String b = getBoxSize();
                                    Log.e(TAG, "asdfghhh" + b);
                                    boxsize.setText(b);
                                }
*/
                            }
                    }else{
                     }
                    break;
                }else{
                    boxlist.add(_gts(R.id.edtboxsize));

                    String processedCnt =KoguchiCount;

                    ScannedKoguchi= U.plusTo(ScannedKoguchi, "1");
                    //increase qunatity in mpackdata
                    if (Integer.parseInt(KoguchiCount) >= Integer.parseInt(ScannedKoguchi)) {

                         /* check if update in quantity need next action */
                        if (ScannedKoguchi.equals(KoguchiCount)) {
                            StringBuffer boxSize = new StringBuffer();
                            StringBuffer track1 = new StringBuffer();

                            for (String t1 : boxlist) {
                                boxSize.append(",").append(t1);
                            }

                            for(String t1 : tracklist){
                                track1.append(",").append(t1);
                            }
                            // pass boxsize as  "boxSize.substring(1).toString()"
                            //pass tracking no.  as  "track1.substring(1).toString()"
                            progress.Show();
                            SubmitKoguchiBoxsizeReq req = new SubmitKoguchiBoxsizeReq(adminID,app.getSerial(),BaseActivity.getShopId(),boxSize.substring(1).toString(),
                                    ORDERID,track1.substring(1).toString());
                            manager.SubmitKoguchiBoxsize(req,this);

                        }else{

                            trackingNumber.setText("");
                             setProc(PROC_TRACKID);
                            setback =true ;
                        }
                    }else{
                     }
                }
                break;
        }
    }


    public void setTrack(ArrayList <String> list){
        tracklist = list;
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
        setProc(PROC_TRACKID);
        trackingNumber.setText("");
        boxsize.setText("");
        ScannedKoguchi="0";
        btnpress=false;
        boxlist.clear();
        setback =false;
        size="";
        setBoxSize("");
        tracklist.clear();
        FirstScan="";
        trackscan = false;
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

        }
    }

    public void setProductList(Map<String, String> data) {
        Log.e("ShipSpecification", " setProductList");
        mTarget = data;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
    protected void onDestroy() {
        super.onDestroy();
        if (mTextToSpeak.isSpeechReady){
            mTextToSpeak.onStopSpeaking();
            mTextToSpeak.resetQueue();}
    }


    @Override
    public void onSucess(int status, OrderIdScanKoguchiResponse message) throws JsonIOException {

        if (message.getCode().equals("0")){
            progress.Dismiss();
            setProc(PROC_TRACKID);
            ScannedKoguchi= "0";
            KoguchiCount = message.getKoguchi();
            Comapany= message.getShipping_method();
        }
    }

    @Override
    public void onSucess(int status, SubmitkoguchiboxsizeResponse message) throws JsonIOException {
        progress.Dismiss();
         if (message.getCode().equals("0")){

             if (message.getStatus().equals("1")){
                 U.beepKakutei(this, "検品データを登録しました。");
                 Intent q = new Intent(this, PrintKoguchiActivity.class);
                 startActivity(q);
                 finish();
             }else{

             }
         }else if(message.getCode().equals("10201")){
             U.beepError(this,message.getMessage());
             BaseActivity.setsizepos(0);
             setProc(PROC_TRACKID);
             trackingNumber.setText("");
             boxsize.setText("");
             ScannedKoguchi="0";
             btnpress=false;
             boxlist.clear();
             setback =false;
             size="";
             setBoxSize("");
             tracklist.clear();
             FirstScan="";
             trackscan = false;
          }else{
             U.beepError(this,message.getMessage());
         }
    }

    @Override
    public void onError(int status, ResponseBody error) {
     progress.Dismiss();
    }

    @Override
    public void onFaliure() {
        progress.Dismiss();
        U.beepError(this,"Server error");
     }

    @Override
    public void onNetworkFailure() {
progress.Dismiss();
        U.beepError(this,"Network failure");

    }
}
