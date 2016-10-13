package clientConnection;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class Client
{
    private MqttConnectOptions options;
    private ScheduledExecutorService scheduler;
   private MqttClient client;

    public Client(String url, String id)
    {
        try {
            options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setConnectionTimeout(10);
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
//TODO we might need to figure out a way to do this...
    public void startReconnect() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                if (!client.isConnected()) {
                    try {

                        client.connect(options);
                    } catch (MqttSecurityException e) {
                        e.printStackTrace();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
    }


    public MqttClient getClient()
    {
        return client;
    }
}
