package com.vrmightypirates.smallfhemcontrol;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Boke on 15.05.2016.
 */
public class API {

    private static final String TAG = API.class.getSimpleName() ;
    ConnectToFhem connectionToFhem = null;
    ArrayList<FhemDevice> deviceList = new ArrayList<FhemDevice>();

    public API(){
        connectionToFhem = new ConnectToFhem();
    }


    public FhemDevice createDevice(String deviceName, DeviceType deviceType, int widget){

        FhemDevice device = null;

        switch (deviceType) {
            case HeaterMax:
                device = new FhemDevice(deviceName, deviceType, widget);
                Log.i(TAG, "createDevice: " + deviceName);
                break;
            case HeaterHomematic:
                break;
            case Sonos:
                break;
            default:
                Log.i(TAG, "createDevice: No such Device!");
        }

        return device;
    }

    public boolean addDeviceToUpdateListener(FhemDevice device) {

        deviceList.add(device);
        return true;
    }

    public boolean startAutoUpdate(ConnectionType connectionType) {

        connectionToFhem.autoUpdateAllDevices(this.deviceList, connectionType);
        return true;
    }


    public FhemMessageParser getParser() {

        return   connectionToFhem.getFhemParser();
    }

}
