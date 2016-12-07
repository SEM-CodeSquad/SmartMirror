package smartMirror.dataHandlers.widgetsDataHandlers.busTimetable;

import smartMirror.dataModels.widgetsModels.busTimetableModels.BusInfo;

/**
 * @author Axel Verner @copyright on 06/12/2016.
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