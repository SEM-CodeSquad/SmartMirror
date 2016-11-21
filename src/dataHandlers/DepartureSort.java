package dataHandlers;

import dataModels.BusInfo;


public class DepartureSort {

    private BusInfo busArray[];

    public void timeSort(BusInfo[] busArray, int length) {

        this.busArray = busArray;
        quickSort(0, length - 1);
    }

    private void quickSort(int lowerIndex, int higherIndex) {

        int i = lowerIndex;
        int j = higherIndex;
        int pivot = busArray[(lowerIndex + (higherIndex - lowerIndex) / 2)].getBusDeparture();
        while (i <= j) {
            while (busArray[i].getBusDeparture() < pivot) {
                i++;
            }
            while (busArray[j].getBusDeparture() > pivot) {
                j--;
            }
            if (i <= j) {
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

    private void exchangeObjects(int i, int j) {
        BusInfo busData = busArray[i];
        busArray[i] = busArray[j];
        busArray[j] = busData;
    }
}