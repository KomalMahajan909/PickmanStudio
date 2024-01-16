package com.itechvision.ecrobo.pickman.Fragments;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gigatms.BaseDevice;
import com.gigatms.BaseScanner;
import com.gigatms.CommunicationType;
import com.gigatms.ScanDebugCallback;
import com.gigatms.ScannerCallback;
import com.gigatms.tools.Glog;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.RFBarcodePrinterActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.RFIDArrivalActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.RFID_ReturnActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.RFReadWriteActivity;
import com.itechvision.ecrobo.pickman.Chatman.Arrivals.RFTagCheckActivity;
import com.itechvision.ecrobo.pickman.Chatman.RFID.RFWriterActivity;
import com.itechvision.ecrobo.pickman.Chatman.ConnectedDevices;
import com.itechvision.ecrobo.pickman.Chatman.RFDeviceBaseScan;
import com.itechvision.ecrobo.pickman.Chatman.Ship.RFPickingActivity;
import com.itechvision.ecrobo.pickman.R;

import static com.gigatms.CommunicationType.BLE;
import static com.gigatms.CommunicationType.TCP;
import static com.gigatms.CommunicationType.UDP;

/**
 * Created by lenovo on 6/19/2019.
 */

public abstract class BaseScanFragment  extends Fragment implements ScannerCallback, ScanDebugCallback
        , View.OnClickListener {
    private static final String TAG = BaseScanFragment.class.getSimpleName();
    public static final String DEBUG = "DEBUG";
    private Button mBtnDebug;
    private Button mBtnDebugClearLog;
    private boolean mDebugMode = false;
    private boolean mFirstClick = true;
    private int mClickDebugCount;
    long mTime, mTemp = 0;
    private TextView mTvLog;
    private Guideline mGuideLine;
    private ScrollView mScrollView;
    protected BaseScanner mBaseScanner;
    public DevicesAdapter mDevicesAdapter;
    private FloatingActionButton mFabDeviceScan;
    private RecyclerView mRecyclerView;
    private SharedPreferences mSharedPreferences;
    SharedPreferences spDomain;
    public static final String DOMAINPREFERENCE = "domain";
    public abstract BaseScanner newScanner();

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_scan, container, false);
        mBaseScanner = newScanner();
        mBaseScanner.setScanDebugCallback(this);
        findViews(view);
        spDomain = getActivity().getSharedPreferences(DOMAINPREFERENCE, Context.MODE_PRIVATE);
        return view;
    }

    private void setViews() {
        getActivity().setTitle(getString(R.string.app_name));
        setDebugViews();
        initRecyclerView();
    }

    private void setDebugViews() {
        mSharedPreferences = getActivity().getSharedPreferences("Debug", Context.MODE_PRIVATE);
        mDebugMode = mSharedPreferences.getBoolean(DEBUG, false);
        mBtnDebug.setOnClickListener(this);
        mBtnDebugClearLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvLog.setText("");
            }
        });
        setBtnClearEnable();
        mFabDeviceScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDevicesAdapter.clear();
                mDevicesAdapter.notifyDataSetChanged();
                if (isCommunicationEnable()) {
                    scan();
                } else {
                    showAlert("Please Open " + (mBaseScanner.getCurrentCommunicationType() == BLE ? "BLE" : "Wi-Fi") + "!");
                }
            }
        });
    }private void showAlert(String s) {
        Log.d(TAG, "showAlert: " + s);
        new AlertDialog.Builder(getContext())
                .setTitle(s)
                .setPositiveButton("OK", null)
                .show();
    }


    private void initRecyclerView() {
        initDevicesAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setAdapter(mDevicesAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
        linearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void initDevicesAdapter() {

        mDevicesAdapter = new DevicesAdapter(getContext());
        mDevicesAdapter.setControlCallback(new DevicesAdapter.OnControlCallback() {
            @Override
            public void onControlClicked(BaseDevice baseDevice) {
                SharedPreferences.Editor editor = spDomain.edit();
                editor.putString("MacAddress",baseDevice.getDeviceMacAddr());
                editor.commit();
                Log.e(TAG,"MAC   "+baseDevice.getDeviceMacAddr());
                if(RFDeviceBaseScan.rftype.equals("arrival")){
                    Intent inti =new Intent(getActivity(), RFIDArrivalActivity.class);
                inti.putExtra(RFIDArrivalActivity.EXTRAS_DEVICE_ADDRESS,baseDevice.getDeviceMacAddr());
                startActivity(inti);}
                else if(RFDeviceBaseScan.rftype.equals("return")){
                    Intent inti = new Intent(getActivity(), RFID_ReturnActivity.class);
                   inti.putExtra(RFID_ReturnActivity.EXTRAS_DEVICE_ADDRESS,baseDevice.getDeviceMacAddr());
                startActivity(inti);}
                else if(RFDeviceBaseScan.rftype.equals("printer"))
                {
                    Intent inti = new Intent(getActivity(), RFBarcodePrinterActivity.class);
                    inti.putExtra(RFBarcodePrinterActivity.EXTRAS_DEVICE_ADDRESS,baseDevice.getDeviceMacAddr());
                    startActivity(inti);
                }
                else if(RFDeviceBaseScan.rftype.equals("check"))
                {
                    Intent inti = new Intent(getActivity(), RFTagCheckActivity.class);
                    inti.putExtra(RFTagCheckActivity.EXTRAS_DEVICE_ADDRESS,baseDevice.getDeviceMacAddr());
                    startActivity(inti);
                }
                else if(RFDeviceBaseScan.rftype.equals("readWrite"))
                {
                    Intent inti = new Intent(getActivity(), RFReadWriteActivity.class);
                    inti.putExtra(RFReadWriteActivity.EXTRAS_DEVICE_ADDRESS,baseDevice.getDeviceMacAddr());
                    startActivity(inti);
                }

                else if(RFDeviceBaseScan.rftype.equals("picking"))
                {
                    Intent inti = new Intent(getActivity(), RFPickingActivity.class);
                    inti.putExtra(RFPickingActivity.EXTRAS_DEVICE_ADDRESS,baseDevice.getDeviceMacAddr());
                    startActivity(inti);
                }
                else if(RFDeviceBaseScan.rftype.equals("write"))
                {
                    Intent inti = new Intent(getActivity(), RFWriterActivity.class);
                    inti.putExtra(RFWriterActivity.EXTRAS_DEVICE_ADDRESS,baseDevice.getDeviceMacAddr());
                    startActivity(inti);
                }
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, DeviceControlFragment.newFragment(baseDevice.getDeviceMacAddr()))
//                        .addToBackStack(null)
//                        .commit();
            }
        });
    }

    private void findViews(View view) {
        mBtnDebug = view.findViewById(R.id.btn_debug);
        mBtnDebugClearLog = view.findViewById(R.id.btn_clear);
        mScrollView = view.findViewById(R.id.scrollView);
        mTvLog = view.findViewById(R.id.tv_log);
        mGuideLine = view.findViewById(R.id.guideline);
        mRecyclerView = view.findViewById(R.id.device_list);
        mFabDeviceScan = view.findViewById(R.id.fab_scan_device);
    }
    private void setBtnClearEnable() {
        if (mDebugMode) {
            mBtnDebugClearLog.setEnabled(true);
            mBtnDebugClearLog.setVisibility(View.VISIBLE);
            mScrollView.setVisibility(View.VISIBLE);
            mTvLog.setVisibility(View.VISIBLE);
            setGuidePercent(0.35f);
        } else {
            mBtnDebugClearLog.setEnabled(false);
            mBtnDebugClearLog.setVisibility(View.GONE);
            mScrollView.setVisibility(View.GONE);
            mTvLog.setVisibility(View.GONE);
            setGuidePercent(0f);
        }
    }

    private void scan() {
        mDevicesAdapter.notifyDataSetChanged();
        mBaseScanner.startScan();
    }

    @Override
    public void onResume() {
        super.onResume();
        setViews();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        if (mBaseScanner != null && isCommunicationEnable()) {
            mBaseScanner.stopScan();
        }
        mDevicesAdapter.notifyDataSetChanged();
    }
    private void setGuidePercent(float percent) {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mGuideLine.getLayoutParams();
        params.guidePercent = percent;
        mGuideLine.setLayoutParams(params);
    }

    protected boolean isCommunicationEnable() {
        CommunicationType type = mBaseScanner.getCurrentCommunicationType();
        switch (type) {
            case UDP:
                WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if (wifiManager.isWifiEnabled()) {
                    return true;
                } else {
                    return false;
                }
            case BLE:
                BluetoothManager bleManager = (BluetoothManager) getContext().getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
                BluetoothAdapter bluetoothAdapter = bleManager.getAdapter();
                if (bluetoothAdapter.isEnabled()) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }



    @Override
    public void onClick(View v) {
        //Debug mode
        mTime = System.currentTimeMillis();
        if (mFirstClick) {
            mClickDebugCount++;
            mTemp = System.currentTimeMillis();
            mFirstClick = false;
        } else {
            Log.d(TAG, "onClick: mTime - mTemp: " + (mTime - mTemp));
            if (mTime - mTemp < 2000) {
                mTemp = mTime;
                mClickDebugCount++;
                if (mClickDebugCount == 10) {
                    mDebugMode = !mDebugMode;
                    mSharedPreferences.edit().putBoolean(DEBUG, mDebugMode).apply();
                    setBtnClearEnable();
                    mTvLog.setText("");
                    clearDebugCountData();

                }
            } else {
                clearDebugCountData();
            }
        }
        Log.d(TAG, "onClick: " +
                "\nmTime:" + mTime
                + "\nmTemp:" + mTemp
                + "\nclick debug count: " + mClickDebugCount
                + "\nfirst click: " + mFirstClick);
    }

    @Override
    public void didSend(byte[] data, String s) {
        updateLog("ID: " + s
                + "\nTX[" + data.length + "]: " + Glog.bytesToHexString(data, " "), mDebugMode);
    }

    @Override
    public void didReceive(byte[] data, String s) {
        updateLog("ID: " + s
                + "\nRX[" + data.length + "]: " + Glog.bytesToHexString(data, " ") + "\n", mDebugMode);
    }

    @Override
    public void didScanStop() {
        Log.d(TAG, "didScanStop: ");
    }

    @Override
    public void didDiscoveredDevice(final BaseDevice baseDevice) {
        Log.d(TAG, "didDiscoveredDevice: ");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDevicesAdapter.addDevice(baseDevice);
            }
        });
    }
    protected abstract CommunicationType getCurrentCommunicationType();

    protected void addConnectedDeviceToList() {
        Log.d(TAG, "addConnectedDeviceToList: ");
        CommunicationType currentType = getCurrentCommunicationType();
        ConnectedDevices connectedDevices = ConnectedDevices.getInstance();
        for (String key : connectedDevices.keySet()) {
            Log.d(TAG, "addConnectedDeviceToList: ");
            BaseDevice baseDevice = connectedDevices.get(key);
            CommunicationType baseDeviceCommunicationType = baseDevice.getCommunicationType();
            if (currentType == UDP) {
                currentType = TCP;
            }
            if (baseDeviceCommunicationType == currentType) {
                mDevicesAdapter.addDevice(baseDevice);
                Log.d(TAG, "addConnectedDeviceToList: " + baseDevice.getDeviceMacAddr());
            }
        }
        if (connectedDevices.size() > 0) {
            mDevicesAdapter.notifyDataSetChanged();
        }
    }

    private void clearDebugCountData() {
        mFirstClick = true;
        mClickDebugCount = 0;
        mTime = 0;
        mTemp = 0;
    }
    private void updateLog(@NonNull final String message, final boolean debug) {
        if (debug && getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTvLog.append(message + "\n");
                    mScrollView.post(new Runnable() {
                        public void run() {
                            mScrollView.fullScroll(View.FOCUS_DOWN);
                        }
                    });

                }
            });
        }
    }
}
