package widgets;

import javafx.concurrent.Worker;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.concurrent.TimeUnit;

public class BusTimetable
{
    private WebView webView;
    private GridPane gridPane;

    //TODO please do not delete this one until we fix the new one
    public void setBusTimetable(String busStop, WebView webViewBus, GridPane busGrid)
    {
        try {
            this.webView = webViewBus;
            this.gridPane = busGrid;

            WebEngine web = webViewBus.getEngine();
            web.load("http://www.vasttrafik.se/nasta-tur-fullskarm/?externalid=9021014004945000&friendlyname="
                    + busStop + "+G%C3%B6teborg");
            web.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    // new page has loaded, process:
                    setVisible();
                }
            });
//            System.out.println("loaded timetable");
//            webViewBus.setVisible(true);
//            busGrid.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setVisible()
    {
        webView.setVisible(true);
        gridPane.setVisible(true);
    }
}
