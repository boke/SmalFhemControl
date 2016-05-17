package com.vrmightypirates.smalfhemcontrol;

import android.widget.TextView;

/**
 * Created by Boke on 15.05.2016.
 */
public class DeviceHeaterMax extends FhemDevice {

    private String desireTemperature;

    public DeviceHeaterMax(String deviceName, Object widget) {
        super(deviceName,DeviceType.HeaterMax, widget );
    }


    public String getDesireTemperature() {
        return desireTemperature;
    }

    public void setDesireTemperature(String desireTemperature) {
        this.desireTemperature = desireTemperature;
        TextView textView = (TextView) super.getWidget();
        textView.setText(desireTemperature);
    }

}
