package dataHandlers;

import dataModels.BusInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import widgets.BusTimetable;

/**
 * Created by Axel on 11/10/2016.
 */

public class BusDepartureParser {

    public String busJson;
    BusInfo[] busArray = new BusInfo[40];
    private String busTime;

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
        this.busTime = busTime;

        try {
            this.busJson = busJson;
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
                String tempTime = jObjectData.get("rtTime").toString();
                busData.busDeparture = convertToMinutes(tempTime, busTime);

                busArray[i] = busData;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        DepartureSort DS = new DepartureSort();
        DS.timeSort(busArray, i);


        //Prints all departures in sorter order
//        for (int j = 0; j < i; j++) {
//            System.out.println("Bus " + busArray[j].getBusName() + " leaves in " + busArray[j].getBusDeparture() + " minutes, heading towards " + busArray[j].getBusDirection());
//        }

    }
}
