package smartMirror.dataHandlers.settings;

import smartMirror.dataModels.widgetsModels.busTimetableModels.BusStop;
import smartMirror.dataModels.widgetsModels.feedModels.NewsSource;
import smartMirror.dataModels.widgetsModels.weatherModels.Town;

import java.util.Observable;
import java.util.Observer;

public class SettingsManager extends Observable implements Observer {

    private String busStopName;
    private String townName;
    private String newsSourceName;

    private void assignSettings() {

    }

    private void applySettings() {

    }


    @Override
    @SuppressWarnings("unchecked")
    public void update(Observable o, Object arg) {
        if (arg instanceof BusStop) {
            BusStop busStop = (BusStop) arg;
            this.busStopName = busStop.getBusStop();

        } else if (arg instanceof Town) {
            Town town = (Town) arg;
            this.townName = town.getTown();

        } else if (arg instanceof NewsSource) {
            NewsSource newsSource = (NewsSource) arg;
            this.newsSourceName = newsSource.getNewsSource();

        }

    }
}
