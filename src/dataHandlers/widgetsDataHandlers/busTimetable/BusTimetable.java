package dataHandlers.widgetsDataHandlers.busTimetable;

import dataModels.widgetsModels.busTimetableModels.BusInfo;
import dataModels.applicationModels.ChainedMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class BusTimetable extends Observable implements Observer {

    private static DateTimeFormatter SHORT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String busTime = LocalTime.now().format(SHORT_TIME_FORMATTER);
    private String busDate = LocalDate.now().format(SHORT_DATE_FORMATTER);
    private BusDepartureParser bdp = new BusDepartureParser();
    private String stop;


    /**
     * Sends a HTTP request to Vasttrafik.
     * Gets a json object in response that is sent to the BusDepartureParser().
     *
     * @param busStop s
     */
    public synchronized void setBusTimetable(String busStop) {
        this.stop = busStop;
        this.bdp.addObserver(this);

        String code = generateAccessCode();

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
        bdp.busJsonParser(result.toString());

    }

    private String generateAccessCode() {


        StringBuilder result = new StringBuilder();

        URL url;
        try {
            String authParam = "Basic b3BPMlJKT3o3em9RaEhseE5VS0ozWXdoaVJNYTpuajR3bmc2R3dqUWxBRHk1aktRRFR1aHpFdGNh";
            url = new URL("https://api.vasttrafik.se:443/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Authorization", authParam);
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            wr.write("grant_type=client_credentials");
            wr.flush();
            wr.close();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);

            }
            rd.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.substring(result.lastIndexOf(":") + 2, result.length() - 2);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(Observable o, Object arg) {
        if (arg instanceof BusInfo[]) {
            BusInfo[] busInfos = (BusInfo[]) arg;

            ChainedMap<String, LinkedList<BusInfo>> busData = new ChainedMap<>();

            for (BusInfo busInfo : busInfos) {

                if (busInfo != null) {
                    String key = busInfo.getBusName() + busInfo.getBusDirection();

                    if (busData.size() > 0 && busData.getId().contains(key)) {
                        busData.getMap().get(key).add(busInfo);
                    } else {
                        LinkedList<BusInfo> list = new LinkedList();
                        list.add(busInfo);
                        busData.add(key, list);
                    }
                }
            }

            setChanged();
            notifyObservers(busData);
        } else if (arg.equals("crash") && this.stop != null) {
            setBusTimetable(this.stop);
        }
    }
}
