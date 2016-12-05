package smartMirror.dataModels.widgetsModels.devicesModels;

public class Device {
    private String deviceName;
    private String status;

    public Device(String name, String value) {
        this.deviceName = name;
        this.status = value;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getStatus() {
        return status;
    }
}
