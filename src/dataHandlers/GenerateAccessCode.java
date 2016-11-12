package dataHandlers;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


public class GenerateAccessCode {
    String code;

    public GenerateAccessCode() {


        StringBuilder result = new StringBuilder();

        URL url = null;
        try {
            String authParam = "Basic UmJseEkyeTFsWVNFTTZ0Z2J6anBTa2E0R1o6Wk1nSkR0Y0paRGV4OTJldUxpQUdYOFExUnU=grant_type=client_credentials&scope=mirror_1";
            String authEnc = URLEncoder.encode(authParam, "UTF-8");
            url = new URL("https://api.vasttrafik.se/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("HTTP-Version", "HTTP/1.1");
            conn.setRequestProperty("User-Agent", "Mozilla/4.0");
            conn.setRequestProperty("Content-Type:", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Authorization:", authEnc);
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.flush();
            wr.close();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);

            }
            rd.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(result.toString());

        code = result.toString();
    }

    public String getResult() {
        return code;
    }

    public static void main(String[] args) {
        GenerateAccessCode gen = new GenerateAccessCode();
        System.out.println(gen.getResult());
    }
}
