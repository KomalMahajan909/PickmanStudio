package com.itechvision.ecrobo.pickman.Chatman.Ship;

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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.BoxNumberDetail;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.CheckMediaBoxNumber;
import com.itechvision.ecrobo.pickman.Chatman.Ship.api.SubmitBoxSize;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonDialogs;
import com.itechvision.ecrobo.pickman.Util.CustomPagerAdapter;
import com.itechvision.ecrobo.pickman.Util.TextToSpeak;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.widget.ListViewAdapter;
import com.itechvision.ecrobo.pickman.widget.ListViewItems;
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

public class BoxSizeActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar)
    ActionBar actionbar;
    @BindView(R.id.add_layout) Button numbrbtn;

    @BindView(R.id.submit) Button submit;
    @BindView(R.id.layout_number) ViewGroup layout;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;
    @BindView(R.id.use) RadioGroup radiogUse;
    @BindView(R.id.btn_null) Button nullbtn;
    @BindView(R.id.boxlayout2) LinearLayout layout2;
    @BindView(R.id.boxlayout3) LinearLayout layout3;

    public EditText boxsize;
    public Spinner boxspinner;
    protected int mProcNo = 0;
    public TextToSpeak mTextToSpeak;
    public static final int PROC_TRACKID = 2;
    public static final int PROC_BOXNO = 1;
    public static final int PROC_QNTY =3;
    private boolean showKeyboard;
    public boolean setback = false;
    String selectedboxsize;
    public static boolean trackreq=false;
    public boolean btnpress= false;
    public String size="";
    private boolean visible = false;
    public Context mcontext=this;
    boolean inspectcomplete = false ;
    // if radio button box is selected
    boolean boxslect = true;
    private int totalcount=0;
    protected List<Map<String, String>> mOrderList = null;
    public List<String> boxlist = new ArrayList<>();
    public Map<String, String> mTarget = null;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    ECRApplication app=new ECRApplication();
    String adminID="";
    public static final int COMPLETE_INSPECT = 1;
    public static final int INCOMPLETE_INSPECT = 2;
    public String _lastUpdateQty = "0";
    public static int mRequestStatus =0;
    public static final int REQ_INITIAL = 1;
    public static final int REQ_TRACKING = 2;
    public static final int REQ_BOXNO = 3;
    public static final int REQ_DETAIL = 4;
    String TAG= BoxSizeActivity.class.getSimpleName();
    private String color[] = {"#d63535","#faa450"};
    ArrayList<String> sizes= new ArrayList<>();
    String arrsize[] = {"デフォルトサイズを選択","60","80","100","120","140","160","200","2","2.5","3","null"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_size);

        ButterKnife.bind(BoxSizeActivity.this);

        getIDs();
        Log.d(TAG,"On Create ");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id",null);

        boxsize=(EditText)_gt(R.id.edtboxsize);
        boxspinner = (Spinner)findViewById(R.id.box_spinner) ;
        BaseActivity.setsizepos(0);
        Log.e(TAG,"chaeckValue  "+showKeyboard);

        showKeyboard = BaseActivity.getaddKeyboard();
        mTextToSpeak = new TextToSpeak(this);

        if(boxslect){
            if(!sizes.isEmpty())
                sizes.clear();
            for (int i=0;i<arrsize.length;i++)
                sizes.add(arrsize[i]);

            nullbtn.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
        }



        if(showKeyboard==true)
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
            visible = true;

            numbrbtn.setText(R.string.hideKeyboard);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e(TAG,"SetlayoutMarginnn");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }
        if (mProcNo == 0) nextProcess();

    }

    //set title and icons on actionbar
    private void getIDs() {

        actionbarImplement(this, "箱サイズ変更", " ",
                0, true,false,false );
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
//                showPopup3();
                break;

            default:
                break;
        }
    }

    /*private void setSpinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,sizes){
            // Disable click item < month current
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                     // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(BaseActivity.getsizepos());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0)
                {
                    setBoxSize(sizes.get(position));
                    BaseActivity.setsizepos(position);
                    btnpress=false;
                    size="";
                    boxsize.setText(getBoxSize());
                }
                if (position==0)
                    boxsize.setText("");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                U.beepError(BoxSizeActivity.this,"サイズを選択");
                setBoxSize(sizes.get(0));
            }
        });
    }*/

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
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        Log.e(TAG,"Sizeee  "+ BaseActivity.getsizepos());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        boxspinner.setAdapter(adapter);
        boxspinner.setSelection(BaseActivity.getsizepos());
        Log.e(TAG,"Sizeee11112222222222222  "+ BaseActivity.getsizepos());
        boxspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0) {
                    Log.e(TAG,"Sizeee1111  "+position);
                    setBoxSize(sizes.get(position));
                    BaseActivity.setsizepos(position);
                    btnpress=false;
                    size="";
                    boxsize.setText(getBoxSize());
                    inputedEvent(size);

                }
                if (position==0)
                    boxsize.setText("");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                U.beepError(BoxSizeActivity.this,"サイズを選択");
                setBoxSize(sizes.get(0));
            }
        });


    }


    public void AddLayout(View view)
    {
        ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
        if(visible==false)
        {
            visible = true;

            numbrbtn.setText(R.string.hideKeyboard);
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

        }else{
            visible = false;

            numbrbtn.setText(R.string.showkeyboard);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

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

    public void Enter (View view){
        String s = mBuff.toString();
        Log.e("ScannerbindActivity", "Keyput11222 " + mBuff);
        mBuff.delete(0, mBuff.length());
        Log.e("ScannerbindActivity", "Keyput11222 buff " + mBuff);
        inputedEvent(s);

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

                Log.e(TAG,"array    1"+sizes.get(0)+"   2"+sizes);
                setSpinner();

                nullbtn.setVisibility(View.GONE);
                layout2.setVisibility(View.GONE);
                layout3.setVisibility(View.GONE);
                break;
        }
    }
    public void SizeSelect(View view)
    {
        switch(view.getId())
        {
            case R.id.btn_sixty:
                btnpress=true;
                size="60";
                boxsize.setText(size);
                break;
            case R.id.btn_eighty:
                btnpress=true;
                size="80";
                boxsize.setText(size);
                break;
            case R.id.btn_hundred:
                btnpress=true;
                size="100";
                boxsize.setText(size);
                break;
            case R.id.btn_onetwenty:
                btnpress=true;
                size="120";
                boxsize.setText(size);
                break;
            case R.id.btn_onefourty:
                btnpress=true;
                size="140";
                boxsize.setText(size);
                break;
            case R.id.btn_onesixty:
                btnpress=true;
                size="160";
                boxsize.setText(size);
                break;
            case R.id.btn_two:
                btnpress=true;
                size="2";
                boxsize.setText(size);
                break;
            case R.id.btn_twopfive:
                btnpress=true;
                size="2.5";
                boxsize.setText(size);
                break;
            case R.id.btn_three:
                btnpress=true;
                size="3";
                boxsize.setText(size);
                break;
            case R.id.btn_null:
                btnpress=true;
                size="null";
                boxsize.setText(size);
                break;
            case R.id.btn_twohundred:
                btnpress=true;
                size="200";
                boxsize.setText(size);
                break;
            default:
                btnpress=false;
                size="";
                boxsize.setText(getBoxSize());

        }

    }
    public void setProc(int procNo) {
        mProcNo = procNo;
        switch (procNo) {
            case PROC_TRACKID:
                _gt(R.id.trackingNumber).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
                _gt(R.id.edtboxsize).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.trackingNumber).setFocusableInTouchMode(true);
                break;
            case PROC_BOXNO:
                _gt(R.id.trackingNumber).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.quantity).setBackground(getResources().getDrawable(R.drawable.basic_edittext_off_nopadding));
                _gt(R.id.edtboxsize).setBackground(getResources().getDrawable(R.drawable.basic_edittext_on_nopadding));
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
                }

                Globals.getterList = new ArrayList<>();

                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("mediacode",_gts(R.id.trackingNumber)));
                Globals.getterList.add(new ParamsGetter("mediacode_check","true"));
                Globals.getterList.add(new ParamsGetter("box_update","true"));

                mRequestStatus = REQ_TRACKING;

                new MainAsyncTask(this, Globals.Webservice.shippingStatus, 1, BoxSizeActivity.this, "Form", Globals.getterList,true).execute();

                break;
            case PROC_BOXNO:
                if(BaseActivity.getsizepos()==0 && btnpress==false && this._gts(R.id.edtboxsize).equals(""))
                {
                    U.beepError(this, "サイズを選択");
                    _gt(R.id.edtboxsize).setFocusableInTouchMode(true);
                    break;
                }
                trackreq= true;

                Globals.getterList = new ArrayList<>();

                Log.e(TAG,"shopidddddd  "+ BaseActivity.getShopId());


                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("mediacode",_gts(R.id.trackingNumber)));
                Globals.getterList.add(new ParamsGetter("boxsize",_gts(R.id.edtboxsize)));
                Globals.getterList.add(new ParamsGetter("box_update","true"));

                if(boxslect)
                    Globals.getterList.add(new ParamsGetter("is_polythene","0"));
                else
                    Globals.getterList.add(new ParamsGetter("is_polythene","1"));

                mRequestStatus = REQ_BOXNO;

                new MainAsyncTask(this, Globals.Webservice.shippingStatus, 1, BoxSizeActivity.this, "Form", Globals.getterList,true).execute();

                break;
        }
    }


    @Override
    public void nextProcess() {
        _sts(R.id.trackingNumber, "");
        setBadge3(0);
        setProc(PROC_TRACKID);
        _sts(R.id.quantity, "");
        _sts(R.id.edtboxsize, "");
        btnpress=false;
        trackreq =false;
        size="";
        setBoxSize("");

        BaseActivity.setsizepos(0);
        setSpinner();
        Log.e(TAG, "SetlayoutMarginnnn   0n");


        if(showKeyboard==false) {
            visible = false;
            numbrbtn.setText(R.string.showkeyboard);
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);
            ViewGroup hiddenPanel = (ViewGroup) findViewById(R.id.layout_number);
            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.GONE);
            Log.e(TAG, "SetlayoutMarginnnnn");
            mainlayout.setLayoutParams(params);
        }

        Globals.getterList = new ArrayList<>();

        Log.e(TAG,"shopidddddd  "+ BaseActivity.getShopId());

        Globals.getterList.add(new ParamsGetter("admin_id",adminID));
        Globals.getterList.add(new ParamsGetter("check_count","true"));
        Globals.getterList.add(new ParamsGetter("box_update","true"));

        mRequestStatus = REQ_INITIAL;

        new MainAsyncTask(this, Globals.Webservice.shippingStatus, 1, BoxSizeActivity.this, "Form", Globals.getterList,true).execute();

    }


    public void setBoxSize(String size) {
        this.selectedboxsize = size;
    }

    public String getBoxSize() {
        return this.selectedboxsize;
    }

    @Override
    public void inputedEvent(String buff) {
        Log.e(TAG, "inputedEventtttttt");
        inputedEvent(buff, false);
    }

    @Override
    public void clearEvent() {
        mTextToSpeak.startSpeaking("clear");
        U.beepBigsound(this, null);
        nextProcess();
    }

    @Override
    public void allclearEvent() {

    }

    @Override
    public void skipEvent() {

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
            if (mProcNo == PROC_TRACKID) _sts(R.id.trackingNumber, barcode);
            if (mProcNo == PROC_BOXNO) {
                _sts(R.id.edtboxsize, barcode);
            }
        }
        this.inputedEvent(barcode,true);
    }

    @Override
    public void enterEvent() {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onStop() {
        mTextToSpeak.resetQueue();
        super.onStop();
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

    protected boolean showPopup3() {
        if (totalcount>0) {
            Globals.getterList = new ArrayList<>();

            Log.e(TAG,"shopidddddd  "+ BaseActivity.getShopId());

            Globals.getterList.add(new ParamsGetter("admin_id",adminID));
            Globals.getterList.add(new ParamsGetter("order_detail","true"));
            Globals.getterList.add(new ParamsGetter("box_update","true"));

            mRequestStatus = REQ_DETAIL;

            new MainAsyncTask(this, Globals.Webservice.shippingStatus, 1, BoxSizeActivity.this, "Form", Globals.getterList,true).execute();

            return false;
        }

        return true;
    }

    public boolean initiatePopup(List<Map<String, String>> data) {
        Log.e(TAG, " setOrdertListttt ");
        mOrderList = data;
        if (getPopupWindow() == null) {
            final PopupWindow popupWindow = new PopupWindow(this);
            // レイアウト設定
            View popupView=null;

            popupView = getLayoutInflater().inflate(R.layout.order_details, null);

            popupWindow.setContentView(popupView);
            // 背景設定
            popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_background));
            // タップ時に他のViewでキャッチされないための設定
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);

            // 表示サイズの設定 今回は幅300dp
            float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 324, getResources().getDisplayMetrics());
            popupWindow.setWindowLayoutMode((int) width, WindowManager.LayoutParams.WRAP_CONTENT);
            popupWindow.setWidth((int) width);
            popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            setPopupWindow(popupWindow);
            ImageView close =(ImageView)getPopupWindow().getContentView().findViewById(R.id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();

                }
            });

        }

        ListView lv = (ListView) getPopupWindow().getContentView().findViewById(R.id.workPicking);
        initWorkList(lv);

        // 画面中央に表示
        getPopupWindow().showAtLocation(findViewById(R.id.trackingNumber), Gravity.CENTER, 0, 32);
        return false;
    }

    protected ListViewItems initWorkList(ListView lv) {
        Log.e(TAG, "initWorkList");
        lv.setAdapter(null);
        ListViewItems data = new ListViewItems();
        String date ="";
        final String[] colorarray = new String[mOrderList.size()];
        String setcolor= color[0];
        Map<String, String> map = mOrderList.get(0);
        date = map.get("date_time");
        for (int i = mOrderList.size() - 1; i >= 0; i--) {
            Map<String, String> row = mOrderList.get(i);

            if(date.equals(row.get("date_time")))
            {
                colorarray[i] = setcolor;
            }
            else
            {
                if(setcolor.equals(color[0]))
                    setcolor=color[1];
                else
                    setcolor=color[0];
                colorarray[i] =setcolor;
                date =row.get("date_time");
            }

            data.add(data.newItem().add(R.id.wrk_ins_0,row.get("name2"))
                    .add(R.id.wrk_ins_1,row.get("order_no"))
                    .add(R.id.wrk_ins_2, row.get("date_time"))
            );
            Log.e(TAG,"DATA >>>>"+data);

        }

        ListViewAdapter adapter = new ListViewAdapter(
                getApplicationContext() , data
                , R.layout.order_detail_inspection_row){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (convertView == null) {
                    v = super.getView(position, convertView, parent);
                    TextView time = (TextView) v.findViewById(R.id.wrk_ins_2);
                    TextView order = (TextView) v.findViewById(R.id.wrk_ins_1);
                    TextView name = (TextView) v.findViewById(R.id.wrk_ins_0);
                    if (time == null) {
                        time = new TextView(getApplicationContext());
                        order = new TextView(getApplicationContext());
                        name = new TextView(getApplicationContext());
                    }

                    time.setTextColor(Color.parseColor(colorarray[position]));
                    order.setTextColor(Color.parseColor(colorarray[position]));
                    name.setTextColor(Color.parseColor(colorarray[position]));

                }
                return v;
            }

        };


        lv.setAdapter(adapter);

        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        if (data.getData().size() > 0)
            lv.setItemChecked(0, true);
        return data;
    }




    public void updateBadge1(String qtyCount) {
        Log.e(TAG,"updateBadge3333  "+qtyCount);
        setBadge1(Integer.valueOf(qtyCount));
        totalcount=Integer.parseInt(qtyCount);
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
                Log.e(TAG,"CODEeee===Nulllll");
                CommonDialogs.customToast(getApplicationContext(),"Network error occured");

            }  if ("0".equals(code) ) {

                Log.e("SendLogs111",code+"  "+msg+"  "+result1);

                if(mRequestStatus == REQ_INITIAL)
                {
                    new SubmitBoxSize().post(code,msg,result1, mHash, BoxSizeActivity.this);
                }
                else if(mRequestStatus == REQ_TRACKING)
                {
                    new CheckMediaBoxNumber().post(code,msg,result1, mHash, BoxSizeActivity.this);
                }
                else if(mRequestStatus == REQ_BOXNO) {
                    new SubmitBoxSize().post(code,msg,result1, mHash, BoxSizeActivity.this);
                }
                else if(mRequestStatus == REQ_DETAIL) {
                    new BoxNumberDetail().post(code,msg,result1, mHash, BoxSizeActivity.this);
                }
            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(BoxSizeActivity.this, LoginActivity.class);
                                in.putExtra("ACTION", "logout" );
                                startActivity(in);
                                finish();

                                dialog.dismiss();
                            }
                        })

                        .show();
            }
            else{
                if(mRequestStatus == REQ_INITIAL)
                {
                    new SubmitBoxSize().valid(code,msg,result1, mHash, BoxSizeActivity.this);
                }
                if(mRequestStatus == REQ_TRACKING)
                {
                    new CheckMediaBoxNumber().valid(code,msg,result1, mHash, BoxSizeActivity.this);
                }
                else if(mRequestStatus == REQ_BOXNO){
                    new SubmitBoxSize().valid(code,msg,result1, mHash, BoxSizeActivity.this);
                }
                else if(mRequestStatus == REQ_DETAIL) {
                    new BoxNumberDetail().valid(code,msg,result1, mHash, BoxSizeActivity.this);
                }
            }
        }
        catch (Exception e)
        {
            System.out.print(e);
        }
    }

    @Override
    public void onPostError(int flag) {

    }
}