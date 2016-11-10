package dataHandlers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.*;

/**
 * Created by Axel on 11/10/2016.
 */

public class BusDepartureParser {

    public String busJson;
    public BusInfo busData;

    BusInfo[] busArray = new BusInfo[7];

    public void busJsonParser(String busJson){

        //jsonObj.getJSONObject("DepartureBoard").getJSONArray("Departure");
        try {
            this.busJson = busJson;
            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse(this.busJson);
            this.busData.busFrom = jsonObj.get("DepartureBoard").toString();               //Gets the name of the busstop
            this.busData.busDirection = jsonObj.get("direction").toString();     //Gets the direction displayed on the bus
            this.busData.busName = jsonObj.get("name").toString();               //Use "sname" for short name instead if preferred.
            this.busData.busDeparture = jsonObj.get("rtTime").toString();        //Time when the bus leaves
            //this.busData.busArrival = jsonObj.get("stop").toString();
            //this.busData.busDuration = jsonObj.get("stop").toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //return busArray;
    }
}
