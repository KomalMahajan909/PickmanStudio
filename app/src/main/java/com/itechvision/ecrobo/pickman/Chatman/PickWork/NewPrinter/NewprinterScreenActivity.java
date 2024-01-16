package com.itechvision.ecrobo.pickman.Chatman.PickWork.NewPrinter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Application.ECRApplication;
import com.itechvision.ecrobo.pickman.AsyncTask.Globals;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsynListener;
import com.itechvision.ecrobo.pickman.AsyncTask.MainAsyncTask;
import com.itechvision.ecrobo.pickman.AsyncTask.ParamsGetter;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Chatman.BaseActivity;
import com.itechvision.ecrobo.pickman.Models.NewPrinter.CheckPrinterResponse;
import com.itechvision.ecrobo.pickman.Models.NewPrinter.PrinterCategoryResult;
import com.itechvision.ecrobo.pickman.Models.NewPrinter.PrinterCategorydata;
import com.itechvision.ecrobo.pickman.Models.NewPrinter.SavePrinter.SavePrinter_Request;
import com.itechvision.ecrobo.pickman.Models.NewPrinter.Template.PrinterTempResult;
import com.itechvision.ecrobo.pickman.Models.NewPrinter.Template.PrinterTemplatedata;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.SlideMenu;
import com.itechvision.ecrobo.pickman.Util.Gettersetter;
import com.itechvision.ecrobo.pickman.Util.SharedPrefrences;
import com.itechvision.ecrobo.pickman.Util.U;
import com.itechvision.ecrobo.pickman.Util.UtilsMethods;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.reginald.editspinner.EditSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class NewprinterScreenActivity extends BaseActivity implements MainAsynListener<String>, DataManager.GetPinterCatcall, DataManager.PrinterTemplatecall, DataManager.SavePrintercall , DataManager.CheckPrintercall {

    @BindView(R.id.menubtn) ImageView menubtn;
    @BindView(R.id.et_categories) EditSpinner etCategories;
    @BindView(R.id.et_template) EditSpinner etTemplate;
    @BindView(R.id.et_machine) EditSpinner etMachine;
    @BindView(R.id.et_printer) EditSpinner etPrinter;

    SharedPreferences spDomain;
    String TAG = NewprinterScreenActivity.class.getSimpleName();
    ECRApplication app = new ECRApplication();
    public static final String DOMAINPREFERENCE = "domain";
    public String adminID = "", warehouse = "",CayegoryID="",TemplateId="",PrinterID="", MachineID = "", PrinterName= "";
    public boolean PrinterSelect = false;
    ArrayList<PrinterCategorydata> data;
    ArrayList<PrinterTemplatedata> Templatedata;
    DataManager manager ;
    progresBar progress;
    DataManager.PrinterTemplatecall tempcall;
    DataManager.SavePrintercall Savecall;
    DataManager.CheckPrintercall checkprint;
    static SlidingMenu menu;
    SlideMenu slidemenu;
    ArrayList<Gettersetter> arry;
    ArrayList<String> arrayList2;
    Gettersetter getset;
    public static int mRequestStatus = 0;
    public static final int REQ_MACHINE = 1;
    public static final int REQ_PRINTER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newprinter_screen);
        ButterKnife.bind(this);
        Log.d(TAG,"On Create ");

        data= new ArrayList<>();
        arry= new ArrayList<>();
        tempcall = this;
        Savecall = this;
        checkprint = this;
        menu = setSlidingMenu(this);
        slidemenu = new SlideMenu(menu, this);

        manager = new DataManager();
        progress = new progresBar(this);
        spDomain = getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        adminID = spDomain.getString("admin_id", null);
        warehouse = spDomain.getString("warehouse_id", null);


        if(!checkPrinterSelect()) {
            U.beepError(this,"(プリンター選択)画面でプリンターが選択されてます。");
        }
        else if(BaseActivity.getShopId().equalsIgnoreCase("")){
            U.beepError(this,"ショップを選択してください。");
        }
        else
        {
            progress.Show();
            manager.PrinterCategoty(adminID, app.getSerial(), this);
        }

        SharedPrefrences.set_AdminId(this,adminID);
        SharedPrefrences.set_authId(this,app.getSerial());
        SharedPrefrences.set_shopid(this,BaseActivity.getShopId());
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
    public void nextProcess() {
    }

    @OnClick(R.id.save) void onSaveClick() {
        //TODO implement
        if (CayegoryID.equalsIgnoreCase("")){
            U.beepError(this,"カテゴリーを選択してください");
            UtilsMethods.ShakeEditText(etCategories);
        }else if(TemplateId.equalsIgnoreCase("")){
            U.beepError(this,"テンプレートを選択してください");
            UtilsMethods.ShakeEditText(etTemplate);
        }else if (MachineID.equalsIgnoreCase("")){
            U.beepError(this,"プリンターを選択してください");
            UtilsMethods.ShakeEditText(etMachine);
        }else if (PrinterID.equalsIgnoreCase("")) {
            U.beepError(this, "プリンターを選択してください");
            UtilsMethods.ShakeEditText(etPrinter);
        }
        else
        {
            progress.Show();
            SavePrinter_Request req = new SavePrinter_Request(adminID, app.getSerial(), BaseActivity.getShopId(), TemplateId, PrinterID, etPrinter.getText().toString(), CayegoryID, MachineID);
            manager.SavePrinter(req, Savecall);
        }
    }

    @OnClick(R.id.savedprinter) void onSavedprinterClick() {

        Intent in = new Intent(this,NewsavedPrinterActivity.class);
        startActivity(in);

    }


    @OnClick(R.id.menubtn) void menu() {
        menu.showMenu();
    }


    @Override
    public void onPostSuccess(String result, int flag, boolean isSucess) {
        if (result != null) {
            try {
                if (flag == 1) {
                    Globals.jsonObj = new JSONObject(result);
                    String status = Globals.jsonObj.getString("code");
                    String message = Globals.jsonObj.getString("message");

                    if (status.equalsIgnoreCase("0")) {
                        JSONArray ja = Globals.jsonObj.getJSONArray("results");

                        if(mRequestStatus == REQ_MACHINE){

                            for (int arr = 0; arr < ja.length(); arr++) {
                                JSONObject json1 = ja.getJSONObject(arr);

                                String id = json1.getString("machine_id");

                                getset = new Gettersetter();
                                getset.setEventId(id);
                                arry.add(getset);

                            }
                            arrayList2 = new ArrayList<String>();

                            for (int i = 0; i < arry.size(); i++) {
                                arry.get(i).getEventId();
                                String a = /*data.get(i).getAp_form_category_id() + ": " +*/ arry.get(i).getEventId();
                                arrayList2.add(a);
                            }

                            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getApplicationContext(),spinner, arrayList2);
                            adapter3.setDropDownViewResource(spinner);
                            etMachine.setAdapter(adapter3);

                            etMachine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    MachineID = arrayList2.get(position);

                                    Globals.getterList = new ArrayList<>();
                                    Globals.getterList.add(new ParamsGetter("admin_id", adminID));
                                    Globals.getterList.add(new ParamsGetter("authId", app.getSerial()));
                                    Globals.getterList.add(new ParamsGetter("ap_machine_id", MachineID));

                                    mRequestStatus = REQ_PRINTER;

                                    PrinterID ="";
                                    PrinterSelect = false;
                                    etPrinter.setText("");

                                    // new MainAsyncTask(NewprinterScreenActivity.this, Globals.Webservice.NewPrinterlist,true, 1, NewprinterScreenActivity.this, "Form", Globals.getterList,false).execute();
                                    new MainAsyncTask(NewprinterScreenActivity.this, Globals.Webservice.NewPrinterlist, 1, NewprinterScreenActivity.this, "Form", Globals.getterList,false).execute();
                                }
                            });
                        }
                        else if(mRequestStatus ==REQ_PRINTER) {
                            arry= new ArrayList<Gettersetter>();
                            for (int arr = 0; arr < ja.length(); arr++) {
                                JSONObject json1 = ja.getJSONObject(arr);

                                String id = json1.getString("printer_id");
                                String name = json1.getString("printer_name");

                                Gettersetter  getsets = new Gettersetter();
                                getsets.setStrCName(name);
                                getsets.setStrEventId(id);

                                arry.add(getsets);
                            }
                            ArrayList<String> arrayList2 = new ArrayList<String>();

                            for (int i = 0; i < arry.size(); i++) {
                                arry.get(i).getStrEventId();
                                arry.get(i).getStrCName();
                                String a = /*data.get(i).getAp_form_category_id() + ": " +*/ arry.get(i).getStrCName();
                                arrayList2.add(a);

                            }

                            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getApplicationContext(),spinner, arrayList2);
                            adapter3.setDropDownViewResource(spinner);
                            etPrinter.setAdapter(adapter3);

                            etPrinter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    PrinterID = arry.get(position).getStrEventId();
                                    //      Toast.makeText(NewprinterScreenActivity.this,arry.get(position).getStrEventId(),Toast.LENGTH_SHORT).show();

                                }
                            });

                            if(PrinterSelect){
                                etPrinter.setText(PrinterName);
                                PrinterSelect = false;
                            }
                        }
                    }else if (message.equalsIgnoreCase("1020")){
                        new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                                .setTitle("Error!")
                                .setMessage(message)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent in = new Intent(NewprinterScreenActivity.this, LoginActivity.class);
                                        startActivity(in);
                                        finish();
                                    }
                                })

                                .show();
                    }else {
                        U.beepError(this,message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onPostError(int flag) {
    }

    @Override
    public void onSucess(int status, PrinterCategoryResult message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")){
            data = message.getResults();

            ArrayList<String> arrayList = new ArrayList<String>();

            for (int i = 0; i < data.size(); i++) {
                data.get(i).getAp_form_category_id();
                data.get(i).getName();

                Log.e("qwertyui", data.get(i).getName());
                String a = /*data.get(i).getAp_form_category_id() + ": " +*/ data.get(i).getName();
                arrayList.add(a);
            }

            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), spinner, arrayList);
            adapter1.setDropDownViewResource(spinner);
            etCategories.setAdapter(adapter1);

            etCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    CayegoryID = data.get(position).getAp_form_category_id();
                    //   Toast.makeText(NewprinterScreenActivity.this,data.get(position).getAp_form_category_id(),Toast.LENGTH_SHORT).show();
                    progress.Show();
                    manager.PrinterTemplate(adminID,app.getSerial(),CayegoryID,BaseActivity.getShopId(),tempcall);

                }
            });

            Globals.getterList = new ArrayList<>();
            Globals.getterList.add(new ParamsGetter("admin_id",adminID));
            Globals.getterList.add(new ParamsGetter("authId",app.getSerial()));

            mRequestStatus = REQ_MACHINE;

            new MainAsyncTask(this, Globals.Webservice.NewMachinelist, 1, NewprinterScreenActivity.this, "Form", Globals.getterList,false).execute();
            //     new MainAsyncTask(this, Globals.Webservice.NewPrinterlist,true, 1, NewprinterScreenActivity.this, "Form", Globals.getterList,false).execute();

        }else if (message.getCode().equalsIgnoreCase("1020")){
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(NewprinterScreenActivity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })

                    .show();
        }else {
            U.beepError(this,message.getMessage());
        }

    }

    @Override
    public void onSucess(int status, final PrinterTempResult message) throws JsonIOException {
        progress.Dismiss();

        if (message.getCode().equalsIgnoreCase("0")){

            Templatedata = message.getResults();
            ArrayList<String> arrayList1 = new ArrayList<String>();

            for (int i = 0; i < Templatedata.size(); i++) {
                Templatedata.get(i).getAp_form_id();
                Templatedata.get(i).getName();

                String a = /*data.get(i).getAp_form_category_id() + ": " +*/Templatedata.get(i).getName();
                arrayList1.add(a);
            }

            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), spinner, arrayList1);
            adapter2.setDropDownViewResource(spinner);
            etTemplate.setAdapter(adapter2);

            etTemplate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TemplateId = Templatedata.get(position).getAp_form_id();
                    progress.Show();
                    manager.CheckPrinter(adminID,app.getSerial(),BaseActivity.getShopId(),TemplateId,checkprint);

                }
            });

        }else if (message.getCode().equalsIgnoreCase("1020")){
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(NewprinterScreenActivity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })

                    .show();
        }else {
            U.beepError(this,message.getMessage());
        }

    }

    @Override
    public void onSucess(int status, ResponseBody message) throws JsonIOException {
        progress.Dismiss();
        if (status==200) {
            etTemplate.setText("");
            etPrinter.setText("");
            etCategories.setText("");
            etMachine.setText("");
        }
    }

    @Override
    public void onSucess(int status, CheckPrinterResponse message) throws JsonIOException {
        progress.Dismiss();
        if (message.getCode().equalsIgnoreCase("0")){

            if (message.getPrinter_id().equalsIgnoreCase("") || message.getMachine_id().equalsIgnoreCase("")){
                etPrinter.setText("");
                etMachine.setText("");
                PrinterSelect = false;
            }else {
                etMachine.setText(message.getMachine_id());
                MachineID = message.getMachine_id();

                etPrinter.setText(message.getPrinter_name());
                PrinterID = message.getPrinter_id();
                PrinterName = message.getPrinter_name();
                MachineID = message.getMachine_id();
                PrinterSelect = true;

                Globals.getterList = new ArrayList<>();
                Globals.getterList.add(new ParamsGetter("admin_id",adminID));
                Globals.getterList.add(new ParamsGetter("authId",app.getSerial()));
                Globals.getterList.add(new ParamsGetter("ap_machine_id",MachineID));

                mRequestStatus = REQ_PRINTER;

//                new MainAsyncTask(NewprinterScreenActivity.this, Globals.Webservice.NewPrinterlist,true, 1, NewprinterScreenActivity.this, "Form", Globals.getterList,false).execute();
                new MainAsyncTask(NewprinterScreenActivity.this, Globals.Webservice.NewPrinterlist, 1, NewprinterScreenActivity.this, "Form", Globals.getterList,false).execute();

            }
        }else if (message.getCode().equalsIgnoreCase("1020")){
            new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(NewprinterScreenActivity.this, LoginActivity.class);
                            startActivity(in);
                            finish();
                        }
                    })

                    .show();
        }else {
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
    }

    @Override
    public void onNetworkFailure() {
        progress.Dismiss();
    }

}
