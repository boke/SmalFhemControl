package com.vrmightypirates.smallfhemcontrol;

import android.util.Log;

/**
 * Created by Boke on 15.05.2016.
 */
public class DeviceHeaterMax extends FhemDevice {

    private static final String TAG = DeviceHeaterMax.class.getSimpleName() ;
    private String desireTemperature;

    public DeviceHeaterMax(String deviceName, Object widget) {
        super(deviceName,DeviceType.HeaterMax, widget);

    }


    public String getDesireTemperature() {
        return desireTemperature;
    }

    public void setDesireTemperature(String desireTemperature) {
        this.desireTemperature = desireTemperature;
        Log.i(TAG, "setDesireTemperature: " + desireTemperature);
    }
}
