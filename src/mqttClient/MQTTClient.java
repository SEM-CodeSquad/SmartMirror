package mqttClient;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;




public class MQTTClient
{
    private MqttConnectOptions options;
    private MqttClient client;
    private String clientId;

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
        }
    }

    public void disconnect(String clientId)
    {
        System.out.println("Disconnecting...");
        try
        {
            String topic = "presence/" + clientId;
            byte[] emptyArray = new byte[0];
            this.client.publish(topic, emptyArray, 1, true);

            client.disconnect();
        }
        catch (MqttException e)
        {
            e.printStackTrace();
        }
    }

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


    MqttClient getClient()
    {
        return client;
    }

    public String getClientId()
    {
        return this.clientId;
    }


}
