package widgets;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class BusTimetable
{
    public void setBusTimetable(String busStop, WebView webViewBus)
    {
        WebEngine web = webViewBus.getEngine();
        web.load("http://www.vasttrafik.se/nasta-tur-fullskarm/?externalid=9021014004945000&friendlyname="
                + busStop + "+G%C3%B6teborg");
    }
}
