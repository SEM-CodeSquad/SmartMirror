package widgets;

import dataHandlers.BusDepartureParser;
import dataHandlers.GenerateAccessCode;
import dataModels.BusInfo;
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

public class BusTimetable extends Observable implements Observer {

    private static DateTimeFormatter SHORT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String busTime = LocalTime.now().format(SHORT_TIME_FORMATTER);
    private String busDate = LocalDate.now().format(SHORT_DATE_FORMATTER);
    private BusDepartureParser bdp = new BusDepartureParser();


    /**
     * Sends a HTTP request to Vasttrafik.
     * Gets a json object in response that is sent to the BusDepartureParser().
     *
     * @param busStop s
     */
    public void setBusTimetable(String busStop) {
        this.bdp.addObserver(this);
        GenerateAccessCode authCode = new GenerateAccessCode();
        String code = authCode.getResult();

        StringBuilder result = new StringBuilder();
        URL url;
        try {
            url = new URL("https://api.vasttrafik.se/bin/rest.exe/v2/departureBoard?id=" + busStop + "&date=" + busDate + "&time=" + busTime + "&needJourneyDetail=0&type=accurate&format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + code);
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
        bdp.busJsonParser(result.toString(), busTime);

    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof BusInfo[]) {
            BusInfo[] busInfos = (BusInfo[]) arg;
            setChanged();
            notifyObservers(busInfos);
        }
    }
}
