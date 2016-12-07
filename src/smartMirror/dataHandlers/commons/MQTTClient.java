package smartMirror.dataHandlers.commons;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author CodeHigh @copyright on 06/12/2016.
 *         Class resposible for establishing connection with the MQTT broker
 */
public class MQTTClient
{
    private MqttConnectOptions options;
    private MqttClient client;
    private String clientId;

    /**
     * Constructor sets the options for the connection, sets the hostname of the broker and client id, then connects
     *
     * @param url broker hostname
     * @param id  client id
     */
    public MQTTClient(String url, String id)
    {
        try
        {
            this.clientId = id;
            options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setKeepAliveInterval(20);
            client = new MqttClient(url, id);
            client.connect(options);
            System.out.println("Client Connected!");
        }
        catch (MqttException e)
        {
            e.printStackTrace();
            disconnect();
            reconnect();
        }
    }

    /**
     * Method that publishes the last will and ends the connection with the broker
     */
    public void disconnect()
    {
        if (this.client.isConnected())
        {
            System.out.println("Disconnecting...");
            try
            {
                String topic = "presence/" + this.clientId;
                byte[] emptyArray = new byte[0];
                this.client.publish(topic, emptyArray, 1, true);

                client.disconnect();
            }
            catch (MqttException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method responsible for reestablishing the connection
     */
    void reconnect()
    {
        if (!client.isConnected())
        {
            try
            {
                client.connect(options);
            }
            catch (MqttException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Getter method that provides the MQTT client
     *
     * @return mqtt client
     */
    MqttClient getClient()
    {
        return client;
    }

    /**
     * Getter method that provide the client id of the connected client
     *
     * @return client id of the connected mqtt client
     */
    String getClientId()
    {
        return this.clientId;
    }
}
