package dataHandlers.widgetsDataHandlers.busTimetable;

import dataModels.widgetsModels.busTimetableModels.BusInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


class BusDepartureParser extends Observable {
    private BusInfo[] busArray;


    private int convertToMinutes(String departTime) {
        int minutes = 0;

        try {
            DateTimeFormatter SHORT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
            String timeNow = LocalTime.now().format(SHORT_TIME_FORMATTER);

            if (departTime.equals(timeNow)) return 0;

            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date date1 = format.parse(timeNow);
            Date date2 = format.parse(departTime);

            long difference = date2.getTime() - date1.getTime();

            long dminutes = difference / (60 * 1000);

            minutes = (int) dminutes;

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return minutes;
    }

    @SuppressWarnings("unchecked")
    void busJsonParser(String busJson) {

        int index = 0;

        try {
            JSONParser parser = new JSONParser();

            JSONObject jObject = (JSONObject) parser.parse(busJson);
            JSONObject jObject1 = (JSONObject) jObject.get("DepartureBoard");
            JSONArray jArray2 = (JSONArray) jObject1.get("Departure");

            busArray = new BusInfo[jArray2.size()];

            for (int i = 0; i < jArray2.size() - 1; i++) {

                JSONObject jObjectData = (JSONObject) jArray2.get(i);
                BusInfo busData = new BusInfo();

                if (jObjectData.get("rtTime") != null && convertToMinutes(jObjectData.get("rtTime").toString()) >= 0) {
                    busData.busFrom = jObjectData.get("stop").toString();
                    busData.busDirection = jObjectData.get("direction").toString();
                    busData.busName = jObjectData.get("sname").toString();
                    String tempTime = jObjectData.get("rtTime").toString();
                    busData.busDeparture = convertToMinutes(tempTime);
                    busData.busColor = jObjectData.get("fgColor").toString();

                    busArray[index] = busData;
                    index++;
                }
            }

            DepartureSort DS = new DepartureSort();
            DS.timeSort(busArray, index);

        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        setChanged();
        notifyObservers(this.busArray);
    }

}