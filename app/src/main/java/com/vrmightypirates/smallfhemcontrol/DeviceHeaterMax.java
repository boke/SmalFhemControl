package com.vrmightypirates.smallfhemcontrol;

/**
 * Created by Boke on 15.05.2016.
 */
public class DeviceHeaterMax extends FhemDevice {

    private static final String TAG = DeviceHeaterMax.class.getSimpleName() ;
    private String desireTemperature;
    private ConnectToFhem connectToFhem = null;

    public DeviceHeaterMax(String deviceName, Object widget) {
        super(deviceName,DeviceType.HeaterMax, widget);
        connectToFhem = new ConnectToFhem();

    }


    public String getDesireTemperatureValue() {
        return desireTemperature;
    }


    public void setDesireTemperatureValue(String desireTemperature) {
        this.desireTemperature = desireTemperature;
    }

    public void setDesireTemperatureInFhem(String desireTemperature, ConnectionType connectionType) {
        this.desireTemperature = desireTemperature;
        connectToFhem.sendMessage("set " + this.getDeviceName() + " desireTemperature " + desireTemperature, connectionType);

    }




}
