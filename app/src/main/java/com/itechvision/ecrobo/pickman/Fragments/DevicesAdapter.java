package com.itechvision.ecrobo.pickman.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.gigatms.BaseDevice;
import com.gigatms.CommunicationCallback;
import com.gigatms.CommunicationType;
import com.gigatms.ConnectionState;
import com.itechvision.ecrobo.pickman.Chatman.ConnectedDevices;
import com.itechvision.ecrobo.pickman.R;

import java.util.ArrayList;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.ViewHolder> {

    private static final String TAG = DevicesAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<BaseDevice> mDevices;
    private OnControlCallback mControlCallback;
    private Handler mHandler;

    interface OnControlCallback {
        void onControlClicked(BaseDevice baseDevice);
    }

    public DevicesAdapter(Context mContext) {
        this.mContext = mContext;
        mDevices = new ArrayList<>();
        mHandler = new Handler();
    }

    public void setControlCallback(OnControlCallback mControlCallback) {
        this.mControlCallback = mControlCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_device, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        BaseDevice device = mDevices.get(i);
        viewHolder.bind(device);
    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }

    public synchronized void addDevice(BaseDevice device) {
        Log.d(TAG, "mDevices list: size: " + mDevices.size());
        if (!mDevices.contains(device)) {
            Log.d(TAG, "addDevice: 1");
            mDevices.add(device);
            notifyDataSetChanged();
        } else {
            Log.d(TAG, "addDevice: 2");
            int i = mDevices.indexOf(device);
            Log.d(TAG, "index: " + i);

            BaseDevice currentDevice = mDevices.get(i);
        }
    }

    public void clear() {
        for (BaseDevice device : mDevices) {
            device.disconnect();
            device.destroy();
        }
        mDevices.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements CommunicationCallback {

        private TextView deviceName;
        private TextView macAddress;
        private TextView connectState;
        private Button btnConnect;
        private Button btnAccount;
        private BaseDevice device;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
        }

        private void findViews(@NonNull View itemView) {
            deviceName = itemView.findViewById(R.id.tv_connection_status);
            macAddress = itemView.findViewById(R.id.tv_mac_address);
            connectState = itemView.findViewById(R.id.tv_status);
            btnConnect = itemView.findViewById(R.id.btn_connect);
            btnAccount = itemView.findViewById(R.id.btn_account);
        }


        private void bind(final BaseDevice device) {
            Log.d(TAG, "bind: ");
            this.device = device;
            this.device.setCommunicationCallback(this);
            deviceName.setText(device.getDeviceName());
            macAddress.setText(device.getDeviceMacAddr());
            connectState.setText(device.getConnectionState().name());
            btnConnect.setEnabled(!device.getConnectionState().equals(ConnectionState.CONNECTING));
            btnConnect.setText(device.getConnectionState().equals(ConnectionState.DISCONNECTED) ? "Connect" : "Disconnect");
            btnConnect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (device.getConnectionState().equals(ConnectionState.DISCONNECTED)) {
                        new AlertDialog.Builder(mContext)
                                .setTitle("Please choose the way of connection the UHF.")
                                .create();
                        device.connect();
                    } else {
                        device.disconnect();
                    }
                }
            });
            btnAccount.setEnabled(device.getConnectionState().equals(ConnectionState.CONNECTED));
            btnAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mControlCallback != null) {
                        mControlCallback.onControlClicked(device);
                    }
                }
            });

            ConnectedDevices devices = ConnectedDevices.getInstance();
            if (device.getConnectionState().equals(ConnectionState.CONNECTED)) {
                Log.d(TAG, "didUpdateConnection: add to list \n" + device.getDeviceMacAddr());
                devices.put(device.getDeviceMacAddr(), device);
            } else {
                Log.d(TAG, "didUpdateConnection: remove to ConnectedDevices \n" + device.getDeviceMacAddr());
                devices.remove(device.getDeviceMacAddr());
            }
        }

        private void repaintView() {
            Log.d(TAG, "repaintView: ");
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run: " + device.getConnectionState());
                    int index = mDevices.indexOf(device);
                    notifyItemChanged(index);
                }
            });
        }

        @Override
        public void didUpdateConnection(ConnectionState connectedState, CommunicationType type) {
            Log.d(TAG, "didUpdateConnection: ");
            repaintView();
        }
    }
}