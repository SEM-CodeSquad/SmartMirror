package dataHandlers;

public class DeviceStatus
{
    private String deviceName;
    private String deviceStatus;

    public DeviceStatus(String name, String status)
    {
        this.deviceName = name;
        this.deviceStatus = status;
    }

    public String getDeviceName()
    {
        return deviceName;
    }

    public String getDeviceStatus()
    {
        return deviceStatus;
    }
}
