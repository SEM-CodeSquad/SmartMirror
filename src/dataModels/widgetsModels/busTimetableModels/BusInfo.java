package dataModels.widgetsModels.busTimetableModels;

public class BusInfo {
    public String busFrom;
    public String busDirection;
    public String busName;
    public int busDeparture;
    public String busColor;

    public String getBusFrom(){
        return busFrom;
    }

    public String getBusDirection() {
        return busDirection;
    }

    public String getBusName() {
        return busName;
    }

    public String getBusColor() {
        return busColor;
    }

    public int getBusDeparture() {
        return busDeparture;
    }
}