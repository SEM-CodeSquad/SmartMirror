package clientConnection;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;


public class Client
{
   private MqttClient client;

    public Client(String url, String id)
    {
        try {
            client = new MqttClient(url, id);
            client.connect();
            System.out.println("Client Connected!");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public MqttClient getClient()
    {
        return client;
    }
}
