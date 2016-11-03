package dataHandlers;

import gui.InterfaceController;
import mqttClient.MQTTClient;
import mqttClient.SmartMirror_Publisher;
import mqttClient.SmartMirror_Subscriber;
import java.util.Observable;
import java.util.Observer;

public class PairingManager extends Observable implements Observer
{
    private String clientId;
    private MQTTClient client;
    private InterfaceController gui;
    private boolean clientPaired;

    public PairingManager(String clientId, MQTTClient client, InterfaceController gui)
    {
        this.clientId = clientId;
        this.client = client;
        this.gui = gui;
        this.clientPaired = false;
        addObserver(this.gui);
    }

    private void listenSubscription(String topic)
    {
        try
        {
            //TODO set pairing code back after testing
            Thread thread = new Thread(() ->{
                SmartMirror_Subscriber subscriber = new SmartMirror_Subscriber(this.client, topic);
                subscriber.addObserver(this.gui);
            });
            thread.start();
            System.out.println("Listening to: " + topic);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void listenPairing()
    {
       listenSubscription("dit029/SmartMirror/"+ this.clientId +"/pairing");
    }

    private void paired()
    {
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/postit");
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/settings");
        listenSubscription("dit029/SmartMirror/" + this.clientId + "/device");
    }

    private void sendHeartBeat()
    {
        SmartMirror_Publisher publisher = new SmartMirror_Publisher(this.client);
        String heartbeatTopic = "dit029/SmartMirror/" + this.clientId + "/heartbeat";
        String msg = clientId;
        Thread thread = new Thread(()->{
            while (this.clientPaired) {
                try
                {
                    Thread.sleep(300000);
                    publisher.publish(heartbeatTopic, msg);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public void update(Observable o, Object arg)
    {
       if (arg.equals("pairing"))
       {
           if (!this.clientPaired)
           {
               paired();
               sendHeartBeat();
               this.clientPaired = true;
               setChanged();
               notifyObservers(this);
           }
       }
    }
}
