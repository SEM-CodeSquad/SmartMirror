package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Class responsible for establishing http connection with web server and send data to it.
 */
class HttpRequestSender {

    private String brokerHostname;
    private String topic;
    private String msg;
    private String qos;
    private String retain;
    private String httpResponse;

    /**
     * Constructor for the http sender.
     *
     * @param brokerHostname String hostname for the broker
     * @param topic          String topic to be published on the broker
     * @param msg            String message to be published in the broker
     */
    HttpRequestSender(String brokerHostname, String topic, String msg, String qos, String retain) {
        this.brokerHostname = brokerHostname;
        this.topic = topic;
        this.msg = msg;
        this.qos = qos;
        this.retain = retain;
    }

    /**
     * This method connects to a web server and send a query string to the web server. The query string must contain
     * the necessary parameters otherwise the web server will not accept it.
     *
     * @param targetURL String containing the url and port to the web server
     */
    void executePost(String targetURL) {

        HttpURLConnection connection = null;

        try {
            String encodedMsg = URLEncoder.encode(this.msg, "utf-8");
            String query = "?broker=" + this.brokerHostname + "&topic=" + this.topic + "&msg=" + encodedMsg
                    + "&qos=" + this.qos + "&retain=" + this.retain + "&password=CodeHigh_SmartMirror";

            //Create connection
            URL url = new URL(targetURL + query);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);


            //Get Response
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            this.httpResponse = response.toString();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Method to get the String containing the response from the web server.
     *
     * @return String web server response
     */
    String getHttpResponse() {
        return httpResponse;
    }
}

