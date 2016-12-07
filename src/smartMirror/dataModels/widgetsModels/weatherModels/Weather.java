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

package smartMirror.dataModels.widgetsModels.weatherModels;

import java.util.Observable;

/**
 * @author CodeHigh @copyright on 07/12/2016.
 *         The weather object here stores the information related to the weather of the city that we call.
 *         It will store the current, maximum and minimum temperature along with the description and icon of the weather.
 *         Public getters and setters can be used to view any of the above mentioned variables.
 */
public class Weather extends Observable
{
    private String currentTemp;
    private String minTemp;
    private String maxTemp;
    private String desc;
    private String icon;
    private String dayName;

    /**
     * Getter method provides the current temperature
     *
     * @return current temperature
     */
    public double getTemp()
    {
        return Math.round(Double.parseDouble(currentTemp));
    }

    /**
     * Setter method sets current temperature
     *
     * @param temp current temperature
     */
    public void setTemp(String temp)
    {
        this.currentTemp = temp;
    }

    /**
     * Getter method provides the minimum temperature
     *
     * @return minimum temperature
     */
    public double getMinTemp()
    {
        return Math.round(Double.parseDouble(minTemp));
    }

    /**
     * Setter method sets the minimum temperature
     *
     * @param minTemp minimum temperature
     */
    public void setMinTemp(String minTemp)
    {
        this.minTemp = minTemp;
    }

    /**
     * Getter method provides the maximum temperature
     *
     * @return maximum temperature
     */
    public double getMaxTemp()
    {
        return Math.round(Double.parseDouble(maxTemp));
    }

    /**
     * Setter method sets the maximum temperature
     *
     * @param maxTemp maximum temperature
     */
    public void setMaxTemp(String maxTemp)
    {
        this.maxTemp = maxTemp;
    }

    /**
     * Getter method provides the weather description
     *
     * @return weather description
     */
    public String getDesc()
    {
        return desc;
    }

    /**
     * Setter method sets the weather description
     *
     * @param desc weather description
     */
    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    /**
     * Getter method provides the icon id for the weather
     *
     * @return icon id for the weather
     */
    public String getIcon()
    {
        return this.icon;
    }

    /**
     * Setter method sets the icon id for the weather
     *
     * @param icon icon id for the weather
     */
    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    /**
     * Getter method provides the day name for the weather
     *
     * @return day name
     */
    public String getDayName()
    {
        return this.dayName;
    }

    /**
     * Setter method sets the day name for the weather
     *
     * @param dayName day name
     */
    public void setDayName(String dayName)
    {
        this.dayName = dayName;
    }
}




