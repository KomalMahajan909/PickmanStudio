package com.itechvision.ecrobo.pickman.Chatman.PickWork.NewPrinter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.JsonIOException;
import com.itechvision.ecrobo.pickman.Adapter.Adap_selectedprinterlist;
import com.itechvision.ecrobo.pickman.Chatman.Account.LoginActivity;
import com.itechvision.ecrobo.pickman.Models.NewPrinter.SelectedprinterList.SelectedPrinterResult;
import com.itechvision.ecrobo.pickman.Models.NewPrinter.SelectedprinterList.selectedlistdata;
import com.itechvision.ecrobo.pickman.R;
import com.itechvision.ecrobo.pickman.ServerRetro.DataManager;
import com.itechvision.ecrobo.pickman.ServerRetro.progresBar;
import com.itechvision.ecrobo.pickman.Util.SharedPrefrences;
import com.itechvision.ecrobo.pickman.Util.U;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class NewsavedPrinterActivity extends Activity  implements DataManager.SelectedPrinter_listcall, DataManager.Delete_SelectedPrintercall {

    @BindView(R.id.back) ImageView back;
    @BindView(R.id.listView) SwipeMenuListView listView;
    @BindView(R.id.error)
    TextView error;
    DataManager manager ;
    progresBar progress;
    Adap_selectedprinterlist adap ;
    ArrayList<selectedlistdata> array;
    DataManager.Delete_SelectedPrintercall delete;
    String TAG = NewsavedPrinterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsaved_printer);
        ButterKnife.bind(this);
        manager = new DataManager();
        progress = new progresBar(this);
        array = new ArrayList<>();
        delete = this;
        Log.d(TAG,"On Create ");

        progress.Show();
        manager.SelectedPrinter_list(SharedPrefrences.get_AdminId(this),SharedPrefrences.get_authId(this),SharedPrefrences.get_shopid(this),this);

    }


    @OnClick(R.id.back) void setback(){
        finish();
    }

    @Override
    public void onSucess(int status, SelectedPrinterResult message) throws JsonIOException {
        progress.Dismiss();

        if (message.getCode().equalsIgnoreCase("0")){

            array = message.getResults();

            if (message.getResults().size()!=0) {
                error.setVisibility(View.GONE);
                adap = new Adap_selectedprinterlist(NewsavedPrinterActivity.this, message.getResults());
                listView.setAdapter(adap);
                adap.notifyDataSetChanged();
                SwipeList();
            }else{
                error.setVisibility(View.VISIBLE);
                adap = new Adap_selectedprinterlist(NewsavedPrinterActivity.this, message.getResults());
                listView.setAdapter(adap);
                adap.notifyDataSetChanged();
            }
        }else if (message.getCode().equalsIgnoreCase("1020")){
                    new android.app.AlertDialog.Builder(this, R.style.DialogThemee)
                    .setTitle("Error!")
                    .setMessage(message.getMessage())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent in = new Intent(NewsavedPrinterActivity.this, LoginActivity.class);
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
        if (status==200){
             manager.SelectedPrinter_list(SharedPrefrences.get_AdminId(this),SharedPrefrences.get_authId(this),SharedPrefrences.get_shopid(this),this);
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

    private void SwipeList() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.parseColor("#d71b1b")));
                // set item width
                deleteItem.setWidth(dp2px(90));
                deleteItem.setTitle("Delete");
                deleteItem.setTitleSize(18);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // set a icon
                // deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {

                    case 0:

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NewsavedPrinterActivity.this);
                        alertDialogBuilder.setMessage("Are you sure you want to delete this entry?");
                        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        progress.Show();
                        manager.Delete_SelectedPrinter(SharedPrefrences.get_AdminId(NewsavedPrinterActivity.this),
                        SharedPrefrences.get_authId(NewsavedPrinterActivity.this),SharedPrefrences.get_shopid(NewsavedPrinterActivity.this)
                        ,array.get(position).getId(),delete);

                                    }
                        });

                        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                        break;

                    case 1:
                        // delete

                        break;
                }
                return false;
            }
        });

            // set SwipeListener
            listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {

            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });

        // set MenuStateChangeListener
        listView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });

           // other setting
          // listView.setCloseInterpolator(new BounceInterpolator());
         // test item long click
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //  Toast.makeText(this, position + " long click", Toast.LENGTH_SHORT).show();
            return false;

            }
        });

    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
