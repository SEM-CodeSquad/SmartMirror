package dataHandlers.widgetsDataHandlers.busTimetable;

import java.io.*;
import java.net.*;


public class GenerateAccessCode {
    private String code;

    public GenerateAccessCode() {


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
        code = result.substring(result.lastIndexOf(":")+2, result.length()-2);
    }

    public String getResult() {
        return code;
    }
}
