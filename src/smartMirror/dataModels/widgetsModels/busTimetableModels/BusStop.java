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
 *         Class containg the model for the bus stop
 */
public class BusStop
{
    private String busStop;

    /**
     * Constructor that build the bus stop according to the id provided
     *
     * @param busStop id of the bus stop
     */
    public BusStop(String busStop)
    {
        this.busStop = busStop;
    }

    /**
     * Getter method that provides the id of the bus stop
     *
     * @return id of the bus stop
     */
    public String getBusStop()
    {
        return busStop;
    }
}
