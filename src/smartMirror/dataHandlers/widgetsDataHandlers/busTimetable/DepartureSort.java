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

package smartMirror.dataHandlers.widgetsDataHandlers.busTimetable;

import smartMirror.dataModels.widgetsModels.busTimetableModels.BusInfo;

/**
 * @author CodeHigh @copyright on 06/12/2016.
 *         Class responsible for sorting the bus info data by the departure time
 */
class DepartureSort
{

    private BusInfo busArray[];

    void timeSort(BusInfo[] busArray, int length)
    {

        this.busArray = busArray;
        quickSort(0, length - 1);
    }

    /**
     * Quick sort algorithm
     *
     * @param lowerIndex  first index in the array
     * @param higherIndex last index in the array
     */
    private void quickSort(int lowerIndex, int higherIndex)
    {

        int i = lowerIndex;
        int j = higherIndex;
        int pivot = busArray[(lowerIndex + (higherIndex - lowerIndex) / 2)].getBusDeparture();
        while (i <= j)
        {
            while (busArray[i].getBusDeparture() < pivot)
            {
                i++;
            }
            while (busArray[j].getBusDeparture() > pivot)
            {
                j--;
            }
            if (i <= j)
            {
                exchangeObjects(i, j);
                i++;
                j--;
            }
        }
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }

    /**
     * Method responsible for swipe the data position in the array
     *
     * @param i index of the first element
     * @param j index of the second element
     */
    private void exchangeObjects(int i, int j)
    {
        BusInfo busData = busArray[i];
        busArray[i] = busArray[j];
        busArray[j] = busData;
    }
}