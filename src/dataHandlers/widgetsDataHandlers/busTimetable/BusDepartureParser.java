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

            long dMinutes = difference / (60 * 1000);

            minutes = (int) dMinutes;

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return minutes - 1;
    }

    @SuppressWarnings("unchecked")
    void busJsonParser(String busJson) {

        int index = 0;

        try {
            JSONParser parser = new JSONParser();

            JSONObject jObject = (JSONObject) parser.parse(busJson);
            JSONObject jObject1 = (JSONObject) jObject.get("DepartureBoard");

            Object departure = jObject1.get("Departure");

            if (departure instanceof JSONObject) {
                JSONObject jData = (JSONObject) departure;
                BusInfo busData = new BusInfo();
                if (jData.get("rtTime") != null && convertToMinutes(jData.get("rtTime").toString()) >= 0) {
                    busData.busFrom = jData.get("stop").toString();
                    busData.busDirection = jData.get("direction").toString();
                    busData.busName = jData.get("sname").toString();
                    String tempTime = jData.get("rtTime").toString();
                    busData.busDeparture = convertToMinutes(tempTime);
                    busData.busColor = jData.get("fgColor").toString();

                    busArray[index] = busData;
                } else if (convertToMinutes(jData.get("time").toString()) >= 0) {
                    busData.busFrom = jData.get("stop").toString();
                    busData.busDirection = jData.get("direction").toString();
                    busData.busName = jData.get("sname").toString();
                    String tempTime = jData.get("time").toString();
                    busData.busDeparture = convertToMinutes(tempTime);
                    busData.busColor = jData.get("fgColor").toString();

                    busArray[index] = busData;
                }
                setChanged();
                notifyObservers(this.busArray);

            } else if (departure instanceof JSONArray) {
                JSONArray jArray2 = (JSONArray) departure;

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
                    } else if (convertToMinutes(jObjectData.get("time").toString()) >= 0) {
                        busData.busFrom = jObjectData.get("stop").toString();
                        busData.busDirection = jObjectData.get("direction").toString();
                        busData.busName = jObjectData.get("sname").toString();
                        String tempTime = jObjectData.get("time").toString();
                        busData.busDeparture = convertToMinutes(tempTime);
                        busData.busColor = jObjectData.get("fgColor").toString();

                        busArray[index] = busData;
                        index++;
                    }
                }

                DepartureSort DS = new DepartureSort();
                DS.timeSort(busArray, index);
                setChanged();
                notifyObservers(this.busArray);
            } else {
                System.err.println(departure.getClass().toString());
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }


    }

}