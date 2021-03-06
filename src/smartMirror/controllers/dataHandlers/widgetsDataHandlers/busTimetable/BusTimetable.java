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

import smartMirror.dataModels.applicationModels.ChainedMap;
import smartMirror.dataModels.widgetsModels.busTimetableModels.BusInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * @author CodeHigh @copyright on 06/12/2016.
 *         Class responsible for establishing connection with västrafik API, query for the departure board
 *         and receive the data
 */
public class BusTimetable extends Observable implements Observer
{

    private static DateTimeFormatter SHORT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String busTime = LocalTime.now().format(SHORT_TIME_FORMATTER);
    private String busDate = LocalDate.now().format(SHORT_DATE_FORMATTER);
    private BusDepartureParser bdp = new BusDepartureParser();
    private String stop;


    /**
     * Sends a HTTP request to Vasttrafik.
     * Gets a json object in response that is sent to the BusDepartureParser().
     *
     * @param busStop name of the bus stop
     */
    public synchronized void setBusTimetable(String busStop)
    {
        this.stop = busStop;
        this.bdp.addObserver(this);

        String code = generateAccessCode();

        StringBuilder result = new StringBuilder();
        URL url;
        try
        {
            url = new URL("https://api.vasttrafik.se/bin/rest.exe/v2/departureBoard?id=" +
                    busStop + "&date=" + busDate + "&time=" + busTime + "&timeSpan=360&needJourneyDetail=0&type=accurate&format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + code);
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = rd.readLine()) != null)
            {
                result.append(line);
            }
            rd.close();


            bdp.busJsonParser(result.toString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method that generates the access code to fetch the data from the API
     *
     * @return the access code
     */
    private String generateAccessCode()
    {


        StringBuilder result = new StringBuilder();

        URL url;
        try
        {
            String authParam = "Basic b3BPMlJKT3o3em9RaEhseE5VS0ozWXdoaVJNYTpuajR3bmc2R3dqUWxBRHk1aktRRFR1aHpFdGNh";
            url = new URL("https://api.vasttrafik.se:443/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Authorization", authParam);
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            wr.write("grant_type=client_credentials");
            wr.flush();
            wr.close();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null)
            {
                result.append(line);

            }
            rd.close();


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return result.substring(result.lastIndexOf(":") + 2, result.length() - 2);
    }

    /**
     * Update method where the observable classes sends notifications messages
     *
     * @param o   observable object
     * @param arg object arg
     */
    @Override
    @SuppressWarnings("unchecked")
    public void update(Observable o, Object arg)
    {
        if (arg instanceof BusInfo[])
        {
            BusInfo[] busInfos = (BusInfo[]) arg;

            ChainedMap<String, LinkedList<BusInfo>> busData = new ChainedMap<>();

            for (BusInfo busInfo : busInfos)
            {

                if (busInfo != null)
                {
                    String key = busInfo.getBusName() + busInfo.getBusDirection();

                    if (busData.size() > 0 && busData.getId().contains(key))
                    {
                        busData.getMap().get(key).add(busInfo);
                    }
                    else
                    {
                        LinkedList<BusInfo> list = new LinkedList();
                        list.add(busInfo);
                        busData.add(key, list);
                    }
                }
            }

            setChanged();
            notifyObservers(busData);
        }
        else if (arg.equals("crash") && this.stop != null)
        {
            setBusTimetable(this.stop);
        }
    }
}
