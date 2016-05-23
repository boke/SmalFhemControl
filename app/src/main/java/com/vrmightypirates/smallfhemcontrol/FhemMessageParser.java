package com.vrmightypirates.smallfhemcontrol;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Boke on 16.05.2016.
 */
public class FhemMessageParser {

    private static final String TAG = FhemMessageParser.class.getSimpleName();

    public void parseMessage(String message, ArrayList<FhemDevice> deviceList){

            for (FhemDevice device: deviceList) {
                Log.i(TAG, "");
                if (message.contains(device.getDeviceName())) {

                    String params[] = message.split(" ");

                    switch (device.getDeviceType()) {
                        case HeaterMax:
                            DeviceHeaterMax deviceHeaterMax = (device instanceof DeviceHeaterMax) ? ((DeviceHeaterMax) device) : null;

                            switch(params[2]){
                                case "desiredTemperature": {
                                    deviceHeaterMax.setDesireTemperature(params[3]);
                                    break;
                                }
                                case "battery": {
                                    deviceHeaterMax.setBatteryStatus(params[3]);
                                    break;
                                }
                                default:
                                    break;

                            }

                            Log.i(TAG, "deviceHeaterMax: " + deviceHeaterMax.getDesireTemperature() );
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