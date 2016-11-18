package widgets;

import dataHandlers.BusDepartureParser;
import dataHandlers.GenerateAccessCode;
import javafx.concurrent.Worker;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Axel on 8/11/2016.
 */

public class BusTimetable implements Observer {

    private static DateTimeFormatter SHORT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String busTime = LocalTime.now().format(SHORT_TIME_FORMATTER);
    String busDate = LocalDate.now().format(SHORT_DATE_FORMATTER);
    String busStop;
    GenerateAccessCode authCode;
    BusDepartureParser bdp = new BusDepartureParser();
    private WebView webView;
    private GridPane gridPane;
    private GridPane busTableContainer;

    /**
     * Sends a HTTP request to Vasttrafik.
     * Gets a json object in response that is sent to the BusDepartureParser().
     *
     * @param busStop
     */
    public void setBusTimetable(String busStop, GridPane gridPane) {

        this.busStop = busStop;
        authCode = new GenerateAccessCode();
        String code = authCode.getResult();

        StringBuilder result = new StringBuilder();
        URL url;
        try {
            url = new URL("https://api.vasttrafik.se/bin/rest.exe/v2/departureBoard?id=" + busStop + "&date=" + busDate + "&time=" + busTime + "&needJourneyDetail=0&format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization:", "Bearer " + code);
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print(result.toString());
        bdp.busJsonParser(result.toString(), busTime, this.gridPane);

    }


    private void setVisible() {
        webView.setVisible(true);
        busTableContainer.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        setBusTimetable(this.busStop, this.busTableContainer);
    }

    //TODO please do not delete this one until we fix the new one
    public void INACTIVEsetBusTimetable(String busStop, WebView webViewBus, GridPane busGrid) {
        try {
            this.webView = webViewBus;
            this.busTableContainer = busGrid;

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
}
