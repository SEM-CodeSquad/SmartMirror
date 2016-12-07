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

package smartMirror.dataModels.widgetsModels.busTimetableModels;

/**
 * @author CodeHigh @copyright on 07/12/2016.
 *         Class model for the bus info
 */
public class BusInfo
{
    public String busFrom;
    public String busDirection;
    public String busName;
    public int busDeparture;
    public String busColor;

    /**
     * Getter that provides the name of the place where the bus is leaving from
     *
     * @return name of the place where the bus is leaving from
     */
    public String getBusFrom()
    {
        return busFrom;
    }

    /**
     * Getter method that provides the direction that the bus is travelling to
     *
     * @return direction that the bus is travelling to
     */
    public String getBusDirection()
    {
        return busDirection;
    }

    /**
     * Getter method that provides the name of the bus
     *
     * @return name of the bus
     */
    public String getBusName()
    {
        return busName;
    }

    /**
     * Getter method that provides the color identifier for the bus
     *
     * @return color identifier for the bus
     */
    public String getBusColor()
    {
        return busColor;
    }

    /**
     * Getter method that provides the departure time
     *
     * @return departure time
     */
    public int getBusDeparture()
    {
        return busDeparture;
    }
}