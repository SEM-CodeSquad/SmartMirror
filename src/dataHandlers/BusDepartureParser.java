package dataHandlers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Created by Axel on 11/10/2016.
 */

public class BusDepartureParser {

    public String busJson;

    BusInfo[] busArray = new BusInfo[35];

    public void busJsonParser(String busJson) {

        try {
            this.busJson = busJson;
            JSONParser parser = new JSONParser();

            JSONObject jObject = (JSONObject) parser.parse(busJson);
            JSONObject jObject1 = (JSONObject) jObject.get("DepartureBoard");
            JSONArray jArray2 = (JSONArray) jObject1.get("Departure");

            System.out.println("Size of the array  = " + (jArray2.size()-1));
            for (int i = 0; i < jArray2.size() - 1; i++) {

                System.out.println("Search:" + i);

                JSONObject jObjectData = (JSONObject) jArray2.get(i);
                BusInfo busData = new BusInfo();

                busData.busFrom = jObjectData.get("stop").toString();
                busData.busDirection = jObjectData.get("direction").toString();
                busData.busName = jObjectData.get("name").toString();
                busData.busDeparture = jObjectData.get("rtTime").toString();

                busArray[i] = busData;
            }

            //Method to extract data from the array.
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
