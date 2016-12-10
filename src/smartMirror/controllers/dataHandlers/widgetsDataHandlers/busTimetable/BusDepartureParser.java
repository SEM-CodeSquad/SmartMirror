/*
 * Copyright 2016 CodeHigh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (C) 2016 CodeHigh.
 *     Permission is granted to copy, distribute and/or modify this document
 *     under the terms of the GNU Free Documentation License, Version 1.3
 *     or any later version published by the Free Software Foundation;
 *     with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 *     A copy of the license is included in the section entitled "GNU
 *     Free Documentation License".
 */

package smartMirror.controllers.dataHandlers.widgetsDataHandlers.busTimetable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import smartMirror.dataModels.widgetsModels.busTimetableModels.BusInfo;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Observable;

/**
 * @author CodeHigh @copyright on 06/12/2016.
 *         Class responsible for parsing the data received from västrafik API
 */
class BusDepartureParser extends Observable
{
    private BusInfo[] busArray;


    /**
     * Method responsible for converting the time into the difference in minutes between the received time and the actual
     * time
     *
     * @param departTime time received from the västrafik API
     * @return difference in minutes between the actual time and the received time
     */
    private int convertToMinutes(String departTime)
    {
        int minutes = 0;

        try
        {
            DateTimeFormatter SHORT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
            String timeNow = LocalTime.now().format(SHORT_TIME_FORMATTER);

            if (departTime.equals(timeNow)) return 0;

            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date date1 = format.parse(timeNow);
            Date date2 = format.parse(departTime);

            long difference = date2.getTime() - date1.getTime();

            long dMinutes = difference / (60 * 1000);

            minutes = (int) dMinutes;

        }
        catch (java.text.ParseException e)
        {
            e.printStackTrace();
        }

        return minutes - 1;
    }

    /**
     * Method responsible for parsing the data and creating a list with all the bus data sorted by the time the bus will
     * be departing
     *
     * @param busJson data to be parsed
     * @see DepartureSort
     * @see BusInfo
     */
    @SuppressWarnings("unchecked")
    void busJsonParser(String busJson)
    {
        System.out.println(busJson);
        int index = 0;

        try
        {
            JSONParser parser = new JSONParser();

            JSONObject jObject = (JSONObject) parser.parse(busJson);
            JSONObject jObject1 = (JSONObject) jObject.get("DepartureBoard");

            Object departure = jObject1.get("Departure");

            if (departure instanceof JSONObject)
            {
                JSONObject jData = (JSONObject) departure;
                BusInfo busData = new BusInfo();
                if (jData.get("rtTime") != null && convertToMinutes(jData.get("rtTime").toString()) >= 0)
                {
                    busData.busFrom = jData.get("stop").toString();
                    busData.busDirection = jData.get("direction").toString();
                    busData.busName = jData.get("sname").toString();
                    String tempTime = jData.get("rtTime").toString();
                    busData.busDeparture = convertToMinutes(tempTime);
                    busData.busColor = jData.get("fgColor").toString();

                    busArray[index] = busData;
                }
                else if (convertToMinutes(jData.get("time").toString()) >= 0)
                {
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

            }
            else if (departure instanceof JSONArray)
            {
                JSONArray jArray2 = (JSONArray) departure;

                busArray = new BusInfo[jArray2.size()];

                for (int i = 0; i < jArray2.size() - 1; i++)
                {

                    JSONObject jObjectData = (JSONObject) jArray2.get(i);
                    BusInfo busData = new BusInfo();

                    if (jObjectData.get("rtTime") != null && convertToMinutes(jObjectData.get("rtTime").toString()) >= 0)
                    {
                        busData.busFrom = jObjectData.get("stop").toString();
                        busData.busDirection = jObjectData.get("direction").toString();
                        busData.busName = jObjectData.get("sname").toString();
                        String tempTime = jObjectData.get("rtTime").toString();
                        busData.busDeparture = convertToMinutes(tempTime);
                        busData.busColor = jObjectData.get("fgColor").toString();

                        busArray[index] = busData;
                        index++;
                    }
                    else if (convertToMinutes(jObjectData.get("time").toString()) >= 0)
                    {
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
            }
            else
            {
                System.err.println(departure.getClass().toString());
            }
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
        }


    }

}