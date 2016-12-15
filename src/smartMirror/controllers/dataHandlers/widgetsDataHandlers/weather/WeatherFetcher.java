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

package smartMirror.controllers.dataHandlers.widgetsDataHandlers.weather;

import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Observable;

/**
 * @author CodeHigh @copyright on 07/12/2016.
 *         The WeatherFetcher class fetches the weather of a city that is inputted in the parameters of the method fetchWeather()
 *         It returns a String containing weather information of that city in JSON format.
 */
public class WeatherFetcher extends Observable
{
    private String weatherData;
    private boolean isForecast;

    /**
     * Method responsible for connecting with the API and requesting the weather data
     *
     * @param query    query to be used in the request such as forecast/daily for the daily forecast or weather for the current
     *                 weather
     * @param cityName the city name for the requested weather the name must be followed by a coma and the name of the country
     *                 to fetch the weather for the right town
     */
    public void fetchWeather(String query, String cityName)
    {

        HttpURLConnection connection = null;
        InputStream IS = null;

        try
        {
            String town = URLEncoder.encode(cityName, "UTF-8");
            String BASE_URL = "http://api.openweathermap.org/data/2.5/" + query + "?q=";
            connection = (HttpURLConnection) (new URL(BASE_URL + town + "&type=accurate&units=metric&cnt=3" +
                    "&lang=en&APPID=47d83bc50f7e59413e487108ded5c729")).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();


            StringBuilder buffer = new StringBuilder();
            IS = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(IS));
            String line;
            while ((line = br.readLine()) != null)
                buffer.append(line).append("\r\n");

            IS.close();
            connection.disconnect();
            this.weatherData = buffer.toString();

            isForecast = query.equals("forecast/daily");
            setChanged();
            notifyObservers(this);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            try
            {
                assert IS != null;
                IS.close();
            }
            catch (Throwable ignored)
            {
            }
            try
            {
                assert connection != null;
                connection.disconnect();
            }
            catch (Throwable ignored)
            {
            }
        }
    }

    /**
     * Method that provides the icon for the weather
     *
     * @param code code supplied in the json data
     * @return the image with the icon for the weather
     */
    public Image getImage(String code)
    {
        InputStream is;

        try
        {
            is = getClass().getClassLoader().getResourceAsStream(code + ".png");
        }
        catch (Exception e)
        {
            is = getClass().getClassLoader().getResourceAsStream("na.png");
        }
        return new Image(is);
    }

    /**
     * Getter that provides the weather data
     *
     * @return json weather data
     */
    String getWeatherData()
    {
        return weatherData;
    }

    /**
     * Method that identifies if the requested data is a forecast or the current weather
     *
     * @return true if is forecast otherwise false
     */
    boolean isForecast()
    {
        return this.isForecast;
    }
}

