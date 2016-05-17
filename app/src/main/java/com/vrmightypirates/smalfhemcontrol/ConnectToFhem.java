package com.vrmightypirates.smalfhemcontrol;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Boke on 15.05.2016.
 */
public class ConnectToFhem {

    private static final String TAG = ConnectToFhem.class.getSimpleName();
    ConnectionType connectionType;
    ConnectionHttp connectionHttp = null;
    ConnectionTelnet connectionTelnet = null;
    ArrayList<FhemDevice> deviceList = new ArrayList<FhemDevice>();

    public boolean connect(ConnectionType connectionType){

        ConnectionTelnet connectionTelnet = null;
        this.connectionType = connectionType;

        switch (connectionType) {
            case http:
                ConnectionHttp connectionHttp = new ConnectionHttp();
                break;
            case telnet:
                connectionTelnet = new ConnectionTelnet();
                connectionTelnet.execute();
                break;
            default:
                return false;
        }

        return true;
    }

    public boolean disconnect(String device){

        switch (connectionType) {
            case http:
                connectionHttp.closeConnection();
                break;
            case telnet:
                connectionTelnet.cancel(true);
                break;
            default:
                return false;
        }

        return true;
    }

    public boolean sendMessage(String message){

        switch (connectionType) {
            case http:
                break;
            case telnet:
                connectionTelnet.sendMessage(message);
                break;
            default:
                return false;
        }

        return true;
    }

    public boolean getComand(String device){

        return true;
    }

    public boolean addDeviceToListener(String deviceName, DeviceType deviceType, Object widget) {

        switch (deviceType) {
            case HeaterMax:
                deviceList.add(new DeviceHeaterMax(deviceName, widget));
                Log.i(TAG, "addDeviceToListener: " + deviceName);
                break;
            case HeaterHomematic:
                break;
            case Sonos:
                break;
            default:
                return false;
        }

        autoUpdateAllDevices(deviceList);


        return true;
    }

    private boolean autoUpdateAllDevices(ArrayList<FhemDevice> deviceList) {

        StringBuilder message = new StringBuilder();

        message.append("inform ");

        for (FhemDevice object: deviceList) {
           message.append(object.getDeviceName());
        }

        Log.i(TAG, "autoUpdateAllDevices: "+ message);
        switch (connectionType) {
            case http:
                break;
            case telnet:
                connectionTelnet.sendMessage("inform " +message);
                break;
            default:
                return false;
        }

        return true;
    }


}
