package dataHandlers;

import dataModels.BusInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by Axel on 11/10/2016.
 */

public class BusDepartureParser {

    public String busJson;

    //TODO: Sometimes the returned data from VT is way bigger then expected
    BusInfo[] busArray = new BusInfo[150];

    public void busJsonParser(String busJson) {

        try {
            this.busJson = busJson;
            JSONParser parser = new JSONParser();

            JSONObject jObject = (JSONObject) parser.parse(busJson);
            JSONObject jObject1 = (JSONObject) jObject.get("DepartureBoard");
            JSONArray jArray2 = (JSONArray) jObject1.get("Departure");

            for (int i = 0; i < jArray2.size() - 1; i++) {

                JSONObject jObjectData = (JSONObject) jArray2.get(i);
                BusInfo busData = new BusInfo();

                busData.busFrom = jObjectData.get("stop").toString();
                busData.busDirection = jObjectData.get("direction").toString();
                busData.busName = jObjectData.get("name").toString();
                busData.busDeparture = jObjectData.get("rtTime").toString();

                busArray[i] = busData;
            }

            //KEEP : Method to extract data from the array.
//            for (int j = 0; j < jArray2.size() - 1; j++) {
//
//                System.out.println("Print:" + j);
//
//                System.out.println(busArray[j].getBusFrom());
//                System.out.println(busArray[j].getBusDirection());
//                System.out.println(busArray[j].getBusName());
//                System.out.println(busArray[j].getBusDeparture());
//
//            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //return busArray;
    }
}
