package com.vrmightypirates.smalfhemcontrol;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Boke on 16.05.2016.
 */
public class FhemMessageParser {

    private static final String TAG = FhemMessageParser.class.getSimpleName();

    public void parseMessage(String message, ArrayList<FhemDevice> deviceList){

       String [] messages;
       messages = message.split(System.lineSeparator());

        for (int i=0; i<messages.length; i++){
            for (FhemDevice device: deviceList) {

                if(messages[i].contains(device.getDeviceName())){

                   switch (device.getDeviceType()){
                        case HeaterMax:
                            DeviceHeaterMax deviceHeaterMax = (device instanceof DeviceHeaterMax) ? ((DeviceHeaterMax) device) : null;
                            Log.i(TAG, "deviceHeaterMax: " + messages[i]);
                            //deviceHeaterMax.setDesireTemperature();
                            Log.i(TAG, "HeaterMax: ");
                            break;
                        case HeaterHomematic:
                            break;
                       case Sonos:
                           break;
                   }




                }

            }
        }
        
    }
    
}
