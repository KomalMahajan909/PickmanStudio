package com.itechvision.ecrobo.pickman.Chatman;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itechvision.ecrobo.pickman.Activity.ScannerBindActivity;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.ActionBar;
import com.itechvision.ecrobo.pickman.Util.CommonUtilities;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public abstract class BaseActivity extends ScannerBindActivity {
    protected final int _singleItemRes = R.layout.arrival_spinner_item;
    protected final int spinner = R.layout.spinner;
    protected final int spinner2 = R.layout.arrival_spinner_item;
    protected final int _dropdownRes = R.layout.spinner_dropdown_item;

    SweetAlertDialog pDialog1;
    String TAG = BaseActivity.class.getSimpleName();
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    //truckbatch is complete
    public static boolean truckComplete = true;

    public static int sizepos = 0;
    public static int shoppos = 0;
    public static int Pickshop = 0;
    public static int dolphinshop = 0;

    public static String dolphinDate = "";
    public static String shopinfo = "";

    public static int domainpos = 0,langpos =0;
    public static  String role= "";
    public static int stockpos =0;
    public static int errorsoundpos =0;

    public static int oksoundpos =0;
    public static  String urls= "";
    public static  int batchPOS= 0;
    public static String shopID="";
    private PopupWindow mPopupWindow;
    private PopupWindow mPopupWindow2;
    public static String printerbarcodeslip = "";
    public static String printerbarcode = "";
    public static String printerinvoice = "";
    public static String printerpostpay = "";
    public static String printerintegrated = "",integrateprID ="";
    public static String printerCsv= "";
    public static String shippingdate = "";
    public static int barcodeprinterPos = 0,barcodeSlipprinterPos = 0, integratedPos = 0, invoicePos =0, postpayPos = 0 ,csvPos =0;
    public static int barcodemachinePos = 0,barcodeSlipmachinePos = 0, integratedmachinePos = 0, invoicemachinePos =0, postpaymachinePos = 0 ,csvmachinePos = 0, filemachinePos = 0;
    public static int barcodeselectedPos = 0,barcodeSlipselectedPos = 0, integratedselectedPos = 0, invoiceselectedPos =0, postpayselectedPos = 0 ,csvselectedPos =0 ,fileselectedPos =0;
    public static String barcodeselectedID = "",barcodeSlipselectedID = "", integratedselectedID = "",integratedselectedName = "", invoiceselectedID = "",invoiceselectedName = "", postpayselectedID = "" ,csvselectedID = "",csvselectedName = "",fileselectedID = "",fileselectedName = "";
    public static boolean directToStock = false;
    public static boolean lotnoPress = false;
    public static boolean shippingFlag = false;
    public static boolean tshipping_Flag = false;
    public static boolean parentScanSelected = false;
    public static boolean boxNo = false;
    public static boolean doublePicking = false;
    public static boolean boxSelected = false;
    public static boolean shippedpicked = false;
    public static boolean Tas_ReShip = false;
    public static boolean sinclude =true;
    public static boolean scanProduct = true;
    public static boolean addkeyboard = false;
    public static boolean includeScreen =true;
    public static boolean trackCheck =false;
    public static boolean batchScreen =true;
    public static boolean triplebarcode = false;
    public static boolean arrivalNyuka = false;
    public static boolean optShelf = false;
    public static boolean stockAdjust =false;
    public static boolean supplierlistPress = false;
    public static boolean nosupplierlistPress = false;
    public static boolean searchproduct = false;
    public static boolean packinglistPress = false;
    public static boolean remarkPress = false;
    public static String serialselect = "";
    public static boolean printerPress = false;
    public static boolean batchSelected = false;
    public static boolean toPickingScreen = false;
    public static boolean tracking_date_check = false;
    public static boolean boxSizeChange = false;
    public static boolean caseQtyCheckSelfPut = false;
    public static boolean caseQtyCheckArrival = false;
    public static boolean caseQtyCheckPicking = false;
    public static boolean reshipment = false;
    public  static int CSVSpinnerPos=0;
    public static int KoguchiSpinnerPos =0;
    public static String deviceID = "";


    public static int orderpos = 0;
    public static int returnstockpos = 0;
    public Long threadStartTime = 0l;
    public Long threadStopTime = 0l;
    public Dialog dialog;

    public static int orderInfoBy = 2;
    public static String setDate ="",setTruckDate = "", setrfDate = "";
    public static final String ADDNEXTBOX = "add_next_box_action";
    public static final String NEXTBOX = "next_button";
    public static final String FINISH = "finish_action";
    public static final String ISUSERLOGIN = "isUserLogin";
    public static final String ENTER_BARCODE = "ENTER";
    public static final String CLEAR_BARCODE = "CLEAR";
    protected static String barcodeTemp = "";
    public static SlidingMenu menu;
    static ActionBar actionBar;
    public static TextView txtTitle,txtRightTitle;
    public static ImageView imgLeft,imgRight,img3ActionBar;
    public static  RelativeLayout relLayout1,relLayout2,relLayout3;
    protected HashMap<String, String> mConfig = new HashMap<String, String>();

    public static Button btnRed,btnGreen,btnBlue,btnYellow;
    public static int getsizepos(){
        return sizepos;
    }
    public static void setsizepos(int pos){
        sizepos = pos;
    }
    public static int getshoppos(){
        return shoppos;
    }
    public static void setshoppos(int pos){
        shoppos = pos;
    }

    public static int getPickshop(){
        return Pickshop;
    }
    public static void setPickshop(int pos){
        Pickshop = pos;
    }

    public static int getDolfinArrivalshop(){
        return dolphinshop;
    }
    public static void setDolfinArrivalshop(int pos){
        dolphinshop = pos;
    }

    public static String getDolphinDate(){
        return dolphinDate;
    }
    public static void setDolphinDate(String date){
        dolphinDate = date;
    }

    public static String getShopinfo(){
        return shopinfo;
    }
    public static void setShopinfo(String shop){
        shopinfo = shop;
    }
    public static int getdomainpos(){
        return domainpos;
    }
    public static void setdomainpos(int pos){
        domainpos = pos;
    }

    public static int getlangpos(){
        return langpos;
    }
    public static void setlangpos(int pos){
        langpos = pos;
    }

    public static void setUrl(String url){ urls=url;}
    public static String getUrl (){return urls;}
    public static String getShopId(){
        return shopID;
    }
    public static void setShopId(String shop){
        shopID = shop;
    }

    public static boolean getShippingflag(){
        return shippingFlag;
    }

    public static void setShippingflag(boolean option){
        shippingFlag = option;
    }

    public static boolean getTshipping_flag(){
        return tshipping_Flag;
    }

    public static void setTshipping_flag(boolean option){
        tshipping_Flag = option;
    }

    public static boolean getDoublePicking(){
        return doublePicking;
    }

    public static void setDoublePicking(boolean option){
        doublePicking = option;
    }

    public static boolean getBoxNo(){
        return boxNo;
    }

    public static void setBoxNo(boolean option){
        boxNo = option;
    }

    public static boolean getBoxSelected(){
        return boxSelected;
    }

    public static void setBoxSelected(boolean option){
        boxSelected = option;
    }

    public static boolean getShipppicked(){
        return shippedpicked;
    }

    public static void setShipppicked(boolean option){
        shippedpicked = option;
    }

    public static boolean get_Tas_ReShip(){
        return Tas_ReShip;
    }

    public static void set_Tas_ReShip(boolean option){
        Tas_ReShip = option;
    }


 public static String get_DeviceID(){
        return deviceID;
    }

    public static void set_DeviceID(String dvc){
        deviceID = dvc;
    }




    public static boolean getLotPress(){
        return lotnoPress;
    }

    public static void setLotPress(boolean option){
        lotnoPress = option;
    }

    public static int getdirectStockPos(){
        return stockpos;
    }
    public static void setdirectStockPos(int pos){
        stockpos = pos;
    }

    public static int getErrorSound(){
        return errorsoundpos;
    }
    public static void setErrorSound(int pos){
        errorsoundpos = pos;
    }

    public static int getOkSound(){
        return oksoundpos;
    }
    public static void setOkSound(int pos){
        oksoundpos = pos;
    }

    public PopupWindow getPopupWindow() {
        return mPopupWindow;
    }
    public PopupWindow getPopupWindow2() {
        return mPopupWindow2;
    }

    public void setPopupWindow(PopupWindow popupWindow) {
        mPopupWindow = popupWindow;
    }

    public void setPopupWindow2(PopupWindow popupWindow) {
        mPopupWindow2 = popupWindow;
    }



    protected void scrollToView(final ScrollView scrollViewParent, final View view) {
        // Get deepChild Offset
        view.requestFocus();
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        // Scroll to child.
        scrollViewParent.smoothScrollTo(0, childOffset.y);
    }
    protected void getDeepChildOffset(final ViewGroup mainParent, final ViewParent parent, final View child, final Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        accumulatedOffset.y += child.getTop();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
    }

    public static SlidingMenu setSlidingMenu(Context c) {
        menu = new SlidingMenu(c);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.35f);
        menu.setBehindWidth(CommonUtilities.setWidth(c)/2+140);

        menu.attachToActivity((Activity) c, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.activity_slider);
        menu.addIgnoredView(menu);

        return menu;
    }
    public static SlidingMenu hideSlidingMenu(Context c) {
        menu = new SlidingMenu(c);
        menu.setMode(SlidingMenu.RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.35f);
        menu.setBehindWidth(CommonUtilities.setWidth(c)/2+140);


        menu.attachToActivity((Activity) c, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.activity_slider);

        menu.removeIgnoredView(menu);

        return menu;
    }

    public  void actionbarImplement(Activity _Context,String strTitle,String strRighttTitle,Integer leftImage,boolean showblue,boolean showred, boolean showgreen ){

        actionBar = (ActionBar)_Context.findViewById(R.id.actionbar);
        relLayout1=(RelativeLayout)actionBar.findViewById(R.id.relLayout1);
        relLayout2=(RelativeLayout)actionBar.findViewById(R.id.relLayout2);
        txtTitle=(TextView)actionBar.findViewById(R.id.txtTitle);

//        txtRightTitle=(TextView)actionBar.findViewById(R.id.txtRightTitle);
        imgLeft=(ImageView)actionBar.findViewById(R.id.img1ActionBar);
//        imgRight=(ImageView)actionBar.findViewById(R.id.img2ActionBar);

        btnBlue=(Button) actionBar.findViewById(R.id.notif_count_blue);
        btnGreen=(Button)actionBar.findViewById(R.id.notif_count_green);
        btnRed=(Button)actionBar.findViewById(R.id.notif_count_red);



        txtTitle.setText(strTitle);
//        txtRightTitle.setText(strRighttTitle);
        imgLeft.setBackgroundResource(leftImage);
//        imgRight.setBackgroundResource(righttImage);

        if(showred) {
            btnRed.setVisibility(View.VISIBLE);
            setBadge2(0);
        } else
            btnRed.setVisibility(View.GONE);

        if(showblue) {
            btnBlue.setVisibility(View.VISIBLE);
            setBadge1(0);
        }else
            btnBlue.setVisibility(View.GONE);

        if(showgreen) {
            btnGreen.setVisibility(View.VISIBLE);
            setBadge3(0);
        } else
            btnGreen.setVisibility(View.GONE);

    }

    public  void actionbarImplement(Activity _Context,String strTitle,String strRighttTitle,Integer leftImage,boolean showblue,boolean showred, boolean showgreen ,boolean showyellow){

        actionBar = (ActionBar)_Context.findViewById(R.id.actionbar);
        relLayout1=(RelativeLayout)actionBar.findViewById(R.id.relLayout1);
        relLayout2=(RelativeLayout)actionBar.findViewById(R.id.relLayout2);
        txtTitle=(TextView)actionBar.findViewById(R.id.txtTitle);

//        txtRightTitle=(TextView)actionBar.findViewById(R.id.txtRightTitle);
        imgLeft=(ImageView)actionBar.findViewById(R.id.img1ActionBar);
//        imgRight=(ImageView)actionBar.findViewById(R.id.img2ActionBar);

        btnBlue = (Button) actionBar.findViewById(R.id.notif_count_blue);
        btnGreen = (Button)actionBar.findViewById(R.id.notif_count_green);
        btnRed = (Button)actionBar.findViewById(R.id.notif_count_red);
        btnYellow = (Button)actionBar.findViewById(R.id.notif_count_yellow);



        txtTitle.setText(strTitle);
//        txtRightTitle.setText(strRighttTitle);
        imgLeft.setBackgroundResource(leftImage);
//        imgRight.setBackgroundResource(righttImage);

        if(showyellow) {
            btnYellow.setVisibility(View.VISIBLE);
            setBadge4(0);
        } else
            btnYellow.setVisibility(View.GONE);


        if(showred) {
            btnRed.setVisibility(View.VISIBLE);
            setBadge2(0);
        } else
            btnRed.setVisibility(View.GONE);

        if(showblue) {
            btnBlue.setVisibility(View.VISIBLE);
            setBadge1(0);
        }else
            btnBlue.setVisibility(View.GONE);

        if(showgreen) {
            btnGreen.setVisibility(View.VISIBLE);
            setBadge3(0);
        } else
            btnGreen.setVisibility(View.GONE);

    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch(ev.getAction())
//        { case MotionEvent.ACTION_DOWN:
//            x1 = ev.getX();
//            break;
//            case MotionEvent.ACTION_UP:
//
//                x2 = ev.getX();
//
//                float deltaX = x2 - x1;
//
//                if (Math.abs(deltaX) > MIN_DISTANCE)
//                {
//                    // Left to Right swipe action
//                    if (x2 > x1)
//                    { menu = setSlidingMenu(this);
//                      if(!menu.isMenuShowing())
//                      menu.showMenu();
//                        Toast.makeText(this, "Left to Right swipe [Next]  "+x2+"  "+x1, Toast.LENGTH_SHORT).show ();
//                    }
//
//                    // Right to left swipe action
//                    else
//                    { menu = hideSlidingMenu(this);
//                        if(menu.isMenuShowing())
//                        menu.hideMenu();
//                        Toast.makeText(this, "Right to Left swipe [Previous]"+x2+"  "+x1, Toast.LENGTH_SHORT).show ();
//                    }
//
//                }
//                else
//                {
//                    // consider as something else - a screen tap for example
//                }
//                break;
//
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    public void setBadge1(long cnt) {
        btnBlue.setText(cnt+"");

    }
    public void setBadge2(long cnt) {

        btnRed.setText(cnt+"");

    }
    public void setBadge3(long cnt) {
        btnGreen.setText(cnt+"");

    }
    public void setBadge4(long cnt) {
        btnYellow.setText(cnt+"");
    }

    public void setBadge11(String cnt) {
        btnBlue.setText(cnt);

    }
    public void setBadge12(String cnt) {

        btnRed.setText(cnt);

    }
    public void setBadge13(String cnt) {
        btnGreen.setText(cnt);

    }
    public void setBadge14(String cnt) {
        btnYellow.setText(cnt);
    }
    public long getBadge1() {
//        if (btnBlue != null) {
        String value = btnBlue.getText().toString();
        if ("".equals(value))
            return 0;
        else
            return Long.valueOf(value);
    }
    public long getBadge2() {
//        if (mNotifyBadge1 != null) {
        String value = btnRed.getText().toString();
        if ("".equals(value))
            return 0;
        else
            return Long.valueOf(value);
//        }
//        return -1;
    }
    public long getBadge3() {
//        if (mNotifyBadge1 != null) {
        String value = btnGreen.getText().toString();
        if ("".equals(value))
            return 0;
        else
            return Long.valueOf(value);
//        }
//        return -1;
    }

    public long getBadge4() {
//        if (btnBlue != null) {
        String value = btnYellow.getText().toString();
        if ("".equals(value))
            return 0;
        else
            return Long.valueOf(value);

    }
    protected boolean showPopup1() {
        Log.d("CounterClicked", "popup2");
        return false;
    }
    protected boolean showPopup2() {
        Log.d("CounterClicked", "popup2");
        return false;
    }
    protected boolean showPopup3() {
        Log.d("CounterClicked", "popup3");
        return false;
    }
    protected boolean showInfo() {
        Log.d("CounterClicked", "showinfo");
        return false;
    }
    public void startTimer(){
        threadStartTime = SystemClock.uptimeMillis();
    }
    public void stopTimer(){
        threadStopTime = SystemClock.uptimeMillis();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void showDialog(String msg)
    {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set GUI of login screen
        dialog.setContentView(R.layout.new_picking_dialog);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());


        dialog.setCanceledOnTouchOutside(false);

        // Init button of login GUI
        TextView txt=(TextView)dialog.findViewById(R.id.txt) ;
        txt.setText(msg);
        ImageView close=(ImageView)dialog.findViewById(R.id.icon_close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        // Make dialog box visible.
        dialog.show();

    }

    public Long getThreadStartTime(){
        return (threadStartTime > 0)? threadStartTime : 0;
    }
    public Long getThreadStopTime(){
        return (threadStopTime > 0)? threadStopTime : 0;
    }
    public Long timeTaken(){
        Long totalMili = getThreadStopTime() - getThreadStartTime();
        Long durationInSec = 0l;
        if (totalMili > 0)
            durationInSec = totalMili / 1000;
        return durationInSec;
    }


    public boolean checkPrinterSelect(){
        Log.e(TAG,">>>>>>>>>>>>>>>>>>>"+getbarcodeselectedPrinterID());
        Log.e(TAG,">>>>>>>>>>>>>>>>>>>1111111"+getBarcodeSlipselectedPrinterID());
        Log.e(TAG,">>>>>>>>>>>>>>>>>>>222222"+getinvoiceselectedPrinterID());
        Log.e(TAG,">>>>>>>>>>>>>>>>>>>333333"+getintegratedselectedPrinterID());
        Log.e(TAG,">>>>>>>>>>>>>>>>>>>444444"+getFileselectedPrinterID());
        Log.e(TAG,">>>>>>>>>>>>>>>>>>>555555"+getPostPayselectedPrinterID());
        Log.e(TAG,">>>>>>>>>>>>>>>>>>>666666"+getCsvselectedPrinterID());

        if(getbarcodeselectedPrinterID().equalsIgnoreCase("") && getBarcodeSlipselectedPrinterID().equalsIgnoreCase("")
                && getinvoiceselectedPrinterID().equalsIgnoreCase("") && getintegratedselectedPrinterID().equalsIgnoreCase("")
                && getPostPayselectedPrinterID().equalsIgnoreCase("") && getCsvselectedPrinterID().equalsIgnoreCase("")
                && getFileselectedPrinterID().equalsIgnoreCase(""))
        {
            return true;
        }
        return false;

    }
    public void setBatchPOS(int id){
        this.batchPOS = id;
    }
    public int getBatchPOS(){
        return this.batchPOS;
    }

    public static boolean getDirectToStock(){
        return directToStock;
    }
    public static void setDirectToStock(boolean option){
        directToStock = option;
    }
    public static String getdate(){
        return setDate;
    }
    public static void setdate(String option){
        setDate = option;
    }

    public static String gettruckdate(){
        return setTruckDate;
    }
    public static void settruckdate(String option){
        setTruckDate = option;
    }
    public static String getRfdate(){
        return setrfDate;
    }
    public static void setRFdate(String option){
        setrfDate = option;
    }

    public static int getOrderInfoBy(){
        return orderInfoBy;
    }
    public static void setOrderInfoBy(int option){
        orderInfoBy = option;
    }

    public static boolean getPackingList(){return packinglistPress;  }
    public static void setPackingList(boolean option){
        packinglistPress = option;
    }

    public static boolean getsinclude(){
        return sinclude;
    }
    public static void setsinclude(boolean option){
        sinclude = option;
    }

    public static boolean getscanProduct(){
        return scanProduct;
    }
    public static void setscanProduct(boolean option){
        scanProduct = option;
    }

    public static boolean getincludeScreen(){
        return includeScreen;
    }
    public static void setincludeScreen(boolean option){
        includeScreen = option;
    }
    public static boolean getbatchScreen(){
        return includeScreen;
    }
    public static void setbatchScreen(boolean option){
        includeScreen = option;
    }

    public static boolean getTrackCheck(){
        return trackCheck;
    }
    public static void setTrackCheck(boolean option){
        trackCheck = option;
    }

    public static boolean getTripleBarcode(){
        return triplebarcode;
    }

    public static void setTripleBarcode(boolean option){
        triplebarcode = option;
    }
    protected void setBarcodeTemp(String code){
        barcodeTemp = code;
    }
    protected String getBarcodeTemp(){
        return barcodeTemp;
    }
    public static boolean getArrivalNyuka(){return arrivalNyuka;  }

    public static boolean getRemarkPress(){return remarkPress;}
    public static void setRemarkPress(boolean option){
        remarkPress = option;
    }

    public static boolean getToPickingScreen(){return toPickingScreen;}
    public static void setToPickingScreen(boolean option){
        toPickingScreen = option;
    }

    public static String getshippingDate(){return shippingdate;}
    public static void setshippingDate(String date){
        shippingdate = date;
    }
    public static void setArrivalNyuka(boolean option){
        arrivalNyuka = option;
    }
    public static boolean getOptShelf(){
        return optShelf;
    }
    public static void setOptShelf(boolean option){
        optShelf = option;
    }
    public static int getorderpos(){
        return orderpos;
    }
    public static void setorderpos(int pos){
        orderpos = pos;
    }

    public static int getCSVSpinnerPos(){
        return CSVSpinnerPos;
    }
    public static void setCSVSpinnerPos(int option){
        CSVSpinnerPos = option;
    }

    public static int getKoguchiSpinnerPos(){
        return KoguchiSpinnerPos;
    }
    public static void setKoguchiSpinnerPos(int option){
        KoguchiSpinnerPos = option;
    }

    public static int getReturnstockpos(){
        return returnstockpos;
    }
    public static void setReturnstockpos(int pos){
        returnstockpos = pos;
    }
    public static boolean getaddKeyboard(){
        return addkeyboard;
    }
    public static void setaddKeyboard(boolean option){
        addkeyboard = option;
    }
    public static boolean getSupplierList(){
        return supplierlistPress;
    }
    public static void setSupplierList(boolean option){
        supplierlistPress = option;
    }
    public static boolean getNoSupplierList(){
        return nosupplierlistPress;
    }
    public static void setNoSupplierList(boolean option){
        nosupplierlistPress = option;
    }
    public static void setrole(String rolee){ role=rolee;}
    public static String getrole (){return role;}
    public static boolean getSearchProduct(){
        return searchproduct;
    }
    public static void setSearchproduct(boolean option){
        searchproduct=option;
    }
    public static boolean getPrinterSelected(){
        return printerPress;
    }
    public static void setPrinterSelected(boolean option){
        printerPress = option;
    }
    public static boolean getBatchSelected(){
        return batchSelected;
    }
    public static void setBatchSelected(boolean option){
        batchSelected = option;
    }
    public abstract void nextProcess();
    //getter Setter for Printer Machine
    public static int getbarcodeMachinePos(){
        return barcodemachinePos;
    }
    public static void setbarcodeMachinePos(int printer){
        barcodemachinePos = printer;
    }
    public static int getintegratedMachinePos(){
        return integratedmachinePos;
    }
    public static void setintegratedMachinePos(int printer){
        integratedmachinePos = printer;
    }
    public static int getBarcodeSlipMachinePos(){
        return barcodeSlipmachinePos;
    }
    public static void setBarcodeSlipMachinePos(int printer){
        barcodeSlipmachinePos = printer;
    }
    public static int getinvoiceMachinePos(){
        return invoicemachinePos;
    }
    public static void setinvoiceMachinePos(int printer){
        invoicemachinePos = printer;
    }
    public static int getPostPayMachinePos(){
        return postpaymachinePos;
    }
    public static void setPostPayMachinePos(int printer){
        postpaymachinePos = printer;
    }
    public static int getCsvMachinePos(){
        return csvmachinePos;
    }
    public static void setCsvMachinePos(int printer){
        csvmachinePos = printer;
    }
    public static int getFileMachinePos(){
        return filemachinePos;
    }
    public static void setFileMachinePos(int printer){
        filemachinePos = printer;
    }
    // getter Setter for selected printer
    public static int getbarcodeselectedPrinterPOS(){
        return barcodeselectedPos;
    }
    public static void setbarcodeselectedPrinterPOS(int printer){
        barcodeselectedPos = printer;
    }
    public static int getintegratedselectedPrinterPOS(){
        return integratedselectedPos;
    }
    public static void setintegratedselectedPrinterPOS(int printer){integratedselectedPos = printer;}
    public static int getBarcodeSlipselectedPrinterPOS(){
        return barcodeSlipselectedPos;
    }
    public static void setBarcodeSlipselectedPrinterPOS(int printer){barcodeSlipselectedPos = printer; }
    public static int getinvoiceselectedPrinterPOS(){
        return invoiceselectedPos;
    }
    public static void setinvoiceselectedPrinterPOS(int printer){
        invoiceselectedPos = printer;
    }
    public static int getPostPayselectedPrinterPOS(){
        return postpayselectedPos;
    }
    public static void setPostPayselectedPrinterPOS(int printer){
        postpayselectedPos = printer;
    }
    public static int getCsvselectedPrinterPOS(){
        return csvselectedPos;
    }
    public static void setCsvselectedPrinterPOS(int printer){csvselectedPos = printer; }
    public static int getFileselectedPrinterPOS(){
        return fileselectedPos;
    }
    public static void setFileselectedPrinterPOS(int printer){fileselectedPos = printer; }
    // getter Setter for selected printerID
    public static String getbarcodeselectedPrinterID(){
        return barcodeselectedID;
    }
    public static void setbarcodeselectedPrinterID(String printer){
        barcodeselectedID = printer;
    }
    public static String getintegratedselectedPrinterID(){
        return integratedselectedID;
    }
    public static void setintegratedselectedPrinterID(String printer){integratedselectedID = printer;}
    public static String getintegratedselectedPrinterName(){
        return integratedselectedName;
    }
    public static void setintegratedselectedPrinterName(String printer){integratedselectedName = printer;}

    public static String getBarcodeSlipselectedPrinterID(){
        return barcodeSlipselectedID;
    }
    public static void setBarcodeSlipselectedPrinterID(String printer){barcodeSlipselectedID = printer; }

    public static String getinvoiceselectedPrinterID(){
        return invoiceselectedID;
    }
    public static void setinvoiceselectedPrinterID(String printer){
        invoiceselectedID = printer;
    }

    public static String getinvoiceselectedPrinterName(){
        return invoiceselectedName;
    }
    public static void setetinvoiceselectedPrinterName(String printer){
        invoiceselectedName = printer;
    }

    public static String getPostPayselectedPrinterID(){
        return postpayselectedID;
    }
    public static void setPostPayselectedPrinterID(String printer){
        postpayselectedID = printer;
    }

    public static String getCsvselectedPrinterID(){
        return csvselectedID;
    }
    public static void setCsvselectedPrinterID(String printer){csvselectedID = printer; }

    public static String getCsvselectedPrinterName(){
        return csvselectedName;
    }
    public static void setCsvselectedPrinterName(String printer){csvselectedName = printer;}

    public static String getFileselectedPrinterID(){
        return fileselectedID;
    }
    public static void setFileselectedPrinterID(String printer){fileselectedID = printer; }

    public static String getFileselectedPrinterName(){
        return fileselectedName;
    }
    public static void setFileelectedPrinterName(String printer){fileselectedName = printer;}

    public static boolean getBoxsizeChange(){
        return boxSizeChange;
    }

    public static void setBoxsizeChange(boolean option){
        boxSizeChange = option;
    }

    public static boolean getTracking_date_check(){
        return tracking_date_check;
    }

    public static void setTracking_date_check(boolean option){
        tracking_date_check = option;
    }

    //for SelfPut screen
    public static boolean get_CaseQtyCheckSelfPut(){
        return caseQtyCheckSelfPut;
    }

    public static void set_CaseQtyCheckSelfPut(boolean option){
        caseQtyCheckSelfPut = option;
    }

    //for arrival screens
    public static boolean get_CaseQtyCheckArrival(){
        return caseQtyCheckArrival;
    }

    public static void set_CaseQtyCheckArrival(boolean option){
        caseQtyCheckArrival = option;
    }

    //for Picking screen
    public static boolean get_CaseQtyCheckPicking(){
        return caseQtyCheckPicking;
    }

    public static void set_CaseQtyCheckPicking(boolean option){
        caseQtyCheckPicking = option;
    }

    //For packset activity if parent barcode scan radio checked
    public static boolean getParentScanSelected(){
        return parentScanSelected;
    }
    public static void setParentScanSelected(boolean option){
        parentScanSelected = option;
    }

    public static boolean getReshipment(){
        return reshipment;
    }

    public static void setReshipment(boolean option){
        reshipment = option;
    }

}
