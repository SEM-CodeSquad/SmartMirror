package widgets;

import dataHandlers.BusDepartureParser;
import dataHandlers.GenerateAccessCode;
import javafx.concurrent.Worker;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Axel on 8/11/2016.
 */

public class BusTimetable {

    private WebView webView;
    private GridPane gridPane;
    private static DateTimeFormatter SHORT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String busTime = LocalTime.now().format(SHORT_TIME_FORMATTER);
    String busDate = LocalDate.now().format(SHORT_DATE_FORMATTER);
    GenerateAccessCode authCode;

    //TODO please do not delete this one until we fix the new one
    public void INACTIVEsetBusTimetable(String busStop, WebView webViewBus, GridPane busGrid)
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

    public void setBusTimetable(String busStop, GridPane busGrid) {

        authCode = new GenerateAccessCode();
        String code = authCode.getResult();
        StringBuilder result = new StringBuilder();
        URL url;
        try {
            url = new URL("https://api.vasttrafik.se/bin/rest.exe/v2/departureBoard?id=" + busStop + "&date=" + busDate + "&time=" + busTime + "&format=json");
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

        System.out.println(result.toString());

        BusDepartureParser bdp = new BusDepartureParser();
        bdp.busJsonParser(result.toString());
    }

}
