package Test;

/**
 * Created by Geoffrey on 2016/11/10.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class responsible for establishing http connection with web server and send data to it.
 */
public class HttpRequestSender
{

    private String brokerHostname;
    private String clientId;
    private String topic;
    private String msg;
    private String httpResponse;

    /**
     * Constructor for the http sender.
     * @param brokerHostname String hostname for the broker
     * @param clientId String client id in the broker
     * @param topic String topic to be published on the broker
     * @param msg String message to be published in the broker
     */
    HttpRequestSender(String brokerHostname, String clientId, String topic, String msg)
    {
        this.brokerHostname = brokerHostname;
        this.clientId = clientId;
        this.topic = topic;
        this.msg = msg;
    }

    /**
     * This method connects to a web server and send data to it the web server to be used is
     * "http://codehigh.ddns.net:5000/"
     * @param targetURL String containing the url and port to the web server
     */
    void executePost(String targetURL)
    {

        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects( false );
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "Application/SmartMirror");
            connection.setRequestProperty("Content-Language", "utf-8");
            connection.setRequestProperty("Broker", this.brokerHostname);
            connection.setRequestProperty("ClientId", this.clientId);
            connection.setRequestProperty("Topic", this.topic);
            connection.setRequestProperty("Message", this.msg);
            connection.setRequestProperty("CodeHigh", "");
            connection.setUseCaches(false);


            //Get Response
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }
            in.close();
            this.httpResponse = response.toString();

        } catch (Exception e)
        {
            e.printStackTrace();

        } finally
        {
            if (connection != null)
            {
                connection.disconnect();
            }
        }
    }

    /**
     *  Method to get the String containing the response from the web server.
     * @return String web server response
     */
    public String getHttpResponse()
    {
        return httpResponse;
    }
}

