package dataModels.applicationModels;

import dataModels.widgetsModels.busTimetableModels.BusStop;
import dataModels.widgetsModels.feedModels.NewsSource;
import dataModels.widgetsModels.weatherModels.Town;

public class Settings {
    private Object object;

    public Settings(String name, String value) {
        switch (name) {
            case "busStop":
                object = new BusStop(value);
                break;
            case "news":
                object = new NewsSource(value);
                break;
            case "weather":
                object = new Town(value);
                break;
        }
    }

    public Object getObject() {
        return object;
    }
}

