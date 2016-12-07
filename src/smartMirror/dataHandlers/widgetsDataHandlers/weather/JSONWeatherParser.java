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

package smartMirror.dataHandlers.widgetsDataHandlers.weather;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import smartMirror.dataModels.widgetsModels.weatherModels.Weather;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author CodeHigh @copyright on 07/12/2016.
 *         Class responsible for parsing the json weatehr data
 */
public class JSONWeatherParser implements Observer
{
    private Weather[] wArray;

    /**
     * This method takes the whole JSON formatted data returned by the web server when called the fetchWeather() method
     * from the WeatherFetcher class in the constructor. Then returns 3 instances of the object Weather in an array.
     * With the first element holding values for the present day, the next element holding the values for the next day
     * and so on.
     *
     * @param data json weather data
     * @return array containing the weather data
     */
    private Weather[] parseWeatherForecast(String data)
    {
        JSONParser parser = new JSONParser();
        wArray = new Weather[3];
        try
        {
            JSONObject jObject = (JSONObject) parser.parse(data);
            JSONArray jArray = (JSONArray) jObject.get("list");
            JSONObject jArrayObj1 = (JSONObject) jArray.get(0);
            JSONObject jTempObj1 = (JSONObject) jArrayObj1.get("temp");
            Weather weather1 = new Weather();
            weather1.setTemp(jTempObj1.get(getTime()).toString());
            weather1.setMaxTemp(jTempObj1.get("max").toString());
            weather1.setMinTemp(jTempObj1.get("min").toString());
            JSONObject jTempObj2 = (JSONObject) ((JSONArray) jArrayObj1.get("weather")).get(0);
            weather1.setDesc(jTempObj2.get("description").toString());
            weather1.setIcon(jTempObj2.get("icon").toString());
            weather1.setDayName(getDay(0));

            JSONObject jArrayObj2 = (JSONObject) jArray.get(1);
            JSONObject jTempObj3 = (JSONObject) jArrayObj2.get("temp");
            Weather weather2 = new Weather();
            weather2.setTemp(jTempObj3.get(getTime()).toString());
            weather2.setMaxTemp(jTempObj3.get("max").toString());
            weather2.setMinTemp(jTempObj3.get("min").toString());
            JSONObject jTempObj4 = (JSONObject) ((JSONArray) jArrayObj1.get("weather")).get(0);
            weather2.setDesc(jTempObj4.get("description").toString());
            weather2.setIcon(jTempObj4.get("icon").toString());
            weather2.setDayName(getDay(1));
            wArray[1] = weather2;

            JSONObject jArrayObj3 = (JSONObject) jArray.get(2);
            JSONObject jTempObj5 = (JSONObject) jArrayObj3.get("temp");
            Weather weather3 = new Weather();
            weather3.setTemp(jTempObj5.get(getTime()).toString());
            weather3.setMaxTemp(jTempObj5.get("max").toString());
            weather3.setMinTemp(jTempObj5.get("min").toString());
            JSONObject jTempObj6 = (JSONObject) ((JSONArray) jArrayObj1.get("weather")).get(0);
            weather3.setDesc(jTempObj6.get("description").toString());
            weather3.setIcon(jTempObj6.get("icon").toString());
            weather3.setDayName(getDay(2));
            wArray[2] = weather3;

        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return wArray;
    }

    /**
     * Method that parses the json data to fetch the current weather
     *
     * @param data json data to be parsed
     * @return array containing the weather data
     */
    private Weather[] parseCurrentWeather(String data)
    {
        JSONParser parser = new JSONParser();
        try
        {
            JSONObject jObject = (JSONObject) parser.parse(data);
            JSONArray jArray = (JSONArray) jObject.get("weather");
            JSONObject jTempObj1 = (JSONObject) jArray.get(0);
            Weather weather1 = new Weather();
            weather1.setDesc(jTempObj1.get("description").toString());
            weather1.setIcon(jTempObj1.get("icon").toString());

            JSONObject jTempObj2 = (JSONObject) jObject.get("main");
            weather1.setTemp(jTempObj2.get("temp").toString());
            weather1.setMaxTemp(jTempObj2.get("temp_max").toString());
            weather1.setMinTemp(jTempObj2.get("temp_min").toString());


            weather1.setDayName(getDay(0));
            wArray[0] = weather1;

        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return wArray;
    }

    /**
     * Getter method that provides the requested weather from the array. The positions are 0 for current weather, 1 for
     * the next day, 2 for the day following the next day
     *
     * @param i requested index
     * @return Weather element
     */
    public Weather getWeather(int i)
    {
        return wArray[i];
    }

    /**
     * Method that define the day name for the retrieved weather
     *
     * @param i index of the weather in the relative order as today 0, tomorrow 1, day after tomorrow 2
     * @return first three letters of the day name
     */
    private String getDay(int i)
    {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, i);
        return new SimpleDateFormat("EE", Locale.ENGLISH).format(calendar.getTime());

    }

    /**
     * Method that identifies the time of the day to help the parser identify which data it should look for
     *
     * @return string containing the time such as morn (morning) or eve (evening) and so on.
     */
    private String getTime()
    {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12)
        {
            return "morn";
        }
        else if (timeOfDay >= 12 && timeOfDay < 16)
        {
            return "day";
        }
        else if (timeOfDay >= 16 && timeOfDay < 21)
        {
            return "eve";
        }
        else if (timeOfDay >= 21 && timeOfDay < 24)
        {
            return "night";
        }

        return "";
    }

    /**
     * Update method where the observable classes sends notifications messages
     *
     * @param o   observable object
     * @param arg object arg
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (arg instanceof WeatherFetcher)
        {
            WeatherFetcher weatherFetcher = (WeatherFetcher) arg;
            if (weatherFetcher.isForecast()) this.parseWeatherForecast(weatherFetcher.getWeatherData());
            else this.parseCurrentWeather(weatherFetcher.getWeatherData());
        }
    }
}