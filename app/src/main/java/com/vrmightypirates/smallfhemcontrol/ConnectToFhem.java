package com.vrmightypirates.smallfhemcontrol;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Boke on 15.05.2016.
 */
public class ConnectToFhem implements CommunicateWithFhemTelnet.OnMassageFromFhem {

    private static final String TAG = ConnectToFhem.class.getSimpleName();
    ConnectionType connectionType = ConnectionType.telnet;
    CommunicateWithFhemHttp communicateWithFhemHttp = null;
    CommunicateWithFhemTelnet communicateWithFhemTelnet = null;
    private boolean autoUpdateIsRunning = false;
    private ArrayList<FhemDevice> deviceList;

    private  FhemMessageParser fhemParser = new FhemMessageParser();

    public boolean disconnect(String device){

        switch (connectionType) {
            case http:
                communicateWithFhemHttp.closeConnection();
                break;
            case telnet:
              //  connectionTelnet.cancel(true);
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
               // connectionTelnet.sendMessage(message);
                break;
            default:
                return false;
        }

        return true;
    }

    public boolean getComand(String device){

        return true;
    }



    public boolean autoUpdateAllDevices(ArrayList<FhemDevice> deviceList) {

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
                communicateWithFhemTelnet = new CommunicateWithFhemTelnet(this, message.toString());
                break;
            default:
                return false;
        }

        autoUpdateIsRunning = true;
        return true;
    }

    private boolean parseFhemMessage(String messageFromFhem){

        if(deviceList != null){
            fhemParser.parseMessage(messageFromFhem,deviceList);
        }else{
            return false;
        }

        return true;

    }

    public FhemMessageParser getFhemParser() {
        return fhemParser;
    }

    @Override
    public void onMessageFromFhemReceived(String messageFromFhem) {
        parseFhemMessage(messageFromFhem);
        Log.i(TAG, "onMessageFromFhemReceived: "+messageFromFhem);
    }
}
