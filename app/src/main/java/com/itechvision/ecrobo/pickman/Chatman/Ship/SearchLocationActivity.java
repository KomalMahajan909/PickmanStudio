package com.itechvision.ecrobo.pickman.Chatman.Ship;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.R.drawable;
import com.itechvision.ecrobo.pickman.R.id;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchLocationActivity extends BaseActivity implements View.OnClickListener, MainAsynListener {
    static SlidingMenu menu;
    SlideMenu slidemenu;
    @BindView(R.id.actionbar)
    ActionBar actionbar;
    @BindView(R.id.layout_number) ViewGroup layout;
    @BindView(R.id.layout_main) RelativeLayout mainlayout;

    public TextToSpeak mTextToSpeak;
    private boolean orderRequestSettings;
    String TAG = SearchLocationActivity.class.getSimpleName();
    protected EditText mActiveEdit = null;
    protected int mProcNo = 0;	//
    protected String mSelectedId = null;	//
    private boolean showKeyboard;
    public static final int PROC_LOCATION = 1;
    public static final int PROC_SELECT = 2;
    public static final int PROC_QTY = 3;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    private Button numbrbtn;
    private boolean visible = false;
    public Context mcontext=this;
    ECRApplication app=new ECRApplication();
    String adminID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        ButterKnife.bind(this);

        getIDs();
        Log.d(TAG,"On Create ");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id",null);

        showKeyboard= BaseActivity.getaddKeyboard();
        numbrbtn =(Button) _g(id.add_layout);
        if(showKeyboard==true)
        {
            ViewGroup hiddenPanel = (ViewGroup)findViewById(id.layout_number);
            visible = true;

			numbrbtn.setText("キーボードを隠す");
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);

            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e("MoveActivity","SetlayoutMargin");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            numbrbtn.setVisibility(View.GONE);
        }
        _glv(id.listProduct).setAdapter(null);

        //initialize text to speak listener
        mTextToSpeak = new TextToSpeak(this);

        ListView lv = (ListView) findViewById(R.id.listProduct);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                 U.beepSuccess();
                setProc(PROC_LOCATION);
            }
        });
        if (mProcNo == 0) nextProcess();
    }

    //set title and icons on actionbar
    private void getIDs() {

        actionbarImplement(this, "ロケ検索", " ", 0, true,false,false );
        //menubarr
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);
        relLayout1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relLayout1:
                menu.showMenu();
                break;
            default:
                break;
        }
    }

    public void AddLayout(View view)
    {
        ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.layout_number);
        if(visible==false)
        {
            visible = true;
            numbrbtn.setText("キーボードを隠す");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainlayout.getLayoutParams();
            int bottom= convert(200);
            int top = convert(10);
            params.setMargins(0,0,0,bottom);
            layout = (RelativeLayout) findViewById(R.id.layout_number);
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)layout.getLayoutParams();
            param.setMargins(0,top,0,0);
            Log.e(TAG,"SetlayoutMargin");
            mainlayout.setLayoutParams(params);
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
        }
        else {

            visible = false;
            numbrbtn.setText("キーボードを表示");
            mainlayout = (RelativeLayout) findViewById(R.id.layout_main);
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

    @Override
    protected void onDestroy() {
        mTextToSpeak.onStopSpeaking();
        super.onDestroy();
    }

    public void setProc(int procNo) {
        mProcNo = procNo;
        switch (procNo) {
            case PROC_LOCATION:
                _gt(id.location).setBackground(getResources().getDrawable(drawable.basic_edittext_on));
                _gt(R.id.location).setFocusableInTouchMode(true);
                break;

        }
    }

    @Override
    public void inputedEvent(String buff) {
        //this.keypressEvent("", buff);
        switch (mProcNo) {
            case PROC_LOCATION:	// バーコード
                ListView lv = (ListView) findViewById(R.id.listProduct);
                lv.setAdapter(null);
                Toast.makeText(this, "inputedEvent   " +buff, Toast.LENGTH_SHORT).show();

                Globals.getterList = new ArrayList<>();

                Globals.getterList.add(new ParamsGetter("location", _gts(id.location)));
                Globals.getterList.add(new ParamsGetter("admin_id",adminID));

                new MainAsyncTask(this, Globals.Webservice.GetLocation, 1, SearchLocationActivity.this, "Form", Globals.getterList,true).execute();

                break;
            case PROC_SELECT:	// ロケ選択
                setProc(PROC_LOCATION);
                break;
            case PROC_QTY:		// 数量
                setProc(PROC_LOCATION);
//				String stock2 = _gts(id.stock2);
//				if ("".equals(stock2)) {
//					U.beepError(this, "数量は必須です");
//					_gt(id.stock2).setFocusableInTouchMode(true);
//					break;
//				} else {
//					new ECZaikoClient(this).setListner(new AdjustStock()).adjustStockByLocation(this._gts(id.location), this._gts(id.stock1), mSelectedId, this._gts(id.stock2));
//				}
                break;
        }
    }

    @Override
    public void keypressEvent(String key, String buff) {
        switch (mProcNo) {
            case PROC_LOCATION:	// バーコード
                _sts(id.location, buff);
                break;

        }
    }

    @Override
    public void clearEvent() {
        U.beepSuccess();
        mTextToSpeak.startSpeaking("clear");
//        U.beepBigsound(this, null);
        nextProcess();
    }
    @Override
    public void allclearEvent() {

    }
    @Override
    public void skipEvent() {

    }
    @Override
    public void scanedEvent(String barcode) {
        if (!barcode.equals("")) {
            if (mProcNo == PROC_LOCATION) _sts(id.location, barcode);
        }
        this.inputedEvent(barcode);

    }
    @Override
    public void deleteEvent(String barcode) {
        switch (mProcNo) {
            case PROC_LOCATION:	// バーコード
                _sts(id.location, barcode);
                break;

        }
    }

    @Override
    public void enterEvent() {
    }

    public void nextProcess() {
        this._sts(id.location, "");

        this._glv(id.listProduct).setAdapter(null);

        this.setProc(PROC_LOCATION);
        _gt(id.location).requestFocus();
        _gt(id.location).isTextSelectable();
        if(showKeyboard==false) {
            visible = false;
            numbrbtn.setText("キーボードを表示");
            mainlayout = (RelativeLayout) findViewById(id.layout_main);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mainlayout.getLayoutParams();
            params.setMargins(0, 0, 0, 10);

            ViewGroup hiddenPanel = (ViewGroup) findViewById(id.layout_number);

            hiddenPanel.setVisibility(View.INVISIBLE);
            Log.e("MoveActivity", "SetlayoutMargin");
            mainlayout.setLayoutParams(params);
        }
    }
    @Override
    protected void onStop() {
        mTextToSpeak.resetQueue();
        super.onStop();
    }
    @Override
    public void onBackPressed() {
        // TODO not backed from picking activity
        //super.onBackPressed();
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
                Log.e(TAG,"CODE======Null");
                U.beepError(this, "ネットワーク接続でエラーが発生しました。インターネットに接続できるか確認して下さい。");
            }
            if ("0".equals(code) )
            {

                Log.e("SendLogs111", code + "  " + msg + "  " + result1);
                ListViewItems data = new ListViewItems();

                for (int i = 0; i < result1.size(); i++) {
                    JsonHash row = (JsonHash) result1.get(i);
                    String productCode = row.getStringOrNull("code");
                    String barcode = row.getStringOrNull("barcode");
                    String name = row.getStringOrNull("name");
                    JsonArray list2 = row.getJsonArrayOrNull("stocks");
                    Log.e(TAG, "Listtttttt    "+list2);
                    if (list2 != null) {
                        for (int j = 0; j < list2.size(); j++) {
                            JsonHash row2 = (JsonHash) list2.get(j);
                            String loc = row2.getStringOrNull("location");
                            Log.e(TAG, "11111111111    "+row2);
                            if ("z".equals(loc) == false) {

                                    data.add(data.newItem().add(R.id.stc_prd_0, productCode)
                                            .add(R.id.stc_prd_00,barcode)
                                            .add(R.id.stc_prd_01, row2.getStringOrNull("quantity"))
                                            .add(R.id.stc_prd_1, row2.getStringOrNull("stock_type_label"))
                                    .add(R.id.stc_prd_02,name));

                            }
                        }
                    }
                }
                if (data.getData().size() == 0) {
                    U.beepError(this, msg);
                    return;
                }

                ListViewAdapter adapter = new ListViewAdapter(
                        getApplicationContext()
                        , data
                        , R.layout.list_location_product);
                ListView lv = (ListView) findViewById(R.id.listProduct);
                lv.setAdapter(adapter);
                lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

                U.beepKakutei(this,null);

                setProc(SearchLocationActivity.PROC_LOCATION);
            }else if(code.equalsIgnoreCase("1020")){
                new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                        .setTitle("Error!")
                        .setMessage(msg)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent in = new Intent(SearchLocationActivity.this,LoginActivity.class);
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
        U.beepError(this, "ネットワーク接続でエラーが発生しました。インターネットに接続できるか確認して下さい。");
        System.out.print("FatalException ListShopAllll  ");
    }
}
