package com.itechvision.ecrobo.pickman.Chatman;

import com.gigatms.BaseDevice;

import java.util.HashMap;
import java.util.Set;


public class ConnectedDevices extends HashMap<String, BaseDevice> {
    private static volatile ConnectedDevices instance;

    private ConnectedDevices() {
    }

    public static ConnectedDevices getInstance() {
        if (instance == null) {
            synchronized (ConnectedDevices.class) {
                if (instance == null) {
                    instance = new ConnectedDevices();
                }
            }
        }
        return instance;
    }

    @Override
    public void clear() {
        Set<String> strings = instance.keySet();
        for (String deviceMac : strings) {
            instance.get(deviceMac).disconnect();
            instance.get(deviceMac).destroy();
        }
        super.clear();
    }
}
