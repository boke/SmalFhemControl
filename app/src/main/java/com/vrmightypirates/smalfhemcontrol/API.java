package com.vrmightypirates.smalfhemcontrol;

/**
 * Created by Boke on 15.05.2016.
 */
public class API implements LooperThread.OnNewMessageFromFhemListener {

    ConnectToFhem connectionToFhem = null;

    public boolean initConnection(ConnectionType connectionType){

        connectionToFhem = new ConnectToFhem();
        connectionToFhem.connect(ConnectionType.telnet);//connectionType

        return true;
    }

    public boolean setHeat(String device, float temperature){
        String command = "set " +device + " desiredTemperature "+ temperature;
        connectionToFhem.sendMessage(command);
        return true;
    }

    public boolean getHeat(String device){



        return true;
    }

    public boolean getAutoUpdate(String device, DeviceType deviceType, Object widget){

        connectionToFhem.addDeviceToListener(device, deviceType, widget);

        return true;
    }

    public boolean play(String device){

        return true;
    }

    public boolean stop(String device){

        return true;
    }

    public boolean skip(String device){

        return true;
    }

    public boolean back(String device){

        return true;
    }

    public boolean showPlaylists(String device){

        return true;
    }

    @Override
    public void onMessageFromFhemReceived(String messageFromFhem) {



    }
}
