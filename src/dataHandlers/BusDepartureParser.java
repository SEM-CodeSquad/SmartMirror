package dataHandlers;

import dataModels.BusInfo;
import interfaceView.BusTimetableScreen;
import javafx.scene.layout.GridPane;
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
    int i;
    private String busTime;
    private GridPane gridPane;

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

    public void busJsonParser(String busJson, String busTime, GridPane gridPane) {
        i = 0;
        this.gridPane = gridPane;
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
                busData.busColor = jObjectData.get("fgColor").toString();

                busArray[i] = busData;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        DepartureSort DS = new DepartureSort();
        DS.timeSort(busArray, i);

        setLabels();

        //Prints all departures in sorter order
//        for (int j = 0; j < i; j++) {
//            System.out.println("Bus " + busArray[j].getBusName() + " leaves in " + busArray[j].getBusDeparture() + " minutes, heading towards " + busArray[j].getBusDirection());
//        }

    }

    public void setLabels() {
        BusTimetableScreen BTS = new BusTimetableScreen(gridPane);
        int j = 0;
        if (j <= i) {
            BTS.setLabel(busArray[j].getBusName(), busArray[j].getBusDeparture(), busArray[j].getBusFrom(), busArray[j].getBusColor(), busArray[j].getBusDirection());
            j++;
        }
        if (j <= i) {
            BTS.setLabel1(busArray[j].getBusName(), busArray[j].getBusDeparture(), busArray[j].getBusFrom(), busArray[j].getBusColor(), busArray[j].getBusDirection());
            j++;
        }
        if (j <= i) {
            BTS.setLabel2(busArray[j].getBusName(), busArray[j].getBusDeparture(), busArray[j].getBusFrom(), busArray[j].getBusColor(), busArray[j].getBusDirection());
            j++;
        }
        if (j <= i) {
            BTS.setLabel3(busArray[j].getBusName(), busArray[j].getBusDeparture(), busArray[j].getBusFrom(), busArray[j].getBusColor(), busArray[j].getBusDirection());
            j++;
        }
        if (j <= i) {
            BTS.setLabel4(busArray[j].getBusName(), busArray[j].getBusDeparture(), busArray[j].getBusFrom(), busArray[j].getBusColor(), busArray[j].getBusDirection());
            j++;
        }
        if (j <= i) {
            BTS.setLabel5(busArray[j].getBusName(), busArray[j].getBusDeparture(), busArray[j].getBusFrom(), busArray[j].getBusColor(), busArray[j].getBusDirection());
            j++;
        }
        if (j <= i) {
            BTS.setLabel6(busArray[j].getBusName(), busArray[j].getBusDeparture(), busArray[j].getBusFrom(), busArray[j].getBusColor(), busArray[j].getBusDirection());


        }
    }
}