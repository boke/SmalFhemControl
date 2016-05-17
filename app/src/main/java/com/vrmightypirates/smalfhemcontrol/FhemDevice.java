package com.vrmightypirates.smalfhemcontrol;

/**
 * Created by Boke on 15.05.2016.
 */
public class FhemDevice {

    private String deviceName;
    private DeviceType deviceType;
    private String status;
    private Object widget;

    public Object getWidget() {
        return widget;
    }

    public void setWidget(Object widget) {
        this.widget = widget;
    }

    public FhemDevice(String deviceName, DeviceType deviceType, Object widget) {
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.widget = widget;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
