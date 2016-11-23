package dataHandlers;

import dataModels.BusInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Observable;


public class BusDepartureParser extends Observable {

    private BusInfo[] busArray = new BusInfo[40];

    private static int convertToMinutes(String var, String var1) {

        int hour = Integer.parseInt(var.substring(0, 2)) * 60;
        int min = Integer.parseInt(var.substring(3, 5));

        int hour1 = Integer.parseInt(var1.substring(0, 2)) * 60;
        int min1 = Integer.parseInt(var1.substring(3, 5));

        int depTime = min + hour;
        int currTime = min1 + hour1;

        if (depTime < currTime) {
            depTime += 1440;
        }

        return depTime - currTime;
    }

    public void busJsonParser(String busJson, String busTime) {
        int i = 0;

        try {
            JSONParser parser = new JSONParser();

            JSONObject jObject = (JSONObject) parser.parse(busJson);
            JSONObject jObject1 = (JSONObject) jObject.get("DepartureBoard");
            JSONArray jArray2 = (JSONArray) jObject1.get("Departure");

            for (i = 0; i < jArray2.size() - 1; i++) {

                JSONObject jObjectData = (JSONObject) jArray2.get(i);
                BusInfo busData = new BusInfo();

                busData.busFrom = jObjectData.get("stop").toString();
                busData.busDirection = jObjectData.get("direction").toString();
                busData.busName = jObjectData.get("sname").toString();
                System.out.println(busData.busName + "Mid-loop data confirmation");
                String tempTime = jObjectData.get("rtTime").toString();
                busData.busDeparture = convertToMinutes(tempTime, busTime);
                busData.busColor = jObjectData.get("fgColor").toString();

                busArray[i] = busData;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Parsing done, Starting sort..");

        DepartureSort DS = new DepartureSort();
        DS.timeSort(busArray, i);
        System.out.println("Sorting done. Notify Observer..");

        setChanged();
        notifyObservers(this.busArray);
    }
}