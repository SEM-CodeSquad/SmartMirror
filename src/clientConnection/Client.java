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


//Create a new client with set URL and client ID with options set.
    public Client(String url, String id)
    {
        try {
            //Options set for the MQTT connections
            options = new MqttConnectOptions();
            //set the server to keep a log of connection activities
            options.setCleanSession(false);
            //set the connection time out to 10
            options.setConnectionTimeout(10);
            //client will send a heart beat to the server to keep itself alive, it is set to 20*1.5 sec.
            options.setKeepAliveInterval(20);
            //create a new client and set the URL and the ID
            client = new MqttClient(url, id);
            //connect the client to the server with options
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

//The reconnection functions. as a new single threaded scheduled.
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

//Client getter, it returns a client object.
    public MqttClient getClient()
    {
        return client;
    }
}
