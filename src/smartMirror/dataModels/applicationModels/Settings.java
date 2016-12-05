package smartMirror.dataModels.applicationModels;

import smartMirror.dataModels.widgetsModels.busTimetableModels.BusStop;
import smartMirror.dataModels.widgetsModels.feedModels.NewsSource;
import smartMirror.dataModels.widgetsModels.weatherModels.Town;

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

