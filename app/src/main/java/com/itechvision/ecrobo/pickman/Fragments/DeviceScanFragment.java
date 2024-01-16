package com.itechvision.ecrobo.pickman.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gigatms.BaseScanner;
import com.gigatms.CommunicationType;
import com.gigatms.ScannerCallback;
import com.gigatms.TS100Scanner;
import com.itechvision.ecrobo.pickman.R;

import static com.gigatms.CommunicationType.BLE;
import static com.gigatms.CommunicationType.UDP;

/**
 * Created by lenovo on 6/19/2019.
 */

public class DeviceScanFragment extends BaseScanFragment implements ScannerCallback {

    private static final String TAG = DeviceScanFragment.class.getSimpleName();
    private final int REQUEST_COARSE_LOCATION = 99;
    private CommunicationType currentCommunicationType;

    @Override
    public BaseScanner newScanner() {
        return new TS100Scanner(getContext(), this, BLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestNeededPermissions();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_device_scan, menu);
        currentCommunicationType = BLE;
        menu.getItem(1).setChecked(true);
        addConnectedDeviceToList();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.menu_wifi:
                currentCommunicationType = UDP;
                break;
            case R.id.menu_ble:
                currentCommunicationType = BLE;
                break;
            default:
                break;
        }
        mBaseScanner.setCommunicationType(currentCommunicationType);
        Log.d(TAG, "onOptionsItemSelected: ");
        mDevicesAdapter.clear();
        Log.d(TAG, "onOptionsItemSelected: ");
        mDevicesAdapter.notifyDataSetChanged();
        addConnectedDeviceToList();
        return super.onOptionsItemSelected(item);
    }

    public void requestNeededPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_COARSE_LOCATION) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected CommunicationType getCurrentCommunicationType() {
        return currentCommunicationType;
    }
}

