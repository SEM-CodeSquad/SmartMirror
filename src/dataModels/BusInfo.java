package dataModels;

public class BusInfo {
    public String busFrom;
    public String busDirection;
    public String busName;
    public String busDeparture;

    //Need revision
    public BusInfo() {
        this.busFrom = busFrom;
        this.busDirection = busDirection;
        this.busName = busName;
        this.busDeparture = busDeparture;
    }

    public String getBusFrom(){
        return busFrom;
    }

    public String getBusDirection() {
        return busDirection;
    }

    public String getBusName() {
        return busName;
    }

    public String getBusDeparture(){
        return busDeparture;
    }
}