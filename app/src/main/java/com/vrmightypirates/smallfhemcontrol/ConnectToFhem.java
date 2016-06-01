package com.vrmightypirates.smallfhemcontrol;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Boke on 15.05.2016.
 */
public class ConnectToFhem implements CommunicateWithFhemTelnet.OnMassageFromFhem {

    private static final String TAG = ConnectToFhem.class.getSimpleName();

    CommunicateWithFhemHttp communicateWithFhemHttp = null;
    CommunicateWithFhemTelnet communicateWithFhemTelnet = null;
    private boolean autoUpdateIsRunning = false;
    private ArrayList<FhemDevice> deviceList;
    private  FhemMessageParser fhemParser = new FhemMessageParser();
    FhemDevice device = null;

    public ConnectToFhem() {

    }

    public ConnectToFhem(FhemDevice device) {
        this.device = device;

    }



    public boolean sendMessage(String message, ConnectionType connectionType) {
        Log.i(TAG, "setDesireTemperatureInFhem: " +  message);
        switch (connectionType) {
            case http:
                break;
            case telnet:
                Log.i(TAG, "setDesireTemperatureInFhem: " +  message);
                communicateWithFhemTelnet = new CommunicateWithFhemTelnet(this, message.toString(),true);


                break;
            default:
                return false;
        }

        return true;
    }


    public boolean autoUpdateAllDevices(ArrayList<FhemDevice> deviceList, ConnectionType connectionType) {

        this.deviceList = deviceList;
        StringBuilder message = new StringBuilder();

        if(autoUpdateIsRunning == true){
            communicateWithFhemTelnet.cancel(true);
        }

        message.append("inform on ");

        for (FhemDevice object: deviceList) {
           message.append(object.getDeviceName());
        }

        Log.i(TAG, "autoUpdateAllDevices: "+ message);
        switch (connectionType) {
            case http:
                break;
            case telnet:
                communicateWithFhemTelnet = new CommunicateWithFhemTelnet(this, message.toString(),false);
                break;
            default:
                return false;
        }

        autoUpdateIsRunning = true;
        return true;
    }

    private boolean parseFhemMessage(String messageFromFhem, boolean singleResponse){

        if(singleResponse){

            fhemParser.parseMessageForSingleDevice(messageFromFhem,this.device);

        }else if(deviceList != null){
            fhemParser.parseMessage(messageFromFhem,this.deviceList);
        }

        return true;

    }

    public FhemMessageParser getFhemParser() {
        return fhemParser;
    }

    @Override
    public void onMessageFromFhemReceived(String messageFromFhem, boolean singleResponse) {
        parseFhemMessage(messageFromFhem, singleResponse);
        Log.i(TAG, "onMessageFromFhemReceived: "+messageFromFhem);

    }
}
