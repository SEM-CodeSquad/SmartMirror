package clientConnection;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class MQTTClient
{
    private MqttConnectOptions options;
    private MqttClient client;

    public MQTTClient(String url, String id)
    {
        try {
            options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setKeepAliveInterval(20);
            client = new MqttClient(url, id);
            client.connect(options);

            System.out.println("Client Connected!");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    //Disconnect function, call if needed.
    public void disconnect() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void startReconnect() {
                if (!client.isConnected()) {
                    try {
                        client.connect(options);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            }


    public MqttClient getClient()
    {
        return client;
    }
}
